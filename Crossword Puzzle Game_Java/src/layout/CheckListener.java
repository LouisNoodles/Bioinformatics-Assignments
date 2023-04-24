package layout;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;

/*
 * check the answer that player press the "check" button and pop up whether he or she made it or not
 */
public class CheckListener implements ActionListener {

	private boolean answer = false;
	private JButton[] inputButton;
	private String [] solutionArray;

	CheckListener(){
	}

	CheckListener(JButton[] inputButton, String[] solutionArray){
		this.setInputButton(inputButton);
		this.setSolutionArray(solutionArray);

	}
	public void actionPerformed(ActionEvent e) {
		answer = true;
		for(int i=0;i<9;i++) {
			if(!inputButton[i].getText().equals(solutionArray[i].toUpperCase())) {
				answer = false;
				break;
			}
		}
		if(answer == true) {
			JDialog trueAnswer = new Popup("Congratulation! You made it!!!");
		}else {
			JDialog falseAnswer = new Popup("Hang in there! Don't give up!");
		}
	}

	public boolean isAnswer() {
		return answer;
	}

	public void setAnswer(boolean answer) {
		this.answer = answer;
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
