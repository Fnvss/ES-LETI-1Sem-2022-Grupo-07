package ES_LETI_1Semestre_2022_GRUPO_07.ES_LETI_1Semestre_2022_GRUPO_07;

import static org.junit.jupiter.api.Assertions.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DayCalendarTest {

	ArrayList<CalendarEvent> events = new ArrayList<>();
	List<Element> elements = new ArrayList<>();
	LocalDate date = LocalDate.of(2022, 12, 12);
	Element element1 = new Element("Rafael", "https://fenix.iscte-iul.pt/publico/publicPersonICalendar.do?method=iCalendar&username=ramss3@iscte.pt&password=gGdsqZehHOOyFFAPMPSk8VeB63LPV0scP4qR36srE3dMgEB3b8GPIxCKorPUzQeySmm0XoAR7K0gw8pA5QrVrvx1VwfVrgjOFhMlC0mQkG6UxWuE4R2n12xxgV9hYfbX");
	Element element2 =  new Element("Filipe", "https://fenix.iscte-iul.pt/publico/publicPersonICalendar.do?method=iCalendar&username=fnvss@iscte.pt&password=QXPQEvIYJf6oR7oX3P7Ua19IGGIJTUD2Zmd6q7aqOaxmefDktt4TD0rSiMefsxnRwD8C9w4FmwWsKqF8wIPpxrTVisf31hZMd8KUAUERCtDWOfeWnM64j7k1jV8jbVE6");
	DayCalendar dayCal = new DayCalendar(events, date);
	
	@BeforeEach
	void setUp() throws Exception {
		elements.add(element1);
		elements.add(element2);
	}

	@Test
	void testDateInRange() {
		LocalDate calDate = LocalDate.of(2022, 12, 12);
		assertEquals(calDate.equals(date), dayCal.dateInRange(date));
	}

	@Test
	void testGetDateFromDay() {
		assertEquals(LocalDate.of(2022, 12, 12), dayCal.getDateFromDay(DayOfWeek.MONDAY));
	}

	@Test
	void testNumDaysToShow() {
		assertEquals(1, dayCal.numDaysToShow());
	}

	@Test
	void testGetStartDay() {
		assertEquals(LocalDate.of(2022, 12, 12).getDayOfWeek(), dayCal.getStartDay());
	}

	@Test
	void testGetEndDay() {
		assertEquals(LocalDate.of(2022, 12, 12).getDayOfWeek(), dayCal.getEndDay());
	}

}
