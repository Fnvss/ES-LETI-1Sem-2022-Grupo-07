package ES_LETI_1Semestre_2022_GRUPO_07.ES_LETI_1Semestre_2022_GRUPO_07;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class MainMenu extends JFrame {
	private JPanel panel;
	private JButton teamButton;
	private JButton backButton;
	private JButton calendarButton;
	private JPanel panelContainer;
	private JLabel label;

	public MainMenu() {
		createMenu();
		//		panelContainer.add(createSamplePanel("Home Panel "), ""+0);
		//        for(int i=1; i < 10; i++)
		//        {
		//           panelContainer.add(createSamplePanel("Panel "+i), ""+i);
		//        }
		//
	}

	private void createMenu() {
		panel = new JPanel();
		teamButton = new JButton("TEAM");
		backButton = new JButton("Back");
		calendarButton = new JButton("CALENDAR");
		panelContainer = new JPanel(new CardLayout());
		label = new JLabel("MEETING PLANNER", JLabel.CENTER);

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		panel.setBackground(Color.WHITE);
		
		label.setFont(new Font("Calibri", Font.BOLD, 24));
		label.setSize(300, 100);
		label.setLocation(93, 10);
		panel.add(label);

		teamButton.setSize(90,23);
		panel.add(teamButton);

		calendarButton.setSize(90,23);
		panel.add(calendarButton);

		backButton.setSize(90,23);
		panel.add(backButton);

		panelContainer.setPreferredSize(new Dimension(500, 250));
		panelContainer.add(panel);
		
		
	}

	//		public JPanel createSamplePanel(String panelTitle)
	//	    {
	//	        JPanel samplePanel = new JPanel();
	//	        samplePanel.add(new JLabel(panelTitle));
	//	
	//	        return samplePanel;
	//	
	//	    }                                           


	public static void main(String args[]) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new MainMenu().setVisible(true);
			}
		});
	}
}
