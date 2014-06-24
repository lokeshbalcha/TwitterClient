package com.codepath.apps.basictwitter;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;
public class ComposeTweetActivity extends Activity {

	EditText etComposeTweet;
	TextView tvUserNamec;
	TextView tvScreenNamec;
	ImageView ivProfileImagec;
	private TwitterClient client;
	TextView tvCharsLeft;
	final int TWEET_SIZE = 140;
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.post_tweet, menu);
        return true;
    }

	
	@Override
	public boolean onPrepareOptionsMenu(final Menu menu) {
	    final MenuItem menuItem = menu.findItem(R.id.CharsLeftTV);
	    tvCharsLeft = (TextView) menuItem.getActionView();
	    return super.onPrepareOptionsMenu(menu);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compose_tweet);
		
		etComposeTweet = (EditText) findViewById(R.id.etComposeTweet);
		
		tvUserNamec = (TextView) findViewById(R.id.tvUsernamec);
		tvUserNamec.setText(getIntent().getStringExtra("userName"));
		
		tvScreenNamec = (TextView) findViewById(R.id.tvScreennamec);
		tvScreenNamec.setText("@" + getIntent().getStringExtra("screenName"));
		
		ivProfileImagec = (ImageView) findViewById(R.id.ivProfileImagec);
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.displayImage(getIntent().getStringExtra("imgUrl"), ivProfileImagec);
		
		
		client = TwitterApplication.getRestClient();
		
		etComposeTweet.addTextChangedListener(new TextWatcher() {
			int len = 0;
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				len = etComposeTweet.getText().toString().length();
				tvCharsLeft.setText(String.valueOf(TWEET_SIZE - len));
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
	}
	
	public void PostTweet(MenuItem mip) {
		if(etComposeTweet.getText().toString() != "") {
			client.postUpdate(new AsyncHttpResponseHandler() {
				@Override
				public void onSuccess(String arg0) {
					setResult(RESULT_OK);
					finish();
				}
				
				@Override
				public void onFailure(Throwable e, String s) {
					Log.d("debug", e.toString());
					Log.d("debug", s.toString());
				}
			}, etComposeTweet.getText().toString());
		}
	}
}