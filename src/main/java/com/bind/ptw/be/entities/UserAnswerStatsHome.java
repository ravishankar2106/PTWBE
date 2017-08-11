package com.bind.ptw.be.entities;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.bind.ptw.be.util.StringUtil;

@SuppressWarnings("unchecked")
public class UserAnswerStatsHome {

	private Session session;
	
	public UserAnswerStatsHome(Session session){
		this.session = session;
	}
	
	public void persist(UserAnswerStats transientInstance) {
		try {
			session.persist(transientInstance);
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public void remove(UserAnswerStats persistentInstance) {
		try {
			session.delete(persistentInstance);
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public UserAnswerStats merge(UserAnswerStats detachedInstance) {
		try {
			UserAnswerStats result = (UserAnswerStats)session.merge(detachedInstance);
			return result;
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public UserAnswerStats findById(int id) {
		try {
			UserAnswerStats instance = (UserAnswerStats)session.get(UserAnswerStats.class, id);
			return instance;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public List<UserAnswerStats> findAnswerStatsForUser(Integer userId, Integer tournamentId){
		Query query = null;
		
		try{
			StringBuilder queryToExecute = new StringBuilder();
			queryToExecute.append(QueryConstants.RETRIEVE_USER_ANSWER_STATS);
			queryToExecute.append("AND uas.userId =:userId ");
			if( !StringUtil.isEmptyNull(tournamentId) ) {
				queryToExecute.append("AND uas.tournamentId =:tournamentId ");
			}
			query = session.createQuery(queryToExecute.toString());
			
			query.setParameter("userId", userId);
			if(!StringUtil.isEmptyNull(tournamentId)){
				query.setParameter("tournamentId", tournamentId);
			}
		}catch(RuntimeException e){
			throw e;
		}
		
		return query.list();
	}
}
