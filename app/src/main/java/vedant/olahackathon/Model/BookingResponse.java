package vedant.olahackathon.Model;

import java.util.ArrayList;

/**
 * Created by vedant on 2/8/15.
 */
public class BookingResponse {
    private Integer error;
    private ArrayList<Passenger> bookings;

    public Integer getError() {
        return error;
    }

    public void setError(Integer error) {
        this.error = error;
    }

    public ArrayList<Passenger> getBookings() {
        return bookings;
    }

    public void setBookings(ArrayList<Passenger> bookings) {
        this.bookings = bookings;
    }
}
