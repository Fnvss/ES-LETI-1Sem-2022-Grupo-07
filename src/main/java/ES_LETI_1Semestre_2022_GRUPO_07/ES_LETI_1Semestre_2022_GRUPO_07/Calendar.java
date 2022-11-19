package ES_LETI_1Semestre_2022_GRUPO_07.ES_LETI_1Semestre_2022_GRUPO_07;

import javax.swing.JFrame;

import org.jdatepicker.JDatePanel;
import org.jdatepicker.JDatePicker;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

public class Calendar {
	
	
	public Calendar() {
		UtilDateModel model = new UtilDateModel();
		JDatePanelImpl datePanel = new JDatePanelImpl(model, null);
		JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, null);
		JFrame frame = new JFrame();
		frame.add(datePicker);
		frame.setVisible(true);
	}
	
	public static void main(String[] args) {
		Calendar c = new Calendar();
	}
}
