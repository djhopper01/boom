package com.busted.boom.client;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import android.app.IntentService;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

public class ApiClientService extends IntentService {
	private static final String TAG = ApiClientService.class.getName();
	
	public static final int GET    = 1;
	public static final int POST   = 2;
	public static final int PUT    = 3;
	public static final int DELETE = 4;
	
	public static final String EXTRA_HTTP_VERB       = "com.busted.boom.EXTRA_HTTP_VERB";
	public static final String EXTRA_JSON            = "com.busted.boom.EXTRA_JSON";
	public static final String EXTRA_RESULT_RECEIVER = "com.busted.boom.EXTRA_RESULT_RECEIVER";
	
	public static final String GSON_RESULT           = "com.busted.boom.GSON_RESULT";
	
	public ApiClientService() {
		super(TAG);
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		Uri    action = intent.getData();
		Bundle extras = intent.getExtras();
		
		int verb = extras.getInt(EXTRA_HTTP_VERB, GET);
		String json = extras.getString(EXTRA_JSON);
		ApiClientReceiver receiver = extras.getParcelable(EXTRA_RESULT_RECEIVER);
		
		Log.d(TAG, "Making an API call...");
		
		try {
			HttpRequestBase request = null;
			
			switch (verb) {
			case GET:
				request = new HttpGet();
				break;
			case POST:
				request = new HttpPost();
				HttpPost postRequest = (HttpPost) request;
				postRequest.setHeader("Content-Type", "application/json");
				postRequest.setHeader("Accept", "application/json");
				postRequest.setEntity(new StringEntity(json));
				break;
			case PUT:
				request = new HttpPut();
				break;
			case DELETE:
				request = new HttpDelete();
				break;
			}
			
			if ( request != null ) {
				request.setURI(new URI(action.toString()));
				
				HttpClient client = new DefaultHttpClient();
				
				Log.d(TAG, "Sending request: " + action.toString());
				
				HttpResponse response = client.execute(request);
				
				HttpEntity responseEntity = response.getEntity();
				StatusLine responseStatus = response.getStatusLine();
				int statusCode = responseStatus != null ? responseStatus.getStatusCode() : 0;
				
				if ( responseEntity != null ) {
					Log.d(TAG, "Received a response from the server: " + statusCode);
				}
			}
		}
		catch ( URISyntaxException e ) {
			e.printStackTrace();
		}
		catch ( UnsupportedEncodingException e ) {
			e.printStackTrace();
		}
		catch ( ClientProtocolException e ) {
			e.printStackTrace();
		}
		catch ( IOException e ) {
			e.printStackTrace();
		}
	}

}
