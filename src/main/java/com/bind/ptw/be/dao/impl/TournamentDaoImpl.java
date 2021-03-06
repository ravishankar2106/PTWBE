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
import com.bind.ptw.be.dto.TeamPlayerBean;
import com.bind.ptw.be.dto.TeamPlayerList;
import com.bind.ptw.be.dto.TeamTypeBean;
import com.bind.ptw.be.dto.TournTeamPlayerBeanList;
import com.bind.ptw.be.dto.TournamentBean;
import com.bind.ptw.be.dto.TournamentTAndCBean;
import com.bind.ptw.be.dto.TournamentTeamBean;
import com.bind.ptw.be.dto.TournamentTeamBeanList;
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
import com.bind.ptw.be.entities.TeamPlayerHome;
import com.bind.ptw.be.entities.TeamPlayerMapping;
import com.bind.ptw.be.entities.TeamPlayerMappingKey;
import com.bind.ptw.be.entities.TeamType;
import com.bind.ptw.be.entities.TermsAndConditionHome;
import com.bind.ptw.be.entities.TermsCondition;
import com.bind.ptw.be.entities.TournTeamPlayer;
import com.bind.ptw.be.entities.TournTeamPlayerHome;
import com.bind.ptw.be.entities.Tournament;
import com.bind.ptw.be.entities.TournamentHome;
import com.bind.ptw.be.entities.TournamentTeam;
import com.bind.ptw.be.entities.TournamentTeamHome;
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
		tournamentEntity.setPublishDate(tournament.getPublishDate());
		
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
				resultTournamentBean.setPublishDate(tournament.getPublishDate());
				resultTournamentBean.setPublishDateStr(StringUtil.convertDateToString(tournament.getPublishDate()));
				resultTournamentBean.setSportTypeId(tournament.getSportTypeId());
				resultTournamentBean.setTeamTypeId(tournament.getTeamTypeId());
				resultTournamentBean.setTournamentVenue(tournament.getTournamentVenue());
				resultTournamentBean.setTocId(tournament.getTocGroupId());
				resultTournamentBean.setIconName(tournament.getTournamentIconName());
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
		dbTournament.setPublishDate(tournamentBean.getPublishDate());
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
		SportTypeHome sportTypeHome = new SportTypeHome(getSession());
		List<SportType> sportTypeList = sportTypeHome.findSportType(requestSportTypeBean);
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
		TeamPlayerHome teamPlayerHome = new TeamPlayerHome(getSession());
		List<TeamType> teamTypeList = teamPlayerHome.findTeamType(requestTeamTypeBean);
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
					retTeamBean.setTeamIconName(team.getTeamIconName());
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
			for (CountryBean countryBean : sportTypeCountryList.getCountryBeanList()) {
				CountrySportTypeMapping mapping = new CountrySportTypeMapping();
				CountrySportTypeMappingKey key = new CountrySportTypeMappingKey();
				key.setCountryId(countryBean.getCountryId());
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
			for (CountryBean countryBean : sportTypeCountryList.getCountryBeanList()) {
				CountrySportTypeMapping mapping = new CountrySportTypeMapping();
				CountrySportTypeMappingKey key = new CountrySportTypeMappingKey();
				key.setCountryId(countryBean.getCountryId());
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
		CountryHome countryHome = new CountryHome(getSession());
		List<CountrySportTypeMapping>  dbCountries = sportTypeHome.getCountrySportTypeMappings(sportTypeCountryList.getSportTypeId());
		if(dbCountries != null && !dbCountries.isEmpty()){
			retSportTypeCountryList = new SportTypeCountryList();
			retSportTypeCountryList.setSportTypeId(sportTypeCountryList.getSportTypeId());
			
			Integer[] countryIdList = new Integer[dbCountries.size()];
			int counter = 0;
			for (CountrySportTypeMapping countrySportTypeMapping : dbCountries) {
				countryIdList[counter++] = countrySportTypeMapping.getCountrySportTypeMappingKey().getCountryId();
			}
			List<Country> countryList = countryHome.findCountryByIds(countryIdList);
			if(countryList!= null && !countryList.isEmpty()){
				List<CountryBean> countries = new ArrayList<CountryBean>();
				for (Country country : countryList) {
					CountryBean countryBean = new CountryBean();
					countryBean.setCountryId(country.getCountryId());
					countryBean.setCountryName(country.getCountryName());
					countryBean.setCountryShortName(country.getCountryShortName());
					countries.add(countryBean);
				}
				retSportTypeCountryList.setCountryBeanList(countries);
			}
			
		}
		return retSportTypeCountryList;
	}

	@Override
	public void addPlayerToTeam(TeamPlayerList teamPlayerList) throws PTWException {
		try{
			TeamPlayerHome teamPlayerHome = new TeamPlayerHome(getSession());
			for (Integer playerId : teamPlayerList.getPlayerIdList()) {
				TeamPlayerMapping mapping = new TeamPlayerMapping();
				TeamPlayerMappingKey key = new TeamPlayerMappingKey();
				key.setPlayerId(playerId);
				key.setTeamId(teamPlayerList.getTeamId());
				mapping.setTeamPlayerMappingKey(key);
				teamPlayerHome.persist(mapping);
			}
			
		}catch(Exception exception){
			exception.printStackTrace();
			throw new PTWException(PTWConstants.ERROR_CODE_DB_EXCEPTION, PTWConstants.ERROR_DESC_DB_EXCEPTION);
		}
		
	}
	
	@Override
	public void removePlayerFromTeam(TeamPlayerList teamPlayerList) throws PTWException {
		try{
			TeamPlayerHome teamPlayerHome = new TeamPlayerHome(getSession());
			for (Integer playerId : teamPlayerList.getPlayerIdList()) {
				TeamPlayerMapping mapping = new TeamPlayerMapping();
				TeamPlayerMappingKey key = new TeamPlayerMappingKey();
				key.setPlayerId(playerId);
				key.setTeamId(teamPlayerList.getTeamId());
				mapping.setTeamPlayerMappingKey(key);
				teamPlayerHome.remove(mapping);
			}
			
		}catch(Exception exception){
			exception.printStackTrace();
			throw new PTWException(PTWConstants.ERROR_CODE_DB_EXCEPTION, PTWConstants.ERROR_DESC_DB_EXCEPTION);
		}
		
	}
	
	@Override
	public TeamPlayerList getPlayersForTeam(TeamPlayerList teamPlayerList) throws PTWException{
		TeamPlayerList retTeamPlayerList = new TeamPlayerList();
		retTeamPlayerList.setTeamId(teamPlayerList.getTeamId());
		TeamPlayerHome teamPlayerHome = new TeamPlayerHome(getSession());
		List<TeamPlayerMapping>  dbPlayers = teamPlayerHome.getTeamPlayers(teamPlayerList.getTeamId());
		if(dbPlayers != null && !dbPlayers.isEmpty()){
			List<Integer> players = new ArrayList<Integer>();
			for (TeamPlayerMapping teamPlayerMapping : dbPlayers) {
				players.add(teamPlayerMapping.getTeamPlayerMappingKey().getPlayerId());
			}
			retTeamPlayerList.setPlayerIdList(players);
		}
		return retTeamPlayerList;
	}
	
	@Override
	public void addTeamToTournament(TournamentTeamBeanList tournamentTeamBeanList) throws PTWException {
		try{
			TournamentTeamHome tournamentTeamHome = new TournamentTeamHome(getSession());
			Integer tournamentId = tournamentTeamBeanList.getTournamentId();
			List<TournamentTeamBean> teamList = tournamentTeamBeanList.getTournamentTeamBeanList();
			for (TournamentTeamBean teamBean : teamList) {
				TournamentTeam tournamentTeam = new TournamentTeam();
				Tournament tournament = new Tournament();
				tournament.setTournamentId(tournamentId);
				tournamentTeam.setTournament(tournament);
				
				Team team = new Team();
				team.setTeamId(teamBean.getTeamId());
				tournamentTeam.setTeam(team);
				
				tournamentTeamHome.persist(tournamentTeam);
			}
			
		}catch(Exception exception){
			exception.printStackTrace();
			throw new PTWException(PTWConstants.ERROR_CODE_DB_EXCEPTION, PTWConstants.ERROR_DESC_DB_EXCEPTION);
		}
		
	}
	
	
	
	@Override
	public List<TournamentTeamBean> getTeamsForTournament(TournamentBean tournamentBean) throws PTWException{
		TournamentTeamHome tournamentTeamHome = new TournamentTeamHome(getSession());
		List<TournamentTeamBean> teamBeanList = null;
		List<TournamentTeam>  dbTeams = tournamentTeamHome.findTeamsByTournament(tournamentBean);
		if(dbTeams != null && !dbTeams.isEmpty()){
			teamBeanList = new ArrayList<TournamentTeamBean>();
			for (TournamentTeam tournamentTeam : dbTeams) {
				TournamentTeamBean tournamentTeamBean = new TournamentTeamBean();
				tournamentTeamBean.setTournamentTeamId(tournamentTeam.getTournamentTeamId());
				
				Team team = tournamentTeam.getTeam();
				tournamentTeamBean.setTeamId(team.getTeamId());
				tournamentTeamBean.setTeamName(team.getTeamName());
				tournamentTeamBean.setTeamShortName(team.getTeamShortName());
				tournamentTeamBean.setTeamIconName(team.getTeamIconName());
				teamBeanList.add(tournamentTeamBean);
			}
		}
		return teamBeanList;
	}
	
	@Override
	public void removeTeamFromTournament(TournamentTeamBeanList tournamentTeamBeanList) throws PTWException {
		try{
			TournamentTeamHome tournamentTeamHome = new TournamentTeamHome(getSession());
			for (TournamentTeamBean teamBean : tournamentTeamBeanList.getTournamentTeamBeanList()) {
				TournamentTeam tournamentTeam = tournamentTeamHome.findById(teamBean.getTournamentTeamId());
				if(tournamentTeam == null){
					throw new PTWException(PTWConstants.ERROR_CODE_TEAM_ID_NOT_FOUND, PTWConstants.ERROR_DESC_TEAM_ID_NOT_FOUND);
				}
				tournamentTeamHome.remove(tournamentTeam);
			}
			
		}catch(PTWException exception){
			throw exception;
		}catch(Exception exception){
			exception.printStackTrace();
			throw new PTWException(PTWConstants.ERROR_CODE_DB_EXCEPTION, PTWConstants.ERROR_DESC_DB_EXCEPTION);
		}
		
	}
	
	@Override
	public void addPlayerToTournamentTeam(TournTeamPlayerBeanList tournTeamPlayerBeanList) throws PTWException {
		try{
			TournTeamPlayerHome tourTeamPlayerHome = new TournTeamPlayerHome(getSession());
			for (TeamPlayerBean playerBean : tournTeamPlayerBeanList.getTeamPlayerBeanList()) {
				TournTeamPlayer tourTeamPlayer = new TournTeamPlayer();
				Player player = new Player();
				player.setPlayerId(playerBean.getPlayerBean().getPlayerId());
				tourTeamPlayer.setPlayer(player);
				
				TournamentTeam tournamentTeam = new TournamentTeam();
				tournamentTeam.setTournamentTeamId(tournTeamPlayerBeanList.getTournamentTeamId());
				tourTeamPlayer.setTournamentTeam(tournamentTeam);
				tourTeamPlayerHome.persist(tourTeamPlayer);
			}
			
		}catch(Exception exception){
			exception.printStackTrace();
			throw new PTWException(PTWConstants.ERROR_CODE_DB_EXCEPTION, PTWConstants.ERROR_DESC_DB_EXCEPTION);
		}
		
	}
	
	@Override
	public void removePlayerFromTournamentTeam(TournTeamPlayerBeanList tournTeamPlayerBeanList) throws PTWException {
		try{
			TournTeamPlayerHome tourTeamPlayerHome = new TournTeamPlayerHome(getSession());
			for (TeamPlayerBean playerBean : tournTeamPlayerBeanList.getTeamPlayerBeanList()) {
				TournTeamPlayer tourTeamPlayer = new TournTeamPlayer();
				tourTeamPlayer.setTournTeamPlayerId(playerBean.getTourTeamPlayerId());
				tourTeamPlayerHome.remove(tourTeamPlayer);
			}
			
		}catch(Exception exception){
			exception.printStackTrace();
			throw new PTWException(PTWConstants.ERROR_CODE_DB_EXCEPTION, PTWConstants.ERROR_DESC_DB_EXCEPTION);
		}
		
	}
	
	@Override
	public List<TeamPlayerBean> getPlayersForTournamentTeam(TournamentTeamBean tournamentTeamBean) throws PTWException{
		TournTeamPlayerHome tournTeamPlayerHome = new TournTeamPlayerHome(getSession());
		List<TournTeamPlayer>  dbPlayers = tournTeamPlayerHome.getTournTeamPlayers(tournamentTeamBean.getTournamentTeamId());
		List<TeamPlayerBean> players = null;
		if(dbPlayers != null && !dbPlayers.isEmpty()){
			players = new ArrayList<TeamPlayerBean>();
			for (TournTeamPlayer tourTeamPlayers : dbPlayers) {
				TeamPlayerBean teamPlayerBean = new TeamPlayerBean();
				teamPlayerBean.setTourTeamPlayerId(tourTeamPlayers.getTournTeamPlayerId());
				
				PlayerBean playerBean = new PlayerBean();
				Player player = tourTeamPlayers.getPlayer();
				playerBean.setPlayerId(player.getPlayerId());
				playerBean.setFirstName(player.getFirstName());
				playerBean.setLastName(player.getLastName());
				teamPlayerBean.setPlayerBean(playerBean);
				players.add(teamPlayerBean);
			}
		}
		return players;
	}

	@Override
	public void createTOC(TournamentTAndCBean tocBean) throws PTWException{
		TermsAndConditionHome tocHome = new TermsAndConditionHome(getSession());
		try{
			int newMax = 0;
			if(StringUtil.isEmptyNull(tocBean.getGroupId())){
				Integer currentMax = tocHome.getMaxTOCGroupId();
				if(currentMax == null){
					currentMax = 0;
				}
				newMax = currentMax+1;
				
			}else{
				newMax = 1;
			}
			for(String tocText: tocBean.getTermsText()){
				TermsCondition toc = new TermsCondition();
				toc.setTocGroupId(newMax);
				toc.setTocText(tocText);
				tocHome.persist(toc);
			}
			TournamentHome tournamentHome = new TournamentHome(this.getSession());
			Tournament tournament = tournamentHome.findById(tocBean.getTournamentId());
			tournament.setTocGroupId(newMax);
			tournamentHome.merge(tournament);
		}catch(Exception exception){
			exception.printStackTrace();
			throw new PTWException(PTWConstants.ERROR_CODE_DB_EXCEPTION, PTWConstants.ERROR_DESC_DB_EXCEPTION);
		}
		
	}
	
	
}
