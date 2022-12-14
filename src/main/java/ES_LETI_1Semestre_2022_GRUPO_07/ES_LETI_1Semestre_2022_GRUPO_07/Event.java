package ES_LETI_1Semestre_2022_GRUPO_07.ES_LETI_1Semestre_2022_GRUPO_07;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for representing a calendar event.
 */
public class Event implements Comparable<Event> {

	/** The start date of the event. */
	private LocalDateTime startDate;

	/** The end date of the event. */
	private LocalDateTime endDate;

	/** The summary of the event. */
	private String summary;

	/** The list of elements associated with the event. */
	List<Element> elements = new ArrayList<>();


	/**
	 * Constructs a new Event with the specified start and end dates.
	 *
	 * @param startDate The start date of the event.
	 * @param endDate The end date of the event.
	 */
	public Event(LocalDateTime startDate, LocalDateTime endDate) {
		this.startDate = startDate;
		this.endDate = endDate;
	}


	/**
	 * Constructs a new Event with the specified start and end dates, and summary.
	 *
	 * @param startDate The start date of the event.
	 * @param endDate The end date of the event.
	 * @param summary The summary of the event.
	 */
	public Event(LocalDateTime startDate, LocalDateTime endDate, String summary) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.summary = summary;
	}


	/**
	 * Constructs a new Event with the specified start and end dates, summary, and associated element.
	 *
	 * @param startDate The start date of the event.
	 * @param endDate The end date of the event.
	 * @param summary The summary of the event.
	 * @param element The element associated with the event.
	 */
	public Event(LocalDateTime startDate, LocalDateTime endDate, String summary, Element element) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.summary = summary;
		elements.add(element);
	}


	/**
	 * Constructs a new Event with the specified start and end dates, summary, and list of associated elements.
	 *
	 * @param startDate The start date of the event.
	 * @param endDate The end date of the event.
	 * @param summary The summary of the event.
	 * @param elements The list of elements associated with the event.
	 */
	public Event(LocalDateTime startDate, LocalDateTime endDate, String summary, List<Element> elements) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.summary = summary;
		this.elements = elements;
	}


	/**
	 * Gets the start date of this Event.
	 *
	 * @return The start date of this Event.
	 */
	public LocalDateTime getStartDate() {
		return startDate;
	}


	/**
	 * Sets the start date of this Event.
	 *
	 * @param startDate The new start date for this Event.
	 */
	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}


	/**
	 * Gets the end date of this Event.
	 *
	 * @return The end date of this Event.
	 */
	public LocalDateTime getEndDate() {
		return endDate;
	}


	/**
	 * Sets the end date of this Event.
	 *
	 * @param endDate The new end date for this Event.
	 */
	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}


	/**
	 * Gets the summary of this Event.
	 *
	 * @return The summary of this Event.
	 */
	public String getSummary() {
		return summary;
	}


	/**
	 * Sets the summary of this Event.
	 *
	 * @param summary The new summary for this Event.
	 */
	public void setSummary(String summary) {
		this.summary = summary;
	}


	/**
	 * Gets the list of elements associated with this Event.
	 *
	 * @return The list of elements associated with this Event.
	 */
	public List<Element> getElements() {
		return elements;
	}


	/**
	 * Adds an element to the list of elements associated with this Event.
	 */
	public void addElement(Element element) {
		if(elements.indexOf(element) == -1) {
			this.elements.add(element);
		}
	}

	
	/**
	 * Checks if the current event collides with another event.
	 * 
	 * @param other
	 * @return
	 */
	public boolean collidesWithEvent(Event other) {
		
		//Check if both Events are on the same month
		if(this.getStartDate().getMonth().equals(other.getStartDate().getMonth())) {
			
			//Check if both Events are on the same day
			if(this.getStartDate().getDayOfMonth() == other.getStartDate().getDayOfMonth()) {

				//Check if both Events are on the same time
				if(other.getStartDate().isEqual(this.getStartDate())) {
					return true;
				}

				//Check if the other Event starts before this Event and ends after this event
				if(other.getStartDate().isBefore(this.getStartDate()) 
						&& other.getEndDate().isAfter(this.getEndDate())) {
					return true;
				}
				
				//Check if the other Event starts before this Event and ends during this event
				if(other.getStartDate().isBefore(this.getStartDate()) 
						&& other.getEndDate().isAfter(this.getStartDate()) 
						&& other.getEndDate().isBefore(this.getEndDate())) {
					return true;
				}
				
				//Check if the other Event starts during this Event and ends after this event
				if(other.getStartDate().isAfter(this.getStartDate()) 
						&& other.getStartDate().isBefore(this.getEndDate()) 
						&& other.getEndDate().isAfter(this.getEndDate())) {

					return true;
				}
				
				//Check if the other Event starts during this Event and ends during this event
				if(other.getStartDate().isAfter(this.getStartDate()) 
						&& other.getEndDate().isBefore(this.getEndDate())) {
					return true;
				}
			}
		}
		return false;
	}


	/**
	 * Determines whether two Event objects are equal.
	 * 
	 * @param obj the object to compare this Event to
	 * @return true if the objects are equal, false otherwise
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Event other = (Event) obj;
		return endDate.equals(other.endDate) && startDate.equals(other.startDate)
				&& summary.equals(other.summary);
	}


	/**
	 * Compares this Event to another Event.
	 * 
	 * @param other the Event to compare this Event to
	 * @return a negative integer, zero, or a positive integer as this Event is before date, equal to, or greater than the specified date
	 */
	@Override
	public int compareTo(Event other) {
		if(other.endDate.compareTo(this.startDate) <= 0) 
			return 1;
		if(other.startDate.compareTo(this.endDate) >= 0) 
			return -1;
		return 0;
	}

	/**
	 * Returns a string representation of this Event.
	 * 
	 * @return a string representing the Event
	 */
	@Override
	public String toString() {
		return "Event [Data Ínicio: " + startDate + ", Data fim: " + endDate + ", Evento: " + summary + ",elements: "
				+ elements;
	}



}
