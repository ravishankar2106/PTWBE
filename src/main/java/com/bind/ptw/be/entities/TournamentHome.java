package com.bind.ptw.be.entities;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.bind.ptw.be.dto.SportTypeBean;
import com.bind.ptw.be.dto.TeamTypeBean;
import com.bind.ptw.be.dto.TournamentBean;
import com.bind.ptw.be.util.StringUtil;

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
					queryToExecute.append("AND tournamentId =:tournamentId ");
				}
				if(tournamentRequest.getTournamentName() != null){
					queryToExecute.append( "AND tournamentName =:tournamentName ");
				}
			}
			if(onlyOngoingTournament != null && onlyOngoingTournament){
				queryToExecute.append("AND archievedFlag = 0 ");
			}
            
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
	
	public List<TeamType> findTeamType( TeamTypeBean teamTypeBean ) {
		
		Query query = null;
		
		try{
			StringBuilder queryToExecuteBuilder = new StringBuilder();
			queryToExecuteBuilder.append(QueryConstants.RETRIEVE_TEAM_TYPE);
			if(!StringUtil.isEmptyNull(teamTypeBean.getTeamTypeId())){
				queryToExecuteBuilder.append("AND tt.teamTypeId = :teamTypeId ");
			}
			if(!StringUtil.isEmptyNull(teamTypeBean.getTeamTypeName())){
				queryToExecuteBuilder.append("AND tt.teamTypeId = :teamTypeName ");
			}
            
			query = session.createQuery(queryToExecuteBuilder.toString());
			
			if(!StringUtil.isEmptyNull(teamTypeBean.getTeamTypeId())){
				query.setParameter("teamTypeId", teamTypeBean.getTeamTypeId());
			}
			if(!StringUtil.isEmptyNull(teamTypeBean.getTeamTypeName())){
				query.setParameter("teamTypeName", teamTypeBean.getTeamTypeName());
			}
			
		
		}catch(RuntimeException e){
			throw e;
		}
		
		return query.list();
	}
}
