package jnotes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;


public class Note extends JPanel{
	
	JFrame frame = new JFrame();
	JPanel middlePanel = new JPanel();
	JTextArea display = new JTextArea(20,40);
	String noteName = null;
	String data = null;
	String framePosX, framePosY = null;
	
	public Note(String noteFileName) {
		
		
		final File note = new File(Main.userNotesPath+"/data/"+noteFileName);
		try {
			BufferedReader br = new BufferedReader(new FileReader(note));
			try {
				noteName = br.readLine();
				framePosX = br.readLine();
				framePosY = br.readLine();
				while ((data = br.readLine()) != null) {
					display.append(data+"\n");
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}


	    middlePanel.setBorder(new TitledBorder(new EtchedBorder(),noteName));
	    middlePanel.setLayout(new BorderLayout());
	    
	    display.setEditable(true);
	    display.setBackground(Color.ORANGE);
	    JScrollPane scroll = new JScrollPane(display);
	    scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	    middlePanel.add (scroll,BorderLayout.CENTER);
	    frame.add(middlePanel);
	    frame.pack();
	    int x = Integer.parseInt(framePosX);
	    int y = Integer.parseInt(framePosY);
	    frame.setLocation(x, y);
	    frame.setVisible(true);
	    
	    
	    frame.addWindowListener(new WindowAdapter() {

	    	@Override
            public void windowClosing(WindowEvent e) {
	    		
	    		data=noteName+"\n"+frame.getX()+"\n"+frame.getY()+"\n";
	    		
	    		FileWriter fileWriter;
				try {
					fileWriter = new FileWriter(note);
					fileWriter.write(data);
					fileWriter.append(display.getText());
					fileWriter.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
            }
        });
	    
	    middlePanel.addMouseListener(new MouseAdapter() {
	    	public void mousePressed(MouseEvent me) {
	    		System.out.println("Click");
	    	}
	    });
	}
}
