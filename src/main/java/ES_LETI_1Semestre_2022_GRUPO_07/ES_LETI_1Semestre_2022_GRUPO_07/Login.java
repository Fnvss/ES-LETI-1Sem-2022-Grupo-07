package ES_LETI_1Semestre_2022_GRUPO_07.ES_LETI_1Semestre_2022_GRUPO_07;

/**
 * @author Filipe
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.*;

import org.jdesktop.swingx.prompt.PromptSupport;

import net.fortuna.ical4j.data.ParserException;

public class Login implements ActionListener {
	JFrame frame = new JFrame();
	JTextField name = new JTextField();
	JTextField url = new JTextField();
	JPanel contentPane = new JPanel();
	JLabel label = new JLabel("MEETING PLANNER", JLabel.CENTER);
	JButton memberButton = new JButton("Add Member");
	JButton calendarButton = new JButton("Calendar");
	Schedule schedule = new Schedule();
	
	
/**
 * Menu principal, contem 2 textfields que recebem o nome e o url do user.
 * Adiciona as informações do user ao Element() e adiciona o element a uma lista de elements
 */
	public Login() {

		//Exit_on_close for the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//Set up the panel
		contentPane.setOpaque(true);
		contentPane.setBackground(Color.LIGHT_GRAY);
		contentPane.setLayout(null);

		//Set MainText size, font and location
		label.setFont(new Font("Calibri", Font.BOLD, 24));
		label.setSize(300, 100);
		label.setLocation(93, 10);

		//Set buttons size and location
		memberButton.setSize(150, 30);
		memberButton.setLocation(245, 220);
		calendarButton.setSize(100,30);
		calendarButton.setLocation(95,220);

		//Set url text box size and location
		url.setSize(300,30);
		url.setLocation(95, 160);

		//Set name text box size and location
		name.setSize(300,30);
		name.setLocation(95, 110);

		//Put objects into the panel
		contentPane.add(label);
		contentPane.add(memberButton);
		contentPane.add(calendarButton);
		contentPane.add(name);
		contentPane.add(url);

		//Create frame
		frame.setContentPane(contentPane);
		frame.setSize(500, 350);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		//ActionListener for enter
		memberButton.addActionListener(this);
		calendarButton.addActionListener(this);
		
		//Placeholder Text
		PromptSupport.setPrompt("Name", name);
		PromptSupport.setPrompt("Url", url);
	}
	
/**
 * 
 */
	@Override
	public void actionPerformed(ActionEvent ae) {
		if(ae.getActionCommand() == memberButton.getActionCommand()) {
			if(name.getText().isEmpty() || url.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Please insert a name/ url");
				return;
			}
				String elementName = name.getText();
				String elementUrl = url.getText();
				Element element = new Element(elementName, elementUrl);
				if(schedule.getElements().indexOf(element) != -1) {
					JOptionPane.showMessageDialog(null, elementName+ " Already Exists!");
					return;
				}
				try {
					schedule.addElement(element);
				} catch (IOException | ParserException | ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					JOptionPane.showMessageDialog(null," error registring schedule!" + e.getCause().toString());
				}
				JOptionPane.showMessageDialog(null, elementName+ " has been registered!");
		}
		

		if(ae.getActionCommand() == calendarButton.getActionCommand()) {
			if(schedule.getElements().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Please insert a member");
			} else {
				try {
					FileWriter writer = new FileWriter(new File("C:\\Users\\Filipe\\Desktop\\lista.txt"));
					for(Element str: schedule.getElements()) {
						writer.write(str + System.lineSeparator());
					}
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				JOptionPane.showMessageDialog(null, "CALENDAR!");
			}
		}
	}
}