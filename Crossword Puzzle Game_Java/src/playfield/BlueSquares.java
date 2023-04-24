package playfield;
import java.awt.Color;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JDialog;


/*
 * to create blue squares for specific function
 */
public class BlueSquares extends JButton {

	protected String trueAnswer;
	// receive the true answer of this square from the input file
	protected JButton site;
	// to store the location of each individual square
	
	BlueSquares() {
	}
	public BlueSquares(String trueAnswer) {
		
		this.setBackground(Color.cyan);
		this.setTrueAnswer(trueAnswer);

		this.addActionListener(new AbstractAction(){
			public void actionPerformed(ActionEvent e) {
				site = ((JButton)e.getSource());
				JDialog kb = new KeyBoard(site, "Enter a character", trueAnswer);
			}
		});
	
	}
	public String getTrueAnswer() {
		return trueAnswer;
	}
	public void setTrueAnswer(String trueAnswer) {
		this.trueAnswer = trueAnswer;
	}
	public JButton getSite() {
		return site;
	}
	public void setSite(JButton site) {
		this.site = site;
	}




}


