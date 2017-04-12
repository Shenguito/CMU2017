package pt.ulisboa.tecnico.cmov.rest;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.simple.JSONObject;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class MainActivity extends AppCompatActivity {


    private static final String LOCALHOST="http://192.168.1.2:8080/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);

    }
    @Override
    protected void onStart() {
        super.onStart();
        new HttpRequestTask().execute();
    }
    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_refresh) {
            new HttpRequestTask().execute();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }

    private class HttpRequestTask extends AsyncTask<Void, Void, JSONObject> {
        @Override
        protected JSONObject doInBackground(Void... params) {
            try {
                final String url = LOCALHOST+"create";
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
                JSONObject json=new JSONObject();
                json.put("username", "arlindo");
                json.put("password", "abc123");
                JSONObject response = restTemplate.postForObject(url, json, JSONObject.class);
                return response;
            } catch (Exception e) {
                Log.e("MainActivityTest", e.getMessage(), e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(JSONObject value) {
            TextView greetingIdText = (TextView) findViewById(R.id.id_value);
            TextView greetingContentText = (TextView) findViewById(R.id.content_value);
            //JSONObject j=new String2JSON().getJSON(value.toString());
            greetingIdText.setText("username");
            greetingContentText.setText(value.get("username").toString());
        }
    }
}
