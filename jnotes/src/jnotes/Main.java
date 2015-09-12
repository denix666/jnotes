package jnotes;


import java.awt.AWTException;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.UIManager;



public class Main {
	final static String userNotesPath = System.getProperty("user.home")+"/.jnotes";
	final static String userNotesDataPath = System.getProperty("user.home")+"/.jnotes/data";
	final static JPopupMenu popup = new JPopupMenu();
	final static TrayIcon trayIcon = new TrayIcon(createImage("resources/icon.png", "JNotes"));
	final static SystemTray tray = SystemTray.getSystemTray();
	final static String version = "0.3";
	final static About about = new About();
	final static ImageIcon exit_icon = new ImageIcon(createImage("resources/exit_icon.png", "Exit"));
	final static ImageIcon options_icon = new ImageIcon(createImage("resources/options_icon.png", "Options"));
	final static ImageIcon about_icon = new ImageIcon(createImage("resources/about_icon.png", "About"));
	final static ImageIcon new_icon = new ImageIcon(createImage("resources/new_icon.png", "New note"));
	
	public static void main(String[] args) {
		UIManager.put("swing.boldMetal", Boolean.FALSE);
		
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
        
        trayIcon.addMouseListener(new MouseAdapter() {
        	public void mousePressed(MouseEvent e) {
        		if (e.isPopupTrigger()) {
        			popup.setLocation(e.getX(), e.getY());
        			popup.setInvoker(popup);
        			popup.setVisible(true);
        		}
        	}
        });
	}
	
	public static void firstPartOfMenu() {
		// Create a default popup menu components
		JMenuItem newNote = new JMenuItem("New note", new_icon);
        popup.add(newNote);
        popup.addSeparator();
        
        newNote.getAccessibleContext().setAccessibleDescription("New note");
        newNote.setMnemonic(KeyEvent.VK_P);
        newNote.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	UIManager.put("swing.boldMetal", Boolean.FALSE);
            	Date date = new Date();
            	String newNote = new SimpleDateFormat("YYYYMMddHHmmss").format(date);
            	popup.setVisible(false);
            	new Note(newNote+".jnote");
            }
        });
	}
	
	public static void secondPartOfMenu() {
		
		
		JMenuItem optionsItem = new JMenuItem("Options", options_icon);
		JMenuItem aboutItem = new JMenuItem("About", about_icon);
		JMenuItem exitItem = new JMenuItem("Exit", exit_icon);
		
        popup.addSeparator();
        popup.add(optionsItem);
        popup.addSeparator();
        popup.add(aboutItem);
        popup.addSeparator();
        popup.add(exitItem);
        
        exitItem.getAccessibleContext().setAccessibleDescription("Exit");
        exitItem.setMnemonic(KeyEvent.VK_P);
        exitItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	popup.setVisible(false);
            	tray.remove(trayIcon);
                System.exit(0);
            }
        });
        
        aboutItem.getAccessibleContext().setAccessibleDescription("About");
        aboutItem.setMnemonic(KeyEvent.VK_P);
        aboutItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                about.setVisible(true);
                popup.setVisible(false);
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
						fin.close();
						final JMenuItem menuItem = new JMenuItem(noteName);
						menuItem.setMnemonic(KeyEvent.VK_P);
						menuItem.getAccessibleContext().setAccessibleDescription(noteName);
						menuItem.setName(noteFileName);
						popup.add(menuItem);
						menuItem.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
			            		// Новое окошко с параметром имени заметки
								UIManager.put("swing.boldMetal", Boolean.FALSE);
								popup.setVisible(false);
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
