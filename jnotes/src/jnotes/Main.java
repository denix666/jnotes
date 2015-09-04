package jnotes;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.*;


public class Main {
	final static String userNotesPath = System.getProperty("user.home")+"/.jnotes";
	final static String userNotesDataPath = System.getProperty("user.home")+"/.jnotes/data";
	final static PopupMenu popup = new PopupMenu();
	final static TrayIcon trayIcon = new TrayIcon(createImage("resources/icon.png", "tray icon"));
	final static SystemTray tray = SystemTray.getSystemTray();
	
	public static void main(String[] args) {
		initDataDir();
		
		// Помещаем иконку в трэе
		trayIcon.setImageAutoSize(true);
        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            System.out.println("TrayIcon could not be added.");
            System.exit(1);
        }
        
        
        firstPartOfMenu();
        dynamicMenu();
        secondPartOfMenu();
        
        
        trayIcon.setPopupMenu(popup);
        
        trayIcon.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	UIManager.put("swing.boldMetal", Boolean.FALSE);
            	JOptionPane.showMessageDialog(null,"Control with right mouse click!");
            }
        });
	}
	
	public static void firstPartOfMenu() {
		// Create a default popup menu components
        MenuItem newNote = new MenuItem("New note");
        popup.add(newNote);
        popup.addSeparator();
        
        newNote.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	UIManager.put("swing.boldMetal", Boolean.FALSE);
            	Date date = new Date();
            	String newNote = new SimpleDateFormat("YYYYMMddHHmmss").format(date);
            	new Note(newNote+".jnote");
            }
        });
	}
	
	public static void secondPartOfMenu() {
		MenuItem optionsItem = new MenuItem("Options");
        MenuItem exitItem = new MenuItem("Exit");
        popup.addSeparator();
        popup.add(optionsItem);
        popup.addSeparator();
        popup.add(exitItem);
        
        exitItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tray.remove(trayIcon);
                System.exit(0);
            }
        });
	}
	
	public static void dynamicMenu() {
		String noteFileName;
        String noteName;
        
        File folder = new File(userNotesPath+"/data");
    	File[] listOfFiles = folder.listFiles();
    	
    	for (int i = 0; i < listOfFiles.length; i++) {
    		if (listOfFiles[i].isFile()) {
    			noteFileName = listOfFiles[i].getName();
    			File f = new File(userNotesPath+"/data/"+noteFileName);
    			try {
					BufferedReader fin = new BufferedReader(new FileReader(f));
					try {
						// noteName = первоя строка заметки т.е будет как ее заголовок
						noteName = fin.readLine();
						final MenuItem menuItem = new MenuItem(noteName);
						menuItem.setName(noteFileName);
						popup.add(menuItem);
						menuItem.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
			            		// Новое окошко с параметром имени заметки
								UIManager.put("swing.boldMetal", Boolean.FALSE);
								new Note(menuItem.getName());
			            	}
			            });
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
    		}
    	}
	}
	
	// Obtain the image URL
    protected static Image createImage(String path, String description) {
        URL imageURL = Main.class.getResource(path);
        
        if (imageURL == null) {
            System.err.println("Resource not found: " + path);
            return null;
        } else {
            return (new ImageIcon(imageURL, description)).getImage();
        }
    }
    
    protected static void initDataDir() {
    	// Проверяем созданы ли все директории, если нет, то создаем
		File userNotesDir = new File(userNotesPath);
		if (!userNotesDir.exists()) {
		    System.out.println("creating directory: " + userNotesPath);
		    userNotesDir.mkdir();
		}
		
		File userNotesDataDir = new File(userNotesDataPath);
		if (!userNotesDataDir.exists()) {
		    System.out.println("creating directory: " + userNotesDataPath);
		    userNotesDataDir.mkdir();
		}
    }
}
