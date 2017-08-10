package com.bind.ptw.be.services;

import com.bind.ptw.be.dto.BaseBean;
import com.bind.ptw.be.dto.CityBeanList;
import com.bind.ptw.be.dto.OneSignalUserRegistrationBean;
import com.bind.ptw.be.dto.TournamentBean;
import com.bind.ptw.be.dto.TournamentFanClubBean;
import com.bind.ptw.be.dto.TournamentFanClubList;
import com.bind.ptw.be.dto.UserBean;
import com.bind.ptw.be.dto.UserConfirmationBean;
import com.bind.ptw.be.dto.UserGroupBean;
import com.bind.ptw.be.dto.UserGroupBeanList;
import com.bind.ptw.be.dto.UserGroupInvitationBean;
import com.bind.ptw.be.dto.UserGroupInvitationBeanList;
import com.bind.ptw.be.dto.UserPasswordBean;
import com.bind.ptw.be.dto.UserTournamentBeanList;
import com.bind.ptw.be.dto.UserTournmentRegisterBean;

public interface UserService {
	UserBean createUser(UserBean inputUserDto);
	UserBean getUser(String login);
	UserBean authenticateUser(UserBean authUser, Boolean adminFlag);
	UserConfirmationBean confirmUser(UserConfirmationBean userConfirmationBean);
	CityBeanList getCities();
	BaseBean registerUserToTournament(UserTournmentRegisterBean userTournament);
	UserTournamentBeanList getUserRegisterTournament(UserBean userBean);
	BaseBean resetPassword(UserBean userBean);
	BaseBean updatePassword(UserPasswordBean userPasswordBean);
	UserGroupBean createUserGroup(UserGroupBean userGroupBean);
	BaseBean updateUserGroup(UserGroupBean userGroupBean);
	BaseBean deleteUserOwnerGroup(UserGroupBean userGroupBean);
	BaseBean inviteUserToGroup(UserGroupInvitationBean userGroupInvitationBean);
	UserGroupInvitationBeanList getPendingGrounInvitation(UserBean userBean);
	BaseBean addUserToGroup(UserGroupInvitationBean userGroupInvitationBean);
	UserGroupBeanList getUserGroups(UserGroupBean userGroupBean);
	UserGroupBeanList getUserOwnedGroup(UserGroupBean userGroupBean);
	BaseBean registerUserToPush(OneSignalUserRegistrationBean registrationBean);
	TournamentFanClubList getTournamentFanGroups(TournamentBean tournament);
	BaseBean addUserToFanGroup(UserGroupBean userGroupBean);
	TournamentFanClubBean getUserTournamentFanGroups(UserGroupBean userGroupBean);
}
