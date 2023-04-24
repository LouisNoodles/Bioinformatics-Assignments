package layout;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JButton;
import javax.swing.JPanel;
import playfield.BlackSquares;
import playfield.BlueSquares;
import playfield.GraySquares;
import playfield.Squares;
import playfield.WhiteSquares;


/*
 * construct the playfiled of game (multiple squares in different color with different function) 
 */
public class Playfield extends JPanel{
	private int m;
	private int n;
	private String[] squareNames;

	Playfield(){
	}

	Playfield(int x, int y, String[] squareNames){
		this.setM(x);
		this.setN(y);
		this.setSquareNames(squareNames);
		this.setPreferredSize(new Dimension(m*50,n*50));
		this.setLayout(new GridLayout(m,n));

		JButton square = null;
		for  (String label:squareNames) {
			// label refers to each single squareNames
			switch(label){
			case "O":
				square = new WhiteSquares();
				break;
			case "S":
				square = new GraySquares();
				break;
			case "X":
				square = new BlackSquares();
				break;
			default :
				try {
					square = new BlueSquares(getH(label));
				}catch (Error e){
					e.printStackTrace();
				}
				break;
			}
			try {
				this.add(square);
			}catch (NullPointerException e){
				e.printStackTrace();
			}
		}    

		//create squares with different color and functions

	}
	protected String getH(String label) {
		String pattern = "(?<=\\()[A-Z](?=\\))";
		// regular expression pattern
		String trueAnswer = null;
		Pattern r = Pattern.compile(pattern);
		// r is an instance of Pattern
		Matcher match = r.matcher(label);
		// match is an instance of Matcher
		if (match.find( )) {
			trueAnswer = match.group(0);
		} 
		return trueAnswer;
		// get the character inside brackets, H(*), and return it
	}

	public int getM() {
		return m;
	}

	public void setM(int m) {
		this.m = m;
	}

	public int getN() {
		return n;
	}

	public void setN(int n) {
		this.n = n;
	}

	public String[] getSquareNames() {
		return squareNames;
	}

	public void setSquareNames(String[] squareNames) {
		this.squareNames = squareNames;
	}




}
