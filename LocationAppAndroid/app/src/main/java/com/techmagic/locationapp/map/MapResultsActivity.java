package com.techmagic.locationapp.map;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.activeandroid.Model;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.techmagic.locationapp.R;
import com.techmagic.locationapp.data.DataHelper;
import com.techmagic.locationapp.data.model.LocationData;

import java.util.List;

import butterknife.ButterKnife;


public class MapResultsActivity extends ActionBarActivity {

    private static final int COUNT_LOCATIONS = 1000;
    private static final int ZOOM_LEVEL = 15;
    private MapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
    }

    @Override
    protected void onResume() {
        super.onResume();
        showData();
    }

    private void showData() {
        List<LocationData> locations = DataHelper.getInstance(getApplicationContext()).getLastLocations(COUNT_LOCATIONS);
        if (locations == null || !(locations.size() > 0)) {
            Toast.makeText(this, "No locations to display", Toast.LENGTH_SHORT).show();
            return;
        }
        LocationData locationToZoom = locations.get(0);
        mapFragment.getMap().moveCamera(CameraUpdateFactory
                .newLatLngZoom(new LatLng(locationToZoom.getLatitude(), locationToZoom.getLongitude()), ZOOM_LEVEL));
        for (LocationData d : locations) {
            MarkerOptions mo = new MarkerOptions();
            mo.position(new LatLng(d.getLatitude(), d.getLongitude()));
            mapFragment.getMap().addMarker(mo);
        }
    }

}
