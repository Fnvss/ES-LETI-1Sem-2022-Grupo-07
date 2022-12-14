package ES_LETI_1Semestre_2022_GRUPO_07.ES_LETI_1Semestre_2022_GRUPO_07;

import javax.swing.*;
import javax.swing.event.EventListenerList;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Locale;

/**
 * An abstract base class for creating a graphical calendar component.
 * Maintains a list of events and provides an interface for adding and removing events. 
 * Has a number of methods that handle the rendering of the calendar and the handling of user input such as clicking on events.
 */
public abstract class Calendar extends JComponent {
	protected static final LocalTime START_TIME = LocalTime.of(7, 0);
	protected static final LocalTime END_TIME = LocalTime.of(20, 59);
	protected static final int MIN_WIDTH = 600;
	protected static final int MIN_HEIGHT = MIN_WIDTH;
	protected static final int HEADER_HEIGHT = 30;
	protected static final int TIME_COL_WIDTH = 100;
	private static final int FONT_LETTER_PIXEL_WIDTH = 7; // An estimate of the width of a single character

	/** The list of events to be displayed in the calendar. */
	private ArrayList<CalendarEvent> events;

	/** The scale used to convert time to pixels. */
	private double timeScale;

	/** The width of each day column in pixels. */
	private double dayWidth;

	/** The `Graphics2D` object used for rendering the calendar.*/
	private Graphics2D g2;

	/** The list of registered event listeners.*/
	private EventListenerList listenerList = new EventListenerList();


	/**
	 * Creates a new `Calendar` instance with an empty list of events.
	 */
	public Calendar() {
		this(new ArrayList<>());
	}


	/**
	 * Creates a new `Calendar` instance with the specified list of events.
	 *
	 * @param events the list of events to be displayed in the calendar
	 */
	Calendar(ArrayList<CalendarEvent> events) {
		this.events = events;
		setupEventListeners();
		setupTimer();
	}


	/**
	 * Rounds a LocalTime object to the nearest multiple of the specified
	 * number of minutes.
	 *
	 * @param time the time to round
	 * @param minutes the number of minutes to round to
	 * @return the rounded time
	 */
	public static LocalTime roundTime(LocalTime time, int minutes) {
		LocalTime t = time;

		if (t.getMinute() % minutes > minutes / 2) {
			t = t.plusMinutes(minutes - (t.getMinute() % minutes));
		} else if (t.getMinute() % minutes < minutes / 2) {
			t = t.minusMinutes(t.getMinute() % minutes);
		}

		return t;
	}


	/**
	 * Sets up mouse event listeners for the calendar. When the mouse is clicked,
	 * the method will first check if the click was on a calendar event by calling
	 * the {@link #checkCalendarEventClick(Point)} method. If the click was not on
	 * a calendar event, the method will call the {@link #checkCalendarEmptyClick(Point)}
	 * method.
	 */
	private void setupEventListeners() {
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				if (!checkCalendarEventClick(e.getPoint())) {
					checkCalendarEmptyClick(e.getPoint());
				}
			}
		});
	}


	/**
	 * Determines whether a date falls within a certain range.
	 *
	 * @param date the date to check
	 * @return true if the date falls within the range, false otherwise
	 */
	protected abstract boolean dateInRange(LocalDate date);


	/**
	 * Checks if a mouse click occurred on a calendar event.
	 *
	 * @param p the coordinates of the mouse click
	 * @return true if the click occurred on a calendar event, false otherwise
	 */
	private boolean checkCalendarEventClick(Point p) {
		double x0, x1, y0, y1;
		for (CalendarEvent event : events) {
			if (!dateInRange(event.getStart().toLocalDate())) continue;

			x0 = dayToPixel(event.getStart().toLocalDate().getDayOfWeek());
			y0 = timeToPixel(event.getStart().toLocalTime());
			x1 = dayToPixel(event.getStart().toLocalDate().getDayOfWeek()) + dayWidth;
			y1 = timeToPixel(event.getEnd().toLocalTime());

			if (p.getX() >= x0 && p.getX() <= x1 && p.getY() >= y0 && p.getY() <= y1) {
				fireCalendarEventClick(event);
				return true;
			}
		}
		return false;
	}


	/**
	 * Checks if a mouse click occurred on an empty part of the calendar.
	 *
	 * @param p the coordinates of the mouse click
	 * @return true if the click occurred on an empty part of the calendar,
	 *         false otherwise
	 */
	private boolean checkCalendarEmptyClick(Point p) {
		final double x0 = dayToPixel(getStartDay());
		final double x1 = dayToPixel(getEndDay()) + dayWidth;
		final double y0 = timeToPixel(START_TIME);
		final double y1 = timeToPixel(END_TIME);

		if (p.getX() >= x0 && p.getX() <= x1 && p.getY() >= y0 && p.getY() <= y1) {
			LocalDate date = getDateFromDay(pixelToDay(p.getX()));
			fireCalendarEmptyClick(LocalDateTime.of(date, pixelToTime(p.getY())));
			return true;
		}
		return false;
	}


	/**
	 * Determines the date corresponding to a given day of the week.
	 *
	 * @param day the day of the week
	 * @return the date corresponding to the given day
	 */
	protected abstract LocalDate getDateFromDay(DayOfWeek day);


	/**
	 * Registers a listener for CalendarEventClickListener
	 * 
	 * @param l the listener to register
	 */
	public void addCalendarEventClickListener(CalendarEventClickListener l) {
		listenerList.add(CalendarEventClickListener.class, l);
	}


	/**
	 * Unregisters a listener for CalendarEventClickListener
	 * 
	 * @param l the listener to unregister
	 */
	public void removeCalendarEventClickListener(CalendarEventClickListener l) {
		listenerList.remove(CalendarEventClickListener.class, l);
	}


	/**
	 * Notify all listeners that have registered interest for notification on this event type.
	 * 
	 * @param calendarEvent
	 */
	private void fireCalendarEventClick(CalendarEvent calendarEvent) {
		// Guaranteed to return a non-null array
		Object[] listeners = listenerList.getListenerList();
		// Process the listeners last to first, notifying
		// those that are interested in this event
		CalendarEventClickEvent calendarEventClickEvent;
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == CalendarEventClickListener.class) {
				calendarEventClickEvent = new CalendarEventClickEvent(this, calendarEvent);
				((CalendarEventClickListener) listeners[i + 1]).calendarEventClick(calendarEventClickEvent);
			}
		}
	}


	/**
	 * Registers a listener for CalendarEmptyClick events.
	 *
	 * @param l the listener to register
	 */
	public void addCalendarEmptyClickListener(CalendarEmptyClickListener l) {
		listenerList.add(CalendarEmptyClickListener.class, l);
	}


	/**
	 * Unregisters a listener for CalendarEmptyClick events.
	 *
	 * @param l the listener to unregister
	 */
	public void removeCalendarEmptyClickListener(CalendarEmptyClickListener l) {
		listenerList.remove(CalendarEmptyClickListener.class, l);
	}


	/**
	 * Fires a CalendarEmptyClick event to all registered listeners.
	 *
	 * @param dateTime the date and time of the event
	 */
	private void fireCalendarEmptyClick(LocalDateTime dateTime) {
		Object[] listeners = listenerList.getListenerList();
		CalendarEmptyClickEvent calendarEmptyClickEvent;
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == CalendarEmptyClickListener.class) {
				calendarEmptyClickEvent = new CalendarEmptyClickEvent(this, dateTime);
				((CalendarEmptyClickListener) listeners[i + 1]).calendarEmptyClick(calendarEmptyClickEvent);
			}
		}
	}


	/**
	 * Calculates the scaling variables used to convert between calendar units
	 * (e.g., days of the week, times of day) and pixel coordinates on the screen.
	 */
	private void calculateScaleVars() {
		int width = getWidth();
		int height = getHeight();

		if (width < MIN_WIDTH) {
			width = MIN_WIDTH;
		}

		if (height < MIN_HEIGHT) {
			height = MIN_HEIGHT;
		}

		// Units are pixels per second
		timeScale = (double) (height - HEADER_HEIGHT) / (END_TIME.toSecondOfDay() - START_TIME.toSecondOfDay());
		dayWidth = (width - TIME_COL_WIDTH) / numDaysToShow();
	}


	/**
	 * Determines the number of days that should be shown on the calendar.
	 *
	 * @return the number of days to show
	 */
	protected abstract int numDaysToShow();


	/**
	 *  Gives x val of left most pixel for day col
	 *  
	 * @param dayOfWeek
	 * @return
	 */
	protected abstract double dayToPixel(DayOfWeek dayOfWeek);


	/**
	 * Converts a LocalTime object to a pixel position on the screen.
	 *
	 * @param time the LocalTime object to convert
	 * @return the pixel position corresponding to the given time
	 */
	private double timeToPixel(LocalTime time) {
		return ((time.toSecondOfDay() - START_TIME.toSecondOfDay()) * timeScale) + HEADER_HEIGHT;
	}


	/**
	 * Converts a pixel position to a LocalTime object.
	 *
	 * @param y the pixel position to convert
	 * @return the LocalTime object corresponding to the given pixel position
	 */
	private LocalTime pixelToTime(double y) {
		return LocalTime.ofSecondOfDay((int) ((y - HEADER_HEIGHT) / timeScale) + START_TIME.toSecondOfDay()).truncatedTo(ChronoUnit.MINUTES);
	}


	/**
	 * Converts a pixel value on a screen to a day of the week.
	 *
	 * @param x the pixel value to convert
	 * @return the day of the week corresponding to the input pixel value, or null if the value doesn't fall within the range of any of the days of the week
	 */
	private DayOfWeek pixelToDay(double x) {
		double pixel;
		DayOfWeek day;
		for (int i = getStartDay().getValue(); i <= getEndDay().getValue(); i++) {
			day = DayOfWeek.of(i);
			pixel = dayToPixel(day);
			if (x >= pixel && x < pixel + dayWidth) {
				return day;
			}
		}
		return null;
	}


	/**
	 * Repaints the panel by drawing various elements on it, including the day headings, a grid, the events, and the current time line.
	 *
	 * @param g the graphics context to use for drawing
	 */
	@Override
	protected void paintComponent(Graphics g) {
		calculateScaleVars();
		g2 = (Graphics2D) g;

		// Rendering hints try to turn anti-aliasing on which improves quality
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		// Set background to white
		g2.setColor(Color.white);
		g2.fillRect(0, 0, getWidth(), getHeight());

		// Set paint colour to black
		g2.setColor(Color.black);

		drawDayHeadings();
		drawTodayShade();
		drawGrid();
		drawTimes();
		drawEvents();
		drawCurrentTimeLine();
	}

	protected abstract DayOfWeek getStartDay();
	protected abstract DayOfWeek getEndDay();


	/**
	 * Draws the day headings on the panel, consisting of the day of the week in short form and the date in the format "day/month".
	 */
	private void drawDayHeadings() {
		int y = 20;
		int x;
		LocalDate day;
		DayOfWeek dayOfWeek;

		for (int i = getStartDay().getValue(); i <= getEndDay().getValue(); i++) {
			dayOfWeek = DayOfWeek.of(i);
			day = getDateFromDay(dayOfWeek);

			String text = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.ENGLISH) + " " + day.getDayOfMonth() + "/" + day.getMonthValue();
			x = (int) (dayToPixel(DayOfWeek.of(i)) + (dayWidth / 2) - (FONT_LETTER_PIXEL_WIDTH * text.length() / 2));
			g2.drawString(text, x, y);
		}
	}


	/**
	 * Draws a grid on the panel consisting of horizontal and vertical lines, with horizontal lines for every 30 minutes and vertical lines for each day of the week.
	 */
	private void drawGrid() {
		// Save the original colour
		final Color ORIG_COLOUR = g2.getColor();

		// Set colour to grey with half alpha (opacity)
		Color alphaGray = new Color(128, 128, 128, 128);
		Color alphaGrayLighter = new Color(200, 200, 200, 128);
		g2.setColor(alphaGray);

		// Draw vertical grid lines
		double x;
		for (int i = getStartDay().getValue(); i <= getEndDay().getValue(); i++) {
			x = dayToPixel(DayOfWeek.of(i));
			g2.draw(new Line2D.Double(x, HEADER_HEIGHT, x, timeToPixel(END_TIME)));
		}

		// Draw horizontal grid lines
		double y;
		int x1;
		for (LocalTime time = START_TIME; time.compareTo(END_TIME) <= 0; time = time.plusMinutes(30)) {
			y = timeToPixel(time);
			if (time.getMinute() == 0) {
				g2.setColor(alphaGray);
				x1 = 0;
			} else {
				g2.setColor(alphaGrayLighter);
				x1 = TIME_COL_WIDTH;
			}
			g2.draw(new Line2D.Double(x1, y, dayToPixel(getEndDay()) + dayWidth, y));
		}

		// Reset the graphics context's colour
		g2.setColor(ORIG_COLOUR);
	}

	/**
	 * Draws a translucent blue rectangle on the panel to indicate the current date.
	 */
	private void drawTodayShade() {
		LocalDate today = LocalDate.now();

		// Check that date range being viewed is current date range
		if (!dateInRange(today)) return;

		final double x = dayToPixel(today.getDayOfWeek());
		final double y = timeToPixel(START_TIME);
		final double width = dayWidth;
		final double height = timeToPixel(END_TIME) - timeToPixel(START_TIME);

		final Color origColor = g2.getColor();
		Color lightBlue = new Color(220, 220, 255, 128);
		g2.setColor(lightBlue);
		g2.fill(new Rectangle2D.Double(x, y, width, height));
		g2.setColor(origColor);
	}

	/**
	 * Draws a vertical line on the panel to indicate the current time.
	 */
	private void drawCurrentTimeLine() {
		LocalDate today = LocalDate.now();

		// Check that date range being viewed is current date range
		if (!dateInRange(today)) return;

		final double x0 = dayToPixel(today.getDayOfWeek());
		final double x1 = dayToPixel(today.getDayOfWeek()) + dayWidth;
		final double y = timeToPixel(LocalTime.now());

		final Color origColor = g2.getColor();
		final Stroke origStroke = g2.getStroke();

		g2.setColor(new Color(255, 127, 110));
		g2.setStroke(new BasicStroke(2));
		g2.draw(new Line2D.Double(x0, y, x1, y));

		g2.setColor(origColor);
		g2.setStroke(origStroke);
	}

	/**
	 * Draws the hours on the left side of the calendar.
	 */
	private void drawTimes() {
		int y;
		for (LocalTime time = START_TIME; time.compareTo(END_TIME) <= 0; time = time.plusHours(1)) {
			y = (int) timeToPixel(time) + 15;
			g2.drawString(time.toString(), TIME_COL_WIDTH - (FONT_LETTER_PIXEL_WIDTH * time.toString().length()) - 5, y);
		}
	}
	
	/**
	 * Draws the events on the calendar.
	*/
	private void drawEvents() {
		double x;
		double y0;

		for (CalendarEvent event : events) {
			if (!dateInRange(event.getStart().toLocalDate())) continue;

			x = dayToPixel(event.getStart().toLocalDate().getDayOfWeek());
			y0 = timeToPixel(event.getStart().toLocalTime());

			Rectangle2D rect = new Rectangle2D.Double(x, y0, dayWidth, (timeToPixel(event.getEnd().toLocalTime()) - timeToPixel(event.getStart().toLocalTime())));
			Color origColor = g2.getColor();
			g2.setColor(event.getColor());
			g2.fill(rect);
			g2.setColor(origColor);

			// Draw time header
			// Store the current font state
			Font origFont = g2.getFont();

			final float fontSize = origFont.getSize() - 1.6F;

			// Create a new font with same properties but bold
			Font newFont = origFont.deriveFont(Font.BOLD, fontSize);
			g2.setFont(newFont);

			g2.drawString(event.getStart().toLocalTime() + " - " + event.getEnd().toLocalTime(), (int) x + 5, (int) y0 + 11);

			// Unbolden
			g2.setFont(origFont.deriveFont(fontSize));

			// Draw the event's text
			g2.drawString(event.getText(), (int) x + 5, (int) y0 + 23);

			g2.drawString(event.getElements().toString(), (int) x + 5, (int) y0 + 40);

			// Reset font
			g2.setFont(origFont);
		}
	}
	
	/**
	 * Gets dayWidth
	 * 
	 * @return dayWidth value
	 */
	protected double getDayWidth() {
		return dayWidth;
	}


	//Repaints every minute to update the current time line
	private void setupTimer() {
		Timer timer = new Timer(1000*60, e -> repaint());
		timer.start();
	}

	protected abstract void setRangeToToday();

	/** Go to today */
	public void goToToday() {
		setRangeToToday();
		repaint();
	}

	/**
	 * Add the event to the list of events
	 * 
	 * @param event the event to be added
	 */
	public void addEvent(CalendarEvent event) {
		events.add(event);
		repaint();
	}

	/**
	 * Remove the event to the list of events
	 * 
	 * @param event the event to be removed
	 */
	public boolean removeEvent(CalendarEvent event) {
		boolean removed = events.remove(event);
		repaint();
		return removed;
	}

	/**
	 * Set the list with CalendarEvent
	 * 
	 * @param events the list with events
	 */
	public void setEvents(ArrayList<CalendarEvent> events) {
		this.events = events;
		repaint();
	}
}