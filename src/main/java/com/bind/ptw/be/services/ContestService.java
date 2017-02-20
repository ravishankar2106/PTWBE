package com.bind.ptw.be.services;

import com.bind.ptw.be.dto.AnswerOptionBean;
import com.bind.ptw.be.dto.AnswerTypeBeanList;
import com.bind.ptw.be.dto.BaseBean;
import com.bind.ptw.be.dto.ContestBean;
import com.bind.ptw.be.dto.ContestBeanList;
import com.bind.ptw.be.dto.LeaderBoardBeanList;
import com.bind.ptw.be.dto.MatchBean;
import com.bind.ptw.be.dto.MatchBeanList;
import com.bind.ptw.be.dto.PossibleAnswerBean;
import com.bind.ptw.be.dto.QuestionBean;
import com.bind.ptw.be.dto.QuestionBeanList;
import com.bind.ptw.be.dto.UserContestAnswer;

public interface ContestService {
	MatchBean createMatch(MatchBean matchBean);
	MatchBeanList getMatches(MatchBean matchBean);
	BaseBean updateMatch(MatchBean matchBean);
	BaseBean deleteMatch(MatchBean matchBean);
	QuestionBeanList getMatchQuestions(MatchBean matchBean);
	
	ContestBean createContest(ContestBean contestBean);
	ContestBeanList getContests(ContestBean contestBean);
	BaseBean updateContest(ContestBean contestBean);
	BaseBean updateContestStatus(ContestBean contestBean);
	BaseBean deleteContest(ContestBean contestBean);
	ContestBeanList getOngoingContests(ContestBean contestBean);
	BaseBean processContests(ContestBean contestBean);
	
	QuestionBean createQuestion(QuestionBean questionBean);
	BaseBean updateQuestion(QuestionBean questionBean);
	ContestBean getContestQuestion(ContestBean contestBean);
	BaseBean deleteQuestion(ContestBean contestBean);
	
	BaseBean createAnswerOptions(QuestionBean questionBean);
	BaseBean updateAnswerOption(AnswerOptionBean answerOptionBean);
	QuestionBean getAnswersForQuestion(QuestionBean questionBean);
	BaseBean deleteAnswerOption(AnswerOptionBean answerOptionBean);
	
	BaseBean submitUserAnswer(UserContestAnswer userContestAsnwer);
	AnswerTypeBeanList getAnswerTypes();
	PossibleAnswerBean getPossibleAnswers(QuestionBean questionBean);
	LeaderBoardBeanList getLeaderBoard(LeaderBoardBeanList leaderBoardRequest);
	
	
}
