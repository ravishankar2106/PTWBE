package com.bind.ptw.be.entities;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.bind.ptw.be.dto.UserConfirmationBean;

@SuppressWarnings("unchecked")
public class UserConfirmationHome {
	
	private Session session;
	
	public UserConfirmationHome(Session session){
		this.session = session;
	}
	
	public void save(UserConfirmation userConfirmation){
		session.save(userConfirmation);
	}
	
	public void remove(UserConfirmation persistentInstance) {
		try {
			session.delete(persistentInstance);
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public UserConfirmation merge(UserConfirmation detachedInstance) {
		try {
			UserConfirmation result = (UserConfirmation)session.merge(detachedInstance);
			return result;
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public UserConfirmation findById(int id) {
		try {
			UserConfirmation instance = (UserConfirmation)session.get(UserConfirmation.class, id);
			return instance;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public UserConfirmation findUsersByFilters( UserConfirmationBean userRequest ) {
		
		Query query = null;
		
		try{
			String queryToExecute = QueryConstants.RETRIEVE_USER_CONFIRMATIONS;
			
			if( userRequest != null ) {
				
				if(userRequest.getUserId() != null){
					queryToExecute += "AND uc.user.userId =:userId ";
				}
			}
            
			query = session.createQuery(queryToExecute);
			if(userRequest != null){
				if(userRequest.getUserId() != null){
					query.setParameter("userId", userRequest.getUserId());
				}
			}
		}catch(RuntimeException e){
			throw e;
		}
		
		UserConfirmation userConfirmation = null;
		List<UserConfirmation> userConfimationList = query.list();
		if(userConfimationList != null && !userConfimationList.isEmpty()){
			userConfirmation = userConfimationList.get(0);
		}
		return userConfirmation;
	}
	
}
