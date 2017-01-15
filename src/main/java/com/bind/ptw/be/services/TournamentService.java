package com.bind.ptw.be.services;

import com.bind.ptw.be.dto.BaseBean;
import com.bind.ptw.be.dto.SportTypeBeanList;
import com.bind.ptw.be.dto.TeamTypeBeanList;
import com.bind.ptw.be.dto.TournamentBean;
import com.bind.ptw.be.dto.TournamentBeanList;

public interface TournamentService {
	TournamentBean createTournament(TournamentBean tournamentBean);
	TournamentBeanList getOngoingTournament(TournamentBean tournamentBean);
	BaseBean updateTournament(TournamentBean tournamentBean);
	BaseBean archieveTournament(TournamentBean tournamentBean);
	BaseBean deleteTournament(TournamentBean tournamentBean);
	TeamTypeBeanList getTeamType();
	SportTypeBeanList getSportType();
	
}
