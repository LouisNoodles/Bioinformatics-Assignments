package layout;

import javax.swing.JButton;

/*
 * a check button for checking the final solution
 */

public class Check extends JButton{

	private JButton[] inputButton;
	private String [] solutionArray;

	Check () {
	}

	Check (String label, JButton[] inputButton, String [] solutionArray){
		this.setText(label);
		this.setInputButton(inputButton);
		this.setSolutionArray(solutionArray);

		CheckListener checkListener = new CheckListener(this.getInputButton(), this.getSolutionArray());
		this.addActionListener(checkListener);

	}

	public JButton[] getInputButton() {
		return inputButton;
	}

	public void setInputButton(JButton[] inputButton) {
		this.inputButton = inputButton;
	}

	public String[] getSolutionArray() {
		return solutionArray;
	}

	public void setSolutionArray(String[] solutionArray) {
		this.solutionArray = solutionArray;
	}




}
