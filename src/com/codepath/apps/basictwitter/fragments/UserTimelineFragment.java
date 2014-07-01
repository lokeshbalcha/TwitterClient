package com.codepath.apps.basictwitter.fragments;

import org.json.JSONArray;

import android.os.Bundle;
import android.util.Log;

import com.codepath.apps.basictwitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public class UserTimelineFragment extends TweetsListFragment {

	private long userId;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		populateUserTimeline();
	}

	public void setUserId(long id) {
		this.userId = id;
	}

	public void populateUserTimeline() {
		client.getUserTimeline(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONArray json) {
				clearAll();
				addAll(Tweet.fromJSONArray(json));
			}

			@Override
			public void onFailure(Throwable e, String s) {
				Log.d("debug", e.toString());
				Log.d("debug", s.toString());
			}
		}, userId);
	}

	@Override
	void customLoadMoreDataFromApi(long max_id) {
		client.getMoreUserTimeline(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONArray json) {
				addAll(Tweet.fromJSONArray(json));
			}

			@Override
			public void onFailure(Throwable e, String s) {
				Log.d("debug", e.toString());
				Log.d("debug", s.toString());
			}
		}, userId, max_id);
	}

}
