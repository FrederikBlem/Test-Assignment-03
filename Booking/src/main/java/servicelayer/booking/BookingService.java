package servicelayer.booking;

import dto.Booking;

import java.util.Collection;

public interface BookingService {
    int createBooking(int customerId, int employeeId, String date, String start, String end) throws BookingServiceException;
    Collection<Booking> getBookingsForCustomer(int customerId);
    Collection<Booking> getBookingsForEmployee(int employeeId);
}
