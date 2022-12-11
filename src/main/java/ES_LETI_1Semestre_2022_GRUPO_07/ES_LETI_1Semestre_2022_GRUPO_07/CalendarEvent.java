package ES_LETI_1Semestre_2022_GRUPO_07.ES_LETI_1Semestre_2022_GRUPO_07;

import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class CalendarEvent {

    private static final Color DEFAULT_COLOR = Color.PINK;


    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String text;
    private Color color;
    private List<Element> elements = new ArrayList<>();

    public CalendarEvent(LocalDateTime startDate, LocalDateTime endDate, String text, List<Element> elements) {
        this(startDate, endDate, text, elements, DEFAULT_COLOR);
    }

    public CalendarEvent(LocalDateTime startDate, LocalDateTime endDate, String text, List<Element> elements, Color color) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.text = text;
        this.color = color;
        this.elements = elements;
    }


    public LocalDateTime getStart() {
        return startDate;
    }

    public void setStart(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    /**
	 * @return the elements
	 */
	public List<Element> getElements() {
		return elements;
	}

	/**
	 * @param elements the elements to set
	 */
	public void setElements(List<Element> elements) {
		this.elements = elements;
	}

	public LocalDateTime getEnd() {
        return endDate;
    }

    public void setEnd(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String toString() {
        return getStart().toLocalDate() + " " + getStart() + "-" + getEnd() + ". " + getText();
    }

    public Color getColor() {
        return color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CalendarEvent that = (CalendarEvent) o;

        if (!startDate.toLocalDate().equals(that.startDate.toLocalDate())) return false;
        if (!startDate.toLocalTime().equals(that.startDate.toLocalTime())) return false;
        return endDate.toLocalTime().equals(that.endDate.toLocalTime());

    }

    @Override
    public int hashCode() {
        int result = startDate.toLocalDate().hashCode();
        result = 31 * result + startDate.toLocalTime().hashCode();
        result = 31 * result + endDate.toLocalTime().hashCode();
        return result;
    }
}