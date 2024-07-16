public class FitnessProgress {
    //Author Kagen
    //Student Id:24000582
    // Fields to store date and scores
    private Date date;
    private double[] scores;

    // Constructor
    public FitnessProgress(Date date, double[] scores) {
        this.date = date;
        this.scores = scores;
    }

    // Getter for date
    public Date getDate() {
        return date;
    }

    // Setter for date
    public void setDate(Date date) {
        this.date = date;
    }

    // Getter for scores
    public double[] getScores() {
        return scores;
    }

    // Setter for scores
    public void setScores(double[] scores) {
        this.scores = scores;
    }
}

