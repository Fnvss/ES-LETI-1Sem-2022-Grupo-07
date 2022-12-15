package ES_LETI_1Semestre_2022_GRUPO_07.ES_LETI_1Semestre_2022_GRUPO_07;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.text.PlainDocument;

import org.jdesktop.swingx.JXTextField;

/**
 * The ReunionWindow class represents a window for scheduling reunions.
 * It extends the JFrame class and contains a panel, a label, a button,
 * a text field, a combo box, a list, and several other components.
 */
public class ReunionWindow implements ActionListener {

	JFrame frame = new JFrame();
	JPanel contentPane = new JPanel();
	JComboBox<String> timeOfDayCombo = new JComboBox();;
	JLabel label = new JLabel("Set up your reunion");
	JButton backButton = new JButton("Back");
	JButton submitButton = new JButton("Submit Reunion");
	JButton submitPeriodicButton = new JButton("Submit Periodic Reunion");
	JList list;
	JLabel dateLabel = new JLabel("Duration: ");
	JLabel reunionsLabel = new JLabel("Nº Reunions:");
	TimeOfDay timeOfDay;
	String time;
	JXTextField duration = new JXTextField();
	JXTextField periodicity;
	JXTextField numberOfReunions;
	List<Element> elementsReunion = new ArrayList<>();
	int durationTime = 0;
	int periodicityTime = 0;
	int number = 0;

	/**
	 * Constructs a new ReunionWindow with the specified schedule.
	 *
	 * @param schedule the schedule to be used for the reunion window
	 */
	ReunionWindow(Schedule schedule) {

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
		label.setBounds(170, 10, 200, 90);
		label.setFont(new Font("Calibri", Font.BOLD, 18));

		// Sets the size and location of the button to go to the previous window
		backButton.setBounds(20,10,70,30);

		// Set duration text box size and location, allowing only integers in the text box
		PlainDocument filter = (PlainDocument) duration.getDocument();
		filter.setDocumentFilter(new IntegerFilter());
		duration.setBounds(95, 120, 100, 30);

		// Set text prompt in the reunion text box.
		duration.setPrompt("Minutes");
		duration.setPromptForeground(Color.DARK_GRAY);
		duration.setHorizontalAlignment(SwingConstants.CENTER);

		// Set info for duration box size and location.
		dateLabel.setBounds(40, 120, 300, 30);

		/* 
		 * Creates a ComboBox to select with the TimeOfDay enum,
		 * for the user setect which time he wants to appoint the reunion.
		 */
		timeOfDayCombo.setModel(new DefaultComboBoxModel(TimeOfDay.values()));
		timeOfDayCombo.setRenderer(new MyComboBoxRenderer("Time"));
		timeOfDayCombo.setSelectedIndex(-1);
		((JLabel)timeOfDayCombo.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

		// Set size and location of the ComboBox.
		timeOfDayCombo.setBounds(265, 210, 100, 30);

		// Creates a List of elements to be selected for the reunion, with the possibility of multiple selections.
		list = new JList(schedule.getElements().toArray());
		list.setFixedCellHeight(15);
		list.setFixedCellWidth(100);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setVisibleRowCount(4);
		Dimension dimension = list.getPreferredSize();

		// Set size and location of the list of elements.
		list.setBounds(265, 120, dimension.width, dimension.height);
		list.setVisibleRowCount(4);
		list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

		// Set duration text box size and location, allowing only integers in the text box
		numberOfReunions = new JXTextField();
		PlainDocument filter2 = (PlainDocument) numberOfReunions.getDocument();
		filter2.setDocumentFilter(new IntegerFilter());
		numberOfReunions.setBounds(95, 165, 100, 30);

		// Set text prompt in the reunion text box.
		numberOfReunions.setPrompt("Limit");
		numberOfReunions.setPromptForeground(Color.DARK_GRAY);
		numberOfReunions.setHorizontalAlignment(SwingConstants.CENTER);
		
		// Set info for duration box size and location.
		reunionsLabel.setBounds(20, 165, 100, 30);

		// Set duration text box size and location, allowing only integers in the text box
		periodicity  = new JXTextField();
		PlainDocument filter3 = (PlainDocument) periodicity.getDocument();
		filter3.setDocumentFilter(new IntegerFilter());
		periodicity.setBounds(95, 210, 100, 30);
		

		// Set text prompt in the reunion text box.
		periodicity.setPrompt("Periodicity");
		periodicity.setPromptForeground(Color.DARK_GRAY);
		periodicity.setHorizontalAlignment(SwingConstants.CENTER);


		// Set size and location of the submit button.
		submitButton.setBounds(30,250,200,30);

		submitPeriodicButton.setBounds(260, 250, 200, 30);

		// Put objects into the panel
		contentPane.add(label);
		contentPane.add(backButton);
		contentPane.add(duration);
		contentPane.add(dateLabel);
		contentPane.add(timeOfDayCombo);
		contentPane.add(list);
		contentPane.add(numberOfReunions);
		contentPane.add(reunionsLabel);
		contentPane.add(periodicity);
		contentPane.add(submitButton);
		contentPane.add(submitPeriodicButton);

		// Create frame
		frame.setContentPane(contentPane);
		frame.setSize(500, 350);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		// ActionListener for enter
		backButton.addActionListener(this);
		submitButton.addActionListener(this);
		submitPeriodicButton.addActionListener(this);

	}

	/**
	 * Returns the component that will be used to render the title
	 * in the combo box.
	 *
	 * @param list the JList component that the renderer is being used for
	 * @param value the value of the item to be rendered
	 * @param index the index of the item to be rendered
	 * @param isSelected a boolean value indicating whether the item is currently selected
	 * @param hasFocus a boolean value indicating whether the item currently has focus
	 * @return the component that will be used to render the title
	 */
	class MyComboBoxRenderer extends JLabel implements ListCellRenderer
	{
		private String _title;

		public MyComboBoxRenderer(String title)
		{
			_title = title;
		}

		@Override
		public Component getListCellRendererComponent(JList list, Object value,
				int index, boolean isSelected, boolean hasFocus)
		{
			if (index == -1 && value == null) setText(_title);
			else setText(value.toString());
			return this;
		}
	}

	/**
	 * Called when an action is performed on a component.
	 *
	 * @param ae the action event object
	 */
	@Override
	public void actionPerformed(ActionEvent ae) {

		// When the backButton it's pressed it goes back to the login window.
		if(ae.getActionCommand() == backButton.getActionCommand()) {
			frame.setVisible(false);
			Login.getInstance().getFrameInstance().setVisible(true);		
		} else {

			
			timeOfDay = (TimeOfDay) timeOfDayCombo.getSelectedItem();

			List<Element> elementsReunion = new ArrayList<>();
			Object[] elements = list.getSelectedValues();
			for(int i = 0; i < list.getSelectedValues().length; i++) {
				elementsReunion.add((Element) elements[i]);
			}

			/*
			 * When the submitButton is pressed, it starts searching for a date for the reunion, 
			 * with the inputs received on the reunion window.
			 */
			if(ae.getActionCommand() == submitButton.getActionCommand()) {
				if(!elementsReunion.isEmpty() && !(duration.getText().equals("")) && timeOfDay != null) {
					try {
						durationTime = Integer.parseInt(duration.getText());
						Login.schedule.checkAvailableDate(elementsReunion, timeOfDay, durationTime);
						JOptionPane.showMessageDialog(null, "Sucess! Reunion registered");
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(null,"Insert all inputs!");
				}
			}

			/*
			 * When the submitPeriodicButton is pressed, it starts searching for available dates for the periodic reunion, 
			 * with the inputs received on the reunion window.
			 */			
			if(ae.getActionCommand() == submitPeriodicButton.getActionCommand()) {
			
				if (!numberOfReunions.getText().equals("") && !periodicity.getText().equals("") && !elementsReunion.isEmpty()
					&& timeOfDay != null && !duration.getText().equals("")) {
					try {
						periodicityTime = Integer.parseInt(periodicity.getText());
						number = Integer.parseInt(numberOfReunions.getText());
						durationTime = Integer.parseInt(duration.getText());
						Login.schedule.periodicReunion(elementsReunion, timeOfDay, durationTime, periodicityTime, number);
						JOptionPane.showMessageDialog(null, "Sucess! Periodic reunion registered");
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(null,"Insert all inputs!");
				}

			}
		}
	}

}

