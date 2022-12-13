package ES_LETI_1Semestre_2022_GRUPO_07.ES_LETI_1Semestre_2022_GRUPO_07;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

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

public class CalendarViews {
	static ArrayList<CalendarEvent> eventsPrincipal = new ArrayList<>();
	static JComboBox cmbDay, cmbWeek;
	static JComboBox membersCombo = new JComboBox();
	static JFrame dayFrm = new JFrame("Calendar");
	static JFrame weekFrm = new JFrame("Calendar");
	static JButton teste = new JButton("Teste");

	public static void weeklyView(WeekCalendar aux) throws IOException, ParserException, ParseException {
		weekFrm = new JFrame("Calendar");
		String[] typeOfView = {"Weekly", "Daily", "Monthly"};
		cmbWeek = new JComboBox(typeOfView);

		//WeekCalendar aux = new WeekCalendar(getListOfEvents());
		aux.addCalendarEventClickListener(e -> System.out.println(e.getCalendarEvent()));
		aux.addCalendarEmptyClickListener(e -> {
			System.out.println(e.getDateTime());
			System.out.println(Calendar.roundTime(e.getDateTime().toLocalTime(), 30));
		});


		JButton goToTodayBtn = new JButton("Today");
		goToTodayBtn.addActionListener(e -> aux.goToToday());
		JButton nextWeekBtn = new JButton(">");
		nextWeekBtn.addActionListener(e -> aux.nextWeek());
		JButton prevWeekBtn = new JButton("<");
		prevWeekBtn.addActionListener(e -> aux.prevWeek());


		JButton backBtn = new JButton("Back");
		backBtn.addActionListener(e -> {
			switch (cmbWeek.getSelectedItem().toString()) {
			case "Weekly":
				CalendarWindow.getInstance().getFrameInstance().setVisible(true);
				weekFrm.dispose();
				break;
			}
		});

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
				MonthlyCalendar.main(null);
				weekFrm.dispose();
				break;
			}
		});

		JPanel weekControls = new JPanel();
		weekControls.add(cmbWeek);
		weekControls.add(prevWeekBtn);
		weekControls.add(goToTodayBtn);
		weekControls.add(nextWeekBtn);
		weekControls.add(backBtn);

		weekFrm.add(weekControls, BorderLayout.NORTH);
		weekFrm.add(aux, BorderLayout.CENTER);
		weekFrm.setSize(1600, 1000);
		weekFrm.setLocationRelativeTo(null);
		weekFrm.setVisible(true);
		weekFrm.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


	}



	public static void dailyView(DayCalendar aux) throws IOException, ParserException, ParseException {
		dayFrm = new JFrame("Calendar");
		String[] typeOfView = {"Daily", "Weekly", "Monthly"};
		cmbDay = new JComboBox(typeOfView);

		aux.addCalendarEventClickListener(e -> System.out.println(e.getCalendarEvent()));
		aux.addCalendarEmptyClickListener(e -> {
			System.out.println(e.getDateTime());
			System.out.println(Calendar.roundTime(e.getDateTime().toLocalTime(), 30));
		});

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
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				dayFrm.dispose();
				break;
			case "Monthly":
				MonthlyCalendar.main(null);
				dayFrm.dispose();
				break;
			}
		});

		JPanel dayControls = new JPanel();
		dayControls.add(cmbDay);
		dayControls.add(prevDayBtn);
		dayControls.add(goToTodayBtn);
		dayControls.add(nextDayBtn);
		dayControls.add(backBtn);

		dayFrm.add(dayControls, BorderLayout.NORTH);
		dayFrm.add(aux, BorderLayout.CENTER);
		dayFrm.setSize(1600, 1000);
		dayFrm.setLocationRelativeTo(null);
		dayFrm.setVisible(true);
		dayFrm.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}

	public static ArrayList<CalendarEvent> getListOfEvents() throws IOException, ParserException, ParseException {
		ArrayList<CalendarEvent> events = new ArrayList<>();
		Schedule schedule = Login.schedule;
		List<Event> list = schedule.getEvents();
		
		for(Event e : list) {
			double i = (double) e.getElements().size() / schedule.getElements().size();

			if (i <= 0.25) {
				events.add(new CalendarEvent(e.getStartDate(), e.getEndDate(), e.getSummary(), e.getElements(),  new Color(153,255,51)));
			} else if(i <= 0.5) {
				events.add(new CalendarEvent(e.getStartDate(), e.getEndDate(), e.getSummary(), e.getElements(),  new Color(255,255,102)));

			} else if(i <= 0.75) {
				events.add(new CalendarEvent(e.getStartDate(), e.getEndDate(), e.getSummary(), e.getElements(), Color.ORANGE));

			} else if(i > 0.75) {
				events.add(new CalendarEvent(e.getStartDate(), e.getEndDate(), e.getSummary(), e.getElements(), new Color(204,0,0)));

			}
			System.out.println(e.toString());
		}
		return events;
	}

	public static ArrayList<CalendarEvent> getListOfEventForElement(Element element) throws IOException, ParserException, ParseException {
		ArrayList<CalendarEvent> events = new ArrayList<>();
		Schedule schedule = Login.schedule;
		Schedule newSchedule = schedule.readCalendar(element);
		List<Event> list = newSchedule.getEvents();
		for(Event e : list) {
			events.add(new CalendarEvent(e.getStartDate(), e.getEndDate(), e.getSummary(), e.getElements()));
			System.out.println(e.toString());
		}
		return events;
	}

}

