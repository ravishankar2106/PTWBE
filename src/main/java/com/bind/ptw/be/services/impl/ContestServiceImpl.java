package com.bind.ptw.be.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bind.ptw.be.dao.ContestDao;
import com.bind.ptw.be.dao.TournamentDao;
import com.bind.ptw.be.dao.UserDao;
import com.bind.ptw.be.dto.AnswerBean;
import com.bind.ptw.be.dto.AnswerOptionBean;
import com.bind.ptw.be.dto.AnswerPulseBean;
import com.bind.ptw.be.dto.AnswerPulseBeanList;
import com.bind.ptw.be.dto.AnswerTypeBean;
import com.bind.ptw.be.dto.AnswerTypeBeanList;
import com.bind.ptw.be.dto.BaseBean;
import com.bind.ptw.be.dto.CodeMojoRewardBean;
import com.bind.ptw.be.dto.ContestBean;
import com.bind.ptw.be.dto.ContestBeanList;
import com.bind.ptw.be.dto.Coupon;
import com.bind.ptw.be.dto.LeaderBoardBean;
import com.bind.ptw.be.dto.LeaderBoardBeanList;
import com.bind.ptw.be.dto.MatchBean;
import com.bind.ptw.be.dto.MatchBeanList;
import com.bind.ptw.be.dto.OneSignalUserRegistrationBean;
import com.bind.ptw.be.dto.PossibleAnswerBean;
import com.bind.ptw.be.dto.PrizeContestBean;
import com.bind.ptw.be.dto.PrizeContestBeanList;
import com.bind.ptw.be.dto.PrizeContestWinnerBean;
import com.bind.ptw.be.dto.PushBean;
import com.bind.ptw.be.dto.QuestionBean;
import com.bind.ptw.be.dto.QuestionBeanList;
import com.bind.ptw.be.dto.TeamPlayerBean;
import com.bind.ptw.be.dto.TournamentBean;
import com.bind.ptw.be.dto.TournamentFanClubBean;
import com.bind.ptw.be.dto.TournamentTAndCBean;
import com.bind.ptw.be.dto.TournamentTeamBean;
import com.bind.ptw.be.dto.UserBean;
import com.bind.ptw.be.dto.UserContestAnswer;
import com.bind.ptw.be.dto.UserGroupBean;
import com.bind.ptw.be.dto.UserGroupBeanList;
import com.bind.ptw.be.dto.UserScoreBoardBean;
import com.bind.ptw.be.dto.UserSelectedAnswerBean;
import com.bind.ptw.be.services.ContestService;
import com.bind.ptw.be.services.util.ContestBeanValidator;
import com.bind.ptw.be.services.util.TournamentBeanValidator;
import com.bind.ptw.be.services.util.UserBeanValidator;
import com.bind.ptw.be.util.EmailContent;
import com.bind.ptw.be.util.EmailUtil;
import com.bind.ptw.be.util.OneSignalUtil;
import com.bind.ptw.be.util.PTWConstants;
import com.bind.ptw.be.util.PTWException;
import com.bind.ptw.be.util.StringUtil;

@Service("contestService")
@PropertySource("file:${prop.path}/app.properties")
@Transactional
public class ContestServiceImpl implements ContestService{

	@Autowired
	ContestDao contestDao;
	
	@Autowired
	TournamentDao tournamentDao;
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	private Environment env;
	
	@Autowired
	HibernateTemplate hibernateTemplate;
	
	@Override
	public MatchBean createMatch(MatchBean matchBean) {
		MatchBean retBean = null;
		try{
			TournamentBeanValidator.validateRequest(matchBean);
			ContestBeanValidator.validateCreateMatch(matchBean, tournamentDao, contestDao);
			retBean = contestDao.createMatch(matchBean);
		}catch(PTWException exception){
			retBean = new MatchBean();
			retBean.setResultCode(exception.getCode());
			retBean.setResultDescription(exception.getDescription());
		}
		return retBean;
	}

	@Override
	public MatchBeanList getMatches(MatchBean matchBean) {
		MatchBeanList matchBeanList = new MatchBeanList();
		try{
			List<MatchBean> matches = contestDao.getMatches(matchBean);
			matchBeanList.setMatchBeanList(matches);
			matchBeanList.setTournamentId(matchBean.getTournamentId());
		}catch(PTWException exception){
			matchBeanList.setResultCode(exception.getCode());
			matchBeanList.setResultDescription(exception.getDescription());
		}
		
		return matchBeanList;
	}

	@Override
	public BaseBean updateMatch(MatchBean matchBean) {
		BaseBean retBean = new BaseBean();
		try{
			TournamentBeanValidator.validateRequest(matchBean);
			ContestBeanValidator.validateMatchId(matchBean.getMatchId());
			ContestBeanValidator.validateStatusId(matchBean.getMatchStatusId());
			contestDao.updateMatchStatus(matchBean);
		}catch(PTWException exception){
			retBean.setResultCode(exception.getCode());
			retBean.setResultDescription(exception.getDescription());
		}
		return retBean;
	}

	@Override
	public BaseBean deleteMatch(MatchBean matchBean) {
		BaseBean retBean = new BaseBean();
		try{
			TournamentBeanValidator.validateRequest(matchBean);
			ContestBeanValidator.validateMatchId(matchBean.getMatchId());
			contestDao.deleteMatch(matchBean);
		}catch(PTWException exception){
			retBean.setResultCode(exception.getCode());
			retBean.setResultDescription(exception.getDescription());
		}
		return retBean;
	}
	
	@Override
	public QuestionBeanList getMatchQuestions(MatchBean matchBean){
		QuestionBeanList retQuestionBeanList = new QuestionBeanList();
		try{
			TournamentBeanValidator.validateRequest(matchBean);
			ContestBeanValidator.validateMatchId(matchBean.getMatchId());
			List<QuestionBean> questionBeanList = contestDao.getMatchQuestions(matchBean);
			retQuestionBeanList.setQuestionBeanList(questionBeanList);
		}catch(PTWException exception){
			retQuestionBeanList.setResultCode(exception.getCode());
			retQuestionBeanList.setResultDescription(exception.getDescription());
		}
		return retQuestionBeanList;
	}

	@Override
	public ContestBean createContest(ContestBean contestBean) {
		ContestBean retBean = null;
		try{
			TournamentBeanValidator.validateRequest(contestBean);
			ContestBeanValidator.validateCreateContest(contestBean, tournamentDao, contestDao);
			TournamentBean queryBean = new TournamentBean();
			queryBean.setTournamentId(contestBean.getTournamentId());
			List<TournamentBean> tournamentBeanList = tournamentDao.getTournament(queryBean, false);
			if(tournamentBeanList == null || tournamentBeanList.isEmpty()){
				throw new PTWException(PTWConstants.ERROR_CODE_TOURNAMENT_ID_NOT_FOUND, PTWConstants.ERROR_DESC_TOURNAMENT_ID_NOT_FOUND);
			}
			if(StringUtil.isEmptyNull(contestBean.getTocId())){
				TournamentBean tournamentBean = tournamentBeanList.get(0);
				contestBean.setTocId(tournamentBean.getTocId());
			}
			retBean = contestDao.createContest(contestBean);
		}catch(PTWException exception){
			retBean = new ContestBean();
			retBean.setResultCode(exception.getCode());
			retBean.setResultDescription(exception.getDescription());
		}
		return retBean;
	}

	@Override
	public ContestBeanList getContests(ContestBean contestBean) {
		ContestBeanList resContestBeanList = new ContestBeanList();
		try{
			TournamentBeanValidator.validateRequest(contestBean);
			TournamentBeanValidator.validateTournamentId(contestBean.getTournamentId());
			List<ContestBean> contestBeanList = contestDao.getMatches(contestBean, false, true);
			resContestBeanList.setContestBeanList(contestBeanList);
		}catch(PTWException exception){
			resContestBeanList.setResultCode(exception.getCode());
			resContestBeanList.setResultDescription(exception.getDescription());
		}
		return resContestBeanList;
	}
	
	@Override
	public ContestBeanList getOngoingContests(ContestBean contestBean) {
		ContestBeanList retContestBeanList = new ContestBeanList();
		try{
			TournamentBeanValidator.validateRequest(contestBean);
			//TournamentBeanValidator.validateTournamentId(contestBean.getTournamentId());
			/*if(StringUtil.isEmptyNull(contestBean.getContestTypeId())){
				contestBean.setContestTypeId(1);
			}*/
			List<ContestBean> contestBeanList = contestDao.getMatches(contestBean, true, false);
			if(contestBeanList != null && !contestBeanList.isEmpty()){
				if(!StringUtil.isEmptyNull(contestBean.getTournamentId())){
					for (ContestBean retContestBean : contestBeanList) {
						List<QuestionBean> questionList = contestDao.getQuestion(retContestBean);
						if(questionList != null && !questionList.isEmpty()){
							for (QuestionBean questionBean : questionList) {
								List<AnswerOptionBean> answers = contestDao.getAnswersForQuestion(questionBean);
								questionBean.setAnswerOptionList(answers);
							}
						}
						retContestBean.setQuestionList(questionList);
					}
				}
				retContestBeanList.setContestBeanList(contestBeanList);
			}
			
		}catch(PTWException exception){
			retContestBeanList.setResultCode(exception.getCode());
			retContestBeanList.setResultDescription(exception.getDescription());
		}
		return retContestBeanList;
	}

	@Override
	public ContestBean getContestQAndA(Integer contestId){
		ContestBean retContestBean = null;
		try{
			ContestBeanValidator.validateContestId(contestId);
			ContestBean contestBean = new ContestBean();
			contestBean.setContestId(contestId);
			
			List<ContestBean> contestBeanList = contestDao.getMatches(contestBean, true, true);
			if(contestBeanList != null && !contestBeanList.isEmpty()){
				retContestBean = contestBeanList.get(0);
				List<QuestionBean> questionList = contestDao.getQuestion(retContestBean);
				if(questionList != null && !questionList.isEmpty()){
					for (QuestionBean questionBean : questionList) {
						List<AnswerOptionBean> answers = contestDao.getAnswersForQuestion(questionBean);
						questionBean.setAnswerOptionList(answers);
					}
				}
				retContestBean.setQuestionList(questionList);
			}
		}catch(PTWException exception){
			retContestBean = new ContestBean();
			retContestBean.setResultCode(exception.getCode());
			retContestBean.setResultDescription(exception.getDescription());
		}
		return retContestBean;
	}
	
	@Override
	public BaseBean updateContest(ContestBean contestBean) {
		BaseBean retBean = new BaseBean();
		try{
			TournamentBeanValidator.validateRequest(contestBean);
			ContestBeanValidator.validateUpdateContest(contestBean, tournamentDao, contestDao);
			contestDao.updateContest(contestBean);
		}catch(PTWException exception){
			retBean.setResultCode(exception.getCode());
			retBean.setResultDescription(exception.getDescription());
		}
		return retBean;
	}

	@Override
	public BaseBean updateContestStatus(ContestBean contestBean) {
		BaseBean retBean = new BaseBean();
		try{
			TournamentBeanValidator.validateRequest(contestBean);
			ContestBean statusContestBean = ContestBeanValidator.validateUpdateContestStatus(contestBean, tournamentDao, contestDao);
			contestDao.updateContest(statusContestBean);
		}catch(PTWException exception){
			retBean.setResultCode(exception.getCode());
			retBean.setResultDescription(exception.getDescription());
		}
		return retBean;
	}

	@Override
	public BaseBean deleteContest(ContestBean contestBean) {
		BaseBean retBean = new BaseBean();
		try{
			TournamentBeanValidator.validateRequest(contestBean);
			ContestBeanValidator.validateContestId(contestBean.getContestId());
			contestDao.deleteContest(contestBean);
		}catch(PTWException exception){
			retBean.setResultCode(exception.getCode());
			retBean.setResultDescription(exception.getDescription());
		}
		return retBean;
	}
	
	@Override
	public ContestBeanList getProcessingContests() {
		ContestBeanList retContestBeanList = new ContestBeanList();
		try{
			List<ContestBean> contestBeanList = contestDao.getRunningContests();
			retContestBeanList.setContestBeanList(contestBeanList);
		}catch(PTWException exception){
			retContestBeanList.setResultCode(exception.getCode());
			retContestBeanList.setResultDescription(exception.getDescription());
		}
		return retContestBeanList;
	}
	
	@Override
	public BaseBean processContests(ContestBean contestBean){
		BaseBean retBean = new BaseBean();
		try{
			if(contestBean == null){
				contestBean = new ContestBean();
			}
			if(!StringUtil.isEmptyNull(contestBean.getContestId()) && !(StringUtil.isEmptyNull(contestBean.getContestStatusId()))){
				throw new PTWException(PTWConstants.ERROR_CODE_INVALID_PROCESS_REQUEST, PTWConstants.ERROR_DESC_FIELD_INVALID + "Status Id");
			}
			contestBean.setContestStatusId(2);
			List<ContestBean> contestBeanList = contestDao.getContestList(contestBean);
			if(contestBeanList == null || contestBeanList.isEmpty()){
				throw new PTWException(PTWConstants.ERROR_CODE_CONTEST_PROCESS_NO_RECORD, PTWConstants.ERROR_DESC_CONTEST_PROCESS_NO_RECORD);
			}
			for (ContestBean dbContestBean : contestBeanList) {
				processResults(dbContestBean);
			}
		}catch(PTWException exception){
			retBean.setResultCode(exception.getCode());
			retBean.setResultDescription(exception.getDescription());
		}
		return retBean;
	}

	private void processResults(ContestBean dbContestBean) throws PTWException {
		int contestId = dbContestBean.getContestId();
		int tournamentId = dbContestBean.getTournamentId();
		int contestBonusPoint = dbContestBean.getBonusPoints();
		int totalMaxPoints = 0;
		Map<Integer,Integer> userPointMap = new HashMap<Integer, Integer>();
		Map<Integer,Integer> selectedAnswerPointMap = new HashMap<Integer, Integer>();
		
		List<QuestionBean> contestQuestionsList = contestDao.getQuestion(dbContestBean);
		if(contestQuestionsList != null && !contestQuestionsList.isEmpty()){
			for (QuestionBean questionBean : contestQuestionsList) {
				List<AnswerOptionBean> answerOptions = contestDao.getAnswersForQuestion(questionBean);
				for (AnswerOptionBean answerOptionBean : answerOptions) {
					int pointsScored = answerOptionBean.getPoints();
					if(StringUtil.isEmptyNull(answerOptionBean.getPoints())){
						continue;
					}
					if(answerOptionBean.getCorrectAnswerFlag()){
						totalMaxPoints += pointsScored;
					}
					
					Integer correctAnswerOptionId = answerOptionBean.getAnswerOptionId();
					selectedAnswerPointMap.put(correctAnswerOptionId, pointsScored);
					
					UserSelectedAnswerBean reqSelectedAnswer = new UserSelectedAnswerBean();
					reqSelectedAnswer.setSelectedAnswerOptionId(correctAnswerOptionId);
					List<UserSelectedAnswerBean> userSelectedAnswerList = contestDao.getUserAnswers(reqSelectedAnswer);
					if(userSelectedAnswerList != null && !userSelectedAnswerList.isEmpty()){
						for (UserSelectedAnswerBean userSelectedAnswerBean : userSelectedAnswerList) {
							Integer userId = userSelectedAnswerBean.getUserId();
							Integer newPoints = pointsScored;
							if(userPointMap.containsKey(userId)){
								Integer currentPoints = userPointMap.get(userId);
								newPoints = newPoints + currentPoints;
							}
							userPointMap.put(userId, newPoints);
						}
					}
				}
			}
			
			List<Integer> bonusWinners = null;
			if(!userPointMap.isEmpty()){
				if(!StringUtil.isEmptyNull(contestBonusPoint)){
					bonusWinners = new ArrayList<Integer>();
					for (Map.Entry<Integer, Integer> userIdPointMap : userPointMap.entrySet()) {
						Integer userId = userIdPointMap.getKey();
						Integer pointsWon = userIdPointMap.getValue();
						if(pointsWon == totalMaxPoints){
							bonusWinners.add(userId);
							pointsWon = pointsWon+contestBonusPoint;
							userPointMap.put(userId, pointsWon);
						}
					}
				}
			}
			
			updateUserAnswers(selectedAnswerPointMap);
			if(!StringUtil.isEmptyNull(contestBonusPoint )){
				updateBonusPoints(contestId, contestBonusPoint, bonusWinners);
			}
			updateScoreBoard(tournamentId, userPointMap);
			processRanking(tournamentId);
			processFanGroupRanking(tournamentId);
			markContestAsCompleted(contestId);
			
		}
		
		
	}

	@Override
	public void processFanGroupRanking(int tournamentId) {
		try{
			TournamentBean tournamentBean = new TournamentBean();
			tournamentBean.setTournamentId(tournamentId);
			List<TournamentFanClubBean> userGroupBean = userDao.getTournamentSystemGroups(tournamentBean);
			Set<Long> pointSet = new TreeSet<Long>().descendingSet(); 
			if(userGroupBean != null && !userGroupBean.isEmpty()){
				for (TournamentFanClubBean tournamentFanClubBean : userGroupBean) {
					tournamentFanClubBean.setTournamentId(tournamentId);
					Long totalPoints = userDao.getGroupPoints(tournamentFanClubBean);
					if(totalPoints == null){
						totalPoints = 0l;
					}
					pointSet.add(totalPoints);
					tournamentFanClubBean.setClubPoints(totalPoints);
				}
				
				int rank = 0;
				for (Long points : pointSet) {
					rank++;
					for (TournamentFanClubBean tournamentFanClubBean : userGroupBean) {
						if(tournamentFanClubBean.getClubPoints().equals(points)){
							tournamentFanClubBean.setClubPosition(rank);
						}
					}
				}
				for (TournamentFanClubBean tournamentFanClubBean : userGroupBean) {
					contestDao.updateFanClubStandings(tournamentFanClubBean);
				}
			}
		}catch(PTWException e){
			e.printStackTrace();
		}
	}

	private void processRanking(int tournamentId)throws PTWException {
		TournamentBean tournamentBean = new TournamentBean();
		tournamentBean.setTournamentId(tournamentId);
		List<Integer> pointsList = contestDao.getTournamentScores(tournamentBean);
		Set<Integer> newPointRanking = new TreeSet<Integer>().descendingSet();
		for (Integer points : pointsList) {
			newPointRanking.add(points);
		}
		contestDao.updateUserRanks(newPointRanking, tournamentId);
	}

	private void updateScoreBoard(Integer tournamentId, Map<Integer, Integer> userPointMap) throws PTWException{
		List<UserScoreBoardBean> userScoreBoardBeanList = new ArrayList<UserScoreBoardBean>();
		for (Map.Entry<Integer, Integer> userPointScored : userPointMap.entrySet()){
			Integer userId = userPointScored.getKey();
			Integer pointsScored = userPointScored.getValue();
			UserScoreBoardBean userScoreBoardBean = new UserScoreBoardBean();
			userScoreBoardBean.setUserId(userId);
			userScoreBoardBean.setTournamentId(tournamentId);
			userScoreBoardBean.setPointsScored(pointsScored);
			userScoreBoardBeanList.add(userScoreBoardBean);
		}
		contestDao.updateUserScoreBoard(userScoreBoardBeanList);
	}

	private void updateBonusPoints(int contestId, int contestBonusPoint, List<Integer> bonusWinners)
			throws PTWException {
		ContestBean contestBean = new ContestBean();
		contestBean.setContestId(contestId);
		contestBean.setBonusPoints(contestBonusPoint);
		userDao.updateBonusPoints(contestBean, bonusWinners);
	}

	private void markContestAsCompleted(int contestId) throws PTWException {
		ContestBean newContestStatus = new ContestBean();
		newContestStatus.setContestId(contestId);
		newContestStatus.setContestStatusId(4);
		contestDao.updateContest(newContestStatus);
	}

	private void updateUserAnswers(Map<Integer, Integer> selectedAnswerPointMap) throws PTWException{
		if(selectedAnswerPointMap!= null && !selectedAnswerPointMap.isEmpty()){
			List<AnswerBean> answerBeanList = new ArrayList<AnswerBean>();
			for (Map.Entry<Integer, Integer> answerOptionPointMap : selectedAnswerPointMap.entrySet()) {
				AnswerBean answerBean = new AnswerBean();
				answerBean.setAnswerOptionId(answerOptionPointMap.getKey());
				answerBean.setPointsScored(answerOptionPointMap.getValue());
				answerBeanList.add(answerBean);
			}
			contestDao.updatePointsForUserAnswer(answerBeanList);
		}
		
	}

	@Override
	public QuestionBean createQuestion(QuestionBean questionBean) {
		QuestionBean retBean = null;
		try{
			TournamentBeanValidator.validateRequest(questionBean);
			ContestBeanValidator.validateCreateQuestion(questionBean, tournamentDao, contestDao);
			retBean = contestDao.createQuestion(questionBean);
			
			if(questionBean.getAnswerTypeId() == 1){
				Integer contestId = questionBean.getContestId();
				ContestBean contestBean = new ContestBean();
				contestBean.setContestId(contestId);
				List<ContestBean> contestList = contestDao.getMatches(contestBean, false, true);
				if(contestList != null && !contestList.isEmpty()){
					ContestBean dbContestBean = contestList.get(0);
					Integer matchId = dbContestBean.getMatchId();
					MatchBean matchBean = new MatchBean();
					matchBean.setMatchId(matchId);
					List<TournamentTeamBean> tournamentTeams = contestDao.getMatchTeams(matchBean);
					if(tournamentTeams != null && !tournamentTeams.isEmpty()){
						List<AnswerOptionBean> answerOptionBeanList = new ArrayList<AnswerOptionBean>();
						for (TournamentTeamBean tournamentTeam : tournamentTeams) {
							AnswerOptionBean answerOptionBean = new AnswerOptionBean();
							answerOptionBean.setAnswerOptionStr(tournamentTeam.getTeamName());
							answerOptionBeanList.add(answerOptionBean);
						}
						QuestionBean answerQuestionBean = new QuestionBean();
						answerQuestionBean.setQuestionId(retBean.getQuestionId());
						answerQuestionBean.setAnswerOptionList(answerOptionBeanList);
						contestDao.createAnswerOptions(answerQuestionBean);
					}
				}
				
			}
			
		}catch(PTWException exception){
			retBean = new QuestionBean();
			retBean.setResultCode(exception.getCode());
			retBean.setResultDescription(exception.getDescription());
		}
		return retBean;
	}

	@Override
	public BaseBean updateQuestion(QuestionBean questionBean) {
		BaseBean retBean = new BaseBean();
		try{
			TournamentBeanValidator.validateRequest(questionBean);
			ContestBeanValidator.validateUpdateQuestion(questionBean, tournamentDao, contestDao);
			contestDao.updateQuestion(questionBean);
		}catch(PTWException exception){
			retBean.setResultCode(exception.getCode());
			retBean.setResultDescription(exception.getDescription());
		}
		return retBean;
	}

	@Override
	public ContestBean getContestQuestion(ContestBean contestBean) {
		ContestBean retBean = new ContestBean();
		try{
			TournamentBeanValidator.validateRequest(contestBean);
			ContestBeanValidator.validateContestId(contestBean.getContestId());
			List<QuestionBean> questionList = contestDao.getQuestion(contestBean);
			retBean.setContestId(contestBean.getContestId());
			retBean.setQuestionList(questionList);
		}catch(PTWException exception){
			retBean.setResultCode(exception.getCode());
			retBean.setResultDescription(exception.getDescription());
		}
		return retBean;
	}

	@Override
	public BaseBean deleteQuestion(QuestionBean questionBean) {
		BaseBean retBean = new BaseBean();
		try{
			TournamentBeanValidator.validateRequest(questionBean);
			ContestBeanValidator.validateQuestionId(questionBean.getQuestionId());
			/*List<QuestionBean> questionList = contestDao.getQuestion(contestBean);
			if(questionList != null && !questionList.isEmpty()){
				for (QuestionBean questionBean : questionList) {*/
					contestDao.deleteQuestion(questionBean);
				/*}
			}*/
			
		}catch(PTWException exception){
			retBean.setResultCode(exception.getCode());
			retBean.setResultDescription(exception.getDescription());
		}
		return retBean;
	}

	@Override
	public BaseBean createAnswerOptions(QuestionBean questionBean) {
		BaseBean retBean = new BaseBean();
		try{
			TournamentBeanValidator.validateRequest(questionBean);
			ContestBeanValidator.validateQuestionId(questionBean.getQuestionId());
			ContestBeanValidator.validateAnswerOption(questionBean.getAnswerOptionList());
			contestDao.createAnswerOptions(questionBean);
		}catch(PTWException exception){
			retBean.setResultCode(exception.getCode());
			retBean.setResultDescription(exception.getDescription());
		}
		return retBean;
	}

	@Override
	public BaseBean updateAnswerOption(AnswerOptionBean answerOptionBean) {
		BaseBean retBean = new BaseBean();
		try{
			if(answerOptionBean == null){
				throw new PTWException(PTWConstants.ERROR_CODE_INVALID_REQUEST, PTWConstants.ERROR_DESC_INVALID_REQUEST);
			}
			ContestBeanValidator.validateUpdateAnswerOption(answerOptionBean);
			contestDao.updateAnswerOption(answerOptionBean);
		}catch(PTWException exception){
			retBean.setResultCode(exception.getCode());
			retBean.setResultDescription(exception.getDescription());
		}
		return retBean;
	}
	
	

	@Override
	public QuestionBean getAnswersForQuestion(QuestionBean questionBean) {
		QuestionBean retBean = new QuestionBean();
		try{
			TournamentBeanValidator.validateRequest(questionBean);
			ContestBeanValidator.validateQuestionId(questionBean.getQuestionId());
			List<AnswerOptionBean> answers = contestDao.getAnswersForQuestion(questionBean);
			retBean.setQuestionId(questionBean.getQuestionId());
			retBean.setAnswerOptionList(answers);
		}catch(PTWException exception){
			retBean.setResultCode(exception.getCode());
			retBean.setResultDescription(exception.getDescription());
		}
		return retBean;
	}

	@Override
	public BaseBean deleteAnswerOption(AnswerOptionBean answerOptionBean) {
		BaseBean retBean = new BaseBean();
		try{
			if(answerOptionBean == null){
				throw new PTWException(PTWConstants.ERROR_CODE_INVALID_REQUEST, PTWConstants.ERROR_DESC_INVALID_REQUEST);
			}
			ContestBeanValidator.validateAnswerOptionId(answerOptionBean.getAnswerOptionId());
			contestDao.deleteAnswerOption(answerOptionBean);
		}catch(PTWException exception){
			retBean.setResultCode(exception.getCode());
			retBean.setResultDescription(exception.getDescription());
		}
		return retBean;
	}

	@Override
	public BaseBean submitUserAnswer(UserContestAnswer userContestAsnwer){
		BaseBean retBean = new BaseBean();
		try{
			ContestBeanValidator.validateUserAnswer(userContestAsnwer, contestDao);
			contestDao.addUserAnswer(userContestAsnwer);
		}catch(PTWException exception){
			retBean.setResultCode(exception.getCode());
			retBean.setResultDescription(exception.getDescription());
		}
		
		return retBean;
	}

	@Override
	public AnswerTypeBeanList getAnswerTypes() {
		AnswerTypeBeanList anwerTypeBeanList = new AnswerTypeBeanList();
		try{
			List<AnswerTypeBean> answerTypes = contestDao.getAnswerTypes();
			anwerTypeBeanList.setAnswerTypes(answerTypes);
		}catch(PTWException exception){
			anwerTypeBeanList.setResultCode(exception.getCode());
			anwerTypeBeanList.setResultDescription(exception.getDescription());
		}
		
		return anwerTypeBeanList;
	}

	@Override
	public PossibleAnswerBean getPossibleAnswers(QuestionBean questionBean) {
		PossibleAnswerBean possibleAnswerBean = new PossibleAnswerBean();
		try{
			TournamentBeanValidator.validateRequest(questionBean);
			ContestBeanValidator.validateQuestionId(questionBean.getQuestionId());
			QuestionBean question = contestDao.getQuestion(questionBean);
			if(question == null){
				throw new PTWException(PTWConstants.ERROR_CODE_QUESTION_NOT_FOUND, PTWConstants.ERROR_DESC_QUESTION_NOT_FOUND);
			}
			int answerTypeId = question.getAnswerTypeId();
			if(answerTypeId == 2 || answerTypeId == 4){
				ContestBean queryContest = new ContestBean();
				queryContest.setContestId(question.getContestId());
				ContestBean contestBean = contestDao.getContest(queryContest);
				if(answerTypeId == 2){
					List<String> playersList = getMatchPlayers(possibleAnswerBean, contestBean);
					possibleAnswerBean.setPossibleAnswerList(playersList);
				}else{
					List<String> teamListStr = new ArrayList<String>();
					TournamentBean tournamentBean = new TournamentBean();
					tournamentBean.setTournamentId(contestBean.getTournamentId());;
					List<TournamentTeamBean> teamList = tournamentDao.getTeamsForTournament(tournamentBean);
					if(teamList != null && !teamList.isEmpty()){
						for (TournamentTeamBean tournamentTeam : teamList) {
							teamListStr.add(tournamentTeam.getTeamName());
						}
					}
					possibleAnswerBean.setPossibleAnswerList(teamListStr);
				}
			}
		}catch(PTWException exception){
			possibleAnswerBean.setResultCode(exception.getCode());
			possibleAnswerBean.setResultDescription(exception.getDescription());
		}
		return possibleAnswerBean;
	}

	private List<String> getMatchPlayers(PossibleAnswerBean possibleAnswerBean, ContestBean contestBean) throws PTWException {
		List<String> namesList = new ArrayList<String>();
		MatchBean queryMatch = new MatchBean();
		queryMatch.setMatchId(contestBean.getMatchId());
		List<MatchBean> matchBeanList = contestDao.getMatches(queryMatch);
		if(matchBeanList != null && !matchBeanList.isEmpty()){
			MatchBean matchBean = matchBeanList.get(0);
			Integer teamAId = matchBean.getTeamA().getTournamentTeamId();
			Integer teamBId = matchBean.getTeamB().getTournamentTeamId();
			appendPlayers(namesList, teamAId);
			appendPlayers(namesList, teamBId);
		}
		return namesList;
	}

	private void appendPlayers(List<String> namesList, Integer teamId) throws PTWException {
		
		TournamentTeamBean tournTeamBean = new TournamentTeamBean();
		tournTeamBean.setTournamentTeamId(teamId);
		List<TeamPlayerBean> teamPlayers = tournamentDao.getPlayersForTournamentTeam(tournTeamBean);
		if(teamPlayers != null && !teamPlayers.isEmpty()){
			for (TeamPlayerBean teamPlayer : teamPlayers) {
				namesList.add(teamPlayer.getPlayerBean().getFirstName() + " " + teamPlayer.getPlayerBean().getLastName());
			}
			
		}
	}

	@Override
	public LeaderBoardBeanList getLeaderBoard(LeaderBoardBeanList leaderBoardRequest) {
		LeaderBoardBeanList retLeaderBoard = new LeaderBoardBeanList();
		try{
			TournamentBeanValidator.validateRequest(leaderBoardRequest);
			TournamentBeanValidator.validateTournamentId(leaderBoardRequest.getTournamentId());
			List<LeaderBoardBean> leadersList = contestDao.getLeaderBoard(leaderBoardRequest);
			retLeaderBoard.setLeaders(leadersList);
		}catch(PTWException exception){
			retLeaderBoard.setResultCode(exception.getCode());
			retLeaderBoard.setResultDescription(exception.getDescription());
		}
		return retLeaderBoard;
	}

	@Override
	public UserContestAnswer getUserAnswer(UserContestAnswer userContestBean) {
		UserContestAnswer userContestAnswer = null;
		try{
			TournamentBeanValidator.validateRequest(userContestBean);
			ContestBeanValidator.validateContestId(userContestBean.getContestId());
			userContestAnswer = contestDao.getUserAnswer(userContestBean);
		}catch(PTWException exception){
			userContestAnswer = new UserContestAnswer();
			userContestAnswer.setResultCode(exception.getCode());
			userContestAnswer.setResultDescription(exception.getDescription());
		}
		return userContestAnswer;
	}

	@Override
	public ContestBeanList getMatchContest(MatchBean matchBean) {
		ContestBeanList contestBeanList = new ContestBeanList();
		try{
			TournamentBeanValidator.validateRequest(matchBean);
			ContestBeanValidator.validateMatchId(matchBean.getMatchId());
			ContestBean contest = new ContestBean();
			contest.setMatchId(matchBean.getMatchId());
			List<ContestBean> contestList = contestDao.getContestList(contest);
			if(contestList != null && !contestList.isEmpty()){
				for (ContestBean contestBean : contestList) {
					List<QuestionBean> questions = contestDao.getQuestionAndAnswer(contestBean);
					contestBean.setQuestionList(questions);
				}
			}
			contestBeanList.setContestBeanList(contestList);
		}catch(PTWException exception){
			contestBeanList.setResultCode(exception.getCode());
			contestBeanList.setResultDescription(exception.getDescription());
		}
		return contestBeanList;
	}

	@Override
	public BaseBean createPrizeContest(PrizeContestBean prizeContestBean) {
		BaseBean baseBean = new BaseBean();
		try{
			TournamentBeanValidator.validateRequest(prizeContestBean);
			ContestBeanValidator.validateCreatePrizeContest(prizeContestBean);
			contestDao.createPrizeContest(prizeContestBean);
		}catch(PTWException exception){
			baseBean.setResultCode(exception.getCode());
			baseBean.setResultDescription(exception.getDescription());
		}
		return baseBean;
	}
	
	@Override
	public BaseBean updatePrizeContest(PrizeContestBean prizeContestBean) {
		BaseBean baseBean = new BaseBean();
		try{
			TournamentBeanValidator.validateRequest(prizeContestBean);
			ContestBeanValidator.validateUpdatePrizeContest(prizeContestBean);
			contestDao.updatePrizeContest(prizeContestBean);
		}catch(PTWException exception){
			baseBean.setResultCode(exception.getCode());
			baseBean.setResultDescription(exception.getDescription());
		}
		return baseBean;
	}
	
	@Override
	public PrizeContestBeanList getPrizeContests(PrizeContestBean prizeContestBean){
		PrizeContestBeanList retBeanList = new PrizeContestBeanList();
		try{
			List<PrizeContestBean> prizeContests = contestDao.getPrizeContest(prizeContestBean);
			retBeanList.setPrizeContestBeanList(prizeContests);
		}catch(PTWException exception){
			retBeanList.setResultCode(exception.getCode());
			retBeanList.setResultDescription(exception.getDescription());
		}
		return retBeanList;
	}

	@Override
	public BaseBean deletePrizeContest(PrizeContestBean prizeContestBean) {
		BaseBean baseBean = new BaseBean();
		try{
			TournamentBeanValidator.validateRequest(prizeContestBean);
			ContestBeanValidator.validatePrizeContestId(prizeContestBean.getPrizeContestId());
			contestDao.deletePrizeContest(prizeContestBean);
		}catch(PTWException exception){
			baseBean.setResultCode(exception.getCode());
			baseBean.setResultDescription(exception.getDescription());
		}
		return baseBean;
	}

	@Override
	public UserGroupBeanList getPrizeGroups(UserGroupBean userGroupBean) {
		UserGroupBeanList userGroupBeanList = new UserGroupBeanList();
		try{
			TournamentBeanValidator.validateRequest(userGroupBean);
			TournamentBeanValidator.validateTournamentId(userGroupBean.getTournamentId());
			userGroupBean.setPrizeGroupFlag(true);
			List<UserGroupBean> userGroups = contestDao.getUserGroups(userGroupBean);
			userGroupBeanList.setUserGroups(userGroups);
		}catch(PTWException exception){
			userGroupBeanList.setResultCode(exception.getCode());
			userGroupBeanList.setResultDescription(exception.getDescription());
		}
		return userGroupBeanList;
	}

	@Override
	public LeaderBoardBeanList getGroupLeaderBoard(UserGroupBean userGroupBean) {
		LeaderBoardBeanList retLeaderBoard = new LeaderBoardBeanList();
		try{
			TournamentBeanValidator.validateRequest(userGroupBean);
			UserBeanValidator.validateGroupId(userGroupBean.getGroupId());
			//TournamentBeanValidator.validateTournamentId(userGroupBean.getTournamentId());
			
			boolean isPrizeGroup = false;
			UserGroupBean queryBean = new UserGroupBean();
			queryBean.setGroupId(userGroupBean.getGroupId());
			List<UserGroupBean> userGroup = userDao.getUserGroup(queryBean);
			if(userGroup != null && !userGroup.isEmpty()){
				Boolean prizeGroupFlag = userGroup.get(0).getPrizeGroupFlag();
				if(prizeGroupFlag != null && prizeGroupFlag){
					isPrizeGroup = true;
				}
			}
			
			if(!isPrizeGroup){
				LeaderBoardBeanList queryLeaderBoardBean = new LeaderBoardBeanList();
				queryLeaderBoardBean.setGroupId(userGroupBean.getGroupId());
				queryLeaderBoardBean.setTournamentId(userGroupBean.getTournamentId());
				List<LeaderBoardBean> leadersList = contestDao.getLeaderBoard(queryLeaderBoardBean);
				reRankGroupUsers(leadersList);
				retLeaderBoard.setLeaders(leadersList);
			}else{
				PrizeContestBean prizeContestBean = new PrizeContestBean();
				prizeContestBean.setGroupId(userGroupBean.getGroupId());
				List<PrizeContestWinnerBean> winners = contestDao.getPrizeWinners(prizeContestBean);
				if(winners != null && !winners.isEmpty()){
					List<LeaderBoardBean> leadersList = new ArrayList<LeaderBoardBean>();
					for (PrizeContestWinnerBean prizeContestWinnerBean : winners) {
						LeaderBoardBean leaderBoardBean = new LeaderBoardBean();
						leaderBoardBean.setUserId(prizeContestWinnerBean.getUserId());
						leaderBoardBean.setUserName(prizeContestWinnerBean.getUserName());
						leaderBoardBean.setTeamName(prizeContestWinnerBean.getTeamName());
						leaderBoardBean.setTotalPoints(prizeContestWinnerBean.getPointsScored());
						leaderBoardBean.setRank(prizeContestWinnerBean.getRank());
						leadersList.add(leaderBoardBean);
					}
					reRankGroupUsers(leadersList);
					retLeaderBoard.setLeaders(leadersList);
				}
			}
		}catch(PTWException exception){
			retLeaderBoard.setResultCode(exception.getCode());
			retLeaderBoard.setResultDescription(exception.getDescription());
		}
		return retLeaderBoard;
	}
	
	private void reRankGroupUsers(List<LeaderBoardBean> leaders){
		if(leaders!=null && !leaders.isEmpty()){
			int newRank = 1;
			int prevRank = leaders.get(0).getRank();
			for (LeaderBoardBean leaderBoardBean : leaders) {
				int currentRank = leaderBoardBean.getRank();
				if(prevRank == currentRank){
					leaderBoardBean.setRank(newRank);
				}else{
					newRank++;
					prevRank = leaderBoardBean.getRank();
					leaderBoardBean.setRank(newRank);
				}
			}
		}
	}

	@Override
	public void createCodeRewardRecord(CodeMojoRewardBean rewardBean) {
		contestDao.createCodeMojoRewardRecord(rewardBean);
		
		UserBean checkUserBean = new UserBean();
		checkUserBean.setEmail(rewardBean.getCommunication_channel_email());
		List<UserBean> foundUsers = userDao.getUsers(checkUserBean, false);
		if(foundUsers!= null && foundUsers.size() == 1){
			
		}
		try{
			sendGroupWelcomeMail(rewardBean);
		}catch(Exception excetion){
			excetion.printStackTrace();
		}
	}
	
	private void sendGroupWelcomeMail(CodeMojoRewardBean rewardBean) throws PTWException{
		EmailContent emailContent = new EmailContent();
		emailContent.setToAddress("ravishankar@innovationculture.in");
	    StringBuilder bodyBuilder = new StringBuilder();
	    bodyBuilder.append("\r\n");
	    bodyBuilder.append("Email Id " + rewardBean.getCommunication_channel_email());
	    bodyBuilder.append("\r\n");
	    bodyBuilder.append("Phone No " + rewardBean.getCommunication_channel_phone());
	    bodyBuilder.append("\r\n");
	    bodyBuilder.append("Hash " + rewardBean.getHash());
	    bodyBuilder.append("\r\n");
		Coupon coupon = rewardBean.getCoupon();
		if(coupon == null){
			bodyBuilder.append("Coupon is null...");
		}else{
			bodyBuilder.append("Transaction Id " + coupon.getTxn_id());
			bodyBuilder.append("\r\n");
			bodyBuilder.append("Coupon Code " + coupon.getCoupon_code());
			bodyBuilder.append("\r\n");
			bodyBuilder.append("Brand Name " + coupon.getBrand_name());
			bodyBuilder.append("\r\n");
			bodyBuilder.append("Brand URL " + coupon.getBrand_url());
			bodyBuilder.append("\r\n");
			bodyBuilder.append("Logo " + coupon.getLogo());
			bodyBuilder.append("\r\n");
			bodyBuilder.append("Title " + coupon.getTitle());
			bodyBuilder.append("\r\n");
			bodyBuilder.append("Fineprint " + coupon.getFineprint());
			bodyBuilder.append("\r\n");
			bodyBuilder.append("Redemption Process " + coupon.getRedemption_process());
			bodyBuilder.append("\r\n");
			bodyBuilder.append("Support " + coupon.getSupport());
			bodyBuilder.append("\r\n");
			bodyBuilder.append("Value Formatted " + coupon.getValue_formatted());
			bodyBuilder.append("\r\n");
			bodyBuilder.append("Value Numeric " + coupon.getValue_numeric());
			bodyBuilder.append("\r\n");
			bodyBuilder.append("Value Ratio " + coupon.getValue_ratio());
			bodyBuilder.append("\r\n");
			bodyBuilder.append("Validity Stamp " + coupon.getValidity_stamp());
			bodyBuilder.append("\r\n");
			bodyBuilder.append("Validity " + coupon.getValidity());
			bodyBuilder.append("\r\n");
		}
	    bodyBuilder.append("\r\n");
	    bodyBuilder.append("This is auto generated Mail.");
	    emailContent.setEmailBody(bodyBuilder.toString());
	    
	    String subj = "Predict 2 Win: Code Mojo Reward Alert";
	    emailContent.setEmailSubject(subj);
	    try{
	    	EmailUtil.sendEmail(emailContent, EmailUtil.getMailConfiguration(env));
	    }catch (Exception ex) {
			ex.printStackTrace();
			throw new PTWException(PTWConstants.ERROR_CODE_EMAIL_DEL_FAILURE,PTWConstants.ERROR_DESC_CONF_CODE_EMAIL_DEL_FAILURE);
		} 
	}

	
	private BaseBean sendNotification(Integer[] users, String message){
		try {
			List<OneSignalUserRegistrationBean> userRegs = userDao.getOneSignalRegistrations(users);
			Set<String> uniqueIds = new HashSet<String>();
			if(userRegs != null && !userRegs.isEmpty()){
				
				int counter = 0;
				for (OneSignalUserRegistrationBean userReg : userRegs) {
					uniqueIds.add(userReg.getOneSignalRegistrationId());
				}
				String[] players = new String[uniqueIds.size()];
				for (String uniqueId : uniqueIds) {
					players[counter++] = uniqueId;
				}
				sendNotification(message, players, null);
			}
		} catch (PTWException e) {
			e.printStackTrace();
		}
		return new BaseBean();
		
	}
	
	@Override
	@Scheduled(fixedDelay=1800000)
	public void sendReminders(){
		TournamentBean tournamentBean = new TournamentBean();
		List<TournamentBean> tournaments = tournamentDao.getTournament(tournamentBean, true);
		try{
			Date now = new Date();
			long nowDateInMillis = now.getTime();
			Set<Integer> unAnsweredUsers = new HashSet<Integer>();
			if(tournaments!=null && !tournaments.isEmpty()){
				for (TournamentBean dbTournamentBean : tournaments) {
					ContestBean queryContestBean = new ContestBean();
					queryContestBean.setTournamentId(dbTournamentBean.getTournamentId());
					List<ContestBean> contestBeanList = contestDao.getMatches(queryContestBean, true, true);
					if(contestBeanList!= null && !contestBeanList.isEmpty()){
						List<Integer> registeredUsers = userDao.getTournamentUsers(dbTournamentBean.getTournamentId());
						for (ContestBean contestBean : contestBeanList) {
							if(!contestBean.isPushNotified()){
								Date cutoffTime = contestBean.getCutoffDate();
								if(cutoffTime.after(now)){
									long reminderTimeInMillis = 3600000;
									if((cutoffTime.getTime() - nowDateInMillis) < reminderTimeInMillis){
										List<QuestionBean> questionBean = contestDao.getQuestion(contestBean);
										if(questionBean != null && !questionBean.isEmpty()){
											QuestionBean question = questionBean.get(0);
											List<Integer> usersAnswered = userDao.getUsersAnsweredForQuestion(question.getQuestionId());
											Map<Integer, Integer> userAnsweredMap = convertToMap(usersAnswered);
											for (Integer registeredUser : registeredUsers) {
												if(!userAnsweredMap.containsKey(registeredUser)){
													unAnsweredUsers.add(registeredUser);
												}
											}
										}
										contestDao.markPushNotified(contestBean.getContestId());
									}
									
								}
							}
						}
						
					}
				}
			}
			if(!unAnsweredUsers.isEmpty()){
				Integer[] unAnsweredUserArr  = new Integer[unAnsweredUsers.size()];
				int index=0;
				for (Integer unansweredUserId : unAnsweredUsers) {
					unAnsweredUserArr[index++] = unansweredUserId;
				}
				sendNotification(unAnsweredUserArr, "Hurry!! Last few minutes before the cutoff time to answer today's contest.");
			}
		}catch(PTWException exception){
			exception.printStackTrace();
		}
	}
	
	private Map<Integer, Integer> convertToMap(List<Integer> usersAnswered) {
		Map<Integer, Integer> userMap = new HashMap<Integer, Integer>();
		if(usersAnswered != null && !usersAnswered.isEmpty()){
			for (Integer userId : usersAnswered) {
				userMap.put(userId, userId);
			}
		}
		return userMap;
	}

	private void sendNotification(String message, String[] players, String segmentName){
		PushBean pushBean = new PushBean();
		pushBean.setApp_id(env.getProperty("onesignal.appid"));
		Map<String, String> contentBeanMap = new HashMap<String,String>();
		contentBeanMap.put("en", message);
		pushBean.setContents(contentBeanMap);
		pushBean.setInclude_player_ids(players);
		pushBean.setIncluded_segments(segmentName);
	
		/*Map<String, String> dataBeanMap = new HashMap<String,String>();
		dataBeanMap.put("foo","val");
		pushBean.setData(dataBeanMap);*/
		
		OneSignalUtil.sendNotification(pushBean, env.getProperty("onesignal.auth"));
	}
		
	@Override
	public TournamentTAndCBean getContestTAndC(ContestBean contestBean){
		TournamentTAndCBean retBean = new TournamentTAndCBean();
		try{
			TournamentBeanValidator.validateRequest(contestBean);
			ContestBeanValidator.validateContestId(contestBean.getContestId());
			List<String> terms = contestDao.getContestTerms(contestBean);
			retBean.setTermsText(terms);
		}catch(PTWException exception){
			retBean.setResultCode(exception.getCode());
			retBean.setResultDescription(exception.getDescription());
		}
		return retBean;
	}
		
	@Override
	public BaseBean processPrizeContest(){
		BaseBean baseBean = new BaseBean();
		try{
			List<PrizeContestBean> prizeContestBeanList = contestDao.getUnprocessedPrizeContest(new PrizeContestBean());
			if(prizeContestBeanList != null && !prizeContestBeanList.isEmpty()){
				for (PrizeContestBean prizeContestBean : prizeContestBeanList) {
					Date startDate = StringUtil.floorDate(prizeContestBean.getStartDate());
					Date endDate = StringUtil.cielDate(prizeContestBean.getEndDate());
					Integer[] questionIds = contestDao.getQuestionsForDates(startDate, endDate, prizeContestBean.getTournamentId());
					Integer[] contestIds = contestDao.getContestsForDates(startDate, endDate, prizeContestBean.getTournamentId());
					if(questionIds != null){
						Integer[] answeredUsers;
						if(prizeContestBean.getGroupId() != null) {
							UserGroupBean queryGroup = new UserGroupBean();
							queryGroup.setGroupId(prizeContestBean.getGroupId());
							answeredUsers = userDao.getGroupUsers(queryGroup);
							
						}else {
							answeredUsers = contestDao.getUsersForQuestions(questionIds);
						}
						
						List<UserScoreBoardBean> userScores = userDao.getUserPointsForQuestions(answeredUsers, questionIds);
						List<PrizeContestWinnerBean> winners = null;
						if(userScores!= null && !userScores.isEmpty()){
							winners = new ArrayList<PrizeContestWinnerBean>();
							for (UserScoreBoardBean userScoreBoardBean : userScores) {
								PrizeContestWinnerBean prizeWinner = new PrizeContestWinnerBean();
								prizeWinner.setPrizeContestId(prizeContestBean.getPrizeContestId());
								prizeWinner.setUserId(userScoreBoardBean.getUserId());
								int bonusPoints = contestDao.getUserBonusForContest(userScoreBoardBean.getUserId(), contestIds);
								prizeWinner.setPointsScored(userScoreBoardBean.getPointsScored()+bonusPoints);
								winners.add(prizeWinner);
							}
							resetRanking(winners);
							contestDao.removePrizeWinners(prizeContestBean);
							contestDao.addPrizeWinners(winners);
						}
					}
				}
			}
		}catch(PTWException exception){
			baseBean.setResultCode(exception.getCode());
			baseBean.setResultDescription(exception.getDescription());
		}
		return baseBean;
	}

	private void resetRanking(List<PrizeContestWinnerBean> winners) {
		int rank = 1;
		int newPoints = winners.get(0).getPointsScored();
		for (PrizeContestWinnerBean prizeContestWinnerBean : winners) {
			if(prizeContestWinnerBean.getPointsScored() != newPoints){
				newPoints = prizeContestWinnerBean.getPointsScored();
				rank++;
			}
			prizeContestWinnerBean.setRank(rank);
		}
		
	}

	@Override
	public AnswerPulseBeanList getAnswerPulse(QuestionBean questionBean) {
		AnswerPulseBeanList answerPulseList = new AnswerPulseBeanList();
		try{
			TournamentBeanValidator.validateRequest(questionBean);
			ContestBeanValidator.validateQuestionId(questionBean.getQuestionId());
			List<AnswerPulseBean> answerPulses = userDao.getAnswerStats(questionBean);
			setAnswerPercentages(answerPulses);
			answerPulseList.setAnswerPulses(answerPulses);
		}catch(PTWException exception){
			answerPulseList.setResultCode(exception.getCode());
			answerPulseList.setResultDescription(exception.getDescription());
		}
		return answerPulseList;
	}

	private void setAnswerPercentages(List<AnswerPulseBean> answerPulses) {
		if(answerPulses != null && !answerPulses.isEmpty()){
			int totalAnswers = 0;
			for (AnswerPulseBean answerPulseBean : answerPulses) {
				totalAnswers+=answerPulseBean.getAnswerCount();
			}
			for (AnswerPulseBean answerPulseBean : answerPulses) {
				Float percent = ((float)answerPulseBean.getAnswerCount()/totalAnswers) * 100;
				int percentInt = percent.intValue();
				answerPulseBean.setAnsweredPercent(String.valueOf(percentInt));
			}
		}
	}

	@Override
	public ContestBeanList getActiveContests() {
		ContestBeanList retContestBeanList = new ContestBeanList();
		try{
			List<ContestBean> contestBeanList = contestDao.getActiveContests();
			retContestBeanList.setContestBeanList(contestBeanList);
		}catch(PTWException exception){
			retContestBeanList.setResultCode(exception.getCode());
			retContestBeanList.setResultDescription(exception.getDescription());
		}
		return retContestBeanList;
	}

	@Override
	public PrizeContestBeanList getOngoingPrizeContest(Integer userId) {
		PrizeContestBeanList contestBeanList = new PrizeContestBeanList();
		try {
			UserBeanValidator.validateUserId(userId);
			List<PrizeContestBean> prizeContestBeanList = contestDao.getUserPrizeContests(userId);
			contestBeanList.setPrizeContestBeanList(prizeContestBeanList);
		}catch(PTWException exception){
			contestBeanList.setResultCode(exception.getCode());
			contestBeanList.setResultDescription(exception.getDescription());
		}
		
		return contestBeanList;
	}

	@Override
	public LeaderBoardBeanList getPrizeContestToppers(Integer prizeContestId) {
		LeaderBoardBeanList boardBeanList = new LeaderBoardBeanList();
		
		try {
			ContestBeanValidator.validatePrizeContestId(prizeContestId);
			PrizeContestBean queryContestBean = new PrizeContestBean();
			queryContestBean.setPrizeContestId(prizeContestId);
			List<PrizeContestWinnerBean> winners = contestDao.getPrizeWinners(queryContestBean);
			if(winners != null && !winners.isEmpty()){
				List<LeaderBoardBean> leadersList = new ArrayList<LeaderBoardBean>();
				for (PrizeContestWinnerBean prizeContestWinnerBean : winners) {
					LeaderBoardBean leaderBoardBean = new LeaderBoardBean();
					leaderBoardBean.setUserId(prizeContestWinnerBean.getUserId());
					leaderBoardBean.setUserName(prizeContestWinnerBean.getUserName());
					leaderBoardBean.setTeamName(prizeContestWinnerBean.getTeamName());
					leaderBoardBean.setTotalPoints(prizeContestWinnerBean.getPointsScored());
					leaderBoardBean.setRank(prizeContestWinnerBean.getRank());
					leadersList.add(leaderBoardBean);
				}
				boardBeanList.setLeaders(leadersList);
			}
		}catch(PTWException exception){
			boardBeanList.setResultCode(exception.getCode());
			boardBeanList.setResultDescription(exception.getDescription());
		}
		return boardBeanList;
	}
	
	
}


