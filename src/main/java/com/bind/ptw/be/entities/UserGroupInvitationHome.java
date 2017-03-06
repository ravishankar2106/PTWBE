package com.bind.ptw.be.entities;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.bind.ptw.be.dto.UserGroupInvitationBean;
import com.bind.ptw.be.util.StringUtil;


public class UserGroupInvitationHome {

	private Session session;
	
	public UserGroupInvitationHome(Session session){
		this.session = session;
	}
	
	public void persist(UserGroupInvitation transientInstance) {
		try {
			session.persist(transientInstance);
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public void remove(UserGroupInvitation persistentInstance) {
		try {
			session.delete(persistentInstance);
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public UserGroupInvitation merge(UserGroupInvitation detachedInstance) {
		try {
			UserGroupInvitation result = (UserGroupInvitation)session.merge(detachedInstance);
			return result;
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public UserGroupInvitation findById(int id) {
		try {
			UserGroupInvitation instance = (UserGroupInvitation)session.get(UserGroupInvitation.class, id);
			return instance;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public List<UserGroupInvitation> findByFilter(UserGroupInvitationBean userGroupInvitationBean){
		Query query = null;
		StringBuilder queryToExecute = new StringBuilder();
		queryToExecute.append(QueryConstants.RETRIEVE_USER_INVIATION);
		if(!StringUtil.isEmptyNull(userGroupInvitationBean.getInviteeUserId())){
			queryToExecute.append("AND ugi.inviteeUserId =:inviteeUserId ");
		}else{
			if(!StringUtil.isEmptyNull(userGroupInvitationBean.getEmailId()) || 
					!StringUtil.isEmptyNull(userGroupInvitationBean.getPhone())){
				queryToExecute.append("AND (");
				boolean emailQuery = false;
				if(!StringUtil.isEmptyNull(userGroupInvitationBean.getPhone()) ){
					queryToExecute.append("ugi.phone =:phone ");
					emailQuery = true;
				}
				if(!StringUtil.isEmptyNull(userGroupInvitationBean.getEmailId())){
					if(emailQuery){
						queryToExecute.append("OR ");
					}
					queryToExecute.append("ugi.emailId =:emailId ");
				}
				queryToExecute.append(" )");
				
			}
		}
		if(!StringUtil.isEmptyNull(userGroupInvitationBean.getInvitationStatusId())){
			queryToExecute.append("AND ugi.userInvitationStatus.invitationStatusId =:invitationStatusId ");
		}
		if(!StringUtil.isEmptyNull(userGroupInvitationBean.getGroupId())){
			queryToExecute.append("AND ugi.userGroup.userGroupId =:userGroupId ");
		}
		query = session.createQuery(queryToExecute.toString());
		if(!StringUtil.isEmptyNull(userGroupInvitationBean.getInviteeUserId())){
			query.setParameter("inviteeUserId", userGroupInvitationBean.getInviteeUserId());
		}else {
			if(!StringUtil.isEmptyNull(userGroupInvitationBean.getEmailId())){
				query.setParameter("emailId", userGroupInvitationBean.getEmailId());
			}
			if(!StringUtil.isEmptyNull(userGroupInvitationBean.getPhone())){
				query.setParameter("phone", userGroupInvitationBean.getPhone());
			}
		}
		if(!StringUtil.isEmptyNull(userGroupInvitationBean.getInvitationStatusId())){
			query.setParameter("invitationStatusId", userGroupInvitationBean.getInvitationStatusId());
		}
		if(!StringUtil.isEmptyNull(userGroupInvitationBean.getGroupId())){
			query.setParameter("userGroupId", userGroupInvitationBean.getGroupId());
		}
		return query.list();
	}

	
}
	
	
