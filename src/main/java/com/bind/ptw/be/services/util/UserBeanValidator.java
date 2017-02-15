package com.bind.ptw.be.services.util;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.bind.ptw.be.dao.UserDao;
import com.bind.ptw.be.dto.CityBean;
import com.bind.ptw.be.dto.UserBean;
import com.bind.ptw.be.dto.UserConfirmationBean;
import com.bind.ptw.be.dto.UserPasswordBean;
import com.bind.ptw.be.dto.UserTournmentRegisterBean;
import com.bind.ptw.be.util.PTWConstants;
import com.bind.ptw.be.util.PTWException;
import com.bind.ptw.be.util.StringUtil;

public class UserBeanValidator {
	
	public static void validateRegisterUser(UserBean userBean, UserDao userDao) throws PTWException{
		
		validateUserLoginId(userBean.getUserLoginId());
		userBean.setEmail(userBean.getUserLoginId());
		
		validateUserPassword(userBean.getPassword());
		String sha256hex = org.apache.commons.codec.digest.DigestUtils.sha256Hex(userBean.getPassword());
		userBean.setPassword(sha256hex);
		validateUserName(userBean.getUserName());
		validateTeamName(userBean.getTeamName());
		validatePhoneNumber(userBean.getPhone());
		
		validateDuplicateLoginId(userBean.getUserLoginId(), userDao);
		validateDuplicateUserTeam(userBean.getTeamName(), userDao);
		validateCity(userBean.getCityId(), userDao);
	}
	
	private static void validateCity(Integer cityId, UserDao userDao) throws PTWException{
		if(StringUtil.isEmptyNull(cityId)){
			throw new PTWException(PTWConstants.ERROR_CODE_CITY_EMPTY, PTWConstants.ERROR_DESC_FIELD_EMPTY + "City");
		}
		
		CityBean reqCityBean = new CityBean();
		reqCityBean.setCityId(cityId);
		CityBean resultCityBean = userDao.getCity(reqCityBean);
		if(resultCityBean == null){
			throw new PTWException(PTWConstants.ERROR_CODE_INVALID_CITY, PTWConstants.ERROR_DESC_INVALID_CITY);
		}
		
	}

	public static void validateAuthenticateUser(UserBean userBean) throws PTWException{
		validateUserLoginId(userBean.getUserLoginId());
		if(StringUtil.isEmptyNull(userBean.getPassword())){
			throw new PTWException(PTWConstants.ERROR_CODE_USER_LOGIN_PASSWORD_EMPTY, PTWConstants.ERROR_DESC_FIELD_EMPTY + "Login Password");
		}
		String sha256hex = org.apache.commons.codec.digest.DigestUtils.sha256Hex(userBean.getPassword());
		userBean.setPassword(sha256hex);
		
	}
	
	public static void validateUserConfirmationRequest(UserConfirmationBean userConfirmationBean) throws PTWException{
		validateUserId(userConfirmationBean.getUserId());
		validateConfirmationCode(userConfirmationBean.getConfirmationCode());
		
	}
	
	public static UserBean validateResetPasswordRequest(UserBean userBean, UserDao userDao)throws PTWException{
		UserBean returnBean = null;
		validateUserLoginId(userBean.getUserLoginId());
		UserBean searchLoginUserBean = new UserBean();
		searchLoginUserBean.setUserLoginId(userBean.getUserLoginId());
		List<UserBean> searchedUserList = userDao.getUsers(searchLoginUserBean, false);
		if(searchedUserList != null && !searchedUserList.isEmpty()){
			returnBean = searchedUserList.get(0);
		}
		return returnBean;
	}
	
	public static void validateGetUserRegisteredTournaments(UserBean userBean) throws PTWException{
		validateUserId(userBean.getUserId());
	}
	
	public static void validateUpdatePassword(UserBean userBean) throws PTWException{
		validateUserLoginId(userBean.getUserLoginId());
		validateUserPassword(userBean.getPassword());
		String sha256hex = org.apache.commons.codec.digest.DigestUtils.sha256Hex(userBean.getPassword());
		userBean.setPassword(sha256hex);
	}

	public static void validateUserLoginId(String loginId) throws PTWException{
		
		if(StringUtil.isEmptyNull(loginId)){
			throw new PTWException(PTWConstants.ERROR_CODE_USER_LOGIN_ID_EMPTY, PTWConstants.ERROR_DESC_FIELD_EMPTY + "Login Id");
		}
		
		Pattern pattern = null;
		Matcher matcher = null;
		
		pattern = Pattern.compile( PTWConstants.EMAIL_REGEX );
		matcher = pattern.matcher( loginId );
		if( !matcher.matches() ){
			throw new PTWException( PTWConstants.ERROR_CODE_USER_LOGIN_ID_INVALID, 
					PTWConstants.ERROR_DESC_FIELD_INVALID + "Login Id" );
		}
	}
	
	private static void validateUserPassword(String password) throws PTWException{
		if(StringUtil.isEmptyNull(password)){
			throw new PTWException(PTWConstants.ERROR_CODE_USER_LOGIN_PASSWORD_EMPTY, PTWConstants.ERROR_DESC_FIELD_EMPTY + "Login Password");
		}
		
		if(password.length() < 6 || password.length() > 15){
			throw new PTWException( PTWConstants.ERROR_CODE_USER_PASSWORD_INVALID, 
					"Invalid password. Allowed lenght is between 6 to 15" );
		}
	}
	
	private static void validateUserName(String name) throws PTWException{
		if(StringUtil.isEmptyNull(name)){
			throw new PTWException(PTWConstants.ERROR_CODE_USER_NAME_EMPTY, PTWConstants.ERROR_DESC_FIELD_EMPTY + "User Name");
		}
	}
	
	public static void validateUserId(Integer userId) throws PTWException{
		if(StringUtil.isEmptyNull(userId)){
			throw new PTWException(PTWConstants.ERROR_CODE_USER_ID_EMPTY, PTWConstants.ERROR_DESC_FIELD_EMPTY + "UserId");
		}
	}
	
	private static void validateConfirmationCode(Integer confirmationCode) throws PTWException{
		if(StringUtil.isEmptyNull(confirmationCode)){
			throw new PTWException(PTWConstants.ERROR_CODE_CONFIRMATION_CODE_EMPTY, PTWConstants.ERROR_DESC_FIELD_EMPTY + "Confirmation Code");
		}
	}
	
	private static void validateTeamName(String teamName) throws PTWException{
		
		if(StringUtil.isEmptyNull(teamName)){
			throw new PTWException(PTWConstants.ERROR_CODE_USER_TEAM_NAME_EMPTY, PTWConstants.ERROR_DESC_FIELD_EMPTY + "Team Name");
		}
		
		Pattern pattern = null;
		Matcher matcher = null;
		
		pattern = Pattern.compile( PTWConstants.GENERAL_NAME_REGEX );
		matcher = pattern.matcher( teamName );
		if( !matcher.matches() ){
			throw new PTWException( PTWConstants.ERROR_CODE_USER_TEAM_NAME_INVALID, 
					PTWConstants.ERROR_DESC_FIELD_INVALID + "Team Name" );
		}
	}
	
	private static void validatePhoneNumber(String phoneNo) throws PTWException{
		if(StringUtil.isEmptyNull(phoneNo)){
			throw new PTWException(PTWConstants.ERROR_CODE_PHONE_NO_EMPTY, PTWConstants.ERROR_DESC_FIELD_EMPTY + "Phone Number");
		}
		if(!StringUtil.isEmptyNull(phoneNo)){
		
			Pattern pattern = null;
			Matcher matcher = null;
			
			pattern = Pattern.compile( PTWConstants.PHONE_NUMBER_REGEX );
			matcher = pattern.matcher( phoneNo );
			if( !matcher.matches() ){
				throw new PTWException( PTWConstants.ERROR_CODE_USER_PHONE_INVALID, 
						PTWConstants.ERROR_DESC_FIELD_INVALID + "Phone Number" );
			}
		}
	}
	
	private static void validateDuplicateLoginId(String loginId,
			UserDao userDao) throws PTWException {
		UserBean searchLoginUserBean = new UserBean();
		searchLoginUserBean.setUserLoginId(loginId);
		List<UserBean> searchedUserList = userDao.getUsers(searchLoginUserBean, false);
		if(searchedUserList != null && !searchedUserList.isEmpty()){
			throw new PTWException( PTWConstants.ERROR_CODE_USER_LOGIN_ID_DUPLICATE, 
					PTWConstants.ERROR_DESC_USER_LOGIN_ID_DUPLICATE);
		}
	}
	
	private static void validateDuplicateUserTeam(String teamName,
			UserDao userDao) throws PTWException {
		UserBean searchLoginUserBean = new UserBean();
		searchLoginUserBean.setTeamName(teamName);
		List<UserBean> searchedUserList = userDao.getUsers(searchLoginUserBean, false);
		if(searchedUserList != null && !searchedUserList.isEmpty()){
			throw new PTWException( PTWConstants.ERROR_CODE_USER_TEAM_NAME_DUPLICATE, 
					PTWConstants.ERROR_DESC_USER_TEAM_NAME_DUPLICATE);
		}
	}

	public static void validateTournamentRegistration(UserTournmentRegisterBean userTournament) throws PTWException{
		validateUserId(userTournament.getUserId());
		List<Integer> tournamentIdList = userTournament.getTournamentList();
		if(tournamentIdList == null || tournamentIdList.isEmpty()){
			throw new PTWException( PTWConstants.ERROR_CODE_TOURNAMENT_ID_EMPTY, 
					PTWConstants.ERROR_DESC_FIELD_INVALID +"Tournament Id");
		}
		for (Integer tournamentId : tournamentIdList) {
			if(StringUtil.isEmptyNull(tournamentId)){
				throw new PTWException( PTWConstants.ERROR_CODE_TOURNAMENT_ID_EMPTY, 
						PTWConstants.ERROR_DESC_FIELD_INVALID +"Tournament Id");
			}
		}
	}

	public static void validateUpdatePassword(UserPasswordBean userPasswordBean) throws PTWException{
		validateUserLoginId(userPasswordBean.getUserLoginId());
		validateUserPassword(userPasswordBean.getOldPassword());
		validateUserPassword(userPasswordBean.getPassword());
	}

	
}
