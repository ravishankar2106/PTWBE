package com.bind.ptw.be.entities;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.bind.ptw.be.dto.QuestionBean;
import com.bind.ptw.be.dto.TeamTypeBean;
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
	
	public List<UserAnswer> getUserAnswer(QuestionBean questionBean){
		Query query = null;
		StringBuilder queryToExecute = new StringBuilder();
		queryToExecute.append(QueryConstants.RETRIEVE_USER_ANSWER);
		queryToExecute.append("AND ua.questionId =: questionId");
		query = session.createQuery(queryToExecute.toString());
		query.setParameter("questionId", questionBean.getQuestionId());
		return query.list();
	}

	
}
