package ES_LETI_1Semestre_2022_GRUPO_07.ES_LETI_1Semestre_2022_GRUPO_07;

import java.util.EventListener;

/**
 * An interface for handling clicks on calendar events.
 */
public interface CalendarEventClickListener extends EventListener {

	/**
	 * Handles a calendar event click event.
	 *
	 * @param e The CalendarEventClickEvent object representing the click on a calendar event.
	 */
	void calendarEventClick(CalendarEventClickEvent e);
}