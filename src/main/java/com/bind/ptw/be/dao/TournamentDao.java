package com.bind.ptw.be.dao;

import java.util.List;

import com.bind.ptw.be.dto.CountryBean;
import com.bind.ptw.be.dto.PlayerBean;
import com.bind.ptw.be.dto.SportTypeBean;
import com.bind.ptw.be.dto.SportTypeCountryList;
import com.bind.ptw.be.dto.TeamBean;
import com.bind.ptw.be.dto.TeamPlayerBean;
import com.bind.ptw.be.dto.TeamPlayerList;
import com.bind.ptw.be.dto.TeamTypeBean;
import com.bind.ptw.be.dto.TournTeamPlayerBeanList;
import com.bind.ptw.be.dto.TournamentBean;
import com.bind.ptw.be.dto.TournamentTAndCBean;
import com.bind.ptw.be.dto.TournamentTeamBean;
import com.bind.ptw.be.dto.TournamentTeamBeanList;
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

	PlayerBean createPlayer(PlayerBean playerBean) throws PTWException;
	List<PlayerBean> getPlayerList(PlayerBean playerBean) throws PTWException;
	void updatePlayer(PlayerBean playerBean) throws PTWException;
	void deletePlayer(PlayerBean playerBean) throws PTWException;
	
	void addCountryToSport(SportTypeCountryList sportTypeCountryList) throws PTWException;
	void removeCountryFromSport(SportTypeCountryList sportTypeCountryList) throws PTWException;
	SportTypeCountryList getCountriesForSport(SportTypeCountryList sportTypeCountryList) throws PTWException;
	
	void addPlayerToTeam(TeamPlayerList teamPlayerList) throws PTWException;
	void removePlayerFromTeam(TeamPlayerList teamPlayerList) throws PTWException;
	TeamPlayerList getPlayersForTeam(TeamPlayerList teamPlayerList) throws PTWException;
	
	void addTeamToTournament(TournamentTeamBeanList tournamentTeamBeanList) throws PTWException;
	void removeTeamFromTournament(TournamentTeamBeanList tournamentTeamBeanList) throws PTWException;
	List<TournamentTeamBean> getTeamsForTournament(TournamentBean tournamentBean) throws PTWException;
	
	void addPlayerToTournamentTeam(TournTeamPlayerBeanList tournTeamPlayerBeanList) throws PTWException;
	void removePlayerFromTournamentTeam(TournTeamPlayerBeanList tournTeamPlayerBeanList) throws PTWException;
	List<TeamPlayerBean> getPlayersForTournamentTeam(TournamentTeamBean tournamentTeamBean) throws PTWException;
	void createTOC(TournamentTAndCBean tocBean) throws PTWException;
	
}
