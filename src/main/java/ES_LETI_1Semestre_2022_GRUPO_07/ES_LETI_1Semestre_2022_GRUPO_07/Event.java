package ES_LETI_1Semestre_2022_GRUPO_07.ES_LETI_1Semestre_2022_GRUPO_07;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Event implements Comparable<Event> {

	LocalDateTime startDate;
	LocalDateTime endDate;
	String summary;

	List<Element> elements = new ArrayList<>();

	public Event(LocalDateTime startDate, LocalDateTime endDate) {
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
	public Event(LocalDateTime startDate, LocalDateTime endDate, String summary) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.summary = summary;
	}

	public Event(LocalDateTime startDate, LocalDateTime endDate, String summary, Element element) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.summary = summary;
		elements.add(element);
	}

	public Event(LocalDateTime startDate, LocalDateTime endDate, String summary, List<Element> elements) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.summary = summary;
		this.elements = elements;
		//elements.add(element);
	}



	/**
	 * @return the start date.
	 */
	public LocalDateTime getStartDate() {
		return startDate;
	}
	
	/**
	 * @param Date the start date to set
	 */
	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}


	/**
	 * @return the end date.
	 */
	public LocalDateTime getEndDate() {
		return endDate;
	}
	
	/**
	 * @param Date the end date to set
	 */
	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}


	/**
	 * @return the summary.
	 */
	public String getSummary() {
		return summary;
	}

	/**
	 * @param summary the summary to set
	 */
	public void setSummary(String summary) {
		this.summary = summary;
	}


	/**
	 * @return the elements list.
	 */
	public List<Element> getElements() {
		return elements;
	}
	

	/**
	 * adds an element to the list of elements.
	 */
	public void addElement(Element element) {
		if(elements.indexOf(element) == -1) {
			this.elements.add(element);
		}
	}


//	@Override
//	public Event clone() throws CloneNotSupportedException {
//		ZonedDateTime clonedStartDate = (ZonedDateTime) this.startDate.clone();
//		ZonedDateTime clonedEndDate = (ZonedDateTime) this.endDate.clone();
//		String clonedSummary = this.summary;
//		List<Element> cloneList = new ArrayList<>();
//		for (Element e: elements) cloneList.add(e.clone());
//		Event cloneEvent = new Event(clonedStartDate, clonedEndDate, clonedSummary, cloneList);
//		return cloneEvent;
//	}
	

	/**
	 * @return a boolean saying if the comparing objects are equal(true) or not(false).
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
	 * @return a negative integer, zero, or a positive integer as this object is less than, equal to, or greater than the specified object.
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
	 * @return a string representing the event.
	 */
	@Override
	public String toString() {
		return "Event [Data Ínicio: " + startDate + ", Data fim: " + endDate + ", Cadeira: " + summary + ",element: "
				+ elements;
	}



}
