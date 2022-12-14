package ES_LETI_1Semestre_2022_GRUPO_07.ES_LETI_1Semestre_2022_GRUPO_07;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.JComboBox;

/**
 * Represents a calendar that displays a week view.
 */
public class WeekCalendar extends Calendar {

	/** The week that is currently being displayed on the calendar. */
	private Week week;

	/**
	 * Constructs a new `WeekCalendar` instance with the specified events.
	 * 
	 * @param events the events to be displayed on the calendar
	 */
	public WeekCalendar(ArrayList<CalendarEvent> events) {
		super(events);

		week = new Week(LocalDate.now());
	}


	/**
	 * Determines whether the specified date is within the currently displayed week.
	 *
	 * @param date the date to check
	 * @return `true` if the specified date is within the currently displayed week, `false` otherwise
	 */
	@Override
	protected boolean dateInRange(LocalDate date) {
		return Week.getStartOfWeek(date).equals(week.getDay(DayOfWeek.MONDAY));
	}
	
	
	/**
     * Gets the date corresponding to the specified day of the week in the currently displayed week.
     *
     * @param day the day of the week to get the date for
     * @return the date corresponding to the specified day of the week in the currently displayed week
     */
	@Override
	protected LocalDate getDateFromDay(DayOfWeek day) {
		return week.getDay(day);
	}
	
	
	/**
     * Gets the number of days that should be displayed in the calendar.
     * 
     * @return the number of days to display (7 for a week view)
     */
	protected int numDaysToShow() {
		return 7;
	}
	
	
	/**
     * Gets the day of the week that the calendar should start on.
     * 
     * @return the day of the week that the calendar should start on
     */
	@Override
	protected DayOfWeek getStartDay() {
		return DayOfWeek.MONDAY;
	}
	
	
	/**
     * Gets the day of the week that the calendar should end on.
     * 
     * @return the day of the week that the calendar should end on
     */
	@Override
	protected DayOfWeek getEndDay() {
		return DayOfWeek.SUNDAY;
	}
	
	
	/**
     * Sets the current week to the current date.
     */
    protected void setRangeToToday() {
        week = new Week(LocalDate.now());
    }

    
    /**
     * Converts a given day of the week to a pixel value.
     *
     * @param dayOfWeek the day of the week to convert
     * @return the pixel value corresponding to the given day of the week
     */
    protected double dayToPixel(DayOfWeek dayOfWeek) {
        return TIME_COL_WIDTH + getDayWidth() * (dayOfWeek.getValue() - 1);
    }

    
    /**
     * Advances the current week by one week.
     */
    public void nextWeek() {
        week = week.nextWeek();
        repaint();
    }

    
    /**
     * Goes back one week from the current week.
     */
    public void prevWeek() {
        week = week.prevWeek();
        repaint();
    }

    
    /**
     * Advances the current week by one month.
     */
    public void nextMonth() {
        week = new Week(week.getDay(DayOfWeek.MONDAY).plusWeeks(4));
        repaint();
    }

    
    /**
     * Goes back one month from the current week.
     */
    public void prevMonth() {
        week = new Week(week.getDay(DayOfWeek.MONDAY).minusWeeks(4));
        repaint();
    }

}