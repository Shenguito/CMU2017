package pt.ulisboa.tecnico.meic.cmu.locmess;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Akilino on 06/04/2017.
 */

public class LocationActivity extends AppCompatActivity implements LocationAdapter.ItemClickCallback {

    private Toolbar toolbar;
    private ArrayList<Location> locations;
    private LocationAdapter adapter;
    public ArrayList<Location> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.locations);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerViewLocation);
        locations = populateView();
        adapter = new LocationAdapter(this, locations);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setItemClickCallback(this);
    }

    public ArrayList<Location> populateView() {

        list = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            GPS location = new GPS("Location GPS" + i, "12.4" + i, "125.3" + i,"20");
            list.add(location);
        }

        for (int i = 0; i < 5; i++) {
            Wifi location = new Wifi("Location Wifi " + i, "Mac Address" + i);
            list.add(location);
        }
        return list;
    }

    @Override
    public void onItemClick(int p) {
        Toast.makeText(this, "Clicked on primary position " + p, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSecondaryIconClick(int p) {
        Toast.makeText(this, "Clicked on secondary position " + p, Toast.LENGTH_SHORT).show();
        openDialog(p);
    }

    public void removeLocation(int position){
        //TODO
        locations.remove(position);
        adapter.notifyItemRemoved(position);
    }

    public void openDialog(final int position) {
        LayoutInflater li = LayoutInflater.from(LocationActivity.this);
        final View promptsView = li.inflate(R.layout.dialog_warning_delete_location, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                LocationActivity.this);

        alertDialogBuilder.setView(promptsView);

        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                removeLocation(position);
                            }
                        })
                .setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        AlertDialog alertDialog = alertDialogBuilder.create();

        alertDialog.show();
    }

}
