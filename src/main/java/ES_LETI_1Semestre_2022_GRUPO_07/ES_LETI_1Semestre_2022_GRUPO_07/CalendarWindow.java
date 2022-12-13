package ES_LETI_1Semestre_2022_GRUPO_07.ES_LETI_1Semestre_2022_GRUPO_07;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.ParseException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;

import ES_LETI_1Semestre_2022_GRUPO_07.ES_LETI_1Semestre_2022_GRUPO_07.ReunionWindow.MyComboBoxRenderer;
import net.fortuna.ical4j.data.ParserException;

public class CalendarWindow implements ActionListener {
	JFrame frame = new JFrame();
	JPanel contentPane = new JPanel();
	JButton calendarButton = new JButton("All elements");
	JComboBox<Element> membersCombo = new JComboBox<Element>();
	JLabel label = new JLabel("Which Calendar you want to see");
	JButton backButton = new JButton("Back");

	public static CalendarWindow instance;

	/**
	 * Instance object that creates a new login menu if it doesn't exist
	 */

	public static CalendarWindow getInstance() {
		if(instance == null) 
			instance = new CalendarWindow();
		return instance;
	}


	CalendarWindow() {
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
		label.setBounds(120, 20, 250, 90);
		label.setFont(new Font("Calibri", Font.BOLD, 18));

		calendarButton.setBounds(280, 200, 150, 30);

		for(Element e: Login.schedule.getElements()) {
			membersCombo.addItem((Element) e);
		}

		membersCombo.setModel(new DefaultComboBoxModel(TimeOfDay.values()));
		membersCombo.setRenderer(new MyComboBoxRenderer("Element Calendar"));
		membersCombo.setSelectedIndex(-1);
		((JLabel)membersCombo.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

		membersCombo.setBounds(70, 200, 150,30);

		/*
		 * Sets the size and location of the button to go to the previous window
		 */
		backButton.setBounds(20,10,70,30);

		contentPane.add(label);		
		contentPane.add(membersCombo);
		contentPane.add(calendarButton);
		contentPane.add(backButton);
		/*
		 * Create frame
		 */
		frame.setContentPane(contentPane);
		frame.setSize(500, 350);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		calendarButton.addActionListener(this);
		membersCombo.addActionListener(this);
		backButton.addActionListener(this);

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
		}

		if(ae.getActionCommand() == calendarButton.getActionCommand()) {
			try {
				frame.dispose();
				CalendarViews.weeklyView(new WeekCalendar(CalendarViews.getListOfEvents()));
			} catch (IOException | ParserException | ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(ae.getActionCommand() == membersCombo.getActionCommand()) {
			frame.dispose();
			Element selectedElement = (Element) membersCombo.getSelectedItem();
			try {
				CalendarViews.weeklyView(new WeekCalendar(CalendarViews.getListOfEventForElement(selectedElement)));
			} catch (IOException | ParserException | ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}


	public JFrame getFrameInstance() {
		return frame;
	}
}
