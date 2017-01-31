package com.bind.ptw.be.dao;

import java.util.List;

import com.bind.ptw.be.dto.AnswerOptionBean;
import com.bind.ptw.be.dto.ContestBean;
import com.bind.ptw.be.dto.MatchBean;
import com.bind.ptw.be.dto.QuestionBean;
import com.bind.ptw.be.dto.TournamentTeamBean;
import com.bind.ptw.be.util.PTWException;

public interface ContestDao {
	
	MatchBean createMatch(MatchBean matchBean) throws PTWException;
	List<MatchBean> getMatches(MatchBean matchBean) throws PTWException;
	void updateMatchStatus(MatchBean matchBean) throws PTWException;
	void deleteMatch(MatchBean matchBean) throws PTWException;
	List<TournamentTeamBean> getMatchTeams(MatchBean matchBean) throws PTWException;
	
	ContestBean createContest(ContestBean contestBean) throws PTWException;
	List<ContestBean> getMatches(ContestBean contestBean, Boolean isOngoingContest) throws PTWException;
	void updateContest(ContestBean contestBean) throws PTWException;
	void deleteContest(ContestBean contestBean) throws PTWException;
	
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
	
}
