package View;

import javafx.scene.media.AudioClip;

public class MazeAudioPlayer {


    private static MazeAudioPlayer mediaPlayer = null;

    private AudioClip backgroundAudio, startAudio, wallAudio, solutionAudio, propertiesAudio;

    private MazeAudioPlayer() {
        this.loadSound();
    }

    public static MazeAudioPlayer getInstance() {
        if (mediaPlayer == null) {
            mediaPlayer = new MazeAudioPlayer();
        }

        return mediaPlayer;
    }

    private void loadSound() {
        String path;
        try {
            path = getClass().getResource("/Sound/background.mp3").toString();
            this.backgroundAudio = new AudioClip(path);
        } catch (Exception e) {
            System.out.println("Start sound not found");
        }
        try {
            path = getClass().getResource("/Sound/start.mp3").toString();
            this.startAudio = new AudioClip(path);
        } catch (Exception e) {
            System.out.println("Start sound not found");
        }
        try {
            path = getClass().getResource("/Sound/hitWall.mp3").toString();
            this.wallAudio = new AudioClip(path);
        } catch (Exception e) {
            System.out.println("hitWall sound not found");
        }
        try {
            path = getClass().getResource("/Sound/solution.mp3").toString();
            this.solutionAudio = new AudioClip(path);

        } catch (Exception e) {
            System.out.println("solution sound not found");
        }
        try {
            path = getClass().getResource("/Sound/properties.mp3").toString();
            this.propertiesAudio = new AudioClip(path);

        } catch (Exception e) {
            System.out.println("properties sound not found");
        }
    }

    public void play(MazeSound soundName) {
        switch (soundName) {
            case BackGround -> playBack(this.backgroundAudio);
            case Start -> playThis(this.startAudio);
            case WallHit -> playThis(this.wallAudio);
            case Solution -> playBack(this.solutionAudio);
            case Properties -> playBack(this.propertiesAudio);
        }
    }

    private void playThis(AudioClip audioClip) {
        if (audioClip != null && !PlayerConfig.getInstance().isMute()) {
            audioClip.setVolume(PlayerConfig.getInstance().getFxVolume());
            audioClip.play();
        }
    }

    private void playBack(AudioClip audioClip) {
        this.stopAll();
        if (audioClip != null && !PlayerConfig.getInstance().isMute()) {
            audioClip.setVolume(PlayerConfig.getInstance().getBackVolume());
            audioClip.play();
        }
    }

    public void stopAll() {
        if (backgroundAudio != null)
            backgroundAudio.stop();
        if (startAudio != null)
            startAudio.stop();
        if (wallAudio != null)
            wallAudio.stop();
        if (solutionAudio != null)
            solutionAudio.stop();
        if (propertiesAudio != null)
            propertiesAudio.stop();
    }

    public enum MazeSound {
        BackGround,
        Start,
        WallHit,
        Solution,
        Properties
    }
}
