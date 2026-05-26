package quiz;

import java.util.*;

/**
 * Core quiz engine: asks questions, collects answers, and builds a QuizResult.
 * Demonstrates: OOP, Collections, Exception Handling, Arrays/ArrayList.
 */
public class QuizEngine {

    private static final int TIME_LIMIT_SECONDS = 30; // per question (enforced via hint only)

    private QuestionBank bank;
    private Scanner      scanner;

    public QuizEngine(QuestionBank bank, Scanner scanner) {
        this.bank    = bank;
        this.scanner = scanner;
    }

    /**
     * Runs a full quiz session and returns the completed result.
     *
     * @param playerName name of the player
     * @param numQuestions how many questions to ask
     */
    public QuizResult runQuiz(String playerName, int numQuestions) {
        List<Question> questions = bank.getRandomQuestions(numQuestions);
        QuizResult     result    = new QuizResult(playerName, questions.size());

        ConsoleUI.printSectionHeader("QUIZ STARTED – Good Luck, " + playerName + "!");
        System.out.printf("  You will be asked %d questions. Enter A / B / C / D  (or S to Skip).%n%n",
                questions.size());

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < questions.size(); i++) {
            Question q = questions.get(i);
            char userAnswer = askQuestion(q, i + 1, questions.size());
            result.record(q, userAnswer);

            // Instant per-question feedback
            if (userAnswer == ' ') {
                System.out.println("  ⏭  Skipped. Correct answer was: " + q.getCorrectAnswer()
                        + ") " + q.getOptionText(q.getCorrectAnswer()));
            } else if (q.isCorrect(userAnswer)) {
                System.out.println("  ✔  Correct!\n");
            } else {
                System.out.println("  ✘  Wrong! Correct answer: " + q.getCorrectAnswer()
                        + ") " + q.getOptionText(q.getCorrectAnswer()) + "\n");
            }
        }

        long endTime = System.currentTimeMillis();
        result.setTimeTakenSeconds((endTime - startTime) / 1000);

        return result;
    }

    // ── Private helpers ───────────────────────────────────────────────────────

    private char askQuestion(Question q, int current, int total) {
        System.out.printf("%n  ┌─ Question %d of %d  [%s] ─────────────────────────────%n",
                current, total, q.getCategory());
        System.out.printf("  │  %s%n", wrapText(q.getQuestionText(), 60));
        System.out.println("  │");

        char[] labels = {'A', 'B', 'C', 'D'};
        String[] opts = q.getOptions();
        for (int i = 0; i < 4; i++) {
            System.out.printf("  │  %c) %s%n", labels[i], opts[i]);
        }
        System.out.println("  └────────────────────────────────────────────────────");

        while (true) {
            System.out.print("  Your Answer (A/B/C/D or S to skip): ");
            String input = scanner.nextLine().trim().toUpperCase();

            if (input.isEmpty()) {
                System.out.println("  ⚠  Please enter A, B, C, D, or S.");
                continue;
            }

            char ch = input.charAt(0);
            if (ch == 'S') return ' ';
            if (ch >= 'A' && ch <= 'D') return ch;

            System.out.println("  ⚠  Invalid input. Enter A, B, C, D, or S.");
        }
    }

    /** Naive word-wrap for long questions. */
    private String wrapText(String text, int lineWidth) {
        if (text.length() <= lineWidth) return text;
        StringBuilder sb   = new StringBuilder();
        String[]      words = text.split(" ");
        int           col   = 0;
        for (String w : words) {
            if (col + w.length() > lineWidth) {
                sb.append("\n  │     ");
                col = 0;
            }
            sb.append(w).append(" ");
            col += w.length() + 1;
        }
        return sb.toString().trim();
    }
}

