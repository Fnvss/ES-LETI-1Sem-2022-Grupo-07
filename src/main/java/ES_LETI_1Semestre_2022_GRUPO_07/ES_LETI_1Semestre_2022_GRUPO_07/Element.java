package ES_LETI_1Semestre_2022_GRUPO_07.ES_LETI_1Semestre_2022_GRUPO_07;

import java.net.MalformedURLException;
import java.net.URL;


/**
 * Represents an element with a name and a web link.
 *
 */
public class Element {
	
	String name;
	String webLink;
	
	/**
	 * Constructs a new Element with the given name and web link.
	 *
	 * @param name the name of the element.
	 * @param webLink the web link of the element.
	 */
	public Element(String name, String webLink) {
		this.webLink = webLink;
		this.name = name;
	}
	
	/**
	 * Return the name of the element.
	 * 
	 * @return the name of the element.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Returns the web link.
	 *
	 * @return the web link of the element.
	 */
	public String getWebLink() {
		return webLink;
	}
	
	/**
	 * Sets the name of this person.
	 *
	 * @param name the new name for the element.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Creates a new Element object with the same name and web link
	 * as the original Element object.
	 * 
	 * @return a new Element object with the same name and web link
	 *         as the original Element object
	 * @throws CloneNotSupportedException if the Element class does not
	 *         implement the Cloneable interface
	 */
	@Override
	public Element clone() throws CloneNotSupportedException {
		String clonedName = this.name;
		String clonedWebLink = this.webLink;
		Element clonedElement = new Element(clonedName, clonedWebLink);
		return clonedElement;
	}

	/**
	 * Determines whether two Element objects are equal by comparing
	 * their name and web link fields.
	 * 
	 * @param obj the object to compare with the current Element object
	 * @return true if the name and web link fields of the current Element
	 *         object and the obj parameter are equal, and false if they are not
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
	 * Returns a URL object for the given web link.
	 *
	 * @param webLink the web link (or URL) as a string.
	 * @return the URL object for the given web link.
	 * @throws MalformedURLException if the given web link is not a valid URL.
	 */
	public URL getUrl(String webLink) throws MalformedURLException {
		 URL url = new URL(webLink);
		 return url;
	}


	/**
	 * Returns the name of the element.
	 *
	 * @return the name of the element.
	 */
	@Override
	public String toString() {
		return name;
	}
	
	
	
	
}
