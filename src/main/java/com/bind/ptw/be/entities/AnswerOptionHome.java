package com.bind.ptw.be.entities;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.bind.ptw.be.dto.QuestionBean;
import com.bind.ptw.be.util.StringUtil;

public class AnswerOptionHome {

	private Session session;
	
	public AnswerOptionHome(Session session){
		this.session = session;
	}
	
	public void persist(AnswerOption transientInstance) {
		try {
			session.persist(transientInstance);
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public void remove(AnswerOption persistentInstance) {
		try {
			session.delete(persistentInstance);
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public AnswerOption merge(AnswerOption detachedInstance) {
		try {
			AnswerOption result = (AnswerOption)session.merge(detachedInstance);
			return result;
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public AnswerOption findById(int id) {
		try {
			AnswerOption instance = (AnswerOption)session.get(AnswerOption.class, id);
			return instance;
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public void deleteAnswerForQuestion(QuestionBean questionBean){
		try{
			String queryString = QueryConstants.DELETE_QUESTION_ANSWERS;
			session.persist(queryString);
		}catch(RuntimeException e){
			throw e;
		}
	}
	
	public List<AnswerOption> findAnswerOptionByFilter(QuestionBean questionBean){
		Query query = null;
		
		try{
			StringBuilder queryToExecute = new StringBuilder();
			queryToExecute.append(QueryConstants.RETRIEVE_QUESTION_ANSWERS);
			if( questionBean != null ) {
				if(!StringUtil.isEmptyNull(questionBean.getQuestionId())){
					queryToExecute.append("AND ao.question.questionId =:questionId ");
				}
			}
			query = session.createQuery(queryToExecute.toString());
			
			if(questionBean != null){
				if(!StringUtil.isEmptyNull(questionBean.getQuestionId())){
					query.setParameter("questionId", questionBean.getQuestionId());
				}
			}
		}catch(RuntimeException e){
			throw e;
		}
		
		return query.list();
	}
	
}
