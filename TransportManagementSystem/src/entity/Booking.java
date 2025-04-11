package entity;

public class Booking {
    private int bookingId;
    private int tripId;
    private int passengerId;
    private String bookingDate;
    private String Status;
    public int getBookingId() {
		return bookingId;
	}

	public void setBookingId(int bookingId) {
		this.bookingId = bookingId;
	}

	public int getTripId() {
		return tripId;
	}

	public void setTripId(int tripId) {
		this.tripId = tripId;
	}

	public int getPassengerId() {
		return passengerId;
	}

	public void setPassengerId(int passengerId) {
		this.passengerId = passengerId;
	}

	public String getBookingDate() {
		return bookingDate;
	}

	public void setBookingDate(String bookingDate) {
		this.bookingDate = bookingDate;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

	public Booking(int bookingId, int tripId, int passengerId, String bookingDate, String status) {
		super();
		this.bookingId = bookingId;
		this.tripId = tripId;
		this.passengerId = passengerId;
		this.bookingDate = bookingDate;
		Status = status;
	}
	
	@Override
	public String toString() {
	    return "Booking{" +
	            "bookingId=" + bookingId +
	            ", tripId=" + tripId +
	            ", passengerId=" + passengerId +
	            ", bookingDate='" + bookingDate + '\'' +
	            ", status='" + Status + '\'' +
	            '}';
	}





}
