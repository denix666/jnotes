package jnotes;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.*;


public class Main {
	final static String userNotesPath = System.getProperty("user.home")+"/.jnotes";
	final static String userNotesDataPath = System.getProperty("user.home")+"/.jnotes/data";
	final static PopupMenu popup = new PopupMenu();
	final static TrayIcon trayIcon = new TrayIcon(createImage("resources/icon.png", "tray icon"));
	final static SystemTray tray = SystemTray.getSystemTray();
	final static int[] myArray = new int[2];
	
	public static int index;
	
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
        
        // Create a default popup menu components
        MenuItem newNote = new MenuItem("New note");
        MenuItem optionsItem = new MenuItem("Options");
        MenuItem exitItem = new MenuItem("Exit");
        
        popup.add(newNote);
        popup.addSeparator();
        
        // Динамическое меню
        dynamicMenu();
          
        popup.addSeparator();
        popup.add(optionsItem);
        popup.addSeparator();
        popup.add(exitItem);
        
        trayIcon.setPopupMenu(popup);
        
        trayIcon.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //JOptionPane.showMessageDialog(null,"Управление правой кнопкой мышки!");
            	
            	new Note();
            	
            }
        });
        
        exitItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tray.remove(trayIcon);
                System.exit(0);
            }
        });
        
        for (int g=0; g<2; g++) {
        	index = g+2;
        	System.out.println(index+"---"+popup.getItem(index).getName());
        	popup.getItem(index).addActionListener(new ActionListener() {
        		public void actionPerformed(ActionEvent e) {
            		System.out.println(popup.getItem(index).getName());
            	}
            });
        }
	}
	
	protected static void dynamicMenu() {
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
						// mnu = первоя строка заметки т.е будет как ее заголовок
						noteName = fin.readLine();
						MenuItem menuItem = new MenuItem (noteName);
						menuItem.setName(noteFileName);
						popup.add(menuItem);
						
						myArray[i] = i+2;
						
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
