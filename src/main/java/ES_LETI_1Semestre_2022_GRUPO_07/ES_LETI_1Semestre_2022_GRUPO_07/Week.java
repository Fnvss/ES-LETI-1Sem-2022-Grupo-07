package ES_LETI_1Semestre_2022_GRUPO_07.ES_LETI_1Semestre_2022_GRUPO_07;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;


/**
 * Represents a week in time.
 */
public class Week {

	/** The dates of the days in this week. */
	private ArrayList<LocalDate> days;

	
	/**
	 * Creates a new `Week` object with the given date.
	 *
	 * The date does not have to be the start of the week. The dates of the days
	 * in the week will be determined based on the given date.
	 *
	 * @param date a date within the week
	 */
	public Week(LocalDate date) {
		days = new ArrayList<>();
		LocalDate monday = getStartOfWeek(date);
		days.add(monday);
		for (int i = 1; i < 7; i++) {
			days.add(monday.plusDays(i));
		}
	}

	
	/**
     * Returns the start of the week containing the given date.
     *
     * @param date a date within the week
     * @return the start of the week
     */
	public static LocalDate getStartOfWeek(LocalDate date) {
		LocalDate day = date;
		while (day.getDayOfWeek() != DayOfWeek.MONDAY) {
			day = day.minusDays(1);
		}
		return day;
	}
	
	
	/**
     * Returns the date of the specified day of the week in this week.
     *
     * @param dayOfWeek the day of the week
     * @return the date of the specified day of the week
     */
	public LocalDate getDay(DayOfWeek dayOfWeek) {
		// DayOfWeek enum starts with monday == 1
		return days.get(dayOfWeek.getValue() - 1);
	}
	
	
	/**
     * Returns the next week after this week.
     *
     * @return the next week
     */
	public Week nextWeek() {
		final LocalDate sunday = getDay(DayOfWeek.SUNDAY);
		return new Week(sunday.plusDays(1));
	}
	
	
	/**
     * Returns the previous week before this week.
     *
     * @return the previous week
     */
	public Week prevWeek() {
		final LocalDate monday = getDay(DayOfWeek.MONDAY);
		return new Week(monday.minusDays(1));
	}
	
	
	/**
     * Returns a string representation of this week.
     *
     * The string representation includes the start date of the week.
     *
     * @return a string representation of this week
     */
	public String toString() {
		return "Week of the " + getDay(DayOfWeek.MONDAY);
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Week that = (Week) o;
		return days.equals(that.days);
	}
	
}