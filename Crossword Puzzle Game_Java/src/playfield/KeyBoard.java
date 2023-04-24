package playfield;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.WindowFocusListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
/*
 * After press one of the square (button), a keyboard pop up into the screen with 26 
 * alphabets(buttons) that we can enter. 
 * This KeyBoard class provide 26 alphabets(buttons) to input answer as well as receive
 * the location information of the pressed square from Squares class¡£
 */
public class KeyBoard extends JDialog {

	protected String title;
	protected final String[] characters = {"A", "Z", "E", "R", "T", "Y", "U", "I", "O", "P",
			"Q", "S", "D", "F", "G", "H", "J", "K", "L", "M", "W", "X", "C", "V", "B", "N" };
	protected JButton site;

	private String trueAnswer;
	// receive the true answer of specific blue squares from the BlueSquares class
	private JButton[] button = new JButton [26];
	// button is the variable name for JButton
	private ArrayList<String> arrayList;
	// arrayList is the variable name for an arraylist that derive from the String array with 26 letters
	private ArrayList<String> randomFive;
	// arrayList that contains 4 random letters with the true answer

	KeyBoard() {
	}
	//default constructor

	KeyBoard(JButton site, String title) {
		this.setTitle(title);
		//receive the title information
		this.setSite(site);
		// receive the information of location of pressed button
		ButtonListener bt = new ButtonListener(site);
		// pass the location information into ButtonListener, bt is an instance of ButtonListener
		JPanel keyBoard = new JPanel();
		// keyBoard is an instance of JPanel to contain the buttons those work as keyboard
		keyBoard.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		// c is an  an instance of GridBagConstraints to set the specific location of each buttion
		c.fill = GridBagConstraints.BOTH;
		int counter = 0;
		// counter works as a local variable to count the number of button in a row 
		// and work as a sign for get a newline 
		for (String s : this.characters) {
			// s is a local variable refers to the each character in String array "characters"
			JButton b = new JButton(s);
			// b is a local variable for each button
			b.setBackground(Color.WHITE);
			b.addActionListener(bt);

			c.gridx = counter;
			if (counter < 10) {
				c.gridy = 0;
			}else if (counter < 20)
			{
				c.gridx = counter-10;
				c.gridy = 1;
			}
			else if (counter <26)
			{
				c.gridx = counter-18;
				c.gridy = 2;
			}
			counter++;
			keyBoard.add(b,c);
			// set the specific location of individual button and put them in the JPanle kp
		}

		int width = Toolkit.getDefaultToolkit().getScreenSize().width;
		int height = Toolkit.getDefaultToolkit().getScreenSize().height;
		this.setBounds((width-400)/2,(height-200)/2,400,200);
		// get the width and height of the screen and put the pop-up keyboard at center
		this.add(keyBoard, BorderLayout.PAGE_END);
		this.setResizable(false);
		this.addWindowFocusListener(new WindowFocusListener() {
			public void windowGainedFocus(WindowEvent e) {
			}
			public void windowLostFocus(WindowEvent e) {
				if (SwingUtilities.isDescendingFrom(e.getOppositeWindow(), KeyBoard.this)) {
					return;
				}
				KeyBoard.this.setVisible(false);
			}
		});
		// close the keyboard when click outside the keyboard
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.pack();
		this.setVisible(true);
	}

	KeyBoard(JButton site, String title, String trueAnswer) {
		this.setSite(site);
		// receive the location information of pressed square
		this.setTitle(title);
		// receive the title of frame
		this.setTrueAnswer(trueAnswer);
		// receive the true answer for the input file
		ButtonListener bt = new ButtonListener(site);
		// pass the location information into ButtonListener, bt is an instance of ButtonListener
		JPanel blueKeyBoard = new JPanel();
		blueKeyBoard.setLayout(new GridBagLayout());
		// blueKeyBoard is an instance of JPanel to contain the buttons those work as keyboard
		GridBagConstraints c = new GridBagConstraints();
		// c is an  an instance of GridBagConstraints to set the specific location of each buttion
		c.fill = GridBagConstraints.BOTH;

		for(int i = 0; i <26; i++) {
			button [i] = new JButton(characters[i]);
			button [i].setBackground(Color.GRAY);
			c.gridx = i;
			if (i < 10) {
				c.gridy = 0;
			}else if (i < 20)
			{
				c.gridx = i-10;
				c.gridy = 1;
			}
			else if (i <26)
			{
				c.gridx = i-18;
				c.gridy = 2;
			}
			blueKeyBoard.add(button[i],c);
		}

		// create a JPanle kp to hold 26 buttons without any function with 26 alphabet

		arrayList = new ArrayList<String>(Arrays.asList(this.getCharacters()));  
		arrayList.remove(this.getTrueAnswer());
		// transform the String array that contains 26 letters into an ArrayList
		// to remove the true answer

		randomFive = new ArrayList<String>();
		// create a new arraylist named randomFive to store 4 random letters with the true anwser
		randomFive.add(this.getTrueAnswer());

		Random r = new Random();
		// r is a instance variable for random class
		while(randomFive.size() < 5) {
			int rn = r.nextInt(25);
			// rn is the random number that generated by random class
			String rc = arrayList.get(rn);
			// rc is the random character that corresponding to the rn in the arra
			if(!randomFive.contains(rc)) {
				randomFive.add(rc); 
			}
			// the random character can only be added into randomFive arraylist when it is not in
			// the arraylist before
		}

		for (String symbol : randomFive) {
			for(int i = 0; i <26; i++) {
				if(symbol.equals((button [i]).getText())) {
					button [i].setBackground(Color.WHITE);
					(button [i]).addActionListener(bt);
				}
			}
		}

		// find the corresponding button in keyboard and add listener to them  

		this.add(blueKeyBoard, BorderLayout.PAGE_END);
		int width = Toolkit.getDefaultToolkit().getScreenSize().width;
		int height = Toolkit.getDefaultToolkit().getScreenSize().height;
		this.setBounds((width-400)/2,(height-200)/2,400,200);
		this.addWindowFocusListener(new WindowFocusListener() {
			public void windowGainedFocus(WindowEvent e) {
			}
			public void windowLostFocus(WindowEvent e) {
				if (SwingUtilities.isDescendingFrom(e.getOppositeWindow(), KeyBoard.this)) {
					return;
				}
				KeyBoard.this.setVisible(false);
			}
		});

		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.pack();
		this.setVisible(true);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public JButton getSite() {
		return site;
	}

	public void setSite(JButton site) {
		this.site = site;
	}

	public String getTrueAnswer() {
		return trueAnswer;
	}

	public void setTrueAnswer(String trueAnswer) {
		this.trueAnswer = trueAnswer;
	}

	public JButton[] getButton() {
		return button;
	}

	public void setButton(JButton[] button) {
		this.button = button;
	}

	public ArrayList<String> getArrayList() {
		return arrayList;
	}

	public void setArrayList(ArrayList<String> arrayList) {
		this.arrayList = arrayList;
	}

	public ArrayList<String> getRandomFive() {
		return randomFive;
	}

	public void setRandomFive(ArrayList<String> randomFive) {
		this.randomFive = randomFive;
	}

	public String[] getCharacters() {
		return characters;
	}




}
