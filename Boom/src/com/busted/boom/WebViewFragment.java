package com.busted.boom;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

public class WebViewFragment extends Fragment {
	
	private static final String TAG = "DiscoverFragment";
	
	public static final String ARGS_URL = "com.busted.boom.ARGS_URL";
	
	private String mUrl;
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
		mUrl = mHostname + getArguments().getString(ARGS_URL);
		
		mBrowseView = (WebView) rootView.findViewById(R.id.browse_web_view);
		mBrowseView.loadUrl(mUrl);
		mBrowseView.getSettings().setJavaScriptEnabled(true);
		
		return rootView;
	}

}
