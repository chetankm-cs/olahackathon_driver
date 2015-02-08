package vedant.olahackathon;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.PendingResult;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.Unit;

import java.util.ArrayList;

import vedant.olahackathon.Model.Booking;
import vedant.olahackathon.Model.Passenger;

public class DirectionsActivity extends Activity {
    private static final LatLng BANGALORE_LOCATION = new LatLng(12.9539974, 77.6309395);

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private GeoApiContext mGeoApiContext;
    private Booking mBooking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directions);
        mGeoApiContext = new GeoApiContext();
        mGeoApiContext.setApiKey("AIzaSyDmnplvDn5Lpz88ovLLH3rYvXyOgR53O_I");
        setUpMapIfNeeded();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {

        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);

        LatLng startLatLng = BANGALORE_LOCATION;
        CameraPosition cameraPosition;
        cameraPosition = new CameraPosition.Builder().target(
                startLatLng)
                .zoom(14)
                .build();
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


        String data = getIntent().getStringExtra(Booking.TAG);
        mBooking = (Booking) POJOToJSON.fromJson(data, Booking.class);
        newDestinations();
    }

    /**
     * just draws a blue line to indicate driving directions
     * @param origin
     * @param destination
     */
    private void addDrivingDirections(com.google.maps.model.LatLng origin, com.google.maps.model.LatLng destination){
        DirectionsApiRequest request = DirectionsApi.newRequest(mGeoApiContext)
                .origin(origin)
                .destination(destination)
                .units(Unit.METRIC);

        if (request != null) {
            request.setCallback(new PendingResult.Callback<DirectionsRoute[]>() {
                @Override
                public void onResult(DirectionsRoute[] result) {
                    final PolylineOptions polyline = new PolylineOptions().width(10).color(Color.BLUE);
                    for (DirectionsRoute route : result)
                        for (com.google.maps.model.LatLng latLng : route.overviewPolyline.decodePath())
                            polyline.add(Utils.to(latLng));
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            mMap.addPolyline(polyline);
                        }
                    });
                }

                @Override
                public void onFailure(Throwable e) {
                    e.printStackTrace();
                }
            });
        }
    }

    public void newDestinations() {

        LatLngBounds.Builder builder = LatLngBounds.builder();
        com.google.maps.model.LatLng origin = mBooking.getPickup().getLatLng();
        builder.include(Utils.to(origin));

        mMap.clear();

        // for each destination
        ArrayList<Passenger> passengers = mBooking.getPassengers();
        for (int i = 0; i < passengers.size(); i++) {
            Passenger passenger = passengers.get(i);

            LatLng gmsLatLng = passenger.getGeo().getGmsLatLng();
            builder.include(gmsLatLng);
            // add a marker
            mMap.addMarker(new MarkerOptions()
                            .position(gmsLatLng)
                            .title("Drop " + passenger.getName())
            );

            // add a driving path
            com.google.maps.model.LatLng start = (i > 0 ? passengers.get(i-1).getGeo().getLatLng() : origin);
            com.google.maps.model.LatLng end = passengers.get(i).getGeo().getLatLng();
            try {
                addDrivingDirections(start, end);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        final LatLngBounds bounds = builder.build();
        mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, dpToPixels(24)));
            }
        });

    }

    public int dpToPixels(int dp) {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }


}
