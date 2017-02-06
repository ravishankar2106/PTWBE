package com.bind.ptw.be.entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;


public class UserBonusPointHome {

	private Session session;
	
	public UserBonusPointHome(Session session){
		this.session = session;
	}
	
	public void save(UserBonusPoint persistentInstance){
		session.save(persistentInstance);
	}
	
	public void persist(UserBonusPoint transientInstance) {
		try {
			session.persist(transientInstance);
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public void remove(UserBonusPoint persistentInstance) {
		try {
			session.delete(persistentInstance);
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	
	
}
