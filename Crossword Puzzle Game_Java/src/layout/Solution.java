package layout;

import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JPanel;

/*
 * construct the panel for the final solution part
 */
public class Solution extends JPanel {
	
	private JButton[] inputButton;

	private String finalSolution;
	// String form of the final answer
	private String [] solutionArray;
	// array of button for enter the final answer
	
	Solution(){
	}

	Solution(String finalSolution){
		
		this.setFinalSolution(finalSolution);
		solutionArray = singleChars(this.getFinalSolution());
		
		// input area
		JPanel inputSolution = new InputSolution(this.getSolutionArray());
		// create an instance of InputSolution named inputSolution
		this.inputButton = ((InputSolution) inputSolution).getInputbutton();
		inputSolution.setPreferredSize(new Dimension((solutionArray.length)*50,50));
		this.add(inputSolution);
		

		// check button
		JButton check = new Check("Check", this.getInputButton(),this.getSolutionArray());
		// create an instance of Check named check
		this.add(check);
		
	}
	
	public static String[] singleChars(String s) {
	    return s.split("(?!^)");
	}

	public JButton[] getInputButton() {
		return inputButton;
	}

	public void setInputButton(JButton[] inputButton) {
		this.inputButton = inputButton;
	}

	public String getFinalSolution() {
		return finalSolution;
	}

	public void setFinalSolution(String finalSolution) {
		this.finalSolution = finalSolution;
	}

	public String[] getSolutionArray() {
		return solutionArray;
	}

	public void setSolutionArray(String[] solutionArray) {
		this.solutionArray = solutionArray;
	}
	

	
	
}