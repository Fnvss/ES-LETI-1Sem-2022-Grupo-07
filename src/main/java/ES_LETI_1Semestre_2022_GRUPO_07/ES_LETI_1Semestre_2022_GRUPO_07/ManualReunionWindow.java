package ES_LETI_1Semestre_2022_GRUPO_07.ES_LETI_1Semestre_2022_GRUPO_07;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.NumberFormat;
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
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.SpinnerDateModel;
import javax.swing.SwingConstants;
import javax.swing.text.PlainDocument;

import org.jdesktop.swingx.JXFormattedTextField;
import org.jdesktop.swingx.JXTextField;


import javax.swing.JOptionPane;

public class ManualReunionWindow implements ActionListener {
	JFrame frame = new JFrame();
	JLabel label = new JLabel("Set up your reunion");
	JLabel dateLabel = new JLabel("Date: ");
	JLabel endDateLabel = new JLabel("Reunion End: ");
	JLabel startDateLabel = new JLabel("Reunion start: ");
	JPanel contentPane = new JPanel();
	JComboBox<String> membersCombo = new JComboBox<String>();
	JButton backButton = new JButton("Back");
	DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
	JButton submitButton = new JButton("Submit");

	//JFormattedTextField reunionDate;
	JXTextField reunionDate;
	JSpinner startTimer;
	JSpinner endTimer;
	//JLabel dateFormatLabel = new JLabel("Date Format type: yyyy-mm-dd");

	ManualReunionWindow(Schedule schedule) {

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//Set up the panel
		contentPane.setOpaque(true);
		contentPane.setBackground(Color.GRAY);
		contentPane.setLayout(null);

		label.setBounds(170, 10, 200, 90);
		label.setFont(new Font("Calibri", Font.BOLD, 18));

		//dateFormatLabel.setBounds(155, 90, 300, 30);

		//reunionDate = new JFormattedTextField(dateFormat);
		reunionDate = new JXTextField();
		PlainDocument filter = (PlainDocument) reunionDate.getDocument();
		filter.setDocumentFilter(new IntegerFilter());
		reunionDate.setBounds(95, 120, 300, 30);
		reunionDate.setPrompt("yyyy-mm-dd");
		reunionDate.setPromptForeground(Color.DARK_GRAY);

		dateLabel.setBounds(60, 120, 300, 30);



		Date dateObjS = new Date();
		SpinnerDateModel sdm = new SpinnerDateModel(dateObjS, null, null, Calendar.SECOND);
		startTimer = new JSpinner(sdm);
		JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(startTimer, "HH:mm");
		dateEditor.getTextField().setEditable( false );
		startTimer.setEditor(dateEditor);
		startTimer.setBounds(95, 160, 100, 30);
		startDateLabel.setBounds(10, 160, 100, 30);


		Date dateObjE = new Date();
		SpinnerDateModel sdm2 = new SpinnerDateModel(dateObjE, null, null, Calendar.SECOND);
		endTimer = new JSpinner(sdm2);
		JSpinner.DateEditor dateEditorEnd = new JSpinner.DateEditor(endTimer, "HH:mm");
		dateEditorEnd.getTextField().setEditable( false );
		endTimer.setEditor(dateEditorEnd);
		endTimer.setBounds(295, 160, 100, 30);
		endDateLabel.setBounds(215, 160, 100, 30);



		membersCombo.setRenderer(new MyComboBoxRenderer("Elements"));
		membersCombo.setSelectedIndex(-1);
		((JLabel)membersCombo.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

		for(int i = 0; i < schedule.getElements().size(); i++) {
			membersCombo.insertItemAt(schedule.getElements().get(i).toString(), i);
		}

		membersCombo.setBounds(95, 200, 100, 30);

		//Button to go to the previous window
		backButton.setBounds(20,10,70,30);
		
		submitButton.setBounds(190,250, 100,30);


		//Put objects into the panel
		contentPane.add(label);
		//contentPane.add(dateFormatLabel);
		contentPane.add(reunionDate);
		contentPane.add(startTimer);
		contentPane.add(endTimer);
		contentPane.add(dateLabel);
		contentPane.add(endDateLabel);
		contentPane.add(membersCombo);
		contentPane.add(backButton);
		contentPane.add(submitButton);
		contentPane.add(startDateLabel);



		//Create frame
		frame.setContentPane(contentPane);
		frame.setSize(500, 350);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		//ActionListener for enter
		backButton.addActionListener(this);
		//timeComboBox.addActionListener(this);
		//reunionButton.addActionListener(this);
		//seeMembersList.addActionListener(this);

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

	}

}
