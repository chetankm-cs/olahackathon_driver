package vedant.olahackathon.Model;

import com.google.maps.model.LatLng;

/**
 * Created by vedant on 2/7/15.
 */
public class Position {
    private double latitude;
    private double longitude;

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public LatLng getLatLng() {
        return new LatLng(latitude, longitude);
    }

    public com.google.android.gms.maps.model.LatLng getGmsLatLng() {
        return new com.google.android.gms.maps.model.LatLng(latitude, longitude);
    }
}
