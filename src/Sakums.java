import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.net.URL; 

public class Sakums extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	static Sakums frame = new Sakums();
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
	public Sakums() {
		
		setIconImage(new ImageIcon(getClass().getResource("javaLogo.png")).getImage());
		
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 690, 436);
		setTitle("Sākuma logs");
		
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("Sākt");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				frame.setVisible(false);
	            JautajumuLogs.getFrame().setVisible(true);
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 26));
		
		btnNewButton.setBounds(235, 336, 196, 50);
		btnNewButton.setBackground(new Color(229, 229, 229));
		contentPane.add(btnNewButton);
		
		JLabel lblNewLabel = new JLabel("Teksta Datnes Tests");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 42));
		lblNewLabel.setBounds(141, 240, 386, 41);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("");
		
		
		URL java1ImageUrl = getClass().getResource("java1.png");
		if (java1ImageUrl != null) {
		    lblNewLabel_1.setIcon(new ImageIcon(java1ImageUrl));
		} else {
		    System.out.println("Brīdinājums: Attēls 'java1.png' nav atrasts kā resurss.");
		   
		}
		
		lblNewLabel_1.setBounds(118, 0, 426, 234);
		contentPane.add(lblNewLabel_1);
	}

	public static Sakums getFrame() {
        return frame;
    }

    public static void setFrame(Sakums frame) {
        Sakums.frame = frame;
    }
}