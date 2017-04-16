package com.bind.ptw.be.dao;

import java.util.List;

import com.bind.ptw.be.dto.CityBean;
import com.bind.ptw.be.dto.ContestBean;
import com.bind.ptw.be.dto.OneSignalUserRegistrationBean;
import com.bind.ptw.be.dto.TournamentBean;
import com.bind.ptw.be.dto.TournamentFanClubBean;
import com.bind.ptw.be.dto.UserBean;
import com.bind.ptw.be.dto.UserConfirmationBean;
import com.bind.ptw.be.dto.UserGroupBean;
import com.bind.ptw.be.dto.UserGroupInvitationBean;
import com.bind.ptw.be.dto.UserScoreBoardBean;
import com.bind.ptw.be.dto.UserTournamentBean;
import com.bind.ptw.be.dto.UserTournmentRegisterBean;
import com.bind.ptw.be.util.PTWException;

public interface UserDao {
	
	UserBean save(UserBean userBean);
	void updateUser(UserBean userBean);
	void updateUserStatus(UserBean userBean);
	List<UserBean> getUsers(UserBean userBean, Boolean adminFlag);
	void createUserToken(UserBean user)throws PTWException;
	void createConfirmationCode(UserConfirmationBean userConfirmationBean) throws PTWException;
	UserConfirmationBean getUserConfirmation(UserConfirmationBean userConfirmationBean);
	void updateConfirmationRetryCount(UserConfirmationBean userConfirmationBean);
	void deleteConfirmationCode(UserConfirmationBean userConfirmationBean);
	List<CityBean> getCities();
	CityBean getCity(CityBean cityBean);
	void updateBonusPoints(ContestBean contestBean, List<Integer> userIdList) throws PTWException;
	void registerUserToTournament(UserTournmentRegisterBean userTournament) throws PTWException;
	List<UserTournamentBean> getUserRegisteredTournament(UserBean userBean) throws PTWException;
	UserGroupBean createUserGroup(UserGroupBean userGroupBean) throws PTWException;
	void updateUserGroup(UserGroupBean userGroupBean) throws PTWException;
	void deleteUserGroup(UserGroupBean userGroungBean) throws PTWException;
	void inviteUserToGroup(UserGroupInvitationBean userGroupInvitationBean) throws PTWException;
	List<UserGroupBean> getUserGroup(UserGroupBean userGroupBean) throws PTWException;
	void updateUserIdForInvitation(UserBean userBean) throws PTWException;
	List<UserGroupInvitationBean> getUserInvitations(UserBean userBean) throws PTWException;
	void addUserToGroup(UserGroupInvitationBean groupInvitation) throws PTWException;
	List<UserGroupBean> getUserMappedGroup(UserGroupBean userGroupBean) throws PTWException;
	List<UserGroupBean> getUserCreatedGroups(UserGroupBean userGroupBean) throws PTWException;
	void saveOneSignalRegistraion(OneSignalUserRegistrationBean registrationBean) throws PTWException;
	List<OneSignalUserRegistrationBean> getOneSignalRegistrations(Integer[] userId) throws PTWException;
	List<TournamentFanClubBean> getTournamentSystemGroups(TournamentBean tournament) throws PTWException;
	List<TournamentFanClubBean> getUserFanClub(UserGroupBean userGroupBean) throws PTWException;
	void addUserToSystemGroup(UserGroupBean userGroupBean) throws PTWException;
	Long getGroupPoints(TournamentFanClubBean tournamentFanClubBean) throws PTWException;
	Integer[] getGroupUsers(UserGroupBean userGroupBean) throws PTWException;
	List<UserScoreBoardBean> getUserPointsForQuestions(Integer[] userIds, Integer[] questionIds);
}
