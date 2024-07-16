AMRIL(24000254),KAGENDRAN(24000582),AMIR(24000387),ADAM(24000263),AKID(24000336)
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
public class Client {
    private String name;
    private String phoneNumber;
    private String fitnessPlan;
    private String fitnessProgress;
    private Coach coach;
    public Client(String name, String phoneNumber, String fitnessPlan, String fitnessProgress,
                  Coach coach) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.fitnessPlan = fitnessPlan;
        this.fitnessProgress = fitnessProgress;
        this.coach = coach;
    }
    public String getName() {
        return name;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public String getFitnessPlan() {
        return fitnessPlan;
    }
    public String getFitnessProgress() {
        return fitnessProgress;
    }
    public Coach getCoach() {
        return coach;
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Fitness App");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 400);
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            JTextField clientNameField = new JTextField(15);
            JTextField clientPhoneField = new JTextField(15);
            JTextField fitnessPlanField = new JTextField(15);
            JTextField fitnessProgressField = new JTextField(15);
            JTextField coachNameField = new JTextField(15);
            JTextField coachPhoneField = new JTextField(15);
            JButton submitButton = new JButton("Submit");
            JTextArea outputArea = new JTextArea(50, 30);
            outputArea.setEditable(false);
            submitButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String clientName = clientNameField.getText();
                    String clientPhone = clientPhoneField.getText();
                    String fitnessPlan = fitnessPlanField.getText();
                    String fitnessProgress = fitnessProgressField.getText();
                    String coachName = coachNameField.getText();
                    String coachPhone = coachPhoneField.getText();
                    Coach coach = new Coach(coachName, coachPhone);
                    Client client = new Client(clientName, clientPhone, fitnessPlan, fitnessProgress,
                            coach);
                    outputArea.setText("Client Name: " + client.getName() + "\n"
                            + "Client Phone: " + client.getPhoneNumber() + "\n"
                            + "Fitness Plan: " + client.getFitnessPlan() + "\n"
                            + "Fitness Progress: " + client.getFitnessProgress() + "\n"
                            + "Coach Name: " + client.getCoach().getName() + "\n"
                            + "Coach Phone: " + client.getCoach().getPhoneNumber());
// Insert data into the database
                    insertData(client);
                }
            });
            panel.add(new JLabel("Client Name:"));
            panel.add(clientNameField);
            panel.add(new JLabel("Client Phone:"));
            panel.add(clientPhoneField);
            panel.add(new JLabel("Fitness Plan:"));
            panel.add(fitnessPlanField);
            panel.add(new JLabel("Fitness Progress:"));
            panel.add(fitnessProgressField);
            panel.add(new JLabel("Coach Name:"));
            panel.add(coachNameField);
            panel.add(new JLabel("Coach Phone:"));
            panel.add(coachPhoneField);
            panel.add(submitButton);
            panel.add(new JScrollPane(outputArea));
            frame.getContentPane().add(panel);
            frame.setVisible(true);
        });
    }
    private static void insertData(Client client) {
        String url = "jdbc:mysql://localhost:3306/fitness_app";
        String user = "root";
        String password = "password"; // Change this to your MySQL password
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
// Insert coach data
            String insertCoachSQL = "INSERT INTO coaches (name, phone) VALUES (?, ?)";
            try (PreparedStatement coachStatement =
                         connection.prepareStatement(insertCoachSQL,
                                 PreparedStatement.RETURN_GENERATED_KEYS)) {
                coachStatement.setString(1, client.getCoach().getName());
                coachStatement.setString(2, client.getCoach().getPhoneNumber());
                coachStatement.executeUpdate();
// Get generated coach ID
                try (var generatedKeys = coachStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int coachId = generatedKeys.getInt(1);
// Insert client data with coach ID
                        String insertClientSQL = "INSERT INTO clients (name, phone, fitness_plan,
                        fitness_progress, coach_id) VALUES (?, ?, ?, ?, ?)";
                        try (PreparedStatement clientStatement =
                                     connection.prepareStatement(insertClientSQL)) {
                            clientStatement.setString(1, client.getName());
                            clientStatement.setString(2, client.getPhoneNumber());
                            clientStatement.setString(3, client.getFitnessPlan());
                            clientStatement.setString(4, client.getFitnessProgress());
                            clientStatement.setInt(5, coachId);
                            clientStatement.executeUpdate();
                        }
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}