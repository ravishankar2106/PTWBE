package com.bind.ptw.be.entities;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.bind.ptw.be.dto.TournamentBean;
import com.bind.ptw.be.util.StringUtil;

@SuppressWarnings("unchecked")
public class TournamentTeamHome {

	private Session session;
	
	public TournamentTeamHome(Session session){
		this.session = session;
	}
	
	public void persist(TournamentTeam transientInstance) {
		try {
			session.persist(transientInstance);
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public void remove(TournamentTeam persistentInstance) {
		try {
			session.delete(persistentInstance);
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public TournamentTeam merge(TournamentTeam detachedInstance) {
		try {
			TournamentTeam result = (TournamentTeam)session.merge(detachedInstance);
			return result;
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public TournamentTeam findById(int id) {
		try {
			TournamentTeam instance = (TournamentTeam)session.get(TournamentTeam.class, id);
			return instance;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public List<TournamentTeam> findTeamsByTournament(TournamentBean tournamentBean){
		Query query = null;
		
		try{
			StringBuilder queryToExecute = new StringBuilder();
			queryToExecute.append(QueryConstants.RETRIEVE_TOURNAMENT_TEAMS);
			if( tournamentBean != null ) {
							
				if(!StringUtil.isEmptyNull(tournamentBean.getTournamentId())){
					queryToExecute.append("AND tourTeam.tournament.tournamentId =:tournamentId ");
				}
			}
			query = session.createQuery(queryToExecute.toString());
			
			if(tournamentBean != null){
				if(!StringUtil.isEmptyNull(tournamentBean.getTournamentId())){
					query.setParameter("tournamentId", tournamentBean.getTournamentId());
				}
			}
		}catch(RuntimeException e){
			throw e;
		}
		
		return query.list();
	}
}
