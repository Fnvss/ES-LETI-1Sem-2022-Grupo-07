
package ES_LETI_1Semestre_2022_GRUPO_07.ES_LETI_1Semestre_2022_GRUPO_07;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.URL;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.component.CalendarComponent;

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
	private final int fimTardeMinuto = 59;


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

		URL url = new URL(element.webLink);
		InputStream file = url.openStream();
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
		for(Event e : this.events) {
			System.out.println(e.toString());
		}
	}

	//	public void ScheduleTxt(Element element) throws IOException, ParserException, ParseException {
	//		addElementToSchedule(element);
	//		PrintStream out = new PrintStream("Horário.txt");
	//		System.setOut(out);
	//	}

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

	public List<Event> periodicReunion(List<Element> elementsList, TimeOfDay timeOfDay, int duration, int periodicity) {
		for(Element e: elementsList) {
			if(!elements.contains(e)) {
				System.out.println("Invalid element in list");
				break;
			}	
		}

		List<Event> reunionList = new ArrayList<>();

		LocalDateTime time = LocalDateTime.now().withSecond(0).withNano(0);
		LocalDateTime now = LocalDateTime.now().withSecond(0).withNano(0);

		for(int i = 1; i <= periodicity; i++) {

			if(now.getHour() > timeOfDay.getEndHour()) {

				time = time.plusDays(1).withHour(timeOfDay.getStartHour()).withMinute(timeOfDay.getStartMinute());
			}

			if(now.getHour() < timeOfDay.getStartHour()) {
				time = time.withHour(timeOfDay.getStartHour()).withMinute(timeOfDay.getStartMinute());
			}

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
				event.getStartDate().isAfter(now) &&
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
						//if(head.get(0).getStartDate().isBefore(evento.getStartDate())) {
							
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
			time = time.plusWeeks(1).minusDays(subtract).withHour(timeOfDay.getStartHour()).withMinute(timeOfDay.getStartMinute());
		}

		return reunionList;	
	}

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
	 * Following the input of a list of elements that the costumer wants to have in the reunion, an info if he wants the reunion
	 * at morning or afternoon and the duration for the reunion. First it checks if all of the elements in the input exist, then 
	 * it's created a list of events for the specific elements for the reunion, using a filter. Then a list of events that occurs
	 * in the morning or afternoon, it depends according with the input. At the end, it starts to search for available dates for the reunion,
	 * starting the search for the closest possible time.
	 * @return the created reunion.
	 */
	public Event checkAvailableDate(List<Element> elementsList, TimeOfDay timeOfDay, int duration) {

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
				//avançar o dia
				time = now.plusDays(1).withHour(timeOfDay.getStartHour()).withMinute(timeOfDay.getStartMinute());
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

}
