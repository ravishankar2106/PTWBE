package com.bind.ptw.be.entities;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.util.StringUtils;

import com.bind.ptw.be.util.StringUtil;

public class OneSignalUserRegistrationHome {

	private Session session;
	
	public OneSignalUserRegistrationHome(Session session){
		this.session = session;
	}
	
	public void persist(OneSignalUserRegistration transientInstance) {
		try {
			session.persist(transientInstance);
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public void remove(OneSignalUserRegistration persistentInstance) {
		try {
			session.delete(persistentInstance);
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public void merge(OneSignalUserRegistration detachedInstance) {
		try {
			session.saveOrUpdate(detachedInstance);
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public List<OneSignalUserRegistration> findOneSignalUserRegistrationByFilter(String userId, String oneSignalUserRegitrationId){
		Query query = null;
		
		try{
			StringBuilder queryToExecute = new StringBuilder();
			queryToExecute.append(QueryConstants.RETRIEVE_ONE_SIGNAL_REGISTRATIONS);
			if(!StringUtils.isEmpty(userId)){
				queryToExecute.append("AND osur.user.userId =:userId ");
			}
			if(!StringUtil.isEmptyNull(oneSignalUserRegitrationId)){
				queryToExecute.append("AND osur.oneSignalRegistrationId =:oneSignalRegistrationId ");
			}
			queryToExecute.append("ORDER BY osur.oneSignalUserRegistraionId DESC ");
			query = session.createQuery(queryToExecute.toString());
			
			if(!StringUtils.isEmpty(userId)){
				query.setParameter("userId", userId);;
			}else if(!StringUtil.isEmptyNull(oneSignalUserRegitrationId)){
				query.setParameter("oneSignalUserRegitrationId", oneSignalUserRegitrationId);
			}
		}catch(RuntimeException e){
			throw e;
		}
		
		return query.list();
	}
}
