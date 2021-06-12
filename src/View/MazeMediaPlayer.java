package View;

import javafx.scene.media.AudioClip;

public class MazeMediaPlayer {


    private static MazeMediaPlayer mediaPlayer = null;

    private AudioClip backgroundAudio, startAudio, wallAudio, solutionAudio, propertiesAudio;
    private boolean mute = false;
    private double fxVolume = 0.1;
    private double backVolume = 1.0;

    private MazeMediaPlayer() {
        this.loadSound();
    }

    public static MazeMediaPlayer getInstance() {
        if (mediaPlayer == null) {
            mediaPlayer = new MazeMediaPlayer();
        }

        return mediaPlayer;
    }

    private void loadSound() {
        String path;
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
            case BackGround->playBack();
            case Start -> playThis(this.startAudio);
            case WallHit -> playThis(this.wallAudio);
            case Solution -> playThis(this.solutionAudio);
            case Properties -> playThis(this.propertiesAudio);
        }
    }

    private void playThis(AudioClip audioClip) {
        if (audioClip != null && !this.mute) {
            audioClip.setVolume(fxVolume);
            audioClip.play(fxVolume);
        }
    }
    private void playBack() {
        if (this.backgroundAudio != null && !this.mute) {
            this.backgroundAudio.setVolume(backVolume);
            this.backgroundAudio.play(fxVolume);
        }
    }

    public boolean isMute() {
        return mute;
    }

    public void setMute(boolean mute) {
        this.mute = mute;
    }

    public double getFxVolume() {
        return fxVolume;
    }

    public void setFxVolume(double fxVolume) {
        if (0 <= fxVolume && fxVolume <= 1)
            this.fxVolume = fxVolume;
    }

    public enum MazeSound {
        BackGround,
        Start,
        WallHit,
        Solution,
        Properties
    }
}
