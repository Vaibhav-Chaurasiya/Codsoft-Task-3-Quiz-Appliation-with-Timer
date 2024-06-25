import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

class Question {
    String question;
    String[] options;
    int correctAnswer;

    public Question(String question, String[] options, int correctAnswer) {
        this.question = question;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }
}

class Quiz {
    private Question[] questions;
    private int score;
    private int currentQuestionIndex;
    private boolean answerSubmitted;
    private Timer timer;

    public Quiz(Question[] questions) {
        this.questions = questions;
        this.score = 0;
        this.currentQuestionIndex = 0;
        this.answerSubmitted = false;
    }

    public void start() {
        try (Scanner scanner = new Scanner(System.in)) {
            while (currentQuestionIndex < questions.length) {
                answerSubmitted = false;
                displayQuestion();

                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        if (!answerSubmitted) {
                            System.out.println("\nTime's up! Moving to the next question.");
                            nextQuestion();
                        }
                    }
                }, 10000); // 10 seconds for each question

                System.out.print("Your answer (1-4): ");
                int answer = scanner.nextInt();
                answerSubmitted = true;
                timer.cancel();
                checkAnswer(answer);
                nextQuestion();
            }
        }

        displayResults();
    }

    private void displayQuestion() {
        Question q = questions[currentQuestionIndex];
        System.out.println("\nQuestion " + (currentQuestionIndex + 1) + ": " + q.question);
        for (int i = 0; i < q.options.length; i++) {
            System.out.println((i + 1) + ". " + q.options[i]);
        }
    }

    private void checkAnswer(int answer) {
        if (answer == questions[currentQuestionIndex].correctAnswer) {
            score++;
            System.out.println("Correct!");
        } else {
            System.out.println("Incorrect! The correct answer was " + questions[currentQuestionIndex].correctAnswer);
        }
    }

    private void nextQuestion() {
        currentQuestionIndex++;
    }

    private void displayResults() {
        System.out.println("\nQuiz Over!");
        System.out.println("Your final score is " + score + " out of " + questions.length);
        System.out.println("Summary of correct/incorrect answers:");
        for (int i = 0; i < questions.length; i++) {
            System.out.println(
                    "Question " + (i + 1) + ": " + (i < currentQuestionIndex && i < score ? "Correct" : "Incorrect"));
        }
    }
}

public class QuizApplication {
    public static void main(String[] args) {
        Question[] questions = {
                new Question("What is the capital of India?",
                        new String[] { "New Delhi", "Lucknow", "Agra", "Azamgarh" },
                        1),
                new Question("What is 2 + 2?", new String[] { "3", "4", "5", "6" }, 2),
                new Question("What is the color of the sky?", new String[] { "Blue", "Green", "Red", "Yellow" }, 1)
        };

        Quiz quiz = new Quiz(questions);
        quiz.start();
    }
}
