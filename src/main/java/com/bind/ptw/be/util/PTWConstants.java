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
	
	public final static String ERROR_CODE_INVALID_TOKEN_REQUEST = "130";
	public final static String ERROR_DESC_INVALID_TOKEN_REQUEST = "Error in submitting answer. Logout and login again.";
	
	public final static String ERROR_CODE_INVALID_CITY = "131";
	public final static String ERROR_DESC_INVALID_CITY = "City specified is Invalid";
	
	public final static String ERROR_CODE_CITY_EMPTY = "132";
	
	public final static String ERROR_CODE_INVALID_CONTEST = "150";
	public final static String ERROR_DESC_INVALID_CONTEST = "Unknown Contest";
	
	public final static String ERROR_CODE_CUT_OFF_EXCEEDED = "151";
	public final static String ERROR_DESC_CUT_OFF_EXCEEDED = "Answer Not Saved: Data submitted after cutoff time";
	
	public final static String ERROR_CODE_ANSWER_ALREADY_SUBMITTED = "152";
	public final static String ERROR_DESC_ANSWER_ALREADY_SUBMITTED = "Answer is already submitted for this contest";
	
	public final static String ERROR_CODE_EMAIL_DEL_FAILURE = "121";
	public final static String ERROR_DESC_EMAIL_DEL_FAILURE = "Password is reset but failed to send email. Contact Administrator";
	
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
	
	public final static int MAX_CONFIRMATION_RETRY_COUNT = 5;
}
