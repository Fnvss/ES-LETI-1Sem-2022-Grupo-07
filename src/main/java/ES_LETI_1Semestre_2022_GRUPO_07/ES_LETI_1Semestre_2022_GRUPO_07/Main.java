package ES_LETI_1Semestre_2022_GRUPO_07.ES_LETI_1Semestre_2022_GRUPO_07;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import net.fortuna.ical4j.data.ParserException;

public class Main {

	public static void main(String[] args) throws IOException, ParserException, ParseException {
		// TODO Auto-generated method stub
		//Login teste = new Login();
		
		
		List<Event> events = new ArrayList<>();
		List<Element> elements = new ArrayList<>();
		Schedule schedule = new Schedule(events, elements);
		Element element1 = new Element("Guilherme", "https://fenix.iscte-iul.pt/publico/publicPersonICalendar.do?method=iCalendar&username=ggmso@iscte.pt&password=RapiAbBUH3jevyDETvtVAO2iI7JUi9NTv0i7JAn8tiUpVOAES55J6exaS9U27XMosaQINnY17SXwSe561qHbRbEtUqWTn7SfbiiuQi92WogIhg6U7JJpAhqjvjo3HdOV");
		Element element2 = new Element("Tomás", "https://fenix.iscte-iul.pt/publico/publicPersonICalendar.do?method=iCalendar&username=tlpco@iscte.pt&password=peZuWATHi62pUmdMkFVyVLgeqWYc1Wk9VTCE3hbcbek93N030ZkPxDuiJPHmJP169nua5GMkztSGKD9MXczPYZCXqnUmrBvwklOcRftGPql225ahEjieA2782mKLpEQA");
		Element element3= new Element("Rafael", "https://fenix.iscte-iul.pt/publico/publicPersonICalendar.do?method=iCalendar&username=ramss3@iscte.pt&password=gGdsqZehHOOyFFAPMPSk8VeB63LPV0scP4qR36srE3dMgEB3b8GPIxCKorPUzQeySmm0XoAR7K0gw8pA5QrVrvx1VwfVrgjOFhMlC0mQkG6UxWuE4R2n12xxgV9hYfbX");
		Element element4= new Element("Filipe", "https://fenix.iscte-iul.pt/publico/publicPersonICalendar.do?method=iCalendar&username=fnvss@iscte.pt&password=VEYDs7sb4vBPDE0mtnwC8Z4KWSD6pLsjbAeI4GbWyPT4bz8jm6cjFVbrfc3gJg9VJYRRSAxgiFHh9H0zYMinDqrOmPcZl03IBXckWkRG7QTWuuE1Hzs5XbnWxaD7PUVp");
		schedule.addElement(element1);
		schedule.addElement(element2);
		schedule.addElement(element3);
		schedule.addElement(element4);
		List<Event> list = schedule.getEvents();
		//List<Event> list = schedule.readCalendar(element2).getEvents();
		for(Event e : list) {
			System.out.println(e.toString());			
		}
		

}
}
