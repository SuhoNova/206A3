package spellingAid;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.SwingWorker;

public class VideoRewardFfmpeg extends SwingWorker<Void,Void> {
	String _filename;
	VideoReward _vr;
	String _option;
	String _outputVideo;
	public VideoRewardFfmpeg(String filename, VideoReward vr, String modOption, String path){
		_option = modOption;
		_filename = filename;
		_vr = vr;
		_outputVideo=path+".out.avi";
	}
	@Override
	protected Void doInBackground() throws Exception {
		String command = "";
		System.out.println((new File(_outputVideo)).exists());
		if((new File(_outputVideo)).exists()){
			command = "rm "+_outputVideo+";ffmpeg -i "+_filename+" -vf "+_option+" "+_outputVideo;
		}else{
			command = "ffmpeg -i "+_filename+" -vf "+_option+" "+_outputVideo;
		}
		ProcessBuilder pb = new ProcessBuilder("/bin/bash", "-c", command);
		Process process = pb.start();
		process.waitFor();
		
		return null;
	}
	@Override
	protected void done(){
		_vr.setFilename(_outputVideo);
		_vr.addMedia();
	}
	
}
