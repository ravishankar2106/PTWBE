package com.bind.ptw.be.entities;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import com.bind.ptw.be.util.StringUtil;

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
	
	public List<UserBonusPoint> findByFilter(Integer userId, Integer[] contestId){
		Query query = null;
		StringBuilder queryToExecute = new StringBuilder();
		queryToExecute.append(QueryConstants.RETRIEVE_USER_BONUS_POINTS);
		queryToExecute.append("AND ubp.userId =:userId ");
		queryToExecute.append("AND ubp.contestId IN :contestId ");
		query = session.createQuery(queryToExecute.toString());
		query.setParameter("userId", userId);
		query.setParameterList("contestId", contestId);
		return query.list();
	}
	
	public List<Object> getUserScoreForQuestions(Integer[] userId, Integer[] contest){
		StringBuilder queryToExecute = new StringBuilder();
		queryToExecute.append("select SUM(POINTS_SCORED) AS POINTS, ");
		queryToExecute.append("USER_ID AS USER_ID ");
		queryToExecute.append("from USER_BONUS_POINTS where CONTEST_ID IN (");
		queryToExecute.append(StringUtil.convertToTokens(contest));
		queryToExecute.append(") AND USER_ID IN (");
		queryToExecute.append(StringUtil.convertToTokens(userId));
		queryToExecute.append(") GROUP BY USER_ID;");
		SQLQuery query = session.createSQLQuery(queryToExecute.toString());
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		return query.list();
	}
	
	
}
