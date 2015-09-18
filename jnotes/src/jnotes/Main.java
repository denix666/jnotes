package jnotes;

import java.awt.Color;
import java.io.File;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

@SuppressWarnings("unused")
public class Main {
	final static String userNotesPath = System.getProperty("user.home")+"/.jnotes";
	final static String userNotesDataPath = System.getProperty("user.home")+"/.jnotes/data";
	final static String version = "0.8";
	
	final static Tray tray = new Tray();
	
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	    UIManager.put("swing.boldMetal", Boolean.FALSE);
	    
		initDataDir();

        Tray.firstPartOfMenu();
        Tray.dynamicMenu();
        Tray.secondPartOfMenu();
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
