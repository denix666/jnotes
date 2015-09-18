package jnotes;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

@SuppressWarnings("serial")
public class Options extends JFrame {
	String data = null;
	String defaultNoteColor = null;
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
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(settingsFile));
			try {
				defaultNoteColor = br.readLine();
				br.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		
		final JButton btnClosePreferences = new JButton();
		btnClosePreferences.setBounds(480, 300, 80, 25);
		btnClosePreferences.setText("Close");
		btnClosePreferences.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	closeMe();
            }
        });
		
		final JButton btnCloseNetworkSettings = new JButton();
		btnCloseNetworkSettings.setBounds(480, 300, 80, 25);
		btnCloseNetworkSettings.setText("Close");
		btnCloseNetworkSettings.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	closeMe();
            }
        });
		
		final JLabel lbl1 = new JLabel();
		lbl1.setText("Default color for new notes:");
		lbl1.setBounds(20, 20, 400, 20);
		
		final JButton btnColor = new JButton();
		btnColor.setBounds(230, 20, 120, 20);
		btnColor.setBackground(Color.decode(defaultNoteColor));
		btnColor.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	Color selectedColor = JColorChooser.showDialog(
            			null, 
            			"Pick a Color", 
            			Color.YELLOW);
            	btnColor.setBackground(selectedColor);
            	int newColor = btnColor.getBackground().getRGB();
            	defaultNoteColor = Integer.toString(newColor);
            	saveSettings();
            }
        });
		
		JPanel panelPreferences = new JPanel();
		JLabel fillerPreferences = new JLabel();
		tabbedPane.addTab("Preferences", preferences_icon, panelPreferences);
		fillerPreferences.setHorizontalAlignment(JLabel.CENTER);
		panelPreferences.setLayout(new GridLayout(1, 1));
		panelPreferences.add(fillerPreferences);
		fillerPreferences.add(btnClosePreferences);
		fillerPreferences.add(lbl1);
		fillerPreferences.add(btnColor);
		
		JPanel panelNetworkSettings = new JPanel();
		JLabel fillerNetworkSettings = new JLabel();
		tabbedPane.addTab("Network settings", network_icon, panelNetworkSettings);
		fillerNetworkSettings.setHorizontalAlignment(JLabel.CENTER);
		panelNetworkSettings.setLayout(new GridLayout(1, 1));
		panelNetworkSettings.add(fillerNetworkSettings);
		fillerNetworkSettings.add(btnCloseNetworkSettings);
		

		this.add(tabbedPane);
		this.setIconImage(optionsIcon.img);
		this.setTitle("JNotes Options:");
		this.setSize(600,400);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		
	}
	
	public void closeMe() {
		this.setVisible(false);
	}
	
	public void saveSettings() {
		data = defaultNoteColor + "\n";
		
		FileWriter fileWriter;
		try {
			fileWriter = new FileWriter(settingsFile);
			fileWriter.write(data);
			fileWriter.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
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
