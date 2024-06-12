import java.util.*;
import java.text.SimpleDateFormat;

public class FitnessPlan {
    // Author: Amril Najwan
    // Student Id: 24000254

    // Private fields
    private String name;
    private int age;
    private double weight;
    private double height;
    private double bmi;
    private String[] objectives;
    private Date startDate;
    private Date endDate;

    // Constructor
    public FitnessPlan(String name, int age, double weight, double height, String[] objectives, Date startDate, Date endDate) {
        this.name = name;
        this.age = age;
        this.weight = weight;
        this.height = height;
        this.objectives = objectives;
        this.startDate = startDate;
        this.endDate = endDate;
        this.bmi = calculateBMI(weight, height);
    }

    // Getter methods
    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public double getWeight() {
        return weight;
    }

    public double getHeight() {
        return height;
    }

    public double getBMI() {
        return bmi;
    }

    public String[] getObjectives() {
        return objectives;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    // Method to calculate BMI
    public double calculateBMI(double weight, double height) {
        return weight / Math.pow(height / 100, 2);
    }

    // Method to format Date to String
    public String formatDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        return formatter.format(date);
    }
}