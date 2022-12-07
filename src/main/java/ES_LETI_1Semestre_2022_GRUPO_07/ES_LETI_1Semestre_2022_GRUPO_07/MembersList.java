package ES_LETI_1Semestre_2022_GRUPO_07.ES_LETI_1Semestre_2022_GRUPO_07;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MembersList {

	JFrame frame = new JFrame();
	JPanel contentPane = new JPanel();
	JLabel label;
	JLabel displayLabel;
	int j = 30;

	MembersList(Schedule schedule) {
		//Set up the panel
		contentPane.setOpaque(true);
		contentPane.setBackground(Color.GRAY);
		contentPane.setLayout(null);
		displayLabel = new JLabel("Lista de elementos: ");
		displayLabel.setBounds(65, 15, 120, 30);
		contentPane.add(displayLabel);
		
		for(int i = 0; i < schedule.getElements().size(); i++) {
//			teste.add(new JLabel(schedule.getElements().get(i).getName()));
//			teste.setBounds(150, j, 100, 30);
//			contentPane.add(teste);
			label = new JLabel(schedule.getElements().get(i).getName());
			label.setBounds(95, j, 80, 30);
			contentPane.add(label);
			j = j + 15;
		}

		//Create frame
		frame.setContentPane(contentPane);
		frame.setSize(250, 170);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}
