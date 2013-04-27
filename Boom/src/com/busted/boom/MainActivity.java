package com.busted.boom;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;

public class MainActivity extends Activity {
	
	private Button mButton;
	private WebView mBrowseView;
	private String mHostname;
	private String mBrowseUrl;
	private Context mContext;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mContext = this;
		
		mHostname = getResources().getString(R.string.server_url);
		mBrowseUrl = mHostname + getResources().getString(R.string.browse_uri);

		setContentView(R.layout.activity_main);
		
		mButton = (Button) findViewById(R.id.take_picture);
		mButton.setOnClickListener(mTakePictureListener);
		
		mBrowseView = (WebView) findViewById(R.id.browse_web_view);
		mBrowseView.loadUrl(mBrowseUrl);
		mBrowseView.getSettings().setJavaScriptEnabled(true);
//		mBrowseView.getSettings().setLoadWithOverviewMode(true);
//		mBrowseView.getSettings().setUseWideViewPort(true);

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
				mBrowseView.reload();
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
	
}
