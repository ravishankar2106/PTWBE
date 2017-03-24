package com.bind.ptw.be.entities;

import org.hibernate.Session;

public class CodeMojoRewardHome {

	private Session session;
	
	public CodeMojoRewardHome(Session session){
		this.session = session;
	}
	
	public void persist(CodeMojoReward transientInstance) {
		try {
			session.persist(transientInstance);
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public void remove(CodeMojoReward persistentInstance) {
		try {
			session.delete(persistentInstance);
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public CodeMojoReward merge(CodeMojoReward detachedInstance) {
		try {
			CodeMojoReward result = (CodeMojoReward)session.merge(detachedInstance);
			return result;
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public CodeMojoReward findById(int id) {
		try {
			CodeMojoReward instance = (CodeMojoReward)session.get(CodeMojoReward.class, id);
			return instance;
		} catch (RuntimeException re) {
			throw re;
		}
	}

	
	
}
