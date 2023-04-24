package playfield;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JDialog;
import java.awt.Color;
import javax.swing.border.LineBorder;

/*
 * This Squares class is to construct the individual square with proper property
 */

public class Squares extends JButton{

	protected JButton site;
	// to store the location of each individual square
	
	public Squares() {
		this.setBorder(new LineBorder(Color.BLACK));
		this.addActionListener(new AbstractAction(){
			public void actionPerformed(ActionEvent e) {
				site = ((JButton)e.getSource());
				// add listener to get the location information
				JDialog keyBoard = new KeyBoard(site, "Enter a character");
				// keyBoard is the variable name of a KeyBoard
			}
		});
	}
	

	public JButton getSite() {
		return site;
	}

	public void setSite(JButton site) {
		this.site = site;
	}




}




