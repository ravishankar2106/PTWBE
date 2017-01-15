package com.bind.ptw.be.dao;

import java.util.List;

import com.bind.ptw.be.dto.CityBean;
import com.bind.ptw.be.dto.UserBean;
import com.bind.ptw.be.dto.UserConfirmationBean;
import com.bind.ptw.be.util.PTWException;

public interface UserDao {
	
	UserBean save(UserBean userBean);
	void updateUser(UserBean userBean);
	void updateUserStatus(UserBean userBean);
	List<UserBean> getUsers(UserBean userBean);
	void createUserToken(UserBean user)throws PTWException;
	void createConfirmationCode(UserConfirmationBean userConfirmationBean) throws PTWException;
	UserConfirmationBean getUserConfirmation(UserConfirmationBean userConfirmationBean);
	void updateConfirmationRetryCount(UserConfirmationBean userConfirmationBean);
	void deleteConfirmationCode(UserConfirmationBean userConfirmationBean);
	List<CityBean> getCities();
	CityBean getCity(CityBean cityBean);
}
