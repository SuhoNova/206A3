package spellingAid;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.swing.SwingWorker;

public class VideoRewardFfmpeg extends SwingWorker<Void,Void> {
	String _filename;
	VideoReward _vr;
	String _option;
	String _path;
	public VideoRewardFfmpeg(String filename, VideoReward vr, String modOption, String path){
		_option = modOption;
		_filename = filename;
		_path = path;
		_vr = vr;
	}
	@Override
	protected Void doInBackground() throws Exception {
		String command = "";
		if((new File(_path+".out.avi")).exists()){
			command = "rm out.avi;ffmpeg -i "+_filename+" -vf "+_option+" "+_path+".out.avi";
		}else{
			command = "ffmpeg -i "+_filename+" -vf "+_option+" "+_path+".out.avi";
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
		_vr.setFilename("out.avi");
		_vr.addMedia();
	}
	
}
