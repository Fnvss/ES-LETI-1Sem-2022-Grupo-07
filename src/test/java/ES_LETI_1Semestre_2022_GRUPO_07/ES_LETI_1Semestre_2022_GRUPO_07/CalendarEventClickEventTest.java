package ES_LETI_1Semestre_2022_GRUPO_07.ES_LETI_1Semestre_2022_GRUPO_07;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CalendarEventClickEventTest {
	 
	List<Element> elements = new ArrayList<>();
	Element element1 = new Element("Rafael", "https://fenix.iscte-iul.pt/publico/publicPersonICalendar.do?method=iCalendar&username=ramss3@iscte.pt&password=gGdsqZehHOOyFFAPMPSk8VeB63LPV0scP4qR36srE3dMgEB3b8GPIxCKorPUzQeySmm0XoAR7K0gw8pA5QrVrvx1VwfVrgjOFhMlC0mQkG6UxWuE4R2n12xxgV9hYfbX");
	Element element2 = new Element("Filipe", "https://fenix.iscte-iul.pt/publico/publicPersonICalendar.do?method=iCalendar&username=fnvss@iscte.pt&password=QXPQEvIYJf6oR7oX3P7Ua19IGGIJTUD2Zmd6q7aqOaxmefDktt4TD0rSiMefsxnRwD8C9w4FmwWsKqF8wIPpxrTVisf31hZMd8KUAUERCtDWOfeWnM64j7k1jV8jbVE6");

	CalendarEvent cal = new CalendarEvent(LocalDateTime.of(2022, 11, 11, 9, 0), LocalDateTime.of(2022, 11, 11, 9, 30), "Reunião", elements);
	CalendarEventClickEvent aux = new CalendarEventClickEvent(Object.class, cal);
	
	@BeforeEach
	void setUp() throws Exception {
		elements.add(element1);
		elements.add(element2);
	}

	@Test
	void testGetCalendarEvent() {
		assertEquals(new CalendarEvent(LocalDateTime.of(2022, 11, 11, 9, 0), LocalDateTime.of(2022, 11, 11, 9, 30), "Reunião", elements) , aux.getCalendarEvent());
	}

}
