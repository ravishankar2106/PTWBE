package com.bind.ptw.be.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bind.ptw.be.dao.TournamentDao;
import com.bind.ptw.be.dto.BaseBean;
import com.bind.ptw.be.dto.CountryBean;
import com.bind.ptw.be.dto.CountryBeanList;
import com.bind.ptw.be.dto.PlayerBean;
import com.bind.ptw.be.dto.PlayerBeanList;
import com.bind.ptw.be.dto.SportTypeBean;
import com.bind.ptw.be.dto.SportTypeBeanList;
import com.bind.ptw.be.dto.SportTypeCountryList;
import com.bind.ptw.be.dto.TeamBean;
import com.bind.ptw.be.dto.TeamBeanList;
import com.bind.ptw.be.dto.TeamPlayerList;
import com.bind.ptw.be.dto.TeamTypeBean;
import com.bind.ptw.be.dto.TeamTypeBeanList;
import com.bind.ptw.be.dto.TournamentBean;
import com.bind.ptw.be.dto.TournamentBeanList;
import com.bind.ptw.be.dto.TournamentTeamBeanList;
import com.bind.ptw.be.services.TournamentService;
import com.bind.ptw.be.services.util.TournamentBeanValidator;
import com.bind.ptw.be.util.PTWException;

@Service("tournamentService")
@Transactional
public class TournamentServiceImpl implements TournamentService{

	@Autowired
	TournamentDao tournamentDao;
	
	@Autowired
	HibernateTemplate hibernateTemplate;
	
	@Override
	public TournamentBean createTournament(TournamentBean inputTournamentBean){
		TournamentBean tournamentBeanResponse;
		try{
			TournamentBeanValidator.vaidateRequest(inputTournamentBean);
			TournamentBeanValidator.validateCreateTournament(inputTournamentBean, tournamentDao);
			tournamentBeanResponse = tournamentDao.createTournament(inputTournamentBean);
		}catch(PTWException exception){
			exception.printStackTrace();
			tournamentBeanResponse = new TournamentBean();
			tournamentBeanResponse.setResultCode(exception.getCode());
			tournamentBeanResponse.setResultDescription(exception.getDescription());
		}	
		return tournamentBeanResponse;
	}

	@Override
	public BaseBean deleteTournament(TournamentBean tournamentBean) {
		BaseBean returnBean = new BaseBean();
		try{
			TournamentBeanValidator.vaidateRequest(tournamentBean);
			TournamentBeanValidator.validateTournamentId(tournamentBean.getTournamentId());
			tournamentDao.deleteTournament(tournamentBean);
		}catch(PTWException exception){
			returnBean.setResultCode(exception.getCode());
			returnBean.setResultDescription(exception.getDescription());
		}
		return returnBean;
	}
	
	@Override
	public TournamentBeanList getOngoingTournament(TournamentBean tournamentBean){
		TournamentBeanList tournamentBeanList = new TournamentBeanList();
		List<TournamentBean> tournaments = tournamentDao.getTournament(tournamentBean, true);
		tournamentBeanList.setTournamentList(tournaments);
		return tournamentBeanList;
	}
	
	@Override
	public BaseBean updateTournament(TournamentBean tournamentBean) {
		BaseBean returnBean = new BaseBean();
		try{
			TournamentBeanValidator.vaidateRequest(tournamentBean);
			TournamentBeanValidator.validateUpdateTournament(tournamentBean, tournamentDao);
			tournamentDao.updateTournament(tournamentBean);
		}catch(PTWException exception){
			returnBean.setResultCode(exception.getCode());
			returnBean.setResultDescription(exception.getDescription());
		}
		return returnBean;
	}

	@Override
	public BaseBean archieveTournament(TournamentBean tournamentBean) {
		BaseBean returnBean = new BaseBean();
		try{
			TournamentBeanValidator.vaidateRequest(tournamentBean);
			TournamentBeanValidator.validateTournamentId(tournamentBean.getTournamentId());
			tournamentDao.archieveTournament(tournamentBean);
		}catch(PTWException exception){
			returnBean.setResultCode(exception.getCode());
			returnBean.setResultDescription(exception.getDescription());
		}
		return returnBean;
	}
	
	@Override
	public TeamTypeBeanList getTeamType() {
		TeamTypeBeanList teamTypeBeanList = new TeamTypeBeanList();
		List<TeamTypeBean> teamTypeList = tournamentDao.getTeamTypes(new TeamTypeBean());
		teamTypeBeanList.setTeamTypes(teamTypeList);
		return teamTypeBeanList;
	}

	@Override
	public SportTypeBeanList getSportType() {
		SportTypeBeanList sportTypeBeanResponse= new SportTypeBeanList();
		List<SportTypeBean> sportTypeBeanList = tournamentDao.getSportTypes(new SportTypeBean());
		sportTypeBeanResponse.setSportTypes(sportTypeBeanList);
		return sportTypeBeanResponse;
	}

	@Override
	public CountryBean createCountry(CountryBean countryBean) {
		CountryBean returnBean;
		try{
			TournamentBeanValidator.vaidateRequest(countryBean);
			TournamentBeanValidator.validateCreateCountryBean(countryBean);
			returnBean = tournamentDao.createCountry(countryBean); 
		}catch(PTWException exception){
			returnBean = new CountryBean();
			returnBean.setResultCode(exception.getCode());
			returnBean.setResultDescription(exception.getDescription());
		}
		return returnBean;
	}

	@Override
	public CountryBeanList getCountryList(CountryBean countryBean) {
		CountryBeanList returnBeanList = new CountryBeanList();
		try{
			List<CountryBean> countriesList = tournamentDao.getCountryList(countryBean);
			returnBeanList.setCountries(countriesList);
		}catch(PTWException exception){
			returnBeanList.setResultCode(exception.getCode());
			returnBeanList.setResultDescription(exception.getDescription());
		}
		return returnBeanList;
	}

	@Override
	public BaseBean updateCountry(CountryBean countryBean) {
		BaseBean returnBean = new BaseBean();
		try{
			TournamentBeanValidator.vaidateRequest(countryBean);
			TournamentBeanValidator.validateUpdateCountryBean(countryBean);
			tournamentDao.updateCountry(countryBean); 
		}catch(PTWException exception){
			returnBean.setResultCode(exception.getCode());
			returnBean.setResultDescription(exception.getDescription());
		}
		return returnBean;
	}

	@Override
	public BaseBean deleteCountry(CountryBean countryBean) {
		BaseBean returnBean = new BaseBean();
		try{
			TournamentBeanValidator.vaidateRequest(countryBean);
			TournamentBeanValidator.validateCountryId(countryBean.getCountryId());
			tournamentDao.deleteCountry(countryBean); 
		}catch(PTWException exception){
			returnBean.setResultCode(exception.getCode());
			returnBean.setResultDescription(exception.getDescription());
		}
		return returnBean;
	}

	@Override
	public TeamBean createTeam(TeamBean teamBean) {
		TeamBean returnBean;
		try{
			TournamentBeanValidator.vaidateRequest(teamBean);
			TournamentBeanValidator.validateCreateTeamBean(teamBean, tournamentDao);
			returnBean = tournamentDao.createTeam(teamBean); 
		}catch(PTWException exception){
			returnBean = new TeamBean();
			returnBean.setResultCode(exception.getCode());
			returnBean.setResultDescription(exception.getDescription());
		}
		return returnBean;
	}

	@Override
	public TeamBeanList getTeamList(TeamBean teamBean) {
		TeamBeanList returnBeanList = new TeamBeanList();
		try{
			List<TeamBean> teamList = tournamentDao.getTeamList(teamBean);
			returnBeanList.setTeams(teamList);
		}catch(PTWException exception){
			returnBeanList.setResultCode(exception.getCode());
			returnBeanList.setResultDescription(exception.getDescription());
		}
		return returnBeanList;
	}

	@Override
	public BaseBean updateTeam(TeamBean teamBean) {
		BaseBean returnBean = new BaseBean();
		try{
			TournamentBeanValidator.vaidateRequest(teamBean);
			TournamentBeanValidator.validateUpdateTeamBean(teamBean, tournamentDao);
			tournamentDao.updateTeam(teamBean); 
		}catch(PTWException exception){
			returnBean.setResultCode(exception.getCode());
			returnBean.setResultDescription(exception.getDescription());
		}
		return returnBean;
	}

	@Override
	public BaseBean deleteTeam(TeamBean teamBean) {
		BaseBean returnBean = new BaseBean();
		try{
			TournamentBeanValidator.vaidateRequest(teamBean);
			TournamentBeanValidator.validateTeamId(teamBean.getTeamId());
			tournamentDao.deleteTeam(teamBean); 
		}catch(PTWException exception){
			returnBean.setResultCode(exception.getCode());
			returnBean.setResultDescription(exception.getDescription());
		}
		return returnBean;
	}
	
	@Override
	public PlayerBean createPlayer(PlayerBean playerBean) {
		PlayerBean returnBean;
		try{
			TournamentBeanValidator.vaidateRequest(playerBean);
			TournamentBeanValidator.validateCreatePlayerBean(playerBean, tournamentDao);
			returnBean = tournamentDao.createPlayer(playerBean); 
		}catch(PTWException exception){
			returnBean = new PlayerBean();
			returnBean.setResultCode(exception.getCode());
			returnBean.setResultDescription(exception.getDescription());
		}
		return returnBean;
	}

	@Override
	public PlayerBeanList getPlayerList(PlayerBean playerBean) {
		PlayerBeanList returnBeanList = new PlayerBeanList();
		try{
			List<PlayerBean> playerList = tournamentDao.getPlayerList(playerBean);
			returnBeanList.setPlayers(playerList);
		}catch(PTWException exception){
			returnBeanList.setResultCode(exception.getCode());
			returnBeanList.setResultDescription(exception.getDescription());
		}
		return returnBeanList;
	}

	@Override
	public BaseBean updatePlayer(PlayerBean playerBean) {
		BaseBean returnBean = new BaseBean();
		try{
			TournamentBeanValidator.vaidateRequest(playerBean);
			TournamentBeanValidator.validateUpdatePlayerBean(playerBean, tournamentDao);
			tournamentDao.updatePlayer(playerBean); 
		}catch(PTWException exception){
			returnBean.setResultCode(exception.getCode());
			returnBean.setResultDescription(exception.getDescription());
		}
		return returnBean;
	}

	@Override
	public BaseBean deletePlayer(PlayerBean playerBean) {
		BaseBean returnBean = new BaseBean();
		try{
			TournamentBeanValidator.vaidateRequest(playerBean);
			TournamentBeanValidator.validatePlayerId(playerBean.getPlayerId());
			tournamentDao.deletePlayer(playerBean); 
		}catch(PTWException exception){
			returnBean.setResultCode(exception.getCode());
			returnBean.setResultDescription(exception.getDescription());
		}
		return returnBean;
	}

	@Override
	public BaseBean addCountriesToSportType(SportTypeCountryList sportTypeCountryList) {
		BaseBean returnBean = new BaseBean();
		try{
			TournamentBeanValidator.vaidateRequest(sportTypeCountryList);
			TournamentBeanValidator.validateCountriesToSportMapping(sportTypeCountryList, tournamentDao);
			tournamentDao.addCountryToSport(sportTypeCountryList);
		}catch(PTWException exception){
			returnBean.setResultCode(exception.getCode());
			returnBean.setResultDescription(exception.getDescription());
		}
		return returnBean;
	}
	
	@Override
	public SportTypeCountryList getCountriesForSportType(SportTypeCountryList sportTypeCountryList) {
		SportTypeCountryList returnBean = new SportTypeCountryList();
		try{
			TournamentBeanValidator.vaidateRequest(sportTypeCountryList);
			TournamentBeanValidator.validateCountriesForSportMapping(sportTypeCountryList, tournamentDao);
			returnBean = tournamentDao.getCountriesForSport(sportTypeCountryList);
		}catch(PTWException exception){
			returnBean.setResultCode(exception.getCode());
			returnBean.setResultDescription(exception.getDescription());
		}
		return returnBean;
	}

	@Override
	public BaseBean removeCountriesFromSportType(SportTypeCountryList sportTypeCountryList) {
		BaseBean returnBean = new BaseBean();
		try{
			TournamentBeanValidator.vaidateRequest(sportTypeCountryList);
			TournamentBeanValidator.validateCountriesToSportMapping(sportTypeCountryList, tournamentDao);
			tournamentDao.removeCountryFromSport(sportTypeCountryList);
		}catch(PTWException exception){
			returnBean.setResultCode(exception.getCode());
			returnBean.setResultDescription(exception.getDescription());
		}
		return returnBean;
	}
	
	@Override
	public BaseBean addPlayersToTeam(TeamPlayerList teamPlayerList) {
		BaseBean returnBean = new BaseBean();
		try{
			TournamentBeanValidator.vaidateRequest(teamPlayerList);
			TournamentBeanValidator.validatePlayersToTeam(teamPlayerList, tournamentDao);
			tournamentDao.addPlayerToTeam(teamPlayerList);
		}catch(PTWException exception){
			returnBean.setResultCode(exception.getCode());
			returnBean.setResultDescription(exception.getDescription());
		}
		return returnBean;
	}
	
	@Override
	public TeamPlayerList getPlayersForTeam(TeamPlayerList teamPlayerList) {
		TeamPlayerList returnBean = new TeamPlayerList();
		try{
			TournamentBeanValidator.vaidateRequest(teamPlayerList);
			TournamentBeanValidator.validatePlayersForTeam(teamPlayerList, tournamentDao);
			returnBean = tournamentDao.getPlayersForTeam(teamPlayerList);
		}catch(PTWException exception){
			returnBean.setResultCode(exception.getCode());
			returnBean.setResultDescription(exception.getDescription());
		}
		return returnBean;
	}

	@Override
	public BaseBean removePlayersFromTeam(TeamPlayerList teamPlayerList) {
		BaseBean returnBean = new BaseBean();
		try{
			TournamentBeanValidator.vaidateRequest(teamPlayerList);
			TournamentBeanValidator.validatePlayersToTeam(teamPlayerList, tournamentDao);
			tournamentDao.removePlayerFromTeam(teamPlayerList);
		}catch(PTWException exception){
			returnBean.setResultCode(exception.getCode());
			returnBean.setResultDescription(exception.getDescription());
		}
		return returnBean;
	}
	
	@Override
	public BaseBean addTeamsToTournament(TournamentTeamBeanList tournamentTeamBeanList) {
		BaseBean returnBean = new BaseBean();
		try{
			TournamentBeanValidator.vaidateRequest(tournamentTeamBeanList);
			TournamentBeanValidator.validateTeamsToTournament(tournamentTeamBeanList, tournamentDao);
			tournamentDao.addTeamToTournament(tournamentTeamBeanList);
		}catch(PTWException exception){
			returnBean.setResultCode(exception.getCode());
			returnBean.setResultDescription(exception.getDescription());
		}
		return returnBean;
	}
	
	@Override
	public TournamentTeamBeanList getTeamsForTournament(TournamentBean tournamentBean) {
		TournamentTeamBeanList returnBean = new TournamentTeamBeanList();
		try{
			TournamentBeanValidator.vaidateRequest(tournamentBean);
			TournamentBeanValidator.validateTournamentId(tournamentBean.getTournamentId());
			TournamentBeanValidator.validateTournament(tournamentBean.getTournamentId(), tournamentDao);
			returnBean = tournamentDao.getTeamsForTournament(tournamentBean);
		}catch(PTWException exception){
			returnBean.setResultCode(exception.getCode());
			returnBean.setResultDescription(exception.getDescription());
		}
		return returnBean;
	}

	@Override
	public BaseBean removeTeamsFromTournament(TournamentTeamBeanList tournamentTeamBeanList) {
		BaseBean returnBean = new BaseBean();
		try{
			TournamentBeanValidator.vaidateRequest(tournamentTeamBeanList);
			TournamentBeanValidator.validateTournamentTeamId(tournamentTeamBeanList);
			tournamentDao.removeTeamFromTournament(tournamentTeamBeanList);
		}catch(PTWException exception){
			returnBean.setResultCode(exception.getCode());
			returnBean.setResultDescription(exception.getDescription());
		}
		return returnBean;
	}
	
}
