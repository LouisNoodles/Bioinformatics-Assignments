package layout;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

/*
 * construct the input area of the final solution
 */
public class InputSolution extends JPanel {

	private JButton[] inputbutton;
	// input button area 
	private String[] solutionArray;
	// receive the solution of puzzle in array form

	InputSolution(){

	}


	InputSolution(String[] solutionArray){

		this.setSolutionArray(solutionArray);
		this.setInputbutton(new JButton [solutionArray.length]);
		this.setLayout(new GridBagLayout());
		GridBagConstraints constraint = new GridBagConstraints();
		// create an instance of GridBagConstraints named constraint to put each square together
		constraint.fill = GridBagConstraints.BOTH;
		for(int i = 0; i < solutionArray.length; i++) {
			inputbutton [i] = new playfield.GraySquares();
			inputbutton [i].setPreferredSize(new Dimension(50,50));
			constraint.gridx = i;
			constraint.gridy = 0;
			this.add(inputbutton[i],constraint);	
		}

	}


	public JButton[] getInputbutton() {
		return inputbutton;
	}


	public void setInputbutton(JButton[] inputbutton) {
		this.inputbutton = inputbutton;
	}


	public String[] getSolutionArray() {
		return solutionArray;
	}


	public void setSolutionArray(String[] solutionArray) {
		this.solutionArray = solutionArray;
	}







}


