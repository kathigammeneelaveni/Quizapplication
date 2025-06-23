import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class Question {
    String question;
    String[] options;
    int correctIndex;

    public Question(String q, String[] opts, int correct) {
        question = q;
        options = opts;
        correctIndex = correct;
    }
}

public class QuizGame extends JFrame implements ActionListener {
    Question[] questions;
    int current = 0;
    int score = 0;

    JLabel questionLabel;
    JRadioButton[] options = new JRadioButton[4];
    ButtonGroup group = new ButtonGroup();
    JButton nextButton;

    public QuizGame() {
        setTitle("Java Quiz Game");
        setSize(500, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Questions setup
        questions = new Question[] {
            new Question("What is the capital of France?", new String[]{"Paris", "Rome", "London", "Berlin"}, 0),
            new Question("Which is a Java keyword?", new String[]{"finally", "define", "throwit", "scope"}, 0),
            new Question("Which planet is known as Red Planet?", new String[]{"Earth", "Mars", "Jupiter", "Venus"}, 1)
        };

        // Question label
        questionLabel = new JLabel("Question will appear here");
        questionLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(questionLabel, BorderLayout.NORTH);

        // Options panel
        JPanel optionPanel = new JPanel(new GridLayout(4, 1));
        for (int i = 0; i < 4; i++) {
            options[i] = new JRadioButton();
            group.add(options[i]);
            optionPanel.add(options[i]);
        }
        add(optionPanel, BorderLayout.CENTER);

        // Next button
        nextButton = new JButton("Next");
        nextButton.addActionListener(this);
        add(nextButton, BorderLayout.SOUTH);

        // Load first question
        loadQuestion(current);

        setVisible(true);
    }

    public void loadQuestion(int index) {
        group.clearSelection();
        Question q = questions[index];
        questionLabel.setText("Q" + (index + 1) + ": " + q.question);
        for (int i = 0; i < 4; i++) {
            options[i].setText(q.options[i]);
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (isAnswerSelected()) {
            int selected = getSelectedOptionIndex();
            if (selected == questions[current].correctIndex) {
                score++;
            }
            current++;
            if (current < questions.length) {
                loadQuestion(current);
            } else {
                showResult();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select an option!");
        }
    }

    private boolean isAnswerSelected() {
        for (JRadioButton opt : options) {
            if (opt.isSelected()) return true;
        }
        return false;
    }

    private int getSelectedOptionIndex() {
        for (int i = 0; i < 4; i++) {
            if (options[i].isSelected()) return i;
        }
        return -1;
    }

    private void showResult() {
        JOptionPane.showMessageDialog(this, "Quiz Over! Your score: " + score + "/" + questions.length);
        System.exit(0);
    }

    public static void main(String[] args) {
        new QuizGame();
    }
}
