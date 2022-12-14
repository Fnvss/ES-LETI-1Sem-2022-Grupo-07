package ES_LETI_1Semestre_2022_GRUPO_07.ES_LETI_1Semestre_2022_GRUPO_07;

import java.awt.*;

import java.time.LocalDateTime;

/**
 * An event object for representing a click on an empty date in the calendar.
 */
public class CalendarEmptyClickEvent extends AWTEvent {

	/** The date and time of the click. */
	private LocalDateTime dateTime;

	/**
	 * Constructs a new CalendarEmptyClickEvent with the specified source object and date and time.
	 *
	 * @param source The object on which the event initially occurred.
	 * @param dateTime The date and time of the click.
	 */
	public CalendarEmptyClickEvent(Object source, LocalDateTime dateTime) {
		super(source, 0);
		this.dateTime = dateTime;
	}

	/**
	 * Returns the date and time of the click.
	 *
	 * @return The date and time of the click.
	 */
	public LocalDateTime getDateTime() {
		return dateTime;
	}
}