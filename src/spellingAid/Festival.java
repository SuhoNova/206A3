package spellingAid;

public class Festival{
	private String _voice;
	private boolean _apostropheExist = false;
	private boolean _rehear = false;
	//private FestivalWork _fw;

	public Festival(String voice) {
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
		changeVoice(voice);
		//_fw = new FestivalWork();
	}

	public void speakWord(String word) {
		_apostropheExist = doesApostropheExist(word);
		if(_apostropheExist){
			word =  word + " The one with an apostrophe.";
		} else {
			word = word;
		}
		speakIt(word);
	}
	public void speakWord(String word, boolean rehear){
		_rehear = rehear;
		speakWord(word);
	}
	public void speakLetter(String word) {
		String temp = "";
		for(int i = 0; i < word.length(); i++){
			if(word.charAt(i) == '\''){
				temp += "apostrophe ";
			} else {
				temp += word.charAt(i) + " ";
			}
		}
		_rehear = true;
		word = temp;
		speakIt(word);
	}

	public void speakIt(String word) {
		FestivalWork fw = new FestivalWork();
		fw.setWordAndVoice(word, _voice,_rehear);
		fw.execute();
		_rehear = false;
	}
	
	public boolean doesApostropheExist(String word){
		if(word.contains("'")){
			return true;
		}
		return false;
	}
	public void changeVoice(String voice){
		if(voice.equalsIgnoreCase("american")){
			_voice = "kal_diphone";
		}
		else if(voice.equalsIgnoreCase("new zealander")){
			_voice = "akl_nz_jdt_diphone";
		}
		else if(voice.equalsIgnoreCase("british")){
			_voice = "rab_diphone";
		}
		else{
			_voice = "kal_diphone";
		}
	}
	public String getVoice(){
		return _voice;
	}

}
