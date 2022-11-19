package ES_LETI_1Semestre_2022_GRUPO_07.ES_LETI_1Semestre_2022_GRUPO_07;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.*;
import net.fortuna.ical4j.model.component.CalendarComponent;

public class FiltredCalendar {

	public static void filtred(String s) throws IOException, ParserException {

		FileInputStream fin = new FileInputStream(s);
		CalendarBuilder builder = new CalendarBuilder();
		Calendar calendar = builder.build(fin);
		for (CalendarComponent comp : calendar.getComponents()) {

			System.out.println(comp.getProperties());;


		}




	}


	//	java.util.Calendar today = java.util.Calendar.getInstance();
	//	today.set(java.util.Calendar.HOUR_OF_DAY, 0);
	//	today.clear(java.util.Calendar.MINUTE);
	//	today.clear(java.util.Calendar.SECOND);
	//
	//	// create a period starting now with a duration of one (1) day..
	//	Period period = new Period(new DateTime(today.getTime()), new Dur(1, 0, 0, 0));
	//	Filter filter = new Filter(new PeriodRule(period));
	//
	//	List eventsToday = filter.filter(calendar.getComponents(Component.VEVENT));


	//	public static void main(String[] args) throws IOException, ParserException {
	//		filtred();
	//	}
}