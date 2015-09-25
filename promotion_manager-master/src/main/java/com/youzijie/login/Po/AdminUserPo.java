package com.youzijie.login.Po;

import com.youzijie.login.dao.AdminUserDo;

public class AdminUserPo {
	private String userName;
	private String token;
	private int role;

	public AdminUserPo() {

	}

	public AdminUserPo(AdminUserDo adminUserDo) {
		this.userName = adminUserDo.getUserName();
		this.token = adminUserDo.getToken();
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}
}
