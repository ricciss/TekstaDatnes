import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

public class BeiguLogs extends JFrame {
	
	static JautajumuLogs frame = new JautajumuLogs();
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private final String RESULTS_FILE = "rezultati.txt";
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

	/**
	 * Create the frame.
	 */
	public BeiguLogs(int finalScore, int correctCount, int incorrectCount,
            List<String> allQuestionTexts,
            List<String> allOptionAs, List<String> allOptionBs, List<String> allOptionCs, List<String> allOptionDs,
            List<String> allUserAnswers, List<String> allCorrectAnswers) {
setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
setBounds(100, 100, 900, 700);
setResizable(false);
setTitle("Testa Rezultāti");

contentPane = new JPanel();
contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
contentPane.setLayout(null);
setContentPane(contentPane);

JLabel titleLabel = new JLabel("Tests Pabeigts!");
titleLabel.setFont(new Font("Tahoma", Font.BOLD, 28));
titleLabel.setBounds(350, 20, 250, 40);
contentPane.add(titleLabel);

JLabel scoreLabel = new JLabel("Jūsu kopējais punktu skaits: " + finalScore);
scoreLabel.setFont(new Font("Tahoma", Font.PLAIN, 22));
scoreLabel.setBounds(100, 70, 400, 30);
contentPane.add(scoreLabel);

JLabel correctLabel = new JLabel("Pareizas atbildes: " + correctCount);
correctLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
correctLabel.setBounds(100, 110, 300, 25);
contentPane.add(correctLabel);

JLabel incorrectLabel = new JLabel("Nepareizas atbildes: " + incorrectCount);
incorrectLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
incorrectLabel.setBounds(100, 140, 300, 25);
contentPane.add(incorrectLabel);

JLabel detailsTitleLabel = new JLabel("Detalizēti rezultāti:");
detailsTitleLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
detailsTitleLabel.setBounds(50, 190, 300, 30);
contentPane.add(detailsTitleLabel);

JTextArea resultsTextArea = new JTextArea();
resultsTextArea.setEditable(false);
resultsTextArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
resultsTextArea.setBackground(new Color(240, 240, 240));
resultsTextArea.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

StringBuilder details = new StringBuilder();

for (int i = 0; i < allQuestionTexts.size(); i++) {
   String questionText = allQuestionTexts.get(i);
   String optionA = allOptionAs.get(i);
   String optionB = allOptionBs.get(i);
   String optionC = allOptionCs.get(i);
   String optionD = allOptionDs.get(i);
   String userAnswerLetter = (i < allUserAnswers.size()) ? allUserAnswers.get(i) : "N/A";
   String correctAnswerLetter = allCorrectAnswers.get(i);

   String status = "N/A";
   String userAnswerText = "";
   String correctAnswerText = "";

   if (!userAnswerLetter.equals("N/A")) {
       status = userAnswerLetter.equalsIgnoreCase(correctAnswerLetter) ? "PAREIZI" : "NEPAREIZI";
       userAnswerText = getOptionText(userAnswerLetter, optionA, optionB, optionC, optionD);
       correctAnswerText = getOptionText(correctAnswerLetter, optionA, optionB, optionC, optionD);
   }

   details.append("Jautājums ").append(i + 1).append(": ").append(questionText).append("\n");
   details.append("  A) ").append(optionA).append("\n");
   details.append("  B) ").append(optionB).append("\n");
   details.append("  C) ").append(optionC).append("\n");
   details.append("  D) ").append(optionD).append("\n");
   details.append("  Jūsu atbilde: ").append(userAnswerLetter);
   details.append("\n  Statuss: ").append(status);

   if (!userAnswerLetter.equalsIgnoreCase(correctAnswerLetter) && !userAnswerLetter.equals("N/A")) {
       details.append(" | Pareizā atbilde: ").append(correctAnswerLetter);
   }
   details.append("\n\n");
}

resultsTextArea.setText(details.toString());

JScrollPane scrollPane = new JScrollPane(resultsTextArea);
scrollPane.setBounds(50, 230, 800, 350);
contentPane.add(scrollPane);

JButton backButton = new JButton("Atpakaļ");
backButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
backButton.setBounds(350, 600, 180, 40);
contentPane.add(backButton);

backButton.addActionListener(new ActionListener() {
   @Override
   public void actionPerformed(ActionEvent e) {
       dispose();
       Sakums.getFrame().setVisible(true);
   }
});

saveResultsToFile(finalScore, correctCount, incorrectCount,
                 allQuestionTexts, allOptionAs, allOptionBs, allOptionCs, allOptionDs,
                 allUserAnswers, allCorrectAnswers);
}

private String getOptionText(String optionLetter, String optionA, String optionB, String optionC, String optionD) {
switch (optionLetter.toUpperCase()) {
   case "A": return optionA;
   case "B": return optionB;
   case "C": return optionC;
   case "D": return optionD;
   default: return "";
}
}

private void saveResultsToFile(int finalScore, int correctCount, int incorrectCount,
                          List<String> allQuestionTexts,
                          List<String> allOptionAs, List<String> allOptionBs, List<String> allOptionCs, List<String> allOptionDs,
                          List<String> allUserAnswers, List<String> allCorrectAnswers) {
try (BufferedWriter bw = new BufferedWriter(new FileWriter(RESULTS_FILE, true))) {
   bw.write("----------- Testa Rezultāti -----------");
   bw.newLine();
   bw.write("Kopējais punktu skaits: " + finalScore);
   bw.newLine();
   bw.write("Pareizas atbildes: " + correctCount);
   bw.newLine();
   bw.write("Nepareizas atbildes: " + incorrectCount);
   bw.newLine();
   bw.write("---------------------------------------");
   bw.newLine();

   bw.write("Detalizēti rezultāti:");
   bw.newLine();
   bw.write("--------------------------------------------------------------------------------------------------------------------");
   bw.newLine();

   for (int i = 0; i < allQuestionTexts.size(); i++) {
       String questionText = allQuestionTexts.get(i);
       String optionA = allOptionAs.get(i);
       String optionB = allOptionBs.get(i);
       String optionC = allOptionCs.get(i);
       String optionD = allOptionDs.get(i);
       String userAnswerLetter = (i < allUserAnswers.size()) ? allUserAnswers.get(i) : "N/A";
       String correctAnswerLetter = allCorrectAnswers.get(i);

       String status = "N/A";
       String userAnswerText = "";
       String correctAnswerText = "";

       if (!userAnswerLetter.equals("N/A")) {
           status = userAnswerLetter.equalsIgnoreCase(correctAnswerLetter) ? "PAREIZI" : "NEPAREIZI";
           userAnswerText = getOptionText(userAnswerLetter, optionA, optionB, optionC, optionD);
           correctAnswerText = getOptionText(correctAnswerLetter, optionA, optionB, optionC, optionD);
       }

       bw.write("Jautājums " + (i + 1) + ": " + questionText);
       bw.newLine();
       bw.write("  A) " + optionA);
       bw.newLine();
       bw.write("  B) " + optionB);
       bw.newLine();
       bw.write("  C) " + optionC);
       bw.newLine();
       bw.write("  D) " + optionD);
       bw.newLine();
       bw.write("  Jūsu atbilde: " + userAnswerLetter);
       bw.newLine();
       bw.write("  Statuss: " + status);

       if (!userAnswerLetter.equalsIgnoreCase(correctAnswerLetter) && !userAnswerLetter.equals("N/A")) {
           bw.write(" | Pareizā atbilde: " + correctAnswerLetter);
       }
       bw.newLine();
       bw.newLine();
   }
   bw.newLine();
} catch (IOException e) {
   JOptionPane.showMessageDialog(this, "Kļūda, saglabājot rezultātus failā: " + e.getMessage(), "Faila Saglabāšanas Kļūda", JOptionPane.ERROR_MESSAGE);
   e.printStackTrace();
}
}

}
