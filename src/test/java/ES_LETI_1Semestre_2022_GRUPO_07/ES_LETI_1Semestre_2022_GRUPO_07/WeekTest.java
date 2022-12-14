package ES_LETI_1Semestre_2022_GRUPO_07.ES_LETI_1Semestre_2022_GRUPO_07;

import static org.junit.jupiter.api.Assertions.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WeekTest {
	Week week = new Week(LocalDate.of(2022, 12, 13));
	
	@BeforeEach
	void setUp() throws Exception {
		week.toString();
	}

	@Test
	void testGetStartOfWeek() {
		LocalDate day = LocalDate.of(2022, 12, 15);
		assertEquals(LocalDate.of(2022, 12, 12), week.getStartOfWeek(day));
	}

	@Test
	void testGetDay() {
		assertEquals(LocalDate.of(2022, 12, 12), week.getDay(DayOfWeek.MONDAY));
	}
	
	@Test
	void testNextWeek() {
		Week week1 = new Week(LocalDate.of(2022, 12, 20));
		assertEquals(week1,week.nextWeek());
	}

	@Test
	void testPrevWeek() {
		Week week1 = new Week(LocalDate.of(2022, 12, 6));
		assertEquals(week1,week.prevWeek());
	}

}
