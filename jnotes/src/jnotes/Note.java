package jnotes;

import java.awt.Color;
import java.awt.Font;

import javax.swing.*;


@SuppressWarnings("serial")
public class Note extends JPanel {
	
	JFrame frame = new JFrame();
	
	
	//
	
	Note() {
		frame.add(this);
		/*frame.setSize(111, 111);
		frame.setTitle("ku");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);*/
		frame.setBounds(100, 100, 450, 300);
		frame.setTitle("ku");
		frame.setBackground(Color.ORANGE);
		frame.setResizable(true);
		frame.setUndecorated(true);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
		
		JTextArea tArea;
		
		tArea = new JTextArea("fdfdf");
	      tArea.setFont(new Font("Serif", Font.ITALIC, 13));
	      tArea.setLineWrap(true);       // wrap line
	      tArea.setWrapStyleWord(true);  // wrap line at word boundary
	      tArea.setBackground(new Color(204, 238, 241)); // light blue
	      // Wrap the JTextArea inside a JScrollPane
	      JScrollPane tAreaScrollPane = new JScrollPane(tArea);
	      tAreaScrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
	      tAreaScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	      frame.add(tArea);
	}
}
