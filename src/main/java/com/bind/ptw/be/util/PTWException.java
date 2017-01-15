package com.bind.ptw.be.util;

public class PTWException extends Exception{
	private static final long serialVersionUID = 612534479608911675L;
	private String code;
	private String description;
	
	public PTWException(){
		super();
	}

	public PTWException(String code, String description){
		super("code [" + code + "] description [" + description + "]");
		this.code = code;
		this.description = description;
	}
	
	public PTWException(String code, String description, Throwable cause) {

		super("code [" + code + "] description [" + description + "]", cause);
		this.code = code;
		this.description = description;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	

}
