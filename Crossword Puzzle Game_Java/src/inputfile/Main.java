package inputfile;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import layout.Layout;
/*
 * this is the entry of the whole program, read the property file first, store the solution of the
 * puzzle and then read puzzle-setting file by knowing the name of it.
 */
public class Main {

	public static void main(String[] args) throws FileNotFoundException {

		String filename = null;
		// store the filename that supposed to read after reading the property file
		String finalSolution = null;
		// store the final solution of the puzzle after reading the property file

		Properties prop = new Properties();
		// create an instance of Properties named prop
		InputStream input = null;
		// define a InputStream type variable named input
		try {
			input = new FileInputStream("InputFile\\config.properties");
			// load a properties file
			prop.load(input);
			// get the property value and print it out
			filename = prop.getProperty("filename");
			finalSolution = prop.getProperty("solution");
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		ReadFile readFile = new ReadFile("InputFile\\" + filename);
		// create an instance of ReadFile named readFile

		FileContent fileContent = new FileContent(readFile.getAllFileContent(),readFile.getLineNumber());
		// create an instance of FileContent named fileContent

		JFrame layout = new Layout(fileContent.getX(), fileContent.getY(), fileContent.getInstructions(), fileContent.getSquareNames(), finalSolution);
		// create an instance of Layout named layout

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
			}
		});
	}

}
