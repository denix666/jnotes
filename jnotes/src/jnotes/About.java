package jnotes;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class About extends JDialog {
	
	
	JLabel lbl1 = new JLabel();
	JLabel lbl2 = new JLabel();
	Img aboutBg = new Img("about_bg.png");
	JPanel panel = new JPanel();
	JLabel background = new JLabel(new ImageIcon(aboutBg.img));
	
	About() {
		lbl1.setText("JNotes v"+Main.version);
		lbl1.setFont(new Font("Arial", Font.ITALIC, 22));
		lbl1.setForeground(Color.BLUE);
		lbl1.setBounds(90, 10, 200, 20);
		
		lbl2.setText("Author:  Denis Salmanovich");
		lbl2.setFont(new Font("Arial", Font.TRUETYPE_FONT, 13));
		lbl2.setForeground(Color.BLACK);
		lbl2.setBounds(60, 40, 300, 20);
		
		this.setTitle("JNotes v"+Main.version);
		//setPreferredSize(new Dimension(330, 110));
		this.setLocationRelativeTo(null);
		
		//this.add(background);
		this.add(lbl1);
		this.add(lbl2);
		
		this.getContentPane().add(background);
		
		this.setUndecorated(true);
		this.getRootPane().setOpaque(false);
		this.getContentPane().setBackground (new Color (3,3,3,3));
		this.setBackground(new Color (3,3,3,3));
		
		//getContentPane().add(panel);
		//getContentPane().add(background);
		this.pack();
	}
}
