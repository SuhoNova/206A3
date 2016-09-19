package spellingAid;

public class Festival{
	private String _voice;
	private boolean _apostropheExist = false;
	private boolean _rehear = false;

	public Festival(String voice) {
		changeVoice(voice);
	}
	/**
	 * Checks if apostrophe exists, and alerts the user that 
	 * this word contains an apostrophe if it does
	 * @param word
	 */
	public void speakWord(String word) {
		_apostropheExist = doesApostropheExist(word);
		if(_apostropheExist){
			word =  word + ". With an apostrophe.";
		} else {
			word = word;
		}
		speakIt(word);
	}
	/**
	 * speakWord method with rehear parameter
	 * @param word
	 * @param rehear
	 */
	public void speakWord(String word, boolean rehear){
		_rehear = rehear;
		speakWord(word);
	}
	/**
	 * Festival speaks out individual letter
	 * @param word
	 */
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
	/**
	 * Passes the word onto festival worker without any modification
	 * @param word
	 */
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
	/**
	 * Change the wanted voice into what festival understands
	 * @param voice
	 */
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
