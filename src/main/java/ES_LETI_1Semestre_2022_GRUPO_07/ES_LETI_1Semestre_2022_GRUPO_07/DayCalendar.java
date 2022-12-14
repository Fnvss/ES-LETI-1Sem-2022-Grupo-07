package ES_LETI_1Semestre_2022_GRUPO_07.ES_LETI_1Semestre_2022_GRUPO_07;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * A calendar that shows events for a single day.
 */
public class DayCalendar extends Calendar {

	/** The day that is currently being displayed on the calendar.*/
	private LocalDate calDate;

	/**
	 * Constructs a new DayCalendar instance with the given list of events and the given date.
	 *
	 * @param events the list of events to be shown on the calendar
	 * @param date the date for which events will be shown
	 */
	public DayCalendar(ArrayList<CalendarEvent> events, LocalDate date) {
		super(events);
		calDate = date;
	}


	/**
	 * Checks if the given date is within the range of dates to show on the calendar.
	 * This implementation checks if the given date is the same as the current date stored in the calDate field.
	 *
	 * @param date the date to check
	 * @return true if the given date is within the range of dates to show on the calendar,
	 *         false otherwise
	 */
	@Override
	protected boolean dateInRange(LocalDate date) {
		return calDate.equals(date);
	}


	/**
	 * Gets the date associated with the given day of the week.
	 * This implementation always returns the current date stored in the calDate field.
	 *
	 * @param day the day of the week
	 * @return the date associated with the given day of the week
	 */
	@Override
	protected LocalDate getDateFromDay(DayOfWeek day) {
		return calDate;
	}


	/**
	 * Gets the number of days to show on the calendar.
	 * This implementation always returns 1, indicating that only the current day
	 * stored in the calDate field should be shown on the calendar.
	 *
	 * @return the number of days to show on the calendar
	 */
	@Override
	protected int numDaysToShow() {
		return 1;
	}


	/**
	 * Gets the day of the week that the calendar should start on.
	 * This implementation returns the day of the week of the current date stored in
	 * the calDate field.
	 *
	 * @return the day of the week that the calendar should start on
	 */
	@Override
	protected DayOfWeek getStartDay() {
		return calDate.getDayOfWeek();
	}
	
	
	/**
	 * Gets the day of the week that the calendar should end on.
	 * This implementation returns the day of the week of the current date stored in
	 * the calDate field.
	 *
	 * @return the day of the week that the calendar should end on
	 */
	@Override
	protected DayOfWeek getEndDay() {
		return calDate.getDayOfWeek();
	}
	
	
	/**
	 * Sets the range of dates to show on the calendar to the current date.
	 * This implementation sets the current date stored in the calDate field
	 */
	@Override
	protected void setRangeToToday() {
		calDate = LocalDate.now();
	}

	
	/**
     * Converts a given day to a pixel value.
     *
     * @param dayOfWeek the day to convert
     * @return the pixel value corresponding to the given day 
     */
	@Override
	protected double dayToPixel(DayOfWeek dayOfWeek) {
		return TIME_COL_WIDTH;
	}
	
	
	/**
     * Advances to the next day.
     */
	public void nextDay() {
		calDate = calDate.plusDays(1);
		repaint();
	}

	
	/**
     * Advances to the previous day.
     */
	public void prevDay() {
		calDate = calDate.minusDays(1);
		repaint();
	}
}