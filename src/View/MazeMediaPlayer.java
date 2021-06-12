package View;

import javafx.scene.media.AudioClip;

public class MazeMediaPlayer {


    private static MazeMediaPlayer mediaPlayer = null;

    private AudioClip startAudio, wallAudio, goalAudio;
    private boolean mute = false;
    private double fxVolume = 0.03;

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
            path = getClass().getResource("/Themes/Mario/Sound/start.wav").toString();
            this.startAudio = new AudioClip(path);
        } catch (Exception e) {
            System.out.println("Start sound not found");
        }
        try {
            path = getClass().getResource("/Themes/Mario/Sound/hitWall.wav").toString();
            this.wallAudio = new AudioClip(path);
        } catch (Exception e) {
            System.out.println("hitWall sound not found");
        }
        try {
            path = getClass().getResource("/Themes/Mario/Sound/goal.wav").toString();
            this.goalAudio = new AudioClip(path);

        } catch (Exception e) {
            System.out.println("goal sound not found");
        }
    }

    public void play(MazeSound soundName) {
        switch (soundName) {
            case Start -> playThis(this.startAudio);
            case WallHit -> playThis(this.wallAudio);
            case Goal -> playThis(this.goalAudio);
        }
    }

    private void playThis(AudioClip audioClip) {
        if (audioClip != null && !this.mute) {
            audioClip.setVolume(fxVolume);
            audioClip.play(fxVolume);
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
        Goal
    }
}
