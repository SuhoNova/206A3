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
	
	public void setWordAndVoice(String word, String voice, boolean rehear){
		_word = word;
		_voice = voice;
		_rehear = rehear;
	}
	
	
	@Override
	protected Void doInBackground() throws Exception {
		
		if(!_rehear&&!_word.equalsIgnoreCase("correct")&&!_word.equalsIgnoreCase("incorrect")&&!_word.equalsIgnoreCase("Incorrect, try once more")){
			Thread.sleep(1000);
			if(!_word.equals("Please spell the word: ")){
				Thread.sleep(1200);
			}
		}
		
		Runtime rt = Runtime.getRuntime();
		Process process = rt.exec("festival --pipe");
		OutputStream output = process.getOutputStream();
		
		output.write(("(voice_"+_voice+")n").getBytes());
		output.flush();
		output.write(("(SayText \""+_word+"\")n").getBytes());
		output.flush();
		//output.write("(exit)n".getBytes());
		output.close();
		//_process = pb.start();
		process.waitFor();
		//process.destroy();
		return null;
	}
	@Override
	protected void done(){
		System.out.println("done");
	}

}
