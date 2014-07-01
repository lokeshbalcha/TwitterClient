package com.codepath.apps.basictwitter.fragments;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.codepath.apps.basictwitter.EndlessScrollListener;
import com.codepath.apps.basictwitter.R;
import com.codepath.apps.basictwitter.TweetArrayAdapter;
import com.codepath.apps.basictwitter.TwitterClient;
import com.codepath.apps.basictwitter.TwitterApplication;
import com.codepath.apps.basictwitter.models.Tweet;

abstract public class TweetsListFragment extends Fragment {
	private ArrayList<Tweet> tweets;
	private TweetArrayAdapter aTweets;
	private ListView lvTweets;
	protected TwitterClient client;

	// Append more data into the adapter
	abstract void customLoadMoreDataFromApi(long max_id);

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		tweets = new ArrayList<Tweet>();
		aTweets = new TweetArrayAdapter(getActivity(), tweets);
		client = TwitterApplication.getRestClient();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout
		View v = inflater.inflate(R.layout.fragment_tweets_list, container,
				false);
		lvTweets = (ListView) v.findViewById(R.id.lvTweets);
		lvTweets.setAdapter(aTweets);

		lvTweets.setOnScrollListener(new EndlessScrollListener() {
			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				long max_id = tweets.get(totalItemsCount - 1).getUid();
				customLoadMoreDataFromApi(max_id);
			}
		});

		// Return the layout view
		return v;
	}

	public void clearAll() {
		aTweets.clear();
	}

	public void addAll(ArrayList<Tweet> tweets) {
		aTweets.addAll(tweets);
	}


}
