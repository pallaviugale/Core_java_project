package quiz;

import java.util.List;
import java.util.ArrayList;

/**
 * Stores the outcome of a single quiz attempt.
 */
public class QuizResult {

    private String playerName;
    private int    totalQuestions;
    private int    correctCount;
    private int    wrongCount;
    private int    skippedCount;
    private long   timeTakenSeconds;

    // Parallel lists – same index links question to user's answer
    private List<Question> questions    = new ArrayList<>();
    private List<Character> userAnswers = new ArrayList<>();

    public QuizResult(String playerName, int totalQuestions) {
        this.playerName     = playerName;
        this.totalQuestions = totalQuestions;
    }

    /** Record one answered question. */
    public void record(Question q, char userAnswer) {
        questions.add(q);
        userAnswers.add(userAnswer);
        if (userAnswer == ' ') {          // ' ' means skipped
            skippedCount++;
        } else if (q.isCorrect(userAnswer)) {
            correctCount++;
        } else {
            wrongCount++;
        }
    }

    // ── Computed ──────────────────────────────────────────────────────────────
    public double getPercentage() {
        return totalQuestions == 0 ? 0 : (correctCount * 100.0) / totalQuestions;
    }

    public String getGrade() {
        double pct = getPercentage();
        if (pct >= 90) return "A+";
        if (pct >= 80) return "A";
        if (pct >= 70) return "B";
        if (pct >= 60) return "C";
        if (pct >= 50) return "D";
        return "F";
    }

    public String getResultMessage() {
        double pct = getPercentage();
        if (pct >= 80) return "Outstanding! Excellent performance!";
        if (pct >= 60) return "Good job! Keep it up!";
        if (pct >= 40) return "Average performance. More practice needed.";
        return "Need significant improvement. Don't give up!";
    }

    // ── Getters ───────────────────────────────────────────────────────────────
    public String          getPlayerName()     { return playerName; }
    public int             getTotalQuestions() { return totalQuestions; }
    public int             getCorrectCount()   { return correctCount; }
    public int             getWrongCount()     { return wrongCount; }
    public int             getSkippedCount()   { return skippedCount; }
    public long            getTimeTakenSeconds(){ return timeTakenSeconds; }
    public List<Question>  getQuestions()      { return questions; }
    public List<Character> getUserAnswers()    { return userAnswers; }

    public void setTimeTakenSeconds(long t)    { this.timeTakenSeconds = t; }
}

