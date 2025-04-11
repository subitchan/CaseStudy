
package dao;



import dao.TransportManagementService;
import entity.*;
import myexceptions.BookingNotFoundException;
import util.DBConnUtil;
import java.sql.*;
import java.util.*;

public class TransportManagementServiceImpl implements TransportManagementService {

    public boolean addVehicle(Vehicle vehicle) {
        String sql = "INSERT INTO Vehicles (Model, Capacity, Type, Status) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnUtil.getDbConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, vehicle.getModel());
            ps.setDouble(2, vehicle.getCapacity());
            ps.setString(3, vehicle.getType());
            ps.setString(4, vehicle.getStatus());
            ps.executeUpdate();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateVehicle(Vehicle vehicle) {
        String sql = "UPDATE Vehicles SET Model=?, Capacity=?, Type=?, Status=? WHERE VehicleID=?";
        try (Connection conn = DBConnUtil.getDbConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, vehicle.getModel());
            ps.setDouble(2, vehicle.getCapacity());
            ps.setString(3, vehicle.getType());
            ps.setString(4, vehicle.getStatus());
            ps.setInt(5, vehicle.getVehicleId());
            ps.executeUpdate();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteVehicle(int vehicleId) {
        String sql = "DELETE FROM Vehicles WHERE VehicleID=?";
        try (Connection conn = DBConnUtil.getDbConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, vehicleId);
            ps.executeUpdate();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean scheduleTrip(int vehicleId, int routeId, String departureDate, String arrivalDate) {
        String checkVehicleSql = "SELECT Status FROM Vehicles WHERE VehicleID = ?";
        String insertTripSql = "INSERT INTO Trips (VehicleID, RouteID, DepartureDate, ArrivalDate, Status, TripType, MaxPassengers) VALUES (?, ?, ?, ?, 'Scheduled', 'Passenger', 50)";
        String updateVehicleSql = "UPDATE Vehicles SET Status = 'On Trip' WHERE VehicleID = ?";

        try (Connection conn = DBConnUtil.getDbConnection()) {
            // 1. Check if vehicle is available
            try (PreparedStatement checkStmt = conn.prepareStatement(checkVehicleSql)) {
                checkStmt.setInt(1, vehicleId);
                ResultSet rs = checkStmt.executeQuery();
                
                
                rs.next();
                String status = rs.getString("Status");
                if (!status.equalsIgnoreCase("Available")) {
                    System.out.println("Vehicle is not available. Current status: " + status);
                    return false;
                }
            }

            // 2. Insert trip
            try (PreparedStatement insertStmt = conn.prepareStatement(insertTripSql)) {
                insertStmt.setInt(1, vehicleId);
                insertStmt.setInt(2, routeId);
                insertStmt.setString(3, departureDate);
                insertStmt.setString(4, arrivalDate);
                int inserted = insertStmt.executeUpdate();

                if (inserted > 0) {
                    // 3. Update vehicle status to "On Trip"
                    try (PreparedStatement updateStmt = conn.prepareStatement(updateVehicleSql)) {
                        updateStmt.setInt(1, vehicleId);
                        updateStmt.executeUpdate();
                    }
                    return true;
                } else {
                    System.out.println("Trip scheduling failed.");
                    return false;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean cancelTrip(int tripId) {
        String sql = "UPDATE Trips SET Status='Cancelled' WHERE TripID=?";
        try (Connection conn = DBConnUtil.getDbConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, tripId);
            ps.executeUpdate();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean bookTrip(int tripId, int passengerId, String bookingDate) {
        String sql = "INSERT INTO Bookings (TripID, PassengerID, BookingDate, Status) VALUES (?, ?, ?, 'Confirmed')";
        try (Connection conn = DBConnUtil.getDbConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, tripId);
            ps.setInt(2, passengerId);
            ps.setString(3, bookingDate);
            ps.executeUpdate();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean cancelBooking(int bookingId) {
        String sql = "UPDATE Bookings SET Status='Cancelled' WHERE BookingID=?";
        try (Connection conn = DBConnUtil.getDbConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, bookingId);
            ps.executeUpdate();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Booking> getBookingsByPassenger(int passengerId)  {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT * FROM Bookings WHERE PassengerID=?";
        try (Connection conn = DBConnUtil.getDbConnection();
		     PreparedStatement ps = conn.prepareStatement(sql)) {
		    ps.setInt(1, passengerId);
		    ResultSet rs = ps.executeQuery();
		    while (rs.next()) {
		        Booking b = new Booking(
		            rs.getInt("BookingID"),
		            rs.getInt("TripID"),
		            rs.getInt("PassengerID"),
		            rs.getString("BookingDate"),
		            rs.getString("Status")
		        );
		        bookings.add(b);
		    }
		} catch (SQLException e) {
			e.printStackTrace();
			
		    
		}
        if (bookings.isEmpty()) {
            throw new BookingNotFoundException("No bookings found for passenger ID: " + passengerId);
        }
        

        return bookings;
    }

    public List<Booking> getBookingsByTrip(int tripId) {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT * FROM Bookings WHERE TripID=?";
        try (Connection conn = DBConnUtil.getDbConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, tripId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Booking b = new Booking(
                    rs.getInt("BookingID"),
                    rs.getInt("TripID"),
                    rs.getInt("PassengerID"),
                    rs.getString("BookingDate"),
                    rs.getString("Status")
                );
                bookings.add(b);
            }
        } catch (SQLException e) {
        	e.printStackTrace();
		
            
        }
        if (bookings.isEmpty()) {
            throw new BookingNotFoundException("No bookings found for trip ID: " + tripId);
        }
        return bookings;
    }
}
