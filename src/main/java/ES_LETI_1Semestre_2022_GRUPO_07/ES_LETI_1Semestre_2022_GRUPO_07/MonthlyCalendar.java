package ES_LETI_1Semestre_2022_GRUPO_07.ES_LETI_1Semestre_2022_GRUPO_07;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;

import ES_LETI_1Semestre_2022_GRUPO_07.ES_LETI_1Semestre_2022_GRUPO_07.Event;
import net.fortuna.ical4j.data.ParserException;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class represents a monthly calendar that can be viewed by the user.
 * The calendar provides controls for navigating between months and years, and
 * also allows the user to switch between different views of the calendar, such
 * as a daily or weekly view.
 */
public class MonthlyCalendar{
	static JLabel lblMonth;
	static JButton btnPrev, btnNext, backBtn;
	static JTable tblCalendar;
	static JComboBox cmbYear, cmbView;
	static JFrame frmMain;
	static Container pane;
	static DefaultTableModel mtblCalendar; //Table model
	static JScrollPane stblCalendar; //The scrollpane
	static JPanel pnlCalendar;
	static int realYear, realMonth, realDay, currentYear, currentMonth;
	static LocalDate actualDate;


	/**
	 * This is the main entry point for the MonthlyCalendar program. It creates the
	 * necessary Swing components and registers action listeners for the various
	 * controls in the program, such as the "<" and ">" buttons for navigating between
	 * months and the combo box for selecting the year and view.
	 *
	 * @param args an array of command-line arguments passed to the program
	 * @throws IOException 
	 */
	public static void main (String args[]) throws IOException{
		//Look and feel
		try {UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());}
		catch (ClassNotFoundException e) {}
		catch (InstantiationException e) {}
		catch (IllegalAccessException e) {}
		catch (UnsupportedLookAndFeelException e) {}

		//Prepare frame
		frmMain = new JFrame ("Calendar"); //Create frame
		frmMain.setSize(1600, 1000); //Set size to 400x400 pixels
		pane = frmMain.getContentPane(); //Get content pane
		pane.setLayout(null); //Apply null layout
		frmMain.setLocationRelativeTo(null);
		frmMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Close when X is clicked


		//Create controls
		String[] typeOfView = {"Monthly","Daily", "Weekly"};
		cmbView = new JComboBox(typeOfView);
		cmbView.setToolTipText("View");
		lblMonth = new JLabel ("January");
		lblMonth.setFont(new Font("Calibri", Font.BOLD, 21));
		cmbYear = new JComboBox();
		btnPrev = new JButton ("<");
		btnNext = new JButton (">");
		backBtn = new JButton("Back");
		mtblCalendar = new DefaultTableModel(){public boolean isCellEditable(int rowIndex, int mColIndex){return false;}};
		tblCalendar = new JTable(mtblCalendar);
		stblCalendar = new JScrollPane(tblCalendar);
		pnlCalendar = new JPanel(null);


		//Register action listeners
		btnPrev.addActionListener(new btnPrev_Action());
		btnNext.addActionListener(new btnNext_Action());
		cmbYear.addActionListener(new cmbYear_Action());
		cmbView.addActionListener(new cmbView_Action());
		backBtn.addActionListener(e -> {
			switch (cmbView.getSelectedItem().toString()) {
			case "Monthly":
				Login.getInstance().getFrameInstance().setVisible(true);
				frmMain.dispose();
				break;
			}
		});

		// Create a MouseListener to handle the mouse click event on the calendar table
		MouseListener mouseListener = new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 1) {
					// Get the row and column indices of the clicked cell
					int row = tblCalendar.rowAtPoint(e.getPoint());
					int col = tblCalendar.columnAtPoint(e.getPoint());

					// Get the value of the clicked cell
					int day = (Integer) tblCalendar.getValueAt(row, col);

					actualDate = LocalDate.of(currentYear,currentMonth + 1,day);


					// Check if the cell contains a day of the month
					if (day != 0) {
						try {
							DayCalendar aux = new DayCalendar(CalendarViews.getListOfEvents(), actualDate);
							CalendarViews.dailyView(aux);
						} catch (IOException | ParserException | ParseException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} 
						frmMain.dispose();
					}
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {}

			@Override
			public void mouseReleased(MouseEvent e) {}

			@Override
			public void mouseEntered(MouseEvent e) {}

			@Override
			public void mouseExited(MouseEvent e) {}
		};

		// Add the MouseListener to the calendar table
		tblCalendar.addMouseListener(mouseListener);

		//Add controls to pane
		pane.add(pnlCalendar);
		pnlCalendar.add(cmbView);
		pnlCalendar.add(lblMonth);
		pnlCalendar.add(cmbYear);
		pnlCalendar.add(btnPrev);
		pnlCalendar.add(btnNext);
		pnlCalendar.add(backBtn);
		pnlCalendar.add(stblCalendar);

		//Set bounds
		pnlCalendar.setBounds(0, 0, 1600, 1000);
		lblMonth.setBounds(800-lblMonth.getPreferredSize().width/2, 9, 100, 25);
		cmbYear.setBounds(1490, 920, 80, 25);
		cmbView.setBounds(570, 7, 80, 25);
		btnPrev.setBounds(670, 7, 50, 25);
		btnNext.setBounds(880, 7, 50, 25);
		backBtn.setBounds(950, 7, 70, 25);
		stblCalendar.setBounds(0, 40, 1600, 1000);

		Image iconImage = iconImage();
		// Set the icon image of the JFrame
		frmMain.setIconImage(iconImage);

		//Make frame visible
		frmMain.setVisible(true);
		frmMain.setResizable(false);

		//Get real month/year
		GregorianCalendar cal = new GregorianCalendar(); //Create calendar
		realDay = cal.get(GregorianCalendar.DAY_OF_MONTH); //Get day
		realMonth = cal.get(GregorianCalendar.MONTH); //Get month
		realYear = cal.get(GregorianCalendar.YEAR); //Get year
		currentMonth = realMonth; //Match month and year
		currentYear = realYear;

		//Add headers
		String[] headers = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"}; //All headers
		for (int i=0; i<7; i++){
			mtblCalendar.addColumn(headers[i]);
		}

		tblCalendar.getParent().setBackground(tblCalendar.getBackground()); //Set background

		//No resize/reorder
		// tblCalendar.getTableHeader().setResizingAllowed(false);
		tblCalendar.getTableHeader().setReorderingAllowed(false);

		//Single cell selection
		tblCalendar.setColumnSelectionAllowed(true);
		tblCalendar.setRowSelectionAllowed(true);
		tblCalendar.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		//Set row/column count
		tblCalendar.setRowHeight(140);
		mtblCalendar.setColumnCount(7);
		mtblCalendar.setRowCount(6);

		//Populate table
		for (int i=realYear-100; i<=realYear+100; i++){
			cmbYear.addItem(String.valueOf(i));
		}

		//Refresh calendar
		refreshCalendar (realMonth, realYear); //Refresh calendar
	}


	private static Image iconImage() throws IOException {
		Image iconImage = null;
		iconImage = ImageIO.read(new File("icon.png"));
		return iconImage;
	}


	/**
	 * This method is used to refresh the calendar display with the current month, year,
	 * and view selected by the user. It clears any existing data from the table, then
	 * populates the table with the appropriate dates and events for the current month.
	 *
	 * @param year the year to display in the calendar
	 * @param month the month to display in the calendar
	 * @param view the view to use for the calendar (e.g. daily, weekly, monthly)
	 */
	public static void refreshCalendar(int month, int year){
		lblMonth(month);
		int nod, som; //Number Of Days, Start Of Month

		//Allow/disallow buttons
		btnPrev.setEnabled(true);
		btnNext.setEnabled(true);
		if (month == 0 && year <= realYear-10){btnPrev.setEnabled(false);} //Too early
		if (month == 11 && year >= realYear+100){btnNext.setEnabled(false);} //Too late
		cmbYear.setSelectedItem(String.valueOf(year)); //Select the correct year in the combo box

		//Clear table
		for (int i=0; i<6; i++){
			for (int j=0; j<7; j++){
				mtblCalendar.setValueAt(null, i, j);
			}
		}

		//Get first day of month and number of days
		GregorianCalendar cal = new GregorianCalendar(year, month, 1);
		nod = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
		som = cal.get(GregorianCalendar.DAY_OF_WEEK);

		//Draw calendar
		for (int i=1; i<=nod; i++){
			int row = new Integer((i+som-2)/7);
			int column  =  (i+som-2)%7;
			mtblCalendar.setValueAt(i, row, column);
		}

		//Apply renderers
		tblCalendar.setDefaultRenderer(tblCalendar.getColumnClass(0), new tblCalendarRenderer());
	}


	private static void lblMonth(int month) {
		String[] months = { "January", "February", "March", "April", "May", "June", "July", "August", "September",
				"October", "November", "December" };
		lblMonth.setText(months[month]);
		lblMonth.setBounds(800 - lblMonth.getPreferredSize().width / 2, 10, 100, 25);
	}


	/**
	 * This inner class is used to customize the rendering of the calendar table. It
	 * extends the DefaultTableCellRenderer class and overrides the
	 * getTableCellRendererComponent method to add custom formatting to the table cells.
	 */
	static class tblCalendarRenderer extends DefaultTableCellRenderer{

		/**
		 * This method is called whenever a cell in the calendar table needs to be
		 * rendered. It applies custom formatting to the cell, such as setting the
		 * background color and font, based on the value of the cell and the current
		 * view of the calendar.
		 *
		 * @param table the JTable that contains the cell
		 * @param value the value of the cell
		 * @param isSelected whether the cell is currently selected
		 * @param hasFocus whether the cell currently has focus
		 * @param row the row index of the cell
		 * @param column the column index of the cell
		 * @return the rendered table cell
		 */
		public Component getTableCellRendererComponent (JTable table, Object value, boolean selected, boolean focused, int row, int column){
			Schedule schedule = Login.schedule;
			List<Event> list = schedule.getEvents();

			super.getTableCellRendererComponent(table, value, selected, focused, row, column);
			if (column == 0 || column == 6){ //Week-end
				setBackground(new Color(211, 211, 211, 128));//Light gray
			}
			else{ //Week
				setBackground(new Color(255, 255, 255));// White
			}


			if (value != null){
				// Convert the cell value to a LocalDate object
				int day = Integer.parseInt(value.toString());
				// Convert the cell value to a LocalDate object
				LocalDate date = LocalDate.of(currentYear, currentMonth+1, day);

				// Filter the list of events to only include events that happen on the same date as the cell date
				List<Event> filteredList = list.stream()
						.filter(event -> event.getStartDate().toLocalDate().equals(date))
						.collect(Collectors.toList());

				//filteredList.size() gives the number of events on a day
				if(filteredList.size() == 0) {//White
				} else if(filteredList.size() <= 1) {
					setBackground(new Color(153,255,51));//Green
				} else if(filteredList.size() <= 2) {
					setBackground(new Color(255,255,102));//Yellow
				} else if(filteredList.size() <= 3) {
					setBackground(Color.ORANGE);//Orange
				} else if(filteredList.size() >= 4) {
					setBackground(new Color(204,0,0));//Red
				}

				//Paint the current day as light blue
				if (Integer.parseInt(value.toString()) == realDay && currentMonth == realMonth && currentYear == realYear){ //Today
					setBackground(new Color(220, 220, 255));
				}
			}
			setBorder(null);
			setForeground(Color.black);
			return this;
		}
	}


	/**
	 * This inner class is used to handle the user's interaction with the "<" button
	 * in the calendar program. It implements the ActionListener interface and defines
	 * a single method, actionPerformed, which is called whenever the user clicks on
	 * the button.
	 */
	static class btnPrev_Action implements ActionListener{


		/**
		 * This method is called whenever the user clicks on the "<" button. It updates
		 * the current month and year based on the value of the current month, then
		 * calls the refreshCalendar method to update the calendar display with the
		 * new values.
		 *
		 * @param e the ActionEvent object containing information about the button click
		 */
		public void actionPerformed (ActionEvent e){
			if (currentMonth == 0){ //Back one year
				currentMonth = 11;
				currentYear -= 1;
			}
			else{ //Back one month
				currentMonth -= 1;
			}
			refreshCalendar(currentMonth, currentYear);
		}
	}


	/**
	 * This inner class is used to handle the user's interaction with the ">" button
	 * in the calendar program. It implements the ActionListener interface and defines
	 * a single method, actionPerformed, which is called whenever the user clicks on
	 * the button.
	 */
	static class btnNext_Action implements ActionListener{


		/**
		 * This method is called whenever the user clicks on the ">" button. It updates
		 * the current month and year based on the value of the current month, then
		 * calls the refreshCalendar method to update the calendar display with the
		 * new values.
		 *
		 * @param e the ActionEvent object containing information about the button click
		 */
		public void actionPerformed (ActionEvent e){
			if (currentMonth == 11){ //Foward one year
				currentMonth = 0;
				currentYear += 1;
			}
			else{ //Foward one month
				currentMonth += 1;
			}
			refreshCalendar(currentMonth, currentYear);
		}
	}


	/**
	 * This inner class is used to handle the user's interaction with the cmbYear ComboBox
	 * in the calendar program. It implements the ActionListener interface and defines
	 * a single method, actionPerformed, which is called whenever the user clicks on
	 * the button.
	 */
	static class cmbYear_Action implements ActionListener{


		/**
		 * This method is called whenever the user clicks on the cmbYear ComboBox and selects a year. 
		 * It updates the current month and year based on year selected on the cmbYear, then
		 * calls the refreshCalendar method to update the calendar display with the
		 * new values.
		 *
		 * @param e the ActionEvent object containing information about the button click
		 */
		public void actionPerformed (ActionEvent e){
			if (cmbYear.getSelectedItem() != null){
				String b = cmbYear.getSelectedItem().toString();
				currentYear = Integer.parseInt(b);
				refreshCalendar(currentMonth, currentYear);
			}
		}
	}


	/**
	 * This inner class is used to handle the user's interaction with the cmbView ComboBox
	 * in the calendar program. It implements the ActionListener interface and defines
	 * a single method, actionPerformed, which is called whenever the user clicks on
	 * the button.
	 */
	static class cmbView_Action implements ActionListener{


		/**
		 * This method is called whenever the user clicks on the cmbView ComboBox and selects a type of view. 
		 * If the selected view is "Weekly" it will call the class CalendarViews and the method weeklyView(),
		 * then disposes the current frame
		 * If the selected view is "Daily" it will call the class CalendarViews and the method DailyView(),
		 * then disposes the current frame
		 * 
		 * @param e the ActionEvent object containing information about the button click
		 */
		public void actionPerformed(ActionEvent e) {
			if(cmbView.getSelectedItem().toString()=="Weekly") {
				try {
					WeekCalendar aux = new WeekCalendar(CalendarViews.getListOfEvents());
					CalendarViews.weeklyView(aux);
				} catch (IOException | ParserException | ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				frmMain.dispose();
			}

			if(cmbView.getSelectedItem().toString()=="Daily") {		
				try {
					DayCalendar aux = new DayCalendar(CalendarViews.getListOfEvents(), LocalDate.now());
					CalendarViews.dailyView(aux);
				} catch (IOException | ParserException | ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}			
				frmMain.dispose();
			}
		}
	}
}