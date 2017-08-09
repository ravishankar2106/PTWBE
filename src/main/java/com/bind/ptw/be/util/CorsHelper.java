package com.bind.ptw.be.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class CorsHelper {

	public List<String> allowedOrigins() {
		List<String> list = new ArrayList<String>();
		list.add("*");
		return list;
	}
	
	
	public List<String> allowedHeaders() {
		List<String> list = new ArrayList<String>();
		list.add("*");
		return list;
	}
	
	
	public List<String> allowedMethods() {
		List<String> list = new ArrayList<String>();
		list.add("GET");
		list.add("PUT");
		list.add("POST");
		list.add("DELETE");
		list.add("OPTIONS");
		return list;
	}
}
