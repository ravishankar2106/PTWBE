package com.bind.ptw.be.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.bind.ptw.be.dao.UserDao;
import com.bind.ptw.be.dto.CityBean;
import com.bind.ptw.be.dto.ContestBean;
import com.bind.ptw.be.dto.TournamentBean;
import com.bind.ptw.be.dto.UserBean;
import com.bind.ptw.be.dto.UserConfirmationBean;
import com.bind.ptw.be.dto.UserGroupBean;
import com.bind.ptw.be.dto.UserTournamentBean;
import com.bind.ptw.be.dto.UserTournmentRegisterBean;
import com.bind.ptw.be.entities.City;
import com.bind.ptw.be.entities.Tournament;
import com.bind.ptw.be.entities.UserBonusPoint;
import com.bind.ptw.be.entities.UserBonusPointHome;
import com.bind.ptw.be.entities.UserConfirmation;
import com.bind.ptw.be.entities.UserConfirmationHome;
import com.bind.ptw.be.entities.UserGroup;
import com.bind.ptw.be.entities.UserGroupHome;
import com.bind.ptw.be.entities.UserGroupMapping;
import com.bind.ptw.be.entities.UserGroupMappingHome;
import com.bind.ptw.be.entities.UserGroupMappingKey;
import com.bind.ptw.be.entities.UserHome;
import com.bind.ptw.be.entities.UserScoreBoard;
import com.bind.ptw.be.entities.UserScoreBoardHome;
import com.bind.ptw.be.entities.UserStatus;
import com.bind.ptw.be.entities.UserToken;
import com.bind.ptw.be.entities.UserTokenHome;
import com.bind.ptw.be.entities.UserTournamentRegistration;
import com.bind.ptw.be.entities.UserTournamentRegistrationHome;
import com.bind.ptw.be.entities.Users;
import com.bind.ptw.be.util.DBConstants;
import com.bind.ptw.be.util.PTWConstants;
import com.bind.ptw.be.util.PTWException;
import com.bind.ptw.be.util.StringUtil;


@Repository("userDao")
public class UserDaoImpl implements UserDao{
	 
	@Autowired
	private HibernateTemplate hibernateTemplate;
	
	private Session getSession() {
	    return hibernateTemplate.getSessionFactory().getCurrentSession();
	}
	
	@Override
	public UserBean save(UserBean userBean){
		UserHome userHome = new UserHome(getSession());
		Users user = new Users();
		user.setLoginId(userBean.getUserLoginId());
		user.setPassword(userBean.getPassword());
		user.setUserName(userBean.getUserName());
		user.setTeamName(userBean.getTeamName());
		user.setEmailId(userBean.getEmail());
		user.setPhone(userBean.getPhone());
		user.setAdminFlag(false);
		
		UserStatus userStatus = new UserStatus();
		userStatus.setUserStatusId(DBConstants.USER_STATUS_PENDING);
		user.setUserStatus(userStatus);
		
		City city = new City();
		city.setCityId(userBean.getCityId());
		user.setCity(city);
		
		userHome.save(user);
		UserBean retUser = new UserBean();
		retUser.setUserId(user.getUserId());
		retUser.setUserStatusId(DBConstants.USER_STATUS_PENDING);
		return retUser;
	}
	
	
	@Override
	public List<UserBean> getUsers(UserBean userBean, Boolean adminFlag){
		List<UserBean> retrievedUserBeanList = null;
		
		UserHome userHome = new UserHome(getSession());
		List<Users> userList = userHome.findUsersByFilters(userBean, adminFlag);
		if(userList != null && !userList.isEmpty()){
			retrievedUserBeanList = new ArrayList<UserBean>();
			for(Users user : userList){
				UserBean retrievedUserBean = new UserBean();
				retrievedUserBean.setUserId(user.getUserId());
				retrievedUserBean.setUserLoginId(user.getLoginId());
				retrievedUserBean.setUserName(user.getUserName());
				if(!adminFlag){
					retrievedUserBean.setTeamName(user.getTeamName());
					retrievedUserBean.setEmail(user.getEmailId());
					retrievedUserBean.setPhone(user.getPhone());
					retrievedUserBean.setUserStatusId(user.getUserStatus().getUserStatusId());
					retrievedUserBean.setCityId(user.getCity().getCityId());
				}
				retrievedUserBeanList.add(retrievedUserBean);
			}
		}
		
		return retrievedUserBeanList;
	}
	
	public void createUserToken(UserBean user)throws PTWException{
		UserTokenHome userTokenHome = new UserTokenHome(getSession());
		List<UserToken> userTokenList = userTokenHome.findByFilter(user.getUserId(), null);
		if(userTokenList != null && !userTokenList.isEmpty()){
			UserToken userToken = userTokenList.get(0);
			userToken.setToken(user.getToken());
			userToken.setLoginDate(new Date());
			userTokenHome.merge(userToken);
		}else{
			UserToken userToken = new UserToken();
			userToken.setUserId(user.getUserId());
			userToken.setToken(user.getToken());
			userToken.setLoginDate(new Date());
			userTokenHome.persist(userToken);
		}
	}

	@Override
	public void createConfirmationCode(UserConfirmationBean userConfirmationBean) throws PTWException {
		UserConfirmation userConfirmation = new UserConfirmation();
		Users user = new Users();
		user.setUserId(userConfirmationBean.getUserId());
		userConfirmation.setUser(user);
		userConfirmation.setConfirmationCode(userConfirmationBean.getConfirmationCode());
		userConfirmation.setCreatedDate(new Date());
		userConfirmation.setEmailId(userConfirmationBean.getEmail());
		userConfirmation.setRetryCount(0);
		UserConfirmationHome userConfirmationHome = new UserConfirmationHome(getSession());
		userConfirmationHome.save(userConfirmation);
	}

	@Override
	public UserConfirmationBean getUserConfirmation(UserConfirmationBean userConfirmationBean){
		UserConfirmationHome userConfirmationHome = new UserConfirmationHome(getSession());
		UserConfirmation resultConfirmation = userConfirmationHome.findUsersByFilters(userConfirmationBean);
		UserConfirmationBean resultConfirmationBean = null;
		if(resultConfirmation != null){
			resultConfirmationBean = new UserConfirmationBean();
			resultConfirmationBean.setUserConfirmationId(resultConfirmation.getUserConfirmationPendingId());
			resultConfirmationBean.setCodeCreationDate(resultConfirmation.getCreatedDate());
			resultConfirmationBean.setConfirmationCode(resultConfirmation.getConfirmationCode());
			resultConfirmationBean.setEmail(resultConfirmation.getEmailId());
			resultConfirmationBean.setRetryCount(resultConfirmation.getRetryCount());
			resultConfirmationBean.setUserId(resultConfirmation.getUser().getUserId());
		}
		return resultConfirmationBean;
	}
	
	@Override
	public void updateConfirmationRetryCount(UserConfirmationBean userConfirmationBean){
		UserConfirmationHome userConfirmationHome = new UserConfirmationHome(getSession());
		UserConfirmation userConfirmation = userConfirmationHome.findById(userConfirmationBean.getUserConfirmationId());
		userConfirmation.setRetryCount(userConfirmationBean.getRetryCount());
		userConfirmationHome.merge(userConfirmation);
	}

	@Override
	public void deleteConfirmationCode(UserConfirmationBean userConfirmationBean) {
		UserConfirmationHome userConfirmationHome = new UserConfirmationHome(getSession());
		UserConfirmation userConfirmation = userConfirmationHome.findById(userConfirmationBean.getUserConfirmationId());
		userConfirmationHome.remove(userConfirmation);
	}

	@Override
	public void updateUser(UserBean userBean) {
		UserHome userHome = new UserHome(getSession());
		Users user = userHome.findById(userBean.getUserId());
		if(!StringUtil.isEmptyNull(userBean.getUserName())){
			user.setUserName(userBean.getUserName());
		}
		if(!StringUtil.isEmptyNull(userBean.getEmail())){
			user.setEmailId(userBean.getEmail());
		}
		if(!StringUtil.isEmptyNull(userBean.getPhone())){
			user.setPhone(userBean.getPhone());
		}
		if(!StringUtil.isEmptyNull(userBean.getPassword())){
			user.setPassword(userBean.getPassword());
		}
		userHome.merge(user);
	}

	@Override
	public void updateUserStatus(UserBean userBean) {
		UserHome userHome = new UserHome(getSession());
		Users user = userHome.findById(userBean.getUserId());
		UserStatus userStatus = new UserStatus();
		userStatus.setUserStatusId(userBean.getUserStatusId());
		user.setUserStatus(userStatus);
		userHome.merge(user);
		
	}

	@Override
	public List<CityBean> getCities() {
		List<CityBean> returnBeanList = new ArrayList<CityBean>();
		UserHome userHome = new UserHome(getSession());
		List<City> cities = userHome.getCities();
		if(cities != null && !cities.isEmpty()){
			for (City city : cities) {
				CityBean cityBean = new CityBean();
				cityBean.setCityId(city.getCityId());
				cityBean.setCityName(city.getCityName());
				returnBeanList.add(cityBean);
			}
		}
		return returnBeanList;
		
	}
	
	@Override
	public CityBean getCity(CityBean cityBean){
		UserHome userHome = new UserHome(getSession());
		CityBean returnBean = null;
		City city = userHome.findByCityId(cityBean.getCityId());
		if(city != null){
			returnBean = new CityBean();
			returnBean.setCityId(city.getCityId());
			returnBean.setCityName(city.getCityName());
		}
		return returnBean;
	}
	
	@Override
	public void updateBonusPoints(ContestBean contestBean, List<Integer> userIdList) throws PTWException{
		UserBonusPointHome userBonusPointHome = new UserBonusPointHome(this.getSession());
		try{
			for (Integer userId : userIdList) {
				UserBonusPoint userBonus = new UserBonusPoint();
				userBonus.setContestId(contestBean.getContestId());
				userBonus.setUserId(userId);
				userBonus.setPoints(contestBean.getBonusPoints());
				userBonusPointHome.save(userBonus);
			}
		}catch(Exception exception){
			exception.printStackTrace();
			throw new PTWException(PTWConstants.ERROR_CODE_DB_EXCEPTION, PTWConstants.ERROR_DESC_DB_EXCEPTION);
		}
	}

	@Override
	public void registerUserToTournament(UserTournmentRegisterBean userTournament)throws PTWException {
		UserTournamentRegistrationHome userTournamentHome = new UserTournamentRegistrationHome(this.getSession());
		UserScoreBoardHome userScoreBoardHome = new UserScoreBoardHome(this.getSession());
		try{
			List<Integer> tournamentIds = userTournament.getTournamentList();
			for (Integer tournamentId : tournamentIds) {
				List<UserTournamentRegistration> existingUserTournament = userTournamentHome.findByFilter(tournamentId, userTournament.getUserId());
				if(existingUserTournament == null || existingUserTournament.isEmpty()){
					UserTournamentRegistration userTournamentRegistration = new UserTournamentRegistration();
					userTournamentRegistration.setUserId(userTournament.getUserId());
					Tournament tournament = new Tournament();
					tournament.setTournamentId(tournamentId);
					userTournamentRegistration.setTournament(tournament);
					userTournamentHome.persist(userTournamentRegistration);
					
					UserScoreBoard userScoreBoard = new UserScoreBoard();
					Users users = new Users();
					users.setUserId(userTournament.getUserId());
					userScoreBoard.setUser(users);
					userScoreBoard.setTournamentId(tournamentId);
					userScoreBoard.setTotalPoints(0);
					userScoreBoard.setRank(0);
					userScoreBoardHome.persist(userScoreBoard);
				}
			}
		}catch(Exception exception){
			exception.printStackTrace();
			throw new PTWException(PTWConstants.ERROR_CODE_DB_EXCEPTION, PTWConstants.ERROR_DESC_DB_EXCEPTION);
		}
	}

	@Override
	public List<UserTournamentBean> getUserRegisteredTournament(UserBean userBean) throws PTWException {
		List<UserTournamentBean> userTournamentList = null;
		UserTournamentRegistrationHome userTournamentHome = new UserTournamentRegistrationHome(this.getSession());
		UserScoreBoardHome scoreBoardHome = new UserScoreBoardHome(getSession());
		try{
			List<UserTournamentRegistration> userRegisteredTournamentList = userTournamentHome.findByFilter(null, userBean.getUserId());
			if(userRegisteredTournamentList!=null && !userRegisteredTournamentList.isEmpty()){
				userTournamentList = new ArrayList<UserTournamentBean>();
				for (UserTournamentRegistration userTournamentRegistration : userRegisteredTournamentList) {
					UserTournamentBean userTournamentBean = new UserTournamentBean();
					
					TournamentBean tournamentBean = new TournamentBean();
					Tournament tournament = userTournamentRegistration.getTournament();
					tournamentBean.setTournamentId(tournament.getTournamentId());
					tournamentBean.setTournamentName(tournament.getTournamentName());
					tournamentBean.setTournamentVenue(tournament.getTournamentVenue());
					tournamentBean.setTournamentDesc(tournament.getTournamentDescription());
					tournamentBean.setStartDateStr(StringUtil.convertDateTImeToString(tournament.getStartDate()));
					tournamentBean.setEndDateStr(StringUtil.convertDateTImeToString(tournament.getEndDate()));
					userTournamentBean.setTournamentBean(tournamentBean);
					
					Integer user[] = new Integer[1];
					user[0] = userBean.getUserId();
					List<UserScoreBoard> scoreBoardList = scoreBoardHome.findByFilter(userTournamentRegistration.getTournament().getTournamentId(), user , null);
					if(scoreBoardList != null && scoreBoardList.size() == 1){
						UserScoreBoard userScores = scoreBoardList.get(0);
						userTournamentBean.setTotalPoints(userScores.getTotalPoints());
						userTournamentBean.setRank(userScores.getRank());
					}
					userTournamentList.add(userTournamentBean);
				}
			}
		}catch(Exception exception){
			exception.printStackTrace();
			throw new PTWException(PTWConstants.ERROR_CODE_DB_EXCEPTION, PTWConstants.ERROR_DESC_DB_EXCEPTION);
		}
		return userTournamentList;
	}

	@Override
	public UserGroupBean createUserGroup(UserGroupBean userGroupBean) throws PTWException {
		UserGroupHome userGroupHome = new UserGroupHome(this.getSession());
		UserGroupMappingHome userGroupMappingHome = new UserGroupMappingHome(this.getSession());
		try{
			UserGroup userGroup = new UserGroup();
			userGroup.setOwnerUserId(userGroupBean.getOwnerId());
			userGroup.setTournamentId(userGroupBean.getTournamentId());
			userGroup.setUserGroupCode(userGroupBean.getGroupCode());
			userGroup.setPrizeIncludedFlag(userGroupBean.getPrizeGroupFlag());
			userGroup.setUserGroupName(userGroupBean.getGroupName());
			userGroupHome.persist(userGroup);
			userGroupBean.setGroupId(userGroup.getUserGroupId());
			createUserGroupMapping(userGroupBean.getOwnerId(), userGroupMappingHome, userGroup.getUserGroupId());
		}catch(Exception exception){
			exception.printStackTrace();
			throw new PTWException(PTWConstants.ERROR_CODE_DB_EXCEPTION, PTWConstants.ERROR_DESC_DB_EXCEPTION);
		}
		return userGroupBean;
	}

	private void createUserGroupMapping(Integer userId, UserGroupMappingHome userGroupMappingHome,
			Integer groupId) {
		UserGroupMapping userGroupMapping = new UserGroupMapping();
		UserGroupMappingKey userGroupMappingKey = new UserGroupMappingKey();
		userGroupMappingKey.setUserGroupId(groupId);
		userGroupMappingKey.setUserId(userId);
		userGroupMapping.setUserGroupMappingKey(userGroupMappingKey);
		userGroupMappingHome.persist(userGroupMapping);
	}

	@Override
	public void updateUserGroup(UserGroupBean userGroupBean) throws PTWException {
		UserGroupHome userGroupHome = new UserGroupHome(this.getSession());
		try{
			UserGroup userGroup = userGroupHome.findById(userGroupBean.getGroupId());
			if(userGroup == null){
				throw new PTWException(PTWConstants.ERROR_CODE_INVALID_GROUP, PTWConstants.ERROR_DESC_INVALID_GROUP);
			}
			userGroup.setUserGroupName(userGroupBean.getGroupName());
			if(userGroupBean.getPrizeGroupFlag() != null && userGroupBean.getPrizeGroupFlag()){
				userGroup.setPrizeIncludedFlag(userGroupBean.getPrizeGroupFlag());
			}
			userGroupHome.merge(userGroup);
		}catch(Exception exception){
			exception.printStackTrace();
			throw new PTWException(PTWConstants.ERROR_CODE_DB_EXCEPTION, PTWConstants.ERROR_DESC_DB_EXCEPTION);
		}
		
	}

	@Override
	public List<UserGroupBean> getUserCreatedGroups(UserBean userBean) throws PTWException {
		UserGroupHome userGroupHome = new UserGroupHome(this.getSession());
		List<UserGroupBean> userGroupBeanList= null;
		try{
			UserGroupBean queryBean = new UserGroupBean();
			queryBean.setOwnerId(userBean.getUserId());
			List<UserGroup> userGroupLst = userGroupHome.findByFilter(queryBean);
			if(userGroupLst != null && !userGroupLst.isEmpty()){
				userGroupBeanList = new ArrayList<UserGroupBean>();
				for (UserGroup userGroup : userGroupLst) {
					UserGroupBean userGroupBean = new UserGroupBean();
					userGroupBean.setGroupId(userGroup.getUserGroupId());
					userGroupBean.setTournamentId(userGroup.getTournamentId());
					userGroupBean.setGroupCode(userGroup.getUserGroupCode());
					userGroupBean.setOwnerId(userGroup.getOwnerUserId());
					userGroupBean.setGroupName(userGroup.getUserGroupName());
					userGroupBean.setPrizeGroupFlag(userGroup.getPrizeIncludedFlag());
					userGroupBeanList.add(userGroupBean);
				}
			}
		}catch(Exception exception){
			exception.printStackTrace();
			throw new PTWException(PTWConstants.ERROR_CODE_DB_EXCEPTION, PTWConstants.ERROR_DESC_DB_EXCEPTION);
		}		
		return userGroupBeanList;
	}
	
	@Override
	public void deleteUserGroup(UserGroupBean userGroupBean)  throws PTWException{
		UserGroupHome userGroupHome = new UserGroupHome(this.getSession());
		UserGroupMappingHome userGroupMappingHome = new UserGroupMappingHome(this.getSession());
		try{
			List<UserGroup> userGroupList = userGroupHome.findByFilter(userGroupBean);
			if(userGroupList == null || userGroupList.size() != 1){
				throw new PTWException(PTWConstants.ERROR_CODE_INVALID_GROUP, PTWConstants.ERROR_DESC_INVALID_GROUP);
			}
			List<UserGroupMapping> userGroups = userGroupMappingHome.findUserGroup(null, userGroupBean.getGroupId());
			if(userGroups == null || userGroups.size() != 1){
				throw new PTWException(PTWConstants.ERROR_CODE_GROUP_DELETE_NOT_ALLOWED, PTWConstants.ERROR_DESC_GROUP_DELETE_NOT_ALLOWED);
			}
			UserGroupMapping userGroupMapping = userGroups.get(0);
			userGroupMappingHome.remove(userGroupMapping);
			UserGroup userGroup = userGroupList.get(0);
			userGroupHome.remove(userGroup);
		}catch(Exception exception){
			exception.printStackTrace();
			throw new PTWException(PTWConstants.ERROR_CODE_DB_EXCEPTION, PTWConstants.ERROR_DESC_DB_EXCEPTION);
		}
	}
}
