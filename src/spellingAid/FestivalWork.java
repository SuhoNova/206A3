package spellingAid;

import java.io.PrintWriter;

import javax.swing.SwingWorker;

public class FestivalWork extends SwingWorker<Void,Void> {
	String _word;
	String _voice;
	Process _process;
	
	public FestivalWork(){
	}
	
	public void setWordAndVoice(String word, String voice){
		_word = word;
		_voice = voice;
	}
	
	@Override
	protected Void doInBackground() throws Exception {
		
		//PrintWriter writer = new PrintWriter("sayText.scm", "UTF-8");
		//writer.println("(utt.save.wave (SayText \"" + _word + "\") \"name.wav\" 'riff)");
		//writer.println("(voice_" + _voice + ")");
		//writer.println("(SayText \"" + _word + "\")");
		PrintWriter writer = new PrintWriter("word.txt", "UTF-8");
		writer.println(_word);
		writer.close();
		
		//String command = "festival -b sayText.scm; rm -rf sayText.scm;exit"; 
		String command = "text2wave -o word.wav word.txt; play word.wav";
		ProcessBuilder pb = new ProcessBuilder("bash", "-c", command);
		_process = pb.start();
		_process.waitFor();
		_process.destroy();
		return null;
	}
	@Override
	protected void done(){
		System.out.println("done");
	}

}
