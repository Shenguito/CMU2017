package pt.ulisboa.tecnico.meic.cmu.locmess.location;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import pt.ulisboa.tecnico.meic.cmu.locmess.R;

/**
 * Created by Akilino on 06/04/2017.
 */

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.ViewHolder> {

    private List<Location> locationList;
    private Context context;
    private ItemClickCallback itemClickCallback;

    public interface ItemClickCallback{
        void onItemClick(int p);
        void onSecondaryIconClick(int p);
    }

    public void setItemClickCallback(final ItemClickCallback itemClickCallback){
        this.itemClickCallback = itemClickCallback;
    }

    public LocationAdapter(Context context, List<Location> locations){
        this.context = context;
        this.locationList = locations;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View locationView = inflater.inflate(R.layout.location_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(locationView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(LocationAdapter.ViewHolder holder, int position) {
        Location location = locationList.get(position);

        TextView locationNameTextView = holder.textViewLocationName;
        TextView locationExtraInfoTextView = holder.textViewLocationExtraInfo;

        if(location instanceof Wifi){
            Wifi wifiLocation = (Wifi) location;
            locationNameTextView.setText(wifiLocation.locationName);
            locationExtraInfoTextView.setText(wifiLocation.SSID);
        }else if(location instanceof GPS){
            GPS gpsLocation = (GPS) location;
            locationNameTextView.setText(gpsLocation.locationName);
            locationExtraInfoTextView.setText(gpsLocation.lat + " " + gpsLocation.lon + " " + gpsLocation.radius + "m");
        }

    }

    @Override
    public int getItemCount() {
        return locationList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView textViewLocationExtraInfo, textViewLocationName;
        public Button deleteButton, viewButton;
        //public ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewLocationExtraInfo = (TextView) itemView.findViewById(R.id.textViewLocationExtraInfo);
            textViewLocationName = (TextView) itemView.findViewById(R.id.textViewLocationName);
            deleteButton = (Button) itemView.findViewById(R.id.deleteButtonLocation);
            viewButton = (Button) itemView.findViewById(R.id.viewButtonLocation);
            deleteButton.setOnClickListener(this);
            viewButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.deleteButtonLocation){
                itemClickCallback.onSecondaryIconClick(getAdapterPosition());
            }else if(v.getId() == R.id.viewButtonLocation){
                itemClickCallback.onItemClick(getAdapterPosition());
                Toast.makeText(context, "View Button clicked", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
