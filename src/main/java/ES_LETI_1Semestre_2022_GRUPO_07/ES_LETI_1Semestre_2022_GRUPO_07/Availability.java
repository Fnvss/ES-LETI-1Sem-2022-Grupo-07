package ES_LETI_1Semestre_2022_GRUPO_07.ES_LETI_1Semestre_2022_GRUPO_07;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class Availability {

	static File file = new File("C:\\Users\\07raf\\Desktop\\newFile.txt");
	static String comecaManha = "7:00";
	static String comecaTarde = "13:01";

	//ele vai dar uma data  e nos temos que ver se nessa data ha disponibilidade de alguem depois temos que ver se ele quer de manha ou de tarde
	//sendo que para isso na minha opiniao temos que separar as datas dadas com dia e hora e separar essas datas que têm o dia
	//pedido para por so as horas dessa data, depois comparar com a data de manha e tarde e depois disponibilizar os horarios para esse dia

	public static void dia(String s) throws ParseException, FileNotFoundException {
		SimpleDateFormat cal = new SimpleDateFormat(" yyyy-MM-dd");
		String dateCal = cal.format(Calendar.getInstance().getTime());
		Scanner sc = new Scanner(file);
		
	}
	
	public static void hora(String s) throws ParseException, FileNotFoundException {

		SimpleDateFormat cal = new SimpleDateFormat("HH:mm");
		String dateCal = cal.format(Calendar.getInstance().getTime());
		Date afternoon = cal.parse(comecaTarde);
		Scanner sc = new Scanner(file);
		
		if(s == "Tarde" || s == "Manhã"|| s == "tarde" || s == "manhã" ) {
			PrintStream out = new PrintStream(new File("C:\\Users\\07raf\\Desktop\\dataFile.txt"));
			System.setOut(out);

			while(sc.hasNextLine()) {
				
				if(sc.next().equals("Begin:"))  {

					String ano = sc.next();
					String hora = sc.next();
					Date dateBegin = cal.parse(hora);
					
					if(s == "Manhã" || s == "manhã") {
						if(dateBegin.compareTo(afternoon) < 0) {
							if(dateBegin.after(cal.parse(dateCal))) {
								String end = sc.nextLine();
								System.out.println("Begin:" + hora);
								System.out.println(end + "\n");
							}
						}
					} else {
						if(dateBegin.compareTo(afternoon) > 0) {
							if(dateBegin.after(cal.parse(dateCal))) {
								String end = sc.nextLine();
								System.out.println("Begin:" + hora);
								System.out.println(end + "\n");
							}
						}
					}
				}
			}
		
		} else {
			System.out.println("Introduza um horário válido");
		}
		sc.close();
	}


	public static void disponivel(String s) throws ParseException, FileNotFoundException {
		SimpleDateFormat cal = new SimpleDateFormat(" yyyy-MM-dd HH:mm");
		String dateCal = cal.format(Calendar.getInstance().getTime());
		Date dateEnd;
		Date date = cal.parse(s);
		Scanner sc = new Scanner(file);

		while(sc.hasNextLine()) {
			if(sc.next().equals("Begin: "))  {
				String start = sc.nextLine();
				Date dateBegin = cal.parse(start);

				if(dateBegin.after(cal.parse(dateCal))) {
					String end = sc.nextLine();
					dateEnd = cal.parse(end);

					if(date.after(dateBegin) && date.before(dateEnd)) {
						System.out.println("Não há disponabilidade do empregado");
					} else {
						System.out.println("O empregado encontra-se disponível");
					}
				}
			}

		}

	}

	public static void main(String[] args) throws Exception {
		//hora("manhã");
		disponivel(" 2023-02-14 13:05");
	}
}
