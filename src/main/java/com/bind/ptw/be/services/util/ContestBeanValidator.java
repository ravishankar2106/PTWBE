package com.bind.ptw.be.services.util;

import java.text.ParseException;
import java.util.Date;

import com.bind.ptw.be.dao.ContestDao;
import com.bind.ptw.be.dao.TournamentDao;
import com.bind.ptw.be.dto.ContestBean;
import com.bind.ptw.be.dto.MatchBean;
import com.bind.ptw.be.dto.QuestionBean;
import com.bind.ptw.be.dto.TournamentTeamBean;
import com.bind.ptw.be.util.PTWConstants;
import com.bind.ptw.be.util.PTWException;
import com.bind.ptw.be.util.StringUtil;


public class ContestBeanValidator {
	
	public static void validateCreateMatch(MatchBean matchBean, TournamentDao tournamentDao, ContestDao contestDao) throws PTWException{
		TournamentBeanValidator.validateTournamentId(matchBean.getTournamentId());
		TournamentBeanValidator.validateTournament(matchBean.getTournamentId(), tournamentDao);
		
		String matchDateTimeStr = matchBean.getMatchDateTimeStr();
		if(StringUtil.isEmptyNull(matchDateTimeStr)){
			throw new PTWException(PTWConstants.ERROR_CODE_MATCH_DATE_EMPTY, PTWConstants.ERROR_DESC_FIELD_EMPTY + "Match Date");
		}
		
		try{
			Date matchDateTime = StringUtil.convertStringToDateTime(matchDateTimeStr);
			matchBean.setMatchDateTime(matchDateTime);
		}catch(ParseException exception){
			throw new PTWException(PTWConstants.ERROR_CODE_MATCH_DATE_INVALID, PTWConstants.ERROR_DESC_FIELD_INVALID + "Match Date");
		}
		TournamentTeamBean teamA = matchBean.getTeamA();
		validateMatchTeam(teamA);
		
		TournamentTeamBean teamB = matchBean.getTeamB();
		validateMatchTeam(teamB);
		if(teamA.getTournamentTeamId().equals(teamB.getTournamentTeamId())){
			throw new PTWException(PTWConstants.ERROR_CODE_MATCH_TEAMS_INVALID, PTWConstants.ERROR_DESC_MATCH_TEAMS_INVALID);
		}
		
	}
	
	public static void validateCreateContest(ContestBean contestBean, TournamentDao tournamentDao, ContestDao contestDao) throws PTWException{
		TournamentBeanValidator.validateTournamentId(contestBean.getTournamentId());
		TournamentBeanValidator.validateTournament(contestBean.getTournamentId(), tournamentDao);
		
		if(StringUtil.isEmptyNull(contestBean.getContestName())){
			throw new PTWException(PTWConstants.ERROR_CODE_CONTEST_NAME_EMPTY, PTWConstants.ERROR_DESC_FIELD_EMPTY + "Contest Name");
		}
		
		String contestStartDateTimeStr = contestBean.getPublishStartDateStr();
		if(StringUtil.isEmptyNull(contestStartDateTimeStr)){
			throw new PTWException(PTWConstants.ERROR_CODE_CONTEST_START_DATE_EMPTY, PTWConstants.ERROR_DESC_FIELD_EMPTY + "Start Date");
		}
		
		try{
			Date contestStartDateTime = StringUtil.convertStringToDateTime(contestStartDateTimeStr);
			contestBean.setPublishStartDate(contestStartDateTime);
		}catch(ParseException exception){
			throw new PTWException(PTWConstants.ERROR_CODE_CONTEST_DATE_INVALID, PTWConstants.ERROR_DESC_FIELD_INVALID + "Start Date");
		}
		
		String contestEndDateTimeStr = contestBean.getPublishEndDateStr();
		if(StringUtil.isEmptyNull(contestEndDateTimeStr)){
			throw new PTWException(PTWConstants.ERROR_CODE_CONTEST_END_DATE_EMPTY, PTWConstants.ERROR_DESC_FIELD_EMPTY + "End Date");
		}
		
		try{
			Date contestEndDateTime = StringUtil.convertStringToDateTime(contestEndDateTimeStr);
			contestBean.setPublishEndDate(contestEndDateTime);
		}catch(ParseException exception){
			throw new PTWException(PTWConstants.ERROR_CODE_CONTEST_DATE_INVALID, PTWConstants.ERROR_DESC_FIELD_INVALID + "End Date");
		}
		
		if(contestBean.getPublishStartDate().after(contestBean.getPublishEndDate())){
			throw new PTWException(PTWConstants.ERROR_CODE_CONTEST_END_DATE_EMPTY, PTWConstants.ERROR_DESC_FIELD_EMPTY + "End Date");
		}
		Date currentDate = new Date();
		if(contestBean.getPublishEndDate().before(currentDate)){
			throw new PTWException(PTWConstants.ERROR_CODE_CONTEST_END_DATE_EMPTY, PTWConstants.ERROR_DESC_FIELD_EMPTY + "End Date");
		}
		
		
		String contestCutoffDateTimeStr = contestBean.getCutoffDateStr();
		if(StringUtil.isEmptyNull(contestCutoffDateTimeStr)){
			throw new PTWException(PTWConstants.ERROR_CODE_CONTEST_CUTOFF_DATE_EMPTY, PTWConstants.ERROR_DESC_FIELD_EMPTY + "Cutoff Date");
		}
		
		try{
			Date contestCutoffDateTime = StringUtil.convertStringToDateTime(contestCutoffDateTimeStr);
			contestBean.setCutoffDate(contestCutoffDateTime);
		}catch(ParseException exception){
			throw new PTWException(PTWConstants.ERROR_CODE_CONTEST_DATE_INVALID, PTWConstants.ERROR_DESC_FIELD_INVALID + "Cutoff Date");
		}
		
		if(contestBean.getCutoffDate().before(contestBean.getPublishEndDate())){
			throw new PTWException(PTWConstants.ERROR_CODE_CONTEST_END_DATE_EMPTY, PTWConstants.ERROR_DESC_FIELD_EMPTY + "Cutoff Date");
		}
		
		if(contestBean.getCutoffDate().before(currentDate)){
			throw new PTWException(PTWConstants.ERROR_CODE_CONTEST_END_DATE_EMPTY, PTWConstants.ERROR_DESC_FIELD_EMPTY + "Cutoff Date");
		}
		
		Integer contestTypeId = contestBean.getContestTypeId();
		if(!StringUtil.isEmptyNull(contestTypeId)){
			if(!(contestTypeId == 1 || contestTypeId == 2)){
				throw new PTWException(PTWConstants.ERROR_CODE_CONTEST_TYPE_INVALID, PTWConstants.ERROR_DESC_FIELD_INVALID + "Contest Type");
			}
			if(contestTypeId==1 && StringUtil.isEmptyNull(contestBean.getMatchId())){
				throw new PTWException(PTWConstants.ERROR_CODE_CONTEST_TYPE_INVALID, PTWConstants.ERROR_DESC_FIELD_INVALID + "Contest Type");
			}
		}
		
		if(StringUtil.isEmptyNull(contestBean.getBonusPoints())){
			contestBean.setBonusPoints(0);
		}
		
	}
	
	public static void validateContestId(Integer contestId) throws PTWException{
		if(StringUtil.isEmptyNull(contestId)){
			throw new PTWException(PTWConstants.ERROR_CODE_CONTEST_ID_EMPTY, PTWConstants.ERROR_DESC_FIELD_EMPTY + "Contest Id");
		}
	}
	
	private static void validateContestStatusId(Integer statusId) throws PTWException{
		if(StringUtil.isEmptyNull(statusId)){
			throw new PTWException(PTWConstants.ERROR_CODE_CONTEST_STATUS_ID_EMPTY, PTWConstants.ERROR_DESC_FIELD_EMPTY + "Status Id");
		}
	}
	
	public static void validateUpdateContest(ContestBean contestBean, TournamentDao tournamentDao, ContestDao contestDao) throws PTWException{
		
		validateContestId(contestBean.getContestId());
		
		String contestStartDateTimeStr = contestBean.getPublishStartDateStr();
		if(!StringUtil.isEmptyNull(contestStartDateTimeStr)){
			try{
				Date contestStartDateTime = StringUtil.convertStringToDateTime(contestStartDateTimeStr);
				contestBean.setPublishStartDate(contestStartDateTime);
			}catch(ParseException exception){
				throw new PTWException(PTWConstants.ERROR_CODE_CONTEST_DATE_INVALID, PTWConstants.ERROR_DESC_FIELD_INVALID + "Start Date");
			}
		}
		
		String contestEndDateTimeStr = contestBean.getPublishEndDateStr();
		if(!StringUtil.isEmptyNull(contestEndDateTimeStr)){
			try{
				Date contestEndDateTime = StringUtil.convertStringToDateTime(contestEndDateTimeStr);
				contestBean.setPublishEndDate(contestEndDateTime);
			}catch(ParseException exception){
				throw new PTWException(PTWConstants.ERROR_CODE_CONTEST_DATE_INVALID, PTWConstants.ERROR_DESC_FIELD_INVALID + "End Date");
			}
		}
		
		String contestCutoffDateTimeStr = contestBean.getCutoffDateStr();
		if(StringUtil.isEmptyNull(contestCutoffDateTimeStr)){
			try{
				Date contestCutoffDateTime = StringUtil.convertStringToDateTime(contestCutoffDateTimeStr);
				contestBean.setCutoffDate(contestCutoffDateTime);
			}catch(ParseException exception){
				throw new PTWException(PTWConstants.ERROR_CODE_CONTEST_DATE_INVALID, PTWConstants.ERROR_DESC_FIELD_INVALID + "Cutoff Date");
			}
		}
		
		Integer contestTypeId = contestBean.getContestTypeId();
		if(!StringUtil.isEmptyNull(contestTypeId)){
			if(!(contestTypeId == 1 || contestTypeId == 2)){
				throw new PTWException(PTWConstants.ERROR_CODE_CONTEST_TYPE_INVALID, PTWConstants.ERROR_DESC_FIELD_INVALID + "Contest Type");
			}
			if(contestTypeId==1 && StringUtil.isEmptyNull(contestBean.getMatchId())){
				throw new PTWException(PTWConstants.ERROR_CODE_CONTEST_TYPE_INVALID, PTWConstants.ERROR_DESC_FIELD_INVALID + "Contest Type");
			}
		}
		
	}
	
	public static ContestBean validateUpdateContestStatus(ContestBean contestBean, TournamentDao tournamentDao, ContestDao contestDao) throws PTWException{
		ContestBean newContestBean = new ContestBean();
		
		validateContestId(contestBean.getContestId());
		validateContestStatusId(contestBean.getContestStatusId());
		
		newContestBean.setContestId(contestBean.getContestId());
		newContestBean.setContestStatusId(contestBean.getContestStatusId());
		return newContestBean;
	}
	
	private static void validateMatchTeam(TournamentTeamBean team) throws PTWException{
		if(team == null || StringUtil.isEmptyNull(team.getTournamentTeamId())){
			throw new PTWException(PTWConstants.ERROR_CODE_TEAM_ID_EMPTY, PTWConstants.ERROR_DESC_FIELD_EMPTY + "Tournament Team Id");
		}
	}
	
	public static void validateMatchId(Integer matchId) throws PTWException{
		if(StringUtil.isEmptyNull(matchId)){
			throw new PTWException(PTWConstants.ERROR_CODE_MATCH_ID_EMPTY, PTWConstants.ERROR_DESC_FIELD_EMPTY + "Match Id");
		}
	}
	
	public static void validateStatusId(Integer statusId) throws PTWException{
		if(StringUtil.isEmptyNull(statusId)){
			throw new PTWException(PTWConstants.ERROR_CODE_MATCH_STATUS_ID_EMPTY, PTWConstants.ERROR_DESC_FIELD_EMPTY + "Match Status Id");
		}
	}
	
	public static void validateCreateQuestion(QuestionBean questionBean, TournamentDao tournamentDao, ContestDao contestDao) throws PTWException{
		validateContestId(questionBean.getContestId());
		
		String questionStr = questionBean.getQuestion();
		if(StringUtil.isEmptyNull(questionStr)){
			throw new PTWException(PTWConstants.ERROR_DESC_FIELD_EMPTY, PTWConstants.ERROR_DESC_FIELD_EMPTY + "Question");
		}
		
		Integer answerTypeId = questionBean.getAnswerTypeId();
		if(StringUtil.isEmptyNull(answerTypeId)){
			throw new PTWException(PTWConstants.ERROR_DESC_FIELD_EMPTY, PTWConstants.ERROR_DESC_FIELD_EMPTY + "Answer Type");
		}
		
		if(StringUtil.isEmptyNull(questionBean.getAnswerCount())){
			questionBean.setAnswerCount(1);
		}else{
			if(questionBean.getAnswerCount() > 4){
				throw new PTWException(PTWConstants.ERROR_CODE_QUESTION_ANSWER_COUNT_INVALID, PTWConstants.ERROR_DESC_QUESTION_ANSWER_COUNT_INVALID);
			}
		}
		
	}
	
	public static void validateUpdateQuestion(QuestionBean questionBean, TournamentDao tournamentDao, ContestDao contestDao) throws PTWException{
		validateQuestionId(questionBean.getQuestionId());
		
		String questionStr = questionBean.getQuestion();
		if(StringUtil.isEmptyNull(questionStr)){
			throw new PTWException(PTWConstants.ERROR_DESC_FIELD_EMPTY, PTWConstants.ERROR_DESC_FIELD_EMPTY + "Question");
		}
		
		Integer answerTypeId = questionBean.getAnswerTypeId();
		if(StringUtil.isEmptyNull(answerTypeId)){
			throw new PTWException(PTWConstants.ERROR_DESC_FIELD_EMPTY, PTWConstants.ERROR_DESC_FIELD_EMPTY + "Answer Type");
		}
		
		if(StringUtil.isEmptyNull(questionBean.getAnswerCount())){
			questionBean.setAnswerCount(1);
		}else{
			if(questionBean.getAnswerCount() > 4){
				throw new PTWException(PTWConstants.ERROR_CODE_QUESTION_ANSWER_COUNT_INVALID, PTWConstants.ERROR_DESC_QUESTION_ANSWER_COUNT_INVALID);
			}
		}
		
	}
	
	public static void validateQuestionId(Integer questionId) throws PTWException{
		if(StringUtil.isEmptyNull(questionId)){
			throw new PTWException(PTWConstants.ERROR_DESC_FIELD_EMPTY, PTWConstants.ERROR_DESC_FIELD_EMPTY + "Question Id");
		}
	}
	
}
