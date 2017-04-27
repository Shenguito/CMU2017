package ist.meic.cmu.server.storage;

import org.json.simple.JSONObject;

public class Post {
	private String username;
	private double latitude;
	private double longitude;
	private int radius;
	private String type;
	public Post(String username, double latitude, double longitude, int radius, String type) {
		super();
		this.username = username;
		this.latitude = latitude;
		this.longitude = longitude;
		this.radius = radius;
		this.type = type;
	}
	public int getRadius() {
		return radius;
	}
	public void setRadius(int radius) {
		this.radius = radius;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUsername() {
		return username;
	}
	public double getLatitude() {
		return latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	
}
