package jnotes;

import java.awt.Image;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

@SuppressWarnings("serial")
public class Options extends JFrame {
	String data = null;
	final JTabbedPane tabbedPane = new JTabbedPane();
	final JPanel panel = new JPanel();
	final Img optionsIcon = new Img("options_icon.png");
	final ImageIcon preferences_icon = new ImageIcon(createImage("resources/preferences_icon.png", "Preferences"));
	final ImageIcon network_icon = new ImageIcon(createImage("resources/network_icon.png", "Preferences"));
	final File settingsFile = new File(Main.userNotesPath+"/jnotes_settings.ini");
	
	Options() {

		if (!settingsFile.isFile()) {
			createDefaultSettings();
		}
		
		JPanel panelPreferences = new JPanel();
		tabbedPane.addTab("Preferences", preferences_icon, panelPreferences);
		
		JPanel panelNetworkSettings = new JPanel();
		tabbedPane.addTab("Network settings", network_icon, panelNetworkSettings);

		this.add(tabbedPane);
		this.setIconImage(optionsIcon.img);
		this.setTitle("JNotes Options:");
		this.setSize(600,400);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
	}
	
	public void createDefaultSettings() {
		data="-154\n";
		
		FileWriter fileWriter;
		try {
			fileWriter = new FileWriter(settingsFile);
			fileWriter.write(data);
			fileWriter.close();
		} catch (IOException e1) {
			e1.printStackTrace();
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
}
