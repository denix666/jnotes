package jnotes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
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

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;



public class Note extends JPanel {
	
	public JFrame frame = new JFrame();
	JPopupMenu notePopup = new JPopupMenu();
	JPanel middlePanel = new JPanel();
	JTextArea display = new JTextArea(20,40);
	String noteName = null;
	String data = null;
	String framePosX, framePosY = null;
	String frameSizeX, frameSizeY = null;
	int x,y = 0;
	
	public Note(final String noteFileName) {

		final File note = new File(Main.userNotesPath+"/data/"+noteFileName);
		
		createPopupMenu(noteFileName);
		
		
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
	    middlePanel.setBorder(new TitledBorder(new EtchedBorder(),""));
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
	    		saveNote(noteFileName);
            }
        });
	    
	    display.addMouseListener(new MouseAdapter() {
			 
	        @Override
	        public void mousePressed(MouseEvent e) {
	            showPopup(e);
	        }

	        @Override
	        public void mouseReleased(MouseEvent e) {
	            showPopup(e);
	        }

	        private void showPopup(MouseEvent e) {
	            if (e.isPopupTrigger()) {
	            	notePopup.show(e.getComponent(), e.getX(), e.getY());
	            }
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
	
	public void createPopupMenu(final String noteFile) {
		JMenuItem renameNote = new JMenuItem("Rename",new ImageIcon(this.getClass().getResource("resources/rename_icon.png")));
		renameNote.setMnemonic(KeyEvent.VK_P);
		renameNote.getAccessibleContext().setAccessibleDescription("Rename");
		renameNote.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	String s = (String)JOptionPane.showInputDialog (null, "Enter new note name:", "Rename note", JOptionPane.PLAIN_MESSAGE,null, null,noteName);
            	if (s != null) {
            		frame.setTitle(s);
            		noteName = s;
            		saveNote(noteFile);
            		Main.popup.removeAll();
            		Main.firstPartOfMenu();
            		Main.dynamicMenu();
            		Main.secondPartOfMenu();
            	}
            }
        });
		notePopup.add(renameNote);
		
		JMenuItem deleteNote = new JMenuItem("Delete",new ImageIcon(this.getClass().getResource("resources/delete_icon.png")));
		deleteNote.setMnemonic(KeyEvent.VK_P);
		deleteNote.getAccessibleContext().setAccessibleDescription("Delete");
		deleteNote.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	System.out.println("Delete requested");
            }
        });
		notePopup.add(deleteNote);
	}
}
