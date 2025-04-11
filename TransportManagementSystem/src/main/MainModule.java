
package main;

import dao.*;

import entity.*;
import myexceptions.BookingNotFoundException;

import java.util.*;

public class MainModule {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        TransportManagementService service = new TransportManagementServiceImpl();

        int choice;
        do {
            System.out.println("\n===== Transport Management Menu =====");
            System.out.println("1. Add Vehicle");
            System.out.println("2. Update Vehicle");
            System.out.println("3. Delete Vehicle");
            System.out.println("4. Schedule Trip");
            System.out.println("5. Cancel Trip");
            System.out.println("6. Book Trip");
            System.out.println("7. Cancel Booking");
            System.out.println("8. View Bookings by Passenger");
            System.out.println("9. View Bookings by Trip");
            System.out.println("10. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Model: ");
                    String model = sc.nextLine();
                    System.out.print("Capacity: ");
                    double capacity = sc.nextDouble();
                    sc.nextLine();
                    System.out.print("Type (Truck/Van/Bus): ");
                    String type = sc.nextLine();
                    System.out.print("Status (Available/On Trip/Maintenance): ");
                    String status = sc.nextLine();
                    Vehicle vehicle = new Vehicle(0, model, capacity, type, status);
                    if (service.addVehicle(vehicle)) {
                        System.out.println("Vehicle added.");
                    } else {
                        System.out.println("Failed to add vehicle.");
                    }
                    break;

                case 2:
                    System.out.print("Vehicle ID to update: ");
                    int vid = sc.nextInt(); sc.nextLine();
                    System.out.print("New Model: ");
                    String newModel = sc.nextLine();
                    System.out.print("New Capacity: ");
                    double newCapacity = sc.nextDouble(); sc.nextLine();
                    System.out.print("New Type: ");
                    String newType = sc.nextLine();
                    System.out.print("New Status: ");
                    String newStatus = sc.nextLine();
                    Vehicle updatedVehicle = new Vehicle(vid, newModel, newCapacity, newType, newStatus);
                    if (service.updateVehicle(updatedVehicle)) {
                        System.out.println("Vehicle updated.");
                    } else {
                        System.out.println("Vehicle update failed.");
                    }
                    break;

                case 3:
                    System.out.print("Enter Vehicle ID to delete: ");
                    int delId = sc.nextInt();
                    if (service.deleteVehicle(delId)) {
                        System.out.println("Vehicle deleted.");
                    } else {
                        System.out.println("Vehicle deletion failed.");
                    }
                    break;

                case 4:
                    System.out.print("Vehicle ID: ");
                    int vehicleId = sc.nextInt();
                    System.out.print("Route ID: ");
                    int routeId = sc.nextInt(); sc.nextLine();
                    System.out.print("Departure Date (YYYY-MM-DD HH:MM:SS): ");
                    String dep = sc.nextLine();
                    System.out.print("Arrival Date (YYYY-MM-DD HH:MM:SS): ");
                    String arr = sc.nextLine();
                    if (service.scheduleTrip(vehicleId, routeId, dep, arr)) {
                        System.out.println("Trip scheduled.");
                    } else {
                        System.out.println("Trip scheduling failed.");
                    }
                    break;

                case 5:
                    System.out.print("Trip ID to cancel: ");
                    int tripId = sc.nextInt();
                    if (service.cancelTrip(tripId)) {
                        System.out.println("Trip cancelled.");
                    } else {
                        System.out.println("Trip cancellation failed.");
                    }
                    break;

                case 6:
                    System.out.print("Trip ID: ");
                    int tId = sc.nextInt();
                    System.out.print("Passenger ID: ");
                    int pId = sc.nextInt(); sc.nextLine();
                    System.out.print("Booking Date (YYYY-MM-DD HH:MM:SS): ");
                    String bDate = sc.nextLine();
                    if (service.bookTrip(tId, pId, bDate)) {
                        System.out.println("Trip booked.");
                    } else {
                        System.out.println("Trip booking failed.");
                    }
                    break;

                case 7:
                    System.out.print("Booking ID: ");
                    int bId = sc.nextInt();
                    if (service.cancelBooking(bId)) {
                        System.out.println("Booking cancelled.");
                    } else {
                        System.out.println("Booking cancellation failed.");
                    }
                    break;

                case 8:
                    System.out.print("Passenger ID: ");
                    int pid = sc.nextInt();
				List<Booking> bookingsByPassenger = null;
				bookingsByPassenger = service.getBookingsByPassenger(pid);
                    for (Booking b : bookingsByPassenger) {
                        System.out.println(b);
                    }
                    break;

                case 9:
                    System.out.print("Trip ID: ");
                    int tid = sc.nextInt();
                    List<Booking> bookingsByTrip = service.getBookingsByTrip(tid);
                    for (Booking b : bookingsByTrip) {
                        System.out.println(b);
                    }
                    break;

                case 10:
                    System.out.println("Exiting... Goodbye!");
                    break;

                default:
                    System.out.println("Invalid choice. Try again.");
            }
        } while (choice != 10);

        sc.close();
    }
}
