package inputfile;
/*
 * read and collect information from the input file
 */

import java.io.File;  
import java.io.FileNotFoundException;
import java.util.Scanner;


public class ReadFile {

	private String filePath;
	private String allFileContent;
	private int lineNumber;

	ReadFile(){
	}

	ReadFile(String filePath) throws FileNotFoundException{
		this.setFilePath(filePath);
		// receive the relative path of the file

		File file = new File(filePath);
		// create an instance of File named file

		Scanner scan = new Scanner(file);
		// create an instance of Scanner named scan

		allFileContent = "";

		// collect the information scanned from inout file and store them in the variable allFileContent

		while(scan.hasNextLine()) {
			allFileContent = allFileContent.concat(scan.nextLine() + "\n");
			lineNumber++;
			// count the line number of the input file
		}
		scan.close();

	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getAllFileContent() {
		return allFileContent;
	}

	public void setAllFileContent(String allFileContent) {
		this.allFileContent = allFileContent;
	}

	public int getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}


}
