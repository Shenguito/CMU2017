package ist.meic.cmu.server.storage;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import ist.meic.cmu.server.tool.StringParser;

public class Storage {

	private ArrayList<GPSLocation> location;
	private ArrayList<User> user;
	private ArrayList<Post> post;
	private ArrayList<Property> property;
	public Storage(){
		location=new ArrayList<GPSLocation>();
		user=new ArrayList<User>();
		post=new ArrayList<Post>();
		property=new ArrayList<Property>();
		User u=new User("a", "a");
		u.addProperty(new Property("a", "a"));
		user.add(u);
		location.add(new GPSLocation("location", 65.96669666666666, -18.5333, 100));
		DateFormat format = new SimpleDateFormat("dd/MM/yy hh:mm");
		try {
			Date start = format.parse("01/01/01 01:01");
			Date end=format.parse("11/11/11 11:11");
			property.add(new Property("a", "a"));
			property.add(new Property("b", "b"));
			post.add(new Post("title", "default message", "a", start, end, "location", "Blacklist", "Centralized", property));
		} catch (ParseException e) {
			System.out.println("defaul value not added!!!");
		}
	}
	public boolean createUser(String username, String password){
		if(user.size()!=0)
		for (User tmpUser : user) {
			if(tmpUser.getUsername().equals(username)){
				return false;
			}
		}
		user.add(new User(username, password));
		return true;
		
	}
	public boolean verifyUser(String username, String password){
		if(user.size()!=0)
			for (User tmpUser : user) {
				if(tmpUser.getUsername().equals(username)&&tmpUser.getPassword().equals(password)){
					return true;
				}
			}
		return false;
	}
	public String giveSessionID(String username, String password){
		if(user.size()!=0)
			for (User tmpUser : user) {
				if(tmpUser.getUsername().equals(username)&&tmpUser.getPassword().equals(password)&&tmpUser.getSessionID().equals("")){
					String random = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
					for(int i=0;i<4;i++){
						random+=""+(new Random().nextInt(9 - 0 + 1) + 0);
					}
					tmpUser.setSessionID(random);
					return random;
				}
			}
		return "";
	}
	public boolean logout(String username, String session){
		if(user.size()!=0)
			for (User tmpUser : user) {
				if(tmpUser.getUsername().equals(username)&&tmpUser.getSessionID().equals(session)){
					tmpUser.setSessionID("");
					return true;
				}
			}
		return false;
	}
	public boolean addLocation(String name, String lat, String lon, String radius){

		if(location.size()!=0)
			for (GPSLocation tmplocation : location) {
				if(tmplocation.getName().equals(name)||
						(tmplocation.getLatitude()==Double.parseDouble(lat)&&
						tmplocation.getLongitude()==Double.parseDouble(lon))){
					return false;
				}
			}
		System.out.println("Location added:\n"+"name: "+name+
			"latitude: "+Double.parseDouble(lat)+
				"longitude: "+Double.parseDouble(lon)+
			"radius: "+Integer.parseInt(radius));
		location.add(new GPSLocation(name, Double.parseDouble(lat), Double.parseDouble(lon), Integer.parseInt(radius)));
		return true;
	}
	public boolean removeLocation(String name, String lat, String lon) {
		if(location.size()!=0)
			for (int i=0;i<location.size();i++) {
				if(location.get(i).getName().equals(name)&&
						location.get(i).getLatitude()==Double.parseDouble(lat)&&
								location.get(i).getLongitude()==Double.parseDouble(lon)){
					location.remove(i);
					return true;
				}
			}
		return false;
	}
	public JSONObject getLocation(String lat, String lon) {
		JSONObject json=new JSONObject();
		if(location.size()!=0)
			for (int i=0;i<location.size();i++) {
				JSONArray jsonLocation = new JSONArray();
				
					jsonLocation.add(location.get(i).getName());
					jsonLocation.add(location.get(i).getLatitude());
					jsonLocation.add(location.get(i).getLongitude());
					jsonLocation.add(location.get(i).getRadius());
					json.put("location"+i, jsonLocation);
			}
			System.out.println("json output: "+json.toJSONString());
		return json;
	}
	public boolean userLocation(String username, String lat, String lon, String sessionid) {
		
		return true;
	}
	public boolean sendPost(String title, String message, String username, String startDate, String endDate, String location, String filter, String mode, String property) {
		if(post.size()!=0)
			for(Post p:post){
				if(p.getTitle().equals(title)
						&& p.getMessage().equals(message)
						&& p.getUsername().equals(username)
						&& p.getLocation().equals(location)){
					return false;
				}
			}
		DateFormat format = new SimpleDateFormat("dd/MM/yy hh:mm");
		Date start=null;
		Date end=null;
		try {
			start = format.parse(startDate);
			end = format.parse(endDate);
		} catch (ParseException e) {
			System.out.println("Date format error!!!");
			return false;
		}
		ArrayList<Property> prof=new ArrayList<>();
		if(property!=null){
			String[] propertyParser=StringParser.getProperty(property);
			for(int i=0;i<propertyParser.length;i+=2){
				prof.add(new Property(propertyParser[i], propertyParser[i+1]));
			}
			post.add(new Post(title, message, username, start, end, location, filter, mode, prof));
			System.out.println("post added :"+post.get(post.size()-1).getTitle());
			return true;
		}else{
			post.add(new Post(title, message, username, start, end, location, filter, mode, prof));
			System.out.println("post added :"+post.get(post.size()-1).getTitle());
			return true;
		}
		
	}
	public JSONObject getPost(String username, String latitude, String longitude) {
		JSONObject json=new JSONObject();
		ArrayList<Property> property=new ArrayList<Property>();
		if(user.size()!=0&&post.size()!=0&&location.size()!=0){
			for (int i=0;i<user.size();i++) {
				if(user.get(i).getUsername().equals(username)){
					property=user.get(i).getProperty();
					System.out.println("User exists!");
					break;
				}
			}
			System.out.println("user has location: "+latitude+":"+longitude);
			for(int i=0,w=0;i<location.size();i++){
				if(Algorithm.distFrom(location.get(i).getLatitude(), location.get(i).getLongitude(), Double.parseDouble(latitude), Double.parseDouble(longitude))<=
								location.get(i).getRadius()){
					for(int j=0 ; j<post.size();j++){
						JSONArray arrayPost=new JSONArray();
						if(post.get(j).getLocation().equals(location.get(i).getName())||post.get(j).getUsername().equals(username)){
							if(post.get(j).getFilter().equals("Whitelist")&&property.size()!=0){
								for(Property p:post.get(j).getProperty()){
									if(property.contains(p)){
										arrayPost.add(post.get(j).getTitle());
										arrayPost.add(post.get(j).getMessage());
										arrayPost.add(post.get(j).getUsername());
										arrayPost.add(post.get(j).getStartDate());
										arrayPost.add(post.get(j).getEndDate());
										arrayPost.add(post.get(j).getLocation());
										arrayPost.add(post.get(j).getFilter());
										arrayPost.add(post.get(j).getMode());
										arrayPost.add(post.get(j).getPropertyString());
										
										json.put("post"+w, arrayPost);
										w++;
										System.out.println("whitepost: "+post.get(j).getTitle()+" : "+post.get(j).getMode()+" : "+post.get(j).getFilter()+" : "+post.get(j).getPropertyString());
										break;
									}
								}
							}else if(post.get(j).getFilter().equals("Blacklist")){
								if(property.size()!=0){
									for(Property p:post.get(j).getProperty()){
										if(!property.contains(p)){
											arrayPost.add(post.get(j).getTitle());
											arrayPost.add(post.get(j).getMessage());
											arrayPost.add(post.get(j).getUsername());
											arrayPost.add(post.get(j).getStartDate());
											arrayPost.add(post.get(j).getEndDate());
											arrayPost.add(post.get(j).getLocation());
											arrayPost.add(post.get(j).getFilter());
											arrayPost.add(post.get(j).getMode());
											arrayPost.add(post.get(j).getPropertyString());
											
											json.put("post"+w, arrayPost);
											w++;
											System.out.println("blackpost: "+post.get(j).getTitle()+" : "+post.get(j).getMode()+" : "+post.get(j).getFilter()+" : "+post.get(j).getPropertyString());
											break;
										}
									}
								}else{
									arrayPost.add(post.get(j).getTitle());
									arrayPost.add(post.get(j).getMessage());
									arrayPost.add(post.get(j).getUsername());
									arrayPost.add(post.get(j).getStartDate());
									arrayPost.add(post.get(j).getEndDate());
									arrayPost.add(post.get(j).getLocation());
									arrayPost.add(post.get(j).getFilter());
									arrayPost.add(post.get(j).getMode());
									arrayPost.add(post.get(j).getPropertyString());
									
									json.put("post"+w, arrayPost);
									w++;
									System.out.println("blackpost: "+post.get(j).getTitle()+" : "+post.get(j).getMode()+" : "+post.get(j).getFilter()+" : "+post.get(j).getPropertyString());
								}
							}
						}
					}
				}
			}
		}
		System.out.println("Post result: "+json.toJSONString());
		return json;
	}
	public boolean deletePost(String title, String message, String username, String location) {
		if(post.size()!=0)
			for(Post p:post){
				if(p.getTitle().equals(title)
						&& p.getMessage().equals(message)
						&& p.getUsername().equals(username)
						&& p.getLocation().equals(location)){
					post.remove(p);
					System.out.println("Remove successful");
					return true;
				}
			}
		return false;
	}
	
	public boolean addProperty(String username, String sessionid, String key, String value) {
		if(user.size()!=0)
			for (User tmpUser : user) {
				if(tmpUser.getUsername().equals(username)&&tmpUser.getSessionID().equals(sessionid)){
					for(Property property:tmpUser.getProperty()){
						if(property.getKey().equals(key)&&property.getValue().equals(value))
						return false;
					}
					System.out.println("add: "+key+" : "+value);
					Property p=new Property(key, value);
					tmpUser.addProperty(p);
					if(!property.contains(p.getKey().equals(key)))
						property.add(p);
					return true;
				}
			}
		return false;
	}
	public JSONObject getProperty(String username, String sessionid) {
		JSONObject json=new JSONObject();
		if(user.size()!=0)
			for (User tmpUser : user) {
				if(tmpUser.getUsername().equals(username)&&tmpUser.getSessionID().equals(sessionid)){
					for(int i=0; i<tmpUser.getProperty().size();i++){
						JSONArray jsonProperty=new JSONArray();
						jsonProperty.add(tmpUser.getProperty().get(i).getKey());
						jsonProperty.add(tmpUser.getProperty().get(i).getValue());
						json.put("property"+i, jsonProperty);
					}
				}
			}
		return json;
	}
	public JSONObject getAllProperties() {
		JSONObject json=new JSONObject();
		if(property.size()!=0)
			for(int i=0; i<property.size();i++){
				JSONArray jsonProperty=new JSONArray();
				jsonProperty.add(property.get(i).getKey());
				jsonProperty.add(property.get(i).getValue());
				json.put("property"+i, jsonProperty);
			}
		return json;
	}
	public boolean removeProperty(String username, String sessionid, String key, String value) {
		if(user.size()!=0)
			for (User tmpUser : user) {
				if(tmpUser.getUsername().equals(username)&&tmpUser.getSessionID().equals(sessionid)){
					for(Property property:tmpUser.getProperty()){
						if(property.getKey().equals(key)&&property.getValue().equals(value)){
							tmpUser.removeProperty(key, value);
							System.out.println("remove: "+key+" : "+value);
							return true;
						}
					}
				}
			}
		return false;
	}
	public boolean editProperty(String username, String sessionid, String oldkey, String oldvalue, String newkey,
			String newvalue) {
		JSONObject json=new JSONObject();
		if(user.size()!=0)
			for (User tmpUser : user) {
				if(tmpUser.getUsername().equals(username)&&tmpUser.getSessionID().equals(sessionid)){
					for(int i=0; i<tmpUser.getProperty().size();i++){
						if(tmpUser.getProperty().get(i).getKey().equals(oldkey)&&tmpUser.getProperty().get(i).getValue().equals(oldvalue)){
							tmpUser.getProperty().get(i).setKey(newkey);
							tmpUser.getProperty().get(i).setValue(newvalue);
							return true;
						}
					}
				}
			}
		return false;
	}
}
