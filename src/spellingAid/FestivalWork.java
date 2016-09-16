package spellingAid;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.swing.SwingWorker;

public class FestivalWork extends SwingWorker<Void,Void> {
	String _word;
	String _voice;
	
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
		//PrintWriter writer = new PrintWriter("word.txt", "UTF-8");
		//writer.println(_word);
		//writer.close();
		
		//String command = "festival -b sayText.scm; rm -rf sayText.scm;exit"; 
		//String command = "text2wave -o word.wav word.txt; play word.wav";
		//String command = "festival --pipe;(SayText \""+_word+"\");(exit)";
		//ProcessBuilder pb = new ProcessBuilder("bash", "-c", command);
		if(!_word.equalsIgnoreCase("correct")&&!_word.equalsIgnoreCase("incorrect")&&!_word.equalsIgnoreCase("Incorrect, try once more")){
			Thread.sleep(1500);
		}
		
		Runtime rt = Runtime.getRuntime();
		Process process = rt.exec("festival --pipe");
		OutputStream output = process.getOutputStream();
		
		output.write(("(voice"+_voice+")n").getBytes());
		output.flush();
		output.write(("(SayText \""+_word+"\")n").getBytes());
		output.flush();
		output.write("(exit)n".getBytes());
		output.close();
		//_process = pb.start();
		//process.waitFor();
		//_process.destroy();
		return null;
	}
	@Override
	protected void done(){
		System.out.println("done");
	}

}
