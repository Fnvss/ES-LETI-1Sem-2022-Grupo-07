package ES_LETI_1Semestre_2022_GRUPO_07.ES_LETI_1Semestre_2022_GRUPO_07;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalField;
import java.util.ArrayList;
import java.util.Collections;
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
	private final DateTimeFormatter inputDateFormat = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss");
	private final int inicioManhaHora = 8;
	private final int inicioManhaMinuto = 0;
	private final int fimManhaHora = 12;
	private final int fimManhaMinuto = 59;

	private final int inicioTardeHora = 13;
	private final int inicioTardeMinuto = 0;
	private final int fimTardeHora = 20;
	private final int fimTardeMinuto = 0;


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

	/**
	 * Add an element to the schedule.
	 * Receives an elements which contains an name and url, it adds the element to the list of elements and parse url to 
	 * URL type. Creates the calendar type from the url and reads it. In the end, it sorts the events dates.
	 *
	 * @throws IOException If an I/O error occurs.
	 * @throws ParserException throws an exception when an error occurs in parsing iCalendar data.
	 * @throws ParseException throws signals that an error has been reached unexpectedly while parsing.
	 */
	public void addElementToSchedule(Element element) throws IOException, ParserException, ParseException {

		elements.add(element);
		URL url = new URL(element.webLink);
		InputStream file = url.openStream();
		CalendarBuilder builder = new CalendarBuilder();
		Calendar calendar = builder.build(file);

		for(CalendarComponent comp : calendar.getComponents()) {
			String aux = (String) comp.getRequiredProperty("DTSTART").getValue();
			LocalDateTime startDate = LocalDateTime.parse(aux.replaceAll("Z$", ""), inputDateFormat);
			String aux2 = (String) comp.getRequiredProperty("DTEND").getValue();
			LocalDateTime endDate = LocalDateTime.parse(aux2.replaceAll("Z$", ""), inputDateFormat);
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


	/**
	 * Reads the calendar of a specific element.
	 * Sees if the list of elements is not empty and create another list from the events list filtering it by the specific element
	 * read the new filtered list and adds the events to an empty list
	 * @return that new list.
	 */
	public Schedule readCalendar(Element element) {
		if(elements.size() == 0)
			System.out.println("Elements are empty!");
		if(!elements.contains(element))
			System.out.println("Insert a valid Element!");
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


	/**
	 * Reads all available calendars from the events list.
	 * First checks if the elements list isn't empty and create an object Schedule.
	 * @return the created new object Schedule.
	 */
	public Schedule readAllCalendars() {
		if(elements.size() == 0 && events.size() == 0) {
			System.out.println("No elements or events available!");
		}
		Schedule newSchedule = new Schedule(events, elements);
		return newSchedule;
	}

	//public Schedule 
	/**
	 * @return the events in a specific month.
	 */
	public Schedule readCalendarForSpecificMonth(String  month) {
		if(elements.size() == 0 && events.size() == 0) {
			System.out.println("Elements are empty!");
		}
		List<Event> filteredList = events.stream().filter(event -> event.getStartDate().getMonth().toString().equalsIgnoreCase(month)).collect(Collectors.toList());
		List<Event> newList = new ArrayList<>();
		for(Event e : filteredList) {
			newList.add(e);
		}
		Schedule newSchedule = new Schedule(newList, elements);
		return newSchedule;
	}


	/**
	 * Following the input of a specific month, it searches for the events in a specific day
	 * @return the events in a specific day of a month.
	 */
	public Schedule readCalendarForSpecificDayOfTheMonth(int day, String month) {
		Schedule schedule = readCalendarForSpecificMonth(month);
		List<Event> monthList = schedule.events;
		List<Event> newList = new ArrayList<>();
		for(Event e : monthList) {
			if(e.getEndDate().getDayOfMonth() == day) {
				newList.add(e);
			} 
		}
		if(newList.size() == 0) 
			System.out.println("There are no events for the selected day!");

		Schedule newSchedule = new Schedule(newList, elements);
		return newSchedule;
	}

	public boolean addReunion(Event newEvent, List<Event> eventList) {

		for(Event e: eventList) {
			if(!e.collidesWithEvent(newEvent)) {
				this.events.add(newEvent);
				return true;
			}
		}
		return false;
	}





	//manha ou tarde, analisando os elementos em questao ver o horario de disponibilidade dos mesmos para marcar uma reuniao e ver qual
	// a mnelhor hora par a fazer a reuniao com todos os elementos

	public List<Event> checkAvailableDate(List<Element> elementsList, String timeOfTheDay, String duration) {
		for(Element e: elementsList) {
			if(!elements.contains(e)) {
				System.out.println("Invalid element in list");
				break;
			}	
		}
		
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime time = LocalDateTime.now();
		List<Event> eventsForSpecificElements = events.stream().filter(event -> 
		!Collections.disjoint(event.getElements(), elementsList)
				).collect(Collectors.toList());		
		List<Event> eventsList = null;
		int endHour = 0;
		int endMinute = 0;
		int startHour = 0;
		int startMinute = 0;
		Boolean insert = false;
		
		
		if(timeOfTheDay.equalsIgnoreCase("Manhã")) {
			eventsList = eventsForSpecificElements.stream()
					.filter(event -> 
					event.getStartDate().isAfter(now) &&
					event.startDate.getHour() <= fimManhaHora &&
					event.startDate.getMinute() <= fimManhaMinuto
							).collect(Collectors.toList());
			startHour = inicioManhaHora;
			startMinute = inicioManhaMinuto;
			endHour = fimManhaHora;
			endMinute = fimManhaMinuto;
			
		} else if(timeOfTheDay.equalsIgnoreCase("Tarde")) {
			eventsList = eventsForSpecificElements.stream()
					.filter(event ->
					event.getStartDate().isAfter(now) &&
					event.startDate.getHour() >= inicioTardeHora &&
					event.startDate.getMinute() >= inicioTardeMinuto
							).collect(Collectors.toList());
			startHour = inicioTardeHora;
			startMinute = inicioTardeMinuto;
			endHour = fimTardeHora;
			endMinute = fimTardeMinuto;
		}
		
		time = time.withHour(startHour).withMinute(startMinute);
		
		while(!insert) {
			while(now.getHour() <= endHour && now.getMinute() <= endMinute) {
				time = now.plusMinutes(1);
				//verificar evento
				insert = true;
				
			}	
			//avançar o dia
			time = now.plusDays(1).withHour(startHour).withMinute(startMinute);
		}

		return null;


	}



	//public Schedule 
	/**
	 * @return the elements list.
	 */

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
