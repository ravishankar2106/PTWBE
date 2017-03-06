package com.bind.ptw.be.entities;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.util.StringUtils;

import com.bind.ptw.be.dto.MatchBean;
import com.bind.ptw.be.util.StringUtil;

public class MatchHome {

	private Session session;
	
	public MatchHome(Session session){
		this.session = session;
	}
	
	public void persist(Match transientInstance) {
		try {
			session.persist(transientInstance);
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public void remove(Match persistentInstance) {
		try {
			session.delete(persistentInstance);
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public Match merge(Match detachedInstance) {
		try {
			Match result = (Match)session.merge(detachedInstance);
			return result;
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public Match findById(int id) {
		try {
			Match instance = (Match)session.get(Match.class, id);
			return instance;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public List<Match> findMatchByFilter(MatchBean matchBean){
		Query query = null;
		
		try{
			StringBuilder queryToExecute = new StringBuilder();
			queryToExecute.append(QueryConstants.RETRIEVE_TOURNAMENT_MATCHES);
			if( matchBean != null ) {
				if(!StringUtils.isEmpty(matchBean.getMatchId())){
					queryToExecute.append("AND m.matchId =:matchId ");
				}else if(!StringUtil.isEmptyNull(matchBean.getTournamentId())){
					queryToExecute.append("AND m.tournament.tournamentId =:tournamentId ");
				}
				if(!StringUtil.isEmptyNull(matchBean.getMatchStatusId())){
					queryToExecute.append("AND m.matchStatus.matchStatusId =:matchStatusId ");
				}
				
			}
			queryToExecute.append("ORDER BY m.matchDateTime");
			query = session.createQuery(queryToExecute.toString());
			
			if(matchBean != null){
				if(!StringUtils.isEmpty(matchBean.getMatchId())){
					query.setParameter("matchId", matchBean.getMatchId());;
				}else if(!StringUtil.isEmptyNull(matchBean.getTournamentId())){
					query.setParameter("tournamentId", matchBean.getTournamentId());
				}
				if(!StringUtil.isEmptyNull(matchBean.getMatchStatusId())){
					query.setParameter("matchStatusId", matchBean.getMatchStatusId());
				}
				
			}
		}catch(RuntimeException e){
			throw e;
		}
		
		return query.list();
	}
	
	public int getMatchNo(Integer tournamentId){
		Query query = null;
		int count=0;
		try{
			StringBuilder queryToExecute = new StringBuilder();
			queryToExecute.append("select count(*) from Match match where match.tournament.tournamentId = ");
			queryToExecute.append(tournamentId);
			query = session.createQuery(queryToExecute.toString());
			count = ((Long)query.uniqueResult()).intValue();
		}catch(RuntimeException e){
			throw e;
		}
		return count;
	}
}
