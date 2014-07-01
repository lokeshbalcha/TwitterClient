package com.codepath.apps.basictwitter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.codepath.apps.basictwitter.fragments.ComposeFragment;
import com.codepath.apps.basictwitter.fragments.ComposeFragment.onTweetPostedListener;

public class ComposeActivity extends FragmentActivity implements
		onTweetPostedListener {

	ComposeFragment composeFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compose);
		composeFragment = (ComposeFragment) getSupportFragmentManager()
				.findFragmentById(R.id.fragment_compose);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.compose_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	// Communicate to Fragment to post the tweet
	public void onTweetSave(MenuItem mi) {
		composeFragment.postTweet();
	}

	// Listen to the Fragment once the tweet is posted
	@Override
	public void onTweetPosted() {
			Intent timeLineIntent = new Intent(this, TimelineActivity.class);
			setResult(200, timeLineIntent);
			finish();
	}
}
