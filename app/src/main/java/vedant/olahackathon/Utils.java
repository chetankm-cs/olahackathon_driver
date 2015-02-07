package vedant.olahackathon;

/**
 * Created by vedant on 2/7/15.
 */
public class Utils {
    public static com.google.maps.model.LatLng to(com.google.android.gms.maps.model.LatLng latLng){
        return new com.google.maps.model.LatLng(latLng.latitude, latLng.longitude);
    }
    public static com.google.android.gms.maps.model.LatLng to(com.google.maps.model.LatLng latLng){
        return new com.google.android.gms.maps.model.LatLng(latLng.lat, latLng.lng);
    }
}
