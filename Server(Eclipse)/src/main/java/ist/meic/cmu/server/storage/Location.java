package ist.meic.cmu.server.storage;

public class Location {
	
	private String name;
	private double longitude;
	private double latitude;

	public Location(String name, double latitude, double longitude){
		this.name=name;
		this.latitude=latitude;
		this.longitude=longitude;
		
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
