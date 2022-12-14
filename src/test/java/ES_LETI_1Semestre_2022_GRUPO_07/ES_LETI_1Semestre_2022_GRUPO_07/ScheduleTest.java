package ES_LETI_1Semestre_2022_GRUPO_07.ES_LETI_1Semestre_2022_GRUPO_07;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ScheduleTest {

	Schedule schedule = new Schedule();
	List<Event> events = new ArrayList<>();
	Element element1 = new Element("Rafael", "https://fenix.iscte-iul.pt/publico/publicPersonICalendar.do?method=iCalendar&username=ramss3@iscte.pt&password=gGdsqZehHOOyFFAPMPSk8VeB63LPV0scP4qR36srE3dMgEB3b8GPIxCKorPUzQeySmm0XoAR7K0gw8pA5QrVrvx1VwfVrgjOFhMlC0mQkG6UxWuE4R2n12xxgV9hYfbX");
	Element element2 =  new Element("Filipe", "https://fenix.iscte-iul.pt/publico/publicPersonICalendar.do?method=iCalendar&username=fnvss@iscte.pt&password=QXPQEvIYJf6oR7oX3P7Ua19IGGIJTUD2Zmd6q7aqOaxmefDktt4TD0rSiMefsxnRwD8C9w4FmwWsKqF8wIPpxrTVisf31hZMd8KUAUERCtDWOfeWnM64j7k1jV8jbVE6");
	List<Element> elements = new ArrayList<>();
	Event event1 = new Event(LocalDateTime.of(2023, 1, 4, 16, 30), LocalDateTime.of(2023, 1, 4, 19, 0), "Arquitetura de Redes", element1);
	Event event2 = new Event(LocalDateTime.of(2022, 12, 15, 11, 0),LocalDateTime.of(2022, 12, 15, 12, 30), "Engenharia de Software", element1);
	Event event3 = new Event(LocalDateTime.of(2022, 12, 15, 8, 0),LocalDateTime.of(2022, 12, 15, 9, 30), "Engenharia de Software", element2);
	TimeOfDay timeOfDay;
	int duration;
	int periodicity;
	
	@BeforeEach
	void setUp() throws Exception {
		elements.add(element1);
		elements.add(element2);
		events.add(event1);
		events.add(event2);
		events.add(event3);
		schedule = new Schedule(events, elements);
		schedule.setEvents(events);
		
	}
	
	@Test
	void testGetUpperChars() {
		assertEquals("AR", schedule.getUpperChars(event1.getSummary(), ""));
	}

	@Test
	void testReadCalendar() {
		List<Event> aux = new ArrayList<>();
		aux.add(event1);
		aux.add(event2);
		
		assertEquals(new Schedule(aux, element1), schedule.readCalendar(element1));
	}

	@Test
	void testPeriodicReunion() throws FileNotFoundException {
		List<Event> eventsTest = new ArrayList<>();
		List<Event> events2 = new ArrayList<>();
		Event eventTeste = new Event(LocalDateTime.of(2022, 12, 15, 9, 30), LocalDateTime.of(2022, 12, 15, 10, 0), "Reunião", elements);
		Event aux = new Event(LocalDateTime.of(2022, 12, 15, 13, 0), LocalDateTime.of(2022, 12, 15, 13, 30), "Reunião", elements);
		eventsTest.add(eventTeste);
		events2.add(aux);
		assertEquals(eventsTest, schedule.periodicReunion(elements, TimeOfDay.MANHA, 30, 1, 1));
		assertEquals(events2, schedule.periodicReunion(elements, TimeOfDay.TARDE, 30, 1, 1));
	}

	@Test
	void testFilteredEvents() {
		List<Event> eventsList = new ArrayList<>();
		List<Event> aux = new ArrayList<>();
		eventsList.add(event3);
		eventsList.add(event2);
		aux.add(event1);
		assertEquals(eventsList, schedule.filteredEvents(TimeOfDay.MANHA, elements));
		assertEquals(aux, schedule.filteredEvents(TimeOfDay.TARDE, elements));
	}

	@Test
	void testCheckAvailableDate() throws FileNotFoundException {
		assertEquals(new Event(LocalDateTime.of(2022, 12, 15, 9, 30),LocalDateTime.of(2022, 12, 15, 10, 0), "Reunião", elements), schedule.checkAvailableDate(elements, TimeOfDay.MANHA, 30));
	}

	@Test
	void testGetElements() {
		assertEquals(elements, schedule.getElements());
	}

	@Test
	void testGetEvents() {
		assertEquals(events, schedule.getEvents());
	}

}
