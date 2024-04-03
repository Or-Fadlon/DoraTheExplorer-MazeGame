package Frontend.View;

import javafx.scene.image.Image;

import java.io.InputStream;
import java.util.Random;

public class MazeGallery {
    private static MazeGallery mazeGallery;
    private Theme theme;
    private Image playerUp, playerRight, playerDown, playerLeft, follower, wall, solution, goal, out;

    private MazeGallery() {
        this.theme = Theme.Dora;
        this.loadImages();
    }

    public static MazeGallery getInstance() {
        if (mazeGallery == null)
            mazeGallery = new MazeGallery();
        return mazeGallery;
    }

    public void loadTheme() {
        Theme theme = this.theme;
        String prefix = "/resources/Images/";
        InputStream stream = getClass().getResourceAsStream(prefix + "Player/" + theme.toString() + "/up.png");
        if (stream == null) System.out.println("image not found");
        else this.playerUp = new Image(stream);
        stream = getClass().getResourceAsStream(prefix + "Player/" + theme.toString() + "/right.png");
        if (stream == null) System.out.println("image not found");
        else this.playerRight = new Image(stream);
        stream = getClass().getResourceAsStream(prefix + "Player/" + theme.toString() + "/down.png");
        if (stream == null) System.out.println("image not found");
        else this.playerDown = new Image(stream);
        stream = getClass().getResourceAsStream(prefix + "Player/" + theme.toString() + "/left.png");
        if (stream == null) System.out.println("image not found");
        else this.playerLeft = new Image(stream);
        stream = getClass().getResourceAsStream(prefix + "Player/" + theme.toString() + "/follower.png");
        if (stream == null) System.out.println("image not found");
        else this.follower = new Image(stream);

    }

    private void loadImages() {
        String prefix = "/resources/Images/";
        this.loadTheme();
        InputStream stream = getClass().getResourceAsStream(prefix + "Wall/wall.png");
        if (stream == null) System.out.println("image not found");
        else this.wall = new Image(stream);
        stream = getClass().getResourceAsStream(prefix + "Solution/solution.png");
        if (stream == null) System.out.println("image not found");
        else this.solution = new Image(stream);
        Random rand = new Random();
        stream = getClass().getResourceAsStream(prefix + "Goal/goal" + rand.nextInt(4) + ".png");
        if (stream == null) System.out.println("image not found");
        else this.goal = new Image(stream);
        stream = getClass().getResourceAsStream(prefix + "Wall/out.png");
        if (stream == null) System.out.println("image not found");
        else this.out = new Image(stream);
    }

    public void reloadTheme(Theme theme) {
        if (this.theme == theme) return;
        this.theme = theme;
        this.loadTheme();
    }

    public Image getImage(MazeImage image) {
        Image value = null;
        switch (image) {
            case PlayerUp -> value = this.playerUp;
            case PlayerRight -> value = this.playerRight;
            case PlayerDown -> value = this.playerDown;
            case PlayerLeft -> value = this.playerLeft;
            case Follower -> value = this.follower;
            case Wall -> value = this.wall;
            case Solution -> value = this.solution;
            case Goal -> value = this.goal;
            case Out -> value = this.out;

        }
        return value;
    }


    public enum MazeImage {
        PlayerUp,
        PlayerRight,
        PlayerDown,
        PlayerLeft,
        Follower,
        Wall,
        Solution,
        Goal,
        Out
    }

    public enum Theme {
        Dora,
        Diego
    }
}
