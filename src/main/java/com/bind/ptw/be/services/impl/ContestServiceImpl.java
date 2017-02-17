package com.bind.ptw.be.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bind.ptw.be.dao.ContestDao;
import com.bind.ptw.be.dao.TournamentDao;
import com.bind.ptw.be.dao.UserDao;
import com.bind.ptw.be.dto.AnswerBean;
import com.bind.ptw.be.dto.AnswerOptionBean;
import com.bind.ptw.be.dto.AnswerTypeBean;
import com.bind.ptw.be.dto.AnswerTypeBeanList;
import com.bind.ptw.be.dto.BaseBean;
import com.bind.ptw.be.dto.ContestBean;
import com.bind.ptw.be.dto.ContestBeanList;
import com.bind.ptw.be.dto.MatchBean;
import com.bind.ptw.be.dto.MatchBeanList;
import com.bind.ptw.be.dto.PossibleAnswerBean;
import com.bind.ptw.be.dto.QuestionBean;
import com.bind.ptw.be.dto.QuestionBeanList;
import com.bind.ptw.be.dto.TeamPlayerBean;
import com.bind.ptw.be.dto.TournTeamPlayerBeanList;
import com.bind.ptw.be.dto.TournamentBean;
import com.bind.ptw.be.dto.TournamentTeamBean;
import com.bind.ptw.be.dto.TournamentTeamBeanList;
import com.bind.ptw.be.dto.UserContestAnswer;
import com.bind.ptw.be.dto.UserScoreBoardBean;
import com.bind.ptw.be.dto.UserSelectedAnswerBean;
import com.bind.ptw.be.services.ContestService;
import com.bind.ptw.be.services.util.ContestBeanValidator;
import com.bind.ptw.be.services.util.TournamentBeanValidator;
import com.bind.ptw.be.util.PTWConstants;
import com.bind.ptw.be.util.PTWException;
import com.bind.ptw.be.util.StringUtil;

@Service("contestService")
@Transactional
public class ContestServiceImpl implements ContestService{

	@Autowired
	ContestDao contestDao;
	
	@Autowired
	TournamentDao tournamentDao;
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	HibernateTemplate hibernateTemplate;
	
	@Override
	public MatchBean createMatch(MatchBean matchBean) {
		MatchBean retBean = null;
		try{
			TournamentBeanValidator.vaidateRequest(matchBean);
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
			TournamentBeanValidator.vaidateRequest(matchBean);
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
			TournamentBeanValidator.vaidateRequest(matchBean);
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
			TournamentBeanValidator.vaidateRequest(matchBean);
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
			TournamentBeanValidator.vaidateRequest(contestBean);
			ContestBeanValidator.validateCreateContest(contestBean, tournamentDao, contestDao);
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
			TournamentBeanValidator.vaidateRequest(contestBean);
			TournamentBeanValidator.validateTournamentId(contestBean.getTournamentId());
			List<ContestBean> contestBeanList = contestDao.getMatches(contestBean, false);
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
			TournamentBeanValidator.vaidateRequest(contestBean);
			TournamentBeanValidator.validateTournamentId(contestBean.getTournamentId());
			List<ContestBean> contestBeanList = contestDao.getMatches(contestBean, true);
			if(contestBeanList != null && !contestBeanList.isEmpty()){
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
				retContestBeanList.setContestBeanList(contestBeanList);
			}
			
		}catch(PTWException exception){
			retContestBeanList.setResultCode(exception.getCode());
			retContestBeanList.setResultDescription(exception.getDescription());
		}
		return retContestBeanList;
	}

	@Override
	public BaseBean updateContest(ContestBean contestBean) {
		BaseBean retBean = new BaseBean();
		try{
			TournamentBeanValidator.vaidateRequest(contestBean);
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
			TournamentBeanValidator.vaidateRequest(contestBean);
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
			TournamentBeanValidator.vaidateRequest(contestBean);
			ContestBeanValidator.validateContestId(contestBean.getContestId());
			contestDao.deleteContest(contestBean);
		}catch(PTWException exception){
			retBean.setResultCode(exception.getCode());
			retBean.setResultDescription(exception.getDescription());
		}
		return retBean;
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
			if(StringUtil.isEmptyNull(contestBean.getContestId())){
				contestBean.setContestStatusId(2);
			}
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
					System.out.println("Correct Answer Option " + correctAnswerOptionId);
					System.out.println("Points Scored " + pointsScored);
					selectedAnswerPointMap.put(correctAnswerOptionId, pointsScored);
					
					UserSelectedAnswerBean reqSelectedAnswer = new UserSelectedAnswerBean();
					reqSelectedAnswer.setSelectedAnswerOptionId(correctAnswerOptionId);
					List<UserSelectedAnswerBean> userSelectedAnswerList = contestDao.getUserAnswers(reqSelectedAnswer);
					if(userSelectedAnswerList != null && !userSelectedAnswerList.isEmpty()){
						for (UserSelectedAnswerBean userSelectedAnswerBean : userSelectedAnswerList) {
							Integer userId = userSelectedAnswerBean.getUserId();
							System.out.println("Correct answer given by " + userId);
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
			markContestAsCompleted(contestId);
			
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
		contestDao.updateUserRanks(newPointRanking);
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
			TournamentBeanValidator.vaidateRequest(questionBean);
			ContestBeanValidator.validateCreateQuestion(questionBean, tournamentDao, contestDao);
			retBean = contestDao.createQuestion(questionBean);
			
			if(questionBean.getAnswerTypeId() == 1){
				Integer contestId = questionBean.getContestId();
				ContestBean contestBean = new ContestBean();
				contestBean.setContestId(contestId);
				List<ContestBean> contestList = contestDao.getMatches(contestBean, false);
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
			TournamentBeanValidator.vaidateRequest(questionBean);
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
			TournamentBeanValidator.vaidateRequest(contestBean);
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
	public BaseBean deleteQuestion(ContestBean contestBean) {
		BaseBean retBean = new BaseBean();
		try{
			TournamentBeanValidator.vaidateRequest(contestBean);
			ContestBeanValidator.validateContestId(contestBean.getContestId());
			List<QuestionBean> questionList = contestDao.getQuestion(contestBean);
			if(questionList != null && !questionList.isEmpty()){
				for (QuestionBean questionBean : questionList) {
					contestDao.deleteQuestion(questionBean);
				}
			}
			
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
			TournamentBeanValidator.vaidateRequest(questionBean);
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
			TournamentBeanValidator.vaidateRequest(questionBean);
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
			TournamentBeanValidator.vaidateRequest(questionBean);
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
					TournamentTeamBeanList teamList = tournamentDao.getTeamsForTournament(tournamentBean);
					if(teamList != null && teamList.getTournamentTeamBeanList() != null){
						for (TournamentTeamBean tournamentTeam : teamList.getTournamentTeamBeanList()) {
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

	private void appendPlayers(List<String> namesList, Integer teamAId) throws PTWException {
		TournamentTeamBean tournTeamBean = new TournamentTeamBean();
		tournTeamBean.setTournamentTeamId(teamAId);
		TournTeamPlayerBeanList teamPlayers = tournamentDao.getPlayersForTournamentTeam(tournTeamBean);
		if(teamPlayers != null && teamPlayers.getTeamPlayerBeanList() != null){
			for (TeamPlayerBean teamPlayer : teamPlayers.getTeamPlayerBeanList()) {
				namesList.add(teamPlayer.getPlayerBean().getFirstName() + " " + teamPlayer.getPlayerBean().getLastName());
			}
			
		}
	}
	
}
