package com.bind.ptw.be.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.bind.ptw.be.dto.AnswerBean;
import com.bind.ptw.be.dto.AnswerOptionBean;
import com.bind.ptw.be.dto.AnswerTypeBean;
import com.bind.ptw.be.dto.CodeMojoRewardBean;
import com.bind.ptw.be.dto.ContestBean;
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
import com.bind.ptw.be.dto.UserContestAnswer;
import com.bind.ptw.be.dto.UserGroupBean;
import com.bind.ptw.be.dto.UserScoreBoardBean;
import com.bind.ptw.be.dto.UserSelectedAnswerBean;
import com.bind.ptw.be.util.PTWException;

public interface ContestDao {
	
	MatchBean createMatch(MatchBean matchBean) throws PTWException;
	List<MatchBean> getMatches(MatchBean matchBean) throws PTWException;
	void updateMatchStatus(MatchBean matchBean) throws PTWException;
	void deleteMatch(MatchBean matchBean) throws PTWException;
	List<TournamentTeamBean> getMatchTeams(MatchBean matchBean) throws PTWException;
	
	ContestBean createContest(ContestBean contestBean) throws PTWException;
	List<ContestBean> getMatches(ContestBean contestBean, Boolean isOngoingContest, Boolean fullData) throws PTWException;
	void updateContest(ContestBean contestBean) throws PTWException;
	void deleteContest(ContestBean contestBean) throws PTWException;
	ContestBean getContest(ContestBean contestBean) throws PTWException;
	List<ContestBean> getContestList(ContestBean contestBean) throws PTWException;
	
	QuestionBean createQuestion(QuestionBean questionBean) throws PTWException;
	List<QuestionBean> getQuestion(ContestBean contestBean) throws PTWException;
	List<QuestionBean> getQuestionAndAnswer(ContestBean contestBean) throws PTWException;
	void updateQuestion(QuestionBean questionBean) throws PTWException;
	void deleteQuestion(QuestionBean questionBean) throws PTWException;
	
	void createAnswerOptions(QuestionBean questionBean) throws PTWException;
	void updateAnswerOption(AnswerOptionBean answerOptionBean) throws PTWException;
	List<AnswerOptionBean> getAnswersForQuestion(QuestionBean questionBean) throws PTWException;
	void deleteAnswerOption(AnswerOptionBean answerOptionBean) throws PTWException;
	List<QuestionBean> getMatchQuestions(MatchBean matchBean) throws PTWException;
	
	void addUserAnswer(UserContestAnswer userContestAnswer)throws PTWException;
	void removeUserAnswer(ContestBean contestBean) throws PTWException;
	List<UserSelectedAnswerBean> getUserAnswers(UserSelectedAnswerBean userSelectedAnswerBean) throws PTWException;
	void updatePointsForUserAnswer(List<AnswerBean> answerBeanList) throws PTWException;
	void updateUserScoreBoard(List<UserScoreBoardBean> userScoreBoardBeanList) throws PTWException;
	List<Integer> getTournamentScores(TournamentBean tournamentBean) throws PTWException;
	void updateUserRanks(Set<Integer> reOrderedList, Integer tournamentId) throws PTWException;
	List<AnswerTypeBean> getAnswerTypes() throws PTWException;
	QuestionBean getQuestion(QuestionBean questionBean) throws PTWException;
	List<LeaderBoardBean> getLeaderBoard(LeaderBoardBeanList leaderBoardBeanList) throws PTWException;
	UserContestAnswer getUserAnswer(UserContestAnswer userContestBean) throws PTWException;
	void createPrizeContest(PrizeContestBean prizeContestBean) throws PTWException;
	void updatePrizeContest(PrizeContestBean prizeContestBean) throws PTWException;
	List<PrizeContestBean> getPrizeContest(PrizeContestBean prizeContestBean) throws PTWException;
	void deletePrizeContest(PrizeContestBean prizeContestBean) throws PTWException;
	List<UserGroupBean> getUserGroups(UserGroupBean userGroupBean) throws PTWException;
	List<ContestBean> getRunningContests() throws PTWException;
	void createCodeMojoRewardRecord(CodeMojoRewardBean rewardBean);
	void updateFanClubStandings(TournamentFanClubBean tournamentFanClubBean);
	void createTOC(TournamentTAndCBean tocBean) throws PTWException;
	List<String> getContestTerms(ContestBean contestBean) throws PTWException;
	Integer[] getQuestionsForDates(Date startDate, Date endDate, Integer tournamentId) throws PTWException;
	List<PrizeContestBean> getUnprocessedPrizeContest(PrizeContestBean prizeContestBean) throws PTWException;
	void addPrizeWinners(List<PrizeContestWinnerBean> winnerBeanList) throws PTWException;
	List<PrizeContestWinnerBean> getPrizeWinners(PrizeContestBean prizeContestBean)throws PTWException;
	void removePrizeWinners(PrizeContestBean prizeContestBean) throws PTWException;
	void markPushNotified(Integer contestId) throws PTWException;
	List<ContestBean> getActiveContests() throws PTWException;
	List<PrizeContestBean> getUserPrizeContests(Integer userId);
	Integer[] getContestsForDates(Date startDate, Date endDate, Integer tournamentId) throws PTWException;
	Integer getUserBonusForContest(Integer userId, Integer[] contestIds) throws PTWException;
	Integer[] getUsersForQuestions(Integer[] questionIds) throws PTWException;
	Integer[] getQuestion(Integer[] contests) throws PTWException;
	Map<Integer, Integer> getUserBonusForContest(Integer[] userIdList, Integer[] contestIds) throws PTWException;
	
	
}
