package View;

import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class VideoPlayer extends AView implements Initializable {
    private static MazeVideo video = MazeVideo.Opening;
    public MediaView mediaView;

    private MediaPlayer mediaPlayer;

    public void setMediaView(MazeVideo video) {
        String videoPath = null;
        switch (video) {
            case Opening -> videoPath = "resources/Video/opening.mp4";
            case Finish -> videoPath = "resources/Video/finish.mp4";
        }
        Media media = new Media(new File(videoPath).toURI().toString());
        this.mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setOnEndOfMedia(() -> this.stopVideo(null));
        this.mediaView.setMediaPlayer(mediaPlayer);
        mediaPlayer.setAutoPlay(true);

    }

    public void stopVideo(MouseEvent mouseEvent) {
        this.mediaPlayer.stop();
        StageGenerator.getInstance(StageGenerator.StageName.Video).close();
        StageGenerator.startMain();
        VideoPlayer.video = MazeVideo.Finish;
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
