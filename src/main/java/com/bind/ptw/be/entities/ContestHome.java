package com.bind.ptw.be.entities;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.util.StringUtils;

import com.bind.ptw.be.dto.ContestBean;
import com.bind.ptw.be.dto.MatchBean;
import com.bind.ptw.be.util.StringUtil;

public class ContestHome {

	private Session session;
	
	public ContestHome(Session session){
		this.session = session;
	}
	
	public void persist(Contest transientInstance) {
		try {
			session.persist(transientInstance);
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public void remove(Contest persistentInstance) {
		try {
			session.delete(persistentInstance);
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public Contest merge(Contest detachedInstance) {
		try {
			Contest result = (Contest)session.merge(detachedInstance);
			return result;
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public Contest findById(int id) {
		try {
			Contest instance = (Contest)session.get(Contest.class, id);
			return instance;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public List<Contest> findContestByFilter(ContestBean contestBean, Boolean isOngoingContest){
		Query query = null;
		
		try{
			StringBuilder queryToExecute = new StringBuilder();
			queryToExecute.append(QueryConstants.RETRIEVE_TOURNAMENT_CONTEST);
			if( contestBean != null ) {
				if(!StringUtils.isEmpty(contestBean.getContestId())){
					queryToExecute.append("AND c.contestId =:contestId ");
				}else if(!StringUtil.isEmptyNull(contestBean.getMatchId())){
					queryToExecute.append("AND c.match.matchId =:matchId ");
				}else if(!StringUtil.isEmptyNull(contestBean.getTournamentId())){
					queryToExecute.append("AND c.tournament.tournamentId =:tournamentId ");
				}
				if(!StringUtil.isEmptyNull(contestBean.getContestStatusId())){
					queryToExecute.append("AND c.contestStatus.contestStatusId =:contestStatusId ");
				}
			}
			if(isOngoingContest != null && isOngoingContest){
				queryToExecute.append("AND ( NOW() BETWEEN c.publishStartDateTime AND ");
				queryToExecute.append("c.publishEndDateTime) AND ");
				queryToExecute.append("c.contestStatus.contestStatusId = 1");
			}
			query = session.createQuery(queryToExecute.toString());
			
			if(contestBean != null){
				if(!StringUtils.isEmpty(contestBean.getContestId())){
					query.setParameter("contestId", contestBean.getContestId());;
				}else if(!StringUtil.isEmptyNull(contestBean.getMatchId())){
					query.setParameter("matchId", contestBean.getMatchId());
				}else if(!StringUtil.isEmptyNull(contestBean.getTournamentId())){
					query.setParameter("tournamentId", contestBean.getTournamentId());
				}
				if(!StringUtil.isEmptyNull(contestBean.getContestStatusId())){
					query.setParameter("contestStatusId", contestBean.getContestStatusId());
				}
				
			}
		}catch(RuntimeException e){
			throw e;
		}
		
		return query.list();
	}
	
	public List<Contest> findRunningContest(){
		Query query = null;
		
		try{
			StringBuilder queryToExecute = new StringBuilder();
			queryToExecute.append(QueryConstants.RETRIEVE_TOURNAMENT_CONTEST);
			queryToExecute.append("AND NOW() > c.publishStartDateTime ");
			queryToExecute.append("AND c.contestStatus.contestStatusId IN (1,2) ");
			queryToExecute.append("ORDER BY c.publishStartDateTime ");
			query = session.createQuery(queryToExecute.toString());
		}catch(RuntimeException e){
			throw e;
		}
		
		return query.list();
	}
	public List<Long> getMatchContests(MatchBean matchBean){
		Query query = null;
		
		try{
			StringBuilder queryToExecute = new StringBuilder();
			queryToExecute.append("Select c.contestId from Contest c where ");
			queryToExecute.append("c.match.matchId =: matchId");
							
			queryToExecute.append("ORDER BY c.contestId");
			query = session.createQuery(queryToExecute.toString());
			
			query.setParameter("matchId", matchBean.getMatchId());;
				
		}catch(RuntimeException e){
			throw e;
		}
		
		return query.list();
	}
	
}
