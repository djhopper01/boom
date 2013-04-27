package com.busted.boom;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

public class ProfileFragment extends Fragment {
	
	private static final String TAG = "ProfileFragment";
	
	private String mProfileUrl;
	private String mHostname;
	private WebView mProfileView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		Log.d(TAG, "Loaded web view.");
		
		View rootView = inflater.inflate(R.layout.webview_fragment,
				container, false);
		
		mHostname = getResources().getString(R.string.server_url);
		mProfileUrl = mHostname + getResources().getString(R.string.profile_uri);
		
		mProfileView = (WebView) rootView.findViewById(R.id.browse_web_view);
		mProfileView.loadUrl(mProfileUrl);
		mProfileView.getSettings().setJavaScriptEnabled(true);
		
//		mBrowseView.getSettings().setLoadWithOverviewMode(true);
//		mBrowseView.getSettings().setUseWideViewPort(true);
		
		return rootView;
	}
}
