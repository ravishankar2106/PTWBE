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
	
	public List<Integer> getPointsForTournament( Integer tournamentId ) {
		
		Query query = null;
		
		try{
			StringBuilder queryToExecute = new StringBuilder();
			queryToExecute.append("SELECT DISTINCT(totalPoints) FROM UserScoreBoard where tournamentId =:tournamentId ");
			/*if(userIdList != null && !userIdList.isEmpty()){
				queryToExecute.append("AND userId IN (:userIdList) ");
			}*/
			          
			query = session.createQuery(queryToExecute.toString());
			
			query.setParameter("tournamentId", tournamentId);
			/*if(userIdList != null && !userIdList.isEmpty()){
				query.setParameter("statusIdArr", userIdList);
			}*/
			
		}catch(RuntimeException e){
			throw e;
		}
		
		return query.list();
	}

	public int updateRanks(Integer points, Integer rank){
		Query query = null;
		
		try{
			StringBuilder queryToExecute = new StringBuilder();
			queryToExecute.append("Update UserScoreBoard SET rank =:rank ");
			queryToExecute.append(" where totalPoints =:points");
			query = session.createQuery(queryToExecute.toString());
			
			query.setParameter("rank", rank);
			query.setParameter("points", points);
			
			int returnInt = query.executeUpdate();
			return returnInt;
			
		}catch(RuntimeException e){
			throw e;
		}
	}
		
}
