package com.bind.ptw.be.entities;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.util.StringUtils;

import com.bind.ptw.be.dto.PlayerBean;
import com.bind.ptw.be.util.StringUtil;

public class PlayerHome {

	private Session session;
	
	public PlayerHome(Session session){
		this.session = session;
	}
	
	public void persist(Player transientInstance) {
		try {
			session.persist(transientInstance);
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public void remove(Player persistentInstance) {
		try {
			session.delete(persistentInstance);
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public Player merge(Player detachedInstance) {
		try {
			Player result = (Player)session.merge(detachedInstance);
			return result;
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public Player findById(int id) {
		try {
			Player instance = (Player)session.get(Player.class, id);
			return instance;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public List<Player> findPlayerByFilter(PlayerBean playerBean){
		Query query = null;
		
		try{
			StringBuilder queryToExecute = new StringBuilder();
			queryToExecute.append(QueryConstants.RETRIEVE_PLAYERS);
			boolean firstQuery=false;
			if( playerBean != null ) {
				if(!StringUtils.isEmpty(playerBean.getPlayerId())){
					queryToExecute.append("AND play.playerId =:playerId");
				}else if(!StringUtil.isEmptyNull(playerBean.getSportTypeId()) && !StringUtil.isEmptyNull(playerBean.getCountryId())){
					queryToExecute.append("AND play.country.countryId =:countryId ");
					queryToExecute.append("AND play.sportType.sportTypeId =:sportTypeId");
				}else if(!StringUtil.isEmptyNull(playerBean.getSportTypeId())){
					queryToExecute.append("AND play.sportType.sportTypeId =:sportTypeId");
				}else if(!StringUtil.isEmptyNull(playerBean.getCountryId())){
					queryToExecute.append("AND play.country.countryId =:countryId");
				}else {
					if(!StringUtil.isEmptyNull(playerBean.getFirstName()) || !StringUtil.isEmptyNull(playerBean.getLastName())){
						queryToExecute.append("AND (");
						if(!StringUtil.isEmptyNull(playerBean.getFirstName())){
							firstQuery=true;
							queryToExecute.append("play.firstName =:firstName ");
						}
						
						if(!StringUtil.isEmptyNull(playerBean.getLastName())){
							if(firstQuery){
								queryToExecute.append("OR ");
							}
							queryToExecute.append("play.lastName =:lastName ");
						}
						queryToExecute.append(")");
					}
					
				}
			}
			query = session.createQuery(queryToExecute.toString());
			
			if(playerBean != null){
				if(!StringUtils.isEmpty(playerBean.getPlayerId())){
					query.setParameter("playerId", playerBean.getPlayerId());;
				}else if(!StringUtil.isEmptyNull(playerBean.getSportTypeId()) && !StringUtil.isEmptyNull(playerBean.getCountryId())){
					query.setParameter("countryId", playerBean.getCountryId());
					query.setParameter("sportTypeId", playerBean.getSportTypeId());
				}else if(!StringUtil.isEmptyNull(playerBean.getSportTypeId())){
					query.setParameter("sportTypeId", playerBean.getSportTypeId());
				}else if(!StringUtil.isEmptyNull(playerBean.getCountryId())){
					query.setParameter("countryId", playerBean.getCountryId());
				}else{				
					if(!StringUtil.isEmptyNull(playerBean.getFirstName())){
						query.setParameter("firstName", playerBean.getFirstName());
					}
					if(!StringUtil.isEmptyNull(playerBean.getLastName())){
						query.setParameter("lastName", playerBean.getLastName());
					}
				}
				
			}
		}catch(RuntimeException e){
			throw e;
		}
		
		return query.list();
	}
}
