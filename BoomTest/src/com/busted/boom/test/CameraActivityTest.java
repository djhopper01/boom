package com.busted.boom.test;

import com.busted.boom.CameraActivity;
import com.busted.boom.R;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ActivityUnitTestCase;
import android.test.AndroidTestCase;
import android.widget.RelativeLayout;

public class CameraActivityTest extends ActivityInstrumentationTestCase2<CameraActivity> {
	
	private CameraActivity mActivity;
	
	public CameraActivityTest() {
		super(CameraActivity.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
		
		mActivity = (CameraActivity) getActivity();
	}
	
	public void testCameraPreviewViewIsAdded() {
		RelativeLayout mPreviewLayout = (RelativeLayout) mActivity.findViewById(R.id.camera_preview);
		assertNotNull(mPreviewLayout.findViewById(69));
	}

}
