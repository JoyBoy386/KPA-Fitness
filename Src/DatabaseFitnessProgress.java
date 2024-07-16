AMRIL(24000254),KAGENDRAN(24000582),AMIR(24000387),ADAM(24000263),AKID(24000336)
        CREATE TABLE IF NOT EXISTS progress (
        date TEXT NOT NULL,
        score TEXT NOT NULL
        );


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class FitnessProgressGUI {
    private JFrame frame;
    private JTextField dateField;
    private JTextField scoreField;
    private DefaultTableModel tableModel;
    private Connection connection;

    public FitnessProgressGUI() {
        initializeDatabase();

        frame = new JFrame("Fitness Progress Tracker");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(3, 2));
        inputPanel.add(new JLabel("Date (YYYY-MM-DD):"));
        dateField = new JTextField();
        inputPanel.add(dateField);

        inputPanel.add(new JLabel("Score:"));
        scoreField = new JTextField();
        inputPanel.add(scoreField);

        JButton submitButton = new JButton("Add Entry");
        inputPanel.add(submitButton);
        inputPanel.add(new JLabel(""));

        frame.add(inputPanel, BorderLayout.NORTH);

        String[] columnNames = {"Date", "Score"};
        tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);

        loadDataFromDatabase();

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String date = dateField.getText();
                String score = scoreField.getText();

                if (isValidInput(date, score)) {
                    addDataToDatabase(date, score);
                    tableModel.addRow(new Object[]{date, score});
                    dateField.setText("");
                    scoreField.setText("");
                } else {
                    JOptionPane.showMessageDialog(frame, "Please enter a valid date and score.", "Input Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        frame.pack();
        frame.setVisible(true);
    }

    private void initializeDatabase() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:fitness_progress.db");

            String createTableSQL = "CREATE TABLE IF NOT EXISTS progress (" +
                    "date TEXT NOT NULL, " +
                    "score TEXT NOT NULL)";
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate(createTableSQL);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void addDataToDatabase(String date, String score) {
        try {
            String insertSQL = "INSERT INTO progress (date, score) VALUES (?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
                preparedStatement.setString(1, date);
                preparedStatement.setString(2, score);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadDataFromDatabase() {
        try {
            String selectSQL = "SELECT * FROM progress";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(selectSQL)) {

                while (resultSet.next()) {
                    String date = resultSet.getString("date");
                    String score = resultSet.getString("score");
                    tableModel.addRow(new Object[]{date, score});
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean isValidInput(String date, String score) {
        return date.matches("\\d{4}-\\d{2}-\\d{2}") && score.matches("\\d+");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(FitnessProgressGUI::new);
    }
}