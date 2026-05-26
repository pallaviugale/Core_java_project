package quiz;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Persists quiz results to a human-readable log file.
 * Demonstrates: File Handling, Exception Handling.
 */
public class ResultManager {

    private static final String RESULTS_FILE   = "data/results.txt";
    private static final DateTimeFormatter FMT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /** Appends a completed QuizResult to the results log. */
    public void saveResult(QuizResult result) {
        File dir = new File("data");
        dir.mkdirs();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(RESULTS_FILE, true))) {
            bw.write("========================================");
            bw.newLine();
            bw.write("Player   : " + result.getPlayerName());
            bw.newLine();
            bw.write("Date     : " + LocalDateTime.now().format(FMT));
            bw.newLine();
            bw.write("Score    : " + result.getCorrectCount() + "/" + result.getTotalQuestions()
                    + "  (" + String.format("%.1f", result.getPercentage()) + "%)");
            bw.newLine();
            bw.write("Grade    : " + result.getGrade());
            bw.newLine();
            bw.write("Correct  : " + result.getCorrectCount());
            bw.newLine();
            bw.write("Wrong    : " + result.getWrongCount());
            bw.newLine();
            bw.write("Skipped  : " + result.getSkippedCount());
            bw.newLine();
            bw.write("Time     : " + result.getTimeTakenSeconds() + " seconds");
            bw.newLine();
            bw.write("========================================");
            bw.newLine();
            bw.newLine();
            System.out.println("\n  [✔] Result saved to " + RESULTS_FILE);
        } catch (IOException e) {
            System.out.println("\n  [✘] Could not save result: " + e.getMessage());
        }
    }

    /** Prints all previously saved results from the log file. */
    public void printAllResults() {
        File file = new File(RESULTS_FILE);
        if (!file.exists() || file.length() == 0) {
            System.out.println("\n  No results found yet. Play a quiz first!");
            return;
        }

        System.out.println();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println("  " + line);
            }
        } catch (IOException e) {
            System.out.println("  Error reading results: " + e.getMessage());
        }
    }
}
