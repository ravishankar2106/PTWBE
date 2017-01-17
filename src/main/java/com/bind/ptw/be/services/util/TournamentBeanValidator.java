package com.bind.ptw.be.services.util;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import com.bind.ptw.be.dao.TournamentDao;
import com.bind.ptw.be.dto.BaseBean;
import com.bind.ptw.be.dto.CountryBean;
import com.bind.ptw.be.dto.SportTypeBean;
import com.bind.ptw.be.dto.TeamBean;
import com.bind.ptw.be.dto.TeamTypeBean;
import com.bind.ptw.be.dto.TournamentBean;
import com.bind.ptw.be.dto.UserTournmentRegisterBean;
import com.bind.ptw.be.util.PTWConstants;
import com.bind.ptw.be.util.PTWException;
import com.bind.ptw.be.util.StringUtil;


public class TournamentBeanValidator {
	
	public static void vaidateRequest(BaseBean baseBean)throws PTWException{
		if(baseBean == null){
			throw new PTWException(PTWConstants.ERROR_CODE_INVALID_REQUEST, PTWConstants.ERROR_DESC_INVALID_REQUEST);
		}
	}
	
	public static void validateRegisterUserForTournament(UserTournmentRegisterBean request) throws PTWException{
		UserBeanValidator.validateUserId(request.getUserId());
		List<Integer> tournamentBeanList = request.getTournamentList();
		if(tournamentBeanList == null || tournamentBeanList.isEmpty() ){
			throw new PTWException(PTWConstants.ERROR_CODE_INVALID_REGISTRATION_REQUEST, PTWConstants.ERROR_DESC_INVALID_REGISTRATION_REQUEST);
		}
		for(Integer tournamentId : tournamentBeanList ){
			validateTournamentId(tournamentId);
		}
	}
	
	public static void validateTournamentId(Integer tournamentId) throws PTWException{
		if(StringUtil.isEmptyNull(tournamentId)){
			throw new PTWException(PTWConstants.ERROR_CODE_TOURNAMENT_ID_EMPTY, PTWConstants.ERROR_DESC_FIELD_EMPTY + "Tournament Id");
		}
	}

	public static void validateCreateTournament(TournamentBean inputTournamentBean, TournamentDao tournamentDao) throws PTWException {
		if(StringUtil.isEmptyNull(inputTournamentBean.getTournamentName())){
			throw new PTWException(PTWConstants.ERROR_CODE_TOURNAMENT_NAME_EMPTY, PTWConstants.ERROR_DESC_FIELD_EMPTY + "Tournament Name");
		}
		
		TournamentBean queryBean = new TournamentBean();
		queryBean.setTournamentName(inputTournamentBean.getTournamentName());
		List<TournamentBean> tournamentBeanList = tournamentDao.getTournament(queryBean, false);
		if(tournamentBeanList != null && !tournamentBeanList.isEmpty()){
			throw new PTWException(PTWConstants.ERROR_CODE_TOURNAMENT_NAME_DUPLICATE, PTWConstants.ERROR_DESC_TOURNAMENT_NAME_DUPLICATE);
		}
		
		if(!StringUtil.isEmptyNull(inputTournamentBean.getSportTypeId())){
			validateSportType(inputTournamentBean.getSportTypeId(), tournamentDao);
		}
		if(!StringUtil.isEmptyNull(inputTournamentBean.getTeamTypeId())){
			validateTeamType(inputTournamentBean.getTeamTypeId(), tournamentDao);
		}
		
		if(!StringUtil.isEmptyNull(inputTournamentBean.getStartDateStr())){
			try{
				Date startDate = StringUtil.convertStringToDate(inputTournamentBean.getStartDateStr());
				inputTournamentBean.setStartDate(startDate);
			}catch(ParseException exception){
				throw new PTWException(PTWConstants.ERROR_CODE_TOURNAMENT_START_DATE_INVALID, PTWConstants.ERROR_DESC_TOURNAMENT_START_DATE_INVALID);
			}
		}
		
		if(!StringUtil.isEmptyNull(inputTournamentBean.getEndDateStr())){
			if(inputTournamentBean.getStartDate() == null){
				throw new PTWException(PTWConstants.ERROR_CODE_TOURNAMENT_START_DATE_INVALID, PTWConstants.ERROR_DESC_TOURNAMENT_START_DATE_INVALID);
			}
			try{
				Date endDate = StringUtil.convertStringToDate(inputTournamentBean.getEndDateStr());
				if(endDate.before(inputTournamentBean.getStartDate())){
					throw new PTWException(PTWConstants.ERROR_CODE_TOURNAMENT_END_DATE_INVALID, PTWConstants.ERROR_DESC_TOURNAMENT_END_DATE_INVALID);
				}
				inputTournamentBean.setEndDate(endDate);
			}catch(ParseException exception){
				throw new PTWException(PTWConstants.ERROR_CODE_TOURNAMENT_END_DATE_INVALID, PTWConstants.ERROR_DESC_TOURNAMENT_END_DATE_INVALID);
			}
		}
		
	}
	
	public static void validateSportType(Integer sportTypeId, TournamentDao tournamentDao) throws PTWException{
		SportTypeBean requestSportTypeBean = new SportTypeBean();
		requestSportTypeBean.setSportTypeId(sportTypeId);
		List<SportTypeBean> sportTypeBeanList = tournamentDao.getSportTypes(requestSportTypeBean);
		if(sportTypeBeanList == null || sportTypeBeanList.isEmpty()){
			throw new PTWException(PTWConstants.ERROR_CODE_SPORT_TYPE_INVALID, PTWConstants.ERROR_DESC_SPORT_TYPE_INVALID);
		}
	}
	
	public static void validateTeamType(Integer teamTypeId, TournamentDao tournamentDao) throws PTWException{
		TeamTypeBean requestTeamTypeBean = new TeamTypeBean();
		requestTeamTypeBean.setTeamTypeId(teamTypeId);
		List<TeamTypeBean> teamTypeBeanList = tournamentDao.getTeamTypes(requestTeamTypeBean);
		if(teamTypeBeanList == null || teamTypeBeanList.isEmpty()){
			throw new PTWException(PTWConstants.ERROR_CODE_TEAM_TYPE_INVALID, PTWConstants.ERROR_DESC_TEAM_TYPE_INVALID);
		}
	}

	public static void validateUpdateTournament(TournamentBean tournamentBean, TournamentDao tournamentDao) throws PTWException {
		validateTournamentId(tournamentBean.getTournamentId());
		TournamentBean queryBean = new TournamentBean();
		queryBean.setTournamentName(tournamentBean.getTournamentName());
		List<TournamentBean> tournamentBeanList = tournamentDao.getTournament(queryBean, false);
		if(tournamentBeanList != null && !tournamentBeanList.isEmpty()){
			TournamentBean existingTournament = tournamentBeanList.get(0);
			if(!existingTournament.getTournamentId().equals(tournamentBean.getTournamentId())){
				throw new PTWException(PTWConstants.ERROR_CODE_TOURNAMENT_NAME_DUPLICATE, PTWConstants.ERROR_DESC_TOURNAMENT_NAME_DUPLICATE);
			}
		}
		if(!StringUtil.isEmptyNull(tournamentBean.getStartDateStr())){
			try{
				Date startDate = StringUtil.convertStringToDate(tournamentBean.getStartDateStr());
				tournamentBean.setStartDate(startDate);
			}catch(ParseException exception){
				throw new PTWException(PTWConstants.ERROR_CODE_TOURNAMENT_START_DATE_INVALID, PTWConstants.ERROR_DESC_TOURNAMENT_START_DATE_INVALID);
			}
		}
		
		if(!StringUtil.isEmptyNull(tournamentBean.getEndDateStr())){
			if(tournamentBean.getStartDate() == null){
				throw new PTWException(PTWConstants.ERROR_CODE_TOURNAMENT_START_DATE_INVALID, PTWConstants.ERROR_DESC_TOURNAMENT_START_DATE_INVALID);
			}
			try{
				Date endDate = StringUtil.convertStringToDate(tournamentBean.getEndDateStr());
				if(endDate.before(tournamentBean.getStartDate())){
					throw new PTWException(PTWConstants.ERROR_CODE_TOURNAMENT_END_DATE_INVALID, PTWConstants.ERROR_DESC_TOURNAMENT_END_DATE_INVALID);
				}
				tournamentBean.setEndDate(endDate);
			}catch(ParseException exception){
				throw new PTWException(PTWConstants.ERROR_CODE_TOURNAMENT_END_DATE_INVALID, PTWConstants.ERROR_DESC_TOURNAMENT_END_DATE_INVALID);
			}
		}
	}

	public static void validateCreateCountryBean(CountryBean countryBean) throws PTWException{
		if(StringUtil.isEmptyNull(countryBean.getCountryName())){
			throw new PTWException(PTWConstants.ERROR_CODE_COUNTRY_NAME_EMPTY, PTWConstants.ERROR_DESC_FIELD_EMPTY + "Country Name");
		}
		if(StringUtil.isEmptyNull(countryBean.getCountryShortName())){
			throw new PTWException(PTWConstants.ERROR_CODE_COUNTRY_SHORT_NAME_EMPTY, PTWConstants.ERROR_DESC_FIELD_EMPTY + "Country Short Name");
		}
	}
	
	public static void validateUpdateCountryBean(CountryBean countryBean) throws PTWException{
		validateCountryId(countryBean.getCountryId());
		if(StringUtil.isEmptyNull(countryBean.getCountryName())){
			throw new PTWException(PTWConstants.ERROR_CODE_COUNTRY_NAME_EMPTY, PTWConstants.ERROR_DESC_FIELD_EMPTY + "Country Name");
		}
		if(StringUtil.isEmptyNull(countryBean.getCountryShortName())){
			throw new PTWException(PTWConstants.ERROR_CODE_COUNTRY_SHORT_NAME_EMPTY, PTWConstants.ERROR_DESC_FIELD_EMPTY + "Country Short Name");
		}
	}
	
	public static void validateCountryId(Integer countryId) throws PTWException{
		if(StringUtil.isEmptyNull(countryId)){
			throw new PTWException(PTWConstants.ERROR_CODE_COUNTRY_ID_EMPTY, PTWConstants.ERROR_DESC_FIELD_EMPTY + "Country Id");
		}
	}
	
	public static void validateCreateTeamBean(TeamBean teamBean, TournamentDao tournamentDao) throws PTWException{
		if(StringUtil.isEmptyNull(teamBean.getTeamName())){
			throw new PTWException(PTWConstants.ERROR_CODE_TEAM_NAME_EMPTY, PTWConstants.ERROR_DESC_FIELD_EMPTY + "Team Name");
		}
		if(StringUtil.isEmptyNull(teamBean.getTeamShortName())){
			throw new PTWException(PTWConstants.ERROR_CODE_TEAM_SHORT_NAME_EMPTY, PTWConstants.ERROR_DESC_FIELD_EMPTY + "Team Short Name");
		}
		if(StringUtil.isEmptyNull(teamBean.getCountryId())){
			throw new PTWException(PTWConstants.ERROR_CODE_COUNTRY_ID_EMPTY, PTWConstants.ERROR_DESC_FIELD_EMPTY + "Country Name");
		}
		validateCountry(teamBean.getCountryId(), tournamentDao);
		
		if(StringUtil.isEmptyNull(teamBean.getSportTypeId())){
			throw new PTWException(PTWConstants.ERROR_CODE_SPORT_TYPE_ID_EMPTY, PTWConstants.ERROR_DESC_FIELD_EMPTY + "Sport Type");
		}
		validateSportType(teamBean.getSportTypeId(), tournamentDao);
		
		if(StringUtil.isEmptyNull(teamBean.getTeamTypeId())){
			throw new PTWException(PTWConstants.ERROR_CODE_TEAM_ID_EMPTY, PTWConstants.ERROR_DESC_FIELD_EMPTY + "Team Type");
		}
		validateTeamType(teamBean.getTeamTypeId(), tournamentDao);
	}
	
	private static void validateCountry(Integer countryId, TournamentDao tournamentDao)throws PTWException{
		CountryBean countryBean = new CountryBean();
		countryBean.setCountryId(countryId);
		List<CountryBean> foundCountryList = tournamentDao.getCountryList(countryBean);
		if(foundCountryList == null || foundCountryList.isEmpty()){
			throw new PTWException(PTWConstants.ERROR_CODE_COUNTRY_ID_NOT_FOUND, PTWConstants.ERROR_DESC_COUNTRY_ID_NOT_FOUND);
		}
		
	}
	
	public static void validateUpdateTeamBean(TeamBean teamBean, TournamentDao tournamentDao) throws PTWException{
		validateTeamId(teamBean.getTeamId());
		if(StringUtil.isEmptyNull(teamBean.getTeamName())){
			throw new PTWException(PTWConstants.ERROR_CODE_TEAM_NAME_EMPTY, PTWConstants.ERROR_DESC_FIELD_EMPTY + "Team Name");
		}
		if(StringUtil.isEmptyNull(teamBean.getTeamShortName())){
			throw new PTWException(PTWConstants.ERROR_CODE_TEAM_SHORT_NAME_EMPTY, PTWConstants.ERROR_DESC_FIELD_EMPTY + "Team Short Name");
		}
		
		if(!StringUtil.isEmptyNull(teamBean.getCountryId())){
			validateCountry(teamBean.getCountryId(), tournamentDao);
		}
		
		if(!StringUtil.isEmptyNull(teamBean.getSportTypeId())){
			validateSportType(teamBean.getSportTypeId(), tournamentDao);
		}
		
		if(!StringUtil.isEmptyNull(teamBean.getTeamTypeId())){
			validateTeamType(teamBean.getTeamTypeId(), tournamentDao);
		}
		
	}
	
	public static void validateTeamId(Integer teamId) throws PTWException{
		if(StringUtil.isEmptyNull(teamId)){
			throw new PTWException(PTWConstants.ERROR_CODE_TEAM_ID_EMPTY, PTWConstants.ERROR_DESC_FIELD_EMPTY + "Team Id");
		}
	}
	
}
