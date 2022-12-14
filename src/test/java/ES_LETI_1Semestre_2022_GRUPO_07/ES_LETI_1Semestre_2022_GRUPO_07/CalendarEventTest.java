package ES_LETI_1Semestre_2022_GRUPO_07.ES_LETI_1Semestre_2022_GRUPO_07;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CalendarEventTest {

	CalendarEvent aux;
	Element element1 = new Element("Rafael", "https://fenix.iscte-iul.pt/publico/publicPersonICalendar.do?method=iCalendar&username=ramss3@iscte.pt&password=gGdsqZehHOOyFFAPMPSk8VeB63LPV0scP4qR36srE3dMgEB3b8GPIxCKorPUzQeySmm0XoAR7K0gw8pA5QrVrvx1VwfVrgjOFhMlC0mQkG6UxWuE4R2n12xxgV9hYfbX");
	Element element2 =  new Element("Filipe", "https://fenix.iscte-iul.pt/publico/publicPersonICalendar.do?method=iCalendar&username=fnvss@iscte.pt&password=QXPQEvIYJf6oR7oX3P7Ua19IGGIJTUD2Zmd6q7aqOaxmefDktt4TD0rSiMefsxnRwD8C9w4FmwWsKqF8wIPpxrTVisf31hZMd8KUAUERCtDWOfeWnM64j7k1jV8jbVE6");
	List<Element> elements = new ArrayList<>();
	CalendarEvent aux2;
	
	@BeforeEach
	void setUp() throws Exception {
		elements.add(element1);
		elements.add(element2);
		aux = new CalendarEvent(LocalDateTime.of(2022, 12, 12, 8, 0), LocalDateTime.of(2022, 12, 12, 9, 0), "Reunião", elements, Color.RED);
		aux = new CalendarEvent(LocalDateTime.of(2022, 12, 12, 8, 0), LocalDateTime.of(2022, 12, 12, 9, 0), "Reunião", elements, Color.RED);
		aux.setStart(LocalDateTime.of(2022, 12, 12, 8, 0));
		aux.setEnd(LocalDateTime.of(2022, 12, 12, 9, 0));
		aux.setText("Reunião");
		aux.setElements(elements);
		aux.toString();
	}

	@Test
	void testGetStart() {
		
		assertEquals(LocalDateTime.of(2022, 12, 12, 8, 0),aux.getStart());
	}

	@Test
	void testGetElements() {
		assertEquals(elements ,aux.getElements());
	}

	@Test
	void testGetEnd() {
		assertEquals(LocalDateTime.of(2022, 12, 12, 9, 0),aux.getEnd());
	}

	@Test
	void testGetText() {
		assertEquals("Reunião", aux.getText());
	}

	@Test
	void testGetColor() {
		assertEquals(Color.RED, aux.getColor());
	}

}
