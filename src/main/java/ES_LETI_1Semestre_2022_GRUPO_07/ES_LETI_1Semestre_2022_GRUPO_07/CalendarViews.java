package ES_LETI_1Semestre_2022_GRUPO_07.ES_LETI_1Semestre_2022_GRUPO_07;

import java.awt.BorderLayout;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import ES_LETI_1Semestre_2022_GRUPO_07.ES_LETI_1Semestre_2022_GRUPO_07.MonthlyCalendar.cmbView_Action;
import net.fortuna.ical4j.data.ParserException;

public class CalendarViews implements ActionListener {
	static ArrayList<CalendarEvent> events = new ArrayList<>();
	static WeekCalendar weekCal = new WeekCalendar(events);
	static DayCalendar aux = new DayCalendar(events);
	static JComboBox cmbDay, cmbWeek;
	static JButton backButton = new JButton("Back");



	public static void main(String[] args) throws IOException, ParserException, ParseException {
		
		//Element element1 = new Element("Guilherme", "https://fenix.iscte-iul.pt/publico/publicPersonICalendar.do?method=iCalendar&username=ggmso@iscte.pt&password=RapiAbBUH3jevyDETvtVAO2iI7JUi9NTv0i7JAn8tiUpVOAES55J6exaS9U27XMosaQINnY17SXwSe561qHbRbEtUqWTn7SfbiiuQi92WogIhg6U7JJpAhqjvjo3HdOV");

		//schedule.addElementToSchedule(element1);
		//getListOfEvents();

	
		//DayCalendar dayCal = new DayCalendar(events);
		weeklyView();
	}
	
	public static ArrayList<CalendarEvent> getListOfEvents() throws IOException, ParserException, ParseException {
		ArrayList<CalendarEvent> events = new ArrayList<>();
		Schedule schedule = Login.schedule;

//		Element element1 = new Element("Guilherme", "https://fenix.iscte-iul.pt/publico/publicPersonICalendar.do?method=iCalendar&username=ggmso@iscte.pt&password=RapiAbBUH3jevyDETvtVAO2iI7JUi9NTv0i7JAn8tiUpVOAES55J6exaS9U27XMosaQINnY17SXwSe561qHbRbEtUqWTn7SfbiiuQi92WogIhg6U7JJpAhqjvjo3HdOV");
//		Element element2 = new Element("Tomás", "https://fenix.iscte-iul.pt/publico/publicPersonICalendar.do?method=iCalendar&username=tlpco@iscte.pt&password=peZuWATHi62pUmdMkFVyVLgeqWYc1Wk9VTCE3hbcbek93N030ZkPxDuiJPHmJP169nua5GMkztSGKD9MXczPYZCXqnUmrBvwklOcRftGPql225ahEjieA2782mKLpEQA");
//		Element element3= new Element("Rafael", "https://fenix.iscte-iul.pt/publico/publicPersonICalendar.do?method=iCalendar&username=ramss3@iscte.pt&password=gGdsqZehHOOyFFAPMPSk8VeB63LPV0scP4qR36srE3dMgEB3b8GPIxCKorPUzQeySmm0XoAR7K0gw8pA5QrVrvx1VwfVrgjOFhMlC0mQkG6UxWuE4R2n12xxgV9hYfbX");
//		Element element4= new Element("Filipe", "https://fenix.iscte-iul.pt/publico/publicPersonICalendar.do?method=iCalendar&username=fnvss@iscte.pt&password=QXPQEvIYJf6oR7oX3P7Ua19IGGIJTUD2Zmd6q7aqOaxmefDktt4TD0rSiMefsxnRwD8C9w4FmwWsKqF8wIPpxrTVisf31hZMd8KUAUERCtDWOfeWnM64j7k1jV8jbVE6");
//		schedule.addElementToSchedule(element1);
//		schedule.addElementToSchedule(element2);
//		schedule.addElementToSchedule(element3);
//		schedule.addElementToSchedule(element4);
		List<Event> list = schedule.getEvents();
		for(Event e : list) {
			events.add(new CalendarEvent(e.getStartDate(), e.getEndDate(), e.getSummary(), e.getElements()));
			System.out.println(e.toString());
		}
		return events;
	}
	



	public static void weeklyView() throws IOException, ParserException, ParseException {
		JFrame weekFrm = new JFrame("Calendar");
		String[] typeOfView = {"Weekly", "Daily", "Monthly"};
		cmbWeek = new JComboBox(typeOfView);
		
		WeekCalendar aux = new WeekCalendar(getListOfEvents());
		aux.addCalendarEventClickListener(e -> System.out.println(e.getCalendarEvent()));
		aux.addCalendarEmptyClickListener(e -> {
			System.out.println(e.getDateTime());
			System.out.println(Calendar.roundTime(e.getDateTime().toLocalTime(), 30));
		});
		
		
		//Login.getInstance().getFrameInstance().setVisible(true);
		//backButton.addActionListener(Login.getInstance().getFrameInstance().setVisible(true));
		//backButton.addActionListener(weekFrm);	
		JButton goToTodayBtn = new JButton("Today");
		goToTodayBtn.addActionListener(e -> aux.goToToday());
		JButton nextWeekBtn = new JButton(">");
		nextWeekBtn.addActionListener(e -> aux.nextWeek());
		JButton prevWeekBtn = new JButton("<");
		prevWeekBtn.addActionListener(e -> aux.prevWeek());

		cmbWeek.addActionListener(e -> {
			switch (cmbWeek.getSelectedItem().toString()) {
			case "Daily":
				try {
					dailyView();
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
		weekControls.add(backButton);

		weekFrm.add(weekControls, BorderLayout.NORTH);
		weekFrm.add(aux, BorderLayout.CENTER);
		weekFrm.setSize(1600, 1000);
		weekFrm.setLocationRelativeTo(null);
		weekFrm.setVisible(true);
		weekFrm.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}



	public static void dailyView() throws IOException, ParserException, ParseException {
		JFrame dayFrm = new JFrame("Calendar");
		String[] typeOfView = {"Daily", "Weekly", "Monthly"};
		cmbDay = new JComboBox(typeOfView);
		
		DayCalendar aux = new DayCalendar(getListOfEvents());
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

		cmbDay.addActionListener(e -> {
			switch (cmbDay.getSelectedItem().toString()) {
			case "Weekly":
				try {
					weeklyView();
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

		dayFrm.add(dayControls, BorderLayout.NORTH);
		dayFrm.add(aux, BorderLayout.CENTER);
		dayFrm.setSize(1500, 1300);
		dayFrm.setLocationRelativeTo(null);
		dayFrm.setVisible(true);
		dayFrm.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
	

	@Override
	public void actionPerformed(ActionEvent ae) {
		if(ae.getActionCommand() == backButton.getActionCommand()) {
			//frame.setVisible(false);
			Login.getInstance().getFrameInstance().setVisible(true);		
		}
		

	}
}

