package vedant.olahackathon;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.PendingResult;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.Unit;

public class DirectionsActivity extends Activity {
    private static final LatLng BANGALORE_LOCATION = new LatLng(12.9539974, 77.6309395);

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private GeoApiContext mGeoApiContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directions);
        setUpMapIfNeeded();
        mGeoApiContext = new GeoApiContext();
        mGeoApiContext.setApiKey(getResources().getString(R.string.google_maps_key));
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
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

        request.setCallback(new PendingResult.Callback<DirectionsRoute[]>() {
            @Override
            public void onResult(DirectionsRoute[] result) {
                PolylineOptions polyline = new PolylineOptions().width(3).color(Color.BLUE);
                for (DirectionsRoute route : result)
                    for (com.google.maps.model.LatLng latLng : route.overviewPolyline.decodePath())
                        polyline.add(Utils.to(latLng));
                mMap.addPolyline(polyline);
            }

            @Override
            public void onFailure(Throwable e) {

            }
        });
    }

    public void newDestinations(com.google.maps.model.LatLng origin,
                         final com.google.maps.model.LatLng[] destinations,
                         final String[] users) {

        if (destinations.length != users.length)
            throw new IllegalArgumentException();

        mMap.clear();

        // for each destination
        for (int i = 0; i < destinations.length; i++) {
            // add a marker
            mMap.addMarker(new MarkerOptions()
                            .position(Utils.to(destinations[i]))
                            .title("Drop " + users[i])
            );

            // add a driving path
            com.google.maps.model.LatLng start = (i > 0 ? destinations[i-1] : origin);
            com.google.maps.model.LatLng end = destinations[i];
            addDrivingDirections(start, end);
        }

    }


}
