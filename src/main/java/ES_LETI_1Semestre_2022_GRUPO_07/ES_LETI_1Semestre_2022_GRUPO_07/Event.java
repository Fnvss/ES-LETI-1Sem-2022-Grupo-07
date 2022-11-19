package ES_LETI_1Semestre_2022_GRUPO_07.ES_LETI_1Semestre_2022_GRUPO_07;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Event implements Comparable<Event> {
	
	Date startDate;
	Date endDate;
	String summary;
	
	List<Element> elements = new ArrayList<>();
	
	
	public Event(Date startDate, Date endDate, String summary) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.summary = summary;
	}
	
	public Event(Date startDate, Date endDate, String summary, Element element) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.summary = summary;
		elements.add(element);
	}

	public Event(Date startDate, Date endDate, String summary, List<Element> elements) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.summary = summary;
		this.elements = elements;
		//elements.add(element);
	}
	
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public List<Element> getElements() {
		return elements;
	}
	public void addElement(Element element) {
		this.elements.add(element);
	}


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

	@Override
	public int compareTo(Event other) {
		if(other.endDate.compareTo(this.startDate) <= 0) 
			return 1;
		if(other.startDate.compareTo(this.endDate) >= 0) 
			return -1;
		return 0;
	}

	@Override
	public String toString() {
		return "Event [Data Ínicio: " + startDate + ", Data fim: " + endDate + ", Cadeira: " + summary + ",elements: "
				+ elements + "]";
	}
	
	
	
}