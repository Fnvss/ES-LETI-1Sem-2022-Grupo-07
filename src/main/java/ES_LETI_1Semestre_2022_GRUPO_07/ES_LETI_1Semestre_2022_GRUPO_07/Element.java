package ES_LETI_1Semestre_2022_GRUPO_07.ES_LETI_1Semestre_2022_GRUPO_07;

import java.util.Objects;

public class Element {
	
	String name;
	String webLink;
	
	public Element(String name, String webLink) {
		this.webLink = webLink;
		this.name = name;
	}
	
	/**
	 * @return the name of a specific element.
	 */
	public String getName() {
		return name;
	}
	
	public String getUrl() {
		return webLink;
	}
	
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public Element clone() throws CloneNotSupportedException {
		String clonedName = this.name;
		String clonedWebLink = this.webLink;
		Element clonedElement = new Element(clonedName, clonedWebLink);
		return clonedElement;
	}

	/**
	 * @return a boolean saying if the comparing objects are equal(true) or not(false).
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (obj instanceof Element) {
			Element other = (Element) obj;
			return this.name.equals(other.name) && this.webLink.equals(other.webLink);
		}
		return false;
	}


	/**
	 * @return a string with the name of the element.
	 */
	@Override
	public String toString() {
		return name;
	}
	
	
	
	
}
