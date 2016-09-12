package spellingAid;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JFrame;

import viewAndController.SpellingAidController;
import viewAndController.SpellingAidView;

public class SpellingAid {
	public static void main(String[] args){
		//create necessary files on startup
		PrintWriter outputFile;
		try{
			outputFile = new PrintWriter(new FileWriter(".mastered", true));
			outputFile.close();
			outputFile = new PrintWriter(new FileWriter(".faulted", true));
			outputFile.close();
			outputFile = new PrintWriter(new FileWriter(".failed", true));
			outputFile.close();
			outputFile = new PrintWriter(new FileWriter(".stats", true));
			outputFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		SpellingAidView view = new SpellingAidView();
		SpellingAidModel model = new SpellingAidModel();
		SpellingAidController controller = new SpellingAidController(view, model); 
	}
	
}
