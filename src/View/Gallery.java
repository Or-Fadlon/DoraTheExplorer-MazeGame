package View;

import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Random;

public class Gallery {
    private static Gallery gallery;

    private Image playerUp, playerDown, playerSide, follower, wall, solution, goal;

    private Gallery(Theme theme) {
        this.loadImages(theme);
    }

    public static Gallery getInstance(Theme theme) {
        if (gallery == null)
            gallery = new Gallery(theme);
        return gallery;
    }

    private void loadImages(Theme theme) {
        String prefix = "./resources/Images/";
        try {
            this.playerUp = new Image(new FileInputStream(prefix + "Player/" + theme.toString() + "/up.png"));
        } catch (FileNotFoundException e) {
            System.out.println("image not found");
        }
        try {
            this.playerDown = new Image(new FileInputStream(prefix + "Player/" + theme.toString() + "/down.png"));
        } catch (FileNotFoundException e) {
            System.out.println("image not found");
        }
        try {
            this.playerSide = new Image(new FileInputStream(prefix + "Player/" + theme.toString() + "/side.png"));
        } catch (FileNotFoundException e) {
            System.out.println("image not found");
        }
        try {
            this.follower = new Image(new FileInputStream(prefix + "Player/" + theme.toString() + "/follower.png"));
        } catch (FileNotFoundException e) {
            System.out.println("image not found");
        }
        try {
            this.wall = new Image(new FileInputStream(prefix + "Wall/wall.png"));
        } catch (FileNotFoundException e) {
            System.out.println("image not found");
        }
        try {
            this.solution = new Image(new FileInputStream(prefix + "Solution/solution.png"));
        } catch (FileNotFoundException e) {
            System.out.println("image not found");
        }
        Random rand = new Random();

        try {
            this.goal = new Image(new FileInputStream(prefix + "Goal/goal" + rand.nextInt(4) + ".png"));
        } catch (FileNotFoundException e) {
            System.out.println("image not found");
        }
    }

    public Image getImage(MazeImage image) {
        Image value = null;
        switch (image) {
            case PlayerUp -> value = this.playerUp;
            case PlayerDown -> value = this.playerDown;
            case PlayerSide -> value = this.playerSide;
            case Follower -> value = this.follower;
            case Wall -> value = this.wall;
            case Solution -> value = this.solution;
            case Goal -> value = this.goal;
        }
        return value;
    }


    public enum MazeImage {
        PlayerUp,
        PlayerDown,
        PlayerSide,
        Follower,
        Wall,
        Solution,
        Goal
    }

    public enum Theme {
        Dora,
        Diego
    }
}
