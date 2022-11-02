package ES_LETI_1Semestre_2022_GRUPO_07.ES_LETI_1Semestre_2022_GRUPO_07;

import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.UnfoldingReader;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.Property;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;


public class ReadFile {

	private static Calendar calendar;

	public String getKey() {
		return "parse-ics";
	}

	public String getTitle() {
		return "Parse an ics file";
	}

	public static String readICS(String ics) {
		return ics;
	}

	final static String ics = "C:\\Users\\07raf\\git\\ES-2022-LETI-Grupo-07\\src\\main\\java\\ramss3@iscte.pt_iscte-iul.ics";

	
	public static void readICSFile() {
		try {
			CalendarBuilder builder = new CalendarBuilder();

			final UnfoldingReader ur = new UnfoldingReader(new FileReader(ics), true);

			calendar = builder.build(ur);

			for (final Object o : calendar.getComponents()) {
				Component component = (Component) o;

				System.out.println("Component: " + component.getName());

				for (final Object o1 : component.getProperties()) {
					Property property = (Property)o1;
					System.out.println(
							property.getName() + ": " + property.getValue());
				}
			}
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

//		public static void IcsToJSon() {
//			Calendar calendar = calendar;
//			SimpleModule module = new SimpleModule();
//			module.addSerializer(Calendar.class, new JCalSerializer());
//			ObjectMapper mapper = new ObjectMapper();
//			mapper.registerModule(module);
//	
//			String serialized = mapper.writeValueAsString(calendar);
//		}

	public static void main(String[] args) throws Exception {
		readICSFile();
	}





}

