package com.busted.boom;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.busted.boom.client.ApiClientService;
import com.busted.boom.client.ImageUploadService;
import com.busted.boom.model.Picture;
import com.busted.boom.util.SystemUiHider;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore.Files.FileColumns;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class CameraActivity extends Activity {
	
	private static final String TAG = "CameraActivity";
	private Camera mCamera;
    private CameraPreview mPreview;
    private RelativeLayout mPreviewLayout;
    private Button mButton;
    private LocationReader mLocationReader;
    private Context mContext;
    
	private String mHostname;
	private String mCreateImageUrl;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mContext = this;

		setContentView(R.layout.activity_camera);
		
		mButton = (Button) findViewById(R.id.take_picture);
		
		mLocationReader = new LocationReader(this);
		
		mHostname = getResources().getString(R.string.server_url);
		mCreateImageUrl = mHostname + getResources().getString(R.string.create_image_uri);
	}
	
	@Override
	protected void onResume() {
		// Create an instance of Camera
        mCamera = getCameraInstance();

        // Create our Preview view and set it as the content of our activity.
        mPreview = new CameraPreview(this, mCamera);
        mPreviewLayout = (RelativeLayout) findViewById(R.id.camera_preview);
        mPreviewLayout.addView(mPreview);
        
        mButton.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // get an image from the camera
                    mCamera.takePicture(mShutterCallback, null, mPictureCallback);
                }
            }
        );
        
        // Listen for location updates
        mLocationReader.startUpdates();
        
		super.onResume();
	}

	@Override
	protected void onPause() {
	    if (mCamera != null) {
	    	mCamera.stopPreview();
	    	mPreviewLayout.removeView(mPreview);
	    	mCamera.release();
	    	mCamera = null;
	    }
	    
	    // Stop listening for location updates
	    mLocationReader.stopUpdates();
	    
	    super.onPause();
	}
	
	public static Camera getCameraInstance(){
	    Camera c = null;
	    try {
	        c = Camera.open(); // attempt to get a Camera instance
	    }
	    catch (Exception e){
	        // Camera is not available (in use or does not exist)
	    }
	    return c; // returns null if camera is unavailable
	}
	
	private PictureCallback mPictureCallback = new PictureCallback() {

	    @Override
	    public void onPictureTaken(byte[] data, Camera camera) {

	        File pictureFile = getOutputMediaFile(FileColumns.MEDIA_TYPE_IMAGE);
	        if (pictureFile == null){
	            Log.d(TAG, "Error creating media file, check storage permissions.");
	            return;
	        }

	        try {
	            FileOutputStream fos = new FileOutputStream(pictureFile);
	            fos.write(data);
	            fos.close();
	        } catch (FileNotFoundException e) {
	            Log.d(TAG, "File not found: " + e.getMessage());
	        } catch (IOException e) {
	            Log.d(TAG, "Error accessing file: " + e.getMessage());
	        }
	        
	        Log.d(TAG, "Lat: " + mLocationReader.getLocation().getLatitude() + ", Lon: " + mLocationReader.getLocation().getLongitude());
	        
	        // Send the picture to the server
	        CameraActivity activity = (CameraActivity) mContext;
	        if ( activity != null ) {
	        	Intent intent = new Intent(activity, ImageUploadService.class);
	        	
	        	String authToken = getResources().getString(R.string.auth_token);
	        	
	        	// This is just for testing. We'll need to use the user's auth token here.
	        	intent.setData(Uri.parse(mCreateImageUrl + "?auth_token=" + authToken));
	        	intent.putExtra(ImageUploadService.EXTRA_FILEPATH, pictureFile.getAbsolutePath());
	        	
	        	Log.d(TAG, "Starting the service");
	        	
	        	activity.startService(intent);
	        }
	        
	        mCamera.startPreview();
	    }
	};
	
	private ShutterCallback mShutterCallback = new ShutterCallback() {

		@Override
		public void onShutter() {
			ConfirmationDialog dialog = new ConfirmationDialog(mContext);
			dialog.show();
		}
		
	};
	
	/** Create a File for saving an image or video */
	private static File getOutputMediaFile(int type){
	    // To be safe, you should check that the SDCard is mounted
	    // using Environment.getExternalStorageState() before doing this.

	    File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
	              Environment.DIRECTORY_PICTURES), "MyCameraApp");
	    // This location works best if you want the created images to be shared
	    // between applications and persist after your app has been uninstalled.

	    // Create the storage directory if it does not exist
	    if (! mediaStorageDir.exists()){
	        if (! mediaStorageDir.mkdirs()){
	            Log.d("MyCameraApp", "failed to create directory");
	            return null;
	        }
	    }

	    // Create a media file name
	    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
	    File mediaFile;
	    if (type == FileColumns.MEDIA_TYPE_IMAGE){
	        mediaFile = new File(mediaStorageDir.getPath() + File.separator +
	        "IMG_"+ timeStamp + ".jpg");
	    } else {
	        return null;
	    }

	    return mediaFile;
	}

}
