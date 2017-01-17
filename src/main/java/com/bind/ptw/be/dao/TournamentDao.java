package com.bind.ptw.be.dao;

import java.util.List;

import com.bind.ptw.be.dto.CountryBean;
import com.bind.ptw.be.dto.SportTypeBean;
import com.bind.ptw.be.dto.TeamBean;
import com.bind.ptw.be.dto.TeamTypeBean;
import com.bind.ptw.be.dto.TournamentBean;
import com.bind.ptw.be.util.PTWException;

public interface TournamentDao {
	
	TournamentBean createTournament(TournamentBean tournamentBean); 
	List<TeamTypeBean> getTeamTypes(TeamTypeBean teamTypeBean);
	void updateTournament(TournamentBean tournamentBean) throws PTWException;
	void archieveTournament(TournamentBean tournamentBean) throws PTWException;
	List<SportTypeBean> getSportTypes(SportTypeBean sportTypeBean);
	List<TournamentBean> getTournament(TournamentBean tournamentBean, Boolean onlyOngoingTournament);
	void deleteTournament(TournamentBean tournament) throws PTWException;
	
	CountryBean createCountry(CountryBean countryBean) throws PTWException;
	List<CountryBean> getCountryList(CountryBean countryBean) throws PTWException;
	void updateCountry(CountryBean countryBean) throws PTWException;
	void deleteCountry(CountryBean countryBean) throws PTWException;
	
	TeamBean createTeam(TeamBean teamBean) throws PTWException;
	List<TeamBean> getTeamList(TeamBean teamBean) throws PTWException;
	void updateTeam(TeamBean teamBean) throws PTWException;
	void deleteTeam(TeamBean teamBean) throws PTWException;

}
