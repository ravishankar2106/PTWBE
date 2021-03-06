package com.bind.ptw.be.entities;

public class QueryConstants {
	
	public static final String RETRIEVE_USERS = "from Users u where 1=1 ";
	public static final String RETRIEVE_TOURNAMENT = "from Tournament t where 1=1 ";
	public static final String RETRIEVE_TOURNAMENT_MATCHES = "from Match m where 1=1 ";
	public static final String RETRIEVE_TOURNAMENT_CONTEST = "from Contest c where 1=1 ";
	public static final String RETRIEVE_PRIZE_CONTEST = "from PrizeContest pc where 1=1 ";
	public static final String RETRIEVE_PRIZE_CONTEST_WINNERS = "from PrizeContestWinners pcw where 1=1 ";
	public static final String DELETE_PRIZE_CONTEST_WINNERS = "delete from PrizeContestWinners pcw where pcw.prizeContest.prizeContestId =:prizeContestId";
	public static final String RETRIEVE_ANSWER_TYPES = "from AnswerType at";
	public static final String RETRIEVE_QUESTION_ANSWERS = "from AnswerOption ao where 1=1 ";
	public static final String DELETE_QUESTION_ANSWERS = "delete from AnswerOption ao where ao.question.questionId = :questionId ";
	public static final String RETRIEVE_QUESTIONS = "from Question q where 1=1 ";
	public static final String RETRIEVE_USER_ANSWER = "from UserAnswer ua where 1=1 ";
	public static final String RETRIEVE_USER_BONUS_POINTS = "from UserBonusPoint ubp where 1=1 ";
	public static final String RETRIEVE_TOTAL_SCORE = "select new list( SUM(us.pointsScored) AS TOTAL_POINTS, us.userId) FROM UserAnswer us "
				+ "WHERE us.questionId IN (:questionIDList) ";
	public static final String RETRIEVE_TOTAL_SCORE_FROM_BKP = "select new list( SUM(us.pointsScored) AS TOTAL_POINTS, us.userId) FROM UserAnswerBkp us "
			+ "WHERE us.questionId IN (:questionIDList) ";
	public static final String RETRIEVE_USER_ANSWER_FROM_ARCIEVE = "from UserAnswerBkp uab where 1=1 ";
	public static final String RETRIEVE_USER_SCOREBOARD = "from UserScoreBoard usb where 1=1 ";
	public static final String RETRIEVE_TOURNAMENT_USERS = "from UserTournamentRegistration utr where 1=1 ";
	public static final String RETRIEVE_USER_GROUP = "from UserGroup ug where 1=1 ";
	public static final String RETRIEVE_USER_GROUP_MAPPING = "from UserGroupMapping ugm where 1=1 ";
	public static final String RETRIEVE_TOURNAMENT_RULES = "from TournamentRules tr where 1=1 ";
	public static final String RETRIEVE_USER_TOKEN = "from UserToken ut where 1=1 ";
	public static final String RETRIEVE_SPORT_TYPE = "from SportType st where 1=1 ";
	public static final String RETRIEVE_TEAM_TYPE = "from TeamType tt where 1=1 ";
	public static final String RETRIEVE_USER_CONFIRMATIONS = "from UserConfirmation uc where 1=1 ";
	public static final String RETRIEVE_CITIES = "from City c where 1=1 ";
	public static final String RETRIEVE_COUNTRIES = "from Country cy where 1=1 ";
	public static final String RETRIEVE_TEAMS = "from Team team where 1=1 ";
	public static final String RETRIEVE_PLAYERS = "from Player play where 1=1 ";
	public static final String RETRIEVE_COUNTRIES_FOR_SPORT = "select csMapping from CountrySportTypeMapping csMapping where 1=1 ";
	public static final String RETRIEVE_PLAYERS_FOR_TEAM = "select tpMapping from TeamPlayerMapping tpMapping where 1=1 ";
	public static final String RETRIEVE_TOURNAMENT_TEAMS = "from TournamentTeam tourTeam where 1=1 ";
	public static final String RETRIEVE_PLAYERS_FOR_TOURN_TEAM = "select trpMapping from TournTeamPlayer trpMapping where 1=1 ";
	public static final String RETRIEVE_USER_INVIATION = "from UserGroupInvitation ugi where 1=1 ";
	public static final String RETRIEVE_USER_ANSWER_STATS = "from UserAnswerStats uas where 1=1 ";
	public static final String RETRIEVE_ONE_SIGNAL_REGISTRATIONS = "from OneSignalUserRegistration osur where 1=1 ";
	public static final String RETRIEVE_TANDC = "from TermsCondition toc where 1=1 ";
	public static final String RETRIEVE_USER_COINS = "from UserCoin uc where 1=1 ";
}
