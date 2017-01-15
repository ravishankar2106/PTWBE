package com.bind.ptw.be.services;

import com.bind.ptw.be.dto.CityBeanList;
import com.bind.ptw.be.dto.UserBean;
import com.bind.ptw.be.dto.UserConfirmationBean;

public interface UserService {
	UserBean createUser(UserBean inputUserDto);
	UserBean authenticateUser(UserBean authUser);
	UserConfirmationBean confirmUser(UserConfirmationBean userConfirmationBean);
	CityBeanList getCities();
}
