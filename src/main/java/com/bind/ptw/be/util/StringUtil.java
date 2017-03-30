package com.bind.ptw.be.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.ThreadLocalRandom;

public class StringUtil {
	
	public static boolean isEmptyNull(String inputStr){
		boolean result = false;
		if(inputStr == null || inputStr.trim().isEmpty()){
			result = true;
		}
		return result;
	}
	
	public static boolean isEmptyNull(Integer inputInt){
		boolean result = false;
		if(inputInt == null || inputInt == 0){
			result = true;
		}
		return result;
	}
	
	public static String convertDateTImeToString(Date inputDate){
		String returnDateStr;
		if(inputDate == null ){
			returnDateStr = null;
		}else{
			SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy HH:mm");
			format.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
			returnDateStr = format.format(inputDate);
		}
		return returnDateStr;
	}
	
	public static String convertDateToString(Date inputDate){
		String returnDateStr;
		if(inputDate == null ){
			returnDateStr = null;
		}else{
			SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
			format.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
			returnDateStr = format.format(inputDate);
		}
		return returnDateStr;
	}
	
	public static Date convertStringToDate(String inputDateStr) throws ParseException{
		Date returnDate;
		if(inputDateStr == null ){
			returnDate = null;
		}else{
			SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
			format.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
			returnDate = format.parse(inputDateStr);
			
		}
		return returnDate;
	}
	
	public static Date convertStringToDateTime(String inputDateStr) throws ParseException{
		Date returnDate;
		if(inputDateStr == null ){
			returnDate = null;
		}else{
			SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy HH:mm");
			format.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
			returnDate = format.parse(inputDateStr);
			
		}
		return returnDate;
	}
	
	public static Date floorDate(Date inputDate){
		Calendar cal = Calendar.getInstance();
		cal.setTime(inputDate);
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND,0);
		inputDate = cal.getTime();
		return inputDate;
	}
	
	public static Date cielDate(Date inputDate){
		Calendar cal = Calendar.getInstance();
		cal.setTime(inputDate);
		cal.set(Calendar.HOUR, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND,59);
		inputDate = cal.getTime();
		return inputDate;
	}
	
	public static int createRandomNum(int start, int end){
		int randomNum = ThreadLocalRandom.current().nextInt(start, end + 1);
		return randomNum;
	}
	
	public static String convertToTokens(Integer[] intArr){
		StringBuilder strBuilder = new StringBuilder();
		int index = 0;
		for (Integer intEle : intArr) {
			strBuilder.append(intEle);
			index++;
			if(index < intArr.length){
				strBuilder.append(",");
			}
		}
		
		return strBuilder.toString();
	}

}
