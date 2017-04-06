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
import com.bind.ptw.be.dto.OneSignalUserRegistrationBean;
import com.bind.ptw.be.dto.TournamentBean;
import com.bind.ptw.be.dto.TournamentFanClubBean;
import com.bind.ptw.be.dto.UserBean;
import com.bind.ptw.be.dto.UserConfirmationBean;
import com.bind.ptw.be.dto.UserGroupBean;
import com.bind.ptw.be.dto.UserGroupInvitationBean;
import com.bind.ptw.be.dto.UserTournamentBean;
import com.bind.ptw.be.dto.UserTournmentRegisterBean;
import com.bind.ptw.be.entities.City;
import com.bind.ptw.be.entities.OneSignalUserRegistration;
import com.bind.ptw.be.entities.OneSignalUserRegistrationHome;
import com.bind.ptw.be.entities.Tournament;
import com.bind.ptw.be.entities.UserBonusPoint;
import com.bind.ptw.be.entities.UserBonusPointHome;
import com.bind.ptw.be.entities.UserConfirmation;
import com.bind.ptw.be.entities.UserConfirmationHome;
import com.bind.ptw.be.entities.UserGroup;
import com.bind.ptw.be.entities.UserGroupHome;
import com.bind.ptw.be.entities.UserGroupInvitation;
import com.bind.ptw.be.entities.UserGroupInvitationHome;
import com.bind.ptw.be.entities.UserGroupMapping;
import com.bind.ptw.be.entities.UserGroupMappingHome;
import com.bind.ptw.be.entities.UserGroupMappingKey;
import com.bind.ptw.be.entities.UserHome;
import com.bind.ptw.be.entities.UserInvitationStatus;
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
					retrievedUserBean.setPushRegistered(user.getPushRegistered());
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
					List<UserScoreBoard> scoreBoardList = scoreBoardHome.findByFilter(userTournamentRegistration.getTournament().getTournamentId(), user , null, false);
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
		try{
			UserGroupBean queryBean = new UserGroupBean();
			queryBean.setGroupName(userGroupBean.getGroupName());
			queryBean.setOwnerId(userGroupBean.getOwnerId());
			queryBean.setTournamentId(userGroupBean.getTournamentId());
			List<UserGroup> existingGroups = userGroupHome.findByFilter(userGroupBean);
			if(existingGroups != null && !existingGroups.isEmpty()){
				throw new PTWException(PTWConstants.ERROR_CODE_DUPLICATE_GROUP_NAME, PTWConstants.ERROR_DESC_DUPLICATE_GROUP_NAME);
			}
			
			UserGroup userGroup = new UserGroup();
			Users user = new Users();
			user.setUserId(userGroupBean.getOwnerId());
			userGroup.setOwnerUser(user);
			Tournament tournament = new Tournament();
			tournament.setTournamentId(userGroupBean.getTournamentId());
			userGroup.setTournament(tournament);
			userGroup.setUserGroupCode(userGroupBean.getGroupCode());
			userGroup.setPrizeIncludedFlag(userGroupBean.getPrizeGroupFlag());
			userGroup.setUserGroupName(userGroupBean.getGroupName());
			userGroup.setGroupPoints(0l);
			userGroup.setGroupRank(0);
			userGroupHome.persist(userGroup);
			userGroupBean.setGroupId(userGroup.getUserGroupId());
			createUserGroupMapping(userGroupBean.getOwnerId(), userGroup.getUserGroupId(), userGroupBean.getTournamentId());
		}catch(PTWException exception){
			throw exception;
		}catch(Exception exception){
			exception.printStackTrace();
			throw new PTWException(PTWConstants.ERROR_CODE_DB_EXCEPTION, PTWConstants.ERROR_DESC_DB_EXCEPTION);
		}
		return userGroupBean;
	}
	
	@Override
	public void addUserToGroup(UserGroupInvitationBean groupInvitation) throws PTWException{
		UserGroupInvitationHome userGroupInvitationHome = new UserGroupInvitationHome(getSession());
		createUserGroupMapping(groupInvitation.getInviteeUserId(), groupInvitation.getGroupId(), groupInvitation.getTournamentId());
		try{
			List<UserGroupInvitation> invitations = userGroupInvitationHome.findByFilter(groupInvitation);
			if(invitations != null && !invitations.isEmpty()){
				for (UserGroupInvitation userGroupInvitation : invitations) {
					UserInvitationStatus status = new UserInvitationStatus();
					status.setInvitationStatusId(DBConstants.USER_INVITATION_STATUS_ACCEPTED);
					userGroupInvitation.setUserInvitationStatus(status);
					userGroupInvitationHome.merge(userGroupInvitation);
				}
			}
		}catch(Exception exception){
			exception.printStackTrace();
			throw new PTWException(PTWConstants.ERROR_CODE_DB_EXCEPTION, PTWConstants.ERROR_DESC_DB_EXCEPTION);
		}
	}

	private void createUserGroupMapping(Integer userId, Integer groupId, Integer tournamentId) {
		UserGroupMappingHome userGroupMappingHome = new UserGroupMappingHome(getSession());
		UserGroupMapping userGroupMapping = new UserGroupMapping();
		UserGroupMappingKey userGroupMappingKey = new UserGroupMappingKey();
		UserGroup userGroup = new UserGroup();
		userGroup.setUserGroupId(groupId);
		userGroupMappingKey.setUserGroup(userGroup);
		userGroupMappingKey.setUserId(userId);
		userGroupMapping.setTournamentId(tournamentId);
		userGroupMapping.setUserGroupMappingKey(userGroupMappingKey);
		userGroupMappingHome.persist(userGroupMapping);
	}
	
	@Override
	public void addUserToSystemGroup(UserGroupBean userGroupBean) throws PTWException{
		try{
			UserGroupInvitationHome userGroupInvitationHome = new UserGroupInvitationHome(getSession());
			createUserGroupMapping(userGroupBean.getUserId(), userGroupBean.getGroupId(), userGroupBean.getTournamentId());
		}catch(Exception exception){
			exception.printStackTrace();
			throw new PTWException(PTWConstants.ERROR_CODE_DB_EXCEPTION, PTWConstants.ERROR_DESC_DB_EXCEPTION);
		}
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
	public List<UserGroupBean> getUserCreatedGroups(UserGroupBean userGroupBean) throws PTWException {
		UserGroupHome userGroupHome = new UserGroupHome(this.getSession());
		List<UserGroupBean> userGroupBeanList= null;
		try{
			UserGroupBean queryBean = new UserGroupBean();
			queryBean.setOwnerId(userGroupBean.getOwnerId());
			queryBean.setTournamentId(userGroupBean.getTournamentId());
			List<UserGroup> userGroupLst = userGroupHome.findByFilter(queryBean);
			if(userGroupLst != null && !userGroupLst.isEmpty()){
				userGroupBeanList = new ArrayList<UserGroupBean>();
				for (UserGroup userGroup : userGroupLst) {
					UserGroupBean retUserGroupBean = getUserGroupBean(userGroup);
					userGroupBeanList.add(retUserGroupBean);
				}
			}
		}catch(Exception exception){
			exception.printStackTrace();
			throw new PTWException(PTWConstants.ERROR_CODE_DB_EXCEPTION, PTWConstants.ERROR_DESC_DB_EXCEPTION);
		}		
		return userGroupBeanList;
	}

	private UserGroupBean getUserGroupBean(UserGroup userGroup) {
		UserGroupBean userGroupBean = new UserGroupBean();
		userGroupBean.setGroupId(userGroup.getUserGroupId());
		userGroupBean.setTournamentId(userGroup.getTournament().getTournamentId());
		userGroupBean.setGroupCode(userGroup.getUserGroupCode());
		userGroupBean.setOwnerId(userGroup.getOwnerUser().getUserId());
		userGroupBean.setGroupName(userGroup.getUserGroupName());
		userGroupBean.setPrizeGroupFlag(userGroup.getPrizeIncludedFlag());
		return userGroupBean;
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
			List<UserGroupMapping> userGroups = userGroupMappingHome.findUserGroup(null, userGroupBean.getGroupId(), null);
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
	
	@Override
	public List<UserGroupBean> getUserGroup(UserGroupBean userGroupBean)  throws PTWException{
		UserGroupHome userGroupHome = new UserGroupHome(this.getSession());
		List<UserGroupBean> retUserGroup = null;
		try{
			List<UserGroup> userGroupList = userGroupHome.findByFilter(userGroupBean);
			if(userGroupList != null && !userGroupList.isEmpty()){
				retUserGroup = new ArrayList<UserGroupBean>();
				for (UserGroup userGroup : userGroupList) {
					UserGroupBean retUserGroupBean = getUserGroupBean(userGroup);
					retUserGroup.add(retUserGroupBean);
				}
			}
		}catch(Exception exception){
			exception.printStackTrace();
			throw new PTWException(PTWConstants.ERROR_CODE_DB_EXCEPTION, PTWConstants.ERROR_DESC_DB_EXCEPTION);
		}
		return retUserGroup;
	}

	@Override
	public void inviteUserToGroup(UserGroupInvitationBean userGroupInvitationBean) throws PTWException {
		UserGroupInvitationHome invitationHome = new UserGroupInvitationHome(getSession());
		try{
			UserGroupInvitation userGroupInvitation = new UserGroupInvitation();
			userGroupInvitation.setInviteeUserId(userGroupInvitationBean.getInviteeUserId());
			
			UserGroup userGroup = new UserGroup();
			userGroup.setUserGroupId(userGroupInvitationBean.getGroupId());
			userGroupInvitation.setUserGroup(userGroup);
			
			userGroupInvitation.setEmailId(userGroupInvitationBean.getEmailId());
			userGroupInvitation.setPhone(userGroupInvitationBean.getPhone());
			
			UserInvitationStatus status = new UserInvitationStatus();
			status.setInvitationStatusId(userGroupInvitationBean.getInvitationStatusId());
			userGroupInvitation.setUserInvitationStatus(status);
			invitationHome.persist(userGroupInvitation);
		}catch(Exception exception){
			exception.printStackTrace();
			throw new PTWException(PTWConstants.ERROR_CODE_DB_EXCEPTION, PTWConstants.ERROR_DESC_DB_EXCEPTION);
		}
		
	}

	@Override
	public List<UserGroupInvitationBean> getUserInvitations(UserBean userBean) throws PTWException {
		UserGroupInvitationHome invitationHome = new UserGroupInvitationHome(getSession());
		List<UserGroupInvitationBean> retList = null;
		try{
			UserGroupInvitationBean queryUserGroupInvitation = new UserGroupInvitationBean();
			queryUserGroupInvitation.setInviteeUserId(userBean.getUserId());
			queryUserGroupInvitation.setInvitationStatusId(DBConstants.USER_INVITATION_STATUS_INVITED);
			List<UserGroupInvitation> invitations = invitationHome.findByFilter(queryUserGroupInvitation);
			if(invitations != null && !invitations.isEmpty()){
				retList = new ArrayList<UserGroupInvitationBean>();
				for (UserGroupInvitation userGroupInvitation : invitations) {
					UserGroupInvitationBean retGroupInvitationBean = new UserGroupInvitationBean();
					retGroupInvitationBean.setUserGroupInvitationId(userGroupInvitation.getUserGroupInvitationId());
					retGroupInvitationBean.setEmailId(userGroupInvitation.getEmailId());
					retGroupInvitationBean.setGroupCode(userGroupInvitation.getUserGroup().getUserGroupCode());
					retGroupInvitationBean.setGroupId(userGroupInvitation.getUserGroup().getUserGroupId());
					retGroupInvitationBean.setGroupName(userGroupInvitation.getUserGroup().getUserGroupName());
					retGroupInvitationBean.setGroupOwnerId(userGroupInvitation.getUserGroup().getOwnerUser().getUserId());
					retGroupInvitationBean.setGroupOwnerName(userGroupInvitation.getUserGroup().getOwnerUser().getUserName());
					retGroupInvitationBean.setTournamentId(userGroupInvitation.getUserGroup().getTournament().getTournamentId());
					retGroupInvitationBean.setTournamentName(userGroupInvitation.getUserGroup().getTournament().getTournamentName());
					retGroupInvitationBean.setTournamentShortName(userGroupInvitation.getUserGroup().getTournament().getTournamentDescription());
					retGroupInvitationBean.setInvitationStatusId(userGroupInvitation.getUserInvitationStatus().getInvitationStatusId());
					retGroupInvitationBean.setInvitationStatusName(userGroupInvitation.getUserInvitationStatus().getInvitationStatusName());
					retList.add(retGroupInvitationBean);
				}
			}
		}catch(Exception exception){
			exception.printStackTrace();
			throw new PTWException(PTWConstants.ERROR_CODE_DB_EXCEPTION, PTWConstants.ERROR_DESC_DB_EXCEPTION);
		}
		return retList;
	}
	
	@Override
	public void updateUserIdForInvitation(UserBean userBean) throws PTWException {
		UserGroupInvitationHome invitationHome = new UserGroupInvitationHome(getSession());
		try{
			UserGroupInvitationBean queryInvitation = new UserGroupInvitationBean();
			queryInvitation.setEmailId(userBean.getEmail());
			queryInvitation.setPhone(userBean.getPhone());
			queryInvitation.setInvitationStatusId(DBConstants.USER_INVITATION_STATUS_INVITED);
			List<UserGroupInvitation> invitations = invitationHome.findByFilter(queryInvitation);
			if(invitations != null && !invitations.isEmpty()){
				for (UserGroupInvitation userGroupInvitation : invitations) {
					userGroupInvitation.setInviteeUserId(userBean.getUserId());
					invitationHome.merge(userGroupInvitation);
				}
			}
		}catch(Exception exception){
			exception.printStackTrace();
			throw new PTWException(PTWConstants.ERROR_CODE_DB_EXCEPTION, PTWConstants.ERROR_DESC_DB_EXCEPTION);
		}
	}

	@Override
	public List<UserGroupBean> getUserMappedGroup(UserGroupBean userGroupBean)throws PTWException {
		UserGroupMappingHome userGroupMappingHome = new UserGroupMappingHome(getSession());
		List<UserGroupBean> userGroups = null;
		try{
			List<UserGroupMapping> userGroupMappingList = userGroupMappingHome.findUserGroup(userGroupBean.getUserId(), null, userGroupBean.getTournamentId());
			if(userGroupMappingList != null && !userGroupMappingList.isEmpty()){
				userGroups = new ArrayList<UserGroupBean>();
				for (UserGroupMapping userGroupMapping : userGroupMappingList) {
					UserGroupBean dbUserGroupBean = new UserGroupBean();
					UserGroup userGroup = userGroupMapping.getUserGroupMappingKey().getUserGroup();
					dbUserGroupBean.setGroupId(userGroup.getUserGroupId());
					dbUserGroupBean.setGroupName(userGroup.getUserGroupName());
					dbUserGroupBean.setGroupCode(userGroup.getUserGroupCode());
					userGroups.add(dbUserGroupBean);
				}
			}
		}catch(Exception exception){
			exception.printStackTrace();
			throw new PTWException(PTWConstants.ERROR_CODE_DB_EXCEPTION, PTWConstants.ERROR_DESC_DB_EXCEPTION);
		}
		return userGroups;
	}

	@Override
	public void saveOneSignalRegistraion(OneSignalUserRegistrationBean registrationBean) throws PTWException{
		OneSignalUserRegistrationHome regHome = new OneSignalUserRegistrationHome(this.getSession());
		UserHome userHome = new UserHome(this.getSession());
		try{
			Users foundUser = userHome.findById(registrationBean.getUserId());
			if(foundUser != null){
				List<OneSignalUserRegistration> currentRegs = regHome.findOneSignalUserRegistrationByFilter(registrationBean.getUserId(), registrationBean.getOneSignalRegistrationId(), null);
				if(currentRegs == null || currentRegs.isEmpty()){
					OneSignalUserRegistration registration = new OneSignalUserRegistration();
					registration.setOneSignalRegistrationId(registrationBean.getOneSignalRegistrationId());
					Users user = new Users();
					user.setUserId(registrationBean.getUserId());
					registration.setUsers(user);
					regHome.persist(registration);
					foundUser.setPushRegistered(true);
					userHome.merge(foundUser);
				}
			}
		}catch(Exception exception){
			exception.printStackTrace();
			throw new PTWException(PTWConstants.ERROR_CODE_DB_EXCEPTION, PTWConstants.ERROR_DESC_DB_EXCEPTION);
		}
		
	}
	
	@Override
	public List<OneSignalUserRegistrationBean> getOneSignalRegistrations(Integer[] userList) throws PTWException{
		List<OneSignalUserRegistrationBean> responses = null;
		OneSignalUserRegistrationHome regHome = new OneSignalUserRegistrationHome(this.getSession());
		try{
			List<OneSignalUserRegistration> registrations = regHome.findOneSignalUserRegistrationByFilter(null, null, userList);
			if(registrations != null && !registrations.isEmpty()){
				responses = new ArrayList<OneSignalUserRegistrationBean>();
				for (OneSignalUserRegistration oneSignalUserRegistration : registrations) {
					OneSignalUserRegistrationBean response = new OneSignalUserRegistrationBean();
					response.setOneSignalRegistrationId(oneSignalUserRegistration.getOneSignalRegistrationId());
					response.setUserId(oneSignalUserRegistration.getUsers().getUserId());
					responses.add(response);
				}
			}
		}catch(Exception exception){
			exception.printStackTrace();
			throw new PTWException(PTWConstants.ERROR_CODE_DB_EXCEPTION, PTWConstants.ERROR_DESC_DB_EXCEPTION);
		}
		return responses;
	}

	@Override
	public List<TournamentFanClubBean> getTournamentSystemGroups(TournamentBean tournament) throws PTWException{
		List<TournamentFanClubBean> fanClubs = null;
		UserGroupHome userGroupHome = new UserGroupHome(this.getSession());
		try{
			List<UserGroup> userGroups = userGroupHome.findSystemGroupsForTournament(tournament.getTournamentId());
			if(userGroups != null && !userGroups.isEmpty()){
				fanClubs = new ArrayList<TournamentFanClubBean>();
				for (UserGroup userGroup : userGroups) {
					fanClubs.add(createTournamentFanClubBean(userGroup));
				}
			}
		}catch(Exception exception){
			exception.printStackTrace();
			throw new PTWException(PTWConstants.ERROR_CODE_DB_EXCEPTION, PTWConstants.ERROR_DESC_DB_EXCEPTION);
		}
		return fanClubs;
	}

	private TournamentFanClubBean createTournamentFanClubBean(UserGroup userGroup) {
		TournamentFanClubBean fanClub = new TournamentFanClubBean();
		fanClub.setGroupId(userGroup.getUserGroupId());
		fanClub.setGroupName(userGroup.getUserGroupName());
		fanClub.setClubPoints(userGroup.getGroupPoints());
		fanClub.setClubPosition(userGroup.getGroupRank());
		return fanClub;
	}
	
	@Override
	public List<TournamentFanClubBean> getUserFanClub(UserGroupBean userGroupBean) throws PTWException{
		UserGroupMappingHome userGroupMappingHome = new UserGroupMappingHome(this.getSession());
		List<TournamentFanClubBean> fanClubs = null;
		List<UserGroupMapping> mappingList = userGroupMappingHome.findSystemUserGroup(userGroupBean.getUserId(), userGroupBean.getTournamentId());
		if(mappingList != null && !mappingList.isEmpty()){
			fanClubs = new ArrayList<TournamentFanClubBean>();
			for (UserGroupMapping userGroupMapping : mappingList) {
				TournamentFanClubBean fanClub = createTournamentFanClubBean(userGroupMapping.getUserGroupMappingKey().getUserGroup());
				fanClubs.add(fanClub);
				
			}
		}
		return fanClubs;
	}

	@Override
	public Long getGroupPoints(TournamentFanClubBean tournamentFanClubBean) throws PTWException {
		UserScoreBoardHome userScoreBoardHome = new UserScoreBoardHome(this.getSession());
		Long totalPoints = userScoreBoardHome.getTotalPointsForGroups(tournamentFanClubBean.getGroupId(), tournamentFanClubBean.getTournamentId());
		return totalPoints;
	}

}
