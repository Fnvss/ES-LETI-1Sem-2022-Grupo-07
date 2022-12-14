
package ES_LETI_1Semestre_2022_GRUPO_07.ES_LETI_1Semestre_2022_GRUPO_07;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.component.CalendarComponent;

/**
 * This class represents a schedule, with a list of events and a list of elements.
 */
public class Schedule {

	private List<Event> events = new ArrayList<>();
	private List<Element> elements = new ArrayList<>();
	private final DateTimeFormatter inputDateFormat = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss");


	/**
     * Constructs a new Schedule with default values for its fields.
     */
	public Schedule() {

	}

	 /**
     * Constructs a new Schedule with the specified list of events and elements.
     *
     * @param events the list of events for the schedule
     * @param elements the list of elements for the schedule
     */
	public Schedule(List<Event> events, List<Element> elements) {
		this.events = events;
		this.elements= elements;
	}

	public Schedule(List<Event> events, Element element) {
		this.events = events;
		elements.add(element);
	}

	/**
	 * Adds an element to a schedule by parsing a calendar file from a URL and extracting events from the calendar.
	 *
	 * @param element   the element to add to the schedule
	 *
	 * @throws IOException if an error occurs while opening the connection to the URL or reading from the calendar file
	 * @throws ParserException if an error occurs while parsing the calendar file
	 * @throws ParseException if an error occurs while parsing the date strings in the calendar file
	 */
	public void addElementToSchedule(Element element) throws IOException, ParserException, ParseException {
		
		InputStream file = element.getUrl(element.webLink).openStream();
		CalendarBuilder builder = new CalendarBuilder();
		Calendar calendar = builder.build(file);
		Boolean bAux = false;

		for(CalendarComponent comp : calendar.getComponents()) {
			String aux = (String) comp.getRequiredProperty("DTSTART").getValue();
			LocalDateTime startDate = LocalDateTime.parse(aux.replaceAll("Z$", ""), inputDateFormat);
			String aux2 = (String) comp.getRequiredProperty("DTEND").getValue();
			LocalDateTime endDate = LocalDateTime.parse(aux2.replaceAll("Z$", ""), inputDateFormat);
			String summary = (String) comp.getRequiredProperty("SUMMARY").getValue();
			String finalSummary = filterString(summary);				
			Event newEvent = new Event(startDate,endDate,finalSummary,element);
			int indexOfNewEvent = events.indexOf(newEvent);
			if(indexOfNewEvent != -1) {
				events.get(indexOfNewEvent).addElement(element);
			} else {
				bAux = true;
				events.add(newEvent);
			}		
		}
		if(bAux != false) {
			elements.add(element);
		}
		Collections.sort(events);
		PrintStream out = new PrintStream("Horário.txt");
		System.setOut(out);
		for(Event e : this.events) {
			System.out.println(e.toString());
		}
	}
		
	/**
	 * Filters a given string and returns a new string containing only certain substrings from the original string.
	 * In this case, if the Summary starts with, "Exame", "Teste" or "AValiação Contínua", this won't 
	 * be filtered by the uppercase, but the remaining words from the summary will be replaced.
	 *
	 * @param s  the string to filter
	 *
	 * @return the filtered string
	 */
	public String filterString(String s) {
		String[] aux = s.split(" -");
		String summary = aux[0];
		s = "";
		if(summary.contains("Exame")) {
			s = s + summary.valueOf("Exame")+": ";
			summary = summary.replaceAll("Exame: ", "");
		}

		if(summary.contains("Teste")) {
			s = s + summary.valueOf("Teste") +": ";
			summary = summary.replaceAll("Teste: ", "");
		}
		if(summary.contains("Avaliação Contínua")) {
			s = s + summary.valueOf("Avaliação Contínua")+": ";
			summary = summary.replaceAll("Avaliação Contínua: ", "");
		}

		s = getUpperChars(summary, s);
		return s;
	}

	/**
	 * Extracts all uppercase letters from a given string and returns them as a new string.
	 *
	 * @param s  the string to extract uppercase letters from
	 * @param string  the string to add the extracted uppercase letters to
	 *
	 * @return the string containing all of the extracted uppercase letters
	 */
	public String getUpperChars(String s, String string) {

		for(int i = 0; i< s.length(); i++) {
			char c = s.charAt(i);
			if(Character.isUpperCase(c) ) {
				string = string + c;
			}
		}
		return string;
	}


	/**
	 * Reads the calendar of a specific element.
	 * Sees if the list of elements is not empty and create another list from the events list filtering it by the specific element
	 * read the new filtered list and adds the events to an empty list
	 * @param element  element to create a schedule for
	 * @return the new schedule for the specific element.
	 */
	public Schedule readCalendar(Element element) {
		if(elements.size() == 0) {
			System.out.println("Elements are empty!");
		}
		if(!elements.contains(element)) {
			System.out.println("Insert a valid Element!");
		}
		List<Event> filteredList = events.stream().filter(event -> event.getElements().contains(element)).collect(Collectors.toList());
		List<Event> newList = new ArrayList<>();
		for(Event e : filteredList) {
			newList.add(e);
		}
		Schedule newSchedule = new Schedule(newList, element);

		return newSchedule;
	}

	/**
	 * Schedules a series of periodic events for a given list of elements at a given time of day for a given duration.
	 *
	 * @param elementsList   a list of elements to schedule events for
	 * @param timeOfDay      the time of day to schedule the events
	 * @param duration       the duration of each event in minutes
	 * @param periodicity    the number of events to schedule
	 *
	 * @return a list of reunions that were scheduled
	 *
	 * @throws FileNotFoundException if the elements list contains invalid elements
	 */
	public List<Event> periodicReunion(List<Element> elementsList, TimeOfDay timeOfDay, int duration, int periodicity, int numberOfReunions) throws FileNotFoundException {
		for(Element e: elementsList) {
			if(!elements.contains(e)) {
				System.out.println("Invalid element in list");
				break;
			}	
		}

		List<Event> reunionList = new ArrayList<>();

		LocalDateTime time = LocalDateTime.now().withSecond(0).withNano(0);
		LocalDateTime now = LocalDateTime.now().withSecond(0).withNano(0);
		
		if(now.getHour() > timeOfDay.getEndHour()) {
			
			time = time.plusDays(1).withHour(timeOfDay.getStartHour()).withMinute(timeOfDay.getStartMinute());
		}
		
		if(now.getHour() < timeOfDay.getStartHour()) {
			time = time.withHour(timeOfDay.getStartHour()).withMinute(timeOfDay.getStartMinute());
		}

		for(int i = 1; i <= numberOfReunions; i++) {


			Event evento = new Event(time, time.plusMinutes(duration), "Reunião", elementsList);
			
			List<Event> eventsList = null;

			switch(timeOfDay) {
			case MANHA:
				eventsList = this.events.stream()
				.filter(event -> 
				!Collections.disjoint(event.getElements(), elementsList) &&
				(event.getStartDate().isAfter(evento.getStartDate()) || 
				event.getStartDate().isEqual(evento.getStartDate())) &&
				event.getStartDate().getHour() <= timeOfDay.getEndHour() &&
				event.getStartDate().getMinute() <= timeOfDay.getEndMinute()
						).collect(Collectors.toList());
				
				break;
			case TARDE:
				eventsList = this.events.stream()
				.filter(event ->
				!Collections.disjoint(event.getElements(), elementsList) &&
				(event.getStartDate().isAfter(evento.getStartDate()) || 
				event.getStartDate().isEqual(evento.getStartDate()))&&
				event.getStartDate().getHour() >= timeOfDay.getStartHour() &&
				event.getStartDate().getMinute() >= timeOfDay.getStartMinute()
						).collect(Collectors.toList());
				
				break;
			default:
				// code bloc
			}
			Collections.sort(eventsList);

			outerloop:
				while(true) {
					while(time.getHour() <= timeOfDay.getEndHour() && time.plusMinutes(duration).getHour() <= timeOfDay.getEndHour()) {

						evento.setStartDate(time);
						evento.setEndDate(time.plusMinutes(duration));

						List<Event> head = eventsList.subList(0, 1);
							
						//}
						if(head.size() == 0) {
							this.events.add(evento);
							break outerloop;
						}
						if(!head.get(0).collidesWithEvent(evento)) {
							this.events.add(evento);
							break outerloop;
						}

						eventsList = eventsList.subList(1, eventsList.size()); // tail
						time = head.get(0).getEndDate();

					}
					//avançar o dia
					time = now.plusDays(1).withHour(timeOfDay.getStartHour()).withMinute(timeOfDay.getStartMinute());
				}

			reunionList.add(evento);
			int subtract = time.getDayOfWeek().getValue() - 1;
			time = time.plusWeeks(periodicity).minusDays(subtract).withHour(timeOfDay.getStartHour()).withMinute(timeOfDay.getStartMinute());
		}
		
		Collections.sort(events);
		PrintStream out = new PrintStream("Horário.txt");
		System.setOut(out);
		for(Event e: this.events) {
			System.out.println(e.toString());
		}

		return reunionList;	
	}
	
	/**
	 * Filters a list of events by a given time of day and a list of elements.
	 *
	 * @param timeOfDay   the time of day to filter events by
	 * @param elementsList   the list of elements to filter events by
	 *
	 * @return a sorted list of events that match the given time of day and elements
	 */
	public List<Event> filteredEvents(TimeOfDay timeOfDay, List<Element> elementsList) {

		LocalDateTime now = LocalDateTime.now().withSecond(0).withNano(0);

		List<Event> eventsList = null;

		switch(timeOfDay) {
		case MANHA:
			eventsList = this.events.stream()
			.filter(event -> 
			!Collections.disjoint(event.getElements(), elementsList) &&
			event.getStartDate().isAfter(now) &&
			event.getStartDate().getHour() <= timeOfDay.getEndHour() &&
			event.getStartDate().getMinute() <= timeOfDay.getEndMinute()
					).collect(Collectors.toList());
			
			break;
		case TARDE:
			eventsList = this.events.stream()
			.filter(event ->
			!Collections.disjoint(event.getElements(), elementsList) &&
			event.getStartDate().isAfter(now) &&
			event.getStartDate().getHour() >= timeOfDay.getStartHour() &&
			event.getStartDate().getMinute() >= timeOfDay.getStartMinute()
					).collect(Collectors.toList());
			
			break;
		default:
			// code bloc
		}
		Collections.sort(eventsList);
		return eventsList;
	}
	
	/**
	 * Checks for an available date and time to schedule a reunion for a given list of elements that will participate in the reunion
	 * and time of day for the reunion.
	 *
	 * @param elementsList   the list of elements to schedule the event for
	 * @param timeOfDay      the time of day to schedule the event
	 * @param duration       the duration of the event in minutes
	 *
	 * @return the scheduled event
	 * @throws FileNotFoundException 
	 */
	public Event checkAvailableDate(List<Element> elementsList, TimeOfDay timeOfDay, int duration) throws FileNotFoundException {

		for(Element e: elementsList) {
			if(!elements.contains(e)) {
				System.out.println("Invalid element in list");
				break;
			}	
		}

		LocalDateTime now = LocalDateTime.now().withSecond(0).withNano(0);
		LocalDateTime time = LocalDateTime.now().withSecond(0).withNano(0);	
		List<Event> eventsList = null;

		eventsList = filteredEvents(timeOfDay, elementsList);

		if(now.getHour() > timeOfDay.getEndHour()) {

			time = time.plusDays(1).withHour(timeOfDay.getStartHour()).withMinute(timeOfDay.getStartMinute());
		}

		if(now.getHour() < timeOfDay.getStartHour()) {
			time = time.withHour(timeOfDay.getStartHour()).withMinute(timeOfDay.getStartMinute());
		}

		Event evento = new Event(time, time.plusMinutes(duration), "Reunião", elementsList);

		outerloop:
			while(true) {
				while(time.getHour() <= timeOfDay.getEndHour() && time.plusMinutes(duration).getHour() <= timeOfDay.getEndHour()) {

					evento.setStartDate(time);
					evento.setEndDate(time.plusMinutes(duration));

					List<Event> head = eventsList.subList(0, 1);
					if(head.size() == 0) {
						this.events.add(evento);
						break outerloop;
					}
					if(!head.get(0).collidesWithEvent(evento)) {
						this.events.add(evento);
						break outerloop;
					}

					eventsList = eventsList.subList(1, eventsList.size()); // tail
					time = head.get(0).getEndDate();

				}
				//advances the day
				time = now.plusDays(1).withHour(timeOfDay.getStartHour()).withMinute(timeOfDay.getStartMinute());
			}
			Collections.sort(events);
			PrintStream out = new PrintStream("Horário.txt");
			System.setOut(out);
			for(Event e: this.events) {
				System.out.println(e.toString());
			}

		return evento;
	}


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
	
	@Override
	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || getClass() != o.getClass()) return false;
	    Schedule schedule = (Schedule) o;
	    return Objects.equals(events, schedule.events) &&
	            Objects.equals(elements, schedule.elements);
	}


}
