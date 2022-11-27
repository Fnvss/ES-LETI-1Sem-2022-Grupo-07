<<<<<<< HEAD
package ES_LETI_1Semestre_2022_GRUPO_07.ES_LETI_1Semestre_2022_GRUPO_07;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;


public class Filter {

	static File newFile;


	public static void FilterIcs(File file) throws ParseException {
		try {
			
			SimpleDateFormat cal = new SimpleDateFormat(" yyyy-MM-dd HH:mm");
			PrintStream out = new PrintStream(new File("C:\\Users\\07raf\\OneDrive\\Ambiente de Trabalho\\newFile.txt"));
			System.setOut(out);

			String dateCal = cal.format(Calendar.getInstance().getTime());
			Date date = cal.parse(dateCal);

			//FileWriter wr = new FileWriter(newFile);
			Scanner sc = new Scanner(file);

			while(sc.hasNextLine()) {
				if(sc.next().equals("Begin:")) {
					//wr.append(sc.nextLine());
					String start = sc.nextLine();

					Date dateStart = cal.parse(start);

					if(dateStart.after(date)) {
						String end = sc.nextLine();
						System.out.println("Begin:" + start);
						System.out.println(end + "\n");
					}
				}
			}
			sc.close();
			
		} catch(IOException e) {
			System.err.println("Erro na abertura do ficheiro");
		}
	}


	public static void main(String[] args) throws Exception {
		FilterIcs(new File("C:\\Users\\07raf\\OneDrive\\Ambiente de Trabalho\\teste.txt"));
	}

=======
package ES_LETI_1Semestre_2022_GRUPO_07.ES_LETI_1Semestre_2022_GRUPO_07;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;


public class Filter {

	static File newFile;


	public static void FilterIcs(File file) throws ParseException {
		try {
			
			SimpleDateFormat cal = new SimpleDateFormat(" yyyy-MM-dd HH:mm");
			PrintStream out = new PrintStream(new File("C:\\Users\\07raf\\OneDrive\\Ambiente de Trabalho\\newFile.txt"));
			System.setOut(out);

			String dateCal = cal.format(Calendar.getInstance().getTime());
			Date date = cal.parse(dateCal);

			//FileWriter wr = new FileWriter(newFile);
			Scanner sc = new Scanner(file);

			while(sc.hasNextLine()) {
				if(sc.next().equals("Begin:")) {
					//wr.append(sc.nextLine());
					String start = sc.nextLine();

					Date dateStart = cal.parse(start);

					if(dateStart.after(date)) {
						String end = sc.nextLine();
						System.out.println("Begin:" + start);
						System.out.println(end + "\n");
					}
				}
			}
			sc.close();
			
		} catch(IOException e) {
			System.err.println("Erro na abertura do ficheiro");
		}
	}


	public static void main(String[] args) throws Exception {
		FilterIcs(new File("C:\\Users\\07raf\\OneDrive\\Ambiente de Trabalho\\teste.txt"));
	}

>>>>>>> refs/remotes/origin/fnvss
}