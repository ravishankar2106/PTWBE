package com.bind.ptw.be.services;

import com.bind.ptw.be.dto.BaseBean;
import com.bind.ptw.be.dto.CountryBean;
import com.bind.ptw.be.dto.CountryBeanList;
import com.bind.ptw.be.dto.PlayerBean;
import com.bind.ptw.be.dto.PlayerBeanList;
import com.bind.ptw.be.dto.SportTypeBeanList;
import com.bind.ptw.be.dto.SportTypeCountryList;
import com.bind.ptw.be.dto.TeamBean;
import com.bind.ptw.be.dto.TeamBeanList;
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
	
	CountryBean createCountry(CountryBean countryBean);
	CountryBeanList getCountryList(CountryBean countryBean);
	BaseBean updateCountry(CountryBean countryBean);
	BaseBean deleteCountry(CountryBean countryBean);
	
	TeamBean createTeam(TeamBean teamBean);
	TeamBeanList getTeamList(TeamBean teamBean);
	BaseBean updateTeam(TeamBean teamBean);
	BaseBean deleteTeam(TeamBean teamBean);
	
	PlayerBean createPlayer(PlayerBean playerBean);
	PlayerBeanList getPlayerList(PlayerBean playerBean);
	BaseBean updatePlayer(PlayerBean playerBean);
	BaseBean deletePlayer(PlayerBean playerBean);
	
	BaseBean addCountriesToSportType(SportTypeCountryList sportTypeCountryList);
	SportTypeCountryList getCountriesForSportType(SportTypeCountryList sportTypeCountryList);
	BaseBean removeCountriesFromSportType(SportTypeCountryList sportTypeCountryList);
}
