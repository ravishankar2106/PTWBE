package com.bind.ptw.be.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.bind.ptw.be.dao.ContestDao;
import com.bind.ptw.be.dto.AnswerBean;
import com.bind.ptw.be.dto.AnswerOptionBean;
import com.bind.ptw.be.dto.AnswerTypeBean;
import com.bind.ptw.be.dto.ContestBean;
import com.bind.ptw.be.dto.LeaderBoardBean;
import com.bind.ptw.be.dto.LeaderBoardBeanList;
import com.bind.ptw.be.dto.MatchBean;
import com.bind.ptw.be.dto.QuestionBean;
import com.bind.ptw.be.dto.TournamentBean;
import com.bind.ptw.be.dto.TournamentTeamBean;
import com.bind.ptw.be.dto.UserAnswerBean;
import com.bind.ptw.be.dto.UserContestAnswer;
import com.bind.ptw.be.dto.UserScoreBoardBean;
import com.bind.ptw.be.dto.UserSelectedAnswerBean;
import com.bind.ptw.be.entities.AnswerOption;
import com.bind.ptw.be.entities.AnswerOptionHome;
import com.bind.ptw.be.entities.AnswerType;
import com.bind.ptw.be.entities.Contest;
import com.bind.ptw.be.entities.ContestHome;
import com.bind.ptw.be.entities.ContestStatus;
import com.bind.ptw.be.entities.ContestType;
import com.bind.ptw.be.entities.Match;
import com.bind.ptw.be.entities.MatchHome;
import com.bind.ptw.be.entities.MatchStatus;
import com.bind.ptw.be.entities.Question;
import com.bind.ptw.be.entities.QuestionHome;
import com.bind.ptw.be.entities.Tournament;
import com.bind.ptw.be.entities.TournamentHome;
import com.bind.ptw.be.entities.TournamentTeam;
import com.bind.ptw.be.entities.TournamentTeamHome;
import com.bind.ptw.be.entities.UserAnswer;
import com.bind.ptw.be.entities.UserAnswerHome;
import com.bind.ptw.be.entities.UserScoreBoard;
import com.bind.ptw.be.entities.UserScoreBoardHome;
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
			if(teamA == null || (!tournament.getTournamentId().equals(teamA.getTournament().getTournamentId()))){
				throw new PTWException(PTWConstants.ERROR_CODE_TEAM_ID_INVALID, PTWConstants.ERROR_DESC_FIELD_INVALID + "Team A");
			}
			
			TournamentTeam teamB = getTournamentTeam(matchBean.getTeamB().getTournamentTeamId());
			if(teamB == null || (!tournament.getTournamentId().equals(teamB.getTournament().getTournamentId()))){
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
					retMatchBean.setTournamentId(match.getTournament().getTournamentId());
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
			
			Tournament tournament = new Tournament();
			tournament.setTournamentId(contestBean.getTournamentId());
			contest.setTournament(tournament);
			
			if(!StringUtil.isEmptyNull(contestBean.getMatchId())){
				MatchHome matchHome = new MatchHome(this.getSession());
				Match match = matchHome.findById(contestBean.getMatchId());
				if(!contestBean.getTournamentId().equals(match.getTournament().getTournamentId())){
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
			retContestBean = createContestBean(contest);
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
					ContestBean dbContestBean = createContestBean(contest);
					retContestBeanList.add(dbContestBean);
				}
				
			}
			
		}catch(Exception exception){
			exception.printStackTrace();
			throw new PTWException(PTWConstants.ERROR_CODE_DB_EXCEPTION, PTWConstants.ERROR_DESC_DB_EXCEPTION);
		}
		return retContestBeanList;
	}

	private ContestBean createContestBean(Contest contest) {
		ContestBean retContestBean;
		retContestBean = new ContestBean();
		retContestBean.setContestId(contest.getContestId());
		retContestBean.setContestName(contest.getContestName());
		retContestBean.setPublishStartDate(contest.getPublishStartDateTime());
		retContestBean.setPublishStartDateStr(StringUtil.convertDateTImeToString(contest.getPublishStartDateTime()));
		retContestBean.setPublishEndDate(contest.getPublishEndDateTime());
		retContestBean.setPublishEndDateStr(StringUtil.convertDateTImeToString(contest.getPublishEndDateTime()));
		retContestBean.setCutoffDate(contest.getCutoffDateTime());
		retContestBean.setCutoffDateStr(StringUtil.convertDateTImeToString(contest.getCutoffDateTime()));
		retContestBean.setBonusPoints(contest.getBonusPoints());
		retContestBean.setContestTypeId(contest.getContestType().getContestTypeId());
		retContestBean.setContestTypeName(contest.getContestType().getContestTypeName());
		retContestBean.setTournamentId(contest.getTournament().getTournamentId());
		retContestBean.setContestStatusId(contest.getContestStatus().getContestStatusId());
		return retContestBean;
	}
	
	@Override
	public List<ContestBean> getMatches(ContestBean contestBean, Boolean isOngoingContest) throws PTWException {
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
					retContestBean.setPublishStartDate(contest.getPublishStartDateTime());
					retContestBean.setPublishStartDateStr(StringUtil.convertDateTImeToString(contest.getPublishStartDateTime()));
					retContestBean.setPublishEndDate(contest.getPublishEndDateTime());
					retContestBean.setPublishEndDateStr(StringUtil.convertDateTImeToString(contest.getPublishEndDateTime()));
					retContestBean.setCutoffDate(contest.getCutoffDateTime());
					retContestBean.setCutoffDateStr(StringUtil.convertDateTImeToString(contest.getCutoffDateTime()));
					retContestBean.setBonusPoints(contest.getBonusPoints());
					retContestBean.setContestTypeId(contest.getContestType().getContestTypeId());
					retContestBean.setContestTypeName(contest.getContestType().getContestTypeName());
					retContestBean.setTournamentId(contest.getTournament().getTournamentId());
					retContestBean.setContestStatusId(contest.getContestStatus().getContestStatusId());
					if(contest.getMatch() != null){
						retContestBean.setMatchId(contest.getMatch().getMatchId());
						retContestBean.setContestTypeName(contest.getContestType().getContestTypeName());
					}
					
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
		questionBean.setContestId(question.getContest().getContestId());
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
			anwerHome.deleteAnswerForQuestion(questionBean);
			questionHome.remove(dbQuestion);
		}catch(PTWException exception){
			throw exception;
		}catch(Exception exception){
			throw new PTWException(PTWConstants.ERROR_CODE_DB_EXCEPTION, PTWConstants.ERROR_DESC_DB_EXCEPTION);
		}
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
		}catch(Exception exception){
			exception.printStackTrace();
			throw new PTWException(PTWConstants.ERROR_CODE_DB_EXCEPTION, PTWConstants.ERROR_DESC_DB_EXCEPTION);
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
	public UserContestAnswer getUserAnswer(ContestBean contestBean) throws PTWException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeUserAnswer(ContestBean contestBean) throws PTWException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<UserSelectedAnswerBean> getUserAnswers(UserSelectedAnswerBean userSelectedAnswerBean) throws PTWException{
		List<UserSelectedAnswerBean> retUserAnswerBeanList = null;
		UserAnswerHome userAnswerHome = new UserAnswerHome(this.getSession());
		try{
			List<UserAnswer> userAnswerList = userAnswerHome.findByAnswerOption(userSelectedAnswerBean.getSelectedAnswerOptionId());
			if(userAnswerList != null && !userAnswerList.isEmpty()){
				System.out.println("Users with correct answer found");
				retUserAnswerBeanList = new ArrayList<UserSelectedAnswerBean>();
				for (UserAnswer userAnswer : userAnswerList) {
					UserSelectedAnswerBean dbUserSelectedAnswerBean = new UserSelectedAnswerBean();
					dbUserSelectedAnswerBean.setUserAnswerId(userAnswer.getUserAnswerId());
					dbUserSelectedAnswerBean.setUserId(userAnswer.getUserId());
					dbUserSelectedAnswerBean.setSelectedAnswerOptionId(userAnswer.getAnswerOption().getAnswerOptionId());
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
		try{
			for (UserScoreBoardBean userScoreBoardBean : userScoreBoardBeanList) {
				Integer user[] = new Integer[1];
				user[0] = userScoreBoardBean.getUserId();
				List<UserScoreBoard> userScoreBoardList = userScoreBoardHome.findByFilter(userScoreBoardBean.getTournamentId(), user , null);
				if(userScoreBoardList != null && !userScoreBoardList.isEmpty()){
					UserScoreBoard userScoreBoard = userScoreBoardList.get(0);
					userScoreBoard.setTotalPoints(userScoreBoardBean.getPointsScored());	
					userScoreBoardHome.merge(userScoreBoard);
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
	public void updateUserRanks(Set<Integer> reOrderedList) throws PTWException {
		UserScoreBoardHome userScoreBoardHome = new UserScoreBoardHome(this.getSession());
		try{
			int rank = 1;
			for (Integer orderedRank : reOrderedList) {
				int updatedRows = userScoreBoardHome.updateRanks(orderedRank, rank);
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
		Integer tournamentId = leaderBoardBeanList.getTournamentId();
		Integer[] users = null;
		Integer rankSize = null;
		try{
			if(!StringUtil.isEmptyNull(leaderBoardBeanList.getGroupId())){
				
			}else{
				rankSize = 100;
			}
			List<UserScoreBoard> userScoreBoardList = userScoreBoardHome.findByFilter(tournamentId, users, rankSize);
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
}
