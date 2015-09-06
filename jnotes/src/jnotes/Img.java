package jnotes;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Img {
	protected Image img;
    
	public Img(String fileName) {
		ImageIcon ii = new ImageIcon(this.getClass().getResource("resources/" + fileName));
		img = ii.getImage();
	}
}
