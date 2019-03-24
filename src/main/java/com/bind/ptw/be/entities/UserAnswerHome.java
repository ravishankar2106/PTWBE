package com.bind.ptw.be.entities;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import com.bind.ptw.be.util.StringUtil;

@SuppressWarnings("unchecked")
public class UserAnswerHome {

	private Session session;
	
	public UserAnswerHome(Session session){
		this.session = session;
	}
	
	public void persist(UserAnswer transientInstance) {
		try {
			session.persist(transientInstance);
		} catch (RuntimeException re) {
			throw re;
		}
	}

	public void remove(UserAnswer persistentInstance) {
		try {
			session.delete(persistentInstance);
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public List<UserAnswer> getUserAnswer(Integer userId, Integer questionId){
		Query query = null;
		StringBuilder queryToExecute = new StringBuilder();
		queryToExecute.append(QueryConstants.RETRIEVE_USER_ANSWER);
		queryToExecute.append("AND ua.userId =:userId ");
		queryToExecute.append("AND ua.questionId =:questionId");
		query = session.createQuery(queryToExecute.toString());
		query.setParameter("userId", userId);
		query.setParameter("questionId", questionId);
		return query.list();
	}
	
	public List<Integer> getUserAnswer(Integer[] questionId){
		Query query = null;
		StringBuilder queryToExecute = new StringBuilder();
		queryToExecute.append("SELECT DISTINCT ua.userId from UserAnswer ua where ");
		queryToExecute.append("ua.questionId IN :questionIds");
		query = session.createQuery(queryToExecute.toString());
		query.setParameterList("questionIds", questionId);
		return query.list();
	}
	
	public List<UserAnswer> findByAnswerOption(Integer answerOptionId){
		Query query = null;
		StringBuilder queryToExecute = new StringBuilder();
		queryToExecute.append(QueryConstants.RETRIEVE_USER_ANSWER);
		queryToExecute.append("AND ua.answerOption.answerOptionId =:answerOptionId ");
		query = session.createQuery(queryToExecute.toString());
		query.setParameter("answerOptionId", answerOptionId);
		return query.list();
	}

	public void deleteUserAnswer(Integer userId, Integer[] questionIdList){
		Query query = null;
		StringBuilder queryToExecute = new StringBuilder();
		queryToExecute.append("DELETE FROM UserAnswer ua where ua.userId = ");
		queryToExecute.append(userId);
		queryToExecute.append(" ");
		queryToExecute.append("AND ua.questionId IN ( ");
		queryToExecute.append(StringUtil.convertToTokens(questionIdList));
		queryToExecute.append(") ");
		query = session.createQuery(queryToExecute.toString());
		query.executeUpdate();
	}
	
	public void saveUserPoints(Integer answerOptionId, Integer pointsScored, double cashWon) {
		Query query = null;
		StringBuilder queryToExecute = new StringBuilder();
		queryToExecute.append("Update UserAnswer ua set ua.pointsScored =:pointsScored ");
		queryToExecute.append(", cashWon =:cashWon ");
		queryToExecute.append("Where ua.answerOption.answerOptionId =:answerOptionId");
		query = session.createQuery(queryToExecute.toString());
		query.setParameter("answerOptionId", answerOptionId);
		query.setParameter("pointsScored", pointsScored);
		query.setParameter("cashWon", cashWon);
		query.executeUpdate();
		
	}
	
	public List<Object> getUserScoreForQuestions(Integer[] userId, Integer[] questionId){
		StringBuilder queryToExecute = new StringBuilder();
		queryToExecute.append("select SUM(POINTS_SCORED) AS POINTS, ");
		queryToExecute.append("USER_ID AS USER_ID ");
		queryToExecute.append("from USER_ANSWERS where CONTEST_QUESTION_ID IN (");
		queryToExecute.append(StringUtil.convertToTokens(questionId));
		queryToExecute.append(") AND USER_ID IN (");
		queryToExecute.append(StringUtil.convertToTokens(userId));
		queryToExecute.append(") GROUP BY USER_ID ORDER BY POINTS DESC;");
		SQLQuery query = session.createSQLQuery(queryToExecute.toString());
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		return query.list();
	}
	
	public List<Object> getCashEarnedReport(Integer contestId){
		StringBuilder queryToExecute = new StringBuilder();
		queryToExecute.append("select SUM(CASH_WON) AS CASH, ");
		queryToExecute.append("USER_ID AS USER_ID ");
		queryToExecute.append("from USER_ANSWERS where CONTEST_QUESTION_ID IN (");
		queryToExecute.append("select CONTEST_QUESTION_ID from CONTEST_QUESTION WHERE ");
		queryToExecute.append("CONTEST_ID  = ");
		queryToExecute.append(contestId);
		queryToExecute.append(") GROUP BY USER_ID;");
		SQLQuery query = session.createSQLQuery(queryToExecute.toString());
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		return query.list();
	}
	
	public List<Object> getAnswerSelectionStats(Integer questionId){
		StringBuilder queryToExecute = new StringBuilder();
		queryToExecute.append("select COUNT(*) AS STATS_COUNT, ");
		queryToExecute.append("SELECTED_ANSWER_OPTION_ID AS ANSWER_OPTION_ID ");
		queryToExecute.append("from USER_ANSWERS where CONTEST_QUESTION_ID =");
		queryToExecute.append(questionId);
		queryToExecute.append(" GROUP BY SELECTED_ANSWER_OPTION_ID;");
		SQLQuery query = session.createSQLQuery(queryToExecute.toString());
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		return query.list();
	}
	
	public List<Integer> getUsersAnsweredForQuestion(Integer questionId){
		StringBuilder queryToExecute = new StringBuilder();
		queryToExecute.append("select USER_ID from USER_ANSWERS where CONTEST_QUESTION_ID =");
		queryToExecute.append(questionId);
		SQLQuery query = session.createSQLQuery(queryToExecute.toString());
		return query.list();
	}
	
	public List<Integer> getUserForTournament(Integer tournamentId){
		StringBuilder queryToExecute = new StringBuilder();
		queryToExecute.append("select DISTINCT(USER_ID) from USER_ANSWERS where ");
		queryToExecute.append("CONTEST_QUESTION_ID in (select CONTEST_QUESTION_ID from CONTEST_QUESTION where CONTEST_ID in ");
		queryToExecute.append("(select CONTEST_ID from CONTEST_MASTER where TOURNAMENT_ID = ");
		queryToExecute.append(tournamentId);
		queryToExecute.append("))");
		SQLQuery query = session.createSQLQuery(queryToExecute.toString());
		return query.list();
	}
	
	public List<Integer> getUserForContest(Integer contestId){
		StringBuilder queryToExecute = new StringBuilder();
		queryToExecute.append("select DISTINCT(USER_ID) from USER_ANSWERS where ");
		queryToExecute.append("CONTEST_QUESTION_ID in (select CONTEST_QUESTION_ID from CONTEST_QUESTION where CONTEST_ID = ");
		queryToExecute.append(contestId);
		queryToExecute.append(")");
		SQLQuery query = session.createSQLQuery(queryToExecute.toString());
		return query.list();
	}
}
