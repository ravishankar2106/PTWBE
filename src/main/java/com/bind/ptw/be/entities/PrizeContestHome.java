package com.bind.ptw.be.entities;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.util.StringUtils;

import com.bind.ptw.be.dto.PrizeContestBean;
import com.bind.ptw.be.util.StringUtil;

@SuppressWarnings("unchecked")
public class PrizeContestHome {

	private Session session;
	
	public PrizeContestHome(Session session){
		this.session = session;
	}
	
	public void persist(PrizeContest transientInstance) {
		try {
			session.persist(transientInstance);
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public void remove(PrizeContest persistentInstance) {
		try {
			session.delete(persistentInstance);
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public PrizeContest merge(PrizeContest detachedInstance) {
		try {
			PrizeContest result = (PrizeContest)session.merge(detachedInstance);
			return result;
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public PrizeContest findById(int id) {
		try {
			PrizeContest instance = (PrizeContest)session.get(PrizeContest.class, id);
			return instance;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public List<PrizeContest> findPrizeContestByFilter(PrizeContestBean prizeContestBean, boolean exludeArchived, boolean publicGroupsOnly){
		Query query = null;
		
		try{
			StringBuilder queryToExecute = new StringBuilder();
			queryToExecute.append(QueryConstants.RETRIEVE_PRIZE_CONTEST);
			if( prizeContestBean != null ) {
				if(!StringUtils.isEmpty(prizeContestBean.getPrizeContestId())){
					queryToExecute.append("AND pc.prizeContestId =:prizeContestId ");
				}
				if(!StringUtil.isEmptyNull(prizeContestBean.getTournamentId())){
					queryToExecute.append("AND pc.tournamentId =:tournamentId ");
				}
				if(!StringUtil.isEmptyNull(prizeContestBean.getGroupId())){
					queryToExecute.append("AND pc.groupId =:groupId ");
				}
				if(exludeArchived){
					queryToExecute.append("AND pc.archievedFlag = false ");
					queryToExecute.append("AND pc.processedFlag = false ");
				}
				if(publicGroupsOnly) {
					queryToExecute.append("AND pc.groupId IS NULL ");
				}
			}
			queryToExecute.append("order by prizeContestId DESC ");
			query = session.createQuery(queryToExecute.toString());
			
			if(prizeContestBean != null){
				if(!StringUtils.isEmpty(prizeContestBean.getPrizeContestId())){
					query.setParameter("prizeContestId", prizeContestBean.getPrizeContestId());;
				}
				if(!StringUtil.isEmptyNull(prizeContestBean.getTournamentId())){
					query.setParameter("tournamentId", prizeContestBean.getTournamentId());
				}
				if(!StringUtil.isEmptyNull(prizeContestBean.getGroupId())){
					query.setParameter("groupId", prizeContestBean.getGroupId());
				}
			}
		}catch(RuntimeException e){
			throw e;
		}
		
		return query.list();
	}
}
