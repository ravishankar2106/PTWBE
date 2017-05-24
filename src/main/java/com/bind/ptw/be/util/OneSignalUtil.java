package com.bind.ptw.be.util;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import com.bind.ptw.be.dto.PushBean;
import com.google.gson.Gson;

public class OneSignalUtil {
	
	public static final String ONE_SIGNAL_URL = "https://onesignal.com/api/v1/notifications";
	
	public static void sendNotification(PushBean pushBean, String oneSignalAuthKey){
		try{
			
			URL url = new URL(ONE_SIGNAL_URL);
			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			con.setUseCaches(false);
			con.setDoOutput(true);
			con.setDoInput(true);
			con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			con.setRequestProperty("Authorization", "Basic "+ oneSignalAuthKey);
			con.setRequestMethod("POST");
			
			Gson gson = new Gson();
			String payLoad = gson.toJson(pushBean);
			/*String payLoad = "{"
                    +   "\"app_id\": \"9a1af85b-5d82-4807-b3a4-2be4a8ae6a5c\","
                    +   "\"included_segments\": [\"All\"],"
                    +   "\"data\": {\"newkey\": \"newvalue\"},"
                    +   "\"contents\": {\"en\": \"sending new message\"}"
                    + "}";*/
			byte[] sendBytes = payLoad.getBytes("UTF-8");
			con.setFixedLengthStreamingMode(sendBytes.length);
			
			OutputStream outputStream = con.getOutputStream();
			outputStream.write(sendBytes);

			int httpResponse = con.getResponseCode();
			String jsonResponse;

			if (  httpResponse >= HttpURLConnection.HTTP_OK && httpResponse < HttpURLConnection.HTTP_BAD_REQUEST) {
				Scanner scanner = new Scanner(con.getInputStream(), "UTF-8");
			    jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
			    scanner.close();
			}
			else {
			    Scanner scanner = new Scanner(con.getErrorStream(), "UTF-8");
			    jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
			    scanner.close();
			}
			System.out.println("jsonResponse:\n" + jsonResponse);
		}catch(Exception exception){
			exception.printStackTrace();
		}
		
	}

}
