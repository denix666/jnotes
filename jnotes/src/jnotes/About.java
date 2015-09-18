package jnotes;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;


@SuppressWarnings("serial")
public class About extends JDialog {
	final JLabel filler = new JLabel();
	final JLabel lbl1 = new JLabel();
	final JLabel lbl2 = new JLabel();
	final JLabel lbl3 = new JLabel();
	final JLabel lbl4 = new JLabel();
	final JLabel lblImage = new JLabel();
	final JButton btn = new JButton();
	final Img notesIcon = new Img("icon.png");
	final ImageIcon about_icon = new ImageIcon(createImage("resources/icon.png", "JNotes"));
	
	About() {
		lbl1.setText("JNotes v" + Main.version);
		lbl1.setFont(new Font("Courier", Font.PLAIN, 22));
		lbl1.setForeground(Color.DARK_GRAY);
		lbl1.setBounds(190, 30, 400, 20);
		
		lbl2.setText("Author:  Denis Salmanovich");
		lbl2.setFont(new Font("Courier", Font.TRUETYPE_FONT, 13));
		lbl2.setForeground(Color.BLACK);
		lbl2.setBounds(170, 80, 400, 20);
		
		lbl3.setText("JNotes Web site:");
		lbl3.setFont(new Font("Courier", Font.TRUETYPE_FONT, 13));
		lbl3.setForeground(Color.BLACK);
		lbl3.setBounds(200, 140, 150, 20);
		
		lbl4.setText("http://os.vc/jnotes");
		lbl4.setFont(new Font("Courier", Font.TRUETYPE_FONT, 13));
		lbl4.setForeground(Color.BLUE);
		lbl4.setBounds(190, 160, 170, 20);
		
		lblImage.setIcon(about_icon);
		lblImage.setBounds(10, 0, 150, 150);
		
		btn.setText("Close");
		btn.setBounds(310, 240, 80, 25);
		
		setPreferredSize(new Dimension(425, 286));
		this.setLocationRelativeTo(null);

		
		this.add(filler);
		this.setIconImage(notesIcon.img);
		filler.add(lbl1);
		filler.add(lbl2);
		filler.add(lbl3);
		filler.add(lbl4);
		filler.add(lblImage);
		filler.add(btn);
		this.setSize(400,300);
		this.setResizable(false);
		this.pack();
		
		btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	closeMe();
            }
        });
		
		lbl4.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lbl4.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				closeMe();
				OpenBrowser.openURL("http://os.vc/jnotes");
			}
		});
	}
	
	public void closeMe() {
		this.setVisible(false);
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
