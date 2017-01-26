package com.bind.ptw.be.entities;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.util.StringUtils;

import com.bind.ptw.be.dto.ContestBean;
import com.bind.ptw.be.util.StringUtil;

public class QuestionHome {

	private Session session;
	
	public QuestionHome(Session session){
		this.session = session;
	}
	
	public void persist(Question transientInstance) {
		try {
			session.persist(transientInstance);
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public void remove(Question persistentInstance) {
		try {
			session.delete(persistentInstance);
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public Question merge(Question detachedInstance) {
		try {
			Question result = (Question)session.merge(detachedInstance);
			return result;
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public Question findById(int id) {
		try {
			Question instance = (Question)session.get(Question.class, id);
			return instance;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public List<Question> findQuestionByFilter(ContestBean contestBean){
		Query query = null;
		
		try{
			StringBuilder queryToExecute = new StringBuilder();
			queryToExecute.append(QueryConstants.RETRIEVE_QUESTIONS);
			if( contestBean != null ) {
				if(!StringUtil.isEmptyNull(contestBean.getContestId())){
					queryToExecute.append("AND q.contest.contestId =:contestId ");
				}
			}
			query = session.createQuery(queryToExecute.toString());
			
			if(contestBean != null){
				if(!StringUtil.isEmptyNull(contestBean.getContestId())){
					query.setParameter("contestId", contestBean.getContestId());
				}
			}
		}catch(RuntimeException e){
			throw e;
		}
		
		return query.list();
	}
	
}
