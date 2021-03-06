package com.bind.ptw.be.entities;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.bind.ptw.be.dto.TournamentBean;

@SuppressWarnings("unchecked")
public class TournamentHome {

	private Session session;
	
	public TournamentHome(Session session){
		this.session = session;
	}
	
	public void persist(Tournament transientInstance) {
		try {
			session.persist(transientInstance);
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public void remove(Tournament persistentInstance) {
		try {
			session.delete(persistentInstance);
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public Tournament merge(Tournament detachedInstance) {
		try {
			Tournament result = (Tournament)session.merge(detachedInstance);
			return result;
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public Tournament findById(int id) {
		try {
			Tournament instance = (Tournament)session.get(Tournament.class, id);
			return instance;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public List<Tournament> findTournamentByFilters( TournamentBean tournamentRequest, Boolean onlyOngoingTournament ) {
		
		Query query = null;
		
		try{
			StringBuilder queryToExecute = new StringBuilder();
			queryToExecute.append(QueryConstants.RETRIEVE_TOURNAMENT);
			
			if( tournamentRequest != null ) {
				
				if(tournamentRequest.getTournamentId() != null){
					queryToExecute.append("AND t.tournamentId =:tournamentId ");
				}
				if(tournamentRequest.getTournamentName() != null){
					queryToExecute.append( "AND t.tournamentName =:tournamentName ");
				}
			}
			if(onlyOngoingTournament != null && onlyOngoingTournament){
				queryToExecute.append("AND t.archievedFlag = 0 AND t.publishDate < CURRENT_TIMESTAMP() ");
			}
			queryToExecute.append("ORDER BY t.publishDate DESC");
			query = session.createQuery(queryToExecute.toString());
			
			if(tournamentRequest != null){
				
				if(tournamentRequest.getTournamentId() != null){
					query.setParameter("tournamentId", tournamentRequest.getTournamentId());
				}
				
				if(tournamentRequest.getTournamentName() != null){
					query.setParameter("tournamentName", tournamentRequest.getTournamentName());
				}
				
			}
			
			
		
		}catch(RuntimeException e){
			throw e;
		}
		
		return query.list();
	}
	
	
}
