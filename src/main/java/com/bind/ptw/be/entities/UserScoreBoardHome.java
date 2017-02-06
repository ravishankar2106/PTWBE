package com.bind.ptw.be.entities;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.bind.ptw.be.util.StringUtil;

public class UserScoreBoardHome {
	
	private Session session;
	
	public UserScoreBoardHome(Session session){
		this.session = session;
	}
	
	public void persist(UserScoreBoard transientInstance) {
		try {
			session.persist(transientInstance);
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public void remove(UserScoreBoard persistentInstance) {
		try {
			session.delete(persistentInstance);
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public UserScoreBoard merge(UserScoreBoard detachedInstance) {
		try {
			UserScoreBoard result = (UserScoreBoard)session.merge(detachedInstance);
			return result;
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public UserScoreBoard findById(int id) {
		try {
			UserScoreBoard instance = (UserScoreBoard)session.get("UserScoreBoard", id);
			return instance;
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public List<UserScoreBoard> findByFilter( Integer tournamentId, Integer userId ) {
		
		Query query = null;
		
		try{
			StringBuilder queryToExecute = new StringBuilder();
			queryToExecute.append(QueryConstants.RETRIEVE_USER_SCOREBOARD);
			if(!StringUtil.isEmptyNull(userId)){
				queryToExecute.append("AND usb.userId =:userId ");
			}
			if(!StringUtil.isEmptyNull(tournamentId)){
				queryToExecute.append("AND usb.tournamentId =:tournamentId ");
			}
			
			query = session.createQuery(queryToExecute.toString());
			
			if(!StringUtil.isEmptyNull(userId)){
				query.setParameter("userId", userId);
			}
			if(!StringUtil.isEmptyNull(tournamentId)){
				query.setParameter("tournamentId", tournamentId);
			}
		
		}catch(RuntimeException e){
			throw e;
		}
		
		return query.list();
	}
	
		
}
