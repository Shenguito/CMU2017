package ist.meic.cmu.server.storage;

import java.util.ArrayList;

public class User {
	private String username;
	private String password;
	private String sessionID;
	private ArrayList<Profile> profile=new ArrayList<Profile>();

	public User(String username, String password) {
		this.username = username;
		this.password = password;
		sessionID="";
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
	public ArrayList<Profile> getProfile() {
		return profile;
	}
	public void addProfile(Profile profile) {
		this.profile.add(profile);
	}
	public void removeProfile(String key, String value) {
		Profile realProfile=null;
		for(Profile profile: this.profile){
			if(profile.getKey().equals(key)&&profile.getValue().equals(value)){
				realProfile=profile;
			}
		}
		this.profile.remove(realProfile);
	}
	
}
