package com.twitterapp;

import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ValueChangeEvent;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.dialect.FirebirdDialect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.twitterapp.models.Subscribe;
import com.twitterapp.models.Tweet;
import com.twitterapp.models.User;

/**
 * The Database Bean handles all interactions with the database. It has an
 * autowired Session Factory and current User, if there is one.
 * 
 *
 */
@Component
@Scope("session")
public class DatabaseBean {

	@Autowired
	private SessionFactory sessionFactory;

	// @Autowired
	// private UserImpl currentUser;

	@Autowired
	private TweetImpl tweetImpl;

	private List<Tweet> tweetList;

	private List<Tweet> tweetList2;

	private String testString;

	private User testUser;
	
	private String currentPendingFriend;

	@Autowired
	private AuthenticationBean authBean;

	private List<User> listOfUsers = new ArrayList<>();

	private List<String> listOfUserNames;

	private List<Integer> listOfUserIds;

	private String friendUser;

	/**
	 * Use this method to retrieve a list of Users whose name matches %name%.
	 * 
	 * 
	 * @param name
	 *            the name of the user searched for
	 * @return List<User> a list of users who match the HQL query based on the
	 *         argument with wildcards
	 */
	@Transactional
	public List<User> getUsersByName(String name) {
		List<User> userList;

		Session session = sessionFactory.getCurrentSession();

		Query query = session.createQuery("FROM User WHERE userName LIKE '%"
				+ name + "%'");
		userList = (List<User>) query.list();

		return userList;
	}

	/**
	 * This method returns a list with all users registered on the DB
	 * 
	 * @return List<User>
	 */
	@Transactional
	public List<String> getAllUserNames() {
		List<User> userList;
		listOfUserIds = new ArrayList<>();
		listOfUserNames = new ArrayList<>();

		Session session = sessionFactory.getCurrentSession();

		Query query = session.createQuery("FROM User");
		userList = (List<User>) query.list();

		listOfUserNames = new ArrayList<>();
		for (User u : userList) {
			listOfUsers.add(u);
			listOfUserIds.add(u.getUserId());
			listOfUserNames.add(u.getUserName());
		}

		return listOfUserNames;
	}

	/**
	 * Returns a single User whose ID matches your argument
	 * 
	 * @param userid
	 *            unique ID of User
	 * @return the user whose ID matches the argument
	 */
	@Transactional
	public User getUserbyId(int userid) {
		User user;

		Session session = sessionFactory.getCurrentSession();

		Query query = session.createQuery("FROM User WHERE userId = '" + userid
				+ "'");
		user = (User) query.uniqueResult();

		return user;
	}

	/**
	 * Use this method to save a new User to the database.
	 * 
	 * @param username
	 * @param password
	 * @return returns true after User is saved to DB
	 */
	@Transactional
	public boolean registerUser(String username, String password) {
		User newUser = new User(username, password);

		Session session = sessionFactory.getCurrentSession();

		session.save(newUser);

		return true;
	}

	/**
	 * Use this method to save a message to the DB. It may be more efficient to
	 * save messages in batches rather than individually. If this is necessary I
	 * can add the functionality later.
	 * 
	 * @param messageBody
	 * @param type
	 *            Is the message public or private?
	 * @return
	 */
	@Transactional
	public boolean saveMessage(String type) {
		Tweet message = new Tweet();
		User user;

		Session session = sessionFactory.getCurrentSession();

		String username = authBean.getCurrentUser().getUserName();

		user = getUsersByName(username).get(0);

		message.setTweet(tweetImpl.getMessageBody());
		message.setUser(user);
		message.setType(type);

		session.save(message);

		return true;
	}

	/**
	 * This method is intended for private messages. The userid param indicates
	 * the recipient of the message.
	 * 
	 * @param messageBody
	 * @param type
	 * @param userid
	 * @return
	 */
	@Transactional
	public boolean saveMessage(String type, int userid) {
		Tweet message = new Tweet();
		User user;

		Session session = sessionFactory.getCurrentSession();

		Query query = session
				.createQuery("FROM User WHERE userName = :name AND userPassword = :password");
		query.setParameter("name", authBean.getCurrentUser().getUserName());
		query.setParameter("password", authBean.getCurrentUser().getPassword());
		user = (User) query.uniqueResult();

		message.setTweet(tweetImpl.getMessageBody());
		message.setUser(user);
		message.setType(type);

		session.save(message);

		return true;
	}

	/**
	 * Saves List<Tweet> to DB.
	 * 
	 * @param messageList
	 * @return returns true when messages are added
	 */
	@Transactional
	public boolean saveMessages(List<Tweet> messageList) {

		Session session = sessionFactory.getCurrentSession();

		// add messages if required fields not null
		for (Tweet message : messageList) {
			if (!(message.getUser() == null) && !(message.getType() == null)
					&& !(message.getTweet() == null)) {
				session.save(message);
			} else
				System.out.println("Invalid message skipped");
		}

		return true;
	}

	/**
	 * This method retrieves all public messages belonging to the given User
	 * 
	 * @param user
	 * @return List<Tweet> contains all the user's public messages
	 */
	@Transactional
	public List<Tweet> getPublicMessages() {
		List<Tweet> messageList;

		Session session = sessionFactory.getCurrentSession();

		Query query = session.createQuery("FROM Tweet WHERE user = :user");
		query.setParameter("user", getUserbyId(authBean.getCurrentUser()
				.getUserId()));

		messageList = (List<Tweet>) query.list();

		tweetList = new ArrayList<>();
		for (Tweet t : messageList) {
			if (t != null) {
				tweetList.add(t);
			}
		}

		return tweetList;
	}

	public List<Tweet> getTweetList() {
		return tweetList;
	}

	public void setTweetList(List<Tweet> tweetList) {
		this.tweetList = tweetList;
	}

	/**
	 * This method retrieves private messages between two users, one passed as a
	 * user object, the other passed as a user id.
	 * 
	 * @param user1
	 * @param user2id
	 * @return List<Tweet> contains all messages sent privately between two
	 *         users
	 */
	@Transactional
	public List<Tweet> getPrivateConversation(int user2id) {
		List<Tweet> messageList;
		User user = getUserbyId(authBean.getCurrentUser().getUserId());

		Session session = sessionFactory.getCurrentSession();

		Query query = session
				.createQuery("FROM Tweet WHERE (User = :user1) AND (type = 'private') AND (isConvo = :user2id)");
		query.setParameter("user1", user);
		query.setParameter("user2id", user2id);
		messageList = (List<Tweet>) query.list();

		tweetList = new ArrayList<>();
		for (Tweet t : messageList) {
			tweetList.add(t);
		}

		return tweetList;
	}

	/**
	 * This method retrieves a list of all a user's friends where friends are
	 * represented by the presence of mutual subscribe objects between 2 users
	 * 
	 * @param user
	 * @return List<User> the user's friends
	 */
	@Transactional
	public List<String> getFriendsFromDB() {
		List<User> friendList = new ArrayList<>();
		List<String> nameList = new ArrayList<>();
		List<Subscribe> sList;
		List<User> confirmedFriendList = new ArrayList<>();
		UserImpl currentUserImpl = authBean.getCurrentUser();

		User user = getUserbyId(currentUserImpl.getUserId());

		Session session = sessionFactory.getCurrentSession();

		// get subscribe objects of for all subscriptions made by current user
		Query query = session
				.createQuery("FROM Subscribe WHERE userName = :username");
		query.setParameter("username", currentUserImpl.getUserName());

		sList = (List<Subscribe>) query.list();

		// turn subscribe into username of user being subscribed to
		// these are names of user's potential friends
		for (Subscribe sub : sList) {
			if (sub.getSubscribedUserName() != null)
				if (!nameList.contains(sub.getSubscribedUserName())) // weed out
																		// duplicates
					nameList.add(sub.getSubscribedUserName());
		}

		// turn usernames into users
		// this will be an issue if we have multiple users with the same name
		for (String s : nameList) {
			Query q = session
					.createQuery("FROM User WHERE userName = :username");
			q.setParameter("username", s);
			User tempUser = (User) q.uniqueResult();
			if (tempUser != null) // bug-killer
				friendList.add(tempUser);
		}

		// find users with mutual subscriptions (i.e. friends)
		// and add to list of confirmed friends
		// this will add everybody in friendslist until we fix alreadyfriends
		// method!
		for (User u : friendList) {
			if (alreadyFriends(user, u)) {
				confirmedFriendList.add(u);
			}
		}

		List<String> stringFriendList = new ArrayList<>();
		for (User us : confirmedFriendList) {
			stringFriendList.add(us.getUserName());
		}

		return stringFriendList;
	}

	/**
	 * Create a friendship in the DB between two users
	 * 
	 * @param user1
	 * @param user2
	 * @return true after update is complete
	 */
	@Transactional
	public boolean createFriendRequest() {
		// Create 2 subscribe object to represent a mutual friendship
		Subscribe request = new Subscribe();
		String requesterName;
		String requesteeName = testString;

		// stop adding null subscribes to DB!
		if (requesteeName.equals("") || requesteeName == null) {
			System.out.println("requestee is null");
			return false;
		}

		requesterName = authBean.getCurrentUser().getUserName();

		request.setUserName(requesterName);
		request.setSubscribedUserName(requesteeName);

		Session session = sessionFactory.getCurrentSession();

		session.save(request);

		System.out.println("subscribe added to DB! " + requesterName
				+ " has friended " + requesteeName);

		return true;
	}

	/**
	 * Checks if 2 users are already friends.
	 * 
	 * @param user1
	 * @param user2
	 * @return true if users are friends, false if not
	 */
	@Transactional
	public boolean alreadyFriends(User user1, User user2) {
		// Object o;
		// Object o1;

		Session session = sessionFactory.getCurrentSession();

		List<Subscribe> list1;
		Query query = session
				.createQuery("FROM Subscribe WHERE userName = :name1 AND subscribedUserName = :name2");
		query.setParameter("name1", user1.getUserName());
		query.setParameter("name2", user2.getUserName());
		list1 = (List<Subscribe>) query.list();

		List<Subscribe> list2;
		Query query1 = session
				.createQuery("FROM Subscribe WHERE userName = :name2 AND subscribedUserName = :name1");
		query1.setParameter("name1", user1.getUserName());
		query1.setParameter("name2", user2.getUserName());
		list2 = (List<Subscribe>) query1.list();

		if (!list1.isEmpty()) {
			System.out.println(list1.get(0).getUserName()
					+ " is subscribed to "
					+ list1.get(0).getSubscribedUserName());
			if (!list2.isEmpty()) {
				System.out.println(list2.get(0).getUserName()
						+ " is subscribed to "
						+ list2.get(0).getSubscribedUserName());
				System.out
						.println("second list in alreadyfriends not null! we have a friendship!");
				return true;
			}
		}

		return false;
	}

	/**
	 * This method looks for pending friend requests for the current user and
	 * returns a list of users who have sent request, else null.
	 * 
	 * @return List<User> or null
	 */
	@Transactional
	public List<Subscribe> pendingRequests() {
		// guarding against lazy init errors
		List<Subscribe> requestingList;
		List<Subscribe> pendingList = new ArrayList<>();

		List<User> tempUserList = new ArrayList<>();

		String currentUserName = authBean.getCurrentUser().getUserName();
		User user = getUserbyId(authBean.getCurrentUser().getUserId());
		User user2;

		Session session = sessionFactory.getCurrentSession();

		Query query = session
				.createQuery("FROM Subscribe WHERE subscribedUserName = :subscribedName");
		query.setParameter("subscribedName", currentUserName);
		requestingList = (List<Subscribe>) query.list();

		if (requestingList == null)
			return null;

		for (Subscribe s : requestingList) {
			user2 = getUsersByName(s.getUserName()).get(0);
			tempUserList.add(user2);
		}

		System.out.println(tempUserList.size() + " entries");
		// ensure you don't return users who are already friends
		int index = 0;
		for (User u : tempUserList) {
			System.out.println(u.getUserName());
			if (!alreadyFriends(user, u)) {
				// requesting list and pending list are copies, hence: ...
				pendingList.add(requestingList.get(index));
				currentPendingFriend = requestingList.get(index).getUserName();
				System.out.println(u.getUserName()
						+ "'s pending friend request added");
			}
			index++;
		}

		if (pendingList.isEmpty()) {
			System.out.println("oh snap you got a null");
			return null;
		} else
			System.out.println("i'm about to give you what you want!");
		return pendingList;
	}

	/**
	 * Returns boolean, true if pendingRequests() returns something (i.e. if
	 * there are pending friend requests), false if it returns null
	 * 
	 * @return boolean
	 */
	@Transactional
	public boolean hasPendingRequests() {
		if (pendingRequests() == null)
			return false;
		else
			return true;
	}

	@Transactional
	public List<Tweet> displayFriendWall() {
		List<Tweet> messageList;

		Session session = sessionFactory.getCurrentSession();

		Query query = session.createQuery("FROM User");
		List<User> userList = new ArrayList<>();

		userList = query.list();

		User friend = null;
		for (User use : userList) {
			if (use.getUserName().equals(friendUser)) {
				friend = use;
			}
		}
		
		query = session.createQuery("FROM Tweet WHERE user = :user");
		query.setParameter("user", getUserbyId(friend.getUserId()));

		messageList = (List<Tweet>) query.list();

		tweetList2 = new ArrayList<>();
		for (Tweet t : messageList) {
			if (t != null) {
				tweetList2.add(t);
			}
		}
		
		return tweetList2;
	}

	public String getTestString() {
		return testString;
	}

	public void setTestString(String testString) {
		this.testString = testString;
	}

	public String getFriendUser() {
		return friendUser;
	}

	public void setFriendUser(String friendUser) {
		this.friendUser = friendUser;
	}


	public String getCurrentPendingFriend() {
		return currentPendingFriend;
	}

	public void setCurrentPendingFriend(String currentPendingFriend) {
		this.currentPendingFriend = currentPendingFriend;
	}

	public User getTestUser() {
		return testUser;
	}

	public void setTestUser(User testUser) {
		this.testUser = testUser;
	}
}
