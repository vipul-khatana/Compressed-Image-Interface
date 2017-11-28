/* This function helps us to read and write a .txt file */

import java.io.IOException; 
import java.io.FileReader; 
import java.io.BufferedReader; 

public class ReadFile{
	private String path; 
	public ReadFile (String file_path){
		path = file_path;
	}

	public int readLines() throws IOException{
		FileReader file_to_read = new FileReader(path);
		BufferedReader bf = new BufferedReader(file_to_read);
		String aLine; 
		int no_of_lines = 0;

		while((aLine = bf.readLine()) != null){
			no_of_lines++;
		}

		bf.close();
		return no_of_lines;
	}

	public String[] OpenFile() throws IOException{
		FileReader fr = new FileReader(path);
		BufferedReader textReader = new BufferedReader (fr);
		int no_of_lines = readLines();
		String[] textData = new String[no_of_lines];

		for(int i=0; i<no_of_lines;i++){
			textData[i] =textReader.readLine();
		}
		textReader.close();
		return textData;
	}
}
