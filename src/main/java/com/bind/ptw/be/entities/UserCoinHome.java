package com.bind.ptw.be.entities;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.util.StringUtils;

import com.bind.ptw.be.dto.QuestionBean;
import com.bind.ptw.be.util.StringUtil;

public class UserCoinHome {

	private Session session;
	
	public UserCoinHome(Session session){
		this.session = session;
	}
	
	public void persist(UserCoin transientInstance) {
		try {
			session.persist(transientInstance);
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public UserCoin merge(UserCoin userCoin) {
		try {
			UserCoin result = (UserCoin)session.merge(userCoin);
			return result;
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public UserCoin findById(int id) {
		try {
			UserCoin instance = (UserCoin)session.get(UserCoin.class, id);
			return instance;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public UserCoin getUserCoins(int userId) {
		try {
			Query query = null;
			
				StringBuilder queryToExecute = new StringBuilder();
				queryToExecute.append(QueryConstants.RETRIEVE_USER_COINS);
				queryToExecute.append("AND uc.userId =:userId ");
				query = session.createQuery(queryToExecute.toString());
				query.setParameter("userId", userId);
				UserCoin userCoin = (UserCoin)query.uniqueResult();
				return userCoin;
		} catch (RuntimeException re) {
			throw re;
		}
	}

	
}
