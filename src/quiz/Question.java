 package quiz;

import java.io.Serializable;

/**
 * Represents a single MCQ question in the quiz.
 * Implements Serializable for file handling.
 */
public class Question implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private String questionText;
    private String[] options;      // Always 4 options: A, B, C, D
    private char correctAnswer;    // 'A', 'B', 'C', or 'D'
    private String category;

    // Constructor
    public Question(int id, String questionText, String[] options, char correctAnswer, String category) {
        if (options == null || options.length != 4) {
            throw new IllegalArgumentException("Each question must have exactly 4 options.");
        }
        this.id            = id;
        this.questionText  = questionText;
        this.options       = options;
        this.correctAnswer = Character.toUpperCase(correctAnswer);
        this.category      = category;
    }

    // ── Getters ───────────────────────────────────────────────────────────────
    public int     getId()            { return id; }
    public String  getQuestionText()  { return questionText; }
    public String[] getOptions()      { return options; }
    public char    getCorrectAnswer() { return correctAnswer; }
    public String  getCategory()      { return category; }

    /** Returns option text for a given letter (A-D). */
    public String getOptionText(char letter) {
        int idx = Character.toUpperCase(letter) - 'A';
        if (idx < 0 || idx > 3) throw new IllegalArgumentException("Invalid option: " + letter);
        return options[idx];
    }

    /** Checks whether the supplied answer is correct. */
    public boolean isCorrect(char userAnswer) {
        return Character.toUpperCase(userAnswer) == correctAnswer;
    }

    @Override
    public String toString() {
        return String.format("Q%d [%s]: %s", id, category, questionText);
    }
}
 
    

