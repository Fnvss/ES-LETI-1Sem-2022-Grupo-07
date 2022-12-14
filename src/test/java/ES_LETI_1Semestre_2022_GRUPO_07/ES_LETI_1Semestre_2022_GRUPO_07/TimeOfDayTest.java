package ES_LETI_1Semestre_2022_GRUPO_07.ES_LETI_1Semestre_2022_GRUPO_07;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TimeOfDayTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testGetStartHour() {
		assertEquals(8, TimeOfDay.MANHA.getStartHour());
	}

	@Test
	void testGetStartMinute() {
		assertEquals(0, TimeOfDay.MANHA.getStartMinute());
	}

	@Test
	void testGetEndHour() {
		assertEquals(12, TimeOfDay.MANHA.getEndHour());
	}

	@Test
	void testGetEndMinute() {
		assertEquals(59, TimeOfDay.MANHA.getEndMinute());
	}

}
