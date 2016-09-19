package spellingAid;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.swing.SwingWorker;

public class FestivalWork extends SwingWorker<Void,Void> {
	String _word;
	String _voice;
	boolean _rehear;
	
	public FestivalWork(){
	}
	/**
	 * Sets word, voice, and rehear option
	 * @param word
	 * @param voice
	 * @param rehear
	 */
	public void setWordAndVoice(String word, String voice, boolean rehear){
		_word = word;
		_voice = voice;
		_rehear = rehear;
	}
	
	/**
	 * If rehear is true, thread does not sleep at all
	 * If it is correct or incorrect or incorrect, try once more thread does not sleep
	 * If it is Please spell the word it sleeps only 1000ms
	 * if it is anything else it sleeps 2200ms 
	 */
	@Override
	protected Void doInBackground() throws Exception {
		
		if(!_rehear&&!_word.equalsIgnoreCase("correct")&&!_word.equalsIgnoreCase("incorrect")&&!_word.equalsIgnoreCase("Incorrect, try once more")){
			Thread.sleep(1000);
			if(!_word.equals("Please spell the word: ")){
				Thread.sleep(1200);
			}
		}
		// pipes into festival
		Runtime rt = Runtime.getRuntime();
		Process process = rt.exec("festival --pipe");
		OutputStream output = process.getOutputStream();
		
		output.write(("(voice_"+_voice+")n").getBytes());
		output.flush();
		output.write(("(SayText \""+_word+"\")n").getBytes());
		output.flush();
		output.close();
		// waits until the process is terminated
		process.waitFor();
		return null;
	}
}
