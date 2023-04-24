package inputfile;

/*
 * decomposition of the file content, divide it into 3 parts
 */

import java.util.ArrayList;
import java.util.List;


public class FileContent {

	private String allFileContent;
	private int lineNumber;
	private List<String> readfile;
	private int x;
	private int y;
	private String[] squareNames;
	private String[] instructions;

	FileContent(){
	}

	FileContent(String allFileContent, int lineNumber){
		this.setAllFileContent(allFileContent);
		// receive all the file content after reading input file
		this.setLineNumber(lineNumber);
		// receive the number of lines of input file 

		readfile = new ArrayList<String>();
		for (String retval:allFileContent.split("\n")){
			readfile.add(retval);
		}

		// put each line of input file into a arraylist named readfile

		String r1 = readfile.get(0);
		// r1 refers to the first row
		String[] coordinate = r1.split(" ");

		x = Integer.parseInt(coordinate[0]);
		y = Integer.parseInt(coordinate[1]);

		// get the first line at arraylist to get the dimension of the coordinates of puzzle


		List<String> s = new ArrayList<String>();
		for(int i = 1; i<= x; i ++) {
			s.add(readfile.get(i));
		}
		// collect all the construct information of the squares and put them in the array list s

		List<String> sn = new ArrayList<String>();
		for(int i = 0; i< y; i ++) {
			for (String retval:s.get(i).split(" ")){
				sn.add(retval);
			}
		}

		// split each sign and put each of them into a new array list named sn

		squareNames = new String [sn.size()];
		for (int i = 0; i < sn.size(); i++) {
			squareNames[i] = sn.get(i);
		}

		// transfer the arraylist to array named squareNames

		List<String> is = new ArrayList<String>();
		for(int i = x+1; i< lineNumber; i ++) {
			is.add(readfile.get(i));
		}

		// put the instructions information into the arraylist is
		instructions = new String [is.size()];
		for (int i = 0; i < is.size(); i++) {
			instructions[i] = is.get(i);
		}

		// transfer the arraylist to array via "for loop"

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

	public List<String> getReadfile() {
		return readfile;
	}

	public void setReadfile(List<String> readfile) {
		this.readfile = readfile;
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

	public String[] getSquareNames() {
		return squareNames;
	}

	public void setSquareNames(String[] squareNames) {
		this.squareNames = squareNames;
	}

	public String[] getInstructions() {
		return instructions;
	}

	public void setInstructions(String[] instructions) {
		this.instructions = instructions;
	}



}


