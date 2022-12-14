package ES_LETI_1Semestre_2022_GRUPO_07.ES_LETI_1Semestre_2022_GRUPO_07;

import java.awt.*;

/**
 * An event object for representing a click on a calendar event.
 */
public class CalendarEventClickEvent extends AWTEvent {

    /** The calendar event that was clicked. */
    private CalendarEvent calendarEvent;

    /**
     * Constructs a new CalendarEventClickEvent with the specified source object and calendar event.
     *
     * @param source The object on which the event initially occurred.
     * @param calendarEvent The calendar event that was clicked.
     */
    public CalendarEventClickEvent(Object source, CalendarEvent calendarEvent) {
        super(source, 0);
        this.calendarEvent = calendarEvent;
    }

    /**
     * Returns the calendar event that was clicked.
     *
     * @return The calendar event that was clicked.
     */
    public CalendarEvent getCalendarEvent() {
        return calendarEvent;
    }
}