package com.busted.boom;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

public class LocationReader implements LocationListener {
	
	// Request location updates every X seconds
	public static final long  MIN_TIME_BETWEEN_UPDATES     = 120;
	public static final float MIN_DISTANCE_BETWEEN_UPDATES = 20; 
	
	private static final String TAG = "LocationReader";
	private LocationManager mLocationManager;
	private Location mLocation;
	private Context mContext;
	
	public LocationReader(Context context) {
		mContext = context;
		mLocationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
	}
	
	public Location getLocation() {
		return mLocation;
	}
	
	public void startUpdates() {
		mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BETWEEN_UPDATES * 1000, MIN_DISTANCE_BETWEEN_UPDATES, this);
	}
	
	public void stopUpdates() {
		mLocationManager.removeUpdates(this);
	}

	@Override
	public void onLocationChanged(Location location) {
		mLocation = location;
	}

	@Override
	public void onProviderDisabled(String arg0) {

	}

	@Override
	public void onProviderEnabled(String arg0) {

	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {

	}

}
