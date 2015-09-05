package jnotes;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

public class RenameNote extends JFrame {
	
	JPanel panel = new JPanel();
	public JTextField txtField = new JTextField();
	JButton btn = new JButton();
	String noteName, noteData, data;
	
	public RenameNote(String noteFile) {
		final String noteFileName = noteFile;
		final File note = new File(Main.userNotesPath+"/data/"+noteFileName);
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(note));
			try {
				noteName = br.readLine();
				noteData = "\n";
				while ((data = br.readLine()) != null) {
					noteData = noteData + data + "\n";
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		
		
		panel.setBackground(Color.lightGray);
		this.setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
		this.setTitle("Rename note:");
		this.setLocationRelativeTo(null);
		
		txtField.setBounds(10, 10, 300, 20);
		btn.setBounds(110, 40, 90, 20);
		btn.setText("Rename");
		txtField.setText(noteName);
		
		this.add(txtField);
		this.add(btn);
		getContentPane().add(panel);
		setPreferredSize(new Dimension(330, 110));
		this.pack();
		this.setVisible(true);
		
		btn.addMouseListener(new MouseAdapter() {
	    	public void mousePressed(MouseEvent me) {
	    		UIManager.put("swing.boldMetal", Boolean.FALSE);
	    		saveNote(noteFileName);
	    		Main.popup.removeAll();
	    		Main.firstPartOfMenu();
	    		Main.dynamicMenu();
	    		Main.secondPartOfMenu();
	    		new Note(noteFileName);
	    		setVisible(false);		
	    	}
	    });
	}
	
	public void saveNote(String noteFileName)  {
		final File noteToSave = new File(Main.userNotesPath+"/data/"+noteFileName);
		
		data = txtField.getText() + noteData;
		
		FileWriter fileWriter;
		try {
			fileWriter = new FileWriter(noteToSave);
			fileWriter.write(data);
			fileWriter.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}
