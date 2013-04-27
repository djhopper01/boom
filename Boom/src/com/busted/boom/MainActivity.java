package com.busted.boom;

import java.util.Locale;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends FragmentActivity {
	
	private static final String TAG = "MainActivity";
	
	private Button mButton;
	private Context mContext;
	
	SectionsPagerAdapter mSectionsPagerAdapter;
	ViewPager mViewPager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mContext = this;

		setContentView(R.layout.activity_main);
		
		mButton = (Button) findViewById(R.id.take_picture);
		mButton.setOnClickListener(mTakePictureListener);
		
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());
		
		Log.d(TAG, "Setting up view pager.");

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.action_bar, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch ( item.getItemId() ) {
			case R.id.refresh:
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	private OnClickListener mTakePictureListener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			Intent intent = new Intent(mContext, CameraActivity.class);
			mContext.startActivity(intent);
		}
		
	};
	
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			Log.d(TAG, "Position: " + position);
			
			Fragment fragment = null;
			Bundle bundle = new Bundle();
			switch (position) {
			case 0:
				fragment = new WebViewFragment();
				bundle.putString(WebViewFragment.ARGS_URL, getString(R.string.browse_uri));
				break;
			case 1:
				fragment = new WebViewFragment();
				bundle.putString(WebViewFragment.ARGS_URL, getString(R.string.profile_uri));
				break;
			}
			
			fragment.setArguments(bundle);
			return fragment;
		}

		@Override
		public int getCount() {
			return 2;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.discover_section_title).toUpperCase(l);
			case 1:
				return getString(R.string.profile_section_title).toUpperCase(l);
			}
			return null;
		}
	}
	
}
