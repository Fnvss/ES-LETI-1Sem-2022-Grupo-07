package ES_LETI_1Semestre_2022_GRUPO_07.ES_LETI_1Semestre_2022_GRUPO_07;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents an event on the calendar
 */
public class CalendarEvent {

	/** The default color for a CalendarEvent. */
	private static final Color DEFAULT_COLOR = Color.PINK;

	/** The date and time when the event starts. */
	private LocalDateTime startDate;

	/** The date and time when the event ends. */
	private LocalDateTime endDate;

	/** The text description of the event. */
	private String text;

	/** The color of the event. */
	private Color color;

	/** A list of elements associated with the event. */
	private List<Element> elements = new ArrayList<>();


	/**
	 * Creates a new CalendarEvent with the given start and end dates and times, text description, and list of elements.
	 * The default color is used.
	 * 
	 * @param startDate The start date and time of the event
	 * @param endDate The end date and time of the event
	 * @param text The text description of the event
	 * @param elements The list of elements associated with the event
	 */
	public CalendarEvent(LocalDateTime startDate, LocalDateTime endDate, String text, List<Element> elements) {
		this(startDate, endDate, text, elements, DEFAULT_COLOR);
	}


	/**
	 * Creates a new CalendarEvent with the given start and end dates and times, text description, list of elements, and color.
	 * 
	 * @param startDate The start date and time of the event
	 * @param endDate The end date and time of the event
	 * @param text The text description of the event
	 * @param elements The list of elements associated with the event
	 * @param color The color of the event
	 */
	public CalendarEvent(LocalDateTime startDate, LocalDateTime endDate, String text, List<Element> elements, Color color) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.text = text;
		this.color = color;
		this.elements = elements;
	}

	
	/**
	 * Gets the start date and time.
	 *
	 * @return the start date and time as a LocalDateTime
	 */
	public LocalDateTime getStart() {
	    return startDate;
	}

	
	/**
	 * Sets the start date and time.
	 *
	 * @param startDate the start date and time to set, as a LocalDateTime
	 */
	public void setStart(LocalDateTime startDate) {
	    this.startDate = startDate;
	}

	
	/**
	 * Gets the list of elements.
	 *
	 * @return the list of elements
	 */
	public List<Element> getElements() {
	    return elements;
	}

	
	/**
	 * Sets the list of elements.
	 *
	 * @param elements the list of elements to set
	 */
	public void setElements(List<Element> elements) {
	    this.elements = elements;
	}

	
	/**
	 * Gets the end date and time.
	 *
	 * @return the end date and time as a LocalDateTime
	 */
	public LocalDateTime getEnd() {
	    return endDate;
	}

	
	/**
	 * Sets the end date and time.
	 *
	 * @param endDate the end date and time to set, as a LocalDateTime
	 */
	public void setEnd(LocalDateTime endDate) {
	    this.endDate = endDate;
	}

	
	/**
	 * Gets the text.
	 *
	 * @return the text
	 */
	public String getText() {
	    return text;
	}

	
	/**
	 * Sets the text.
	 *
	 * @param text the text to set
	 */
	public void setText(String text) {
	    this.text = text;
	}

	
	/**
	 * Returns a string representation of this object.
	 *
	 * @return a string representation of this object
	 */
	public String toString() {
	    return getStart().toLocalDate() + " " + getStart() + "-" + getEnd() + ". " + getText();
	}

	
	/**
	 * Gets the color.
	 *
	 * @return the color
	 */
	public Color getColor() {
		return color;
	}
	
	/**
	 * Compares the given object to this calendar event and returns true if they are equal.
	 *
	 * @param o The object to compare to this calendar event
	 * @return true if the given object is equal to this calendar event, false otherwise
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		CalendarEvent that = (CalendarEvent) o;

		if (!startDate.toLocalDate().equals(that.startDate.toLocalDate())) return false;
		if (!startDate.toLocalTime().equals(that.startDate.toLocalTime())) return false;
		return endDate.toLocalTime().equals(that.endDate.toLocalTime());

	}

	
	/**
	 * Returns a hash code value for this calendar event.
	 *
	 * @return a hash code value for this calendar event
	 */
	@Override
	public int hashCode() {
		int result = startDate.toLocalDate().hashCode();
		result = 31 * result + startDate.toLocalTime().hashCode();
		result = 31 * result + endDate.toLocalTime().hashCode();
		return result;
	}
}