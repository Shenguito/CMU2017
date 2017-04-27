package pt.ulisboa.tecnico.meic.cmu.locmess;

public class Wifi extends Location{

    public String SSID;

    public Wifi(String locationName,String SSID){
        super(locationName);
        this.SSID = SSID;
    }

}