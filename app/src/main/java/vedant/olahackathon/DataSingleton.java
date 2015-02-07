package vedant.olahackathon;

import com.google.maps.model.LatLng;

/**
 * Created by vedant on 2/7/15.
 */
public class DataSingleton {
    private static DataSingleton ourInstance = new DataSingleton();

    private LatLng mOrigin;
    private LatLng[] mDestinations;
    private Person[] mPeople;

    public LatLng getOrigin() {
        return mOrigin;
    }

    public LatLng[] getDestinations() {
        return mDestinations;
    }

    public Person[] getPeople() {
        return mPeople;
    }

    public void enterData(LatLng origin, LatLng[] destinations, Person[] people) {
        mOrigin = origin;
        mDestinations = destinations;
        mPeople = people;
    }

    public static DataSingleton getInstance() {
        return ourInstance;
    }

    private DataSingleton() {
    }

    public class Person {
        public final String name;
        public final String phone;

        Person(String name, String phone) {
            this.name = name;
            this.phone = phone;
        }
    }

}
