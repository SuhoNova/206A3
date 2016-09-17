package spellingAid;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;

import javax.swing.JFrame;

import viewAndController.SpellingAidController;
import viewAndController.SpellingAidView;

public class SpellingAid {
	public static void main(String[] args){
		
		SpellingAidView view = new SpellingAidView();
		SpellingAidModel model = new SpellingAidModel();
		SpellingAidController controller = new SpellingAidController(view, model); 
		//VideoReward vr = new VideoReward();
		
	}
	
}
