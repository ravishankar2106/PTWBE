package com.bind.ptw.be.entities;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.bind.ptw.be.util.StringUtil;

@SuppressWarnings("unchecked")
public class UserTournamentRegistrationHome {
	
	private Session session;
	
	public UserTournamentRegistrationHome(Session session){
		this.session = session;
	}
	
	public void persist(UserTournamentRegistration transientInstance) {
		try {
			session.persist(transientInstance);
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public void remove(UserTournamentRegistration persistentInstance) {
		try {
			session.delete(persistentInstance);
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public UserTournamentRegistration merge(UserTournamentRegistration detachedInstance) {
		try {
			UserTournamentRegistration result = (UserTournamentRegistration)session.merge(detachedInstance);
			return result;
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public UserTournamentRegistration findById(int id) {
		try {
			UserTournamentRegistration instance = (UserTournamentRegistration)session.get("UserTournamentRegistration", id);
			return instance;
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public List<UserTournamentRegistration> findByFilter( Integer tournamentId, Integer userId ) {
		
		Query query = null;
		
		try{
			StringBuilder queryToExecute = new StringBuilder();
			queryToExecute.append(QueryConstants.RETRIEVE_TOURNAMENT_USERS);
			if(!StringUtil.isEmptyNull(tournamentId)){
				queryToExecute.append("AND utr.tournament.tournamentId =:tournamentId ");
			}
			if(!StringUtil.isEmptyNull(userId)){
				queryToExecute.append("AND utr.userId =:userId ");
			}
			queryToExecute.append("AND utr.tournament.archievedFlag = false ");
			query = session.createQuery(queryToExecute.toString());
			if(!StringUtil.isEmptyNull(tournamentId)){
				query.setParameter("tournamentId", tournamentId);
			}
			if(!StringUtil.isEmptyNull(userId)){
				query.setParameter("userId", userId);
			}
		}catch(RuntimeException e){
			throw e;
		}
		return query.list();
	}
	
		
}
