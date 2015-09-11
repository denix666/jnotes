package jnotes;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;


public class About extends JDialog {
	JLabel background = new JLabel(new ImageIcon(this.getClass().getResource("resources/bg.jpg")));
	JLabel lbl1 = new JLabel();
	JLabel lbl2 = new JLabel();
	JButton btn = new JButton();
	
	About() {
		lbl1.setText("JNotes v" + Main.version);
		lbl1.setFont(new Font("Verdana", Font.PLAIN, 22));
		lbl1.setForeground(Color.BLUE);
		lbl1.setBounds(130, 30, 400, 20);
		
		lbl2.setText("Author:  Denis Salmanovich");
		lbl2.setFont(new Font("Verdana", Font.TRUETYPE_FONT, 13));
		lbl2.setForeground(Color.BLACK);
		lbl2.setBounds(110, 80, 400, 20);
		
		btn.setText("Close");
		btn.setBounds(320, 240, 80, 30);
		
		setPreferredSize(new Dimension(425, 286));
		this.setLocationRelativeTo(null);

		this.add(lbl1);
		this.add(lbl2);
		this.add(btn);
		this.getContentPane().add(background);
		this.setUndecorated(true);
		this.pack();
		
		btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	closeMe();
            }
        });
	}
	
	public void closeMe() {
		this.setVisible(false);
	}
}
