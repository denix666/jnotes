package jnotes;


import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

public class RenameNote extends JFrame {
	
	JPanel panel = new JPanel();
	public JTextField txtField = new JTextField();
	JButton btn = new JButton();
	
	public RenameNote(String noteName) {
		//panel.setBackground(Color.pink);
		this.setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
		this.setTitle("Rename note:");
		this.setLocationRelativeTo(null);
		
		txtField.setBounds(10, 10, 300, 20);
		btn.setBounds(110, 40, 90, 20);
		btn.setText("Rename");
		//btn.setBackground(Color.red);
		txtField.setText(noteName);
		
		this.add(txtField);
		this.add(btn);
		getContentPane().add(panel);
		setPreferredSize(new Dimension(330, 110));
		this.pack();
		this.setVisible(true);
		
		btn.addMouseListener(new MouseAdapter() {
	    	public void mousePressed(MouseEvent me) {
	    		UIManager.put("swing.boldMetal", Boolean.FALSE);
	    		Note.
	    		setVisible(false);		
	    	}
	    });
	}
}
