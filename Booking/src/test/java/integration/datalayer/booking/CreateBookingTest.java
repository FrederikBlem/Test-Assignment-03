package integration.datalayer.booking;
import datalayer.booking.BookingStorage;
import datalayer.booking.BookingStorageImpl;
import dto.BookingCreation;
import main.SqlConverter;
import org.junit.jupiter.api.*;

import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import java.sql.SQLException;
import java.util.Date;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("integration")
public class CreateBookingTest {
    private BookingStorage bookingStorage;

    @BeforeAll
    void setUp() throws SQLException {
        bookingStorage = new BookingStorageImpl("jdbc:mysql://localhost:3307/BookingTest", "root", "testuser1234");
    }

    @Test
    public void mustSaveBookingInDatabaseWhenCallingCreateBooking () throws SQLException {
        bookingStorage.createBooking(new BookingCreation(1,1, SqlConverter.convertToSQLDate(new Date()), "09:00", "19:00"));
        var booking = bookingStorage.getBookingsForCustomer(1);

        assertTrue(
                booking.stream().anyMatch(x ->
                    x.getCustomerId() == 1 &&
                    x.getEmployeeId() == 1 &&
                    x.getDate() == "2021-10-29" &&
                    x.getStart() == "09:00:00" &&
                    x.getEnd() == "19:00:00"
                    ));
    }

    @Test
    public void mustReturnLatestId() throws SQLException {
        // Arrange
        // Act
        var id1 = bookingStorage.createBooking(new BookingCreation(1,1,SqlConverter.convertToSQLDate(new Date()), "14:00", "15:00"));
        var id2 = bookingStorage.createBooking(new BookingCreation(1,1,SqlConverter.convertToSQLDate(new Date()), "14:00", "15:00"));

        // Assert
        assertEquals(1, id2 - id1);
    }
}