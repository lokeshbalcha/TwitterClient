package com.codepath.apps.basictwitter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.net.ParseException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.basictwitter.models.Tweet;
import com.nostra13.universalimageloader.core.ImageLoader;

public class TweetArrayAdapter extends ArrayAdapter<Tweet> {
	public TweetArrayAdapter(Context context, List<Tweet> tweets){
		super(context, 0, tweets);
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		//getting the data item for position
		Tweet tweet = getItem(position);
		//finding or inflating the template
		View v;
		if(convertView == null){
			LayoutInflater inflator = LayoutInflater.from(getContext());
			v = inflator.inflate(R.layout.tweet_item,parent, false);
		} else {
			v = convertView;
		}
		//Finding the views from template
		ImageView ivProfileImage = (ImageView) v.findViewById(R.id.ivProfileImage);
		TextView tvUserName = (TextView) v.findViewById(R.id.tvUserName);
		TextView tvBody = (TextView) v.findViewById(R.id.tvBody);
		TextView tvUserid = (TextView) v.findViewById(R.id.tvUserid);
		TextView tvCreatedat = (TextView) v.findViewById(R.id.tvCreatedat);
		ivProfileImage.setImageResource(android.R.color.transparent);
		ImageLoader imageLoader = ImageLoader.getInstance();
		//populating views with tweet data
		imageLoader.displayImage(tweet.getUser().getProfileImageUrl(),  ivProfileImage);
		tvUserName.setText(tweet.getUser().getName());
		tvUserid.setText(" @"+tweet.getUser().getScreenName());
		try {
			tvCreatedat.setText(getRelativeTime(tweet.getCreatedAt()));
		} catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tvBody.setText(tweet.getBody());
		return v;
		
		
	}
	private String getRelativeTime(String time) throws java.text.ParseException {
		try {
			Date d = new SimpleDateFormat("EEE MMM dd kk:mm:ss ZZZZ yyyy", Locale.ENGLISH).parse(time);
			Date now = new Date();
			long diff = now.getTime() - d.getTime();
			//less than 1 minute
			if( diff <= 60000) {
				return "Now";
			}
			//less than 1 hour
			else if ( diff <= 3600000) {
				return String.valueOf( diff / 60000 ) + "m";
			}
			//less than 1 day
			else if ( diff <= 3600000 * 24 ) {
				return String.valueOf( diff / 3600000) + "h";
			}
			else {
				return String.valueOf( diff / (3600000 * 24)) + "d";
			}
		} catch (ParseException e) {
			return time;
		}
	}
	
}
