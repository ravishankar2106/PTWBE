package com.bind.ptw.be.services.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.bind.ptw.be.dto.UserGroupBean;
import com.bind.ptw.be.dto.UserGroupInvitationBean;
import com.bind.ptw.be.util.PTWConstants;
import com.bind.ptw.be.util.PTWException;
import com.bind.ptw.be.util.StringUtil;

public class UserGroupValidator {
	
	public static void validateUserGroupInvitationRequest(UserGroupInvitationBean userGroupInvitation)throws PTWException{
		
	}
	
	public static void validateUserGroupCreationRequest(UserGroupBean userGroupBean)throws PTWException{
		
		if(StringUtil.isEmptyNull(userGroupBean.getGroupName())){
			throw new PTWException(PTWConstants.ERROR_CODE_GROUP_NAME_NO_EMPTY, PTWConstants.ERROR_DESC_FIELD_EMPTY + "League Name");
		}
		
		Pattern pattern = null;
		Matcher matcher = null;
		
		pattern = Pattern.compile( PTWConstants.GROUP_NAME_REGEX );
		matcher = pattern.matcher( userGroupBean.getGroupName());
		if( !matcher.matches() ){
			throw new PTWException( PTWConstants.ERROR_CODE_GROUP_NAME_INVALID, 
					"Group name cant be more than 20 characters" );
		}
	}

}
