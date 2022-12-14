package ES_LETI_1Semestre_2022_GRUPO_07.ES_LETI_1Semestre_2022_GRUPO_07;

import java.util.EventListener;

/**
 * An interface for handling clicks on empty dates in the calendar.
 */
public interface CalendarEmptyClickListener extends EventListener {

    /**
     * Handles a calendar empty click event.
     *
     * @param e The CalendarEmptyClickEvent object representing the click on an empty date in the calendar.
     */
    void calendarEmptyClick(CalendarEmptyClickEvent e);
}