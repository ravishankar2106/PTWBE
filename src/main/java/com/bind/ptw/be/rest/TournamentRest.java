package com.bind.ptw.be.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bind.ptw.be.dto.BaseBean;
import com.bind.ptw.be.dto.CountryBean;
import com.bind.ptw.be.dto.CountryBeanList;
import com.bind.ptw.be.dto.PlayerBean;
import com.bind.ptw.be.dto.PlayerBeanList;
import com.bind.ptw.be.dto.SportTypeBeanList;
import com.bind.ptw.be.dto.SportTypeCountryList;
import com.bind.ptw.be.dto.TeamBean;
import com.bind.ptw.be.dto.TeamBeanList;
import com.bind.ptw.be.dto.TeamPlayerList;
import com.bind.ptw.be.dto.TeamTypeBeanList;
import com.bind.ptw.be.dto.TournamentBean;
import com.bind.ptw.be.dto.TournamentBeanList;
import com.bind.ptw.be.services.TournamentService;

@EnableAutoConfiguration
@RestController
@RequestMapping("/tournament")
public class TournamentRest {
	
	@Autowired
	TournamentService tournamentService;
	
	@PostMapping("/create")
	public TournamentBean create(@RequestBody TournamentBean tournamentBean){
		return tournamentService.createTournament(tournamentBean);
	}
	
	@PostMapping("/getTournament")
	public TournamentBeanList getOngoingTournament (@RequestBody TournamentBean tournamentBean){
		return tournamentService.getOngoingTournament(tournamentBean);
	}
	
	@PostMapping("/updateTournament")
	public BaseBean updateTournament(@RequestBody TournamentBean tournamentBean){
		return tournamentService.updateTournament(tournamentBean);
	}
	
	@PostMapping("/archieveTournament")
	public BaseBean archieveTournament(@RequestBody TournamentBean tournamentBean){
		return tournamentService.archieveTournament(tournamentBean);
	}
	
	@PostMapping("/deleteTournament")
	public BaseBean deleteTournament(@RequestBody TournamentBean tournamentBean){
		return tournamentService.deleteTournament(tournamentBean);
	}
	
	@GetMapping("/sportTypes")
	public SportTypeBeanList getSportTypes(){
		return tournamentService.getSportType();
	}
	
	@GetMapping("/teamTypes")
	public TeamTypeBeanList getTeamTypes(){
		return tournamentService.getTeamType();
	}

	@PostMapping("/createCountry")
	public CountryBean createCountry(@RequestBody CountryBean countryBean){
		return tournamentService.createCountry(countryBean);
	}
	
	@PostMapping("/getCountry")
	public CountryBeanList getCountry(@RequestBody CountryBean countryBean){
		return tournamentService.getCountryList(countryBean);
	}
	
	@PostMapping("/updateCountry")
	public BaseBean updateCountry(@RequestBody CountryBean countryBean){
		return tournamentService.updateCountry(countryBean);
	}
	
	@PostMapping("/deleteCountry")
	public BaseBean deleteCountry(@RequestBody CountryBean countryBean){
		return tournamentService.deleteCountry(countryBean);
	}
	
	@PostMapping("/createTeam")
	public TeamBean createTeam(@RequestBody TeamBean teamBean){
		return tournamentService.createTeam(teamBean);
	}
	
	@PostMapping("/getTeam")
	public TeamBeanList getCountry(@RequestBody TeamBean teamBean){
		return tournamentService.getTeamList(teamBean);
	}
	
	@PostMapping("/updateTeam")
	public BaseBean updateTeam(@RequestBody TeamBean teamBean){
		return tournamentService.updateTeam(teamBean);
	}
	
	@PostMapping("/deleteTeam")
	public BaseBean deleteTeam(@RequestBody TeamBean teamBean){
		return tournamentService.deleteTeam(teamBean);
	}
	
	@PostMapping("/createPlayer")
	public PlayerBean createPlayer(@RequestBody PlayerBean playerBean){
		return tournamentService.createPlayer(playerBean);
	}
	
	@PostMapping("/getPlayer")
	public PlayerBeanList getCountry(@RequestBody PlayerBean playerBean){
		return tournamentService.getPlayerList(playerBean);
	}
	
	@PostMapping("/updatePlayer")
	public BaseBean updatePlayer(@RequestBody PlayerBean playerBean){
		return tournamentService.updatePlayer(playerBean);
	}
	
	@PostMapping("/deletePlayer")
	public BaseBean deletePlayer(@RequestBody PlayerBean playerBean){
		return tournamentService.deletePlayer(playerBean);
	}
	
	@PostMapping("/addCountryToSport")
	public BaseBean addCountriesToSportType(@RequestBody SportTypeCountryList sportTypeCountryList){
		return tournamentService.addCountriesToSportType(sportTypeCountryList);
	}
	
	@PostMapping("/getCountryForSport")
	public SportTypeCountryList getCountriesForSportType(@RequestBody SportTypeCountryList sportTypeCountryList){
		return tournamentService.getCountriesForSportType(sportTypeCountryList);
	}
	
	@PostMapping("/removeCountryFromSport")
	public BaseBean removeCountriesFromSportType(@RequestBody SportTypeCountryList sportTypeCountryList){
		return tournamentService.removeCountriesFromSportType(sportTypeCountryList);
	}
	
	@PostMapping("/addPlayerToTeam")
	public BaseBean addPlayersToTeam(@RequestBody TeamPlayerList teamPlayerList){
		return tournamentService.addPlayersToTeam(teamPlayerList);
	}
	
	@PostMapping("/getPlayerForTeam")
	public TeamPlayerList getPlayersForTeam(@RequestBody TeamPlayerList teamPlayerList){
		return tournamentService.getPlayersForTeam(teamPlayerList);
	}
	
	@PostMapping("/removePlayerFromTeam")
	public BaseBean removePlayersFromTeam(@RequestBody TeamPlayerList teamPlayerList){
		return tournamentService.removePlayersFromTeam(teamPlayerList);
	}
}
