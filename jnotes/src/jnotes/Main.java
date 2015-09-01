package jnotes;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.URL;

import javax.swing.*;


public class Main {
	
	public static void main(String[] args) {
		// Проверяем созданы ли все директории, если нет, то создаем
		final String userNotesPath = System.getProperty("user.home")+"/.jnotes";
		final String userNotesDataPath = System.getProperty("user.home")+"/.jnotes/data";
		
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
		
		
		// Помещаем иконку в трэе
		final TrayIcon trayIcon = new TrayIcon(createImage("resources/icon.png", "tray icon"));
		final SystemTray tray = SystemTray.getSystemTray();
		trayIcon.setImageAutoSize(true);
        
        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            System.out.println("TrayIcon could not be added.");
            System.exit(1);
        }
        
        // Create a default popup menu components
        final PopupMenu popup = new PopupMenu();
        
        MenuItem newNote = new MenuItem("New note");
        MenuItem optionsItem = new MenuItem("Options");
        MenuItem exitItem = new MenuItem("Exit");
        
        popup.add(newNote);
        popup.addSeparator();
        
        // Динамическое меню
        
        String noteFileName;
        String mnu;
        
        File folder = new File(userNotesPath+"/data");
    	File[] listOfFiles = folder.listFiles();
    	for (int i = 0; i < listOfFiles.length; i++) {
    		if (listOfFiles[i].isFile()) {
    			noteFileName = listOfFiles[i].getName();
    			File f = new File(userNotesPath+"/data/"+noteFileName);
    			try {
					BufferedReader fin = new BufferedReader(new FileReader(f));
					try {
						// mnu = первоя строка заметки
						mnu = fin.readLine();
						MenuItem menuItem = new MenuItem (mnu);
						menuItem.setName("dsf");
						popup.add(menuItem);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
    		}
    	}
    	
    	
        popup.addSeparator();
        popup.add(optionsItem);
        popup.addSeparator();
        popup.add(exitItem);
        
        trayIcon.setPopupMenu(popup);
        
        // Проверка
        popup.getItem(0).addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		System.out.println(popup.getItem(0).getName());
        	}
        });

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
        
	}
	
	//Obtain the image URL
    protected static Image createImage(String path, String description) {
        URL imageURL = Main.class.getResource(path);
        
        if (imageURL == null) {
            System.err.println("Resource not found: " + path);
            return null;
        } else {
            return (new ImageIcon(imageURL, description)).getImage();
        }
    }
}
