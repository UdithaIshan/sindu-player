package application;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilterInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.filechooser.FileNameExtensionFilter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.util.Duration;

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
	
	private void stop() {
		if(sinduPlayer != null) {
			sinduPlayer.close();
			onResume = false;
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
					sinduPlayer.play();
				} catch (JavaLayerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}.start();
	}
	
	
	@FXML 
	private Button openBtn;
	
	@FXML
	private Button playBtn;
	
	@FXML
	private ListView listView;
	
	private MediaPlayer mp;
	private Media me;
	
	public void playBtnAction(ActionEvent event) {	
	}
	
	public void stopMouseRelease() {
		stop();
	}
	
	public void playMouseRelease() {
		if(onResume)
			resume();
		else
			play("E:\\Music\\Dátha_Dara_-Ridma_Weerawardane__Dhanith_Sri__Methun_SK__Supun_Perera__Dinesh_Gamage__Dinupa_Kodagoda.mp3");
	}
	public void pauseMouseRelese() {
		pause();
	}
	public void openFileChooser() {
		FileChooser chooser = new FileChooser();
		chooser.getExtensionFilters().addAll(new ExtensionFilter("MP3 Files", "*.mp3"));
		File selectedFile = chooser.showOpenDialog(null);
		if(selectedFile != null)
			play(selectedFile.getAbsolutePath());
	}
}


