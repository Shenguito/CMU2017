package ist.meic.cmu.server;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.omg.CORBA.Object;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class userInfo {

    private String name;
    private String age;
    private String password;
    private ArrayList<Interest> interests = new ArrayList<>();
    private Gson gson = new Gson();
    private SecretKey secretKey;


    public userInfo(String password, String name) {
        this.name = name;
        this.password = password;
        byte[] encoded = (password + name).getBytes();
        secretKey = new SecretKeySpec(encoded, "HmacMD5");
    }

    public String getName() {
        return name;
    }

    public String getPassword()
    {
        return password;
    }
    
    public void addInterest(Interest interest){
    	interests.add(interest);
    }

    public String getProfile()
    {
        HashMap userData = new HashMap();
        userData.put("name", name);
        userData.put("locations", "Actual Location");
        userData.put("interest", "Interested Theme");

        return gson.toJson(userData);
    }

    public boolean verifyMAC(String message, byte[] macReceived) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac mac = Mac.getInstance(this.secretKey.getAlgorithm());
        mac.init(this.secretKey);
        if (Arrays.equals(mac.doFinal(message.getBytes()), macReceived))
            return true;
        else
            return false;
    }

}