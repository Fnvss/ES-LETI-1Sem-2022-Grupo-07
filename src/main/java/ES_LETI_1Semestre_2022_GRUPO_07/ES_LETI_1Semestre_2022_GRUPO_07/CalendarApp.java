package ES_LETI_1Semestre_2022_GRUPO_07.ES_LETI_1Semestre_2022_GRUPO_07;

import java.time.LocalDate;
import java.time.LocalTime;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.Calendar.Style;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.view.CalendarView;
import com.calendarfx.model.Entry;
import com.calendarfx.*;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CalendarApp extends Application {
	

	@Override
	public void start(Stage primaryStage) throws Exception {

		CalendarView calendarView = new CalendarView(); 

		Calendar Filipe = new Calendar("Filipe"); 
		Calendar Rafael = new Calendar("Rafael"); 
		Calendar Guilherme = new Calendar("Guilherme"); 
		Calendar Tomás = new Calendar("Tomás"); 

		Entry<String> aula1 = new Entry<>("Filipe");
		Entry<String> aula2 = new Entry<>("Rafael");
		Entry<String> aula3 = new Entry<>("Guilherme");
		Entry<String> aula4 = new Entry<>("Tomás");

		Filipe.addEntry(aula1);
		Rafael.addEntry(aula2);
		
		CalendarView calendarViews = new CalendarView();
		//calendarViews.setCalendarSourceFactory(param -> new MyCalendarSource());

		Filipe.setStyle(Style.STYLE1); 
		Rafael.setStyle(Style.STYLE2);
		Guilherme.setStyle(Style.STYLE3);
		Tomás.setStyle(Style.STYLE4);

		CalendarSource myCalendarSource = new CalendarSource("My Calendars"); 
		myCalendarSource.getCalendars().addAll(Filipe, Rafael, Guilherme, Tomás);
		
		calendarView.getCalendarSources().addAll(myCalendarSource);
		calendarView.setRequestedTime(LocalTime.now());

		Thread updateTimeThread = new Thread("Calendar: Update Time Thread") {
			@Override
			public void run() {
				while (true) {
					Platform.runLater(() -> {
						calendarView.setToday(LocalDate.now());
						calendarView.setTime(LocalTime.now());
					});

					try {
						// update every 10 seconds
						sleep(10000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

				}
			}
		};

		updateTimeThread.setPriority(Thread.MIN_PRIORITY);
		updateTimeThread.setDaemon(true);
		updateTimeThread.start();

		Scene scene = new Scene(calendarView);
		primaryStage.setTitle("Calendar");
		primaryStage.setScene(scene);
		primaryStage.setWidth(1300);
		primaryStage.setHeight(1000);
		primaryStage.centerOnScreen();
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}