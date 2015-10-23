package com.twitterapp;


import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.twitterapp.models.User;

/*
 * Bean that handles New User Registration
 *  @author George McClain
 */

@Component
@Scope("session")
public class RegistrationBean {
	
	private String userName;
	private String password;
	private NavigationHandler handler;
	@Autowired
	private SessionFactory sessionFactory;
	/** Checks the database to see if a user with the given username
	 * exists. If so, the user is notified. If not, the user is registered to the database, and
	 * redirected to the login page.
	 * @return String: The URL of the page the user is redirected to.
	 */
	@Transactional
	public String register(){

		Session session = sessionFactory.getCurrentSession();
		Query checkUser = session.createQuery("from User u where u.userName ='" + userName + "'");
	
		if (checkUser.list() == null ||
				checkUser.list().isEmpty()) {
			User newUser = new User();
			newUser.setUserName(userName);
			newUser.setUserPassword(password);
			session.save(newUser);
			return handler.toHomePage();
		}

		return handler.toRegistrationPage();
	}


	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
}