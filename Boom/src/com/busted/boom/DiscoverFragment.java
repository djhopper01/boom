package com.busted.boom;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

public class DiscoverFragment extends Fragment {
	
	private static final String TAG = "DiscoverFragment";
	
	private String mBrowseUrl;
	private String mHostname;
	private WebView mBrowseView;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		Log.d(TAG, "Loaded web view.");
		
		View rootView = inflater.inflate(R.layout.webview_fragment,
				container, false);
		
		mHostname = getResources().getString(R.string.server_url);
		mBrowseUrl = mHostname + getResources().getString(R.string.browse_uri);
		
		mBrowseView = (WebView) rootView.findViewById(R.id.browse_web_view);
		mBrowseView.loadUrl(mBrowseUrl);
		mBrowseView.getSettings().setJavaScriptEnabled(true);
		
//		mBrowseView.getSettings().setLoadWithOverviewMode(true);
//		mBrowseView.getSettings().setUseWideViewPort(true);
		
		return rootView;
	}

}
