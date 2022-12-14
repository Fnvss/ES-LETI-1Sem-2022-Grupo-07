package ES_LETI_1Semestre_2022_GRUPO_07.ES_LETI_1Semestre_2022_GRUPO_07;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CalendarEmptyClickEventTest {

	CalendarEmptyClickEvent aux;

	@BeforeEach
	void setUp() throws Exception {
		aux = new CalendarEmptyClickEvent(Object.class ,LocalDateTime.of(2022, 12, 15, 8, 0));
	}

	@Test
	void testGetDateTime() {
		assertEquals(LocalDateTime.of(2022, 12, 15, 8, 0) , aux.getDateTime());
	}

}
