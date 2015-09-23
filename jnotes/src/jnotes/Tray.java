package jnotes;

import java.awt.AWTException;
import java.awt.Frame;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.UIManager;

public class Tray {
	final static TrayIcon trayIcon = new TrayIcon(createImage("resources/icon.png", "JNotes"));
	final static SystemTray tray = SystemTray.getSystemTray();
	final static JPopupMenu popup = new JPopupMenu();
	final static ImageIcon exit_icon = new ImageIcon(createImage("resources/exit_icon.png", "Exit"));
	final static ImageIcon options_icon = new ImageIcon(createImage("resources/options_icon.png", "Options"));
	final static ImageIcon about_icon = new ImageIcon(createImage("resources/about_icon.png", "About"));
	final static ImageIcon new_icon = new ImageIcon(createImage("resources/new_icon.png", "New note"));
	final static ImageIcon hide_icon = new ImageIcon(createImage("resources/hide_icon.png", "Hide menu"));
	final static About about = new About();
	final static Options options = new Options();
	static int foundFrame;
	
	Tray() {
		trayIcon.setToolTip("JNotes v" + Main.version);
		trayIcon.setImageAutoSize(true);
		try {
			tray.add(trayIcon);
		} catch (AWTException e) {
			System.out.println("TrayIcon could not be added.");
			System.exit(1);
		}
		
		trayIcon.addMouseListener(new MouseAdapter() {
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
		newNote.setMnemonic(KeyEvent.VK_P);
        newNote.getAccessibleContext().setAccessibleDescription("New note");
        newNote.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	UIManager.put("swing.boldMetal", Boolean.FALSE);
            	Date date = new Date();
            	String newNote = new SimpleDateFormat("YYYYMMddHHmmss").format(date);
            	popup.setVisible(false);
            	new Note(newNote+".jnote");
            }
        });
		popup.add(newNote);
		popup.addSeparator();
	}
	
	public static void secondPartOfMenu() {
		// Create a default popup menu components
		popup.addSeparator();
		JMenuItem hideItem = new JMenuItem("Hide menu", hide_icon);
		hideItem.setMnemonic(KeyEvent.VK_P);
        hideItem.getAccessibleContext().setAccessibleDescription("Hide menu");
        hideItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	popup.setVisible(false);
            }
        });
        popup.add(hideItem);
        popup.addSeparator();
        
		JMenuItem optionsItem = new JMenuItem("Options", options_icon);
		optionsItem.setMnemonic(KeyEvent.VK_P);
		optionsItem.getAccessibleContext().setAccessibleDescription("Options");
		optionsItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                options.setVisible(true);
                options.toFront();
                options.repaint();
                popup.setVisible(false);
            }
        });
		popup.add(optionsItem);
		popup.addSeparator();
        
		JMenuItem aboutItem = new JMenuItem("About", about_icon);
		aboutItem.setMnemonic(KeyEvent.VK_P);
        aboutItem.getAccessibleContext().setAccessibleDescription("About");
        aboutItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                about.setVisible(true);
                about.toFront();
                about.repaint();
                popup.setVisible(false);
            }
        });
        popup.add(aboutItem);
        popup.addSeparator();
        
		JMenuItem exitItem = new JMenuItem("Exit", exit_icon);
		exitItem.setMnemonic(KeyEvent.VK_P);
        exitItem.getAccessibleContext().setAccessibleDescription("Exit");
        exitItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	popup.setVisible(false);
            	tray.remove(trayIcon);
                System.exit(0);
            }
        });
        popup.add(exitItem);
	}
	
	public static void dynamicMenu() {
		String noteFileName;
        String noteName;
        String noteColor;
        
        File folder = new File(Main.userNotesPath+"/data");
    	File[] listOfFiles = folder.listFiles();
    	
    	for (int i = 0; i < listOfFiles.length; i++) {
    		if (listOfFiles[i].isFile()) {
    			noteFileName = listOfFiles[i].getName();
    			File f = new File(Main.userNotesPath+"/data/"+noteFileName);
    			
    			try {
					BufferedReader fin = new BufferedReader(new FileReader(f));
					try {
						// noteName = первоя строка заметки т.е будет как ее заголовок
						noteName = fin.readLine();
						noteColor = fin.readLine();
						fin.close();
						final JMenuItem menuItem = new JMenuItem(noteName,new ColorIcon(noteColor));
						menuItem.setMnemonic(KeyEvent.VK_P);
						menuItem.getAccessibleContext().setAccessibleDescription(noteName);
						menuItem.setName(noteFileName);
						menuItem.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
			            		// Новое окошко с параметром имени заметки
								UIManager.put("swing.boldMetal", Boolean.FALSE);
								popup.setVisible(false);
								
								String noteTitle = noteTitle(menuItem.getName());
								Frame[] activeframes = Frame.getFrames();
								boolean isVisible = false;

								for (int i=0; i < activeframes.length; i++) {
									String frameTitle = activeframes[i].getTitle();
									if (noteTitle.equals(frameTitle)) {
										isVisible = activeframes[i].isVisible();
										foundFrame = i;
									}
								}
								
								if (!isVisible) {
									// Новое окошко с параметром имени файла заметки
									new Note(menuItem.getName());
								} else {
									// Если окошко уже открыто, только показываем его
									activeframes[foundFrame].toFront();
								}
			            	}
			            });
						popup.add(menuItem);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
    		}
    	}
	}
	
	public static String noteTitle(String fileName) {
		File file = new File(Main.userNotesPath+"/data/"+fileName);
		String title = null;
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			try {
				title = br.readLine();
				br.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		return title;
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
}
