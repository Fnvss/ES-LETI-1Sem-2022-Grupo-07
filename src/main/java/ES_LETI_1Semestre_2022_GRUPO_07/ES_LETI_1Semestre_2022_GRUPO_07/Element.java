package ES_LETI_1Semestre_2022_GRUPO_07.ES_LETI_1Semestre_2022_GRUPO_07;

import java.util.Objects;

public class Element {
	
	String name;
	String webLink;
	
	public Element(String name, String webLink) {
		this.webLink = webLink;
		this.name = name;
	}
	

	public String getName() {
		return name;
	}

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


	@Override
	public String toString() {
		return name;
	}
	
	
	
	
}