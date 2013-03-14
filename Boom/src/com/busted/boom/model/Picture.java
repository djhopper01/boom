package com.busted.boom.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Picture {
	
	@Expose
	private int id;
	public void setId(int id) {
		this.id = id;
	}
	
	@Expose
	private double latitude;
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	
	@Expose
	private double longitude;
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
	@Expose
	private String href;
	public void setHref(String href) {
		this.href = href;
	}
}
