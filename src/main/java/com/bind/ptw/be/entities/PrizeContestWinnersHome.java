package com.bind.ptw.be.entities;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.util.StringUtils;

import com.bind.ptw.be.dto.PrizeContestBean;

@SuppressWarnings("unchecked")
public class PrizeContestWinnersHome {

	private Session session;
	
	public PrizeContestWinnersHome(Session session){
		this.session = session;
	}
	
	public void persist(PrizeContestWinners transientInstance) {
		try {
			session.persist(transientInstance);
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public void remove(PrizeContestBean prizeContestBean) {
		try {
			Query query = session.createQuery(QueryConstants.DELETE_PRIZE_CONTEST_WINNERS);
			query.setParameter("prizeContestId", prizeContestBean.getPrizeContestId());
			query.executeUpdate();
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public PrizeContestWinners merge(PrizeContestWinners detachedInstance) {
		try {
			PrizeContestWinners result = (PrizeContestWinners)session.merge(detachedInstance);
			return result;
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public PrizeContestWinners findById(int id) {
		try {
			PrizeContestWinners instance = (PrizeContestWinners)session.get(PrizeContestWinners.class, id);
			return instance;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public List<PrizeContestWinners> findPrizeContestByFilter(PrizeContestBean prizeContestBean){
		Query query = null;
		
		try{
			StringBuilder queryToExecute = new StringBuilder();
			queryToExecute.append(QueryConstants.RETRIEVE_PRIZE_CONTEST_WINNERS);
			if( prizeContestBean != null ) {
				if(!StringUtils.isEmpty(prizeContestBean.getPrizeContestId())){
					queryToExecute.append("AND pcw.prizeContest.prizeContestId =:prizeContestId ");
				}
			}
			query = session.createQuery(queryToExecute.toString());
			
			if(prizeContestBean != null){
				if(!StringUtils.isEmpty(prizeContestBean.getPrizeContestId())){
					query.setParameter("prizeContestId", prizeContestBean.getPrizeContestId());
				}
			}
		}catch(RuntimeException e){
			throw e;
		}
		
		return query.list();
	}
}
