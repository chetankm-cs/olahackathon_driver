package vedant.olahackathon.Model;

import java.util.ArrayList;

/**
 * Created by vedant on 2/7/15.
 */
public class Booking {

    public final static String TAG = "Booking";

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Position getPickup() {
        return pickup;
    }

    public void setPickup(Position pickup) {
        this.pickup = pickup;
    }

    public ArrayList<Passenger> getPassengers() {
        return passengers;
    }

    public void setPassengers(ArrayList<Passenger> passengers) {
        this.passengers = passengers;
    }

    private Integer id;
    private Position pickup;
    private ArrayList<Passenger> passengers;
}
