package layout;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.*;
import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

/*
 * construct the instructions part of the game
 */

public class Instructions extends JTextPane{

	private String [] instructions;

	private List<String> arraylist;

	private boolean isMatch;

	Instructions(){
	}

	Instructions(String [] instructions) {
		this.setInstructions(instructions);
		// receive the information for the instruction part

		this.setFont(new Font("Segoe UI", Font.PLAIN, 12));

		SimpleAttributeSet blue = new SimpleAttributeSet();
		StyleConstants.setForeground(blue, Color.blue);
		StyleConstants.setBold(blue, true);
		// set a font that is bold and in blue color

		arraylist = new ArrayList<String>();
		for(int i = 0; i< instructions.length; i ++) {
			for (String retval:instructions[i].split(" ",2)){
				arraylist.add(retval);
			}
		}
		// to further separate the content of instructions for setting the font of different part
		// and then store them in the variable named arraylist


		append("Cross puzzle instructions\r\n", blue);
		String pattern1 = "^[A-Z]+";
		String pattern2 = "(\\d+).";
		String pattern3 = "^[0-9,]+.*";
		/*
		there are there regular expression pattern stand for pattern1: "all upper case letter",
		pattern2: "start with digital number and end with dot", pattern3: "start with several 
		digital number and end with comma"
		 */

		for(String element: arraylist)
		{	
			if (isMatch = Pattern.matches(pattern1, element)) {
				append("\r\n",null);
				append(element,blue);
				append("\r\n",null);
			}else if (isMatch = Pattern.matches(pattern2, element)) {
				append(element,blue);
				append(" ",null);
			}else if (isMatch = Pattern.matches(pattern3, element)) {
				append(element,blue);
				append("\r\n",null);
			}
			else {
				append(element,null);
				append("\r\n",null);
			}
		}
	}
	// for loop to change the font of each recognized pattern separately 

	protected void append(String s, AttributeSet attributes) {
		Document d = this.getDocument();
		try {
			d.insertString(d.getLength(), s, attributes);
		} catch (BadLocationException ble) {
		}
	}
	// method append is designed to add each part into "text area"

	public String[] getInstructions() {
		return instructions;
	}

	public void setInstructions(String[] instructions) {
		this.instructions = instructions;
	}

	public List<String> getArraylist() {
		return arraylist;
	}

	public void setArraylist(List<String> arraylist) {
		this.arraylist = arraylist;
	}

	public boolean isMatch() {
		return isMatch;
	}

	public void setMatch(boolean isMatch) {
		this.isMatch = isMatch;
	}


	

}
