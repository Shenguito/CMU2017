package pt.ulisboa.tecnico.meic.cmu.locmess;

public class GPS extends Location{

    public String lon, lat, radius;

    public GPS(String locationName, String lon, String lat, String radius){
        super(locationName);
        this.lon = lon;
        this.lat = lat;
        this.radius = radius;
    }
    public String getLat(){
        return lat;
    }
    public String getLon(){
        return lon;
    }

}
