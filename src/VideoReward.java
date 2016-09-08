import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;

import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

public class VideoReward extends JFrame implements ActionListener{
	private String _filename;
	
	private final EmbeddedMediaPlayerComponent _mediaPlayerComponent;
	private final EmbeddedMediaPlayer _video;
	 
	private JButton _stopButton;
	private JButton _pauseButton;
	private JButton _fastforward;
	private JButton _rewind;
	private JPanel _mainPanel;
	private JPanel _panel;
	
	
	public VideoReward(String filename){	
		super("Video Reward");		
		setLibUp();

		_mediaPlayerComponent = new EmbeddedMediaPlayerComponent();
		_video = _mediaPlayerComponent.getMediaPlayer();

		setAddButtonsToPanel();
		setActionListenerToButtons();
        
        setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		setSize(640,480);	
		setLocation(100,100);
       
		/**
		 * CHANGE THIS TO GET VIDEO YOU WANT
		 */
		//_filename = filename;
		_filename = "big_buck_bunny_1_minute.avi";
		_video.playMedia(_filename);
	}
	
	public void actionPerformed(ActionEvent event) {
		if(event.getSource() == _stopButton){
			_video.stop();
			this.dispose();
		} else if (event.getSource() == _pauseButton){
			_video.pause();
			if(_pauseButton.getText().equals("Pause")){
				_pauseButton.setText("Play");
			} else if(_pauseButton.getText().equals("Play")){
				_pauseButton.setText("Pause");
			}
		} else if(event.getSource() == _rewind){
			_video.skip(-5000);
		} else if(event.getSource() == _fastforward){
			_video.skip(5000);
		}
	}
	
	public void setLibUp(){
		NativeLibrary.addSearchPath(
	            RuntimeUtil.getLibVlcLibraryName(), "/Applications/vlc-2.0.0/VLC.app/Contents/MacOS/lib"
	    );
		Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(), LibVlc.class);
	}
	
	public void setAddButtonsToPanel(){
		_mainPanel = new JPanel(new BorderLayout());
		_panel = new JPanel(new GridLayout(1,4));
		
		_pauseButton = new JButton("Pause");
		_stopButton = new JButton("Exit Video");
		_fastforward = new JButton("Jump Forward");
		_rewind = new JButton("Jump Backward");
		
		_panel.add(_rewind);
		_panel.add(_pauseButton);
		_panel.add(_fastforward);
		_panel.add(_stopButton);
		
		setContentPane(_mainPanel);
		
		_mainPanel.add(_mediaPlayerComponent, BorderLayout.CENTER);
		_mainPanel.add(_panel, BorderLayout.SOUTH);
	}
	public void setActionListenerToButtons(){
		_stopButton.addActionListener(this);
        _pauseButton.addActionListener(this);
        _fastforward.addActionListener(this);
        _rewind.addActionListener(this);
	}
	
}
