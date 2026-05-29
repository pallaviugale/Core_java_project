# 🎓 Online Quiz Application — Core Java Project

A fully-featured console-based quiz application demonstrating core Java concepts.

---

## 📁 Project Structure

```
OnlineQuizApp/
├── src/
│   └── quiz/
│       ├── Main.java            ← Entry point, menu-driven loop
│       ├── Question.java        ← MCQ model (OOP, Serializable)
│       ├── QuizResult.java      ← Quiz attempt outcome model
│       ├── QuestionBank.java    ← File handling, question storage
│       ├── QuizEngine.java      ← Core quiz logic, user interaction
│       ├── ResultManager.java   ← Save/load results to file
│       └── ConsoleUI.java       ← All display/formatting methods
├── data/
│   ├── questions.txt            ← Auto-generated question store
│   └── results.txt             ← Persisted quiz history
└── README.md
```

---

## ▶️ How to Compile & Run

```bash
# 1. Compile
mkdir -p out
javac -d out src/quiz/*.java

# 2. Run
java -cp out quiz.Main
```

---

## 🗂️ Java Concepts Used

| Concept               | Where Applied                                          |
|-----------------------|--------------------------------------------------------|
| **OOP**               | 6 classes with encapsulation, abstraction, SoC         |
| **Collections**       | `ArrayList`, `List`, `Collections.shuffle()`           |
| **Exception Handling**| `try-catch` in file I/O, input parsing, validation     |
| **File Handling**     | `BufferedReader/Writer` for questions & results        |
| **Arrays**            | `String[]` for 4 answer options per question           |
| **Menu-Driven**       | `switch` statement main loop in `Main.java`            |
| **Serializable**      | `Question` implements `Serializable`                   |

---

## 🎮 Features

1. **Start Quiz** — Random MCQ selection, instant per-question feedback
2. **View All Questions** — Browse full question bank with answers marked
3. **View Past Results** — Full history loaded from `data/results.txt`
4. **Add Custom Question** — Input validation, persisted immediately
5. **Instructions** — Grading scale and how-to guide
6. **Skip Support** — Type `S` to skip any question
7. **Auto-scoring** — Correct / Wrong / Skipped counted automatically
8. **Detailed Review** — Every Q shown with your answer vs correct answer
9. **Grading** — A+ / A / B / C / D / F based on percentage
10. **20 Seed Questions** — Java, OOP, DSA, General CS categories

---

## 📝 Question File Format (`data/questions.txt`)

```
1|Java|Which keyword is used to define a class in Java?
define|class|Class|struct
B
---
```
Each block: `id|category|question` → `optA|optB|optC|optD` → `correctLetter` → `---`

---

## 🏗️ Class Responsibilities

### `Question.java`
- Stores question text, 4 options, correct answer, category
- `isCorrect(char)` validates user answer
- `getOptionText(char)` retrieves option by letter

### `QuizResult.java`
- Tracks correct/wrong/skipped counts
- Computes percentage, grade, result message
- Stores parallel lists of questions + user answers for review

### `QuestionBank.java`
- Loads questions from `data/questions.txt` on startup
- Seeds 20 default questions if file is absent
- `getRandomQuestions(n)` shuffles and returns a subset
- Saves after every `addQuestion()` call

### `QuizEngine.java`
- Drives the question-answer loop
- Validates input (A/B/C/D/S only)
- Shows instant feedback after each answer
- Records each answer in `QuizResult`

### `ResultManager.java`
- Appends formatted results to `data/results.txt`
- Prints full history on demand

### `ConsoleUI.java`
- All print methods — banners, menus, result tables, question lists
- Keeps business logic classes clean

### `Main.java`
- Menu-driven `switch` loop (choices 0–5)
- Instantiates all components
- Input validation with `try-catch NumberFormatException`
