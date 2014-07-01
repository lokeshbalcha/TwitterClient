package com.codepath.apps.basictwitter.fragments;

import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.basictwitter.R;
import com.codepath.apps.basictwitter.TwitterApplication;
import com.codepath.apps.basictwitter.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

public class UserProfileFragment extends Fragment {

	private long userId;

	private ImageView ivProfileImage;
	private TextView tvName;
	private TextView tvTagline;
	private TextView tvFollowers;
	private TextView tvFollowing;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		populateProfileHeader();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_user_profile, container,
				false);

		ivProfileImage = (ImageView) v.findViewById(R.id.ivProfileImage);
		tvName = (TextView) v.findViewById(R.id.tvName);
		tvTagline = (TextView) v.findViewById(R.id.tvTagline);
		tvFollowers = (TextView) v.findViewById(R.id.tvFollowers);
		tvFollowing = (TextView) v.findViewById(R.id.tvFollowing);

		return v;
	}

	public void populateProfileHeader() {
		TwitterApplication.getRestClient().getUserDetails(
				new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(JSONObject json) {
						User user = User.fromJSON(json);
						String profileImageUrl = user.getProfileImageUrl();

						ivProfileImage.setImageResource(android.R.color.transparent);
						ImageLoader imageLoader = ImageLoader.getInstance();
						imageLoader.displayImage(profileImageUrl,
								ivProfileImage);

						tvName.setText(user.getName());
						tvTagline.setText(user.getScreenName());
						tvFollowers.setText(user.getCountFollowers()
								+ " Followers");
						tvFollowing.setText(user.getCountFriends()
								+ " Following");
					}

					@Override
					public void onFailure(Throwable e, String s) {
						Log.d("debug", e.toString());
						Log.d("debug", s.toString());
					}
				}, userId);
	}

	public void setUserId(long id) {
		this.userId = id;
	}
}
