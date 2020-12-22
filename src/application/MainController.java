package application;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.ResourceBundle;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

import javazoom.jl.decoder.*;
import javazoom.jl.player.Player;

public class MainController implements Initializable{

	FileInputStream FIS;
	BufferedInputStream BIS;

	Player sinduPlayer;

	private long pauseLocation;
	private long songLength;
	private String location;

	private boolean onResume;
	private boolean onPlay = false;
	
	private Long currentLocation;

	private void stop() {
		if (sinduPlayer != null) {
			sinduPlayer.close();
			onResume = false;
			onPlay = false;
			musicLabel.setText("");
		}
	}

	private void pause() {
		if (sinduPlayer != null) {
			try {
				pauseLocation = FIS.available();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			onResume = true;
			;
			sinduPlayer.close();
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
			sliderEndValue.setText(songLength + "");
			sinduPlayer = new Player(BIS);
		} catch (JavaLayerException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		new Thread() {
			@Override
			public void run() {
				try {
					if (!onPlay) {
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

	private void selectSong() {
		FileChooser chooser = new FileChooser();
		chooser.getExtensionFilters().addAll(new ExtensionFilter("MP3 Files", "*.mp3"));
		File selectedFile = chooser.showOpenDialog(null);
		if (selectedFile != null) {
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
	private Label musicLabel, sliderStartValue, sliderEndValue;
	
	@FXML
	private Slider slider;

	public void playBtnAction(ActionEvent event) {
	}

	public void stopMouseRelease() {
		stop();
	}

	public void playMouseRelease() {
		if (onResume)
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

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		slider.setValue(10.0);
		slider.valueProperty().addListener(new ChangeListener<Number>() {
			
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				sliderStartValue.textProperty().setValue(
                        String.valueOf(newValue.intValue()));
				
			}
		});

	}
}
