import java.util.ArrayList;//Author:Amir,Adam

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

    @Override
    public String toString() {
        return "FitnessPlan{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}

public class FitnessPlans {
    private ArrayList<FitnessPlan> plans;

    public FitnessPlans() {
        this.plans = new ArrayList<>();
    }

    // Add a new fitness plan
    public void addPlan(String name, String description) {
        FitnessPlans plan = new FitnessPlans(name, description);
        plans.add(plan);
        System.out.println("Added: " + plan);
    }

    // Remove a fitness plan by name
    public void removePlan(String name) {
        FitnessPlans planToRemove = null;
        for (FitnessPlans plan : plans) {
            if (plan.getName().equalsIgnoreCase(name)) {
                planToRemove = plan;
                break;
            }
        }
        if (planToRemove != null) {
            plans.remove(planToRemove);
            System.out.println("Removed: " + planToRemove);
        } else {
            System.out.println("Plan not found: " + name);
        }
    }

    // Print all fitness plans
    public void printPlans() {
        System.out.println("Fitness Plans:");
        for (FitnessPlan plan : plans) {
            System.out.println(plan);
        }
    }

    // Find a fitness plan by name
    public void findPlan(String name) {
        for (FitnessPlans plan : plans) {
            if (plan.getName().equalsIgnoreCase(name)) {
                System.out.println("Found: " + plan);
                return;
            }
        }
        System.out.println("Plan not found: " + name);
    }

    public static void main(String[] args) {
        FitnessPlans fitnessPlans = new FitnessPlans();
        fitnessPlans.addPlan("Cardio Blast", "A high-intensity cardio workout plan.");
        fitnessPlans.addPlan("Strength Training", "A plan focused on building muscle strength.");
        fitnessPlans.printPlans();
        fitnessPlans.findPlan("Cardio Blast");
        fitnessPlans.removePlan("Strength Training");
        fitnessPlans.printPlans();
    }
}
