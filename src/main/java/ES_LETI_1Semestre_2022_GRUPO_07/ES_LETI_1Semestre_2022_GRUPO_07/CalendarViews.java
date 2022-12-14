package ES_LETI_1Semestre_2022_GRUPO_07.ES_LETI_1Semestre_2022_GRUPO_07;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;

import ES_LETI_1Semestre_2022_GRUPO_07.ES_LETI_1Semestre_2022_GRUPO_07.MonthlyCalendar.cmbView_Action;
import net.fortuna.ical4j.data.ParserException;

/**
 * CalendarViews is a utility class that provides methods for rendering
 * different views of a calendar in a GUI window.
 */
public class CalendarViews {
	static ArrayList<CalendarEvent> eventsPrincipal = new ArrayList<>();
	static JComboBox cmbDay, cmbWeek;
	static JComboBox membersCombo = new JComboBox();
	static JFrame dayFrm = new JFrame("Calendar");
	static JFrame weekFrm = new JFrame("Calendar");
	static JButton teste = new JButton("Teste");

	/**
	 * Creates a graphical user interface (GUI) for a weekly calendar view.
	 *
	 * @param aux The WeekCalendar object to display in the GUI.
	 * @throws IOException If there is an error reading from the calendar.
	 * @throws ParserException If there is an error parsing the calendar events.
	 * @throws ParseException If there is an error parsing the dates of the calendar events.
	 */
	public static void weeklyView(WeekCalendar aux) throws IOException, ParserException, ParseException {
		
		/*
		 * Create the frame
S		 * Create the combo box
		 */
		weekFrm = new JFrame("Calendar");
		String[] typeOfView = {"Weekly", "Daily", "Monthly"};
		cmbWeek = new JComboBox(typeOfView);


		//The WeekCalendar object to display in the GUI.
		aux.addCalendarEventClickListener(e -> System.out.println(e.getCalendarEvent()));
		aux.addCalendarEmptyClickListener(e -> {
			System.out.println(e.getDateTime());
			System.out.println(Calendar.roundTime(e.getDateTime().toLocalTime(), 30));
		});


		/*
		 * Create the buttons for the calendar control
		 * Set up the buttons
		 */
		JButton goToTodayBtn = new JButton("Today");
		goToTodayBtn.addActionListener(e -> aux.goToToday());
		JButton nextWeekBtn = new JButton(">");
		nextWeekBtn.addActionListener(e -> aux.nextWeek());
		JButton prevWeekBtn = new JButton("<");
		prevWeekBtn.addActionListener(e -> aux.prevWeek());
		JButton nextMonthBtn = new JButton(">>");
		nextMonthBtn.addActionListener(e -> aux.nextMonth());
		JButton prevMonthBtn = new JButton("<<");
		prevMonthBtn.addActionListener(e -> aux.prevMonth());


		//Back button with function to go back to the previous menu
		JButton backBtn = new JButton("Back");
		backBtn.addActionListener(e -> {
			switch (cmbWeek.getSelectedItem().toString()) {
			case "Weekly":
				CalendarWindow.getInstance().getFrameInstance().setVisible(true);
				weekFrm.dispose();
				break;
			}
		});

		//Combo box functionality, changes views depending on the selectedItem
		cmbWeek.addActionListener(e -> {
			switch (cmbWeek.getSelectedItem().toString()) {
			case "Daily":
				try {
					dailyView(new DayCalendar(getListOfEvents(), LocalDate.now()));
				} catch (IOException | ParserException | ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				weekFrm.dispose();
				break;
			case "Monthly":
				try {
					MonthlyCalendar.main(null);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				weekFrm.dispose();
				break;
			}
		});

		// Load the icon image
		Image iconImage = null;
		try {
			iconImage = ImageIO.read(new File("icon.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Set the icon image of the JFrame
		weekFrm.setIconImage(iconImage);


		//Create the Panel to house the calendar controls
		JPanel weekControls = new JPanel();
		weekControls.add(cmbWeek);
		weekControls.add(prevMonthBtn);
		weekControls.add(prevWeekBtn);
		weekControls.add(goToTodayBtn);
		weekControls.add(nextWeekBtn);
		weekControls.add(nextMonthBtn);
		weekControls.add(backBtn);


		/*
		 * Add the panel to the frame.
		 * Set up the frame.
		 * Make the frame visible.
		 */
		weekFrm.add(weekControls, BorderLayout.NORTH);
		weekFrm.add(aux, BorderLayout.CENTER);
		weekFrm.setSize(1600, 1000);
		weekFrm.setLocationRelativeTo(null);
		weekFrm.setVisible(true);
		weekFrm.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}


	/**
	 * Creates a graphical user interface (GUI) for a daily calendar view.
	 *
	 * @param aux The DayCalendar object to display in the GUI.
	 * @throws IOException If there is an error reading from the calendar.
	 * @throws ParserException If there is an error parsing the calendar events.
	 * @throws ParseException If there is an error parsing the dates of the calendar events.
	 */
	public static void dailyView(DayCalendar aux) throws IOException, ParserException, ParseException {
		JPanel dayControls = dayControls(aux);
		//The DayCalendar object to display in the GUI.
		aux.addCalendarEventClickListener(e -> System.out.println(e.getCalendarEvent()));
		aux.addCalendarEmptyClickListener(e -> {
			System.out.println(e.getDateTime());
			System.out.println(Calendar.roundTime(e.getDateTime().toLocalTime(), 30));
		});


		/*
		 * Add the panel to the frame.
		 * Set up the frame.
		 * Make the frame visible.
		 */
		dayFrm.add(dayControls, BorderLayout.NORTH);
		dayFrm.add(aux, BorderLayout.CENTER);
		dayFrm.setLocationRelativeTo(null);
	}


	private static JPanel dayControls(DayCalendar aux)
			throws HeadlessException, IOException, ParserException, ParseException {
		dayFrm = new JFrame("Calendar");
		String[] typeOfView = { "Daily", "Weekly", "Monthly" };
		cmbDay = new JComboBox(typeOfView);
		JButton goToTodayBtn = new JButton("Today");
		goToTodayBtn.addActionListener(e -> aux.goToToday());
		JButton nextDayBtn = new JButton(">");
		nextDayBtn.addActionListener(e -> aux.nextDay());
		JButton prevDayBtn = new JButton("<");
		prevDayBtn.addActionListener(e -> aux.prevDay());
		JButton backBtn = new JButton("Back");
		backBtn.addActionListener(e -> {
			switch (cmbDay.getSelectedItem().toString()) {
			case "Daily":
				CalendarWindow.getInstance().getFrameInstance().setVisible(true);
				dayFrm.dispose();
				break;
			}
		});
		cmbDay.addActionListener(e -> {
			switch (cmbDay.getSelectedItem().toString()) {
			case "Weekly":
				try {
					WeekCalendar weekCal = new WeekCalendar(getListOfEvents());
					weeklyView(weekCal);
				} catch (IOException | ParserException | ParseException e1) {
					e1.printStackTrace();
				}
				dayFrm.dispose();
				break;
			case "Monthly":
				try {
					MonthlyCalendar.main(null);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				dayFrm.dispose();
				break;
			}
		});
		Image iconImage = iconImage();
		dayFrm.setIconImage(iconImage);
		JPanel dayControls = new JPanel();
		dayControls.add(cmbDay);
		dayControls.add(prevDayBtn);
		dayControls.add(goToTodayBtn);
		dayControls.add(nextDayBtn);
		dayControls.add(backBtn);
		dayFrm.setSize(1600, 1000);
		dayFrm.setVisible(true);
		dayFrm.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		return dayControls;
	}


	private static Image iconImage() throws IOException {
		Image iconImage = null;
		iconImage = ImageIO.read(new File("icon.png"));
		return iconImage;
	}

	
	/**
	 * This method creates a list of CalendarEvent objects from a list of Event objects.
	 * It calculates the percentage of elements each Event object has compared to the total number of elements in the schedule,
	 * and uses that percentage to assign a color to each CalendarEvent object.
	 * If the percentage is less than or equal to 25%, the color will be green. 
	 * If it is less than or equal to 50%, the color will be yellow. 
	 * If it is less than or equal to 75%, the color will be orange. 
	 * Otherwise, the color will be red.
	 * 
	 * @return A list of CalendarEvent objects.
	 * @throws IOException If there is an error reading from the schedule.
	 * @throws ParserException If there is an error parsing the schedule.
	 * @throws ParseException If there is an error parsing the dates in the schedule.
	 */
	public static ArrayList<CalendarEvent> getListOfEvents() throws IOException, ParserException, ParseException {
		// Create an empty list of CalendarEvent objects
		ArrayList<CalendarEvent> events = new ArrayList<>();
		
		// Read the calendar 
		Schedule schedule = Login.schedule;
		List<Event> list = schedule.getEvents();
		
		// Loop through the list of events
		for(Event e : list) {
			// Ratio comparing the number of elements present on the event versus the number of total elements on the schedule
			double i = (double) e.getElements().size() / schedule.getElements().size();

			if (i <= 0.25) {
				// Color the cell green
				events.add(new CalendarEvent(e.getStartDate(), e.getEndDate(), e.getSummary(), e.getElements(),  new Color(153,255,51)));
			} else if(i <= 0.5) {
				// Color the cell yellow
				events.add(new CalendarEvent(e.getStartDate(), e.getEndDate(), e.getSummary(), e.getElements(),  new Color(255,255,102)));
			} else if(i <= 0.75) {
				// Color the cell orange
				events.add(new CalendarEvent(e.getStartDate(), e.getEndDate(), e.getSummary(), e.getElements(), Color.ORANGE));
			} else if(i > 0.75) {
				// Color the cell red
				events.add(new CalendarEvent(e.getStartDate(), e.getEndDate(), e.getSummary(), e.getElements(), new Color(204,0,0)));
			}
		}
		return events;
	}

	
	/**
	 * This method returns a list of CalendarEvent objects for a given Element object.
	 *
	 * @param element the Element object to read calendar events from
	 * @return a list of CalendarEvent objects
	 * @throws IOException if there is an error reading from the calendar
	 * @throws ParserException if there is an error parsing the calendar
	 * @throws ParseException if there is an error parsing the date/time of an event
	 */
	public static ArrayList<CalendarEvent> getListOfEventForElement(Element element) throws IOException, ParserException, ParseException {
		// Create an empty list of CalendarEvent objects
		ArrayList<CalendarEvent> events = new ArrayList<>();

		// Read the calendar from the given Element object
		Schedule schedule = Login.schedule;
		Schedule newSchedule = schedule.readCalendar(element);

		// Get the list of events from the calendar
		List<Event> list = newSchedule.getEvents();

		// Loop through the list of events
		for (Event e : list) {
			// Create a CalendarEvent object for the current event
			CalendarEvent calendarEvent = new CalendarEvent(e.getStartDate(), e.getEndDate(), e.getSummary(), e.getElements());

			// Add the CalendarEvent object to the list
			events.add(calendarEvent);

			// Print the event to the console (for debugging purposes)
			System.out.println(e.toString());
		}

		// Return the list of CalendarEvent objects
		return events;
	}

}

