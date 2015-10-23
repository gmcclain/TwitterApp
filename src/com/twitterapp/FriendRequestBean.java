package com.twitterapp;
//package com.cooksys.facebookapp;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.hibernate.Query;
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Scope;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.cooksys.facebookapp.models.Subscribe;
//import com.cooksys.facebookapp.models.User;
//
//
//@Component
//@Scope("singleton")
//public class FriendRequestBean {
//
//	private User user;
//	private List<Subscribe> friendRequests;
//	@Autowired
//	private SessionFactory sessionFactory;
//	
//	private AuthenticationBean authenticationBean;
//	private boolean friend = false;
//	
//	@Transactional
//	public void friendRequest(User user2) {
//
//		Session session = sessionFactory.getCurrentSession();
//		Subscribe newFriend = new Subscribe();
//		newFriend.setUserName(user.getUserName());
//		newFriend.setSubscribedUserName(user2.getUserName());
//		session.save(newFriend);
//		setFriend(false);
//
//	}
//	@Transactional
//	public void ignoreRequest(User user2) {
//
//		Session session = sessionFactory.getCurrentSession();
//		Query subscription = session.createQuery("from Subscribe s where s.userName = '" + user.getUserName() + "' and s.subscribeUserName = '"
//				+ user2.getUserName() + "'");
//		Subscribe delete = (Subscribe) subscription.list().get(0);
//		session.delete(delete);
//         setFriend(false);
//	}		
//
//	@Transactional
//	public void addRequest(User user2) {
//
//		Session session = sessionFactory.getCurrentSession();
//		Subscribe request = new Subscribe();
//		request.setUserName(user.getUserName());
//		request.setSubscribedUserName(user2.getUserName());
//		session.save(request);
//
//		setFriend(true);
//	}
//    @Transactional
//	public List<Subscribe> getFriendRequests() {
//		Session session = sessionFactory.getCurrentSession();		
//		return friendRequests;
//	}
//
//
//	/**
//	 * Sets the user to the object from the database.
//	 */
//	@Transactional
//	public void setUser() {
//		// Create the session and query
//		Session session = sessionFactory.getCurrentSession();
//		Query getUser = session.createQuery("from User u where u.userName ='" + authenticationBean.getUserName() + "' and u.userPassword ='" + authenticationBean.getPassword() +"'");
//		this.user = (User) getUser.list().get(0);
//	}
//
//	public boolean isFriend() {
//		return friend;
//	}
//
//	public void setFriend(boolean friend) {
//		this.friend = friend;
//	}
//
//
//	
//	
//	
//}
