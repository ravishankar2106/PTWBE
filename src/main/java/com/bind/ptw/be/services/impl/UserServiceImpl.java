package com.bind.ptw.be.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bind.ptw.be.dao.UserDao;
import com.bind.ptw.be.dto.BaseBean;
import com.bind.ptw.be.dto.CityBean;
import com.bind.ptw.be.dto.CityBeanList;
import com.bind.ptw.be.dto.TournamentBean;
import com.bind.ptw.be.dto.UserBean;
import com.bind.ptw.be.dto.UserConfirmationBean;
import com.bind.ptw.be.dto.UserPasswordBean;
import com.bind.ptw.be.dto.UserTournmentRegisterBean;
import com.bind.ptw.be.services.UserService;
import com.bind.ptw.be.services.util.TournamentBeanValidator;
import com.bind.ptw.be.services.util.UserBeanValidator;
import com.bind.ptw.be.util.DBConstants;
import com.bind.ptw.be.util.EmailContent;
import com.bind.ptw.be.util.EmailUtil;
import com.bind.ptw.be.util.PTWConstants;
import com.bind.ptw.be.util.PTWException;
import com.bind.ptw.be.util.StringUtil;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService{

	private static final Logger logger = Logger.getLogger(UserServiceImpl.class);
	
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
		EmailUtil.sendEmail(emailContent);
	}

	public UserBean authenticateUser(UserBean authUser){
		UserBean userResponse;
		try{
			UserBeanValidator.validateAuthenticateUser(authUser);
			List<UserBean> retrievedUsers = userDao.getUsers(authUser);
			if(retrievedUsers == null || retrievedUsers.isEmpty()){
				throw new PTWException(PTWConstants.ERROR_CODE_USER_PWD_NOT_FOUND, PTWConstants.ERROR_DESC_USER_PWD_NOT_FOUND);
			}
			userResponse = retrievedUsers.get(0);
			createUserToken(userResponse);
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
			TournamentBeanValidator.vaidateRequest(userTournament);
			UserBeanValidator.validateTournamentRegistration(userTournament);
			userDao.registerUserToTournament(userTournament);
		}catch(PTWException exception){
			baseBean.setResultCode(exception.getCode());
			baseBean.setResultDescription(exception.getDescription());
		}
		return baseBean;
	}

	@Override
	public UserTournmentRegisterBean getUserRegisterTournament(UserBean userBean) {
		UserTournmentRegisterBean userTournamentRegisterBean = new UserTournmentRegisterBean();
		try{
			TournamentBeanValidator.vaidateRequest(userBean);
			UserBeanValidator.validateUserId(userBean.getUserId());
			List<TournamentBean> tournamentList = userDao.getUserRegisteredTournament(userBean);
			if(tournamentList!=null && !tournamentList.isEmpty()){
				List<Integer> tournamentIdList = new ArrayList<Integer>();
				for (TournamentBean tournament : tournamentList) {
					tournamentIdList.add(tournament.getTournamentId());
				}
				userTournamentRegisterBean.setTournamentList(tournamentIdList);
			}
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
			TournamentBeanValidator.vaidateRequest(userBean);
			UserBeanValidator.validateUserLoginId(userBean.getUserLoginId());
			List<UserBean> foundUserList = userDao.getUsers(userBean);
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
			EmailUtil.sendEmail(emailContent);
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
			TournamentBeanValidator.vaidateRequest(userPasswordBean);
			UserBeanValidator.validateUpdatePassword(userPasswordBean);
			UserBean userBean = new UserBean();
			userBean.setUserLoginId(userPasswordBean.getUserLoginId());
			String encryptedOldPwd = org.apache.commons.codec.digest.DigestUtils.sha256Hex(userPasswordBean.getOldPassword());
			userBean.setPassword(encryptedOldPwd);
			List<UserBean> foundUserList = userDao.getUsers(userBean);
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

}
