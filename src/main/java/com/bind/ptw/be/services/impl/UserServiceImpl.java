package com.bind.ptw.be.services.impl;

import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bind.ptw.be.dao.UserDao;
import com.bind.ptw.be.dto.BaseBean;
import com.bind.ptw.be.dto.CityBean;
import com.bind.ptw.be.dto.CityBeanList;
import com.bind.ptw.be.dto.UserBean;
import com.bind.ptw.be.dto.UserConfirmationBean;
import com.bind.ptw.be.dto.UserGroupBean;
import com.bind.ptw.be.dto.UserGroupBeanList;
import com.bind.ptw.be.dto.UserPasswordBean;
import com.bind.ptw.be.dto.UserTournamentBean;
import com.bind.ptw.be.dto.UserTournamentBeanList;
import com.bind.ptw.be.dto.UserTournmentRegisterBean;
import com.bind.ptw.be.services.UserService;
import com.bind.ptw.be.services.util.TournamentBeanValidator;
import com.bind.ptw.be.services.util.UserBeanValidator;
import com.bind.ptw.be.util.DBConstants;
import com.bind.ptw.be.util.EmailContent;
import com.bind.ptw.be.util.EmailUtil;
import com.bind.ptw.be.util.MailConfiguration;
import com.bind.ptw.be.util.PTWConstants;
import com.bind.ptw.be.util.PTWException;
import com.bind.ptw.be.util.StringUtil;

@Service("userService")
@PropertySource("file:${prop.path}/app.properties")
@Transactional
public class UserServiceImpl implements UserService{

	private static final Logger logger = Logger.getLogger(UserServiceImpl.class);
	
	@Autowired
	private Environment env;
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	HibernateTemplate hibernateTemplate;
	
	public UserBean createUser(UserBean registerUser){
		UserBean userResponse;
		try{
			logger.debug("Entering register User");
			UserBeanValidator.validateRegisterUser(registerUser, userDao);
			userResponse = userDao.save(registerUser);
			registerUser.setUserId(userResponse.getUserId());
			createUserToken(registerUser);
			createConfirmationRecord(registerUser);
			logger.debug("Exiting register User");
		}catch(PTWException exception){
			logger.error(exception);
			userResponse = new UserBean();
			userResponse.setResultCode(exception.getCode());
			userResponse.setResultDescription(exception.getDescription());
		}	
		return userResponse;
	}

	private void createUserToken(UserBean userResponse) throws PTWException {
		Date currDate = new Date();
		Long currTimeStamp = currDate.getTime();
		userResponse.setToken(String.valueOf(currTimeStamp));
		userDao.createUserToken(userResponse);
	}

	private void createConfirmationRecord(UserBean userResponse) throws PTWException {
		UserConfirmationBean userConfirmationBean = new UserConfirmationBean();
		userConfirmationBean.setUserId(userResponse.getUserId());
		int randomNum = StringUtil.createRandomNum(1111, 9998 + 1);
		userConfirmationBean.setConfirmationCode(randomNum);
		userConfirmationBean.setEmail(userResponse.getEmail());
		userDao.createConfirmationCode(userConfirmationBean);
		
		EmailContent emailContent = new EmailContent();
		emailContent.setToAddress(userResponse.getEmail());
	    StringBuilder bodyBuilder = new StringBuilder();
	    bodyBuilder.append("Thanks for registering for Predict And Win. Your confirmation Code is ");
	    bodyBuilder.append(String.valueOf(randomNum));
	    bodyBuilder.append("\r\n");
	    bodyBuilder.append("Complete registration by providing this confirmation code & start Predicting...");
	    bodyBuilder.append(" ");
	    bodyBuilder.append("\r\n");
	    bodyBuilder.append(" ");
	    bodyBuilder.append("\r\n");
	    bodyBuilder.append("This is auto generated Mail.");
	    emailContent.setEmailBody(bodyBuilder.toString());
	    
	    String subj = "Predict To Win: Confirmation Code";
	    emailContent.setEmailSubject(subj);
	    try{
	    	EmailUtil.sendEmail(emailContent, getMailConfiguration());
	    }catch (Exception ex) {
			ex.printStackTrace();
			throw new PTWException(PTWConstants.ERROR_CODE_EMAIL_DEL_FAILURE,PTWConstants.ERROR_DESC_CONF_CODE_EMAIL_DEL_FAILURE);
		} 
	}
	
	private MailConfiguration getMailConfiguration(){
		MailConfiguration config = new MailConfiguration();
		String senderAddress = env.getProperty(DBConstants.FROM_ADDRESS_KEY);
		String smtpUserName = env.getProperty(DBConstants.SMTP_USERNAME_KEY);
		String smtpPassword = env.getProperty(DBConstants.SMTP_PASSWORD_KEY);
		String smtpHost = env.getProperty(DBConstants.SMTP_HOST_KEY);
		String smtpPort = env.getProperty(DBConstants.PORT_KEY);
		
		config.setFromAddress(senderAddress);
		config.setSmtpUserName(smtpUserName);
		config.setSmtpPassword(smtpPassword);
		config.setHost(smtpHost);
		try{
			config.setPort(Integer.parseInt(smtpPort));
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		return config;
		
	}
	
	public UserBean authenticateUser(UserBean authUser, Boolean adminFlag){
		UserBean userResponse;
		try{
			MailConfiguration config = getMailConfiguration();
			System.out.println(config.getFromAddress());
			UserBeanValidator.validateAuthenticateUser(authUser);
			List<UserBean> retrievedUsers = userDao.getUsers(authUser, adminFlag);
			if(retrievedUsers == null || retrievedUsers.isEmpty()){
				throw new PTWException(PTWConstants.ERROR_CODE_USER_PWD_NOT_FOUND, PTWConstants.ERROR_DESC_USER_PWD_NOT_FOUND);
			}
			userResponse = retrievedUsers.get(0);
			if(!adminFlag){
				createUserToken(userResponse);
			}
		}catch(PTWException exception){
			userResponse = new UserBean();
			userResponse.setResultCode(exception.getCode());
			userResponse.setResultDescription(exception.getDescription());
		}
		return userResponse;
	}

	public UserConfirmationBean confirmUser(UserConfirmationBean userConfirmationBean){
		UserConfirmationBean resultConfirmationBean = new UserConfirmationBean();
		try{
			UserBeanValidator.validateUserConfirmationRequest(userConfirmationBean);
			UserConfirmationBean dbResultBean = userDao.getUserConfirmation(userConfirmationBean);
			if(dbResultBean == null){
				throw new PTWException(PTWConstants.ERROR_CODE_USER_INVALID, PTWConstants.ERROR_DESC_USER_INVALID);
			}
			if(!dbResultBean.getConfirmationCode().equals(userConfirmationBean.getConfirmationCode())){
				int retryCount = dbResultBean.getRetryCount();
				retryCount++;
				dbResultBean.setRetryCount(retryCount);
				userDao.updateConfirmationRetryCount(dbResultBean);
				resultConfirmationBean.setResultCode(PTWConstants.ERROR_CODE_WRONG_CONFIRMATION_CODE);
				resultConfirmationBean.setResultDescription(PTWConstants.ERROR_DESC_WRONG_CONFIRMATION_CODE);
			}else{
				UserBean userBean = new UserBean();
				userBean.setUserId(userConfirmationBean.getUserId());
				userBean.setUserStatusId(DBConstants.USER_STATUS_ACTIVE);
				userDao.updateUserStatus(userBean);
				userDao.deleteConfirmationCode(dbResultBean);
			}
			
		}catch(PTWException exception){
			resultConfirmationBean.setResultCode(exception.getCode());
			resultConfirmationBean.setResultDescription(exception.getDescription());
		}
		return resultConfirmationBean;
	}

	@Override
	public CityBeanList getCities() {
		CityBeanList cityBeanList = new CityBeanList();
		List<CityBean> cityBeaList = userDao.getCities();
		cityBeanList.setCities(cityBeaList);
		return cityBeanList;
	}

	@Override
	public BaseBean registerUserToTournament(UserTournmentRegisterBean userTournament) {
		BaseBean baseBean = new BaseBean();
		try{
			TournamentBeanValidator.validateRequest(userTournament);
			UserBeanValidator.validateTournamentRegistration(userTournament);
			userDao.registerUserToTournament(userTournament);
		}catch(PTWException exception){
			baseBean.setResultCode(exception.getCode());
			baseBean.setResultDescription(exception.getDescription());
		}
		return baseBean;
	}

	@Override
	public UserTournamentBeanList getUserRegisterTournament(UserBean userBean) {
		UserTournamentBeanList userTournamentRegisterBean = new UserTournamentBeanList();
		try{
			TournamentBeanValidator.validateRequest(userBean);
			UserBeanValidator.validateUserId(userBean.getUserId());
			
			userTournamentRegisterBean.setUserId(userBean.getUserId());
			
			List<UserTournamentBean> tournamentList = userDao.getUserRegisteredTournament(userBean);
			userTournamentRegisterBean.setUserTournamentList(tournamentList);
		}catch(PTWException exception){
			userTournamentRegisterBean.setResultCode(exception.getCode());
			userTournamentRegisterBean.setResultDescription(exception.getDescription());
		}
		return userTournamentRegisterBean;
	}

	@Override
	public BaseBean resetPassword(UserBean userBean) {
		BaseBean baseBean = new BaseBean();
		try{
			TournamentBeanValidator.validateRequest(userBean);
			UserBeanValidator.validateUserLoginId(userBean.getUserLoginId());
			List<UserBean> foundUserList = userDao.getUsers(userBean, false);
			if(foundUserList == null || foundUserList.size() != 1){
				throw new PTWException(PTWConstants.ERROR_CODE_USER_INVALID, PTWConstants.ERROR_DESC_USER_INVALID);
			}
			UserBean foundUser = foundUserList.get(0);
			int newPassword = generateRandomPassword();
			
			String sha256hexPwd = org.apache.commons.codec.digest.DigestUtils.sha256Hex(String.valueOf(newPassword));
			foundUser.setPassword(sha256hexPwd);
			userDao.updateUser(foundUser);
			
			EmailContent emailContent = new EmailContent();
			emailContent.setToAddress(foundUser.getEmail());
		    StringBuilder bodyBuilder = new StringBuilder();
		    bodyBuilder.append("Thanks for contacting for resetting password. Your new password is ");
		    bodyBuilder.append(String.valueOf(newPassword));
		    bodyBuilder.append("\r\n");
		    bodyBuilder.append(" ");
		    bodyBuilder.append("\r\n");
		    bodyBuilder.append("This is auto generated Mail.");
		    emailContent.setEmailBody(bodyBuilder.toString());
		    
		    String subj = "Predict To Win: New Password";
		    emailContent.setEmailSubject(subj);
		    try{
		    	EmailUtil.sendEmail(emailContent, getMailConfiguration());
		    }catch (Exception ex) {
				ex.printStackTrace();
				throw new PTWException(PTWConstants.ERROR_CODE_EMAIL_DEL_FAILURE,PTWConstants.ERROR_DESC_RESET_PWD_EMAIL_DEL_FAILURE);
			}
		}catch(PTWException exception){
			baseBean.setResultCode(exception.getCode());
			baseBean.setResultDescription(exception.getDescription());
		}
		return baseBean;
	}
	
	private int generateRandomPassword() {
		int Min = 100000;
		int Max = 999999;
		
		Random random = new Random();
		int nextNumber;
		do{
			nextNumber = random.nextInt(Max);
		}while(!((nextNumber > Min) && (nextNumber < Max)));
		return nextNumber;
	}

	@Override
	public BaseBean updatePassword(UserPasswordBean userPasswordBean) {
		BaseBean baseBean = new BaseBean();
		try{
			TournamentBeanValidator.validateRequest(userPasswordBean);
			UserBeanValidator.validateUpdatePassword(userPasswordBean);
			UserBean userBean = new UserBean();
			userBean.setUserLoginId(userPasswordBean.getUserLoginId());
			String encryptedOldPwd = org.apache.commons.codec.digest.DigestUtils.sha256Hex(userPasswordBean.getOldPassword());
			userBean.setPassword(encryptedOldPwd);
			List<UserBean> foundUserList = userDao.getUsers(userBean, false);
			if(foundUserList == null || foundUserList.size() != 1 ){
				throw new PTWException(PTWConstants.ERROR_CODE_USER_INVALID, PTWConstants.ERROR_DESC_USER_INVALID);
			}
			UserBean foundUser = foundUserList.get(0);
			String encryptedNewPwd = org.apache.commons.codec.digest.DigestUtils.sha256Hex(userPasswordBean.getPassword());
			foundUser.setPassword(encryptedNewPwd);
			userDao.updateUser(foundUser);
		}catch(PTWException exception){
			baseBean.setResultCode(exception.getCode());
			baseBean.setResultDescription(exception.getDescription());
		}
		return baseBean;
	}

	@Override
	public UserGroupBean createUserGroup(UserGroupBean userGroupBean) {
		UserGroupBean retUserGroupBean;
		try{
			TournamentBeanValidator.validateRequest(userGroupBean);
			UserBeanValidator.validateCreateUserGroup(userGroupBean);
			userGroupBean.setPrizeGroupFlag(false);
			int randomGroupCode = StringUtil.createRandomNum(11111, 99998 + 1);
			userGroupBean.setGroupCode(randomGroupCode);
			retUserGroupBean = userDao.createUserGroup(userGroupBean);
		}catch(PTWException exception){
			retUserGroupBean = new UserGroupBean();
			retUserGroupBean.setResultCode(exception.getCode());
			retUserGroupBean.setResultDescription(exception.getDescription());
		}
		return retUserGroupBean;
	}

	@Override
	public BaseBean updateUserGroup(UserGroupBean userGroupBean) {
		BaseBean baseBean = new BaseBean();
		try{
			TournamentBeanValidator.validateRequest(userGroupBean);
			UserBeanValidator.validateUpdateUserGroup(userGroupBean);
			userDao.updateUserGroup(userGroupBean);
		}catch(PTWException exception){
			baseBean.setResultCode(exception.getCode());
			baseBean.setResultDescription(exception.getDescription());
		}
		return baseBean;
	}

	@Override
	public UserGroupBeanList getUserOwnedLeague(UserBean userBean) {
		UserGroupBeanList retGroupBeanList = new UserGroupBeanList();
		try{
			TournamentBeanValidator.validateRequest(userBean);
			UserBeanValidator.validateUserId(userBean.getUserId());
			List<UserGroupBean> userGroups = userDao.getUserCreatedGroups(userBean);
			retGroupBeanList.setUserGroupBean(userGroups);
		}catch(PTWException exception){
			retGroupBeanList.setResultCode(exception.getCode());
			retGroupBeanList.setResultDescription(exception.getDescription());
		}
		return retGroupBeanList;
	}

	@Override
	public BaseBean deleteUserOwnerGroup(UserGroupBean userGroupBean){
		BaseBean baseBean = new BaseBean();
		try{
			TournamentBeanValidator.validateRequest(userGroupBean);
			UserBeanValidator.validateGroupId(userGroupBean.getGroupId());
			UserBeanValidator.validateUserId(userGroupBean.getOwnerId());
			userDao.deleteUserGroup(userGroupBean);
		}catch(PTWException exception){
			baseBean.setResultCode(exception.getCode());
			baseBean.setResultDescription(exception.getDescription());
		}
		return baseBean;
	}
}
