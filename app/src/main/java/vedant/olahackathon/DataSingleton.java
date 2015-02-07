package vedant.olahackathon;

import com.google.maps.model.LatLng;

import java.util.ArrayList;

import vedant.olahackathon.Model.Booking;
import vedant.olahackathon.Model.Passenger;

/**
 * Created by vedant on 2/7/15.
 */
public class DataSingleton {
    private static DataSingleton ourInstance = new DataSingleton();

    private LatLng mOrigin;
    private LatLng[] mDestinations;
    private ArrayList<Passenger> mPeople;


    public void enterData(String object) {
        Booking booking =(Booking) POJOToJSON.fromJson(object,Booking.class);

    }


    public LatLng getmOrigin() {
        return mOrigin;
    }

    public void setmOrigin(LatLng mOrigin) {
        this.mOrigin = mOrigin;
    }

    public LatLng[] getmDestinations() {
        return mDestinations;
    }

    public void setmDestinations(LatLng[] mDestinations) {
        this.mDestinations = mDestinations;
    }

    public ArrayList<Passenger> getmPeople() {
        return mPeople;
    }

    public void setmPeople(ArrayList<Passenger> mPeople) {
        this.mPeople = mPeople;
    }
}
