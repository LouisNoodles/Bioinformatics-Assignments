package layout;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

/*
 * pop-up dialog to show player whether its answer is true or false
 */

public class Popup extends JDialog{
	private String message;
	// message that show in the dialog
	Popup(){
	}

	Popup (String message){
		this.setMessage(message);
		JOptionPane.showMessageDialog(this,this.getMessage());
		this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


}