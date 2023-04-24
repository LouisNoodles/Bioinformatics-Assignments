package playfield;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.JButton;
/*
 * receive the information (which character is entered) when clicking on the keyboard,
 */
public class ButtonListener implements ActionListener {

	private String selectedCharacter;

	protected JButton location;

	ButtonListener() {
	}
	// default constructor

	ButtonListener(JButton location) {
		this.setLocation(location);
		// received location information of pressed square
	}

	public void actionPerformed(ActionEvent e) {
		selectedCharacter = ((JButton)e.getSource()).getText();
		//get the selected character information and then store in the variable selectedCharacter
		location.setFont(new Font("Arial", Font.PLAIN, 16));
		location.setText(this.getSelectedCharacter());
	}

	public String getSelectedCharacter() {
		return selectedCharacter;
	}

	public void setSelectedCharacter(String selectedCharacter) {
		this.selectedCharacter = selectedCharacter;
	}

	public JButton getLocation() {
		return location;
	}

	public void setLocation(JButton location) {
		this.location = location;
	}


}
