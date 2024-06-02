Coach.java//Author:Akid,Amril,Kagen
import java.util.ArrayList; //import arraylist
import java.util.List; // import list

// Akid, Kagen work
public class Coach { //define class needs
    public String name;
    public String phoneNumber;
    public List<Client> clients;
    public double hourlyRate;

    public Coach(String name, String phoneNumber, double hourlyRate) { //retrieve class element
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.clients = new ArrayList<>();
        this.hourlyRate = hourlyRate;
    }

    public void addClient(Client client) {
        // Implement the logic for adding a client
    }

    public void removeClient(Client client) {
        // Implement the logic for removing a client
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public List<Client> getClients() {
        return clients;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }
}

Main.java
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Akid, Amril
public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.print("Please enter the client name: ");
        String clientName = scanner.nextLine();
        // Create some clients
        Client client1 = new Client("Kamal", "010-4578893", null);
        Client client2 = new Client("Ahmad", "019-896785", null);

        // Create some coaches
        Coach coach1 = new Coach("Adam", "010-3966074", 50);
        Coach coach2 = new Coach("Ali", "011-5426488", 75);

        // Add clients to coaches
        coach1.addClient(client1);
        coach2.addClient(client2);

        // Create a list of coaches
        List<Coach> coaches = new ArrayList<>();
        coaches.add(coach1);
        coaches.add(coach2);

        // Print the coaches
        for (Coach coach : coaches) {
            System.out.println("Coach: " + coach.getName());
            System.out.println("Phone Number: " + coach.getPhoneNumber());
            System.out.println("Hourly Rate: " + coach.getHourlyRate());
            System.out.println("Clients:" );
            for (Client client : coach.getClients()) {
                System.out.println("Name: " + client.getName());
                System.out.println("Phone Number: " + client.getPhoneNumber());
                System.out.println("Coach: " + client.getCoach().getName());
                System.out.println();
            }
            System.out.println();
        }

        // Find a coach by name
        Coach foundCoach = null;
        for (Coach coach : coaches) {
            if (coach.getName().equals("Ali")) {
                foundCoach = coach;
                break;
            }
        }
        if (foundCoach != null) {
            System.out.println("Found coach: " + foundCoach.getName());
        } else {
            System.out.println("Coach not found");
        }
    }
}

