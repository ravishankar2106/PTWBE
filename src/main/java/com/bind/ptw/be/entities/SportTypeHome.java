package com.bind.ptw.be.entities;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.bind.ptw.be.dto.SportTypeBean;
import com.bind.ptw.be.util.StringUtil;


public class SportTypeHome {

	private Session session;
	
	public SportTypeHome(Session session){
		this.session = session;
	}
	
	public List<SportType> findSportType( SportTypeBean sportTypeBean ) {
		
		Query query = null;
		
		try{
			StringBuilder queryToExecuteBuilder = new StringBuilder();
			queryToExecuteBuilder.append(QueryConstants.RETRIEVE_SPORT_TYPE);
			if(!StringUtil.isEmptyNull(sportTypeBean.getSportTypeId())){
				queryToExecuteBuilder.append("AND st.sportTypeId = :sportTypeId ");
			}
			if(!StringUtil.isEmptyNull(sportTypeBean.getSportTypeName())){
				queryToExecuteBuilder.append("AND st.sportTypeId = :sportTypeName ");
			}
            
			query = session.createQuery(queryToExecuteBuilder.toString());
			
			if(!StringUtil.isEmptyNull(sportTypeBean.getSportTypeId())){
				query.setParameter("sportTypeId", sportTypeBean.getSportTypeId());
			}
			if(!StringUtil.isEmptyNull(sportTypeBean.getSportTypeName())){
				query.setParameter("sportTypeName", sportTypeBean.getSportTypeName());
			}
			
		
		}catch(RuntimeException e){
			throw e;
		}
		
		return query.list();
	}
	
	public void persist(CountrySportTypeMapping transientInstance) {
		try {
			session.persist(transientInstance);
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public void remove(CountrySportTypeMapping persistentInstance) {
		try {
			session.delete(persistentInstance);
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public List<CountrySportTypeMapping> getCountrySportTypeMappings(Integer sportTypeId){
		Query query = null;
		StringBuilder queryToExecute = new StringBuilder();
		queryToExecute.append(QueryConstants.RETRIEVE_COUNTRIES_FOR_SPORT);
		queryToExecute.append("AND csMapping.countrySportTypeMappingKey.sportTypeId = ");
		queryToExecute.append(sportTypeId);
		query = session.createQuery(queryToExecute.toString());
		return query.list();
	}

	
}
