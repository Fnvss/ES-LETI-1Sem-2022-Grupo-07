package ES_LETI_1Semestre_2022_GRUPO_07.ES_LETI_1Semestre_2022_GRUPO_07;

public class Element {
	
	String name;
	String url;
	
	public Element(String name, String url) {
		this.name = name;
		this.url = url;
	}
	
	public String addElement(String s) {
		return s;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	@Override
	public String toString() {
		return name+ " " +url;
		
	}
	
}