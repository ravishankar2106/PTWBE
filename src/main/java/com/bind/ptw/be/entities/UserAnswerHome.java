package com.bind.ptw.be.entities;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import com.bind.ptw.be.util.StringUtil;


public class UserAnswerHome {

	private Session session;
	
	public UserAnswerHome(Session session){
		this.session = session;
	}
	
	public void persist(UserAnswer transientInstance) {
		try {
			session.persist(transientInstance);
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public void remove(UserAnswer persistentInstance) {
		try {
			session.delete(persistentInstance);
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public List<UserAnswer> getUserAnswer(Integer userId, Integer questionId){
		Query query = null;
		StringBuilder queryToExecute = new StringBuilder();
		queryToExecute.append(QueryConstants.RETRIEVE_USER_ANSWER);
		queryToExecute.append("AND ua.userId =:userId ");
		queryToExecute.append("AND ua.questionId =:questionId");
		query = session.createQuery(queryToExecute.toString());
		query.setParameter("userId", userId);
		query.setParameter("questionId", questionId);
		return query.list();
	}
	
	public List<UserAnswer> findByAnswerOption(Integer answerOptionId){
		Query query = null;
		StringBuilder queryToExecute = new StringBuilder();
		queryToExecute.append(QueryConstants.RETRIEVE_USER_ANSWER);
		queryToExecute.append("AND ua.answerOption.answerOptionId =:answerOptionId ");
		query = session.createQuery(queryToExecute.toString());
		query.setParameter("answerOptionId", answerOptionId);
		return query.list();
	}

	public void deleteUserAnswer(Integer userId, Integer[] questionIdList){
		Query query = null;
		StringBuilder queryToExecute = new StringBuilder();
		queryToExecute.append("DELETE FROM UserAnswer ua where ua.userId = ");
		queryToExecute.append(userId);
		queryToExecute.append(" ");
		queryToExecute.append("AND ua.questionId IN ( ");
		queryToExecute.append(StringUtil.convertToTokens(questionIdList));
		queryToExecute.append(") ");
		query = session.createQuery(queryToExecute.toString());
		query.executeUpdate();
	}
	
	
	public void saveUserPoints(Integer answerOptionId, Integer pointsScored) {
		Query query = null;
		StringBuilder queryToExecute = new StringBuilder();
		queryToExecute.append("Update UserAnswer ua set ua.pointsScored =:pointsScored ");
		queryToExecute.append("Where ua.answerOption.answerOptionId =:answerOptionId");
		query = session.createQuery(queryToExecute.toString());
		query.setParameter("answerOptionId", answerOptionId);
		query.setParameter("pointsScored", pointsScored);
		query.executeUpdate();
		
	}
	
	public List<Object> getUserScoreForQuestions(Integer[] userId, Integer[] questionId){
		StringBuilder queryToExecute = new StringBuilder();
		queryToExecute.append("select SUM(POINTS_SCORED) AS POINTS, ");
		queryToExecute.append("USER_ID AS USER_ID ");
		queryToExecute.append("from USER_ANSWERS where CONTEST_QUESTION_ID IN (");
		queryToExecute.append(StringUtil.convertToTokens(questionId));
		queryToExecute.append(") AND USER_ID IN (");
		queryToExecute.append(StringUtil.convertToTokens(userId));
		queryToExecute.append(") GROUP BY USER_ID ORDER BY POINTS DESC;");
		SQLQuery query = session.createSQLQuery(queryToExecute.toString());
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		return query.list();
	}
}
