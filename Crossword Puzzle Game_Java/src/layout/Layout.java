package layout;

import java.awt.Dimension;
import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextPane;

/*
 * construct the layout of the all game
 */
public class Layout extends JFrame {

	private int x;
	private int y;
	private String[] instructions; 
	private String[] squareNames;
	private String finalSolution;

	Layout() {
	}

	public Layout(int x, int y, String[] instructions, String[] squareNames, String finalSolution) {

		this.setX(x);
		this.setY(y);
		this.setInstructions(instructions);
		this.setSquareNames(squareNames);
		this.setFinalSolution(finalSolution);

		// create an instance of JFrame named frame
		this.setTitle("Swedish-style crossword puzzle");
		this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setPreferredSize(new Dimension(1000,800));

		JPanel panel = new JPanel();
		// create an instance of JPanle named panel
		panel.setLayout(new BorderLayout());

		// text area
		JTextPane instruction = new Instructions(getInstructions());
		// create an instance of Instruction named instruction 
		//instruction.setEnabled(false);
		panel.add(instruction, BorderLayout.LINE_START);

		// x*y squares
		JPanel playfield = new Playfield(getX(),getY(),getSquareNames());
		// create an instance of JPanel named playfield
		panel.add(playfield, BorderLayout.CENTER);

		JPanel solution = new Solution(this.getFinalSolution());
		// create an instance of JPanle named solution
		panel.add(solution, BorderLayout.PAGE_END);

		this.getContentPane().add(panel);
		this.pack();
		this.setVisible(true);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public String[] getInstructions() {
		return instructions;
	}

	public void setInstructions(String[] instructions) {
		this.instructions = instructions;
	}

	public String[] getSquareNames() {
		return squareNames;
	}

	public void setSquareNames(String[] squareNames) {
		this.squareNames = squareNames;
	}

	public String getFinalSolution() {
		return finalSolution;
	}

	public void setFinalSolution(String finalSolution) {
		this.finalSolution = finalSolution;
	}





}

