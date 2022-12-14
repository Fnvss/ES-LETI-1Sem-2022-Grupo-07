package ES_LETI_1Semestre_2022_GRUPO_07.ES_LETI_1Semestre_2022_GRUPO_07;

import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.text.ParseException;
import java.util.Collections;
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
import javax.swing.SwingConstants;

import ES_LETI_1Semestre_2022_GRUPO_07.ES_LETI_1Semestre_2022_GRUPO_07.ReunionWindow.MyComboBoxRenderer;
import net.fortuna.ical4j.data.ParserException;

/**
 * CalendarWindow is a class that creates a graphical user interface (GUI),
 * that gives the user the choice to select what type of calendar to be see.
 * The class extends the ActionListener class to handle user input and events.
 * The class also has a static instance field that allows it to be accessed by other classes.
 */
public class CalendarWindow implements ActionListener {
	JFrame frame = new JFrame();
	JPanel contentPane = new JPanel();
	JButton calendarButton = new JButton("All elements");
	JComboBox<Element> membersCombo = new JComboBox<Element>();
	JLabel label = new JLabel("Which Calendar you want to see");
	JButton backButton = new JButton("Back");
	JButton openFile = new JButton("Open Calendar txt");
	JComboBox<Element> membersComboToTxt = new JComboBox<Element>();

	public static CalendarWindow instance;

	/**
	 * Instance object that creates a new login menu if it doesn't exist
	 */
	public static CalendarWindow getInstance() {
		if(instance == null) 
			instance = new CalendarWindow();
		return instance;
	}

	/**
	 * Creates a new CalendarWindow UI and initializes the Swing components.
	 * Sets the properties of the frame, panel, and components, including the
	 * size, location, and layout. Adds action listeners to the buttons to
	 * handle user input.
	 */
	CalendarWindow() {
		// Exit_on_close for the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//Set up the panel
		contentPane.setOpaque(true);
		contentPane.setBackground(new Color(243,242,242));
		contentPane.setLayout(null);


		//Load the Icon image   
		Image iconImage = null;
		try {
			iconImage = ImageIO.read(new File("icon.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Set the icon image of the JFrame
		frame.setIconImage(iconImage); 


		// Set MainText size, font and location
		label.setBounds(120, 20, 250, 90);
		label.setFont(new Font("Calibri", Font.BOLD, 18));

		//Set buttons size and location
		openFile.setBounds(280, 150, 150, 30);
		calendarButton.setBounds(280, 200, 150, 30);

		//Loop through the elements list and add to the ComboBox
		for(Element e: Login.schedule.getElements()) {
			membersComboToTxt.addItem((Element) e);
			membersCombo.addItem((Element) e);
		}

		//Set up the membersCombo
		membersCombo.setRenderer(new MyComboBoxRenderer("Elements Calendar"));
		membersCombo.setSelectedIndex(-1);
		((JLabel)membersCombo.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

		//Set membersComboBox's size and location
		membersCombo.setBounds(70, 200, 150,30);
		membersComboToTxt.setBounds(70, 150, 150,30);

		//Sets the size and location of the button to go to the previous window
		backButton.setBounds(20,10,70,30);

		//Add  Buttons and ComboBox's to panel
		contentPane.add(label);		
		contentPane.add(membersCombo);
		contentPane.add(membersComboToTxt);
		contentPane.add(calendarButton);
		contentPane.add(backButton);
		contentPane.add(openFile);

		//Create frame
		frame.setContentPane(contentPane);
		frame.setSize(500, 350);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		//Add ActionListeners
		membersComboToTxt.addActionListener(this);
		calendarButton.addActionListener(this);
		membersCombo.addActionListener(this);
		backButton.addActionListener(this);
		openFile.addActionListener(this);

	}

	/**
	 * A custom ListCellRenderer that extends JLabel and is used to render the
	 * cells of a JComboBox. It sets the text of the JLabel to either the title
	 * of the JComboBox or the value of the cell being rendered, depending on
	 * the index of the cell.
	 */
	class MyComboBoxRenderer extends JLabel implements ListCellRenderer{
		private String _title;


		/**
		 * Creates a new MyComboBoxRenderer with the given title.
		 *
		 * @param title the title to use for the JComboBox
		 */
		public MyComboBoxRenderer(String title)
		{
			_title = title;
		}


		/**
		 * Returns the JLabel used to render the cell at the given index in the
		 * JComboBox. Sets the text of the JLabel to either the title of the
		 * JComboBox or the value of the cell being rendered, depending on the
		 * index of the cell.
		 *
		 * @param list the JList that contains the cell being rendered
		 * @param value the value of the cell being rendered
		 * @param index the index of the cell being rendered in the JList
		 * @param isSelected true if the cell is selected, false otherwise
		 * @param hasFocus true if the cell has focus, false otherwise
		 * @return the JLabel used to render the cell
		 */
		@Override
		public Component getListCellRendererComponent(JList list, Object value,
				int index, boolean isSelected, boolean hasFocus){
			if (index == -1 && value == null) setText(_title);
			else setText(value.toString());
			return this;
		}
	}


	/**
	 * Handles user input in the UI. Checks the source of the ActionEvent and
	 * takes different actions based on the source.
	 *
	 * @param ae the ActionEvent to handle
	 */
	@Override
	public void actionPerformed(ActionEvent ae) {

		/*
		 * Reads the selected item from the combo box, 
		 * creates a new Schedule object using the readCalendar(),
		 * sorts the events in the schedule, and writes them to a text file.
		 */
		if(ae.getSource() == membersComboToTxt) {
			try {
				String aux = membersComboToTxt.getSelectedItem().toString();
				String file = aux + ".txt";
				Schedule schedule = Login.schedule;
				Schedule newSchedule = schedule.readCalendar((Element) membersComboToTxt.getSelectedItem());
				List<Event> list = newSchedule.getEvents();
				Collections.sort(list);

				PrintStream out = new PrintStream(file);
				System.setOut(out);

				for(Event e : list) {
					System.out.println(e.toString());
				}
				// Abre o ficheiro txt especificado
				Desktop.getDesktop().open(new File(file));
			} catch (IOException ex) {

				JOptionPane.showMessageDialog(null, "Erro ao abrir o ficheiro: " + ex.getMessage(),
						"Erro", JOptionPane.ERROR_MESSAGE);
			}
		}


		//Opens a predefined text file.
		if(ae.getActionCommand() == openFile.getActionCommand()) {
			try {
				// Obtém o caminho para o ficheiro txt dentro do projeto
				String caminho = "Horário.txt";
				// Abre o ficheiro txt especificado
				Desktop.getDesktop().open(new File(caminho));
			} catch (IOException ex) {

				JOptionPane.showMessageDialog(null, "Erro ao abrir o ficheiro: " + ex.getMessage(),
						"Erro", JOptionPane.ERROR_MESSAGE);
			}
		}


		//Hides the current window and shows the login window.
		if(ae.getActionCommand() == backButton.getActionCommand()) {
			frame.setVisible(false);
			Login.getInstance().getFrameInstance().setVisible(true);
		}

		//Opens a new calendar view window.
		if(ae.getActionCommand() == calendarButton.getActionCommand()) {
			try {
				frame.dispose();
				CalendarViews.weeklyView(new WeekCalendar(CalendarViews.getListOfEvents()));
			} catch (IOException | ParserException | ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		//Opens a new calendar view window.
		if(ae.getActionCommand() == membersCombo.getActionCommand()) {
			if(membersCombo.getSelectedIndex() != -1) {

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
	}

	/**
	 * Gets frameInstacne
	 * 
	 * @return frame
	 */
	public JFrame getFrameInstance() {
		return frame;
	}
}