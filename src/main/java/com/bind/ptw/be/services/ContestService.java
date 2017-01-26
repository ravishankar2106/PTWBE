package com.bind.ptw.be.services;

import java.util.List;

import com.bind.ptw.be.dto.BaseBean;
import com.bind.ptw.be.dto.ContestBean;
import com.bind.ptw.be.dto.ContestBeanList;
import com.bind.ptw.be.dto.MatchBean;
import com.bind.ptw.be.dto.MatchBeanList;
import com.bind.ptw.be.dto.QuestionBean;

public interface ContestService {
	MatchBean createMatch(MatchBean matchBean);
	MatchBeanList getMatches(MatchBean matchBean);
	BaseBean updateMatchStatus(MatchBean matchBean);
	BaseBean deleteMatch(MatchBean matchBean);
	
	ContestBean createContest(ContestBean contestBean);
	ContestBeanList getContests(ContestBean contestBean);
	BaseBean updateContest(ContestBean contestBean);
	BaseBean updateContestStatus(ContestBean contestBean);
	BaseBean deleteContest(ContestBean contestBean);
	ContestBeanList getOngoingContests(ContestBean contestBean);
	
	QuestionBean createQuestion(QuestionBean questionBean);
	BaseBean updateQuestion(QuestionBean questionBean);
	ContestBean getContestQuestion(ContestBean contestBean);
	BaseBean deleteQuestion(ContestBean contestBean);
	
}
