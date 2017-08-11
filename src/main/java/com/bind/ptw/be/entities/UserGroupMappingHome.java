package com.bind.ptw.be.entities;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.bind.ptw.be.util.StringUtil;

@SuppressWarnings("unchecked")
public class UserGroupMappingHome {

	private Session session;
	
	public UserGroupMappingHome(Session session){
		this.session = session;
	}
	
	public void persist(UserGroupMapping transientInstance) {
		try {
			session.persist(transientInstance);
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public void remove(UserGroupMapping persistentInstance) {
		try {
			session.delete(persistentInstance);
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public List<UserGroupMapping> findUserGroup( Integer userId, Integer groupId, Integer tournamentId, boolean excludeFanClub) {
		Query query = null;
		try{
			StringBuilder queryToExecuteBuilder = new StringBuilder();
			queryToExecuteBuilder.append(QueryConstants.RETRIEVE_USER_GROUP_MAPPING);
			if(!StringUtil.isEmptyNull(userId)){
				queryToExecuteBuilder.append("AND ugm.userGroupMappingKey.userId = :userId ");
			}
			if(!StringUtil.isEmptyNull(groupId)){
				queryToExecuteBuilder.append("AND ugm.userGroupMappingKey.userGroup.userGroupId = :userGroupId ");
			}
			if(!StringUtil.isEmptyNull(tournamentId)){
				queryToExecuteBuilder.append("AND ugm.tournamentId = :tournamentId ");
			}
			
			if(excludeFanClub){
				queryToExecuteBuilder.append("AND ugm.userGroupMappingKey.userGroup.ownerUser IS NOT NULL ");
			}
            
			query = session.createQuery(queryToExecuteBuilder.toString());
			
			if(!StringUtil.isEmptyNull(userId)){
				query.setParameter("userId", userId);
			}
			if(!StringUtil.isEmptyNull(groupId)){
				query.setParameter("userGroupId", groupId);
			}
			if(!StringUtil.isEmptyNull(tournamentId)){
				query.setParameter("tournamentId", tournamentId);
			}
		
		}catch(RuntimeException e){
			throw e;
		}
		
		return query.list();
	}
	
	public List<UserGroupMapping> findSystemUserGroup(Integer userId, Integer tournamentId){
		Query query = null;
		try{
			StringBuilder queryToExecuteBuilder = new StringBuilder();
			queryToExecuteBuilder.append("select ugm from UserGroupMapping ugm where 1=1 ");
			if(!StringUtil.isEmptyNull(userId)){
				queryToExecuteBuilder.append("AND ugm.userGroupMappingKey.userId = :userId ");
			}
			
			queryToExecuteBuilder.append("AND ugm.userGroupMappingKey.userGroup.userGroupId IN (");
			queryToExecuteBuilder.append("Select ug.userGroupId from UserGroup ug where ug.tournament.tournamentId =:tournamentId ");
			queryToExecuteBuilder.append("AND ug.ownerUser IS NULL ) ");
			query = session.createQuery(queryToExecuteBuilder.toString());
			if(!StringUtil.isEmptyNull(userId)){
				query.setParameter("userId", userId);
			}
			query.setParameter("tournamentId", tournamentId);
		}catch(RuntimeException e){
			throw e;
		}
		
		return query.list();
			
		
	}
}
