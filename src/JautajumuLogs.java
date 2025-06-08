import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JautajumuLogs extends JFrame {

    private static final long serialVersionUID = 1L;
    static JautajumuLogs frame = new JautajumuLogs();

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private JPanel contentPane;
    private JTextArea questionTextArea;
    private JRadioButton[] optionButtons;
    private ButtonGroup optionsGroup;
    private JButton nextButton;

    private List<String> allQuestionTexts;
    private List<String> allOptionAs;
    private List<String> allOptionBs;
    private List<String> allOptionCs;
    private List<String> allOptionDs;

    private int currentQuestionIndex = 0;

    private final String QUESTIONS_FILE = "jautajumi.txt";

    public JautajumuLogs() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 900, 500);
        setResizable(false);
        setTitle("Jautājumi");

        contentPane = new JPanel();
        contentPane.setBackground(new Color(255, 255, 255));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);
        setContentPane(contentPane);

        questionTextArea = new JTextArea("Jautājums tiks ielādēts...");
        questionTextArea.setFont(new Font("Tahoma", Font.PLAIN, 18));
        questionTextArea.setLineWrap(true);
        questionTextArea.setWrapStyleWord(true);
        questionTextArea.setEditable(false);
        questionTextArea.setBackground(contentPane.getBackground());
        questionTextArea.setBorder(null);

        questionTextArea.setBounds(50, 50, 800, 80);
        contentPane.add(questionTextArea);

        optionButtons = new JRadioButton[4];
        optionsGroup = new ButtonGroup();
        int yPos = 150;
        for (int i = 0; i < 4; i++) {
            optionButtons[i] = new JRadioButton("");
            optionButtons[i].setFont(new Font("Tahoma", Font.PLAIN, 14));
            optionButtons[i].setBounds(70, yPos + (i * 40), 800, 30);
            optionsGroup.add(optionButtons[i]);
            contentPane.add(optionButtons[i]);
        }

        nextButton = new JButton("Nākamais");
        nextButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
        nextButton.setBounds(300, 400, 200, 40);
        contentPane.add(nextButton);

        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadNextQuestion();
            }
        });

        allQuestionTexts = new ArrayList<>();
        allOptionAs = new ArrayList<>();
        allOptionBs = new ArrayList<>();
        allOptionCs = new ArrayList<>();
        allOptionDs = new ArrayList<>();

        loadQuestionsFromFile(QUESTIONS_FILE);
        if (allQuestionTexts.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Neizdevās ielādēt jautājumus vai fails ir tukšs: " + QUESTIONS_FILE, "Kļūda", JOptionPane.ERROR_MESSAGE);
            nextButton.setEnabled(false);
        } else {
            displayQuestion();
        }
    }

    private void loadQuestionsFromFile(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            List<String> currentQuestionData = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                if (line.trim().equals("---")) {
                    if (currentQuestionData.size() >= 5) {
                        allQuestionTexts.add(currentQuestionData.get(0));
                        allOptionAs.add(removeOptionPrefix(currentQuestionData.get(1)));
                        allOptionBs.add(removeOptionPrefix(currentQuestionData.get(2)));
                        allOptionCs.add(removeOptionPrefix(currentQuestionData.get(3)));
                        allOptionDs.add(removeOptionPrefix(currentQuestionData.get(4)));
                    } else {
                        System.out.println("Brīdinājums: Nepilnīgs jautājuma bloks. Rindas: " + currentQuestionData.size());
                    }
                    currentQuestionData.clear();
                } else {
                    currentQuestionData.add(line.trim());
                }
            }
            
            if (currentQuestionData.size() >= 5) {
                allQuestionTexts.add(currentQuestionData.get(0));
                allOptionAs.add(removeOptionPrefix(currentQuestionData.get(1)));
                allOptionBs.add(removeOptionPrefix(currentQuestionData.get(2)));
                allOptionCs.add(removeOptionPrefix(currentQuestionData.get(3)));
                allOptionDs.add(removeOptionPrefix(currentQuestionData.get(4)));
            } else if (!currentQuestionData.isEmpty()) {
                System.out.println("Brīdinājums: Nepilnīgs pēdējais jautājuma bloks. Rindas: " + currentQuestionData.size());
            }

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Kļūda, lasot jautājumus no faila: " + e.getMessage(), "Faila Lasīšanas Kļūda", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private String removeOptionPrefix(String optionText) {
        if (optionText != null && optionText.length() >= 3 && Character.isLetter(optionText.charAt(0)) && optionText.charAt(1) == ')') {
            return optionText.substring(2).trim();
        }
        return optionText;
    }

    private void displayQuestion() {
        if (currentQuestionIndex < allQuestionTexts.size()) {
            questionTextArea.setText(allQuestionTexts.get(currentQuestionIndex));

            optionButtons[0].setText("A) " + allOptionAs.get(currentQuestionIndex));
            optionButtons[1].setText("B) " + allOptionBs.get(currentQuestionIndex));
            optionButtons[2].setText("C) " + allOptionCs.get(currentQuestionIndex));
            optionButtons[3].setText("D) " + allOptionDs.get(currentQuestionIndex));

            optionsGroup.clearSelection();

            if (currentQuestionIndex == allQuestionTexts.size() - 1) {
                nextButton.setText("Pabeigt"); 
            } else {
                nextButton.setText("Nākamais");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Esat apskatījis visus jautājumus!", "Pabeigts", JOptionPane.INFORMATION_MESSAGE);
            nextButton.setEnabled(false); 
        }
    }

    private void loadNextQuestion() {
        currentQuestionIndex++;
        displayQuestion();
    }

    public static JautajumuLogs getFrame() {
        return frame;
    }

    public static void setFrame(JautajumuLogs frame) {
        JautajumuLogs.frame = frame;
    }
}