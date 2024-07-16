AMRIL(24000254),KAGENDRAN(24000582),AMIR(24000387),ADAM(24000263),AKID(24000336)
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
public class Coach {
    private ArrayList<FitnessPlan> plans;
    private JFrame frame;
    private JTextField nameField;
    private JTextField descriptionField;
    private JTextArea displayArea;
    private Connection connection;
    public Coach() {
        plans = new ArrayList<>();
        initializeDatabase();
        initializeGUI();
        loadPlansFromDatabase();
    }
    private void initializeDatabase() {
        try {
            String url = "jdbc:mysql://localhost:3306/fitness_db"; // Replace with your MySQL
            database URL
            String user = "root"; // Replace with your MySQL username
            String password = "password"; // Replace with your MySQL password
            connection = DriverManager.getConnection(url, user, password);
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS plans (name VARCHAR(255),
                    description TEXT)");
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void loadPlansFromDatabase() {
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM plans")) {
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                plans.add(new FitnessPlan(name, description));
            }
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
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO plans
(name, description) VALUES (?, ?)")) {
        statement.setString(1, name);
        statement.setString(2, description);
        statement.executeUpdate();
        plans.add(new FitnessPlan(name, description));
        displayArea.append("Added plan: " + name + "\n");
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
public void removePlan(String name) {
    boolean removed = plans.removeIf(plan -> plan.getName().equals(name));
    if (removed) {
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM plans
                WHERE name = ?")) {
statement.setString(1, name);
        statement.executeUpdate();
        displayArea.append("Removed plan: " + name + "\n");
    } catch (SQLException e) {
        e.printStackTrace();
    }
} else {
        displayArea.append("Plan not found: " + name + "\n");
}
        }
public void printPlans() {
    displayArea.append("Current Plans:\n");
    for (FitnessPlan plan : plans) {
        displayArea.append("Name: " + plan.getName() + ", Description: " + plan.getDescription()
                + "\n");
    }
}
public void findPlan(String name) {
    for (FitnessPlan plan : plans) {
        if (plan.getName().equals(name)) {
            displayArea.append("Found plan: Name: " + plan.getName() + ", Description: " +
                    plan.getDescription() + "\n");
            return;
        }
    }
    displayArea.append("Plan not found: " + name + "\n");
}
public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
        @Override
        public void run() {
            new Coach();
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