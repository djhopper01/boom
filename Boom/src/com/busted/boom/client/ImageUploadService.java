package com.busted.boom.client;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.IntentService;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

public class ImageUploadService extends IntentService {
	private static final String TAG = ApiClientService.class.getName();
	
	public static final String EXTRA_FILEPATH        = "com.busted.boom.EXTRA_FILEPATH";
	public static final String EXTRA_LAT             = "com.busted.boom.EXTRA_LAT";
	public static final String EXTRA_LON             = "com.busted.boom.EXTRA_LON";
	public static final String EXTRA_ORIENTATION     = "com.busted.boom.EXTRA_ORIENTATION";
	public static final String EXTRA_RESULT_RECEIVER = "com.busted.boom.EXTRA_RESULT_RECEIVER";
	
	private Bitmap mBitmap;

	public ImageUploadService() {
		super(TAG);
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		Uri    action = intent.getData();
		Bundle extras = intent.getExtras();

		String filepath = extras.getString(EXTRA_FILEPATH);
		String[] nodes = filepath.split("/");
		String filename = nodes[nodes.length - 1];
		ImageUploadReceiver receiver = extras.getParcelable(EXTRA_RESULT_RECEIVER);
		
		Log.d(TAG, "Filepath to upload: " + filepath);
		mBitmap = BitmapFactory.decodeFile(filepath);
		
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			mBitmap.compress(CompressFormat.JPEG, 75, bos);
			
			byte[] data = bos.toByteArray();

			HttpClient client = new DefaultHttpClient();
			HttpPost request = new HttpPost();
			request.setURI(new URI(action.toString()));
//			request.setURI(new URI("http://requestb.in/muttkqmu"));
			
			ByteArrayBody bab = new ByteArrayBody(data, filename);
			MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
			entity.addPart("file", bab);
			entity.addPart("lat", new StringBody(String.valueOf(extras.getDouble(EXTRA_LAT))));
			entity.addPart("lon", new StringBody(String.valueOf(extras.getDouble(EXTRA_LON))));
			entity.addPart("orientation", new StringBody(extras.getString(EXTRA_ORIENTATION)));
			
			request.setEntity(entity);
			request.setHeader("Accept", "application/json");
			
			if ( request != null ) {
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
		catch (ClientProtocolException e) {
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}

}
