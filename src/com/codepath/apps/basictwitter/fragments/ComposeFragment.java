package com.codepath.apps.basictwitter.fragments;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.basictwitter.R;
import com.codepath.apps.basictwitter.TwitterClient;
import com.codepath.apps.basictwitter.TwitterApplication;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ComposeFragment extends Fragment {
	private TwitterClient client;

	private ImageView ivProfileImage;
	private TextView tvUserName;
	private TextView tvUserHandle;
	private EditText etComposeTweet;
	private TextView tvNumCharsLeft;

	private final int MAX_TWEET_CHARS = 140;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		client = TwitterApplication.getRestClient();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_compose, container, false);

		ivProfileImage = (ImageView) v.findViewById(R.id.ivProfileImage);
		etComposeTweet = (EditText) v.findViewById(R.id.etComposeTweet);
		tvUserName = (TextView) v.findViewById(R.id.tvUserName);
		tvUserHandle = (TextView) v.findViewById(R.id.tvUserHandle);
		tvNumCharsLeft = (TextView) v.findViewById(R.id.tvNumCharsLeft);

		// Get the user details
		getUserCredentials();

		tvNumCharsLeft.setText(Integer.toString(MAX_TWEET_CHARS));

		etComposeTweet.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				updateCharsLeft();
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
			}
		});
		return v;
	}

	private void updateCharsLeft() {
		int numCharsLeft = MAX_TWEET_CHARS
				- etComposeTweet.getText().toString().length();
		tvNumCharsLeft.setText(Integer.toString(numCharsLeft));
	}

	public void getUserCredentials() {
		client.verifyCredentials((new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject json) {

				try {
					String profileImageUrl = json
							.getString("profile_image_url").toString();

					String screen_name = json.getString("screen_name")
							.toString();
					String name = json.getString("name")
							.toString();
					
					ivProfileImage
							.setImageResource(android.R.color.transparent);
					ImageLoader imageLoader = ImageLoader.getInstance();
					imageLoader.displayImage(profileImageUrl, ivProfileImage);

					tvUserName.setText(name);
					tvUserHandle.setText("@" + screen_name);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(Throwable e, String s) {
				Log.d("debug", e.toString());
				Log.d("debug", s.toString());
			}
		}));
	}

	public void postTweet() {
		String newTweet = etComposeTweet.getText().toString();

		// Do nothing if the tweet is longer than 140 chars
		if (newTweet.length() > MAX_TWEET_CHARS)
			return;

		client.postTweet((new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject json) {
				listener.onTweetPosted();
			}

			@Override
			public void onFailure(Throwable e, String s) {
				Log.d("debug", e.toString());
				Log.d("debug", s.toString());
			}
		}), newTweet);
	}

	// Define the listener of the interface type
	// listener is the activity itself
	private onTweetPostedListener listener;

	// Define the events that the fragment will use to communicate
	public interface onTweetPostedListener {
		public void onTweetPosted();
	}

	// Store the listener (activity) that will have events fired once the
	// fragment is attached
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		if (activity instanceof onTweetPostedListener) {
			listener = (onTweetPostedListener) activity;
		} else {
			throw new ClassCastException(activity.toString()
					+ " must implement ComposeFragment.onTweetPostedListener");
		}
	}
}
