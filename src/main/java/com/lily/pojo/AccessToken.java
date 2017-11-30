package com.lily.pojo;

public class AccessToken {

	private Integer id;

	private String access_token;

	public AccessToken() {
		super();
	}

	public AccessToken(String access_token) {
		super();
		this.access_token = access_token;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

}
