package com.bind.ptw.be.entities;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.bind.ptw.be.util.StringUtil;

@SuppressWarnings("unchecked")
public class UserScoreBoardHome {
	
	private Session session;
	
	public UserScoreBoardHome(Session session){
		this.session = session;
	}
	
	public void persist(UserScoreBoard transientInstance) {
		try {
			session.persist(transientInstance);
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public void remove(UserScoreBoard persistentInstance) {
		try {
			session.delete(persistentInstance);
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public UserScoreBoard merge(UserScoreBoard detachedInstance) {
		try {
			UserScoreBoard result = (UserScoreBoard)session.merge(detachedInstance);
			return result;
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public UserScoreBoard findById(int id) {
		try {
			UserScoreBoard instance = (UserScoreBoard)session.get("UserScoreBoard", id);
			return instance;
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public List<UserScoreBoard> findByFilter( Integer tournamentId, Integer[] userIdArr, Integer rankSize, Boolean includeZero ) {
		
		Query query = null;
		
		try{
			StringBuilder queryToExecute = new StringBuilder();
			queryToExecute.append(QueryConstants.RETRIEVE_USER_SCOREBOARD);
			queryToExecute.append("AND usb.tournamentId =:tournamentId ");
			if(userIdArr != null){
				queryToExecute.append("AND usb.user.userId IN (");
				queryToExecute.append(StringUtil.convertToTokens(userIdArr));
				queryToExecute.append(") ");
			}
			
			if(includeZero){
				queryToExecute.append("AND usb.totalPoints > 0 ");
			}
			if(!StringUtil.isEmptyNull(rankSize)){
				queryToExecute.append("AND usb.rank <= ");
				queryToExecute.append(rankSize);
				queryToExecute.append(" ");
			}
			queryToExecute.append("ORDER BY usb.rank ");
			query = session.createQuery(queryToExecute.toString());
			
			query.setParameter("tournamentId", tournamentId);
								
		}catch(RuntimeException e){
			throw e;
		}
		
		return query.list();
	}
	
	public List<Integer> getPointsForTournament( Integer tournamentId ) {
		
		Query query = null;
		
		try{
			StringBuilder queryToExecute = new StringBuilder();
			queryToExecute.append("SELECT DISTINCT(totalPoints) FROM UserScoreBoard where tournamentId =:tournamentId ");
			/*if(userIdList != null && !userIdList.isEmpty()){
				queryToExecute.append("AND userId IN (:userIdList) ");
			}*/
			          
			query = session.createQuery(queryToExecute.toString());
			
			query.setParameter("tournamentId", tournamentId);
			/*if(userIdList != null && !userIdList.isEmpty()){
				query.setParameter("statusIdArr", userIdList);
			}*/
			
		}catch(RuntimeException e){
			throw e;
		}
		
		return query.list();
	}

	public int updateRanks(Integer points, Integer rank, Integer tournamentId){
		Query query = null;
		
		try{
			StringBuilder queryToExecute = new StringBuilder();
			queryToExecute.append("Update UserScoreBoard SET rank =:rank ");
			queryToExecute.append(" where totalPoints =:points ");
			queryToExecute.append("AND tournamentId =:tournamentId ");
			query = session.createQuery(queryToExecute.toString());
			
			query.setParameter("rank", rank);
			query.setParameter("points", points);
			query.setParameter("tournamentId", tournamentId);
			
			int returnInt = query.executeUpdate();
			return returnInt;
				
		}catch(RuntimeException e){
			throw e;
		}
	}

	public Long getTotalPointsForGroups(Integer groupId, Integer tournamentId) {
		Query query = null;
		StringBuilder queryToExecute = new StringBuilder();
		queryToExecute.append("select SUM(usb.totalPoints) from UserScoreBoard usb ");
		queryToExecute.append("WHERE usb.tournamentId =:tournamentId ");
		queryToExecute.append("AND usb.user.userId IN (SELECT ugm.userGroupMappingKey.userId FROM ");
		queryToExecute.append("UserGroupMapping ugm where ugm.userGroupMappingKey.userGroup.userGroupId =:groupId )");
		query = session.createQuery(queryToExecute.toString());
		
		query.setParameter("tournamentId", tournamentId);
		query.setParameter("groupId", groupId);
		
		Long returnLong = (Long)query.list().get(0);
		return returnLong;
	}
		
}
