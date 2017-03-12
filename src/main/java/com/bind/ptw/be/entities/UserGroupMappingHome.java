package com.bind.ptw.be.entities;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.bind.ptw.be.dto.TeamTypeBean;
import com.bind.ptw.be.util.StringUtil;


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
	
	public List<UserGroupMapping> findUserGroup( Integer userId, Integer groupId, Integer tournamentId) {
		Query query = null;
		try{
			StringBuilder queryToExecuteBuilder = new StringBuilder();
			queryToExecuteBuilder.append(QueryConstants.RETRIEVE_User_GROUP_MAPPING);
			if(!StringUtil.isEmptyNull(userId)){
				queryToExecuteBuilder.append("AND ugm.userGroupMappingKey.userId = :userId ");
			}
			if(!StringUtil.isEmptyNull(groupId)){
				queryToExecuteBuilder.append("AND ugm.userGroupMappingKey.userGroup.userGroupId = :userGroupId ");
			}
			if(!StringUtil.isEmptyNull(tournamentId)){
				queryToExecuteBuilder.append("AND ugm.tournamentId = :tournamentId");
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
}
