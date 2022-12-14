package ES_LETI_1Semestre_2022_GRUPO_07.ES_LETI_1Semestre_2022_GRUPO_07;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CalendarTest {
	LocalTime date = LocalTime.of(8, 20);
	
	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testRoundTime() {
		assertEquals(LocalTime.of(8, 20), Calendar.roundTime(date, 2));
	}

	@Test
	void testDateInRange() {
		fail("Not yet implemented");
	}

	@Test
	void testGetDateFromDay() {
		fail("Not yet implemented");
	}

	@Test
	void testNumDaysToShow() {
		fail("Not yet implemented");
	}

	@Test
	void testPaintComponentGraphics() {
		fail("Not yet implemented");
	}

	@Test
	void testGetStartDay() {
		fail("Not yet implemented");
	}

	@Test
	void testGetEndDay() {
		fail("Not yet implemented");
	}

	@Test
	void testGetDayWidth() {
		fail("Not yet implemented");
	}

	@Test
	void testSetRangeToToday() {
		fail("Not yet implemented");
	}

}
