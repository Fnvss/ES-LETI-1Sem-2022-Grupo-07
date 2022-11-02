package ES_LETI_1Semestre_2022_GRUPO_07.ES_LETI_1Semestre_2022_GRUPO_07;

import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.UnfoldingReader;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.Property;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.URL;


public class ReadFile {
	
	//criação de um objeto do tipo Calendar
	private static Calendar calendar;
	private static String string;
	final static String ics = "C:\\Users\\07raf\\git\\ES-2022-LETI-Grupo-07\\src\\main\\java\\ramss3@iscte.pt_iscte-iul.ics";

	public static void ICSWritter(String s) throws Exception {
		
		//transforma o link em string para url
		URL url = new URL(s);
		//converte url para txt
		InputStreamReader input = new InputStreamReader(url.openStream()); 
		BufferedReader br = new BufferedReader(input);
		
		//lê o ficheiro já em txt
		while(( string = br.readLine()) != null) {
			System.out.println(string);
		}
		
		br.close();
		
	}


	
	public static void readICSFile() {
		try {
			CalendarBuilder builder = new CalendarBuilder();

			final UnfoldingReader ur = new UnfoldingReader(new FileReader(ics), true);

			calendar = builder.build(ur);

			for (final Object o : calendar.getComponents()) {
				Component component = (Component) o;

				System.out.println("Component: " + component.getName());

				for (final Object o1 : component.getProperties()) {
					Property property = (Property) o1;
					System.out.println(
							property.getName() + ": " + property.getValue());
				}
			}
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

//	public static void main(String[] args) throws Exception {
//		//readICSFile();
//		ICSWritter("https://fenix.iscte-iul.pt/publico/publicPersonICalendar.do?method=iCalendar&username=ramss3@iscte.pt&password=gGdsqZehHOOyFFAPMPSk8VeB63LPV0scP4qR36srE3dMgEB3b8GPIxCKorPUzQeySmm0XoAR7K0gw8pA5QrVrvx1VwfVrgjOFhMlC0mQkG6UxWuE4R2n12xxgV9hYfbX");
//	}





}

