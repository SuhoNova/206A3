package spellingAid;

import java.io.PrintWriter;

import javax.swing.SwingWorker;

public class FestivalWork extends SwingWorker<Void,Void> {
	String _word;
	String _voice;
	
	public FestivalWork(){
	}
	
	public void callBackground(String word, String voice){
		_word = word;
		_voice = voice;
		try {
			this.doInBackground();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected Void doInBackground() throws Exception {
		
		PrintWriter writer = new PrintWriter("sayText.scm", "UTF-8");
		writer.println("(voice_" + _voice + ")");
		writer.println("(SayText \"" + _word + "\")");
		writer.close();
		
		String command = "festival -b sayText.scm; rm -rf sayText.scm"; 
		ProcessBuilder pb = new ProcessBuilder("bash", "-c", command);
		Process process = pb.start();
		
		
		return null;
	}

}
