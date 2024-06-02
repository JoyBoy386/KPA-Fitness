public class FitnessPlan { //Author:Amril Najwan
    public static void main(String[] args) {

        String name = "Ahmad Ali";
        int age = 21;
        double weight = 72.0;
        double height = 183.0;

        double bmi = weight / Math.pow(height / 100, 2);

        System.out.println("-------------------------------");
        System.out.println("Name: " + name);
        System.out.println("Age: " + age);
        System.out.println("Weight: " + weight + " kg");
        System.out.println("Height: " + height + " cm");
        System.out.println("BMI: " + String.format("%.2f", bmi));
        System.out.println("A healthy BMI of 18.5 to 24.9");
        System.out.println("-------------------------------");

        System.out.println("Fitness Plan Objectives:");
        System.out.println("1. Keep your diet well-balanced, prioritising healthy fats and protein.");
        System.out.println("2. Exercise regularly, including both cardiovascular and strength training workouts.");
        System.out.println("3. Consume hydration by drinking at least 8 glasses of water per day.");
        System.out.println("4. Get at least 7-8 hours of sleep each night to support recovery and overall health.");
        System.out.println("-------------------------------");
    }
}
