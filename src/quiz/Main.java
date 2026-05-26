package quiz;

import java.util.*;

/**
 * ════════════════════════════════════════════════════════
 *   Online Quiz Application – Main Entry Point
 *   Core Java Project | Menu-Driven | OOP | Collections
 *   File Handling | Exception Handling | ArrayList
 * ════════════════════════════════════════════════════════
 *
 *  CONCEPTS DEMONSTRATED
 *  ──────────────────────────────────────────────────────
 *  OOP           → Classes: Question, QuizResult, QuestionBank,
 *                  QuizEngine, ResultManager, ConsoleUI
 *                  Encapsulation, Abstraction, Separation of Concerns
 *  Collections   → ArrayList, List, Collections.shuffle()
 *  Exception Handling → try-catch in file I/O, input validation
 *  File Handling → BufferedReader/Writer for questions & results
 *  Arrays        → String[] for answer options
 *  Menu-Driven   → switch statement main loop
 */
public class Main {

    private static final Scanner      scanner       = new Scanner(System.in);
    private static final QuestionBank questionBank  = new QuestionBank();
    private static final ResultManager resultMgr   = new ResultManager();
    private static final QuizEngine   quizEngine    = new QuizEngine(questionBank, scanner);

    public static void main(String[] args) {
        ConsoleUI.printWelcomeBanner();

        boolean running = true;
        while (running) {
            ConsoleUI.printMainMenu(questionBank.getTotalCount());

            String input = scanner.nextLine().trim();
            int    choice = -1;

            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("\n  ⚠  Invalid input. Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1  -> handleStartQuiz();
                case 2  -> handleViewQuestions();
                case 3  -> handleViewResults();
                case 4  -> handleAddQuestion();
                case 5  -> handleInstructions();
                case 0  -> { running = false; printGoodbye(); }
                default -> System.out.println("\n  ⚠  Invalid choice. Please select 0–5.");
            }
        }

        scanner.close();
    }

    // ── Menu Handlers ─────────────────────────────────────────────────────────

    private static void handleStartQuiz() {
        System.out.print("\n  Enter your name: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) name = "Anonymous";

        int maxQ = questionBank.getTotalCount();
        int numQ = promptInt(
                "  How many questions? (1–" + maxQ + "): ", 1, maxQ);

        // Run quiz
        QuizResult result = quizEngine.runQuiz(name, numQ);

        // Show results
        ConsoleUI.printFinalResult(result);
        ConsoleUI.printDetailedReview(result);

        // Save
        resultMgr.saveResult(result);

        ConsoleUI.pressEnterToContinue(scanner);
    }

    private static void handleViewQuestions() {
        ConsoleUI.printAllQuestions(questionBank.getAllQuestions());
        ConsoleUI.pressEnterToContinue(scanner);
    }

    private static void handleViewResults() {
        ConsoleUI.printSectionHeader("PAST RESULTS");
        resultMgr.printAllResults();
        ConsoleUI.pressEnterToContinue(scanner);
    }

    private static void handleAddQuestion() {
        ConsoleUI.printSectionHeader("ADD CUSTOM QUESTION");
        try {
            System.out.print("  Category (e.g. Java, OOP, DSA): ");
            String category = scanner.nextLine().trim();
            if (category.isEmpty()) category = "General";

            System.out.print("  Question text: ");
            String qText = scanner.nextLine().trim();
            if (qText.isEmpty()) throw new IllegalArgumentException("Question text cannot be empty.");

            String[] options = new String[4];
            char[] labels = {'A', 'B', 'C', 'D'};
            for (int i = 0; i < 4; i++) {
                System.out.print("  Option " + labels[i] + ": ");
                options[i] = scanner.nextLine().trim();
                if (options[i].isEmpty())
                    throw new IllegalArgumentException("Option " + labels[i] + " cannot be empty.");
            }

            System.out.print("  Correct answer (A/B/C/D): ");
            String ans = scanner.nextLine().trim().toUpperCase();
            if (ans.isEmpty() || ans.charAt(0) < 'A' || ans.charAt(0) > 'D')
                throw new IllegalArgumentException("Correct answer must be A, B, C, or D.");

            int  newId = questionBank.getTotalCount() + 1;
            Question q = new Question(newId, qText, options, ans.charAt(0), category);
            questionBank.addQuestion(q);

            System.out.println("\n  ✔  Question added successfully! (ID: " + newId + ")");
        } catch (IllegalArgumentException e) {
            System.out.println("\n  ✘  Error: " + e.getMessage());
        }
        ConsoleUI.pressEnterToContinue(scanner);
    }

    private static void handleInstructions() {
        ConsoleUI.printInstructions();
        ConsoleUI.pressEnterToContinue(scanner);
    }

    // ── Utilities ─────────────────────────────────────────────────────────────

    /**
     * Prompts for an integer in [min, max] with validation.
     */
    private static int promptInt(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            try {
                int val = Integer.parseInt(scanner.nextLine().trim());
                if (val >= min && val <= max) return val;
                System.out.printf("  ⚠  Please enter a number between %d and %d.%n", min, max);
            } catch (NumberFormatException e) {
                System.out.println("  ⚠  Invalid number. Try again.");
            }
        }
    }

    private static void printGoodbye() {
        System.out.println();
        System.out.println("  ╔" + "═".repeat(56) + "╗");
        System.out.println("  ║   Thank you for using the Online Quiz Application!    ║");
        System.out.println("  ║              Keep Learning. Keep Growing. 🎓          ║");
        System.out.println("  ╚" + "═".repeat(56) + "╝");
        System.out.println();
    }
}
