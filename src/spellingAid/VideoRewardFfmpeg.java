package spellingAid;

import java.io.File;
import java.io.IOException;

import javax.swing.SwingWorker;

public class VideoRewardFfmpeg extends SwingWorker<Void,Void> {
	String _filename;
	VideoReward _vr;
	String _option;
	String _path;
	String _outputVideo;
	public VideoRewardFfmpeg(String filename, VideoReward vr, String modOption, String path){
		_option = modOption;
		_filename = filename;
		_path = path;
		_vr = vr;
		_outputVideo=_path+".out.avi";
	}
	@Override
	protected Void doInBackground() throws Exception {
		String command = "";
		if((new File(_path+".out.avi")).exists()){
			command = "rm "+_outputVideo+";ffmpeg -i "+_filename+" -vf "+_option+" "+_outputVideo;
		}else{
			command = "ffmpeg -i "+_filename+" -vf "+_option+" "+_outputVideo;
		}
		ProcessBuilder pb = new ProcessBuilder("bash", "-c", command);
		try {
			Process process = pb.start();
			process.waitFor();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	@Override
	protected void done(){
		_vr.setFilename(_outputVideo);
		_vr.addMedia();
	}
	
}
