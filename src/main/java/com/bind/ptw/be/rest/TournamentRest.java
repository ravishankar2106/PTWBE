package com.bind.ptw.be.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bind.ptw.be.dto.BaseBean;
import com.bind.ptw.be.dto.SportTypeBeanList;
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

}
