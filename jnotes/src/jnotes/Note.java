package jnotes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;


public class Note extends JPanel {
	
	public JFrame frame = new JFrame();
	JPanel middlePanel = new JPanel();
	JTextArea display = new JTextArea(20,40);
	String noteName = null;
	String data = null;
	String framePosX, framePosY = null;
	String frameSizeX, frameSizeY = null;
	int x,y = 0;
	
	public Note(String noteFileName) {
		
		final String noteFile = noteFileName;
		final File note = new File(Main.userNotesPath+"/data/"+noteFileName);
		
		if (!note.isFile()) {
			createNewNote(noteFileName);
		}
		try {
			BufferedReader br = new BufferedReader(new FileReader(note));
			try {
				noteName = br.readLine();
				framePosX = br.readLine();
				framePosY = br.readLine();
				frameSizeX = br.readLine();
				frameSizeY = br.readLine();
				while ((data = br.readLine()) != null) {
					display.append(data+"\n");
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}

		frame.setTitle(noteName);
	    middlePanel.setBorder(new TitledBorder(new EtchedBorder(),noteName));
	    middlePanel.setLayout(new BorderLayout());
	    display.setEditable(true);
	    display.setBackground(Color.ORANGE);
	    JScrollPane scroll = new JScrollPane(display);
	    scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	    middlePanel.add (scroll,BorderLayout.CENTER);
	    frame.add(middlePanel);
	    frame.pack();
	    // Восстанавливаем положение
	    x = Integer.parseInt(frameSizeX);
	    y = Integer.parseInt(frameSizeY);
	    frame.setSize(x, y);
	    // Восстанавливаем размеры
	    x = Integer.parseInt(framePosX);
	    y = Integer.parseInt(framePosY);
	    frame.setLocation(x, y);
	    frame.setVisible(true);
	    

	    frame.addWindowListener(new WindowAdapter() {

	    	@Override
            public void windowClosing(WindowEvent e) {
	    		saveNote(noteFile);
            }
        });
	    
	    middlePanel.addMouseListener(new MouseAdapter() {
	    	public void mousePressed(MouseEvent me) {
	    		UIManager.put("swing.boldMetal", Boolean.FALSE);
	    		saveNote(noteFile);
	    		new RenameNote(noteFile);
	    		frame.setVisible(false);
	    	}
	    });
	}
	
	public void saveNote(String noteFileName)  {
		final File noteToSave = new File(Main.userNotesPath+"/data/"+noteFileName);
		
		data=noteName+"\n"+frame.getX()+"\n"+frame.getY()+"\n"+frame.getSize().width+"\n"+frame.getSize().height+"\n";
		
		FileWriter fileWriter;
		try {
			fileWriter = new FileWriter(noteToSave);
			fileWriter.write(data);
			fileWriter.append(display.getText());
			fileWriter.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public void createNewNote(String noteFileName) {
		final File newNote = new File(Main.userNotesPath+"/data/"+noteFileName);
		
		data="noname note\n600\n330\n500\n350\n";
		
		FileWriter fileWriter;
		try {
			fileWriter = new FileWriter(newNote);
			fileWriter.write(data);
			fileWriter.append(display.getText());
			fileWriter.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		Main.popup.removeAll();
		Main.firstPartOfMenu();
		Main.dynamicMenu();
		Main.secondPartOfMenu();
		
	}
}
