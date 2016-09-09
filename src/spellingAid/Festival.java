package spellingAid;

public class Festival {
	private String _voice;
	private boolean _apostropheExist = false;
	private FestivalWork _fw;
	
	public Festival(String voice){
		// CHANGE to voice FOR VOICE
		/*
		 * voice options i found in ue4
		 * cmu_us_rms_arctic
		 * cmu_us_slt_arctic
		 * cmu_us_bdl_arctic
		 * cmu_us_clb_arctic
		 * kal_diphone
		 * akl_nz_jdt_diphone
		 * rab_diphone
		 * 
		 */
		_voice = "kal_diphone";
		_fw = new FestivalWork();
	}
	
	public void speakWord(String word) throws Exception{
		_apostropheExist = doesApostropheExist(word);
		if(_apostropheExist){
			word = "How do you spell " + word + "? The one with an apostrophe.";
		} else {
			word = "How do you spell " + word + "? The one without an apostrophe.";
		}
		_fw.callBackground(word, _voice);
	}
	public void speakLetter(String word) throws Exception {
		String temp = "";
		for(int i = 0; i < word.length(); i++){
			if(word.charAt(i) == '\''){
				temp += "apostrophe ...... ";
			} else {
				temp += word.charAt(i) + " ...... ";
			}
		}
		_fw.callBackground(word, _voice);
	}
	
	public void speakIt(String word) throws Exception{
		_fw.callBackground(word, _voice);
	}
	
	public boolean doesApostropheExist(String word){
		if(word.contains("'")){
			return true;
		}
		return false;
	}
	public void changeVoice(String voice){
		_voice = voice;
	}
	public String getVoice(){
		return _voice;
	}
}