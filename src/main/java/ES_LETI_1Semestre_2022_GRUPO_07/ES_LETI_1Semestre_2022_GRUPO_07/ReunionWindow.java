package ES_LETI_1Semestre_2022_GRUPO_07.ES_LETI_1Semestre_2022_GRUPO_07;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerDateModel;
import javax.swing.SwingConstants;
import javax.swing.text.DateFormatter;
import javax.swing.text.DefaultFormatterFactory;


public class ReunionWindow implements ActionListener {
	
	JFrame frame = new JFrame();
	JPanel contentPane = new JPanel();
	JComboBox<String> timeOfDayCombo;
	JComboBox<String> membersCombo = new JComboBox<String>();
	JLabel label = new JLabel("Set up your reunion");
	JButton backButton = new JButton("Back");
	JButton submitButton = new JButton("Submit");
	JList list;
	
	JSpinner duration;


	ReunionWindow(Schedule schedule) {

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//Set up the panel
		contentPane.setOpaque(true);
		contentPane.setBackground(Color.GRAY);
		contentPane.setLayout(null);
		
		label.setBounds(170, 10, 200, 90);
		label.setFont(new Font("Calibri", Font.BOLD, 18));
		
		//Button to go to the previous window
		backButton.setBounds(20,10,70,30);
		
		Date dateObjS = new Date();
		SpinnerDateModel sdm = new SpinnerDateModel(dateObjS, null, null, Calendar.SECOND);
		duration = new JSpinner(sdm);
		JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(duration, "HH:mm");
		dateEditor.getTextField().setEditable( false );
		duration.setEditor(dateEditor);
		duration.setBounds(95, 160, 100, 30);
		

		
		String[] timeOfDay = {"Manhã", "Tarde"};
		timeOfDayCombo = new JComboBox(timeOfDay);
		timeOfDayCombo.setRenderer(new MyComboBoxRenderer("Time"));
		timeOfDayCombo.setSelectedIndex(-1);
		((JLabel)timeOfDayCombo.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

		//Set size and location of the button
		timeOfDayCombo.setBounds(95, 200, 100, 30);
		
		list = new JList(schedule.getElements().toArray());
		list.setFixedCellHeight(15);
        list.setFixedCellWidth(100);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setVisibleRowCount(4);
        Dimension dimension = list.getPreferredSize();
        list.setBounds(295, 200, dimension.width, dimension.height);
        list.setVisibleRowCount(4);
        list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
      
    	submitButton.setBounds(190,250, 100,30);

		
//		membersCombo.setRenderer(new MyComboBoxRenderer("Elements"));
//		membersCombo.setSelectedIndex(-1);
//		((JLabel)membersCombo.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
//		
//		for(int i = 0; i < schedule.getElements().size(); i++) {
//			membersCombo.insertItemAt(schedule.getElements().get(i).toString(), i);
//		}
//
//		membersCombo.setBounds(295, 200, 100, 30);
		
		
		//Put objects into the panel
		contentPane.add(label);
		contentPane.add(timeOfDayCombo);
		//contentPane.add(membersCombo);
		contentPane.add(backButton);
		contentPane.add(duration);
		contentPane.add(submitButton);
		contentPane.add(list);

		//Create frame
		frame.setContentPane(contentPane);
		frame.setSize(500, 350);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		//ActionListener for enter
		backButton.addActionListener(this);
		submitButton.addActionListener(this);
		
	}
	
	 class MyComboBoxRenderer extends JLabel implements ListCellRenderer
	    {
	        private String _title;

	        public MyComboBoxRenderer(String title)
	        {
	            _title = title;
	        }

	        @Override
	        public Component getListCellRendererComponent(JList list, Object value,
	                int index, boolean isSelected, boolean hasFocus)
	        {
	            if (index == -1 && value == null) setText(_title);
	            else setText(value.toString());
	            return this;
	        }
	    }
	
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		if(ae.getActionCommand() == backButton.getActionCommand()) {
			frame.setVisible(false);
			Login.getInstance().getFrameInstance().setVisible(true);		
		}
		if(ae.getActionCommand() == submitButton.getActionCommand()) {
			  List<Element> elementsReunion = new ArrayList<>();
		        Object[] elements = list.getSelectedValues();
		        for(int i = 0; i < list.getSelectedValues().length; i++) {
		        	elementsReunion.add((Element) elements[i]);
		        }
		    String tarde = "Tarde";
		    String duration = "1:42";
			Login.schedule.checkAvailableDate(elementsReunion, tarde, duration );
		}

	}
}
