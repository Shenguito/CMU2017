package ist.meic.cmu.server.storage;

import java.util.ArrayList;
import java.util.Date;

public class Post {
	private String title;
	private String message;
	private Date startDate;
	private Date endDate;
	private String username;
	private String location;
	private String filder;
	private String mode;
	private ArrayList<Profile> profile=new ArrayList<Profile>();
	public Post(String title, String message, String username, Date startDate, Date endDate, String location, String filder, String mode, ArrayList<Profile> profile) {
		this.title=title;
		this.message=message;
		this.username = username;
		this.startDate=startDate;
		this.endDate=endDate;
		this.location=location;
		this.filder=filder;
		this.mode=mode;
		this.profile = profile;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String name) {
		this.title = name;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getFilder() {
		return filder;
	}
	public void setFilder(String filder) {
		this.filder = filder;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public ArrayList<Profile> getProfile() {
		return profile;
	}
	public String getProfileString(){
		String result="";
		for(Profile p:profile){
			result+=p.getKey()+","+p.getValue()+"-";
		}
		return result;
	}
	public boolean addProfile(Profile profile) {
		if(this.profile.size()!=0&&this.profile.contains(profile)){
			return false;
		}
		this.profile.add(profile);
		return true;
	}
	public boolean removeProfile(Profile profile) {
		if(this.profile.size()!=0&&this.profile.contains(profile)){
			this.profile.remove(profile);
			return true;
		}
		return false;
	}

}
