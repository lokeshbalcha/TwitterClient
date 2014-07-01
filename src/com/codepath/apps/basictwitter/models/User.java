package com.codepath.apps.basictwitter.models;

import org.json.JSONException;
import org.json.JSONObject;

public class User {
	private String name;
	private long uid;
	private String screenName;
	private String profileImageUrl;
	private String tagline;
	private int countFollowers;
	private int countFriends;
	
	// User.fromJSON(...)
	public static User fromJSON(JSONObject json) {
		User u = new User();
		
		try {
			u.name = json.getString("name");
			u.uid = json.getLong("id");
			u.screenName = json.getString("screen_name");
			u.profileImageUrl = json.getString("profile_image_url");
			u.tagline = json.getString("description");
			u.countFollowers = json.getInt("followers_count");
			u.countFriends = json.getInt("friends_count");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return u;
	}

	public String getName() {
		return name;
	}

	public long getUid() {
		return uid;
	}

	public String getScreenName() {
		return screenName;
	}

	public String getProfileImageUrl() {
		return profileImageUrl;
	}
	
	public String getTagline() {
		return tagline;
	}
	public int getCountFollowers() {
		return countFollowers;
	}

	public int getCountFriends() {
		return countFriends;
	}

}
