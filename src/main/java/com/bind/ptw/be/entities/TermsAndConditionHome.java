package com.bind.ptw.be.entities;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

@SuppressWarnings("unchecked")
public class TermsAndConditionHome {

	private Session session;
	
	public TermsAndConditionHome(Session session){
		this.session = session;
	}
	
	public void save(TermsCondition persistentInstance){
		session.save(persistentInstance);
	}
	
	public void persist(TermsCondition transientInstance) {
		try {
			session.persist(transientInstance);
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public void remove(TermsCondition persistentInstance) {
		try {
			session.delete(persistentInstance);
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public Integer getMaxTOCGroupId(){
		Integer ret = 0;
		try{
			String strBuilder = "Select MAX(TOC_GROUP_ID) from TOC_MASTER";
			Query query = session.createSQLQuery(strBuilder);
			Number num = ((Number)query.uniqueResult());
			if(num != null){
				ret = num.intValue();
			}else{
				ret = 0;
			}
			
			
		}catch (RuntimeException re) {
			throw re;
		}
		return ret;
	}

	public List<TermsCondition> getTOCForContest(Integer contestId) {
		Query query = null;
		StringBuilder strBuilder = new StringBuilder();
		try{
			strBuilder.append(QueryConstants.RETRIEVE_TANDC);
			strBuilder.append("AND toc.tocGroupId = ");
			strBuilder.append("(select c.tocGroupId from Contest c where c.contestId =:contestId) ");
			strBuilder.append("Order By toc.tocId ASC");
			query = session.createQuery(strBuilder.toString());
			query.setParameter("contestId", contestId);
		}catch (RuntimeException re) {
			throw re;
		}
		return query.list();
	}

}
