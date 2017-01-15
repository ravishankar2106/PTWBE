package com.bind.ptw.be.entities;

import javax.persistence.EntityManager;

public class SystemMaintananceHome {
	
	EntityManager entityManager;
	
	public SystemMaintananceHome(EntityManager entityManager){
		this.entityManager=entityManager;
	}
	
	public void persist(Users transientInstance) {
		try {
			entityManager.persist(transientInstance);
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public void remove(Contest persistentInstance) {
		try {
			entityManager.remove(persistentInstance);
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public SystemMaintanance merge(SystemMaintanance detachedInstance) {
		try {
			SystemMaintanance result = entityManager.merge(detachedInstance);
			return result;
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public SystemMaintanance findById(int id) {
		try {
			SystemMaintanance instance = entityManager.find(SystemMaintanance.class, id);
			return instance;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
}
