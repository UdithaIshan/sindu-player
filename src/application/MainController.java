package application;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

import javazoom.jl.decoder.*;
import javazoom.jl.player.Player;

public class MainController {
	
	FileInputStream FIS;
	BufferedInputStream BIS;
	
	Player sinduPlayer;
	
	private long pauseLocation;
	private long songLength;
	private String location;
	
	private boolean onResume;
	private boolean onPlay = false;
	
	private void stop() {
		if(sinduPlayer != null) {
			sinduPlayer.close();
			onResume = false;
			onPlay = false;
			musicLabel.setText("");
		}
	}
	
	private void pause() {
		if(sinduPlayer != null) {
			try {
				pauseLocation = FIS.available();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			onResume = true;
;			sinduPlayer.close();
		}
	}
	
	private void resume() {
		try {
			FIS = new FileInputStream(location);
			FIS.skip(songLength - pauseLocation);
			BIS = new BufferedInputStream(FIS);
			sinduPlayer = new Player(BIS);
		} catch (JavaLayerException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		new Thread() {
			@Override
			public void run() {
				try {
					onPlay = true;
					sinduPlayer.play();
				} catch (JavaLayerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}.start();
	}
	
	private void play(String filePath) {
		try {
			FIS = new FileInputStream(filePath);
			BIS = new BufferedInputStream(FIS);
			location = filePath;
			songLength = FIS.available();
			sinduPlayer = new Player(BIS);
		} catch (JavaLayerException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		new Thread() {
			@Override
			public void run() {
				try {
					if(!onPlay) {
						onPlay = true;
						sinduPlayer.play();
					}		
				} catch (JavaLayerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}.start();
	}
	
	private void selectSong( ) {
		FileChooser chooser = new FileChooser();
		chooser.getExtensionFilters().addAll(new ExtensionFilter("MP3 Files", "*.mp3"));
		File selectedFile = chooser.showOpenDialog(null);
		if(selectedFile != null) {
			stop();
			musicLabel.setText(selectedFile.getName());
			play(selectedFile.getAbsolutePath());
		}
	}
	
	
	@FXML 
	private Button openBtn;
	
	@FXML
	private Button playBtn;
	
	@FXML
	private Label musicLabel;
	
	public void playBtnAction(ActionEvent event) {	
	}
	
	public void stopMouseRelease() {
		stop();
	}
	
	public void playMouseRelease() {
		if(onResume)
			resume();
		else	
			selectSong();
	}
	public void pauseMouseRelese() {
		pause();
	}
	public void openFileChooser() {
		selectSong();
	}
}


