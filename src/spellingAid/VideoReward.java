package spellingAid;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

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
	 
	private JPanel _mainPanel;
	private JPanel _panel;
	private JPanel _timePanel;
	
	private JButton _stopButton;
	private JButton _pauseButton;
	private JButton _jumpForward;
	private JButton _jumpBackward;
	private JButton _mute;
	private JLabel _label;
	private Timer _timer;
	
	
	private long _videoLength;
	private int _timeSkip = 5000;
	
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
		_timer.start();
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
		} else if(event.getSource() == _jumpBackward){
			_video.skip(-_timeSkip);
		} else if(event.getSource() == _jumpForward){
			_video.skip(_timeSkip);
		} else if (event.getSource() == _mute){
			_video.mute();
			if(_pauseButton.getText().equals("Mute")){
				_pauseButton.setText("UnMute");
			} else if(_pauseButton.getText().equals("Unmute")){
				_pauseButton.setText("Mute");
			}
		} else if (event.getSource() == _timer){
			long timeSeconds = (long)(_video.getTime()/1000.0);
			_videoLength = (long)(_video.getLength()/1000.0);
			_label.setText("" + timeSeconds + " / " + _videoLength + " seconds");
			if(timeSeconds == _videoLength){
				_video.stop();
				this.dispose();
			}
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
		_timePanel = new JPanel(new GridLayout(1,2));
		
		_pauseButton = new JButton("Pause");
		_stopButton = new JButton("Exit Video");
		_jumpForward = new JButton("Jump Forward");
		_jumpBackward = new JButton("Jump Backward");
		_mute = new JButton("Mute");
		_timer = new Timer(1000, this);
		_label = new JLabel("0");
		
		_panel.add(_jumpBackward);
		_panel.add(_pauseButton);
		_panel.add(_jumpForward);
		_panel.add(_mute);
		_panel.add(_stopButton);
		
		_timePanel.add(_label);
		
		setContentPane(_mainPanel);
		
		_mainPanel.add(_mediaPlayerComponent, BorderLayout.CENTER);
		_mainPanel.add(_timePanel, BorderLayout.NORTH);
		_mainPanel.add(_panel, BorderLayout.SOUTH);
	}
	public void setActionListenerToButtons(){
		_stopButton.addActionListener(this);
        _pauseButton.addActionListener(this);
        _jumpForward.addActionListener(this);
        _jumpBackward.addActionListener(this);
        _mute.addActionListener(this);
	}
	
}
