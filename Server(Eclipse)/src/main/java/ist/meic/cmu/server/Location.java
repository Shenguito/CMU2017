package ist.meic.cmu.server;

public class Location {
	
	private String name;
	private double longitude;
	private double latitude;
	private int radius;

	public Location(String name, double lontitude, double latitude, int radius){
		this.name=name;
		this.longitude=lontitude;
		this.latitude=latitude;
		this.radius=radius;
	}

	public String getName() {
		return name;
	}

	public double getLongitude() {
		return longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public int getRadius() {
		return radius;
	}
	
}
