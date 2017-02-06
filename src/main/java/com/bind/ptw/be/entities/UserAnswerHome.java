package com.bind.ptw.be.entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;


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
		queryToExecute.append(appendQuestionId(questionIdList));
		queryToExecute.append(")");
		query = session.createQuery(queryToExecute.toString());
		query.executeUpdate();
	}
	
	private String appendQuestionId(Integer[] questionIdList){
		StringBuilder strBuilder = new StringBuilder();
		int index = 0;
		for (Integer questionId : questionIdList) {
			strBuilder.append(questionId);
			index++;
			if(index < questionIdList.length){
				strBuilder.append(",");
			}
		}
		
		return strBuilder.toString();
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
}
