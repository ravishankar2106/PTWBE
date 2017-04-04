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
import com.bind.ptw.be.dto.SportTypeCountryList;
import com.bind.ptw.be.dto.TermsBean;
import com.bind.ptw.be.dto.TeamBean;
import com.bind.ptw.be.dto.TeamBeanList;
import com.bind.ptw.be.dto.TeamPlayerBean;
import com.bind.ptw.be.dto.TeamPlayerList;
import com.bind.ptw.be.dto.TeamTypeBean;
import com.bind.ptw.be.dto.TeamTypeBeanList;
import com.bind.ptw.be.dto.TournTeamPlayerBeanList;
import com.bind.ptw.be.dto.TournamentBean;
import com.bind.ptw.be.dto.TournamentBeanList;
import com.bind.ptw.be.dto.TournamentTAndCBean;
import com.bind.ptw.be.dto.TournamentTeamBean;
import com.bind.ptw.be.dto.TournamentTeamBeanList;
import com.bind.ptw.be.entities.SportTypeBeanList;
import com.bind.ptw.be.services.TournamentService;
import com.bind.ptw.be.services.util.TournamentBeanValidator;
import com.bind.ptw.be.util.PTWConstants;
import com.bind.ptw.be.util.PTWException;
import com.bind.ptw.be.util.StringUtil;

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
			TournamentBeanValidator.validateRequest(inputTournamentBean);
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
			TournamentBeanValidator.validateRequest(tournamentBean);
			TournamentBeanValidator.validateTournamentId(tournamentBean.getTournamentId());
			tournamentDao.deleteTournament(tournamentBean);
		}catch(PTWException exception){
			returnBean.setResultCode(exception.getCode());
			returnBean.setResultDescription(exception.getDescription());
		}
		return returnBean;
	}
	
	@Override
	public TournamentBeanList getTournamentList(TournamentBean tournamentBean) {
		TournamentBeanList tournamentBeanList = new TournamentBeanList();
		List<TournamentBean> tournaments = tournamentDao.getTournament(tournamentBean, false);
		tournamentBeanList.setTournamentList(tournaments);
		return tournamentBeanList;
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
			TournamentBeanValidator.validateRequest(tournamentBean);
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
			TournamentBeanValidator.validateRequest(tournamentBean);
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
			TournamentBeanValidator.validateRequest(countryBean);
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
			TournamentBeanValidator.validateRequest(countryBean);
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
			TournamentBeanValidator.validateRequest(countryBean);
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
			TournamentBeanValidator.validateRequest(teamBean);
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
			TournamentBeanValidator.validateRequest(teamBean);
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
			TournamentBeanValidator.validateRequest(teamBean);
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
			TournamentBeanValidator.validateRequest(playerBean);
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
			TournamentBeanValidator.validateRequest(playerBean);
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
			TournamentBeanValidator.validateRequest(playerBean);
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
			TournamentBeanValidator.validateRequest(sportTypeCountryList);
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
			TournamentBeanValidator.validateRequest(sportTypeCountryList);
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
			TournamentBeanValidator.validateRequest(sportTypeCountryList);
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
			TournamentBeanValidator.validateRequest(teamPlayerList);
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
			TournamentBeanValidator.validateRequest(teamPlayerList);
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
			TournamentBeanValidator.validateRequest(teamPlayerList);
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
			TournamentBeanValidator.validateRequest(tournamentTeamBeanList);
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
			TournamentBeanValidator.validateRequest(tournamentBean);
			TournamentBeanValidator.validateTournamentId(tournamentBean.getTournamentId());
			TournamentBeanValidator.validateTournament(tournamentBean.getTournamentId(), tournamentDao);
			returnBean.setTournamentId(tournamentBean.getTournamentId());
			List<TournamentTeamBean> teams = tournamentDao.getTeamsForTournament(tournamentBean);
			returnBean.setTournamentTeamBeanList(teams);
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
			TournamentBeanValidator.validateRequest(tournamentTeamBeanList);
			TournamentBeanValidator.validateTournamentTeamId(tournamentTeamBeanList);
			tournamentDao.removeTeamFromTournament(tournamentTeamBeanList);
		}catch(PTWException exception){
			returnBean.setResultCode(exception.getCode());
			returnBean.setResultDescription(exception.getDescription());
		}
		return returnBean;
	}
	
	@Override
	public BaseBean addPlayersToTournamentTeam(TournTeamPlayerBeanList tournTeamPlayerBeanList) {
		BaseBean returnBean = new BaseBean();
		try{
			TournamentBeanValidator.validateRequest(tournTeamPlayerBeanList);
			TournamentBeanValidator.validatePlayerToTournTeam(tournTeamPlayerBeanList, tournamentDao);
			tournamentDao.addPlayerToTournamentTeam(tournTeamPlayerBeanList);
		}catch(PTWException exception){
			returnBean.setResultCode(exception.getCode());
			returnBean.setResultDescription(exception.getDescription());
		}
		return returnBean;
	}
	
	@Override
	public TournTeamPlayerBeanList getPlayersForTournamentTeam(TournamentTeamBean tournamentTeamBean) {
		TournTeamPlayerBeanList returnBean = new TournTeamPlayerBeanList();
		try{
			TournamentBeanValidator.validateRequest(tournamentTeamBean);
			TournamentBeanValidator.validateTeamId(tournamentTeamBean.getTournamentTeamId());
			List<TeamPlayerBean> dbBean = tournamentDao.getPlayersForTournamentTeam(tournamentTeamBean);
			returnBean.setTeamPlayerBeanList(dbBean);
		}catch(PTWException exception){
			returnBean.setResultCode(exception.getCode());
			returnBean.setResultDescription(exception.getDescription());
		}
		return returnBean;
	}

	@Override
	public BaseBean removePlayersFromTournament(TournTeamPlayerBeanList tournTeamPlayerBeanList) {
		BaseBean returnBean = new BaseBean();
		try{
			TournamentBeanValidator.validateRequest(tournTeamPlayerBeanList);
			TournamentBeanValidator.validateTeamId(tournTeamPlayerBeanList.getTournamentTeamId());
			tournamentDao.removePlayerFromTournamentTeam(tournTeamPlayerBeanList);
		}catch(PTWException exception){
			returnBean.setResultCode(exception.getCode());
			returnBean.setResultDescription(exception.getDescription());
		}
		return returnBean;
	}

	@Override
	public BaseBean addTermsAndCondition(TournamentTAndCBean tocBean) {
		BaseBean returnBean = new BaseBean();
		try{
			if(tocBean == null ){
				throw new PTWException(PTWConstants.ERROR_CODE_INVALID_REQUEST, PTWConstants.ERROR_DESC_INVALID_REQUEST);
			}
			if(tocBean.getTermsText() == null || tocBean.getTermsText().isEmpty()){
				throw new PTWException(PTWConstants.ERROR_CODE_TOC_EMPTY, PTWConstants.ERROR_DESC_FIELD_EMPTY + " TOC Text");
			}	
			tournamentDao.createTOC(tocBean);
		}catch(PTWException exception){
			returnBean.setResultCode(exception.getCode());
			returnBean.setResultDescription(exception.getDescription());
		}
		return null;
	}

	
}
