package com.bind.ptw.be.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.bind.ptw.be.dao.ContestDao;
import com.bind.ptw.be.dto.AnswerBean;
import com.bind.ptw.be.dto.AnswerOptionBean;
import com.bind.ptw.be.dto.AnswerTypeBean;
import com.bind.ptw.be.dto.CodeMojoRewardBean;
import com.bind.ptw.be.dto.ContestBean;
import com.bind.ptw.be.dto.Coupon;
import com.bind.ptw.be.dto.LeaderBoardBean;
import com.bind.ptw.be.dto.LeaderBoardBeanList;
import com.bind.ptw.be.dto.MatchBean;
import com.bind.ptw.be.dto.PrizeContestBean;
import com.bind.ptw.be.dto.PrizeContestWinnerBean;
import com.bind.ptw.be.dto.QuestionBean;
import com.bind.ptw.be.dto.TournamentBean;
import com.bind.ptw.be.dto.TournamentFanClubBean;
import com.bind.ptw.be.dto.TournamentTAndCBean;
import com.bind.ptw.be.dto.TournamentTeamBean;
import com.bind.ptw.be.dto.UserAnswerBean;
import com.bind.ptw.be.dto.UserContestAnswer;
import com.bind.ptw.be.dto.UserGroupBean;
import com.bind.ptw.be.dto.UserScoreBoardBean;
import com.bind.ptw.be.dto.UserSelectedAnswerBean;
import com.bind.ptw.be.entities.AnswerOption;
import com.bind.ptw.be.entities.AnswerOptionHome;
import com.bind.ptw.be.entities.AnswerType;
import com.bind.ptw.be.entities.CodeMojoReward;
import com.bind.ptw.be.entities.CodeMojoRewardHome;
import com.bind.ptw.be.entities.Contest;
import com.bind.ptw.be.entities.ContestHome;
import com.bind.ptw.be.entities.ContestStatus;
import com.bind.ptw.be.entities.ContestType;
import com.bind.ptw.be.entities.Match;
import com.bind.ptw.be.entities.MatchHome;
import com.bind.ptw.be.entities.MatchStatus;
import com.bind.ptw.be.entities.PrizeContest;
import com.bind.ptw.be.entities.PrizeContestHome;
import com.bind.ptw.be.entities.PrizeContestWinners;
import com.bind.ptw.be.entities.PrizeContestWinnersHome;
import com.bind.ptw.be.entities.Question;
import com.bind.ptw.be.entities.QuestionHome;
import com.bind.ptw.be.entities.TermsAndConditionHome;
import com.bind.ptw.be.entities.TermsCondition;
import com.bind.ptw.be.entities.Tournament;
import com.bind.ptw.be.entities.TournamentHome;
import com.bind.ptw.be.entities.TournamentTeam;
import com.bind.ptw.be.entities.TournamentTeamHome;
import com.bind.ptw.be.entities.UserAnswer;
import com.bind.ptw.be.entities.UserAnswerHome;
import com.bind.ptw.be.entities.UserAnswerStats;
import com.bind.ptw.be.entities.UserAnswerStatsHome;
import com.bind.ptw.be.entities.UserBonusPoint;
import com.bind.ptw.be.entities.UserBonusPointHome;
import com.bind.ptw.be.entities.UserGroup;
import com.bind.ptw.be.entities.UserGroupHome;
import com.bind.ptw.be.entities.UserGroupMapping;
import com.bind.ptw.be.entities.UserGroupMappingHome;
import com.bind.ptw.be.entities.UserScoreBoard;
import com.bind.ptw.be.entities.UserScoreBoardHome;
import com.bind.ptw.be.entities.UserTournamentRegistration;
import com.bind.ptw.be.entities.UserTournamentRegistrationHome;
import com.bind.ptw.be.entities.Users;
import com.bind.ptw.be.util.PTWConstants;
import com.bind.ptw.be.util.PTWException;
import com.bind.ptw.be.util.StringUtil;

@Repository("contestDao")
public class ContestDaoImpl implements ContestDao{

	@Autowired
	private HibernateTemplate hibernateTemplate;
	
	private Session getSession() {
	    return hibernateTemplate.getSessionFactory().getCurrentSession();
	}
	
	@Override
	public MatchBean createMatch(MatchBean matchBean) throws PTWException{
		MatchBean retMatchBean = new MatchBean();
		try{
			Tournament tournament = getTournament(matchBean.getTournamentId());
			TournamentTeam teamA = getTournamentTeam(matchBean.getTeamA().getTournamentTeamId());
			if(teamA == null || (!tournament.getTournamentId().equals(teamA.getTournamentId()))){
				throw new PTWException(PTWConstants.ERROR_CODE_TEAM_ID_INVALID, PTWConstants.ERROR_DESC_FIELD_INVALID + "Team A");
			}
			
			TournamentTeam teamB = getTournamentTeam(matchBean.getTeamB().getTournamentTeamId());
			if(teamB == null || (!tournament.getTournamentId().equals(teamB.getTournamentId()))){
				throw new PTWException(PTWConstants.ERROR_CODE_TEAM_ID_INVALID, PTWConstants.ERROR_DESC_FIELD_INVALID + "Team B");
			}
			MatchHome matchHome = new MatchHome(this.getSession());
			Match match = new Match();
			match.setTournament(tournament);
			match.setTeamA(teamA);
			match.setTeamB(teamB);
			match.setMatchDateTime(matchBean.getMatchDateTime());
			
			Integer matchNo = matchBean.getMatchNo();
			if(StringUtil.isEmptyNull(matchNo)){
				matchNo = matchHome.getMatchNo(matchBean.getTournamentId())+ 1;
			}
			match.setMatchNo(matchNo);
			
			String venue = matchBean.getVenue();
			if(StringUtil.isEmptyNull(venue)){
				venue = tournament.getTournamentVenue();
			}
			match.setVenue(venue);
			
			MatchStatus matchStatus = new MatchStatus();
			matchStatus.setMatchStatusId(1);
			match.setMatchStatus(matchStatus);
			
			matchHome.persist(match);
			retMatchBean.setMatchId(match.getMatchId());
		}catch(PTWException exception){
			throw exception;
		}catch(Exception exception){
			exception.printStackTrace();
			throw new PTWException(PTWConstants.ERROR_CODE_DB_EXCEPTION, PTWConstants.ERROR_DESC_DB_EXCEPTION);
		}
		return retMatchBean;
	}

	private Tournament getTournament(Integer tournamentId) {
		TournamentHome tournamentHome = new TournamentHome(this.getSession());
		Tournament tournament = tournamentHome.findById(tournamentId);
		return tournament;
	}
	
	private TournamentTeam getTournamentTeam(Integer tournamentTeamId) {
		TournamentTeamHome tournamentTeamHome = new TournamentTeamHome(this.getSession());
		TournamentTeam tournamentTeam = tournamentTeamHome.findById(tournamentTeamId);
		return tournamentTeam;
	}

	@Override
	public List<MatchBean> getMatches(MatchBean matchBean) throws PTWException {
		MatchHome matchHome = new MatchHome(this.getSession());
		List<MatchBean> retMatchBeanList = null;
		try{
			List<Match> dbMatchList = matchHome.findMatchByFilter(matchBean);
			if(dbMatchList != null && !dbMatchList.isEmpty()){
				retMatchBeanList = new ArrayList<MatchBean>();
				for (Match match : dbMatchList) {
					MatchBean retMatchBean = new MatchBean();
					retMatchBean.setMatchId(match.getMatchId());
					retMatchBean.setMatchNo(match.getMatchNo());
					retMatchBean.setTournamentId(match.getTournamentId());
					retMatchBean.setVenue(match.getVenue());
					retMatchBean.setMatchDateTime(match.getMatchDateTime());
					retMatchBean.setMatchDateTimeStr(StringUtil.convertDateTImeToString(match.getMatchDateTime()));
					retMatchBean.setMatchStatusId(match.getMatchStatus().getMatchStatusId());
					retMatchBean.setMatchStatusStr(match.getMatchStatus().getMatchStatusName());
					
					retMatchBean.setTeamA(getTournamentTeamBean(match.getTeamA()));
					retMatchBean.setTeamB(getTournamentTeamBean(match.getTeamB()));
					retMatchBeanList.add(retMatchBean);
				}
			}
		}catch(Exception exception){
			exception.printStackTrace();
			throw new PTWException(PTWConstants.ERROR_CODE_DB_EXCEPTION, PTWConstants.ERROR_DESC_DB_EXCEPTION);
		}
		return retMatchBeanList;
	}

	private TournamentTeamBean getTournamentTeamBean(TournamentTeam tournamentTeam) {
		TournamentTeamBean tournamentTeamBean = new TournamentTeamBean();
		tournamentTeamBean.setTournamentTeamId(tournamentTeam.getTournamentTeamId());
		tournamentTeamBean.setTeamId(tournamentTeam.getTeam().getTeamId());
		tournamentTeamBean.setTeamName(tournamentTeam.getTeam().getTeamName());
		tournamentTeamBean.setTeamShortName(tournamentTeam.getTeam().getTeamShortName());
		return tournamentTeamBean;
	}

	@Override
	public void updateMatchStatus(MatchBean matchBean) throws PTWException {
		try{
			MatchHome matchHome = new MatchHome(this.getSession());
			Match match = matchHome.findById(matchBean.getMatchId());
			if(match == null){
				throw new PTWException(PTWConstants.ERROR_CODE_MATCH_NOT_FOUND, PTWConstants.ERROR_DESC_MATCH_NOT_FOUND);
			}
			MatchStatus newMatchStatus = new MatchStatus();
			newMatchStatus.setMatchStatusId(matchBean.getMatchStatusId());
			match.setMatchStatus(newMatchStatus);
			matchHome.merge(match);
		}catch(PTWException exception){
			throw exception;
		}catch(Exception exception){
			throw new PTWException(PTWConstants.ERROR_CODE_DB_EXCEPTION, PTWConstants.ERROR_DESC_DB_EXCEPTION); 
		}
	}

	@Override
	public void deleteMatch(MatchBean matchBean) throws PTWException {
		try{
			MatchHome matchHome = new MatchHome(this.getSession());
			Match match = matchHome.findById(matchBean.getMatchId());
			if(match == null){
				throw new PTWException(PTWConstants.ERROR_CODE_MATCH_NOT_FOUND, PTWConstants.ERROR_DESC_MATCH_NOT_FOUND);
			}
			matchHome.remove(match);
		}catch(PTWException exception){
			throw exception;
		}catch(Exception exception){
			throw new PTWException(PTWConstants.ERROR_CODE_DB_EXCEPTION, PTWConstants.ERROR_DESC_DB_EXCEPTION); 
		}
	}

	@Override
	public List<TournamentTeamBean> getMatchTeams(MatchBean matchBean) throws PTWException{
		List<TournamentTeamBean> tournamentTeamBeanList = new ArrayList<TournamentTeamBean>();
		MatchHome matchHome = new MatchHome(this.getSession());
		try{
			Match match = matchHome.findById(matchBean.getMatchId());
			TournamentTeam tournamentTeamA = match.getTeamA();
			TournamentTeam tournamentTeamB = match.getTeamB();
			TournamentTeamBean tournamentTeamBeanA = getTournamentTeamBean(tournamentTeamA);
			TournamentTeamBean tournamentTeamBeanB = getTournamentTeamBean(tournamentTeamB);
			tournamentTeamBeanList.add(tournamentTeamBeanA);
			tournamentTeamBeanList.add(tournamentTeamBeanB);
		}catch(Exception exception){
			throw new PTWException(PTWConstants.ERROR_CODE_DB_EXCEPTION, PTWConstants.ERROR_DESC_DB_EXCEPTION);
		}
		return tournamentTeamBeanList;
	}
	
	@Override
	public ContestBean createContest(ContestBean contestBean) throws PTWException {
		ContestBean retContestBean = new ContestBean();
		ContestHome contestHome = new ContestHome(this.getSession());
		try{
			Contest contest = new Contest();
			contest.setContestName(contestBean.getContestName());
			contest.setPublishStartDateTime(contestBean.getPublishStartDate());
			contest.setPublishEndDateTime(contestBean.getPublishEndDate());
			contest.setCutoffDateTime(contestBean.getCutoffDate());
			contest.setBonusPoints(contestBean.getBonusPoints());
			contest.setTocGroupId(contestBean.getTocId());
			
			Tournament tournament = new Tournament();
			tournament.setTournamentId(contestBean.getTournamentId());
			contest.setTournament(tournament);
			
			if(!StringUtil.isEmptyNull(contestBean.getMatchId())){
				MatchHome matchHome = new MatchHome(this.getSession());
				Match match = matchHome.findById(contestBean.getMatchId());
				if(!contestBean.getTournamentId().equals(match.getTournamentId())){
					throw new PTWException(PTWConstants.ERROR_CODE_CONTEST_MATCH_INVALID, PTWConstants.ERROR_DESC_CONTEST_MATCH_INVALID);
				}
				match.setMatchId(contestBean.getMatchId());
				contest.setMatch(match);
			}
			
			ContestType contestType = new ContestType();
			if(!StringUtil.isEmptyNull(contestBean.getContestTypeId())){
				contestType.setContestTypeId(contestBean.getContestTypeId());
			}else{
				contestType.setContestTypeId(1);
			}
			contest.setContestType(contestType);
			
			ContestStatus contestStatus = new ContestStatus();
			contestStatus.setContestStatusId(1);
			contest.setContestStatus(contestStatus);
			
			contestHome.persist(contest);
			retContestBean.setContestId(contest.getContestId());
		}catch(Exception exception){
			exception.printStackTrace();
			throw new PTWException(PTWConstants.ERROR_CODE_DB_EXCEPTION, PTWConstants.ERROR_DESC_DB_EXCEPTION);
		}
		return retContestBean;
	}
	
	@Override
	public ContestBean getContest(ContestBean contestBean) throws PTWException{
		ContestHome contestHome = new ContestHome(this.getSession());
		ContestBean retContestBean = null;
		try{
			Contest contest = contestHome.findById(contestBean.getContestId());
			if(contest == null){
				throw new PTWException(PTWConstants.ERROR_CODE_INVALID_CONTEST, PTWConstants.ERROR_DESC_INVALID_CONTEST);
			}
			retContestBean = createContestBean(contest, false);
		}catch(PTWException exception){
			throw exception;
		}catch(Exception exception){
			exception.printStackTrace();
			throw new PTWException(PTWConstants.ERROR_CODE_DB_EXCEPTION, PTWConstants.ERROR_DESC_DB_EXCEPTION);
		}
		return retContestBean;
	}

	@Override
	public List<ContestBean> getContestList(ContestBean contestBean) throws PTWException{
		ContestHome contestHome = new ContestHome(this.getSession());
		List<ContestBean> retContestBeanList = null;
		try{
			List<Contest> contestList = contestHome.findContestByFilter(contestBean, false);
			if(contestList != null && !contestList.isEmpty()){
				retContestBeanList = new ArrayList<ContestBean>();
				for (Contest contest : contestList) {
					ContestBean dbContestBean = createContestBean(contest, false);
					retContestBeanList.add(dbContestBean);
				}
				
			}
			
		}catch(Exception exception){
			exception.printStackTrace();
			throw new PTWException(PTWConstants.ERROR_CODE_DB_EXCEPTION, PTWConstants.ERROR_DESC_DB_EXCEPTION);
		}
		return retContestBeanList;
	}

	private ContestBean createContestBean(Contest contest, boolean includeMatchDetails) {
		ContestBean retContestBean;
		retContestBean = new ContestBean();
		retContestBean.setContestId(contest.getContestId());
		
		retContestBean.setCutoffDateStr(StringUtil.convertDateTImeToString(contest.getCutoffDateTime()));
		if(!includeMatchDetails){
			retContestBean.setPublishStartDate(contest.getPublishStartDateTime());
			retContestBean.setPublishStartDateStr(StringUtil.convertDateTImeToString(contest.getPublishStartDateTime()));
			retContestBean.setPublishEndDateStr(StringUtil.convertDateTImeToString(contest.getPublishEndDateTime()));
			retContestBean.setPublishEndDate(contest.getPublishEndDateTime());
			retContestBean.setCutoffDate(contest.getCutoffDateTime());
			retContestBean.setContestName(contest.getContestName());
		}else{
			StringBuilder contestNameBuilder = new StringBuilder();
			contestNameBuilder.append(contest.getTournament().getTournamentDescription());
			contestNameBuilder.append(" : ");
			contestNameBuilder.append(contest.getContestName());
			retContestBean.setContestName(contestNameBuilder.toString());
		}
		retContestBean.setBonusPoints(contest.getBonusPoints());
		retContestBean.setContestTypeId(contest.getContestType().getContestTypeId());
		retContestBean.setContestTypeName(contest.getContestType().getContestTypeName());
		retContestBean.setTournamentId(contest.getTournamentId());
		Integer matchId = contest.getMatchId();
		if(matchId!=null){
			retContestBean.setMatchId(matchId);
			if(includeMatchDetails){
				Match match = contest.getMatch();
				retContestBean.setMatchDateStr(StringUtil.convertDateTImeToString(match.getMatchDateTime()));
				StringBuilder matchDisplayNameBuilder = new StringBuilder();
				matchDisplayNameBuilder.append(match.getTeamA().getTeam().getTeamShortName());
				matchDisplayNameBuilder.append("-");
				matchDisplayNameBuilder.append(match.getTeamB().getTeam().getTeamShortName());
				matchDisplayNameBuilder.append("-Venue: ");
				matchDisplayNameBuilder.append(match.getVenue());
				matchDisplayNameBuilder.append(" At ");
				matchDisplayNameBuilder.append(StringUtil.convertDateTImeToString(match.getMatchDateTime()));
				retContestBean.setMatchDisplayName(matchDisplayNameBuilder.toString());
			}else{
				if(includeMatchDetails){
					StringBuilder matchDisplayNameBuilder = new StringBuilder();
					matchDisplayNameBuilder.append("Contest Cuoff Time is: ");
					matchDisplayNameBuilder.append(StringUtil.convertDateTImeToString(contest.getCutoffDateTime()));
					retContestBean.setMatchDisplayName(matchDisplayNameBuilder.toString());
				}
			}
		}
		retContestBean.setContestStatusId(contest.getContestStatusId());
		return retContestBean;
	}
	
	@Override
	public List<ContestBean> getMatches(ContestBean contestBean, Boolean isOngoingContest, Boolean fullData) throws PTWException {
		List<ContestBean> retContestBeanList = null;
		ContestHome contestHome = new ContestHome(this.getSession());
		try{
			List<Contest> dbContest = contestHome.findContestByFilter(contestBean, isOngoingContest);
			if(dbContest != null && !dbContest.isEmpty()){
				retContestBeanList = new ArrayList<ContestBean>();
				for (Contest contest : dbContest) {
					ContestBean retContestBean = new ContestBean();
					retContestBean.setContestId(contest.getContestId());
					retContestBean.setContestName(contest.getContestName());
					
					if(fullData){
						retContestBean.setPublishStartDate(contest.getPublishStartDateTime());
						retContestBean.setPublishStartDateStr(StringUtil.convertDateTImeToString(contest.getPublishStartDateTime()));
						retContestBean.setPublishEndDate(contest.getPublishEndDateTime());
						retContestBean.setPublishEndDateStr(StringUtil.convertDateTImeToString(contest.getPublishEndDateTime()));
						retContestBean.setCutoffDate(contest.getCutoffDateTime());
						retContestBean.setBonusPoints(contest.getBonusPoints());
						retContestBean.setCutoffDateStr(StringUtil.convertDateTImeToString(contest.getCutoffDateTime()));
						retContestBean.setContestStatusId(contest.getContestStatusId());
						retContestBean.setContestTypeName(contest.getContestType().getContestTypeName());
						if(contest.getPushNotified() != null && contest.getPushNotified()){
							retContestBean.setPushNotified(true);
						}
					}
					retContestBean.setContestTypeId(contest.getContestType().getContestTypeId());
					retContestBean.setTournamentId(contest.getTournamentId());
					
					if(contest.getMatch() != null){
						Match match = contest.getMatch();
						retContestBean.setMatchId(match.getMatchId());
						StringBuilder matchDisplayNameBuilder = new StringBuilder();
						matchDisplayNameBuilder.append(match.getTeamA().getTeam().getTeamShortName());
						matchDisplayNameBuilder.append("-");
						matchDisplayNameBuilder.append(match.getTeamB().getTeam().getTeamShortName());
						retContestBean.setTeamNames(matchDisplayNameBuilder.toString());
						matchDisplayNameBuilder.append("-Venue: ");
						matchDisplayNameBuilder.append(match.getVenue());
						matchDisplayNameBuilder.append(" At ");
						matchDisplayNameBuilder.append(StringUtil.convertDateTImeToString(match.getMatchDateTime()));
						matchDisplayNameBuilder.append(" Answer By ");
						matchDisplayNameBuilder.append(StringUtil.convertDateTImeToString(contest.getCutoffDateTime()));
						retContestBean.setMatchDisplayName(matchDisplayNameBuilder.toString());
						retContestBean.setContestTypeName(contest.getContestType().getContestTypeName());
						retContestBean.setMatchDateStr(StringUtil.convertDateTImeToString(match.getMatchDateTime()));
					}else{
						retContestBean.setTeamNames("Bonus Contest");
						StringBuilder matchDisplayNameBuilder = new StringBuilder();
						matchDisplayNameBuilder.append("Contest Cuoff Time is: ");
						matchDisplayNameBuilder.append(StringUtil.convertDateTImeToString(contest.getCutoffDateTime()));
						retContestBean.setMatchDisplayName(matchDisplayNameBuilder.toString());
					}
					retContestBean.setCutoffDateStr(StringUtil.convertDateTImeToString(contest.getCutoffDateTime()));
					retContestBeanList.add(retContestBean);
				}
			}
		}catch(Exception exception){
			exception.printStackTrace();
			throw new PTWException(PTWConstants.ERROR_CODE_DB_EXCEPTION, PTWConstants.ERROR_DESC_DB_EXCEPTION);
		}
		return retContestBeanList;
	}
	
	@Override
	public List<QuestionBean> getMatchQuestions(MatchBean matchBean) throws PTWException{
		List<QuestionBean> retQuestionBeanList = null;
		QuestionHome questionHome = new QuestionHome(this.getSession());
		try{
			List<Question> dbQuestionList = questionHome.findQuestionForMatch(matchBean.getMatchId());
			if(dbQuestionList != null && !dbQuestionList.isEmpty()){
				retQuestionBeanList = new ArrayList<QuestionBean>();
				for (Question question : dbQuestionList) {
					QuestionBean questionBean = createQuestionBean(question);
					List<AnswerOption> dbAnswerOptionList = question.getAnswerOptions();
					if(dbAnswerOptionList != null && !dbAnswerOptionList.isEmpty()){
						List<AnswerOptionBean> answerOptionBeanList = createAnswerOptionBeanList(dbAnswerOptionList);
						questionBean.setAnswerOptionList(answerOptionBeanList);
					}
					retQuestionBeanList.add(questionBean);
				}
			}
		}catch(Exception exception){
			exception.printStackTrace();
			throw new PTWException(PTWConstants.ERROR_CODE_DB_EXCEPTION, PTWConstants.ERROR_DESC_DB_EXCEPTION);
		}
		return retQuestionBeanList;
	}

	@Override
	public void updateContest(ContestBean contestBean) throws PTWException {
		ContestHome contestHome = new ContestHome(this.getSession());
		try{
			Contest contest = contestHome.findById(contestBean.getContestId());
			if(contest == null){
				throw new PTWException(PTWConstants.ERROR_CODE_INVALID_CONTEST, PTWConstants.ERROR_DESC_INVALID_CONTEST);
			}
			if(!StringUtil.isEmptyNull(contestBean.getContestName())){
				contest.setContestName(contestBean.getContestName());
			}
			if(!StringUtil.isEmptyNull(contestBean.getBonusPoints())){
				contest.setBonusPoints(contestBean.getBonusPoints());
			}
			if(contestBean.getPublishStartDate() != null){
				contest.setPublishStartDateTime(contestBean.getPublishStartDate());
			}
			if(contestBean.getPublishEndDate() != null){
				contest.setPublishEndDateTime(contestBean.getPublishEndDate());
			}
			if(contestBean.getCutoffDate() != null){
				contest.setCutoffDateTime(contestBean.getCutoffDate());
			}
			if(!StringUtil.isEmptyNull(contestBean.getContestTypeId())){
				ContestType contestType = new ContestType();
				contestType.setContestTypeId(contestBean.getContestTypeId());
				contest.setContestType(contestType);
			}
			if(!StringUtil.isEmptyNull(contestBean.getContestStatusId())){
				ContestStatus contestStatus = new ContestStatus();
				contestStatus.setContestStatusId(contestBean.getContestStatusId());
				contest.setContestStatus(contestStatus);
			}
			contestHome.merge(contest);
			
		}catch(Exception exception){
			exception.printStackTrace();
			throw new PTWException(PTWConstants.ERROR_CODE_DB_EXCEPTION, PTWConstants.ERROR_DESC_DB_EXCEPTION);
		}
		
	}

	@Override
	public void deleteContest(ContestBean contestBean) throws PTWException {
		ContestHome contestHome = new ContestHome(this.getSession());
		try{
			Contest contest = contestHome.findById(contestBean.getContestId());
			if(contest == null){
				throw new PTWException(PTWConstants.ERROR_CODE_INVALID_CONTEST, PTWConstants.ERROR_DESC_INVALID_CONTEST);
			}
			contestHome.remove(contest);
		}catch(PTWException exception){
			throw exception;
		}catch(Exception exception){
			exception.printStackTrace();
			throw new PTWException(PTWConstants.ERROR_CODE_DB_EXCEPTION, PTWConstants.ERROR_DESC_DB_EXCEPTION);
		}
			
	}

	@Override
	public QuestionBean createQuestion(QuestionBean questionBean) throws PTWException {
		QuestionBean retQuestionBean = new QuestionBean();
		QuestionHome questionHome = new QuestionHome(this.getSession());
		try{
			int newQuestionSlNo=1;
			ContestBean contestBean = new ContestBean();
			contestBean.setContestId(questionBean.getContestId());
			List<Question> existingQuestionList = questionHome.findQuestionByFilter(contestBean);
			if(existingQuestionList!=null && !existingQuestionList.isEmpty()){
				newQuestionSlNo = existingQuestionList.size() + 1;
			}
			
			Question question = new Question();
			question.setQuestionDescription(questionBean.getQuestion());
			question.setQuestionSlNo(newQuestionSlNo);
			question.setAnswerCount(questionBean.getAnswerCount());
			
			Contest contest = new Contest();
			contest.setContestId(questionBean.getContestId());
			question.setContest(contest);
			
			AnswerType answerType = new AnswerType();
			answerType.setAnswerTypeId(questionBean.getAnswerTypeId());
			question.setAnswerType(answerType);
			
			questionHome.persist(question);
			retQuestionBean.setQuestionId(question.getQuestionId());
		}catch(Exception exception){
			exception.printStackTrace();
			throw new PTWException(PTWConstants.ERROR_CODE_DB_EXCEPTION, PTWConstants.ERROR_DESC_DB_EXCEPTION);
		}
		return retQuestionBean;
	}

	@Override
	public List<QuestionBean> getQuestion(ContestBean contestBean) throws PTWException {
		List<QuestionBean> retQuestionBeanList = null;
		QuestionHome questionHome = new QuestionHome(this.getSession());
		try{
			List<Question> dbQuestionList = questionHome.findQuestionByFilter(contestBean);
			if(dbQuestionList != null && !dbQuestionList.isEmpty()){
				retQuestionBeanList = new ArrayList<QuestionBean>();
				for (Question question : dbQuestionList) {
					QuestionBean questionBean = createQuestionBean(question);
					retQuestionBeanList.add(questionBean);
				}
			}
			
		}catch(Exception exception){
			exception.printStackTrace();
			throw new PTWException(PTWConstants.ERROR_CODE_DB_EXCEPTION, PTWConstants.ERROR_DESC_DB_EXCEPTION);
		}
		return retQuestionBeanList;
	}
	
	@Override
	public QuestionBean getQuestion(QuestionBean questionBean) throws PTWException {
		QuestionBean retQuestionBean = null;
		QuestionHome questionHome = new QuestionHome(this.getSession());
		try{
			Question dbQuestionList = questionHome.findById(questionBean.getQuestionId());
			retQuestionBean = createQuestionBean(dbQuestionList);
		}catch(Exception exception){
			exception.printStackTrace();
			throw new PTWException(PTWConstants.ERROR_CODE_DB_EXCEPTION, PTWConstants.ERROR_DESC_DB_EXCEPTION);
		}
		return retQuestionBean;
	}

	private QuestionBean createQuestionBean(Question question) {
		QuestionBean questionBean = new QuestionBean();
		questionBean.setQuestionId(question.getQuestionId());
		questionBean.setQuestion(question.getQuestionDescription());
		questionBean.setAnswerCount(question.getAnswerCount());
		questionBean.setContestId(question.getContestId());
		questionBean.setAnswerTypeId(question.getAnswerType().getAnswerTypeId());
		questionBean.setAnswerType(question.getAnswerType().getAnswerTypeName());
		return questionBean;
	}

	@Override
	public List<QuestionBean> getQuestionAndAnswer(ContestBean contestBean) throws PTWException{
		List<QuestionBean> retQuestionBeanList = null;
		QuestionHome questionHome = new QuestionHome(this.getSession());
		
		try{
			List<Question> dbQuestionList = questionHome.findQuestionByFilter(contestBean);
			if(dbQuestionList != null && !dbQuestionList.isEmpty()){
				retQuestionBeanList = new ArrayList<QuestionBean>();
				for (Question question : dbQuestionList) {
					QuestionBean questionBean = createQuestionBean(question);
					List<AnswerOptionBean> answerOptionBeanList = getAnswersForQuestion(questionBean);
					questionBean.setAnswerOptionList(answerOptionBeanList);
					retQuestionBeanList.add(questionBean);
				}
			}
			
		}catch(Exception exception){
			exception.printStackTrace();
			throw new PTWException(PTWConstants.ERROR_CODE_DB_EXCEPTION, PTWConstants.ERROR_DESC_DB_EXCEPTION);
		}
		return retQuestionBeanList;
	}
	
	@Override
	public void updateQuestion(QuestionBean questionBean) throws PTWException {
		QuestionHome questionHome = new QuestionHome(this.getSession());
		try{
			Question dbQuestion = questionHome.findById(questionBean.getQuestionId());
			if(dbQuestion == null){
				throw new PTWException(PTWConstants.ERROR_CODE_INVALID_CONTEST, PTWConstants.ERROR_DESC_INVALID_CONTEST);
			}
			dbQuestion.setQuestionDescription(questionBean.getQuestion());
			dbQuestion.setAnswerCount(questionBean.getAnswerCount());
			if(!StringUtil.isEmptyNull(questionBean.getAnswerTypeId())){
				dbQuestion.getAnswerType().setAnswerTypeId(questionBean.getAnswerTypeId());
			}
			questionHome.merge(dbQuestion);
		}catch(PTWException exception){
			throw exception;
		}catch(Exception exception){
			throw new PTWException(PTWConstants.ERROR_CODE_DB_EXCEPTION, PTWConstants.ERROR_DESC_DB_EXCEPTION);
		}
	}

	@Override
	public void deleteQuestion(QuestionBean questionBean) throws PTWException {
		QuestionHome questionHome = new QuestionHome(this.getSession());
		AnswerOptionHome anwerHome = new AnswerOptionHome(this.getSession());
		try{
			Question dbQuestion = questionHome.findById(questionBean.getQuestionId());
			if(dbQuestion == null){
				throw new PTWException(PTWConstants.ERROR_CODE_INVALID_CONTEST, PTWConstants.ERROR_DESC_INVALID_CONTEST);
			}
			List<AnswerOption> answers = anwerHome.findAnswerOptionByFilter(questionBean);
			if(answers != null && !answers.isEmpty()){
				for (AnswerOption answerOption : answers) {
					anwerHome.remove(answerOption);
				}
			}
			questionHome.remove(dbQuestion);
		}catch(PTWException exception){
			throw exception;
		}catch(Exception exception){
			exception.printStackTrace();
			throw new PTWException(PTWConstants.ERROR_CODE_DB_EXCEPTION, PTWConstants.ERROR_DESC_DB_EXCEPTION);
		}
	}
	
	@Override
	public Integer[] getQuestion(Integer[] contests) throws PTWException {
		Integer[] retQuestionBeanList = null;
		QuestionHome questionHome = new QuestionHome(this.getSession());
		try{
			List<Question> dbQuestionList = questionHome.findQuestionForContest(contests);
			if(dbQuestionList != null && !dbQuestionList.isEmpty()){
				retQuestionBeanList = new Integer[dbQuestionList.size()];
				int index = 0;
				for (Question question : dbQuestionList) {
					retQuestionBeanList[index++] = question.getQuestionId();
				}
			}
			
		}catch(Exception exception){
			exception.printStackTrace();
			throw new PTWException(PTWConstants.ERROR_CODE_DB_EXCEPTION, PTWConstants.ERROR_DESC_DB_EXCEPTION);
		}
		return retQuestionBeanList;
	}

	@Override
	public void createAnswerOptions(QuestionBean questionBean) throws PTWException {
		AnswerOptionHome answerHome = new AnswerOptionHome(this.getSession());
		try{
			Integer questionId = questionBean.getQuestionId();
			List<AnswerOptionBean> answerOptionBeanList = questionBean.getAnswerOptionList();
			for (AnswerOptionBean answerOptionBean : answerOptionBeanList) {
				AnswerOption answerOption = new AnswerOption();
				answerOption.setAnswerOptionStr(answerOptionBean.getAnswerOptionStr());
				answerOption.setCorrectAnswerFlag(false);
				answerOption.setPoints(0);
				
				Question question = new Question();
				question.setQuestionId(questionId);
				answerOption.setQuestion(question);
				answerHome.persist(answerOption);
			}
			
		}catch(Exception exception){
			exception.printStackTrace();
			throw new PTWException(PTWConstants.ERROR_CODE_DB_EXCEPTION, PTWConstants.ERROR_DESC_DB_EXCEPTION);
		}
	}

	@Override
	public void updateAnswerOption(AnswerOptionBean answerOptionBean) throws PTWException {
		AnswerOptionHome answerHome = new AnswerOptionHome(this.getSession());
		try{
			Integer answerOptionId = answerOptionBean.getAnswerOptionId();
			AnswerOption answerOption = answerHome.findById(answerOptionId);
			if(answerOption == null){
				throw new PTWException(PTWConstants.ERROR_CODE_ANSWER_NOT_FOUND, PTWConstants.ERROR_DESC_ANSWER_NOT_FOUND);
			}
			
			if(!StringUtil.isEmptyNull(answerOptionBean.getAnswerOptionStr())){
				answerOption.setAnswerOptionStr(answerOptionBean.getAnswerOptionStr());
			}
			if(answerOptionBean.getCorrectAnswerFlag() != null){
				answerOption.setCorrectAnswerFlag(answerOptionBean.getCorrectAnswerFlag());
			}
			if(!StringUtil.isEmptyNull(answerOptionBean.getPoints())){
				answerOption.setPoints(answerOptionBean.getPoints());
			}
			
			answerHome.persist(answerOption);
			
		}catch(Exception exception){
			exception.printStackTrace();
			throw new PTWException(PTWConstants.ERROR_CODE_DB_EXCEPTION, PTWConstants.ERROR_DESC_DB_EXCEPTION);
		}
		
	}

	@Override
	public List<AnswerOptionBean> getAnswersForQuestion(QuestionBean questionBean) throws PTWException {
		AnswerOptionHome answerHome = new AnswerOptionHome(this.getSession());
		List<AnswerOptionBean> answerOptionBeanList = null;
		try{
			List<AnswerOption> answerOptionList = answerHome.findAnswerOptionByFilter(questionBean);
			if(answerOptionList != null && !answerOptionList.isEmpty()){
				answerOptionBeanList = createAnswerOptionBeanList(answerOptionList);
			}
		}catch(Exception exception){
			exception.printStackTrace();
			throw new PTWException(PTWConstants.ERROR_CODE_DB_EXCEPTION, PTWConstants.ERROR_DESC_DB_EXCEPTION);
		}
		return answerOptionBeanList;
	}

	private List<AnswerOptionBean> createAnswerOptionBeanList(List<AnswerOption> answerOptionList) {
		List<AnswerOptionBean> answerOptionBeanList;
		answerOptionBeanList = new ArrayList<AnswerOptionBean>();
		for (AnswerOption answerOption : answerOptionList) {
			AnswerOptionBean answerOptionBean = new AnswerOptionBean();
			answerOptionBean.setAnswerOptionId(answerOption.getAnswerOptionId());
			answerOptionBean.setAnswerOptionStr(answerOption.getAnswerOptionStr());
			answerOptionBean.setCorrectAnswerFlag(answerOption.getCorrectAnswerFlag());
			answerOptionBean.setPoints(answerOption.getPoints());
			answerOptionBeanList.add(answerOptionBean);
		}
		return answerOptionBeanList;
	}

	@Override
	public void deleteAnswerOption(AnswerOptionBean answerOptionBean) throws PTWException {
		AnswerOptionHome answerHome = new AnswerOptionHome(this.getSession());
		try{
			AnswerOption answerOption = answerHome.findById(answerOptionBean.getAnswerOptionId());
			if(answerOption == null){
				throw new PTWException(PTWConstants.ERROR_CODE_ANSWER_NOT_FOUND, PTWConstants.ERROR_DESC_ANSWER_NOT_FOUND);
			}
			answerHome.remove(answerOption);
		}catch(PTWException exception){
			throw exception;
		}catch(Exception exception){
			exception.printStackTrace();
			throw new PTWException(PTWConstants.ERROR_CODE_DB_EXCEPTION, PTWConstants.ERROR_DESC_DB_EXCEPTION);
		}
		
		
	}

	@Override
	public void addUserAnswer(UserContestAnswer userContestAnswer) throws PTWException {
		UserAnswerHome answerHome = new UserAnswerHome(this.getSession());
		try{
			int userId = userContestAnswer.getUserId();
			List<UserAnswerBean> userAnswerBeanList = userContestAnswer.getUserAnswerList();
			Date currentDate = new Date();
			Integer[] questionIdList = getQuestionId(userAnswerBeanList);
			checkAndRemoveDuplicateAnswer(answerHome, userId, questionIdList);
			for (UserAnswerBean userAnswerBean : userAnswerBeanList) {
				Integer questionId = userAnswerBean.getQuestionId();
				List<AnswerBean> answerBeanList = userAnswerBean.getSelectedAnswerList();
				for (AnswerBean answerBean : answerBeanList) {
					UserAnswer userAnswer = new UserAnswer();
					userAnswer.setQuestionId(questionId);
					userAnswer.setUserId(userId);
					userAnswer.setAnsweredDateTime(currentDate);
					AnswerOption answerOption = new AnswerOption();
					answerOption.setAnswerOptionId(answerBean.getAnswerOptionId());
					userAnswer.setAnswerOption(answerOption);
					answerHome.persist(userAnswer);
					
				}
				
			}
			insertAnswerStats(userContestAnswer.getContestId(), userId);
		}catch(Exception exception){
			exception.printStackTrace();
			throw new PTWException(PTWConstants.ERROR_CODE_DB_EXCEPTION, PTWConstants.ERROR_DESC_DB_EXCEPTION);
		}
		
	}

	private void insertAnswerStats(Integer contestId, int userId) {
		try{
			ContestHome contestHome = new ContestHome(this.getSession());
			Contest contest = contestHome.findById(contestId);
			Integer tournamentId = contest.getTournamentId();
			UserAnswerStatsHome userAnswerStatsHome = new UserAnswerStatsHome(this.getSession());
			List<UserAnswerStats> userAnswerStats = userAnswerStatsHome.findAnswerStatsForUser(userId, tournamentId);
			if(userAnswerStats != null && !userAnswerStats.isEmpty()){
				UserAnswerStats currentStats = userAnswerStats.get(0);
				String currentContest = currentStats.getContestIds();
				String[] contestIds = currentContest.split(",");
				boolean contestAnsweredFlag = false;
				for (String answeredContestId : contestIds) {
					if(contestId.equals((Integer.parseInt(answeredContestId.trim())))){
						contestAnsweredFlag = true;
						break;
					}
				}
				if(!contestAnsweredFlag){
					currentContest = currentContest + ","+ contestId;
					currentStats.setContestIds(currentContest);
					userAnswerStatsHome.merge(currentStats);
				}
				
			}else{
				UserAnswerStats newStats = new UserAnswerStats();
				newStats.setUserId(userId);
				newStats.setTournamentId(tournamentId);
				newStats.setContestIds(String.valueOf(contestId));
				userAnswerStatsHome.persist(newStats);
			}
			
		}catch(Exception exception){
			exception.printStackTrace();
		}
		
	}

	private void checkAndRemoveDuplicateAnswer(UserAnswerHome answerHome, int userId, Integer[] questionIdList) {
		List<UserAnswer> existingAnswers = answerHome.getUserAnswer(userId, questionIdList[0]);
		if(existingAnswers!=null && !existingAnswers.isEmpty()){
			answerHome.deleteUserAnswer(userId, questionIdList);
			this.getSession().flush();
		}
	}

	private Integer[] getQuestionId(List<UserAnswerBean> userAnswerBeanList) {
		Integer[] questionIdArr = new Integer[userAnswerBeanList.size()];
		int index=0;
		for (UserAnswerBean userAnswerBean : userAnswerBeanList) {
			questionIdArr[index++]= userAnswerBean.getQuestionId();
		}
		return questionIdArr;
	}

	@Override
	public UserContestAnswer getUserAnswer(UserContestAnswer userContestBean) throws PTWException {
		QuestionHome questionHome = new QuestionHome(this.getSession());
		UserAnswerHome answerHome = new UserAnswerHome(this.getSession());
		Integer userId = userContestBean.getUserId();
		Integer contestId = userContestBean.getContestId();
		UserContestAnswer retContestAnswer = new UserContestAnswer();
		retContestAnswer.setContestId(contestId);
		retContestAnswer.setUserId(userId);
		int totalPointsWon = 0;
		try{
			ContestBean contestBean = new ContestBean();
			contestBean.setContestId(contestId);
			List<Question> dbQuestionList = questionHome.findQuestionByFilter(contestBean);
			if(dbQuestionList != null && !dbQuestionList.isEmpty()){
				List<UserAnswerBean> userAnswers = null; 
				for (Question question : dbQuestionList) {
					
					UserAnswerBean userAnswerBean = new UserAnswerBean();
					userAnswerBean.setQuestionId(question.getQuestionId());
					List<UserAnswer> dbAnswers = answerHome.getUserAnswer(userId, question.getQuestionId());
					int matchPoints = 0;
					
					if(dbAnswers != null && !dbAnswers.isEmpty()){
						if(userAnswers == null){
							userAnswers = new ArrayList<UserAnswerBean>();
						}
						List<AnswerBean> answerBeanList = new ArrayList<AnswerBean>();
						for (UserAnswer userAnswer : dbAnswers) {
							AnswerBean answerBean = new AnswerBean();
							answerBean.setAnswerId(userAnswer.getUserAnswerId());
							answerBean.setAnswerOptionId(userAnswer.getAnswerOptionId());
							answerBean.setPointsScored(userAnswer.getPointsScored());
							if(!StringUtil.isEmptyNull(userAnswer.getPointsScored())){
								matchPoints += userAnswer.getPointsScored();
							}
							answerBeanList.add(answerBean);
						}
						userAnswerBean.setSelectedAnswerList(answerBeanList);
					}
					
					userAnswerBean.setPointsScored(matchPoints);
					totalPointsWon += matchPoints;
					if(userAnswers != null){
						userAnswers.add(userAnswerBean);
					}
				}
				UserBonusPointHome userBonusPointHome = new UserBonusPointHome(this.getSession());
				List<UserBonusPoint> userBonusPoints = userBonusPointHome.findByFilter(userId, contestId);
				if(userBonusPoints != null && !userBonusPoints.isEmpty()){
					UserBonusPoint userBonusPoint = userBonusPoints.get(0);
					retContestAnswer.setBonusPoints(userBonusPoint.getPoints());
					totalPointsWon += userBonusPoint.getPoints();
				}
				retContestAnswer.setTotalPointsWon(totalPointsWon);
				retContestAnswer.setUserAnswerList(userAnswers);
			}
		}catch(Exception exception){
			exception.printStackTrace();
			throw new PTWException(PTWConstants.ERROR_CODE_DB_EXCEPTION, PTWConstants.ERROR_DESC_DB_EXCEPTION);
		}
		return retContestAnswer;
	}

	@Override
	public void removeUserAnswer(ContestBean contestBean) throws PTWException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Integer getUserBonusForContest(Integer userId, Integer[] contestIds)throws PTWException{
		UserBonusPointHome userBonusPointHome = new UserBonusPointHome(this.getSession());
		int totalPoints = 0;
		List<UserBonusPoint> userBonusPoints = userBonusPointHome.findByFilter(userId, contestIds);
		if(userBonusPoints != null && !userBonusPoints.isEmpty()) {
			for (UserBonusPoint userBonusPoint : userBonusPoints) {
				totalPoints+=userBonusPoint.getPoints();
			}
		}
		
		
		
		return totalPoints;
	}
	
	@Override
	public Map<Integer, Integer> getUserBonusForContest(Integer[] userIdList, Integer[] contestIds)throws PTWException{
		UserBonusPointHome answerHome = new UserBonusPointHome(this.getSession());
		Map<Integer, Integer> returnMap = new HashMap<Integer, Integer>();
		List<Object> objMap = answerHome.getUserScoreForQuestions(userIdList, contestIds);
		if(objMap != null && !objMap.isEmpty()){
			for (Object object : objMap) {
				@SuppressWarnings("rawtypes")
				Map userScoreMap = (Map)object;
				BigDecimal pointsDec = (BigDecimal)userScoreMap.get("POINTS");
				int points = 0;
				if(pointsDec != null){
					points = pointsDec.intValue();
				}
				Integer userId = (Integer)userScoreMap.get("USER_ID");
				returnMap.put(userId, points);
			}
		}
		
		return returnMap;
	}
	
	@Override
	public List<UserSelectedAnswerBean> getUserAnswers(UserSelectedAnswerBean userSelectedAnswerBean) throws PTWException{
		List<UserSelectedAnswerBean> retUserAnswerBeanList = null;
		UserAnswerHome userAnswerHome = new UserAnswerHome(this.getSession());
		try{
			List<UserAnswer> userAnswerList = userAnswerHome.findByAnswerOption(userSelectedAnswerBean.getSelectedAnswerOptionId());
			if(userAnswerList != null && !userAnswerList.isEmpty()){
				retUserAnswerBeanList = new ArrayList<UserSelectedAnswerBean>();
				for (UserAnswer userAnswer : userAnswerList) {
					UserSelectedAnswerBean dbUserSelectedAnswerBean = new UserSelectedAnswerBean();
					dbUserSelectedAnswerBean.setUserAnswerId(userAnswer.getUserAnswerId());
					dbUserSelectedAnswerBean.setUserId(userAnswer.getUserId());
					dbUserSelectedAnswerBean.setSelectedAnswerOptionId(userAnswer.getAnswerOptionId());
					dbUserSelectedAnswerBean.setQuestionId(userAnswer.getQuestionId());
					retUserAnswerBeanList.add(dbUserSelectedAnswerBean);
				}
			}
		}catch(Exception exception){
			exception.printStackTrace();
			throw new PTWException(PTWConstants.ERROR_CODE_DB_EXCEPTION, PTWConstants.ERROR_DESC_DB_EXCEPTION);
		}
		return retUserAnswerBeanList;
	}

	@Override
	public void updatePointsForUserAnswer(List<AnswerBean> answerBeanList) throws PTWException {
		UserAnswerHome userAnswerHome = new UserAnswerHome(this.getSession());
		try{
			for (AnswerBean answerBean : answerBeanList) {
				userAnswerHome.saveUserPoints(answerBean.getAnswerOptionId(), answerBean.getPointsScored());
			}
			
		}catch(Exception exception){
			exception.printStackTrace();
			throw new PTWException(PTWConstants.ERROR_CODE_DB_EXCEPTION, PTWConstants.ERROR_DESC_DB_EXCEPTION);
		}
	}

	@Override
	public void updateUserScoreBoard(List<UserScoreBoardBean> userScoreBoardBeanList) throws PTWException {
		UserScoreBoardHome userScoreBoardHome = new UserScoreBoardHome(this.getSession());
		UserTournamentRegistrationHome userTournamentHome = new UserTournamentRegistrationHome(this.getSession());
		try{
			for (UserScoreBoardBean userScoreBoardBean : userScoreBoardBeanList) {
				int tournamentId = userScoreBoardBean.getTournamentId();
				int userId = userScoreBoardBean.getUserId();
				Integer user[] = new Integer[1];
				user[0] = userId;
				List<UserScoreBoard> userScoreBoardList = userScoreBoardHome.findByFilter(tournamentId, user , null, false);
				if(userScoreBoardList != null && !userScoreBoardList.isEmpty()){
					UserScoreBoard userScoreBoard = userScoreBoardList.get(0);
					Integer currentPoints = userScoreBoard.getTotalPoints();
					Integer newPoints = userScoreBoardBean.getPointsScored();
					if(currentPoints != null){
						newPoints += currentPoints;
					}
					userScoreBoard.setTotalPoints(newPoints);	
					userScoreBoardHome.merge(userScoreBoard);
				}else{
					UserScoreBoard userScoreBoard = new UserScoreBoard();
					userScoreBoard.setTournamentId(tournamentId);
					Users newUser = new Users();
					newUser.setUserId(userId);
					userScoreBoard.setUser(newUser);
					userScoreBoard.setTotalPoints(userScoreBoardBean.getPointsScored());
					userScoreBoardHome.persist(userScoreBoard);
					List<UserTournamentRegistration> userRegistrations = userTournamentHome.findByFilter(userScoreBoardBean.getTournamentId(), userScoreBoardBean.getUserId());
					if(userRegistrations == null || userRegistrations.isEmpty()){
						UserTournamentRegistration userTournamentRegistration = new UserTournamentRegistration();
						userTournamentRegistration.setUserId(userId);
						Tournament tournament = new Tournament();
						tournament.setTournamentId(tournamentId);
						userTournamentRegistration.setTournament(tournament);
						userTournamentHome.persist(userTournamentRegistration);
					}
					
				}
			}
		}catch(Exception exception){
			exception.printStackTrace();
			throw new PTWException(PTWConstants.ERROR_CODE_DB_EXCEPTION, PTWConstants.ERROR_DESC_DB_EXCEPTION);
		}
	}
	
	@Override
	public List<Integer> getTournamentScores(TournamentBean tournamentBean)throws PTWException{
		UserScoreBoardHome userScoreBoardHome = new UserScoreBoardHome(this.getSession());
		try{
			List<Integer> scoresList = userScoreBoardHome.getPointsForTournament(tournamentBean.getTournamentId());
			return scoresList;
		}catch(Exception exception){
			exception.printStackTrace();
			throw new PTWException(PTWConstants.ERROR_CODE_DB_EXCEPTION, PTWConstants.ERROR_DESC_DB_EXCEPTION);
		}
	}
	
	
	@Override
	public void updateUserRanks(Set<Integer> reOrderedList, Integer tournamentId) throws PTWException {
		UserScoreBoardHome userScoreBoardHome = new UserScoreBoardHome(this.getSession());
		try{
			int rank = 1;
			for (Integer orderedRank : reOrderedList) {
				int updatedRows = userScoreBoardHome.updateRanks(orderedRank, rank, tournamentId);
				rank += updatedRows;
			}
			
		}catch(Exception e){
			e.printStackTrace();
			throw new PTWException(PTWConstants.ERROR_CODE_DB_EXCEPTION, PTWConstants.ERROR_DESC_DB_EXCEPTION);
		}
		
	}

	@Override
	public List<AnswerTypeBean> getAnswerTypes() throws PTWException {
		AnswerOptionHome answerOptionHome = new AnswerOptionHome(getSession());
		List<AnswerTypeBean> answerTypeBeanList = new ArrayList<AnswerTypeBean>();
		
		try{
			List<AnswerType> answerTypeList = answerOptionHome.getAnswerType();
			if(answerTypeList != null && !answerTypeList.isEmpty()){
				for (AnswerType answerType : answerTypeList) {
					AnswerTypeBean answerTypeBean = new AnswerTypeBean();
					answerTypeBean.setAnswerTypeId(answerType.getAnswerTypeId());
					answerTypeBean.setAnswerTypeName(answerType.getAnswerTypeName());
					answerTypeBeanList.add(answerTypeBean);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new PTWException(PTWConstants.ERROR_CODE_DB_EXCEPTION, PTWConstants.ERROR_DESC_DB_EXCEPTION);
		}
		return answerTypeBeanList;
	}
	
	@Override
	public List<LeaderBoardBean> getLeaderBoard(LeaderBoardBeanList leaderBoardBeanList) throws PTWException {
		UserScoreBoardHome userScoreBoardHome = new UserScoreBoardHome(getSession());
		List<LeaderBoardBean> leaderBoardList = null; //new ArrayList<LeaderBoardBean>();
		Integer[] users = null;
		Integer rankSize = null;
		Integer tournamentId = null;
		try{
			UserGroupHome userGroupHome = new UserGroupHome(getSession());
			if(!StringUtil.isEmptyNull(leaderBoardBeanList.getGroupId())){
				UserGroup userGroup = userGroupHome.findById(leaderBoardBeanList.getGroupId());
				tournamentId = userGroup.getTournamentId();
				UserGroupMappingHome userGroupHomeMapping = new UserGroupMappingHome(getSession());
				List<UserGroupMapping> userGroups = userGroupHomeMapping.findUserGroup(null, leaderBoardBeanList.getGroupId(), null, false);
				if(userGroups != null && !userGroups.isEmpty()){
					users = new Integer[userGroups.size()];
					int index = 0;
					for (UserGroupMapping userGroupMapping : userGroups) {
						users[index++] = userGroupMapping.getUserGroupMappingKey().getUserId();
					}
				}
			}/*else{
				rankSize = 100;
			}*/
			List<UserScoreBoard> userScoreBoardList = userScoreBoardHome.findByFilter(tournamentId, users, rankSize, true);
			if(userScoreBoardList != null && !userScoreBoardList.isEmpty()){
				leaderBoardList = new ArrayList<LeaderBoardBean>();
				for (UserScoreBoard userScoreBoard : userScoreBoardList) {
					LeaderBoardBean leaderBoard = new LeaderBoardBean();
					leaderBoard.setUserId(userScoreBoard.getUser().getUserId());
					leaderBoard.setUserName(userScoreBoard.getUser().getUserName());
					leaderBoard.setTeamName(userScoreBoard.getUser().getTeamName());
					leaderBoard.setTotalPoints(userScoreBoard.getTotalPoints());
					leaderBoard.setRank(userScoreBoard.getRank());
					leaderBoardList.add(leaderBoard);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new PTWException(PTWConstants.ERROR_CODE_DB_EXCEPTION, PTWConstants.ERROR_DESC_DB_EXCEPTION);
		}
		return leaderBoardList;
	}

	@Override
	public void createPrizeContest(PrizeContestBean prizeContestBean) throws PTWException{
		PrizeContestHome prizeContestHome = new PrizeContestHome(this.getSession());
		try{
			PrizeContest prizeContest = new PrizeContest();
			prizeContest.setPrizeContestName(prizeContestBean.getPrizeContestName());
			prizeContest.setStartDate(prizeContestBean.getStartDate());
			prizeContest.setEndDate(prizeContestBean.getEndDate());
			prizeContest.setTournamentId(prizeContestBean.getTournamentId());
			prizeContest.setWinnerSize(prizeContestBean.getWinnerSize());
			prizeContest.setGroupId(prizeContestBean.getGroupId());
			prizeContest.setProcessedFlag(false);
			prizeContestHome.persist(prizeContest);
		}catch(Exception e){
			e.printStackTrace();
			throw new PTWException(PTWConstants.ERROR_CODE_DB_EXCEPTION, PTWConstants.ERROR_DESC_DB_EXCEPTION);
		}
		
	}
	
	@Override
	public void updatePrizeContest(PrizeContestBean prizeContestBean) throws PTWException{
		PrizeContestHome prizeContestHome = new PrizeContestHome(this.getSession());
		try{
			PrizeContest dbContest = prizeContestHome.findById(prizeContestBean.getPrizeContestId());
			if(dbContest == null){
				throw new PTWException(PTWConstants.ERROR_CODE_PRIZE_CONTEST_NOT_FOUND, PTWConstants.ERROR_DESC_PRIZE_CONTEST_NOT_FOUND);
			}
			dbContest.setPrizeContestName(prizeContestBean.getPrizeContestName());
			dbContest.setStartDate(prizeContestBean.getStartDate());
			dbContest.setEndDate(prizeContestBean.getEndDate());
			dbContest.setTournamentId(prizeContestBean.getTournamentId());
			dbContest.setWinnerSize(prizeContestBean.getWinnerSize());
			dbContest.setGroupId(prizeContestBean.getGroupId());
			prizeContestHome.merge(dbContest);
		}catch(PTWException e){
			throw e;
		}catch(Exception e){
			e.printStackTrace();
			throw new PTWException(PTWConstants.ERROR_CODE_DB_EXCEPTION, PTWConstants.ERROR_DESC_DB_EXCEPTION);
		}
		
	}
	
	@Override
	public List<PrizeContestBean> getPrizeContest(PrizeContestBean prizeContestBean) throws PTWException{
		PrizeContestHome prizeContestHome = new PrizeContestHome(this.getSession());
		List<PrizeContestBean> contests = null;
		try{
			List<PrizeContest> dbContests = prizeContestHome.findPrizeContestByFilter(prizeContestBean, false, false);
			if(dbContests != null && !dbContests.isEmpty()){
				contests = new ArrayList<PrizeContestBean>();
				for (PrizeContest prizeContest : dbContests) {
					PrizeContestBean retPrizeContest = new PrizeContestBean();
					retPrizeContest.setPrizeContestId(prizeContest.getPrizeContestId());
					retPrizeContest.setPrizeContestName(prizeContest.getPrizeContestName());
					retPrizeContest.setTournamentId(prizeContest.getTournamentId());
					retPrizeContest.setGroupId(prizeContest.getGroupId());
					retPrizeContest.setStartDate(prizeContest.getStartDate());
					retPrizeContest.setStartDateStr(StringUtil.convertDateToString(prizeContest.getStartDate()));
					retPrizeContest.setEndDate(prizeContest.getEndDate());
					retPrizeContest.setEndDateStr(StringUtil.convertDateToString(prizeContest.getEndDate()));
					retPrizeContest.setWinnerSize(prizeContest.getWinnerSize());
					retPrizeContest.setArchieved(prizeContest.getProcessedFlag());
					contests.add(retPrizeContest);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new PTWException(PTWConstants.ERROR_CODE_DB_EXCEPTION, PTWConstants.ERROR_DESC_DB_EXCEPTION);
		}
		return contests;
	}
	
	@Override
	public void deletePrizeContest(PrizeContestBean prizeContestBean) throws PTWException{
		PrizeContestHome prizeContestHome = new PrizeContestHome(this.getSession());
		try{
			PrizeContest dbContest = prizeContestHome.findById(prizeContestBean.getPrizeContestId());
			if(dbContest == null){
				throw new PTWException(PTWConstants.ERROR_CODE_PRIZE_CONTEST_NOT_FOUND, PTWConstants.ERROR_DESC_PRIZE_CONTEST_NOT_FOUND);
			}
			prizeContestHome.remove(dbContest);
		}catch(PTWException e){
			throw e;
		}catch(Exception e){
			e.printStackTrace();
			throw new PTWException(PTWConstants.ERROR_CODE_DB_EXCEPTION, PTWConstants.ERROR_DESC_DB_EXCEPTION);
		}
	}

	@Override
	public List<UserGroupBean> getUserGroups(UserGroupBean userGroupBean) throws PTWException{
		UserGroupHome userGroupHome = new UserGroupHome(this.getSession());
		List<UserGroupBean> retUserGroups = null;
		try{
			List<UserGroup> userGroups = userGroupHome.findByFilter(userGroupBean);
			if(userGroups != null && !userGroups.isEmpty()){
				retUserGroups = new ArrayList<UserGroupBean>();
				for (UserGroup userGroup : userGroups) {
					UserGroupBean retUserGroupBean = new UserGroupBean();
					retUserGroupBean.setGroupId(userGroup.getUserGroupId());
					retUserGroupBean.setGroupName(userGroup.getUserGroupName());
					retUserGroupBean.setTournamentId(userGroup.getTournamentId());
					retUserGroupBean.setOwnerId(userGroup.getOwnerUserId());
					retUserGroupBean.setGroupCode(userGroup.getUserGroupCode());
					retUserGroupBean.setPrizeGroupFlag(userGroup.getPrizeIncludedFlag());
					retUserGroups.add(retUserGroupBean);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new PTWException(PTWConstants.ERROR_CODE_DB_EXCEPTION, PTWConstants.ERROR_DESC_DB_EXCEPTION);
		}
		return retUserGroups;
	}

	@Override
	public List<ContestBean> getRunningContests() throws PTWException {
		ContestHome contestHome = new ContestHome(this.getSession());
		List<ContestBean> retContestBeanList = null;
		try{
			List<Contest> contestList = contestHome.findRunningContest(true);
			if(contestList != null && !contestList.isEmpty()){
				retContestBeanList = new ArrayList<ContestBean>();
				for (Contest contest : contestList) {
					ContestBean dbContestBean = createContestBean(contest, false);
					retContestBeanList.add(dbContestBean);
				}
				
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new PTWException(PTWConstants.ERROR_CODE_DB_EXCEPTION, PTWConstants.ERROR_DESC_DB_EXCEPTION);
		}
		return retContestBeanList;
	}

	@Override
	public void createCodeMojoRewardRecord(CodeMojoRewardBean rewardBean) {
		CodeMojoRewardHome rewardHome = new CodeMojoRewardHome(this.getSession());
		CodeMojoReward reward = new CodeMojoReward();
		reward.setHashVal(rewardBean.getHash());
		reward.setCommunicationEmailId(rewardBean.getCommunication_channel_email());
		reward.setCommunicationPhone(rewardBean.getCommunication_channel_phone());
		reward.setCustomerId(reward.getCustomerId());
		Coupon coupon = rewardBean.getCoupon();
		if(coupon != null){
			reward.setBrandName(coupon.getBrand_name());
			reward.setBrandUrl(coupon.getBrand_url());
			reward.setCouponCode(coupon.getCoupon_code());
			reward.setFinePrint(coupon.getFineprint());
			reward.setLogo(coupon.getLogo());
			reward.setRedeemProcess(coupon.getRedemption_process());
			reward.setSupport(coupon.getSupport());
			reward.setTitle(coupon.getTitle());
			reward.setTransactionId(coupon.getTxn_id());
			reward.setValFormatter(coupon.getValue_formatted());
			reward.setValidity(coupon.getValidity());
			reward.setValNumeric(coupon.getValue_numeric());
			reward.setValRation(coupon.getValue_ratio());
			reward.setValStamp(coupon.getValidity_stamp());
			rewardHome.persist(reward);
		}
		
	}

	@Override
	public void updateFanClubStandings(TournamentFanClubBean tournamentFanClubBean) {
		UserGroupHome userGroupHome = new UserGroupHome(this.getSession());
		UserGroup group = userGroupHome.findById(tournamentFanClubBean.getGroupId());
		group.setGroupPoints(tournamentFanClubBean.getClubPoints());
		group.setGroupRank(tournamentFanClubBean.getClubPosition());
		userGroupHome.merge(group);
	}
	
	@Override
	public void createTOC(TournamentTAndCBean tocBean) throws PTWException{
		TermsAndConditionHome tocHome = new TermsAndConditionHome(getSession());
		try{
			int newMax = 0;
			if(StringUtil.isEmptyNull(tocBean.getGroupId())){
				Integer currentMax = tocHome.getMaxTOCGroupId();
				if(currentMax == null){
					currentMax = 0;
				}
				newMax = currentMax+1;
				
			}else{
				newMax = 1;
			}
			for(String tocText: tocBean.getTermsText()){
				TermsCondition toc = new TermsCondition();
				toc.setTocGroupId(newMax);
				toc.setTocText(tocText);
				tocHome.persist(toc);
			}
			ContestHome contestHome = new ContestHome(this.getSession());
			Contest contest = contestHome.findById(tocBean.getContestId());
			contest.setTocGroupId(newMax);
			contestHome.merge(contest);
		}catch(Exception exception){
			exception.printStackTrace();
			throw new PTWException(PTWConstants.ERROR_CODE_DB_EXCEPTION, PTWConstants.ERROR_DESC_DB_EXCEPTION);
		}
		
	}

	@Override
	public List<String> getContestTerms(ContestBean contestBean) throws PTWException{
		List<String> terms = null;
		TermsAndConditionHome termsHome = new TermsAndConditionHome(getSession());
		try{
			List<TermsCondition> termsList = termsHome.getTOCForContest(contestBean.getContestId());
			if(termsList != null && !termsList.isEmpty()){
				terms = new ArrayList<String>();
				for (TermsCondition termsAndCondition : termsList) {
					terms.add(termsAndCondition.getTocText());
				}
			}
		}catch(Exception exception){
			exception.printStackTrace();
			throw new PTWException(PTWConstants.ERROR_CODE_DB_EXCEPTION, PTWConstants.ERROR_DESC_DB_EXCEPTION);
		}
		return terms;
	}
	
	@Override
	public Integer[] getQuestionsForDates(Date startDate, Date endDate, Integer tournamentId)throws PTWException{
		ContestHome contestHome = new ContestHome(this.getSession());
		QuestionHome questionHome = new QuestionHome(this.getSession());
		Integer[] questionIdList = null;
		try{
			List<Contest> contestList = contestHome.getContestBetweenDates(startDate, endDate, tournamentId);
			Integer[] contestIdList = null;
			if(contestList != null && !contestList.isEmpty()){
				contestIdList = new Integer[contestList.size()];
				int index = 0;
				for (Contest contest : contestList) {
					contestIdList[index++] = contest.getContestId();
				}
				
				if(contestIdList != null){
					List<Question> questionList = questionHome.findQuestionForContest(contestIdList);
					if(questionList != null && !questionList.isEmpty()){
						questionIdList = new Integer[questionList.size()];
						int questIndex = 0;
						for (Question question : questionList) {
							questionIdList[questIndex++] = question.getQuestionId();
						}
					}
				}
				
			}
		}catch(Exception exception){
			exception.printStackTrace();
			throw new PTWException(PTWConstants.ERROR_CODE_DB_EXCEPTION, PTWConstants.ERROR_DESC_DB_EXCEPTION);
		}
		return questionIdList;
		
	}
	
	@Override
	public Integer[] getContestsForDates(Date startDate, Date endDate, Integer tournamentId)throws PTWException{
		ContestHome contestHome = new ContestHome(this.getSession());
		Integer[] contestIdList = null;
		try{
			List<Contest> contestList = contestHome.getContestBetweenDates(startDate, endDate, tournamentId);
			
			if(contestList != null && !contestList.isEmpty()){
				contestIdList = new Integer[contestList.size()];
				int index = 0;
				for (Contest contest : contestList) {
					contestIdList[index++] = contest.getContestId();
				}
			}
		}catch(Exception exception){
			exception.printStackTrace();
			throw new PTWException(PTWConstants.ERROR_CODE_DB_EXCEPTION, PTWConstants.ERROR_DESC_DB_EXCEPTION);
		}
		return contestIdList;
		
	}
	
	@Override
	public Integer[] getUsersForQuestions(Integer[] questionIds)throws PTWException{
		UserAnswerHome userAnswerHome = new UserAnswerHome(this.getSession());
		Integer[] foundUserIds= null;
		List<Integer> userIds = null;
		try{
			userIds = userAnswerHome.getUserAnswer(questionIds);
			if(userIds != null && !userIds.isEmpty()) {
				foundUserIds = new Integer[userIds.size()];
				int index =0;
				for (Integer userId : userIds) {
					foundUserIds[index++] = userId;
				}
			}
			
		}catch(Exception exception){
			exception.printStackTrace();
			throw new PTWException(PTWConstants.ERROR_CODE_DB_EXCEPTION, PTWConstants.ERROR_DESC_DB_EXCEPTION);
		}
		return foundUserIds;
		
	}
	
	@Override
	public List<PrizeContestBean> getUnprocessedPrizeContest(PrizeContestBean prizeContestBean) throws PTWException{
		PrizeContestHome prizeContestHome = new PrizeContestHome(this.getSession());
		List<PrizeContestBean> contests = null;
		try{
			List<PrizeContest> dbContests = prizeContestHome.findPrizeContestByFilter(prizeContestBean, true, false);
			if(dbContests != null && !dbContests.isEmpty()){
				contests = new ArrayList<PrizeContestBean>();
				for (PrizeContest prizeContest : dbContests) {
					PrizeContestBean retPrizeContest = new PrizeContestBean();
					retPrizeContest.setPrizeContestId(prizeContest.getPrizeContestId());
					retPrizeContest.setPrizeContestName(prizeContest.getPrizeContestName());
					retPrizeContest.setTournamentId(prizeContest.getTournamentId());
					retPrizeContest.setGroupId(prizeContest.getGroupId());
					retPrizeContest.setStartDate(prizeContest.getStartDate());
					retPrizeContest.setStartDateStr(StringUtil.convertDateToString(prizeContest.getStartDate()));
					retPrizeContest.setEndDate(prizeContest.getEndDate());
					retPrizeContest.setEndDateStr(StringUtil.convertDateToString(prizeContest.getEndDate()));
					retPrizeContest.setWinnerSize(prizeContest.getWinnerSize());
					retPrizeContest.setArchieved(prizeContest.getProcessedFlag());
					contests.add(retPrizeContest);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new PTWException(PTWConstants.ERROR_CODE_DB_EXCEPTION, PTWConstants.ERROR_DESC_DB_EXCEPTION);
		}
		return contests;
	}
	@Override
	public void addPrizeWinners(List<PrizeContestWinnerBean> winnerBeanList) throws PTWException{
		PrizeContestWinnersHome winnerHome = new PrizeContestWinnersHome(this.getSession());
		try{
			for (PrizeContestWinnerBean prizeContestWinnerBean : winnerBeanList) {
				PrizeContestWinners prizeContestWinner = new PrizeContestWinners();
				Users user = new Users();
				user.setUserId(prizeContestWinnerBean.getUserId());
				prizeContestWinner.setUser(user);
				
				PrizeContest prizeContest = new PrizeContest();
				prizeContest.setPrizeContestId(prizeContestWinnerBean.getPrizeContestId());
				prizeContestWinner.setPrizeContest(prizeContest);
				
				prizeContestWinner.setPointsScored(prizeContestWinnerBean.getPointsScored());
				prizeContestWinner.setRank(prizeContestWinnerBean.getRank());
				winnerHome.persist(prizeContestWinner);
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new PTWException(PTWConstants.ERROR_CODE_DB_EXCEPTION, PTWConstants.ERROR_DESC_DB_EXCEPTION);
		}
	}

	@Override
	public List<PrizeContestWinnerBean> getPrizeWinners(PrizeContestBean prizeContestBean) throws PTWException {
		PrizeContestHome prizeContestHome = new PrizeContestHome(this.getSession());
		PrizeContestWinnersHome prizeContestWinnerHome = new PrizeContestWinnersHome(this.getSession());
		List<PrizeContestWinnerBean> winners = null;
		try{
			List<PrizeContest> prizeContestList = prizeContestHome.findPrizeContestByFilter(prizeContestBean, false, false);
			if(prizeContestList != null && prizeContestList.size() == 1){
				PrizeContest dbPrizeContest = prizeContestList.get(0);
				prizeContestBean.setPrizeContestId(dbPrizeContest.getPrizeContestId());
				List<PrizeContestWinners> dbWinners = prizeContestWinnerHome.findPrizeContestByFilter(prizeContestBean);
				
				if(dbWinners != null && !dbWinners.isEmpty()){
					List<PrizeContestWinnerBean> lastPositions = new ArrayList<PrizeContestWinnerBean>();
					winners = new ArrayList<PrizeContestWinnerBean>();
					for (PrizeContestWinners prizeContestWinners : dbWinners) {
						PrizeContestWinnerBean winnerBean = new PrizeContestWinnerBean();
						winnerBean.setPrizeContestWinnerId(prizeContestWinners.getPrizeContestWinnersId());
						winnerBean.setUserId(prizeContestWinners.getUser().getUserId());
						winnerBean.setUserName(prizeContestWinners.getUser().getUserName());
						winnerBean.setTeamName(prizeContestWinners.getUser().getTeamName());
						winnerBean.setPointsScored(prizeContestWinners.getPointsScored());
						winnerBean.setRank(prizeContestWinners.getRank());
						if(prizeContestWinners.getPointsScored() > 0) {
							winners.add(winnerBean);
						}else {
							lastPositions.add(winnerBean);
						}
					}
					if(!lastPositions.isEmpty()) {
						winners.addAll(lastPositions);
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new PTWException(PTWConstants.ERROR_CODE_DB_EXCEPTION, PTWConstants.ERROR_DESC_DB_EXCEPTION);
		}
		return winners;
	}
	
	@Override
	public void removePrizeWinners(PrizeContestBean prizeContestBean)throws PTWException{
		PrizeContestWinnersHome winnerHome = new PrizeContestWinnersHome(this.getSession());
		try{
			winnerHome.remove(prizeContestBean);
		}catch(Exception e){
			e.printStackTrace();
			throw new PTWException(PTWConstants.ERROR_CODE_DB_EXCEPTION, PTWConstants.ERROR_DESC_DB_EXCEPTION);
		}
	}
	
	@Override
	public void markPushNotified(Integer contestId)throws PTWException{
		ContestHome contestHome = new ContestHome(this.getSession());
		try{
			contestHome.updatePushStatus(contestId);
		}catch(Exception e){
			e.printStackTrace();
			throw new PTWException(PTWConstants.ERROR_CODE_DB_EXCEPTION, PTWConstants.ERROR_DESC_DB_EXCEPTION);
		}
	}

	@Override
	public List<ContestBean> getActiveContests()throws PTWException {
		
		ContestHome contestHome = new ContestHome(this.getSession());
		List<ContestBean> retContestBeanList = null;
		try{
			List<Contest> contestList = contestHome.findRunningContest(false);
			if(contestList != null && !contestList.isEmpty()){
				retContestBeanList = new ArrayList<ContestBean>();
				for (Contest contest : contestList) {
					ContestBean dbContestBean = createContestBean(contest, true);
					retContestBeanList.add(dbContestBean);
				}
				
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new PTWException(PTWConstants.ERROR_CODE_DB_EXCEPTION, PTWConstants.ERROR_DESC_DB_EXCEPTION);
		}
		return retContestBeanList;
	
	}

	@Override
	public List<PrizeContestBean> getUserPrizeContests(Integer userId) {
		PrizeContestHome contestHome = new PrizeContestHome(this.getSession());
		List<PrizeContestBean> contestBeanList = new ArrayList<PrizeContestBean>();
		List<PrizeContest> prizeContestList = contestHome.findPrizeContestByFilter(new PrizeContestBean(), false, true);
		if(prizeContestList != null && !prizeContestList.isEmpty()) {
			for (PrizeContest prizeContest : prizeContestList) {
				PrizeContestBean contestBean = new PrizeContestBean();
				contestBean.setPrizeContestId(prizeContest.getPrizeContestId());
				contestBean.setPrizeContestName(prizeContest.getPrizeContestName());
				contestBean.setStartDateStr(StringUtil.convertDateToString(prizeContest.getStartDate()));
				contestBean.setEndDateStr(StringUtil.convertDateToString(prizeContest.getEndDate()));
				contestBeanList.add(contestBean);
			}
		}
		
		UserGroupMappingHome userGroupMappingHome = new UserGroupMappingHome(this.getSession());
		UserGroupBean queryGroupBean = new UserGroupBean();
		queryGroupBean.setUserId(userId);
		List<UserGroupMapping> userGroupMappingList = userGroupMappingHome.findUserGroup(userId, null, null, true);
		if(userGroupMappingList != null && !userGroupMappingList.isEmpty()) {
			for (UserGroupMapping userGroupMapping : userGroupMappingList) {
				UserGroup userGroup = userGroupMapping.getUserGroupMappingKey().getUserGroup();
				PrizeContestBean queryPrizeContestBean = new PrizeContestBean();
				queryPrizeContestBean.setGroupId(userGroup.getUserGroupId());
				List<PrizeContest> userPrizeContestList = contestHome.findPrizeContestByFilter(queryPrizeContestBean, true, false);
				if(userPrizeContestList != null && !userPrizeContestList.isEmpty()) {
					for (PrizeContest prizeContest : userPrizeContestList) {
						PrizeContestBean contestBean = new PrizeContestBean();
						contestBean.setPrizeContestId(prizeContest.getPrizeContestId());
						contestBean.setPrizeContestName(prizeContest.getPrizeContestName());
						contestBean.setStartDateStr(StringUtil.convertDateToString(prizeContest.getStartDate()));
						contestBean.setEndDateStr(StringUtil.convertDateToString(prizeContest.getEndDate()));
						contestBeanList.add(contestBean);
					}
					
				}
			}
		}
		return contestBeanList;
	}
}
