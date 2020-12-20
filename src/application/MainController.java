package application;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FilterInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

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
	
	FilterInputStream FIS;
	BufferedInputStream BIS;
	
	Player sinduPlayer;
	
	private void stop() {
		if(sinduPlayer != null) {
			sinduPlayer.close();
		}
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
}


