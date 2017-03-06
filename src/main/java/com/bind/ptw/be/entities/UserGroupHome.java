package com.bind.ptw.be.entities;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.bind.ptw.be.dto.UserGroupBean;
import com.bind.ptw.be.util.StringUtil;


public class UserGroupHome {

	private Session session;
	
	public UserGroupHome(Session session){
		this.session = session;
	}
	
	public void persist(UserGroup transientInstance) {
		try {
			session.persist(transientInstance);
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public void remove(UserGroup persistentInstance) {
		try {
			session.delete(persistentInstance);
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public UserGroup merge(UserGroup detachedInstance) {
		try {
			UserGroup result = (UserGroup)session.merge(detachedInstance);
			return result;
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public UserGroup findById(int id) {
		try {
			UserGroup instance = (UserGroup)session.get(UserGroup.class, id);
			return instance;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public List<UserGroup> findByFilter(UserGroupBean userGroupBean){
		Query query = null;
		StringBuilder queryToExecute = new StringBuilder();
		queryToExecute.append(QueryConstants.RETRIEVE_USER_GROUP);
		if(!StringUtil.isEmptyNull(userGroupBean.getGroupId())){
			queryToExecute.append("AND ug.userGroupId =:userGroupId ");
		}
		if(!StringUtil.isEmptyNull(userGroupBean.getOwnerId())){
			queryToExecute.append("AND ug.ownerUser.userId =:ownerUserId ");
		}
		if(!StringUtil.isEmptyNull(userGroupBean.getGroupName())){
			queryToExecute.append("AND ug.userGroupName =:userGroupName ");
		}
		if(!StringUtil.isEmptyNull(userGroupBean.getGroupCode())){
			queryToExecute.append("AND ug.userGroupCode =:userGroupCode ");
		}
		if(userGroupBean.getPrizeGroupFlag() != null && userGroupBean.getPrizeGroupFlag()){
			queryToExecute.append("AND ug.prizeIncludedFlag IS TRUE ");
		}
		
		query = session.createQuery(queryToExecute.toString());
		if(!StringUtil.isEmptyNull(userGroupBean.getGroupId())){
			query.setParameter("userGroupId", userGroupBean.getGroupId());
		}
		if(!StringUtil.isEmptyNull(userGroupBean.getOwnerId())){
			query.setParameter("ownerUserId", userGroupBean.getOwnerId());
		}
		if(!StringUtil.isEmptyNull(userGroupBean.getGroupName())){
			query.setParameter("userGroupName", userGroupBean.getGroupName());
		}
		if(!StringUtil.isEmptyNull(userGroupBean.getGroupCode())){
			query.setParameter("userGroupCode", userGroupBean.getGroupCode());
		}
		return query.list();
	}

	
}
	
	
