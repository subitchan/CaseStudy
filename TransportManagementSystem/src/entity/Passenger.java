package entity;

public class Passenger {
    private int passengerId;
    private String name;
    private String email;
    private String phone;

    public Passenger() {}

    public Passenger(int passengerId, String name, String email, String phone) {
        this.passengerId = passengerId;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    // Getters and Setters
}
