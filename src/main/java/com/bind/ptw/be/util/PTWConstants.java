package com.bind.ptw.be.util;

public class PTWConstants {
	
	public static final String EMAIL_REGEX = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	public static final String PASSWORD_REGEX = "[\\-0-9a-zA-Z \\._\\p{L}\\&\\'\\’\\,\\%\\+]{6,50}";
	public static final String GENERAL_NAME_REGEX = "[\\-0-9a-zA-Z \\._\\p{L}\\&\\'\\’\\,\\%\\+]{1,50}";
	public static final String GROUP_NAME_REGEX = "[\\-0-9a-zA-Z \\._\\p{L}\\&\\'\\’\\,\\%\\+]{1,20}";
	public static final String PHONE_NUMBER_REGEX = "[0-9]{10}";
	
	public final static String ERROR_CODE_DB_EXCEPTION = "1001";
	public final static String ERROR_DESC_DB_EXCEPTION = "Internal Error occured during processing the request";

	public final static String ERROR_CODE_INVALID_REQUEST = "1002";
	public final static String ERROR_DESC_INVALID_REQUEST = "Invalid Request";

	public final static String ERROR_DESC_FIELD_EMPTY = "Field Empty Error: ";
	public final static String ERROR_DESC_FIELD_INVALID = "Field Invalid Error: ";
	
	public final static String ERROR_CODE_USER_LOGIN_ID_EMPTY = "101";
	public final static String ERROR_CODE_USER_LOGIN_PASSWORD_EMPTY = "102";
	public final static String ERROR_CODE_USER_LOGIN_ID_DUPLICATE = "103";
	public final static String ERROR_DESC_USER_LOGIN_ID_DUPLICATE = "User Login ID already registered";
	
	public final static String ERROR_CODE_USER_LOGIN_ID_INVALID = "104";
	public final static String ERROR_CODE_USER_PASSWORD_INVALID = "105";
	
	public final static String ERROR_CODE_USER_NAME_EMPTY = "106";
	
	public final static String ERROR_CODE_USER_TEAM_NAME_EMPTY = "108";
	public final static String ERROR_CODE_USER_TEAM_NAME_INVALID = "109";
	public final static String ERROR_CODE_USER_TEAM_NAME_DUPLICATE = "110";
	public final static String ERROR_DESC_USER_TEAM_NAME_DUPLICATE = "Team name already registered. Try another name!";
	
	public final static String ERROR_CODE_USER_PHONE_INVALID = "111";
	public final static String ERROR_CODE_USER_ID_EMPTY = "112";
	public final static String ERROR_CODE_USER_ID_INVALID = "113";
	
	public final static String ERROR_CODE_USER_GROUP_NAME_DUPLICATE = "114";
	public final static String ERROR_DESC_USER_GROUP_NAME_DUPLICATE = "League name already registered. Try another name!";
	
	public final static String ERROR_CODE_USER_GROUP_INVITATION_INVALID = "115";
	public final static String ERROR_DESC_USER_GROUP_INVITATION_INVALID = "Some required field to add users to League is missing";
	
	public final static String ERROR_CODE_USER_GROUP_ID_INVALID = "116";
	public final static String ERROR_DESC_USER_GROUP_ID_INVALID = "League not found";
	
	public final static String ERROR_CODE_USER_GROUP_USER_INVALID = "117";
	public final static String ERROR_DESC_USER_GROUP_USER_INVALID = "User in invitation list not found";
	
	public final static String ERROR_CODE_PHONE_NO_EMPTY = "118";
	
	public final static String ERROR_CODE_USER_PHONE_DUPLICATE = "119";
	public final static String ERROR_DESC_USER_PHONE_DUPLICATE = "User Phone No. is already registered";
	
	
	public final static String ERROR_CODE_USER_PWD_NOT_FOUND = "120";
	public final static String ERROR_DESC_USER_PWD_NOT_FOUND = "Auth failed due to incorrect login user Id or password";
	
	public final static String ERROR_CODE_USER_NOT_FOUND = "121";
	public final static String ERROR_DESC_USER_NOT_FOUND = "Login user ID not found";
	
	public final static String ERROR_CODE_INVALID_REGISTRATION_REQUEST = "122";
	public final static String ERROR_DESC_INVALID_REGISTRATION_REQUEST = "Registration request is not valid";
	
	public final static String ERROR_CODE_TOURNAMENT_ID_EMPTY = "123";
	
	public final static String ERROR_CODE_GROUP_NAME_NO_EMPTY = "124";
	public final static String ERROR_CODE_GROUP_NAME_INVALID = "125";
	
	public final static String ERROR_CODE_USER_INVALID = "126";
	public final static String ERROR_DESC_USER_INVALID = "User Not found";
	
	public final static String ERROR_CODE_WRONG_CONFIRMATION_CODE = "127";
	public final static String ERROR_DESC_WRONG_CONFIRMATION_CODE = "Confirmation code does not match. Try again with correct Confirmation Code";
	
	public final static String ERROR_CODE_CONFIRMATION_CODE_EMPTY = "128";
	public final static String ERROR_CODE_PUBLISH_DATE_EMPTY = "129";
	
	public final static String ERROR_CODE_INVALID_TOKEN_REQUEST = "130";
	public final static String ERROR_DESC_INVALID_TOKEN_REQUEST = "Error in submitting answer. Logout and login again.";
	
	public final static String ERROR_CODE_INVALID_CITY = "131";
	public final static String ERROR_DESC_INVALID_CITY = "City specified is Invalid";
	
	public final static String ERROR_CODE_CITY_EMPTY = "132";
	public final static String ERROR_CODE_ANSWER_EMPTY = "133";
	
	public final static String ERROR_CODE_INVALID_CONTEST = "150";
	public final static String ERROR_DESC_INVALID_CONTEST = "Unknown Contest";
	
	public final static String ERROR_CODE_CUT_OFF_EXCEEDED = "151";
	public final static String ERROR_DESC_CUT_OFF_EXCEEDED = "Answer Not Saved: Data submitted after cutoff time";
	
	public final static String ERROR_CODE_ANSWER_ALREADY_SUBMITTED = "152";
	public final static String ERROR_DESC_ANSWER_ALREADY_SUBMITTED = "Answer is already submitted for this contest";
	
	public final static String ERROR_CODE_EMAIL_DEL_FAILURE = "121";
	public final static String ERROR_DESC_RESET_PWD_EMAIL_DEL_FAILURE = "Password is reset but failed to send email. Contact Administrator.";
	public final static String ERROR_DESC_CONF_CODE_EMAIL_DEL_FAILURE = "User Has been created. Failure in sending email with confirmation code, Contact Administrator.";
	
	public final static String ERROR_CODE_TOURNAMENT_NAME_EMPTY = "171";
	
	public final static String ERROR_CODE_TOURNAMENT_START_DATE_INVALID = "172";
	public final static String ERROR_DESC_TOURNAMENT_START_DATE_INVALID = "Tournament Start Date is Invalid";
	
	public final static String ERROR_CODE_TOURNAMENT_END_DATE_INVALID = "173";
	public final static String ERROR_DESC_TOURNAMENT_END_DATE_INVALID = "Tournament End Date is Invalid";
	
	public final static String ERROR_CODE_SPORT_TYPE_INVALID = "174";
	public final static String ERROR_DESC_SPORT_TYPE_INVALID = "Tournament Sport Type is Invalid";
	
	public final static String ERROR_CODE_TEAM_TYPE_INVALID = "175";
	public final static String ERROR_DESC_TEAM_TYPE_INVALID = "Tournament Team Type is Invalid";
	
	public final static String ERROR_CODE_TOURNAMENT_NAME_DUPLICATE = "176";
	public final static String ERROR_DESC_TOURNAMENT_NAME_DUPLICATE = "Tournament Name Already Exists";
	
	public final static String ERROR_CODE_TOURNAMENT_ID_NOT_FOUND = "177";
	public final static String ERROR_DESC_TOURNAMENT_ID_NOT_FOUND = "Tournament Not Found";
	
	public final static String ERROR_CODE_COUNTRY_ID_NOT_FOUND = "178";
	public final static String ERROR_DESC_COUNTRY_ID_NOT_FOUND = "Country Not Found";
	
	public final static String ERROR_CODE_COUNTRY_NAME_EMPTY = "179";
	public final static String ERROR_CODE_COUNTRY_SHORT_NAME_EMPTY = "180";
	
	public final static String ERROR_CODE_COUNTRY_ID_EMPTY = "181";
	
	public final static String ERROR_CODE_COUNTRY_DUPLICATE = "182";
	public final static String ERROR_DESC_COUNTRY_DUPLICATE = "Country Already Exists";
	
	public final static String ERROR_CODE_TEAM_ID_NOT_FOUND = "183";
	public final static String ERROR_DESC_TEAM_ID_NOT_FOUND = "Team Not Found";
	
	public final static String ERROR_CODE_TEAM_NAME_EMPTY = "184";
	public final static String ERROR_CODE_TEAM_SHORT_NAME_EMPTY = "185";
	public final static String ERROR_CODE_TEAM_ID_EMPTY = "186";
	
	public final static String ERROR_CODE_TEAM_TYPE_ID_EMPTY = "187";
	public final static String ERROR_CODE_SPORT_TYPE_ID_EMPTY = "188";

	public final static String ERROR_CODE_PLAYER_ID_NOT_FOUND = "189";
	public final static String ERROR_DESC_PLAYER_ID_NOT_FOUND = "Player Not Found";
	
	public final static String ERROR_CODE_PLAYER_FIRST_NAME_EMPTY = "190";
	public final static String ERROR_CODE_PLAYER_LAST_NAME_EMPTY = "191";
	public final static String ERROR_CODE_PLAYER_ID_EMPTY = "192";
	
	public final static String ERROR_CODE_TEAM_ID_INVALID = "193";
	public final static String ERROR_DESC_TEAM_ID_INVALID = "Team is Invalid";
	
	public final static String ERROR_CODE_TOURNAMENT_PUBLISH_DATE_INVALID = "194";
	public final static String ERROR_DESC_TOURNAMENT_PUBLISH_DATE_INVALID = "Tournament Publish Date is Invalid";
	
	
	public final static String ERROR_CODE_MATCH_DATE_EMPTY = "201";
	public final static String ERROR_CODE_MATCH_DATE_INVALID = "202";
	
	public final static String ERROR_CODE_MATCH_TEAMS_INVALID = "203";
	public final static String ERROR_DESC_MATCH_TEAMS_INVALID = "Match Teams selected is Invalid";
	
	public final static String ERROR_CODE_MATCH_ID_EMPTY = "204";
	
	public final static String ERROR_CODE_MATCH_NOT_FOUND = "205";
	public final static String ERROR_DESC_MATCH_NOT_FOUND = "Match Not Found";
	
	public final static String ERROR_CODE_MATCH_STATUS_ID_EMPTY = "206";
	public final static String ERROR_CODE_MATCH_STATUS_ID_INVALID = "207";
	
	public final static int MAX_CONFIRMATION_RETRY_COUNT = 5;
	
	public final static String ERROR_CODE_CONTEST_NAME_EMPTY = "210";
	public final static String ERROR_CODE_CONTEST_START_DATE_EMPTY = "211";
	public final static String ERROR_CODE_CONTEST_END_DATE_EMPTY = "212";
	public final static String ERROR_CODE_CONTEST_CUTOFF_DATE_EMPTY = "213";
	public final static String ERROR_CODE_CONTEST_DATE_INVALID = "214";
	public final static String ERROR_CODE_CONTEST_TYPE_INVALID = "215";
	
	public final static String ERROR_CODE_CONTEST_MATCH_INVALID = "216";
	public final static String ERROR_DESC_CONTEST_MATCH_INVALID = "Invalid Match for Tournament";
	public final static String ERROR_CODE_CONTEST_ID_EMPTY = "217";
	public final static String ERROR_CODE_CONTEST_STATUS_ID_EMPTY = "218";
	public final static String ERROR_CODE_QUESTION_ANSWER_COUNT_INVALID = "219";
	public final static String ERROR_DESC_QUESTION_ANSWER_COUNT_INVALID = "Answer Count is Invalid";
	public final static String ERROR_CODE_ANSWER_NOT_FOUND = "220";
	public final static String ERROR_DESC_ANSWER_NOT_FOUND = "Answer Not Found";
	public final static String ERROR_CODE_CONTEST_STATUS_ID_INVALID = "221";
	public final static String ERROR_CODE_CONTEST_CUTOFF_TIME_OVER = "251";
	public final static String ERROR_DESC_CONTEST_CUTOFF_TIME_OVER = "Contest is over for submitting answer";
	public final static String ERROR_CODE_CONTEST_INVALID_QUESTION = "252";
	public final static String ERROR_DESC_CONTEST_INVALID_QUESTION = "Invalid Question Answered for the contest";
	public final static String ERROR_CODE_CONTEST_INCOMPLETE_ANSWER = "253";
	public final static String ERROR_DESC_CONTEST_INCOMPLETE_ANSWER = "Incomplete Answer for the Question";
	public final static String ERROR_CODE_QUESTION_NOT_FOUND = "254";
	public final static String ERROR_DESC_QUESTION_NOT_FOUND = "Question Not Found";
	public final static String ERROR_CODE_PRIZE_WINNERS_INVALID = "255";
	public final static String ERROR_CODE_PRIZE_CONTEST_NOT_FOUND = "256";
	public final static String ERROR_DESC_PRIZE_CONTEST_NOT_FOUND = "Prize Contest Not Found";
	
	public final static String ERROR_CODE_CONTEST_PROCESS_NO_RECORD = "300";
	public final static String ERROR_DESC_CONTEST_PROCESS_NO_RECORD = "No Contests to be processed";
	public final static String ERROR_CODE_INVALID_PROCESS_REQUEST = "301";
	
	public final static String ERROR_CODE_GROUP_ID_EMPTY = "261";
	public final static String ERROR_CODE_GROUP_NAME_EMPTY = "262";
	
	public final static String ERROR_CODE_INVALID_GROUP = "263";
	public final static String ERROR_DESC_INVALID_GROUP = "Group does not exists any more";
	
	public final static String ERROR_CODE_GROUP_DELETE_NOT_ALLOWED = "264";
	public final static String ERROR_DESC_GROUP_DELETE_NOT_ALLOWED = "Group cannot be Deleted as there are registered users.";

	public final static String ERROR_CODE_INVITEE_DETAILS_EMPTY = "265";
	public final static String ERROR_DESC_INVITEE_DETAILS_EMPTY = "Invitee Details Missing";
	
	public final static String ERROR_CODE_INVITEE_USER_NOT_FOUND = "266";
	public final static String ERROR_DESC_INVITEE_USER_NOT_FOUND = "Invited User Not found. User can join once registration is complete";
	
	public final static String ERROR_CODE_GROUP_CODE_EMPTY = "267";
	
	public final static String ERROR_CODE_GROUP_CODE_INCORRECT = "268";
	public final static String ERROR_DESC_GROUP_CODE_INCORRECT = "Incorrect Group Confirmation Code.";
	
	public final static String ERROR_CODE_DUPLICATE_GROUP_NAME = "269";
	public final static String ERROR_DESC_DUPLICATE_GROUP_NAME = "Duplicate Group Name.";
	
	public final static String ERROR_CODE_DUPLICATE_INVITATION = "270";
	public final static String ERROR_DESC_DUPLICATE_INVITATION = "Cannot self-invite.";
	
	public final static String ERROR_CODE_INVALID_GROUP_NAME = "271";
	public final static String ERROR_DESC_INVALID_GROUP_NAME = "Enter shorter group name (Max 20)";

	public final static String ERROR_CODE_FAN_CLUB_JOINED = "272";
	public final static String ERROR_DESC_FAN_CLUB_JOINED = "You have already joined a Fan club!";
	
	public final static String ERROR_CODE_NOT_ENOUGH_COINS = "273";
	public final static String ERROR_DESC_NOT_ENOUGH_COINS = "You dont have enough coins to answer. Unlock the contest by watching the video!";
	
	public final static String ERROR_CODE_TOC_EMPTY = "290";
	
	public final static String ERROR_CODE_APP_UPDATE_REQUIRED = "274";
	public final static String ERROR_MSG_APP_UPDATE_REQUIRED = "Looks like your app is old. Update the app from playstore and relogin to proceed.";
	
}
