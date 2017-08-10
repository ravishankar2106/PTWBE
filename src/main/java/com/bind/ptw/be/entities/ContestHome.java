package com.bind.ptw.be.entities;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.util.StringUtils;

import com.bind.ptw.be.dto.ContestBean;
import com.bind.ptw.be.dto.MatchBean;
import com.bind.ptw.be.util.StringUtil;

public class ContestHome {

	private Session session;

	public ContestHome(Session session) {
		this.session = session;
	}

	public void persist(Contest transientInstance) {
		try {
			session.persist(transientInstance);
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public void remove(Contest persistentInstance) {
		try {
			session.delete(persistentInstance);
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public Contest merge(Contest detachedInstance) {
		try {
			Contest result = (Contest) session.merge(detachedInstance);
			return result;
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public Contest findById(int id) {
		try {
			Contest instance = (Contest) session.get(Contest.class, id);
			return instance;
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public List<Contest> findContestByFilter(ContestBean contestBean, Boolean isOngoingContest) {
		Query query = null;

		try {
			StringBuilder queryToExecute = new StringBuilder();
			queryToExecute.append(QueryConstants.RETRIEVE_TOURNAMENT_CONTEST);
			if (!StringUtils.isEmpty(contestBean.getContestId())) {
				queryToExecute.append("AND c.contestId =:contestId ");
			} else if (!StringUtil.isEmptyNull(contestBean.getMatchId())) {
				queryToExecute.append("AND c.match.matchId =:matchId ");
			} else if (!StringUtil.isEmptyNull(contestBean.getTournamentId())) {
				queryToExecute.append("AND c.tournament.tournamentId =:tournamentId ");
			}
			if (!StringUtil.isEmptyNull(contestBean.getContestStatusId())) {
				queryToExecute.append("AND c.contestStatus.contestStatusId =:contestStatusId ");
			}
			if (!StringUtil.isEmptyNull(contestBean.getContestTypeId())) {
				queryToExecute.append("AND c.contestType.contestTypeId =:contestTypeId ");
			}
			if (StringUtils.isEmpty(contestBean.getContestId()) && (isOngoingContest != null && isOngoingContest)) {
				queryToExecute.append("AND ( NOW() BETWEEN c.publishStartDateTime AND ");
				queryToExecute.append("c.publishEndDateTime) ");
				// queryToExecute.append("AND c.contestStatus.contestStatusId =
				// 1 ");
			}
			queryToExecute.append("ORDER BY c.cutoffDateTime ASC ");
			query = session.createQuery(queryToExecute.toString());

			if (contestBean != null) {
				if (!StringUtils.isEmpty(contestBean.getContestId())) {
					query.setParameter("contestId", contestBean.getContestId());
					;
				} else if (!StringUtil.isEmptyNull(contestBean.getMatchId())) {
					query.setParameter("matchId", contestBean.getMatchId());
				} else if (!StringUtil.isEmptyNull(contestBean.getTournamentId())) {
					query.setParameter("tournamentId", contestBean.getTournamentId());
				}
				if (!StringUtil.isEmptyNull(contestBean.getContestStatusId())) {
					query.setParameter("contestStatusId", contestBean.getContestStatusId());
				}
				if (!StringUtil.isEmptyNull(contestBean.getContestTypeId())) {
					query.setParameter("contestTypeId", contestBean.getContestTypeId());
				}
			}
		} catch (RuntimeException e) {
			throw e;
		}

		return query.list();
	}

	public List<Contest> findRunningContest(boolean forProcessing) {
		Query query = null;

		try {
			StringBuilder queryToExecute = new StringBuilder();
			queryToExecute.append(QueryConstants.RETRIEVE_TOURNAMENT_CONTEST);
			queryToExecute.append("AND NOW() > c.publishStartDateTime ");
			if (forProcessing) {
				queryToExecute.append("AND c.contestStatus.contestStatusId IN (1,2) ");
			} else{
				queryToExecute.append("AND c.publishEndDateTime > NOW() ");
			}
			queryToExecute.append("ORDER BY c.publishStartDateTime ");
			query = session.createQuery(queryToExecute.toString());
		} catch (RuntimeException e) {
			throw e;
		}

		return query.list();
	}

	public List<Long> getMatchContests(MatchBean matchBean) {
		Query query = null;

		try {
			StringBuilder queryToExecute = new StringBuilder();
			queryToExecute.append("Select c.contestId from Contest c where ");
			queryToExecute.append("c.match.matchId =: matchId");

			queryToExecute.append("ORDER BY c.contestId");
			query = session.createQuery(queryToExecute.toString());

			query.setParameter("matchId", matchBean.getMatchId());

		} catch (RuntimeException e) {
			throw e;
		}

		return query.list();
	}

	public List<Contest> getContestBetweenDates(Date startDate, Date endDate, Integer tournamentId) {
		Criteria criteria = session.createCriteria(Contest.class);
		criteria.add(Restrictions.between("cutoffDateTime", startDate, endDate));
		criteria.add(Restrictions.eq("tournament.tournamentId", tournamentId));
		return criteria.list();
	}

	public void updatePushStatus(Integer contestId) {
		StringBuilder queryToExecute = new StringBuilder();
		queryToExecute.append("UPDATE Contest c set c.pushNotified = true where ");
		queryToExecute.append("c.contestId =:contestId");
		Query query = session.createQuery(queryToExecute.toString());
		query.setParameter("contestId", contestId);
		query.executeUpdate();

	}
}
