package com.bind.ptw.be.entities;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;


public class SportTypeHome {

	private Session session;
	
	public SportTypeHome(Session session){
		this.session = session;
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
