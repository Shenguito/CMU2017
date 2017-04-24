package ist.meic.cmu.server.storage;

public class GPSLocation extends Location{
	private int radius;
	public GPSLocation(String name, double lontitude, double latitude, int radius){
		super(name, lontitude, latitude);
		this.radius=radius;
	}

	public int getRadius() {
		return radius;
	}
}
