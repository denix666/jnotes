package jnotes;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.URL;

import javax.swing.*;


public class Main {

	public static void main(String[] args) {
		final String userNotesPath = System.getProperty("user.home")+"/.jnotes";
		
		File userNotesDir = new File(userNotesPath);
		if (!userNotesDir.exists()) {
		    System.out.println("creating directory: " + userNotesPath);
		    userNotesDir.mkdir();
		}
		
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
        MenuItem exitItem = new MenuItem("Exit");
        
        // Убрать потом
        MenuItem intItem = new MenuItem("Check IT");
        popup.add(intItem);
        
        popup.addSeparator();
        popup.add(exitItem);
        trayIcon.setPopupMenu(popup);
        
        
        // Для разных проверок
        intItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	//System.out.println(System.getProperty("user.home"));
            	
            	File f = new File(userNotesPath+"/data/1234.jnote");

                try {
					BufferedReader fin = new BufferedReader(new FileReader(f));
					try {
						// Вывод первой строки
						System.out.println(fin.readLine());
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
            }
        });
        
        trayIcon.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,"This dialog box is run from System Tray");
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
