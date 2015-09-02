package jnotes;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


public class Note extends JPanel {

	JFrame frame = new JFrame();
	JTextArea textArea = new JTextArea();
	JScrollPane textAreaScrollPane = new JScrollPane(textArea);
	
	public Note(String noteFileName) {
		frame.add(this);
		frame.setSize(300, 300);
		frame.setTitle(noteFileName);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
        
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setFont(new Font("Serif", Font.ITALIC, 13));
		textArea.setBackground(new Color(204, 238, 241));
		
			
		textAreaScrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		textAreaScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		textArea.add(textAreaScrollPane);
		
		this.add(textArea);
		
		
	
		
		
		//frame.setLocationRelativeTo(null);
		//frame.setBounds(100, 100, 450, 300);
		//frame.setTitle("ku");
		//frame.setBackground(Color.ORANGE);
		//frame.setResizable(true);
		//frame.setUndecorated(true);
	}
}
