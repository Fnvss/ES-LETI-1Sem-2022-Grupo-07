package ES_LETI_1Semestre_2022_GRUPO_07.ES_LETI_1Semestre_2022_GRUPO_07;

import java.awt.*;

public class CalendarEventClickEvent extends AWTEvent {

    private CalendarEvent calendarEvent;

    public CalendarEventClickEvent(Object source, CalendarEvent calendarEvent) {
        super(source, 0);
        this.calendarEvent = calendarEvent;
    }

    public CalendarEvent getCalendarEvent() {
        return calendarEvent;
    }
}