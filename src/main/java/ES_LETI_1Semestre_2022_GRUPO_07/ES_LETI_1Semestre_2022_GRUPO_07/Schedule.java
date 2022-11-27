package ES_LETI_1Semestre_2022_GRUPO_07.ES_LETI_1Semestre_2022_GRUPO_07;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.component.CalendarComponent;

import java.io.FileReader;
import net.fortuna.ical4j.data.UnfoldingReader;

public class Schedule {

	private List<Event> events = new ArrayList<>();
	private List<Element> elements = new ArrayList<>();
	//final static SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	final static SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyyMMdd'T'HHmmss");

	public Schedule() {

	}

	public Schedule(List<Event> events, List<Element> elements) {
		this.events = events;
		this.elements= elements;
	}

	public Schedule(List<Event> events, Element element) {
		this.events = events;
		elements.add(element);
	}


	public void addElement(Element element) throws IOException, ParserException, ParseException {

		elements.add(element);
		URL url = new URL(element.webLink);
		InputStream file = url.openStream();
		CalendarBuilder builder = new CalendarBuilder();
		Calendar calendar = builder.build(file);

		for(CalendarComponent comp : calendar.getComponents()) {
			String aux = (String) comp.getRequiredProperty("DTSTART").getValue();
			Date startDate = inputDateFormat.parse(aux.replaceAll("Z$", ""));
			String aux2 = (String) comp.getRequiredProperty("DTEND").getValue();
			Date endDate = inputDateFormat.parse(aux2.replaceAll("Z$", ""));
			String summary = (String) comp.getRequiredProperty("SUMMARY").getValue();
			Event newEvent = new Event(startDate,endDate,summary,element);
			int indexOfNewEvent = events.indexOf(newEvent);
			if(indexOfNewEvent != -1) {
				events.get(indexOfNewEvent).addElement(element);
			} else {
				events.add(newEvent);
			}		
		}
		Collections.sort(events);
	}

	public Schedule readCalendar(Element element) {
		if(elements.size() == 0) {
			System.out.println("Elements are empty!");
		}
		List<Event> filteredList = events.stream().filter(event -> event.getElements().contains(element)).collect(Collectors.toList());
		List<Event> newList = new ArrayList<>();
		for(Event e : filteredList) {
			newList.add(e);
		}
//		for(Event e : newList) {
//			if(e.getElements().size() > 1) {
//				e.getElements().clear();
//				e.addElement(element);
//			}
//		}
		Schedule newSchedule = new Schedule(newList, element);

		return newSchedule;
	}

	public List<Element> getElements() {
		return elements;
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