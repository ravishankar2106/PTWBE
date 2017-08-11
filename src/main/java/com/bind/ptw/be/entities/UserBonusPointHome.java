package com.bind.ptw.be.entities;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

@SuppressWarnings("unchecked")
public class UserBonusPointHome {

	private Session session;
	
	public UserBonusPointHome(Session session){
		this.session = session;
	}
	
	public void save(UserBonusPoint persistentInstance){
		session.save(persistentInstance);
	}
	
	public void persist(UserBonusPoint transientInstance) {
		try {
			session.persist(transientInstance);
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public void remove(UserBonusPoint persistentInstance) {
		try {
			session.delete(persistentInstance);
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public List<UserBonusPoint> findByFilter(Integer userId, Integer contestId){
		Query query = null;
		StringBuilder queryToExecute = new StringBuilder();
		queryToExecute.append(QueryConstants.RETRIEVE_USER_BONUS_POINTS);
		queryToExecute.append("AND ubp.userId =:userId ");
		queryToExecute.append("AND ubp.contestId =:contestId ");
		query = session.createQuery(queryToExecute.toString());
		query.setParameter("userId", userId);
		query.setParameter("contestId", contestId);
		return query.list();
	}
	
	
	
}
