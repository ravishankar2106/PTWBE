package com.bind.ptw.be.entities;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.bind.ptw.be.dto.UserBean;
import com.bind.ptw.be.dto.UserGroupBean;

public class UserHome {

	private Session session;

	public UserHome(Session session){
		this.session = session;
	}

	public void save(Users user){
		session.save(user);
	}

	public void remove(Users persistentInstance) {
		try {
			session.delete(persistentInstance);
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public Users merge(Users detachedInstance) {
		try {
			Users result = (Users)session.merge(detachedInstance);
			return result;
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public Users findById(int id) {
		try {
			Users instance = (Users)session.get(Users.class, id);
			return instance;
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public List<Users> findUsersByFilters( UserBean userRequest, Boolean adminFlag) {

		Query query = null;

		try{
			StringBuilder queryToExecute = new StringBuilder();
			queryToExecute.append(QueryConstants.RETRIEVE_USERS);

			if( userRequest != null ) {
				if(userRequest.getUserId()!= null){
					queryToExecute.append("AND userId =:userId ");
				}
				if(userRequest.getUserLoginId() != null){
					queryToExecute.append("AND loginId =:userLoginId ");
				}
				if(userRequest.getPassword() != null){
					queryToExecute.append("AND password =:userPassword ");
				}
				if(userRequest.getTeamName() != null){
					queryToExecute.append("AND teamName =:teamName ");
				}
				if(userRequest.getEmail() != null){
					queryToExecute.append("AND emailId =:emailId ");
				}
				if(userRequest.getPhone() != null){
					queryToExecute.append("AND phone =:phone ");
				}
				if (adminFlag != null) {
					queryToExecute.append("AND adminFlag is ");
					queryToExecute.append(adminFlag);
				}
			}

			query = session.createQuery(queryToExecute.toString());

			if(userRequest != null){
				if(userRequest.getUserId()!= null){
					query.setParameter("userId", userRequest.getUserId());
				}
				if(userRequest.getUserLoginId() != null){
					query.setParameter("userLoginId", userRequest.getUserLoginId());
				}

				if(userRequest.getPassword() != null){
					query.setParameter("userPassword", userRequest.getPassword());
				}

				if(userRequest.getTeamName() != null){
					query.setParameter("teamName", userRequest.getTeamName());
				}

				if(userRequest.getEmail() != null){
					query.setParameter("emailId", userRequest.getEmail());
				}

				if(userRequest.getPhone() != null){
					query.setParameter("phone", userRequest.getPhone());
				}
			}



		}catch(RuntimeException e){
			throw e;
		}

		return query.list();
	}

	public List<Users> findUsersByGroup( UserGroupBean userGroupRequest ) {

		Query query = null;

		try{
			StringBuilder queryToExecuteBuilder = new StringBuilder();
			queryToExecuteBuilder.append(QueryConstants.RETRIEVE_USERS);
			queryToExecuteBuilder.append("AND u.adminFlag=0 ");
			if(userGroupRequest != null){
				queryToExecuteBuilder.append("AND u.userId IN ( ");
				queryToExecuteBuilder.append("select ugm.userId from UserGroupMapping ugm where ugm.userGroupId =:groupId");
				queryToExecuteBuilder.append(")");
			}

			query = session.createQuery(queryToExecuteBuilder.toString());
			if(userGroupRequest != null){	
				query.setParameter("groupId", userGroupRequest.getGroupId());
			}

		}catch(RuntimeException e){
			throw e;
		}

		return query.list();
	}

	public List<City> getCities(){
		Query query = session.createQuery(QueryConstants.RETRIEVE_CITIES);
		return query.list();
	}

	public City findByCityId(int cityId) {
		try {
			City instance = (City)session.get(City.class, cityId);
			return instance;
		} catch (RuntimeException re) {
			throw re;
		}
	}
}
