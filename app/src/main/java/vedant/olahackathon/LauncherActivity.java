package vedant.olahackathon;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.google.maps.model.LatLng;


public class LauncherActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
    }

    public void mapButtonClicked(View v) {
        Intent mapIntent = new Intent(this, DirectionsActivity.class);
        startActivity(mapIntent);
    }

    public void directionsButtonClicked(View v) {
        LatLng destination = DataSingleton.getInstance().getDestinations()[0];
        Uri gmmIntentUri = Uri.parse("google.navigation:q=" + destination.lat + "," + destination.lng);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }
}
