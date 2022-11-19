package ES_LETI_1Semestre_2022_GRUPO_07.ES_LETI_1Semestre_2022_GRUPO_07;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class TeamMembers {
	JFrame frame = new JFrame("Team");
	Container panel = new Container();
	JPanel contentPane = new JPanel();
	JButton button = new JButton("+ ADD MEMBERS");

	public TeamMembers() {

		//Exit_on_close for the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//Set up the panel
		contentPane.setOpaque(true);
		contentPane.setBackground(Color.WHITE);
		contentPane.setLayout(null);

		//Set button size and location
		button.setSize(100, 30);
		button.setLocation(195, 220);

		//Put objects into the panel
		contentPane.add(button);

		JMenuBar mb = new JMenuBar();
		mb.add(button);

		//Create frame
		frame.setJMenuBar(mb);
		frame.setContentPane(contentPane);
		frame.setSize(500, 350);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		//ActionListener for enter
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JTextField newTextField = new JTextField("O RUBEN E GAY");
				newTextField.setSize(200,30);
				newTextField.setLocation(10,10);
				
				contentPane.add(newTextField);
				frame.setContentPane(contentPane);
			}
		});
	}
}


