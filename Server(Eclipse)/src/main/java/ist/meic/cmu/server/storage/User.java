package ist.meic.cmu.server.storage;

public class User {
	private String username;
	private String password;
	private String sessionID;
	private Profile profile;
	private Location location;

	public User(String username, String password) {
		this.username = username;
		this.password = password;
		sessionID="";
		location=null;
	}
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSessionID() {
		return sessionID;
	}
	public void setSessionID(String sessionID) {
		this.sessionID = sessionID;
	}
	public String getUsername() {
		return username;
	}
	public Profile getProfile() {
		return profile;
	}
	public void setProfile(Profile profile) {
		this.profile = profile;
	}
	
}
