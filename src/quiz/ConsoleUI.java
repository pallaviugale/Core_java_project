 package quiz;

import java.util.List;

/**
 * Centralizes all console output formatting.
 * Demonstrates: Separation of Concerns (OOP principle).
 */
public class ConsoleUI {

    private static final String LINE  = "═".repeat(56);
    private static final String THIN  = "─".repeat(56);

    // ── Banners ───────────────────────────────────────────────────────────────

    public static void printWelcomeBanner() {
        System.out.println();
        System.out.println("  ╔" + LINE + "╗");
        System.out.println("  ║       🎓  ONLINE QUIZ APPLICATION  🎓            ║");
        System.out.println("  ║         Core Java Console Project                ║");
        System.out.println("  ╚" + LINE + "╝");
        System.out.println();
    }

    public static void printSectionHeader(String title) {
        System.out.println();
        System.out.println("  ┌" + THIN + "┐");
        System.out.printf("  │  %-54s│%n", title);
        System.out.println("  └" + THIN + "┘");
    }

    // ── Menus ─────────────────────────────────────────────────────────────────

    public static void printMainMenu(int totalQuestions) {
        System.out.println();
        System.out.println("  ┌─── MAIN MENU " + "─".repeat(42) + "┐");
        System.out.println("  │                                                        │");
        System.out.println("  │   1. Start Quiz                                        │");
        System.out.println("  │   2. View All Questions                                │");
        System.out.println("  │   3. View Past Results                                 │");
        System.out.println("  │   4. Add a Custom Question                             │");
        System.out.println("  │   5. About / Instructions                              │");
        System.out.println("  │   0. Exit                                              │");
        System.out.println("  │                                                        │");
        System.out.printf( "  │   Questions in bank: %-33d│%n", totalQuestions);
        System.out.println("  └" + "─".repeat(56) + "┘");
        System.out.print("  Enter your choice: ");
    }

    // ── Results ───────────────────────────────────────────────────────────────

    public static void printFinalResult(QuizResult r) {
        System.out.println();
        System.out.println("  ╔" + LINE + "╗");
        System.out.println("  ║                   QUIZ COMPLETE!                     ║");
        System.out.println("  ╠" + LINE + "╣");
        System.out.printf( "  ║  Player  : %-43s║%n", r.getPlayerName());
        System.out.printf( "  ║  Score   : %d / %d  (%.1f%%)%-30s║%n",
                r.getCorrectCount(), r.getTotalQuestions(), r.getPercentage(), "");
        System.out.printf( "  ║  Grade   : %-43s║%n", r.getGrade());
        System.out.printf( "  ║  Correct : %-43d║%n", r.getCorrectCount());
        System.out.printf( "  ║  Wrong   : %-43d║%n", r.getWrongCount());
        System.out.printf( "  ║  Skipped : %-43d║%n", r.getSkippedCount());
        System.out.printf( "  ║  Time    : %-40s║%n", r.getTimeTakenSeconds() + " seconds");
        System.out.println("  ╠" + LINE + "╣");
        System.out.printf( "  ║  %-53s║%n", r.getResultMessage());
        System.out.println("  ╚" + LINE + "╝");
    }

    public static void printDetailedReview(QuizResult r) {
        printSectionHeader("DETAILED REVIEW – Correct & Wrong Answers");

        List<Question>  questions   = r.getQuestions();
        List<Character> userAnswers = r.getUserAnswers();

        for (int i = 0; i < questions.size(); i++) {
            Question q          = questions.get(i);
            char     userAnswer = userAnswers.get(i);
            boolean  correct    = q.isCorrect(userAnswer);
            boolean  skipped    = (userAnswer == ' ');

            String status = skipped ? "⏭ SKIPPED" : (correct ? "✔  CORRECT" : "✘  WRONG  ");
            System.out.printf("%n  %s  Q%-2d [%s]%n", status, i + 1, q.getCategory());
            System.out.println("        " + q.getQuestionText());

            if (!skipped) {
                System.out.printf("        Your answer : %c) %s%n",
                        userAnswer, q.getOptionText(userAnswer));
            }
            if (!correct || skipped) {
                System.out.printf("        Correct ans : %c) %s%n",
                        q.getCorrectAnswer(), q.getOptionText(q.getCorrectAnswer()));
            }
        }
        System.out.println();
    }

    // ── Question List ─────────────────────────────────────────────────────────

    public static void printAllQuestions(List<Question> questions) {
        printSectionHeader("ALL QUESTIONS IN BANK (" + questions.size() + " total)");
        for (Question q : questions) {
            System.out.printf("%n  Q%-2d [%s]%n", q.getId(), q.getCategory());
            System.out.println("       " + q.getQuestionText());
            char[] labels = {'A', 'B', 'C', 'D'};
            String[] opts = q.getOptions();
            for (int i = 0; i < 4; i++) {
                String marker = (labels[i] == q.getCorrectAnswer()) ? " ✔" : "  ";
                System.out.printf("       %c)%s %s%n", labels[i], marker, opts[i]);
            }
        }
        System.out.println();
    }

    // ── Instructions ──────────────────────────────────────────────────────────

    public static void printInstructions() {
        printSectionHeader("HOW TO PLAY");
        System.out.println();
        System.out.println("  • Choose 'Start Quiz' from the main menu.");
        System.out.println("  • Enter your name and how many questions you'd like.");
        System.out.println("  • For each question, type A, B, C, or D and press Enter.");
        System.out.println("  • Type S to skip a question (no penalty, but no points).");
        System.out.println("  • Instant feedback is shown after every answer.");
        System.out.println("  • A detailed review is shown at the end of every quiz.");
        System.out.println("  • All results are saved automatically in data/results.txt.");
        System.out.println();
        System.out.println("  GRADING SCALE:");
        System.out.println("    A+ : 90 – 100%      A : 80 – 89%");
        System.out.println("    B  : 70 – 79%       C : 60 – 69%");
        System.out.println("    D  : 50 – 59%       F : Below 50%");
        System.out.println();
    }

    // ── Utility ───────────────────────────────────────────────────────────────

    public static void pressEnterToContinue(java.util.Scanner sc) {
        System.out.print("\n  Press ENTER to continue...");
        sc.nextLine();
    }
}
 
    

