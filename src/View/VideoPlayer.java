package View;

import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class VideoPlayer extends AView implements Initializable {
    private static MazeVideo video = MazeVideo.Opening;
    public MediaView mediaView;

    private MediaPlayer mediaPlayer;

    public static void setVideo(MazeVideo other) {
        if (video != null)
            video = other;
    }

    public void setMediaView(MazeVideo video) {
        String videoPath = null;
        switch (video) {
            case Opening -> videoPath = "resources/Video/opening.mp4";
            case Finish -> videoPath = "resources/Video/finish.mp4";
        }
        Media media = new Media(new File(videoPath).toURI().toString());
        this.mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setOnEndOfMedia(() -> StageGenerator.changeMainScene("../View/MyView.fxml"));
        this.mediaView.setMediaPlayer(mediaPlayer);
        mediaPlayer.setAutoPlay(true);

    }

    public void mouseClicked(MouseEvent mouseEvent) {
        this.mediaPlayer.stop();
        StageGenerator.changeMainScene("../View/MyView.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.setMediaView(video);
    }

    public enum MazeVideo {
        Opening,
        Finish
    }
}
