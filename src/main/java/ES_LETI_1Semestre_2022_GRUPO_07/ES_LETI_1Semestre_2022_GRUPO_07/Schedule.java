package ES_LETI_1Semestre_2022_GRUPO_07.ES_LETI_1Semestre_2022_GRUPO_07;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.component.CalendarComponent;


public class Schedule {

	private List<Event> events = new ArrayList<>();
	private List<Element> elements = new ArrayList<>();
	//final static SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	final static SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyyMMdd'T'HHmmss");

	public Schedule(List<Event> events, List<Element> elements) {
		this.events = events;
		this.elements= elements;
	}


	public void addEvents(String name, String webLink) throws IOException, ParserException, ParseException {
		
		Element element = new Element(name);	
		elements.add(element);
		URL url = new URL(webLink);
		InputStream file = url.openStream();
		CalendarBuilder builder = new CalendarBuilder();
		Calendar calendar = builder.build(file);

		for(CalendarComponent comp : calendar.getComponents()) {
			String aux = (String) comp.getRequiredProperty("DTSTART").getValue();
			Date startDate = inputDateFormat.parse(aux.replaceAll("Z$", ""));
			String aux2 = (String) comp.getRequiredProperty("DTEND").getValue();
			Date endDate = inputDateFormat.parse(aux2.replaceAll("Z$", ""));
			String summary = (String) comp.getRequiredProperty("SUMMARY").getValue();
			if(compareEvents(startDate,summary)) {
					Event event = new Event(startDate,endDate,summary,elements);
					events.add(event);
			} else {
				Event event = new Event(startDate,endDate,summary,element);
				events.add(event);
			}
		}
	}

	private boolean compareEvents(Date date, String summary) {
		for(int i = 0; i < events.size(); i++) {
			if(events.get(i).startDate.equals(date) && events.get(i).summary.equals(summary)) {
				return true;
			}
		}
		return false;
	}

	private boolean compareElement(Element element) {
		for(int i = 0; i < elements.size(); i++) {
			if(elements.get(i).equals(element)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @return the events
	 */
	public List<Event> getEvents() {

		for(Event e : events) {
			e.getElements();
		}
		return this.events;
	}

	/**
	 * @param events the events to set
	 */
	public void setEvents(List<Event> events) {
		this.events = events;
	}





}