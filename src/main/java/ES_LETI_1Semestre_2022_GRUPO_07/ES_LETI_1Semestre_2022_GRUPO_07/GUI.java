package ES_LETI_1Semestre_2022_GRUPO_07.ES_LETI_1Semestre_2022_GRUPO_07;
/*Java Program to Create a Menu and Display the Menu Item Selected*/
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileWriter;

public class GUI implements ActionListener {
	JFrame frame = new JFrame("Meeting Planner");
	JLabel text;
	JButton urlButton = new JButton("Insert");
	JButton fileSelectButton = new JButton("Select File");
	FileWriter fileWriter;
	JTextField urlField = new JTextField();
	JLabel urlLabel = new JLabel("Your Url: ");
	JLabel blank = new JLabel();
	JFileChooser fileChooser = new JFileChooser();
	
	//Driver function
	GUI(){

		//Create a frame
		frame.setSize(400,150);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//Create a Menu
		JMenu menu = new JMenu("Menu ▼");

		GridLayout g1 = new GridLayout();
		g1.setColumns(2);
		g1.setRows(3);
		
		
		//Buttons
		urlButton.addActionListener(this);
		fileSelectButton.addActionListener(this);

		//Create a menu bar
		JMenuBar mb = new JMenuBar();
		mb.add(menu);
		
		//Add the UI
		frame.setLayout(g1);
		frame.setJMenuBar(mb);
		frame.add(urlLabel);
		frame.add(urlField);
		frame.add(fileSelectButton);
		frame.add(urlButton);

		//Create custom label
		text = new JLabel();
		frame.add(text);
		
		//Display the frame
		frame.setVisible(true);
	}


	//Function to display the menu item selected
	@Override
	public void actionPerformed(ActionEvent ae) {
		
		if(ae.getActionCommand() == fileSelectButton.getActionCommand()) {
			int response = fileChooser.showOpenDialog(null);
			
			if(response == JFileChooser.APPROVE_OPTION) {
				String s = new String(fileChooser.getSelectedFile().getAbsolutePath());
				try {
					ReadFile.readICSFile(s);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		if(ae.getActionCommand() == urlButton.getActionCommand()) {
			try {
//				fileWriter = new FileWriter("D:/teste.txt");
//				fileWriter.write(urlField.getText());
//				fileWriter.close();
//				JOptionPane.showMessageDialog(null, "Tá a dar!");
				
				ReadFile.ICSWritter(urlField.getText());
				JOptionPane.showMessageDialog(null, "Ficheiro transcrito com sucesso!");
				
			} catch(Exception e) {
				JOptionPane.showMessageDialog(null,"Please Insert a Valid URL");
			}
		}

	}
}