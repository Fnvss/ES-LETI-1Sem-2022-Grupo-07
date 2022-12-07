package ES_LETI_1Semestre_2022_GRUPO_07.ES_LETI_1Semestre_2022_GRUPO_07;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.ParseException;


import javax.swing.*;

import org.jdesktop.swingx.JXTextField;

import net.fortuna.ical4j.data.ParserException;

public class Login implements ActionListener {
	JFrame frame = new JFrame();
	JXTextField name = new JXTextField();
	JXTextField url = new JXTextField();
	JPanel contentPane = new JPanel();
	JLabel label = new JLabel("MEETING PLANNER", JLabel.CENTER);
	JButton addMemberButton = new JButton("Add Member");
	static Schedule schedule = new Schedule();
	JButton calendarButton = new JButton("Calendar");
	JButton seeMembersList = new JButton("Elements List");
	JButton reunionButton = new JButton("Set up a meeting");


	public static Login instance;

	public static Login getInstance() {
		if(instance == null) 
			instance = new Login();
		return instance;
	}


	public Login() {

		//Exit_on_close for the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//Set up the panel
		contentPane.setOpaque(true);
		contentPane.setBackground(Color.GRAY);
		contentPane.setLayout(null);

		//Set MainText size, font and location
		label.setFont(new Font("Calibri", Font.BOLD, 24));
		label.setBounds(93, 0, 300, 100);

		//Set name text box size and location
		name.setBounds(95, 90, 300, 30);
		name.setPrompt("Insert a name");
		name.setPromptForeground(Color.DARK_GRAY);

		//Set url text box size and location
		url.setBounds(95, 130, 300, 30);
		url.setPrompt("Insert an Url");
		url.setPromptForeground(Color.DARK_GRAY);

		//Set size and location of the button
		calendarButton.setBounds(95, 180, 130, 30);

		//Set button size and location
		addMemberButton.setBounds(265, 180, 130, 30);


		reunionButton.setBounds(95, 230, 130, 30);
	

		seeMembersList.setBounds(265, 230, 130, 30);



		//Put objects into the panel
		contentPane.add(label);
		contentPane.add(addMemberButton);
		contentPane.add(calendarButton);
		contentPane.add(reunionButton);
		contentPane.add(name);
		contentPane.add(url);
		contentPane.add(seeMembersList);

		//Create frame
		frame.setContentPane(contentPane);
		frame.setSize(500, 350);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		//ActionListener for enter
		addMemberButton.addActionListener(this);
		calendarButton.addActionListener(this);
		reunionButton.addActionListener(this);
		seeMembersList.addActionListener(this);

	}


	@Override
	public void actionPerformed(ActionEvent ae) {
		if(ae.getActionCommand() == calendarButton.getActionCommand()) {
			schedule.readAllCalendars();

		}

		if(ae.getActionCommand() == addMemberButton.getActionCommand()) {
			if(name.getText().isEmpty() || url.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Please insert a name/ url");
				return;
			}
			String elementName = name.getText();
			String elementUrl = url.getText();
			Element element = new Element(elementName, elementUrl);

			for(Element e: schedule.getElements()) {
				if(e.getUrl().equals(elementUrl)) {
					JOptionPane.showMessageDialog(null, "The Url Already Exists!");
					return;
				}
			}

			if(schedule.getElements().indexOf(element) != -1) {
				JOptionPane.showMessageDialog(null, elementName+ " Already Exists!");
				return;
			}
			try {
				schedule.addElementToSchedule(element);
			} catch (IOException | ParserException | ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				JOptionPane.showMessageDialog(null," error registring schedule!" + e.getCause().toString());
			}
			JOptionPane.showMessageDialog(null, elementName+ " has been registered!");
			name.setText(null);
			url.setText(null);


		}

		if(ae.getActionCommand() == reunionButton.getActionCommand()) {
			frame.dispose();
			ReunionWindow newWindow = new ReunionWindow(schedule);	

		}

		if(ae.getActionCommand() == seeMembersList.getActionCommand()) {
			if(schedule.getElements().size() >= 1) {
				MembersList list = new MembersList(schedule);	
			} else {
				JOptionPane.showMessageDialog(null," No elements inserted!");
			}
		} 

	}

	public JFrame getFrameInstance() {
		return frame;
	}

}