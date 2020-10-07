package application;

import java.io.File;
import java.net.URL;
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

public class MainController {
	@FXML 
	private Button openBtn;
	
	@FXML
	private Button playBtn;
	
	@FXML
	private ListView listView;
	
	private MediaPlayer mp;
	private Media me;
	
//	private List<File> selectedFiles;
	private File selectedFiles;
	
	public void openBtnAction(ActionEvent event) {
		FileChooser fc = new FileChooser();
		fc.getExtensionFilters().addAll(
				new ExtensionFilter("MP3 files", "*.mp3"));
//		selectedFiles = fc.showOpenMultipleDialog(null);
		selectedFiles = fc.showOpenDialog(null);
		
		if(selectedFiles != null) {
//			for(int i = 0; i<selectedFiles.size(); i++)
//			listView.getItems().add(selectedFiles.get(i).getName());
			listView.getItems().add(selectedFiles.getName());
		}
	}
	
	public void playBtnAction(ActionEvent event) {	
		//for(int i = 0; i<selectedFiles.size(); i++) {
		//String bip = selectedFiles.get(i).getAbsolutePath();
		String bip = selectedFiles.getAbsolutePath();
		Media hit = new Media(new File(bip).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(hit);
        mediaPlayer.play(); 
        //}
	}

}
