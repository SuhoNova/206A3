import java.io.PrintWriter;

import javax.swing.SwingWorker;

public class FestivalWork extends SwingWorker<Void,Void> {
	String _sentence;
	String _voice;
	
	public FestivalWork(String sentence, String voice){
		_sentence = sentence;
		_voice = voice;
	}
	@Override
	protected Void doInBackground() throws Exception {
		
		PrintWriter writer = new PrintWriter("sayText.scm", "UTF-8");
		writer.println("(voice_" + _voice + ")");
		writer.println("(SayText \"" + _sentence + "\")");
		writer.close();
		
		String command = "festival -b sayText.scm; rm -rf sayText.scm"; 
		ProcessBuilder pb = new ProcessBuilder("bash", "-c", command);
		Process process = pb.start();
		
		return null;
	}

}
