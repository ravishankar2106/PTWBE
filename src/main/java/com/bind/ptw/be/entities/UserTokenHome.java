package com.bind.ptw.be.entities;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.bind.ptw.be.util.StringUtil;

public class UserTokenHome {
	
	private Session session;
	
	public UserTokenHome(Session session){
		this.session = session;
	}
	
	public void persist(UserToken transientInstance) {
		try {
			session.persist(transientInstance);
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public void remove(UserToken persistentInstance) {
		try {
			session.delete(persistentInstance);
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public UserToken merge(UserToken detachedInstance) {
		try {
			UserToken result = (UserToken)session.merge(detachedInstance);
			return result;
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public UserToken findById(int id) {
		try {
			UserToken instance = (UserToken)session.get("UserToken", id);
			return instance;
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public List<UserToken> findByFilter( Integer userId, String token ) {
		
		Query query = null;
		
		try{
			StringBuilder queryToExecute = new StringBuilder();
			queryToExecute.append(QueryConstants.RETRIEVE_USER_TOKEN);
			queryToExecute.append("AND ut.userId =:userId ");
			if(!StringUtil.isEmptyNull(token)){
				queryToExecute.append("AND ut.token =:token ");
			}
			
			query = session.createQuery(queryToExecute.toString());
			
			query.setParameter("userId", userId);
			if(!StringUtil.isEmptyNull(token)){
				query.setParameter("token", token);
			}
		
		}catch(RuntimeException e){
			throw e;
		}
		
		return query.list();
	}
	
		
}
