AMRIL(24000254),KAGENDRAN(24000582),AMIR(24000387),ADAM(24000263),AKID(24000336)
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

public class FitnessPlansGUI {
    private ArrayList<FitnessPlan> plans;
    private JFrame frame;
    private JTextField nameField;
    private JTextField descriptionField;
    private JTextArea displayArea;
    private Connection connection;

    public FitnessPlansGUI() {
        plans = new ArrayList<>();
        initializeDatabase();
        initializeGUI();
    }

    private void initializeDatabase() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:fitness_plans.db");
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS plans (name TEXT, description TEXT)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void initializeGUI() {
        frame = new JFrame("Fitness Plans Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2));

        panel.add(new JLabel("Plan Name:"));
        nameField = new JTextField();
        panel.add(nameField);

        panel.add(new JLabel("Description:"));
        descriptionField = new JTextField();
        panel.add(descriptionField);

        JButton addButton = new JButton("Add Plan");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addPlan(nameField.getText(), descriptionField.getText());
            }
        });
        panel.add(addButton);

        JButton removeButton = new JButton("Remove Plan");
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removePlan(nameField.getText());
            }
        });
        panel.add(removeButton);

        JButton findButton = new JButton("Find Plan");
        findButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                findPlan(nameField.getText());
            }
        });
        panel.add(findButton);

        JButton printButton = new JButton("Print Plans");
        printButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                printPlans();
            }
        });
        panel.add(printButton);

        displayArea = new JTextArea();
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);

        frame.getContentPane().add(panel, BorderLayout.NORTH);
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    public void addPlan(String name, String description) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO plans (name, description) VALUES (?, ?)");
            statement.setString(1, name);
            statement.setString(2, description);
            statement.executeUpdate();
            displayArea.append("Added plan: " + name + "\n");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removePlan(String name) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM plans WHERE name = ?");
            statement.setString(1, name);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                displayArea.append("Removed plan: " + name + "\n");
            } else {
                displayArea.append("Plan not found: " + name + "\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void printPlans() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM plans");
            displayArea.append("Current Plans:\n");
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                displayArea.append("Name: " + name + ", Description: " + description + "\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void findPlan(String name) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM plans WHERE name = ?");
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String description = resultSet.getString("description");
                displayArea.append("Found plan: Name: " + name + ", Description: " + description + "\n");
            } else {
                displayArea.append("Plan not found: " + name + "\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FitnessPlansGUI();
            }
        });
    }
}

class FitnessPlan {
    private String name;
    private String description;

    public FitnessPlan(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}