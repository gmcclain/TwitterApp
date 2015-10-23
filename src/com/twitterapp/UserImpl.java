package com.twitterapp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of the UserDAO
 *
 */
@Component
@Scope("singleton")
public class UserImpl implements UserDAO {

	private String userName;
	private String password;
	private Integer userId;
	
	private List<String> allUserNames;
	
	
//	public List<String> getAllUserNames() {
//	
//		allUserNames = DBBean.getAllUserNames();
//		
//		return allUserNames;
//	}

	public void setAllUserNames(List<String> allUserNames) {
		this.allUserNames = allUserNames;
	}

	@Override
	public String getUserName() {
		return userName;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public Integer getUserId() {
		// TODO Auto-generated method stub
		return userId;
	}

	@Override
	public void setUserId(Integer userid) {

		this.userId = userid;
	}

}
