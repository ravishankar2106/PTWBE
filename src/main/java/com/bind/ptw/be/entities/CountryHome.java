package com.bind.ptw.be.entities;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.util.StringUtils;

import com.bind.ptw.be.dto.CountryBean;
import com.bind.ptw.be.util.StringUtil;

public class CountryHome {

	private Session session;
	
	public CountryHome(Session session){
		this.session = session;
	}
	
	public void persist(Country transientInstance) {
		try {
			session.persist(transientInstance);
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public void remove(Country persistentInstance) {
		try {
			session.delete(persistentInstance);
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public Country merge(Country detachedInstance) {
		try {
			Country result = (Country)session.merge(detachedInstance);
			return result;
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public Country findById(int id) {
		try {
			Country instance = (Country)session.get(Country.class, id);
			return instance;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public List<Country> findCountryByFilter(CountryBean countryBean){
		Query query = null;
		
		try{
			StringBuilder queryToExecute = new StringBuilder();
			queryToExecute.append(QueryConstants.RETRIEVE_COUNTRIES);
			boolean firstQuery=false;
			if( countryBean != null ) {
				if(!StringUtils.isEmpty(countryBean.getCountryId())){
					queryToExecute.append("AND cy.countryId =:countryId");
				}else{
					if(!StringUtil.isEmptyNull(countryBean.getCountryName()) || !StringUtil.isEmptyNull(countryBean.getCountryShortName())){
						queryToExecute.append("AND (");
						if(!StringUtil.isEmptyNull(countryBean.getCountryName())){
							firstQuery=true;
							queryToExecute.append("cy.countryName =:countryName ");
						}
						
						if(!StringUtil.isEmptyNull(countryBean.getCountryShortName())){
							if(firstQuery){
								queryToExecute.append("OR ");
							}
							queryToExecute.append("cy.countryShortName =:countryShortName ");
						}
						queryToExecute.append(")");
					}
				}
			}
			System.out.println(queryToExecute.toString());
			query = session.createQuery(queryToExecute.toString());
			
			if(countryBean != null){
				if(!StringUtils.isEmpty(countryBean.getCountryId())){
					query.setParameter("countryId", countryBean.getCountryId());;
				}else{
				
					if(!StringUtil.isEmptyNull(countryBean.getCountryName())){
						query.setParameter("countryName", countryBean.getCountryName());
					}
					if(!StringUtil.isEmptyNull(countryBean.getCountryShortName())){
						query.setParameter("countryShortName", countryBean.getCountryShortName());
					}
				}
				
			}
		}catch(RuntimeException e){
			throw e;
		}
		
		return query.list();
	}
}
