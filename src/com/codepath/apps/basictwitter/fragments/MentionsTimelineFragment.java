package com.codepath.apps.basictwitter.fragments;

import org.json.JSONArray;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.codepath.apps.basictwitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public class MentionsTimelineFragment extends TweetsListFragment {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		populateMentionsTimeline();
	}

	public void populateMentionsTimeline() {

		client.getMentionsTimeline(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONArray json) {
				Toast.makeText(getActivity(), "Mentions-SUCCESS", Toast.LENGTH_LONG).show();
				clearAll();
				addAll(Tweet.fromJSONArray(json));
			}

			@Override
			public void onFailure(Throwable e, String s) {
				Toast.makeText(getActivity(), "Mentions-FAILURE", Toast.LENGTH_LONG).show();

				Log.d("debug", e.toString());
				Log.d("debug", s.toString());
			}
		});
	}

	public void customLoadMoreDataFromApi(long max_id) {
		client.getMoreMentionsTimeline(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONArray json) {
				addAll(Tweet.fromJSONArray(json));
			}

			@Override
			public void onFailure(Throwable e, String s) {
				Log.d("debug", e.toString());
				Log.d("debug", s.toString());
			}
		}, max_id);
	}
}
