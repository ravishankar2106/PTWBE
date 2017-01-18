package com.bind.ptw.be.entities;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.bind.ptw.be.dto.TeamTypeBean;
import com.bind.ptw.be.util.StringUtil;


public class TeamPlayerHome {

	private Session session;
	
	public TeamPlayerHome(Session session){
		this.session = session;
	}
	
	public void persist(TeamPlayerMapping transientInstance) {
		try {
			session.persist(transientInstance);
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public void remove(TeamPlayerMapping persistentInstance) {
		try {
			session.delete(persistentInstance);
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public List<TeamPlayerMapping> getTeamPlayers(Integer teamId){
		Query query = null;
		StringBuilder queryToExecute = new StringBuilder();
		queryToExecute.append(QueryConstants.RETRIEVE_PLAYERS_FOR_TEAM);
		queryToExecute.append("AND tpMapping.teamPlayerMappingKey.teamId = ");
		queryToExecute.append(teamId);
		query = session.createQuery(queryToExecute.toString());
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
