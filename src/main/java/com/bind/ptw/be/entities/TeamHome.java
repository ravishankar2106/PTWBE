package com.bind.ptw.be.entities;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.bind.ptw.be.dto.CountryBean;
import com.bind.ptw.be.dto.TeamBean;
import com.bind.ptw.be.util.StringUtil;

public class TeamHome {

	private Session session;
	
	public TeamHome(Session session){
		this.session = session;
	}
	
	public void persist(Team transientInstance) {
		try {
			session.persist(transientInstance);
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public void remove(Team persistentInstance) {
		try {
			session.delete(persistentInstance);
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public Team merge(Team detachedInstance) {
		try {
			Team result = (Team)session.merge(detachedInstance);
			return result;
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public Team findById(int id) {
		try {
			Team instance = (Team)session.get(Team.class, id);
			return instance;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public List<Team> findTeamsByFilter(TeamBean teamBean){
		Query query = null;
		
		try{
			StringBuilder queryToExecute = new StringBuilder();
			queryToExecute.append(QueryConstants.RETRIEVE_TEAMS);
			if( teamBean != null ) {
				
				boolean firstQuery = false;
				if(!StringUtil.isEmptyNull(teamBean.getTeamName())){
					queryToExecute.append("AND (");
					firstQuery = true;
					queryToExecute.append("team.teamName =:teamName ");
				}
				
				if(!StringUtil.isEmptyNull(teamBean.getTeamShortName())){
					firstQuery=true;
					if(firstQuery){
						queryToExecute.append("OR ");
					}else{
						queryToExecute.append("AND (");
					}
					queryToExecute.append("team.teamShortName =:teamShortName ");
				}
				if(firstQuery){
					queryToExecute.append(") ");
				}
				if(!StringUtil.isEmptyNull(teamBean.getCountryId())){
					queryToExecute.append("AND team.country.countryId =:countryId ");
				}
				if(!StringUtil.isEmptyNull(teamBean.getTeamTypeId())){
					queryToExecute.append("AND team.teamType.teamTypeId =:teamTypeId ");
				}
				if(!StringUtil.isEmptyNull(teamBean.getSportTypeId())){
					queryToExecute.append("AND team.sportType.sportTypeId =:sportTypeId ");
				}
				
			}
			query = session.createQuery(queryToExecute.toString());
			
			if(teamBean != null){
				if(!StringUtil.isEmptyNull(teamBean.getTeamName())){
					query.setParameter("teamName", teamBean.getTeamName());
				}
				if(!StringUtil.isEmptyNull(teamBean.getTeamShortName())){
					query.setParameter("teamShortName", teamBean.getTeamShortName());
				}
				if(!StringUtil.isEmptyNull(teamBean.getCountryId())){
					query.setParameter("countryId", teamBean.getCountryId());
				}
				if(!StringUtil.isEmptyNull(teamBean.getTeamTypeId())){
					query.setParameter("teamTypeId", teamBean.getTeamTypeId());
				}
				if(!StringUtil.isEmptyNull(teamBean.getSportTypeId())){
					query.setParameter("sportTypeId", teamBean.getSportTypeId());
				}
				
				
			}
		}catch(RuntimeException e){
			throw e;
		}
		
		return query.list();
	}
}
