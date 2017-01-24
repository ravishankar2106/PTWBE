package com.bind.ptw.be.dao;

import java.util.List;

import com.bind.ptw.be.dto.ContestBean;
import com.bind.ptw.be.dto.MatchBean;
import com.bind.ptw.be.util.PTWException;

public interface ContestDao {
	
	MatchBean createMatch(MatchBean matchBean) throws PTWException;
	List<MatchBean> getMatches(MatchBean matchBean) throws PTWException;
	void updateMatchStatus(MatchBean matchBean) throws PTWException;
	void deleteMatch(MatchBean matchBean) throws PTWException;
	
	ContestBean createContest(ContestBean contestBean) throws PTWException;
	List<ContestBean> getMatches(ContestBean contestBean, Boolean isOngoingContest) throws PTWException;
	void updateContest(ContestBean contestBean) throws PTWException;
	void deleteContest(ContestBean contestBean) throws PTWException;
	
}
