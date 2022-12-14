package ES_LETI_1Semestre_2022_GRUPO_07.ES_LETI_1Semestre_2022_GRUPO_07;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;

import javax.imageio.ImageIO;
import javax.swing.*;

import org.jdesktop.swingx.JXTextField;

import net.fortuna.ical4j.data.ParserException;

/**
 * The Login class is used to create a login menu for a meeting planner.
 * It extends the ActionListener interface, so that it can receive notifications
 * of events that occur when an action is performed.
 *
 * The Login class has several instance variables, including JFrame, JXTextField,
 * JPanel, JLabel, and JButton objects. It also includes a static Schedule object
 * and an instance object of the Login class itself.
 *
 * The Login class has a constructor that sets up and displays a window with
 * several components, such as text fields and buttons, inside of it. The
 * constructor also adds action listeners to the buttons, so that the buttons can
 * perform an action when clicked. When an action is performed, the actionPerformed
 * method is called. This method handles the events that occur when the buttons
 * are clicked.
 */
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

	/**
	 * Instance object that creates a new login menu if it doesn't exist
	 */
	public static Login getInstance() {
		if(instance == null) 
			instance = new Login();
		return instance;
	}

	/**
	 * Constructor for the Login class.
	 * Sets up and displays a JFrame window with a JPanel inside of it.
	 * The JPanel contains several JLabel, JTextField, and JButton components.
	 * The default close operation of the JFrame is set to EXIT_ON_CLOSE,
	 * and an icon image is set for the window.
	 * Action listeners are added to the buttons.
	 */
	public Login() {

		// Exit_on_close for the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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

		 // Set MainText size, font and location
		label.setFont(new Font("Calibri", Font.BOLD, 24));
		label.setBounds(93, 0, 300, 100);

		// Set name text box size and location
		name.setBounds(95, 90, 300, 30);
		name.setPrompt("Insert a name");
		name.setPromptForeground(Color.DARK_GRAY);

		// Set url text box size and location
		url.setBounds(95, 130, 300, 30);
		url.setPrompt("Insert an Url");
		url.setPromptForeground(Color.DARK_GRAY);

		 // Set size and location of the calendar button
		calendarButton.setBounds(95, 180, 130, 30);

		// Set button size and location of the add member button
		addMemberButton.setBounds(265, 180, 130, 30);

		// Set button size and location of the reunion button
		reunionButton.setBounds(95, 230, 130, 30);

		// Set button size and location of the members list button
		seeMembersList.setBounds(265, 230, 130, 30);

		// Put objects into the panel
		contentPane.add(label);
		contentPane.add(addMemberButton);
		contentPane.add(calendarButton);
		contentPane.add(reunionButton);
		contentPane.add(name);
		contentPane.add(url);
		contentPane.add(seeMembersList);

		// Create frame
		frame.setContentPane(contentPane);
		frame.setSize(500, 350);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		// ActionListener for enter
		addMemberButton.addActionListener(this);
		calendarButton.addActionListener(this);
		reunionButton.addActionListener(this);
		seeMembersList.addActionListener(this);

	}

	/**
	 * Called when an action is performed on a component.
	 *
	 * @param ae the action event object
	 */
	@Override
	public void actionPerformed(ActionEvent ae) {

		/*
		 * it opens the calendar window when the calendar button its pressed.
		 */
		if(ae.getActionCommand() == calendarButton.getActionCommand()) {
			if(schedule.getElements().size() == 0) {
				JOptionPane.showMessageDialog(null, "No elements inserted!");
			} else {
				CalendarWindow window = new CalendarWindow();
				frame.dispose();
			}
		}

		/*
		 * it creates an element, if it doesn't exist already, with the name and url received in the JTextFields.
		 */
		if(ae.getActionCommand() == addMemberButton.getActionCommand()) {
			if(name.getText().isEmpty() || url.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Please insert a name/ url");
				return;
			}
			String elementName = name.getText();
			String elementUrl = url.getText();
			URL aux;
			try {
				aux = new URL(elementUrl);
			} catch (MalformedURLException e) {
				JOptionPane.showMessageDialog(frame, "Error: Invalid URL.", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}


			for(Element e: schedule.getElements()) {
				if(e.getWebLink().equals(elementUrl)) {
					JOptionPane.showMessageDialog(null, "The Url Already Exists!");
					return;
				}
			}

			Element element = new Element(elementName, elementUrl);
			
			if(schedule.getElements().indexOf(element) != -1) {
				JOptionPane.showMessageDialog(null, "The inserted element Already Exists!");
				return;
			}

			try {
				schedule.addElementToSchedule(element);
				JOptionPane.showMessageDialog(null, elementName+ " has been registered!");
			} catch (IOException | ParserException | ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error: Incorrect configuration for element.", "Error", JOptionPane.ERROR_MESSAGE);
				
			}

			name.setText(null);
			url.setText(null);
		}

		/*
		 * It opens the reunion window when the reunion button its pressed.
		 */
		if(ae.getActionCommand() == reunionButton.getActionCommand()) {
			if(schedule.getElements().size() >= 1) {
				frame.setVisible(false);
				ReunionWindow newWindow = new ReunionWindow(schedule);		
			} else {
				JOptionPane.showMessageDialog(null,"Insert elements first!");
			}

		}

		/*
		 * It opens a list of all elements already inserted.
		 */
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