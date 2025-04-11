package test;

import dao.TransportManagementService;
import dao.TransportManagementServiceImpl;
import entity.Booking;
import entity.Vehicle;
import myexceptions.BookingNotFoundException;

import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TransportManagementServiceTest {

    static TransportManagementService service;

    @BeforeAll
    public static void init() {
        service = new TransportManagementServiceImpl();
    }

    @Test
  
    void testAddVehicle() {
        Vehicle vehicle = new Vehicle(6, "Tata Ace", 2.5, "Truck", "Available");
        boolean result = service.addVehicle(vehicle);
        assertTrue(result);
    }
//
    @Test
  
    void testUpdateVehicle() {
        Vehicle vehicle = new Vehicle(6, "Tata Updated", 3.0, "Truck", "Maintenance");
        boolean result = service.updateVehicle(vehicle);
        assertTrue(result);
    }
//




    @Test  
    void testBookTrip() {
        boolean result = service.bookTrip(2, 2, "2025-04-15 10:00:00");
        assertTrue(result);
    }



    @Test

    void testGetBookingsByPassenger() {
        List<Booking> bookings = null;
		bookings = service.getBookingsByPassenger(1);
        assertNotNull(bookings);
    }

    @Test

    void testGetBookingsByTrip() {
        List<Booking> bookings = service.getBookingsByTrip(2);
        assertNotNull(bookings);
    
    }
}
