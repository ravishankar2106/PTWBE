package com.bind.ptw.be.services.util;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bind.ptw.be.dao.ContestDao;
import com.bind.ptw.be.dao.TournamentDao;
import com.bind.ptw.be.dto.AnswerBean;
import com.bind.ptw.be.dto.AnswerOptionBean;
import com.bind.ptw.be.dto.ContestBean;
import com.bind.ptw.be.dto.MatchBean;
import com.bind.ptw.be.dto.PrizeContestBean;
import com.bind.ptw.be.dto.QuestionBean;
import com.bind.ptw.be.dto.TournamentTeamBean;
import com.bind.ptw.be.dto.UserAnswerBean;
import com.bind.ptw.be.dto.UserContestAnswer;
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
		//TournamentBeanValidator.validateTournament(contestBean.getTournamentId(), tournamentDao);
		
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
		
		if(contestBean.getCutoffDate().before(contestBean.getPublishStartDate())){
			throw new PTWException(PTWConstants.ERROR_CODE_CONTEST_DATE_INVALID, PTWConstants.ERROR_DESC_FIELD_INVALID + "Cutoff Date");
		}
		
		if(contestBean.getCutoffDate().before(currentDate)){
			throw new PTWException(PTWConstants.ERROR_CODE_CONTEST_DATE_INVALID, PTWConstants.ERROR_DESC_FIELD_INVALID + "Cutoff Date");
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
		if(!(statusId == 2 || statusId == 3)){
			throw new PTWException(PTWConstants.ERROR_CODE_CONTEST_STATUS_ID_INVALID, PTWConstants.ERROR_DESC_FIELD_INVALID + "Status Id");
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
		if(!(statusId == 1 || statusId == 2 || statusId ==3)){
			throw new PTWException(PTWConstants.ERROR_CODE_MATCH_STATUS_ID_INVALID, PTWConstants.ERROR_DESC_FIELD_INVALID + "Match Status Id");
			
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
	
	public static void validateAnswerOptionId(Integer answerOption) throws PTWException{
		if(StringUtil.isEmptyNull(answerOption)){
			throw new PTWException(PTWConstants.ERROR_DESC_FIELD_EMPTY, PTWConstants.ERROR_DESC_FIELD_EMPTY + "Answer Id");
		}
	}
	
	public static void validateAnswerOption(List<AnswerOptionBean> answerOptionBeanList) throws PTWException{
		if(answerOptionBeanList == null || answerOptionBeanList.isEmpty()){
			throw new PTWException(PTWConstants.ERROR_DESC_FIELD_EMPTY, PTWConstants.ERROR_DESC_FIELD_EMPTY + "Answer Option");
		}
		for (AnswerOptionBean answerOptionBean : answerOptionBeanList) {
			if(StringUtil.isEmptyNull(answerOptionBean.getAnswerOptionStr())){
				throw new PTWException(PTWConstants.ERROR_DESC_FIELD_EMPTY, PTWConstants.ERROR_DESC_FIELD_EMPTY + "Answer Option");
			}
		}
		
	}
	
	public static void validateAnswerOption(AnswerOptionBean answerOptionBean) throws PTWException{
		if(StringUtil.isEmptyNull(answerOptionBean.getAnswerOptionStr())){
			throw new PTWException(PTWConstants.ERROR_DESC_FIELD_EMPTY, PTWConstants.ERROR_DESC_FIELD_EMPTY + "Answer Option");
		}
	}
	
	public static void validateUserAnswer(UserContestAnswer userContestAnswer, ContestDao contestDao)throws PTWException{
		validateContestId(userContestAnswer.getContestId());
		UserBeanValidator.validateUserId(userContestAnswer.getUserId());
		List<UserAnswerBean> userAnswerBeanList = userContestAnswer.getUserAnswerList();
		if(userAnswerBeanList == null || userAnswerBeanList.isEmpty()){
			throw new PTWException(PTWConstants.ERROR_CODE_ANSWER_EMPTY, PTWConstants.ERROR_DESC_FIELD_EMPTY + "User Answer");
		}
		
		ContestBean queryContest = new ContestBean();
		queryContest.setContestId(userContestAnswer.getContestId());
		ContestBean contest = contestDao.getContest(queryContest);
		if(contest.getContestStatusId()!=1 || contest.getCutoffDate().before(new Date())){
			throw new PTWException(PTWConstants.ERROR_CODE_CONTEST_CUTOFF_TIME_OVER, PTWConstants.ERROR_DESC_CONTEST_CUTOFF_TIME_OVER);
		}
		
		List<QuestionBean> dbQuestionBeanList = contestDao.getQuestion(queryContest);
		Map<Integer, QuestionBean> dbQuestionMap = getMatchingQuestion(dbQuestionBeanList);
		
		for (UserAnswerBean userAnswerBean : userAnswerBeanList) {
			Integer questionId = userAnswerBean.getQuestionId();
			validateQuestionId(questionId);
			QuestionBean dbQuestionBean = dbQuestionMap.get(questionId);
			if(dbQuestionBean == null){
				throw new PTWException(PTWConstants.ERROR_CODE_CONTEST_INVALID_QUESTION, PTWConstants.ERROR_DESC_CONTEST_INVALID_QUESTION);
			}
			
			List<AnswerBean> answerBeanList = userAnswerBean.getSelectedAnswerList();
			if(answerBeanList == null || answerBeanList.isEmpty()){
				throw new PTWException(PTWConstants.ERROR_DESC_FIELD_EMPTY, PTWConstants.ERROR_DESC_FIELD_EMPTY + "User Answer");
			}
			if(answerBeanList.size() != dbQuestionBean.getAnswerCount()){
				throw new PTWException(PTWConstants.ERROR_CODE_CONTEST_INCOMPLETE_ANSWER, PTWConstants.ERROR_DESC_CONTEST_INCOMPLETE_ANSWER);
			}
			
			for (AnswerBean answerBean : answerBeanList) {
				if(StringUtil.isEmptyNull(answerBean.getAnswerOptionId())){
					throw new PTWException(PTWConstants.ERROR_DESC_FIELD_EMPTY, PTWConstants.ERROR_DESC_FIELD_EMPTY + "User Answer");
				}
			}
		}
		
	}

	private static Map<Integer, QuestionBean> getMatchingQuestion(List<QuestionBean> dbQuestionBeanList) {
		Map<Integer, QuestionBean> dbQuestionBeanMap = new HashMap<Integer, QuestionBean>();
		if(dbQuestionBeanList != null && !dbQuestionBeanList.isEmpty() ){
			for (QuestionBean questionBean : dbQuestionBeanList) {
				dbQuestionBeanMap.put(questionBean.getQuestionId(), questionBean);
			}
		}
		
		return dbQuestionBeanMap;
	}

	public static void validateUpdateAnswerOption(AnswerOptionBean answerOptionBean) throws PTWException {
		validateAnswerOptionId(answerOptionBean.getAnswerOptionId());
		if(answerOptionBean.getCorrectAnswerFlag() != null && answerOptionBean.getCorrectAnswerFlag()){
			if(StringUtil.isEmptyNull(answerOptionBean.getPoints())){
				throw new PTWException(PTWConstants.ERROR_DESC_FIELD_EMPTY, PTWConstants.ERROR_DESC_FIELD_EMPTY + "Points");
			}
		}else{
			if(!StringUtil.isEmptyNull(answerOptionBean.getPoints())){
				answerOptionBean.setCorrectAnswerFlag(true);
			}
				
		}
		
	}

	public static void validateCreatePrizeContest(PrizeContestBean prizeContestBean) throws PTWException {
		TournamentBeanValidator.validateTournamentId(prizeContestBean.getTournamentId());
		if(StringUtil.isEmptyNull(prizeContestBean.getPrizeContestName())){
			throw new PTWException(PTWConstants.ERROR_DESC_FIELD_EMPTY, PTWConstants.ERROR_DESC_FIELD_EMPTY + "Contest Name");
		}
		
		String contestStartDateStr = prizeContestBean.getStartDateStr();
		if(StringUtil.isEmptyNull(contestStartDateStr)){
			throw new PTWException(PTWConstants.ERROR_CODE_CONTEST_START_DATE_EMPTY, PTWConstants.ERROR_DESC_FIELD_EMPTY + "Start Date");
		}
		
		try{
			Date contestStartDate = StringUtil.convertStringToDate(contestStartDateStr);
			prizeContestBean.setStartDate(contestStartDate);
		}catch(ParseException exception){
			throw new PTWException(PTWConstants.ERROR_CODE_CONTEST_DATE_INVALID, PTWConstants.ERROR_DESC_FIELD_INVALID + "Start Date");
		}
		
		String contestEndDateStr = prizeContestBean.getEndDateStr();
		if(StringUtil.isEmptyNull(contestEndDateStr)){
			throw new PTWException(PTWConstants.ERROR_CODE_CONTEST_END_DATE_EMPTY, PTWConstants.ERROR_DESC_FIELD_EMPTY + "End Date");
		}
		
		try{
			Date contestEndDate = StringUtil.convertStringToDate(contestEndDateStr);
			prizeContestBean.setEndDate(contestEndDate);
		}catch(ParseException exception){
			throw new PTWException(PTWConstants.ERROR_CODE_CONTEST_DATE_INVALID, PTWConstants.ERROR_DESC_FIELD_INVALID + "End Date");
		}
		
		if(prizeContestBean.getStartDate().after(prizeContestBean.getEndDate())){
			throw new PTWException(PTWConstants.ERROR_CODE_CONTEST_END_DATE_EMPTY, PTWConstants.ERROR_DESC_FIELD_EMPTY + "End Date");
		}
		
		if(StringUtil.isEmptyNull(prizeContestBean.getWinnerSize())){
			throw new PTWException(PTWConstants.ERROR_DESC_FIELD_EMPTY, PTWConstants.ERROR_DESC_FIELD_EMPTY + "Winners Size");
		}
		
		if(prizeContestBean.getWinnerSize() > 100){
			throw new PTWException(PTWConstants.ERROR_CODE_PRIZE_WINNERS_INVALID, PTWConstants.ERROR_DESC_FIELD_INVALID + "Winners Size");
		}
		
	}
	
	public static void validatePrizeContestId(Integer prizeContestId) throws PTWException{
		if(StringUtil.isEmptyNull(prizeContestId)){
			throw new PTWException(PTWConstants.ERROR_DESC_FIELD_EMPTY, PTWConstants.ERROR_DESC_FIELD_EMPTY + "Prize Contest Id");
		}
	}

	public static void validateUpdatePrizeContest(PrizeContestBean prizeContestBean) throws PTWException{
		validatePrizeContestId(prizeContestBean.getPrizeContestId());
		validateCreatePrizeContest(prizeContestBean);
	}
	
}
