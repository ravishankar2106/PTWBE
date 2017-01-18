package com.bind.ptw.be.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.bind.ptw.be.dao.TournamentDao;
import com.bind.ptw.be.dto.CountryBean;
import com.bind.ptw.be.dto.PlayerBean;
import com.bind.ptw.be.dto.SportTypeBean;
import com.bind.ptw.be.dto.SportTypeCountryList;
import com.bind.ptw.be.dto.TeamBean;
import com.bind.ptw.be.dto.TeamTypeBean;
import com.bind.ptw.be.dto.TournamentBean;
import com.bind.ptw.be.entities.Country;
import com.bind.ptw.be.entities.CountryHome;
import com.bind.ptw.be.entities.CountrySportTypeMapping;
import com.bind.ptw.be.entities.CountrySportTypeMappingKey;
import com.bind.ptw.be.entities.Player;
import com.bind.ptw.be.entities.PlayerHome;
import com.bind.ptw.be.entities.SportType;
import com.bind.ptw.be.entities.SportTypeHome;
import com.bind.ptw.be.entities.Team;
import com.bind.ptw.be.entities.TeamHome;
import com.bind.ptw.be.entities.TeamType;
import com.bind.ptw.be.entities.Tournament;
import com.bind.ptw.be.entities.TournamentHome;
import com.bind.ptw.be.util.PTWConstants;
import com.bind.ptw.be.util.PTWException;
import com.bind.ptw.be.util.StringUtil;

@Repository("tournamentDao")
public class TournamentDaoImpl implements TournamentDao{

	@Autowired
	private HibernateTemplate hibernateTemplate;
	
	private Session getSession() {
	    return hibernateTemplate.getSessionFactory().getCurrentSession();
	}
	
	public TournamentBean createTournament(TournamentBean tournament){
		Tournament tournamentEntity = new Tournament();
		tournamentEntity.setTournamentName(tournament.getTournamentName());
		tournamentEntity.setTournamentDescription(tournament.getTournamentDesc());
		tournamentEntity.setTournamentVenue(tournament.getTournamentVenue());
		tournamentEntity.setStartDate(tournament.getStartDate());
		tournamentEntity.setEndDate(tournament.getEndDate());
		
		TeamType teamType = new TeamType();
		teamType.setTeamTypeId(tournament.getTeamTypeId());
		tournamentEntity.setTeamType(teamType);
		
		SportType sportType = new SportType();
		sportType.setSportTypeId(tournament.getSportTypeId());
		tournamentEntity.setSportType(sportType);
		
		tournamentEntity.setArchievedFlag(false);
		
		TournamentHome tournamentHome = new TournamentHome(getSession());
		tournamentHome.persist(tournamentEntity);
		tournament.setTournamentId(tournamentEntity.getTournamentId());
		return tournament;
	}
	
	public List<TournamentBean> getTournament(TournamentBean tournamentBean, Boolean onlyOngoingTournament){
		List<TournamentBean> resultTournamentBeanList = null;
		TournamentHome tournamentHome = new TournamentHome(getSession());
		List<Tournament> resultTournamentList = tournamentHome.findTournamentByFilters(tournamentBean, onlyOngoingTournament);
		if(resultTournamentList != null && !resultTournamentList.isEmpty()){
			resultTournamentBeanList = new ArrayList<TournamentBean>();
			for (Tournament tournament : resultTournamentList) {
				TournamentBean resultTournamentBean = new TournamentBean();
				resultTournamentBean.setTournamentId(tournament.getTournamentId());
				resultTournamentBean.setTournamentDesc(tournament.getTournamentDescription());
				resultTournamentBean.setTournamentName(tournament.getTournamentName());
				resultTournamentBean.setStartDate(tournament.getStartDate());
				resultTournamentBean.setStartDateStr(StringUtil.convertDateToString(tournament.getStartDate()));
				resultTournamentBean.setEndDate(tournament.getEndDate());
				resultTournamentBean.setEndDateStr(StringUtil.convertDateToString(tournament.getEndDate()));
				resultTournamentBean.setSportTypeId(tournament.getSportType().getSportTypeId());
				resultTournamentBean.setTeamTypeId(tournament.getTeamType().getTeamTypeId());
				resultTournamentBean.setTournamentVenue(tournament.getTournamentVenue());
				resultTournamentBeanList.add(resultTournamentBean);
			}
		}
		return resultTournamentBeanList;
	}
	
	@Override
	public void updateTournament(TournamentBean tournamentBean) throws PTWException {
		TournamentHome tournamentHome = new TournamentHome(getSession());
		Tournament dbTournament = tournamentHome.findById(tournamentBean.getTournamentId());
		if(dbTournament == null){
			throw new PTWException(PTWConstants.ERROR_CODE_TOURNAMENT_ID_NOT_FOUND, PTWConstants.ERROR_DESC_TOURNAMENT_ID_NOT_FOUND);
		}
		dbTournament.setTournamentName(tournamentBean.getTournamentName());
		dbTournament.setTournamentDescription(tournamentBean.getTournamentDesc());
		dbTournament.setTournamentVenue(tournamentBean.getTournamentVenue());
		dbTournament.setStartDate(tournamentBean.getStartDate());
		dbTournament.setEndDate(tournamentBean.getEndDate());
		tournamentHome.merge(dbTournament);
	}
	
	@Override
	public void archieveTournament(TournamentBean tournamentBean) throws PTWException{
		TournamentHome tournamentHome = new TournamentHome(getSession());
		Tournament dbTournament = tournamentHome.findById(tournamentBean.getTournamentId());
		if(dbTournament == null){
			throw new PTWException(PTWConstants.ERROR_CODE_TOURNAMENT_ID_NOT_FOUND, PTWConstants.ERROR_DESC_TOURNAMENT_ID_NOT_FOUND);
		}
		dbTournament.setArchievedFlag(true);
		tournamentHome.merge(dbTournament);
	}

	@Override
	public void deleteTournament(TournamentBean tournamentBean) throws PTWException{
		TournamentHome tournamentHome = new TournamentHome(getSession());
		Tournament dbTournament = tournamentHome.findById(tournamentBean.getTournamentId());
		if(dbTournament == null){
			throw new PTWException(PTWConstants.ERROR_CODE_TOURNAMENT_ID_NOT_FOUND, PTWConstants.ERROR_DESC_TOURNAMENT_ID_NOT_FOUND);
		}
		tournamentHome.remove(dbTournament);
	}
	
	@Override
	public List<SportTypeBean> getSportTypes(SportTypeBean requestSportTypeBean){
		List<SportTypeBean> sportTypeBeanList = null;
		TournamentHome tournamentHome = new TournamentHome(getSession());
		List<SportType> sportTypeList = tournamentHome.findSportType(requestSportTypeBean);
		if(sportTypeList != null && !sportTypeList.isEmpty()){
			sportTypeBeanList = new ArrayList<SportTypeBean>();
			for (SportType sportType : sportTypeList) {
				SportTypeBean sportTypeBean= new SportTypeBean();
				sportTypeBean.setSportTypeId(sportType.getSportTypeId());
				sportTypeBean.setSportTypeName(sportType.getSportTypeName());
				sportTypeBeanList.add(sportTypeBean);
			}
		}
		return sportTypeBeanList;
		
	}
	
	public List<TeamTypeBean> getTeamTypes(TeamTypeBean requestTeamTypeBean){
		List<TeamTypeBean> teamTypeBeanList = null;
		TournamentHome tournamentHome = new TournamentHome(getSession());
		List<TeamType> teamTypeList = tournamentHome.findTeamType(requestTeamTypeBean);
		if(teamTypeList != null && !teamTypeList.isEmpty()){
			teamTypeBeanList = new ArrayList<TeamTypeBean>();
			for (TeamType teamType : teamTypeList) {
				TeamTypeBean teamTypeBean= new TeamTypeBean();
				teamTypeBean.setTeamTypeId(teamType.getTeamTypeId());
				teamTypeBean.setTeamTypeName(teamType.getTeamTypeName());
				teamTypeBeanList.add(teamTypeBean);
			}
		}
		return teamTypeBeanList;
		
	}

	@Override
	public CountryBean createCountry(CountryBean countryBean) throws PTWException{
		try{
			CountryHome countryHome = new CountryHome(getSession());
			List<Country> dbCountries = countryHome.findCountryByFilter(countryBean);
			if(dbCountries != null && !dbCountries.isEmpty()){
				throw new PTWException(PTWConstants.ERROR_CODE_COUNTRY_DUPLICATE, PTWConstants.ERROR_DESC_COUNTRY_DUPLICATE);
			}
			Country country = new Country();
			country.setCountryName(countryBean.getCountryName());
			country.setCountryShortName(countryBean.getCountryShortName());
			countryHome.persist(country);
			countryBean.setCountryId(country.getCountryId());
			return countryBean;
		}catch(PTWException exception){
			throw exception;
		}catch(Exception exception){
			exception.printStackTrace();
			throw new PTWException(PTWConstants.ERROR_CODE_DB_EXCEPTION, PTWConstants.ERROR_DESC_DB_EXCEPTION);
		}
	}

	@Override
	public List<CountryBean> getCountryList(CountryBean countryBean) throws PTWException{
		CountryHome countryHome = new CountryHome(getSession());
		try{
			List<CountryBean> retCountryBeanList = null;
			
			List<Country> dbCountries = countryHome.findCountryByFilter(countryBean);
			if(dbCountries != null && !dbCountries.isEmpty()){
				retCountryBeanList = new ArrayList<CountryBean>();
				for (Country country : dbCountries) {
					CountryBean retCountryBean = new CountryBean();
					retCountryBean.setCountryId(country.getCountryId());
					retCountryBean.setCountryName(country.getCountryName());
					retCountryBean.setCountryShortName(country.getCountryShortName());
					retCountryBeanList.add(retCountryBean);
				}
			}
			return retCountryBeanList;
		}catch(Exception exception){
			exception.printStackTrace();
			throw new PTWException(PTWConstants.ERROR_CODE_DB_EXCEPTION, PTWConstants.ERROR_DESC_DB_EXCEPTION);
		}
		
	}

	@Override
	public void updateCountry(CountryBean countryBean) throws PTWException {
		try{
			CountryHome countryHome = new CountryHome(getSession());
			Country foundCountry = countryHome.findById(countryBean.getCountryId());
			if(foundCountry == null){
				throw new PTWException(PTWConstants.ERROR_CODE_COUNTRY_ID_NOT_FOUND, PTWConstants.ERROR_DESC_COUNTRY_ID_NOT_FOUND);
			}
			List<Country> dbCountries = countryHome.findCountryByFilter(countryBean);
			if(dbCountries != null && !dbCountries.isEmpty()){
				Country existingCountry = dbCountries.get(0);
				if(!existingCountry.getCountryId().equals(foundCountry.getCountryId())){
					throw new PTWException(PTWConstants.ERROR_CODE_COUNTRY_DUPLICATE, PTWConstants.ERROR_DESC_COUNTRY_DUPLICATE);
				}
			}
			foundCountry.setCountryName(countryBean.getCountryName());
			foundCountry.setCountryShortName(countryBean.getCountryShortName());
			countryHome.merge(foundCountry);
		}catch(PTWException exception){
			throw exception;
		}catch(Exception exception){
			exception.printStackTrace();
			throw new PTWException(PTWConstants.ERROR_CODE_DB_EXCEPTION, PTWConstants.ERROR_DESC_DB_EXCEPTION);
		}
	}

	@Override
	public void deleteCountry(CountryBean countryBean) throws PTWException {
		CountryHome countryHome = new CountryHome(getSession());
		Country foundCountry = countryHome.findById(countryBean.getCountryId());
		if(foundCountry == null){
			throw new PTWException(PTWConstants.ERROR_CODE_COUNTRY_ID_NOT_FOUND, PTWConstants.ERROR_DESC_COUNTRY_ID_NOT_FOUND);
		}
		countryHome.remove(foundCountry);
		
	}

	@Override
	public TeamBean createTeam(TeamBean teamBean) throws PTWException{
		try{
			TeamHome teamHome = new TeamHome(getSession());
			
			Team team = new Team();
			team.setTeamName(teamBean.getTeamName());
			team.setTeamShortName(teamBean.getTeamShortName());
			
			Country country = new Country();
			country.setCountryId(teamBean.getCountryId());
			team.setCountry(country);
			
			TeamType teamType = new TeamType();
			teamType.setTeamTypeId(teamBean.getTeamTypeId());
			team.setTeamType(teamType);
			
			SportType sportType = new SportType();
			sportType.setSportTypeId(teamBean.getSportTypeId());
			team.setSportType(sportType);
			
			teamHome.persist(team);
			
			teamBean.setTeamId(team.getTeamId());
			return teamBean;
		}catch(Exception exception){
			exception.printStackTrace();
			throw new PTWException(PTWConstants.ERROR_CODE_DB_EXCEPTION, PTWConstants.ERROR_DESC_DB_EXCEPTION);
		}
	}

	@Override
	public List<TeamBean> getTeamList(TeamBean teamBean) throws PTWException{
		TeamHome teamHome = new TeamHome(getSession());
		try{
			List<TeamBean> retTeamBeanList = null;
			List<Team> dbTeams = teamHome.findTeamsByFilter(teamBean);
			if(dbTeams != null && !dbTeams.isEmpty()){
				retTeamBeanList = new ArrayList<TeamBean>();
				for (Team team : dbTeams) {
					TeamBean retTeamBean = new TeamBean();
					retTeamBean.setTeamId(team.getTeamId());
					retTeamBean.setTeamName(team.getTeamName());
					retTeamBean.setTeamShortName(team.getTeamShortName());
					retTeamBean.setCountryId(team.getCountry().getCountryId());
					retTeamBean.setCountryName(team.getCountry().getCountryName());
					retTeamBean.setSportTypeId(team.getSportType().getSportTypeId());
					retTeamBean.setSportTypeName(team.getSportType().getSportTypeName());
					retTeamBean.setTeamTypeId(team.getTeamType().getTeamTypeId());
					retTeamBean.setTeamTypeName(team.getTeamType().getTeamTypeName());
					retTeamBeanList.add(retTeamBean);
				}
			}
			return retTeamBeanList;
		}catch(Exception exception){
				throw new PTWException(PTWConstants.ERROR_CODE_DB_EXCEPTION, PTWConstants.ERROR_DESC_DB_EXCEPTION);
		}
		
	}

	@Override
	public void updateTeam(TeamBean teamBean) throws PTWException {
		try{
			TeamHome teamHome = new TeamHome(getSession());
			Team foundTeam = teamHome.findById(teamBean.getTeamId());
			if(foundTeam == null){
				throw new PTWException(PTWConstants.ERROR_CODE_TEAM_ID_NOT_FOUND, PTWConstants.ERROR_DESC_TEAM_ID_NOT_FOUND);
			}
			foundTeam.setTeamName(teamBean.getTeamName());
			foundTeam.setTeamShortName(teamBean.getTeamShortName());
			
			if(!StringUtil.isEmptyNull(teamBean.getCountryId())){
				Country country = new Country();
				country.setCountryId(teamBean.getCountryId());
				foundTeam.setCountry(country);
			}
			
			if(!StringUtil.isEmptyNull(teamBean.getSportTypeId())){
				SportType sportType = new SportType();
				sportType.setSportTypeId(teamBean.getSportTypeId());
				foundTeam.setSportType(sportType);
			}
			
			if(!StringUtil.isEmptyNull(teamBean.getTeamTypeId())){
				TeamType teamType = new TeamType();
				teamType.setTeamTypeId(teamBean.getTeamTypeId());
				foundTeam.setTeamType(teamType);
			}
			
			teamHome.merge(foundTeam);
		}catch(PTWException exception){
			throw exception;
		}catch(Exception exception){
			exception.printStackTrace();
			throw new PTWException(PTWConstants.ERROR_CODE_DB_EXCEPTION, PTWConstants.ERROR_DESC_DB_EXCEPTION);
		}
	}

	@Override
	public void deleteTeam(TeamBean teamBean) throws PTWException {
		TeamHome teamHome = new TeamHome(getSession());
		Team foundTeam = teamHome.findById(teamBean.getTeamId());
		if(foundTeam == null){
			throw new PTWException(PTWConstants.ERROR_CODE_TEAM_ID_NOT_FOUND, PTWConstants.ERROR_DESC_TEAM_ID_NOT_FOUND);
		}
		teamHome.remove(foundTeam);
		
	}
	
	@Override
	public PlayerBean createPlayer(PlayerBean playerBean) throws PTWException{
		try{
			PlayerHome playerHome = new PlayerHome(getSession());
			
			Player player = new Player();
			player.setFirstName(playerBean.getFirstName());
			player.setLastName(playerBean.getLastName());
			
			Country country = new Country();
			country.setCountryId(playerBean.getCountryId());
			player.setCountry(country);
			
			SportType sportType = new SportType();
			sportType.setSportTypeId(playerBean.getSportTypeId());
			player.setSportType(sportType);
			
			playerHome.persist(player);
			
			playerBean.setPlayerId(player.getPlayerId());
			
			return playerBean;
		}catch(Exception exception){
			exception.printStackTrace();
			throw new PTWException(PTWConstants.ERROR_CODE_DB_EXCEPTION, PTWConstants.ERROR_DESC_DB_EXCEPTION);
		}
	}

	@Override
	public List<PlayerBean> getPlayerList(PlayerBean playerBean) throws PTWException{
		PlayerHome playerHome = new PlayerHome(getSession());
		try{
			List<PlayerBean> retPlayerBeanList = null;
			List<Player> dbPlayers = playerHome.findPlayerByFilter(playerBean);
			if(dbPlayers != null && !dbPlayers.isEmpty()){
				retPlayerBeanList = new ArrayList<PlayerBean>();
				for (Player team : dbPlayers) {
					PlayerBean retPlayerBean = new PlayerBean();
					retPlayerBean.setPlayerId(team.getPlayerId());
					retPlayerBean.setFirstName(team.getFirstName());
					retPlayerBean.setLastName(team.getLastName());
					retPlayerBean.setCountryId(team.getCountry().getCountryId());
					retPlayerBean.setCountryName(team.getCountry().getCountryName());
					retPlayerBean.setSportTypeId(team.getSportType().getSportTypeId());
					retPlayerBean.setSportTypeName(team.getSportType().getSportTypeName());
					retPlayerBeanList.add(retPlayerBean);
				}
			}
			return retPlayerBeanList;
		}catch(Exception exception){
				throw new PTWException(PTWConstants.ERROR_CODE_DB_EXCEPTION, PTWConstants.ERROR_DESC_DB_EXCEPTION);
		}
		
	}

	@Override
	public void updatePlayer(PlayerBean playerBean) throws PTWException {
		try{
			PlayerHome playerHome = new PlayerHome(getSession());
			Player foundPlayer = playerHome.findById(playerBean.getPlayerId());
			if(foundPlayer == null){
				throw new PTWException(PTWConstants.ERROR_CODE_PLAYER_ID_NOT_FOUND, PTWConstants.ERROR_DESC_PLAYER_ID_NOT_FOUND);
			}
			foundPlayer.setFirstName(playerBean.getFirstName());
			foundPlayer.setLastName(playerBean.getLastName());
			
			if(!StringUtil.isEmptyNull(playerBean.getCountryId())){
				Country country = new Country();
				country.setCountryId(playerBean.getCountryId());
				foundPlayer.setCountry(country);
			}
			
			if(!StringUtil.isEmptyNull(playerBean.getSportTypeId())){
				SportType sportType = new SportType();
				sportType.setSportTypeId(playerBean.getSportTypeId());
				foundPlayer.setSportType(sportType);
			}
			
			playerHome.merge(foundPlayer);
		}catch(PTWException exception){
			throw exception;
		}catch(Exception exception){
			exception.printStackTrace();
			throw new PTWException(PTWConstants.ERROR_CODE_DB_EXCEPTION, PTWConstants.ERROR_DESC_DB_EXCEPTION);
		}
	}

	@Override
	public void deletePlayer(PlayerBean playerBean) throws PTWException {
		try{
			PlayerHome playerHome = new PlayerHome(getSession());
			Player foundPlayer = playerHome.findById(playerBean.getPlayerId());
			if(foundPlayer == null){
				throw new PTWException(PTWConstants.ERROR_CODE_PLAYER_ID_NOT_FOUND, PTWConstants.ERROR_DESC_PLAYER_ID_NOT_FOUND);
			}
			playerHome.remove(foundPlayer);
		}catch(Exception exception){
			exception.printStackTrace();
			throw new PTWException(PTWConstants.ERROR_CODE_DB_EXCEPTION, PTWConstants.ERROR_DESC_DB_EXCEPTION);
		}
	}

	@Override
	public void addCountryToSport(SportTypeCountryList sportTypeCountryList) throws PTWException {
		try{
			SportTypeHome sportTypeHome = new SportTypeHome(getSession());
			for (Integer countryId : sportTypeCountryList.getCountryIdList()) {
				CountrySportTypeMapping mapping = new CountrySportTypeMapping();
				CountrySportTypeMappingKey key = new CountrySportTypeMappingKey();
				key.setCountryId(countryId);
				key.setSportTypeId(sportTypeCountryList.getSportTypeId());
				mapping.setCountrySportTypeMappingKey(key);
				sportTypeHome.persist(mapping);
			}
			
		}catch(Exception exception){
			exception.printStackTrace();
			throw new PTWException(PTWConstants.ERROR_CODE_DB_EXCEPTION, PTWConstants.ERROR_DESC_DB_EXCEPTION);
		}
		
	}
	
	@Override
	public void removeCountryFromSport(SportTypeCountryList sportTypeCountryList) throws PTWException {
		try{
			SportTypeHome sportTypeHome = new SportTypeHome(getSession());
			for (Integer countryId : sportTypeCountryList.getCountryIdList()) {
				CountrySportTypeMapping mapping = new CountrySportTypeMapping();
				CountrySportTypeMappingKey key = new CountrySportTypeMappingKey();
				key.setCountryId(countryId);
				key.setSportTypeId(sportTypeCountryList.getSportTypeId());
				mapping.setCountrySportTypeMappingKey(key);
				sportTypeHome.remove(mapping);
			}
			
		}catch(Exception exception){
			exception.printStackTrace();
			throw new PTWException(PTWConstants.ERROR_CODE_DB_EXCEPTION, PTWConstants.ERROR_DESC_DB_EXCEPTION);
		}
		
	}
	
	@Override
	public SportTypeCountryList getCountriesForSport(SportTypeCountryList sportTypeCountryList) throws PTWException{
		SportTypeCountryList retSportTypeCountryList = null;
		SportTypeHome sportTypeHome = new SportTypeHome(getSession());
		List<CountrySportTypeMapping>  dbCountries = sportTypeHome.getCountrySportTypeMappings(sportTypeCountryList.getSportTypeId());
		if(dbCountries != null && !dbCountries.isEmpty()){
			retSportTypeCountryList = new SportTypeCountryList();
			retSportTypeCountryList.setSportTypeId(sportTypeCountryList.getSportTypeId());
			List<Integer> countries = new ArrayList<Integer>();
			for (CountrySportTypeMapping countrySportTypeMapping : dbCountries) {
				countries.add(countrySportTypeMapping.getCountrySportTypeMappingKey().getCountryId());
			}
			retSportTypeCountryList.setCountryIdList(countries);
		}
		return retSportTypeCountryList;
	}

	
}
