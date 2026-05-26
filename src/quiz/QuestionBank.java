
package quiz;

import java.io.*;
import java.util.*;

/**
 * Manages loading, storing, and retrieving quiz questions.
 * Demonstrates: File Handling, Collections (ArrayList), OOP.
 */
public class QuestionBank {

    private static final String DATA_FILE = "data/questions.txt";
    private List<Question> allQuestions   = new ArrayList<>();

    // ── Constructor ───────────────────────────────────────────────────────────
    public QuestionBank() {
        loadFromFile();
        if (allQuestions.isEmpty()) {
            seedDefaultQuestions();
            saveToFile();
        }
    }

    // ── Public API ────────────────────────────────────────────────────────────

    /** Returns all questions. */
    public List<Question> getAllQuestions() {
        return Collections.unmodifiableList(allQuestions);
    }

    /** Returns a random subset of {@code count} questions. */
    public List<Question> getRandomQuestions(int count) {
        List<Question> shuffled = new ArrayList<>(allQuestions);
        Collections.shuffle(shuffled);
        return shuffled.subList(0, Math.min(count, shuffled.size()));
    }

    /** Adds a new question and persists to file. */
    public void addQuestion(Question q) {
        allQuestions.add(q);
        saveToFile();
    }

    public int getTotalCount() {
        return allQuestions.size();
    }

    // ── File Handling ─────────────────────────────────────────────────────────

    /**
     * File format (one question block):
     *   ID|Category|QuestionText
     *   OptionA|OptionB|OptionC|OptionD
     *   CorrectAnswer
     *   ---
     */
    private void loadFromFile() {
        File file = new File(DATA_FILE);
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.equals("---")) continue;

                // Line 1: id|category|question
                String[] meta    = line.split("\\|", 3);
                int    id        = Integer.parseInt(meta[0].trim());
                String category  = meta[1].trim();
                String qText     = meta[2].trim();

                // Line 2: optA|optB|optC|optD
                String optLine   = br.readLine();
                String[] options = optLine.split("\\|", 4);
                for (int i = 0; i < options.length; i++) options[i] = options[i].trim();

                // Line 3: correct answer letter
                char correct = br.readLine().trim().charAt(0);

                allQuestions.add(new Question(id, qText, options, correct, category));
            }
            System.out.println("[QuestionBank] Loaded " + allQuestions.size() + " questions from file.");
        } catch (IOException | NumberFormatException | ArrayIndexOutOfBoundsException e) {
            System.out.println("[QuestionBank] Warning: Could not read data file – " + e.getMessage());
            allQuestions.clear();
        }
    }

    private void saveToFile() {
        File dir = new File("data");
        dir.mkdirs();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(DATA_FILE))) {
            for (Question q : allQuestions) {
                bw.write(q.getId() + "|" + q.getCategory() + "|" + q.getQuestionText());
                bw.newLine();
                bw.write(String.join("|", q.getOptions()));
                bw.newLine();
                bw.write(String.valueOf(q.getCorrectAnswer()));
                bw.newLine();
                bw.write("---");
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("[QuestionBank] Error saving questions: " + e.getMessage());
        }
    }

    // ── Seed Data ─────────────────────────────────────────────────────────────
    private void seedDefaultQuestions() {
        System.out.println("[QuestionBank] Seeding default questions...");

        // ── Java ──────────────────────────────────────────────────────────────
        allQuestions.add(new Question(1,
            "Which keyword is used to define a class in Java?",
            new String[]{"define", "class", "Class", "struct"}, 'B', "Java"));

        allQuestions.add(new Question(2,
            "What is the default value of an int variable in Java?",
            new String[]{"null", "undefined", "0", "-1"}, 'C', "Java"));

        allQuestions.add(new Question(3,
            "Which of the following is NOT a Java OOP principle?",
            new String[]{"Encapsulation", "Polymorphism", "Compilation", "Inheritance"}, 'C', "Java"));

        allQuestions.add(new Question(4,
            "Which collection class allows duplicate elements and maintains insertion order?",
            new String[]{"HashSet", "TreeSet", "ArrayList", "HashMap"}, 'C', "Java"));

        allQuestions.add(new Question(5,
            "What does JVM stand for?",
            new String[]{"Java Virtual Machine", "Java Variable Method", "Java Verified Module", "Java Visual Manager"}, 'A', "Java"));

        allQuestions.add(new Question(6,
            "Which exception is thrown when dividing an integer by zero?",
            new String[]{"NullPointerException", "ArithmeticException", "NumberFormatException", "IllegalArgumentException"}, 'B', "Java"));

        allQuestions.add(new Question(7,
            "Which access modifier makes a member accessible only within its own class?",
            new String[]{"public", "protected", "default", "private"}, 'D', "Java"));

        allQuestions.add(new Question(8,
            "What is the size of a 'char' data type in Java?",
            new String[]{"8 bits", "16 bits", "32 bits", "64 bits"}, 'B', "Java"));

        // ── OOP ───────────────────────────────────────────────────────────────
        allQuestions.add(new Question(9,
            "Which OOP concept hides implementation details from the user?",
            new String[]{"Inheritance", "Polymorphism", "Encapsulation", "Abstraction"}, 'D', "OOP"));

        allQuestions.add(new Question(10,
            "Which keyword is used to inherit a class in Java?",
            new String[]{"implements", "extends", "inherits", "super"}, 'B', "OOP"));

        allQuestions.add(new Question(11,
            "What is method overloading?",
            new String[]{"Same name, same parameters", "Different name, same return type",
                         "Same name, different parameters", "Same name, different return type only"}, 'C', "OOP"));

        allQuestions.add(new Question(12,
            "Which keyword refers to the parent class in Java?",
            new String[]{"this", "base", "super", "parent"}, 'C', "OOP"));

        // ── DSA ───────────────────────────────────────────────────────────────
        allQuestions.add(new Question(13,
            "What is the time complexity of binary search?",
            new String[]{"O(n)", "O(n²)", "O(log n)", "O(n log n)"}, 'C', "DSA"));

        allQuestions.add(new Question(14,
            "Which data structure uses LIFO order?",
            new String[]{"Queue", "Stack", "LinkedList", "Tree"}, 'B', "DSA"));

        allQuestions.add(new Question(15,
            "Which sorting algorithm has the best average-case time complexity?",
            new String[]{"Bubble Sort", "Selection Sort", "Insertion Sort", "Merge Sort"}, 'D', "DSA"));

        // ── General CS ────────────────────────────────────────────────────────
        allQuestions.add(new Question(16,
            "What does CPU stand for?",
            new String[]{"Central Processing Unit", "Computer Personal Unit", "Central Program Utility", "Core Processing Unit"}, 'A', "General CS"));

        allQuestions.add(new Question(17,
            "Which protocol is used for sending emails?",
            new String[]{"FTP", "HTTP", "SMTP", "TCP"}, 'C', "General CS"));

        allQuestions.add(new Question(18,
            "Which layer of the OSI model handles routing?",
            new String[]{"Physical", "Data Link", "Network", "Transport"}, 'C', "General CS"));

        allQuestions.add(new Question(19,
            "What does HTML stand for?",
            new String[]{"Hyper Text Markup Language", "High Text Manipulation Language",
                         "Hyper Transfer Markup Language", "Hyper Text Modern Language"}, 'A', "General CS"));

        allQuestions.add(new Question(20,
            "Which of the following is a compiled language?",
            new String[]{"Python", "JavaScript", "Java", "Ruby"}, 'C', "General CS"));
    }
}
