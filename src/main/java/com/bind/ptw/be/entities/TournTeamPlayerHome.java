package com.bind.ptw.be.entities;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;


public class TournTeamPlayerHome {

	private Session session;
	
	public TournTeamPlayerHome(Session session){
		this.session = session;
	}
	
	public void persist(TournTeamPlayer transientInstance) {
		try {
			session.persist(transientInstance);
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public void remove(TournTeamPlayer persistentInstance) {
		try {
			session.delete(persistentInstance);
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public List<TournTeamPlayer> getTournTeamPlayers(Integer tournamentTeamId){
		Query query = null;
		StringBuilder queryToExecute = new StringBuilder();
		queryToExecute.append(QueryConstants.RETRIEVE_PLAYERS_FOR_TOURN_TEAM);
		queryToExecute.append("AND trpMapping.tournamentTeam.tournamentTeamId = ");
		queryToExecute.append(tournamentTeamId);
		query = session.createQuery(queryToExecute.toString());
		return query.list();
	}
	
}
