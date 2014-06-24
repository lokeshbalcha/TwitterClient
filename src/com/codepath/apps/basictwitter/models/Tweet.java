package com.codepath.apps.basictwitter.models;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class Tweet {
	private String body;
	private long uid;
	private String createdAt;
	private User user;
	public static Long since_id = 0l;
	public static Long max_id = 0l;
	public static Long top_max_id = 0l;
	
	

	public String getBody() {
		return body;
	}

	public long getUid() {
		return uid;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public User getUser() {
		return user;
	}

	public static ArrayList<Tweet> fromJSONArray(JSONArray jsonArray) {
		ArrayList<Tweet> tweets = new ArrayList<Tweet>(jsonArray.length());
		
		for(int i = 0; i<jsonArray.length(); i++){
			JSONObject tweetJson = null;
			try{
				tweetJson = jsonArray.getJSONObject(i);
			}catch(Exception e){
				e.printStackTrace();
				continue;
			}
			Tweet tweet = Tweet.fromJSON(tweetJson);
			if(tweet != null){
				tweets.add(tweet);
			}
			
			}
			return tweets;
		}
	public static Tweet fromJSON(JSONObject jsonObject){
		Tweet tweet = new Tweet();
		//for extracting values from json to populate member variables
		try{
			tweet.body = jsonObject.getString("text");
			tweet.uid = jsonObject.getLong("id");
			//for the first time set of min id
			if(Tweet.since_id == 0) {
				Tweet.since_id = tweet.uid;
			}
			
			if(tweet.uid < Tweet.since_id) {
				Tweet.since_id = tweet.uid;
			}
			
			if (tweet.uid > Tweet.max_id) {
				Tweet.max_id = tweet.uid;
				Tweet.top_max_id = tweet.uid;
			}
			tweet.createdAt = jsonObject.getString("created_at");
			tweet.user = User.fromJSON(jsonObject.getJSONObject("user"));
		} catch(JSONException e){
			e.printStackTrace();
			return null;
		}
		return tweet;
	}
	
	public String toString(){
		return getBody()+" -" + getUser().getScreenName();
	}
	}

