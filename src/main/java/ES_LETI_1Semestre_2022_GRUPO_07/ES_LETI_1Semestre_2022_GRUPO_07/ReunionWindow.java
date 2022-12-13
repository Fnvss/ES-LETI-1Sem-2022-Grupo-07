package ES_LETI_1Semestre_2022_GRUPO_07.ES_LETI_1Semestre_2022_GRUPO_07;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

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
	TimeOfDay timeOfDay;
	String time;
	int durationTime = 0;
	JXTextField duration;
	JXTextField periodicity;
	List<Element> elementsReunion = new ArrayList<>();
	int periodicityTime = 0;

	ReunionWindow(Schedule schedule) {

		/**
		 * Exit_on_close for the frame
		 */
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		/*
		 * Set up the panel
		 */
		contentPane.setOpaque(true);
		contentPane.setBackground(Color.GRAY);
		contentPane.setLayout(null);

		/**
		 * Set MainText size, font and location
		 */
		label.setBounds(170, 10, 200, 90);
		label.setFont(new Font("Calibri", Font.BOLD, 18));

		/*
		 * Sets the size and location of the button to go to the previous window
		 */
		backButton.setBounds(20,10,70,30);


		/*
		 * Set duration text box size and location, allowing only integers in the text box
		 */
		duration  = new JXTextField();
		PlainDocument filter = (PlainDocument) duration.getDocument();
		filter.setDocumentFilter(new IntegerFilter());
		duration.setBounds(95, 120, 100, 30);

		/*
		 * Set text prompt in the reunion text box.
		 */
		duration.setPrompt("Reunion duration");
		duration.setPromptForeground(Color.DARK_GRAY);

		/*
		 * Set info for duration box size and location.
		 */
		dateLabel.setBounds(40, 120, 300, 30);

		/*
		 * Creates a ComboBox to select with the TimeOfDay enum, for the user setect which time he wants to appoint the reunion.
		 */
		timeOfDayCombo.setModel(new DefaultComboBoxModel(TimeOfDay.values()));
		timeOfDayCombo.setRenderer(new MyComboBoxRenderer("Time"));
		timeOfDayCombo.setSelectedIndex(-1);
		((JLabel)timeOfDayCombo.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

		/*
		 * Set size and location of the ComboBox.
		 */
		timeOfDayCombo.setBounds(95, 160, 100, 30);

		/*
		 * Creates a List of elements to be selected for the reunion, with the possibility of multiple selections.
		 */
		list = new JList(schedule.getElements().toArray());
		list.setFixedCellHeight(15);
		list.setFixedCellWidth(100);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setVisibleRowCount(4);
		Dimension dimension = list.getPreferredSize();

		/*
		 * Set size and location of the list of elements.
		 */
		list.setBounds(265, 120, dimension.width, dimension.height);
		list.setVisibleRowCount(4);
		list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

		/*
		 * Set duration text box size and location, allowing only integers in the text box
		 */
		periodicity  = new JXTextField();
		PlainDocument filter2 = (PlainDocument) periodicity.getDocument();
		filter2.setDocumentFilter(new IntegerFilter());
		periodicity.setBounds(265, 160, 100, 30);

		/*
		 * Set text prompt in the reunion text box.
		 */
		periodicity.setPrompt("Periodicity");
		periodicity.setPromptForeground(Color.DARK_GRAY);


		/*
		 * Set size and location of the submit button.
		 */
		submitButton.setBounds(30,220,200,30);

		submitPeriodicButton.setBounds(260, 220, 200, 30);



		/*
		 * Put objects into the panel
		 */
		contentPane.add(label);
		contentPane.add(timeOfDayCombo);
		contentPane.add(backButton);
		contentPane.add(duration);
		contentPane.add(submitButton);
		contentPane.add(submitPeriodicButton);
		contentPane.add(list);
		contentPane.add(dateLabel);
		contentPane.add(periodicity);

		/*
		 * Create frame
		 */
		frame.setContentPane(contentPane);
		frame.setSize(500, 350);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		/*
		 * ActionListener for enter
		 */
		backButton.addActionListener(this);
		submitButton.addActionListener(this);
		submitPeriodicButton.addActionListener(this);

	}

	/*
	 * Creates a title for the ComboBox.
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

	@Override
	public void actionPerformed(ActionEvent ae) {
		/*
		 * When the backButton it's pressed it goes back to the login window.
		 */
		if(ae.getActionCommand() == backButton.getActionCommand()) {
			frame.setVisible(false);
			Login.getInstance().getFrameInstance().setVisible(true);		
		} else {

			durationTime = Integer.parseInt(duration.getText());
			timeOfDay = (TimeOfDay) timeOfDayCombo.getSelectedItem();
			
			List<Element> elementsReunion = new ArrayList<>();
			Object[] elements = list.getSelectedValues();
			for(int i = 0; i < list.getSelectedValues().length; i++) {
				elementsReunion.add((Element) elements[i]);
			}

			/*
			 * When the submitButton is pressed, it starts searching for a date for the reunion, with the inputs received on the reunion window
			 */

			if(ae.getActionCommand() == submitButton.getActionCommand()) {
				if(!elementsReunion.isEmpty()) {
					System.out.println(Login.schedule.checkAvailableDate(elementsReunion, timeOfDay, durationTime));
				} else {
					JOptionPane.showMessageDialog(null," No elements inserted!");
				}
			}
			if(ae.getActionCommand() == submitPeriodicButton.getActionCommand()) {
				periodicityTime = Integer.parseInt(periodicity.getText());
				if(!elementsReunion.isEmpty()) {
					System.out.println(Login.schedule.periodicReunion(elementsReunion, timeOfDay, durationTime, periodicityTime));
				} else {
					JOptionPane.showMessageDialog(null," No elements inserted!");
				}

			}
		}
	}

}

