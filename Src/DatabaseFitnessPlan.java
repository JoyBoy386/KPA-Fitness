AMRIL(24000254),KAGENDRAN(24000582),AMIR(24000387),ADAM(24000263),AKID(24000336)
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FitnessPlanGUI extends JFrame {
    private JTextField nameField;
    private JTextField ageField;
    private JTextField weightField;
    private JTextField heightField;
    private JTextArea objectivesArea;
    private JTextField startDateField;
    private JTextField endDateField;
    private JButton submitButton;
    private JTextArea outputArea;
    private JButton loadButton;

    public FitnessPlanGUI() {
        createView();

        setTitle("Fitness Plan");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 500);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void createView() {
        JPanel panel = new JPanel();
        getContentPane().add(panel);

        panel.setLayout(new GridLayout(10, 2, 5, 5));

        JLabel nameLabel = new JLabel("Name:");
        panel.add(nameLabel);
        nameField = new JTextField();
        panel.add(nameField);

        JLabel ageLabel = new JLabel("Age:");
        panel.add(ageLabel);
        ageField = new JTextField();
        panel.add(ageField);

        JLabel weightLabel = new JLabel("Weight (kg):");
        panel.add(weightLabel);
        weightField = new JTextField();
        panel.add(weightField);

        JLabel heightLabel = new JLabel("Height (cm):");
        panel.add(heightLabel);
        heightField = new JTextField();
        panel.add(heightField);

        JLabel objectivesLabel = new JLabel("Objectives:");
        panel.add(objectivesLabel);
        objectivesArea = new JTextArea();
        panel.add(new JScrollPane(objectivesArea));

        JLabel startDateLabel = new JLabel("Start Date (dd/MM/yyyy):");
        panel.add(startDateLabel);
        startDateField = new JTextField();
        panel.add(startDateField);

        JLabel endDateLabel = new JLabel("End Date (dd/MM/yyyy):");
        panel.add(endDateLabel);
        endDateField = new JTextField();
        panel.add(endDateField);

        submitButton = new JButton("Submit");
        panel.add(submitButton);

        loadButton = new JButton("Load Plans");
        panel.add(loadButton);

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        panel.add(new JScrollPane(outputArea));

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                submitFitnessPlan();
            }
        });

        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    loadFitnessPlans();
                } catch (SQLException | ParseException ex) {
                    showError("Failed to load plans: " + ex.getMessage());
                }
            }
        });
    }

    private void submitFitnessPlan() {
        try {
            String name = nameField.getText().trim();
            int age = Integer.parseInt(ageField.getText().trim());
            double weight = Double.parseDouble(weightField.getText().trim());
            double height = Double.parseDouble(heightField.getText().trim());
            String[] objectives = objectivesArea.getText().split("\\n");
            String startDate = startDateField.getText().trim();
            String endDate = endDateField.getText().trim();

            if (name.isEmpty() || startDate.isEmpty() || endDate.isEmpty()) {
                showError("Name, start date, and end date are required.");
                return;
            }

            Date start = parseDate(startDate);
            Date end = parseDate(endDate);

            if (start.after(end)) {
                showError("Start date must be before end date.");
                return;
            }

            FitnessPlan plan = new FitnessPlan(name, age, weight, height, objectives, start, end);
            plan.save();
            displayFitnessPlan(plan);
        } catch (NumberFormatException ex) {
            showError("Age, weight, and height must be valid numbers.");
