package com.bind.ptw.be.services;

import com.bind.ptw.be.dto.BaseBean;
import com.bind.ptw.be.dto.CityBeanList;
import com.bind.ptw.be.dto.UserBean;
import com.bind.ptw.be.dto.UserConfirmationBean;
import com.bind.ptw.be.dto.UserGroupBean;
import com.bind.ptw.be.dto.UserPasswordBean;
import com.bind.ptw.be.dto.UserTournamentBeanList;
import com.bind.ptw.be.dto.UserTournmentRegisterBean;

public interface UserService {
	UserBean createUser(UserBean inputUserDto);
	UserBean authenticateUser(UserBean authUser, Boolean adminFlag);
	UserConfirmationBean confirmUser(UserConfirmationBean userConfirmationBean);
	CityBeanList getCities();
	BaseBean registerUserToTournament(UserTournmentRegisterBean userTournament);
	UserTournamentBeanList getUserRegisterTournament(UserBean userBean);
	BaseBean resetPassword(UserBean userBean);
	BaseBean updatePassword(UserPasswordBean userPasswordBean);
	UserGroupBean createUserGroup(UserGroupBean userGroupBean);
	BaseBean updateUserGroup(UserGroupBean userGroupBean);
}
