package hotel;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

public class PenthouseRoom extends Room {
    private List<Booking> bookings = new ArrayList<Booking>();

    public Booking book(LocalDate arrival, LocalDate departure) {
        for (Booking booking : bookings) {
            if (booking.overlaps(arrival, departure)) {
                return null;
            }
        }

        Booking booking = new Booking(arrival, departure);
        bookings.add(booking);
        return booking;
    }

    public JSONObject toJSON() {
        JSONObject json = super.toJSON();
        json.put("type", "standard");
        return json;
    }


    public void printWelcomeMessage() {
        System.out.println(
                "Welcome to your penthouse apartment, complete with ensuite, lounge, kitchen and master bedroom.");
    }

}
