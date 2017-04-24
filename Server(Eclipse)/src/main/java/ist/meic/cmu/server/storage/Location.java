package ist.meic.cmu.server.storage;

public class Location {
	
	private String name;
	private double longitude;
	private double latitude;

	public Location(String name, double lontitude, double latitude){
		this.name=name;
		this.longitude=lontitude;
		this.latitude=latitude;
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
	
}
