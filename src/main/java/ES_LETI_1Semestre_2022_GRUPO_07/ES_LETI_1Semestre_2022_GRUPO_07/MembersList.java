package ES_LETI_1Semestre_2022_GRUPO_07.ES_LETI_1Semestre_2022_GRUPO_07;

import java.awt.Color;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * A GUI that displays a list of elements in a schedule.
 *
 */
public class MembersList {

	JFrame frame = new JFrame("List");
	JPanel contentPane = new JPanel();
	JLabel label;
	JLabel displayLabel;
	int j = 30;

	/**
	 * Constructs a new MembersList GUI that displays the names of all elements in the specified schedule.
	 * 
	 * @param schedule The schedule whose elements will be displayed in the MembersList GUI.
	 */
	MembersList(Schedule schedule) {
		
		
		 // Set up the panel
		contentPane.setOpaque(true);
		contentPane.setBackground(new Color(243,242,242));
		contentPane.setLayout(null);
		
		/*
         * Load the Icon image
         * Then sets the icon image of the JFrame
         */
        Image iconImage = null;
        try {
            iconImage = ImageIO.read(new File("icon.png"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        frame.setIconImage(iconImage); 

        // set up displayLabel
		displayLabel = new JLabel("Elements: ");
		displayLabel.setBounds(85, 15, 120, 30);
		contentPane.add(displayLabel);
		
		/*
		 * The code in this loop creates a new JLabel for each element in the schedule and sets its text to the name of the element. 
		 * The label is then added to the content pane of the frame, 
		 * and the vertical position of the next label is increased by 15 pixels so that each label is displayed on a new line. 
		 * This loop allows the members list GUI to display the name of each element in the schedule.
		 */		
		for(int i = 0; i < schedule.getElements().size(); i++) {
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
