import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Date;

public class HotelReservationSystem {

    // User class to store user information
    static class User {
        private String username;
        private String password;

        public User(String username, String password) {
            this.username = username;
            this.password = password;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }
    }

    // Room class to store room details
    static class Room {
        private int roomNumber;
        private String roomType;
        private boolean isAvailable;
        private double price;

        public Room(int roomNumber, String roomType, boolean isAvailable, double price) {
            this.roomNumber = roomNumber;
            this.roomType = roomType;
            this.isAvailable = isAvailable;
            this.price = price;
        }

        public int getRoomNumber() {
            return roomNumber;
        }

        public String getRoomType() {
            return roomType;
        }

        public boolean isAvailable() {
            return isAvailable;
        }

        public void setAvailable(boolean available) {
            isAvailable = available;
        }

        public double getPrice() {
            return price;
        }

        @Override
        public String toString() {
            return "Room Number: " + roomNumber + ", Type: " + roomType + ", Available: " + isAvailable + ", Price: $" + price;
        }
    }

    // Reservation class to store reservation details
    static class Reservation {
        private String customerName;
        private Room room;
        private Date checkInDate;
        private Date checkOutDate;

        public Reservation(String customerName, Room room, Date checkInDate, Date checkOutDate) {
            this.customerName = customerName;
            this.room = room;
            this.checkInDate = checkInDate;
            this.checkOutDate = checkOutDate;
        }

        public String getCustomerName() {
            return customerName;
        }

        public Room getRoom() {
            return room;
        }

        public Date getCheckInDate() {
            return checkInDate;
        }

        public Date getCheckOutDate() {
            return checkOutDate;
        }

        @Override
        public String toString() {
            return "Reservation for " + customerName + ": Room " + room.getRoomNumber() + " from " + checkInDate + " to " + checkOutDate;
        }
    }

    // Hotel class to handle rooms, users, and reservations
    static class Hotel {
        private List<Room> rooms;
        private List<Reservation> reservations;
        private Map<String, User> users;
        private User loggedInUser;

        public Hotel() {
            rooms = new ArrayList<>();
            reservations = new ArrayList<>();
            users = new HashMap<>();
            loggedInUser = null;
        }

        public void addRoom(Room room) {
            rooms.add(room);
        }

        public boolean registerUser(String username, String password) {
            if (users.containsKey(username)) {
                System.out.println("Username already exists.");
                return false;
            }
            User user = new User(username, password);
            users.put(username, user);
            System.out.println("User registered successfully.");
            return true;
        }

        public boolean signIn(String username, String password) {
            User user = users.get(username);
            if (user != null && user.getPassword().equals(password)) {
                loggedInUser = user;
                System.out.println("Welcome, " + username);
                return true;
            }
            System.out.println("Invalid username or password.");
            return false;
        }

        public void signOut() {
            loggedInUser = null;
            System.out.println("You have been signed out.");
        }

        public boolean isLoggedIn() {
            return loggedInUser != null;
        }

        public void listAvailableRooms() {
            for (Room room : rooms) {
                if (room.isAvailable()) {
                    System.out.println(room);
                }
            }
        }

        public void makeReservation(int roomNumber, Date checkInDate, Date checkOutDate) {
            if (loggedInUser == null) {
                System.out.println("Please sign in to make a reservation.");
                return;
            }
            Room room = findRoom(roomNumber);
            if (room != null && room.isAvailable()) {
                Reservation reservation = new Reservation(loggedInUser.getUsername(), room, checkInDate, checkOutDate);
                reservations.add(reservation);
                room.setAvailable(false);
                System.out.println("Reservation made successfully: " + reservation);
            } else {
                System.out.println("Room not available.");
            }
        }

        public void viewReservations() {
            if (loggedInUser == null) {
                System.out.println("Please sign in to view your reservations.");
                return;
            }
            for (Reservation reservation : reservations) {
                if (reservation.getCustomerName().equals(loggedInUser.getUsername())) {
                    System.out.println(reservation);
                }
            }
        }

        private Room findRoom(int roomNumber) {
            for (Room room : rooms) {
                if (room.getRoomNumber() == roomNumber) {
                    return room;
                }
            }
            return null;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Hotel hotel = new Hotel();

        // Adding some rooms to the hotel
        hotel.addRoom(new Room(101, "Single", true, 100));
        hotel.addRoom(new Room(102, "Double", true, 150));
        hotel.addRoom(new Room(103, "Suite", true, 250));

        int choice;

        do {
            System.out.println("\nHotel Reservation System");
            System.out.println("1. Register");
            System.out.println("2. Sign in");
            System.out.println("3. List available rooms");
            System.out.println("4. Make a reservation");
            System.out.println("5. View my reservations");
            System.out.println("6. Sign out");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter username: ");
                    scanner.nextLine();  // Consume newline
                    String regUsername = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String regPassword = scanner.nextLine();
                    hotel.registerUser(regUsername, regPassword);
                    break;

                case 2:
                    System.out.print("Enter username: ");
                    scanner.nextLine();  // Consume newline
                    String username = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String password = scanner.nextLine();
                    hotel.signIn(username, password);
                    break;

                case 3:
                    hotel.listAvailableRooms();
                    break;

                case 4:
                    if (!hotel.isLoggedIn()) {
                        System.out.println("Please sign in first.");
                        break;
                    }
                    System.out.print("Enter room number: ");
                    int roomNumber = scanner.nextInt();
                    System.out.print("Enter check-in date (yyyy-mm-dd): ");
                    scanner.nextLine(); // Consume newline
                    String checkInDateString = scanner.nextLine();
                    System.out.print("Enter check-out date (yyyy-mm-dd): ");
                    String checkOutDateString = scanner.nextLine();

                    // Simple date parsing (could be improved with a proper Date format)
                    Date checkInDate = new Date(checkInDateString);
                    Date checkOutDate = new Date(checkOutDateString);
                    hotel.makeReservation(roomNumber, checkInDate, checkOutDate);
                    break;

                case 5:
                    hotel.viewReservations();
                    break;

                case 6:
                    hotel.signOut();
                    break;

                case 7:
                    System.out.println("Exiting system.");
                    break;

                default:
                    System.out.println("Invalid choice.");
            }

        } while (choice != 7);

        scanner.close();
    }
}
