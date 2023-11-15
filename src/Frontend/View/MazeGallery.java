package Frontend.View;

import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Random;

public class MazeGallery {
    private static MazeGallery mazeGallery;
    private Theme theme;
    private Image playerUp, playerRight, playerDown, playerLeft, follower, wall, solution, goal, out;

    private MazeGallery() {
        this.theme = Theme.Dora;
        this.loadImages(this.theme);
    }

    public static MazeGallery getInstance() {
        if (mazeGallery == null)
            mazeGallery = new MazeGallery();
        return mazeGallery;
    }

    private void loadImages(Theme theme) {
        String prefix = "./resources/Images/";
        try {
            this.playerUp = new Image(new FileInputStream(prefix + "Player/" + theme.toString() + "/up.png"));
        } catch (FileNotFoundException e) {
            System.out.println("image not found");
        }
        try {
            this.playerRight = new Image(new FileInputStream(prefix + "Player/" + theme.toString() + "/right.png"));
        } catch (FileNotFoundException e) {
            System.out.println("image not found");
        }
        try {
            this.playerDown = new Image(new FileInputStream(prefix + "Player/" + theme.toString() + "/down.png"));
        } catch (FileNotFoundException e) {
            System.out.println("image not found");
        }
        try {
            this.playerLeft = new Image(new FileInputStream(prefix + "Player/" + theme.toString() + "/left.png"));
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
        try {
            this.out = new Image(new FileInputStream(prefix + "Wall/out.png"));
        } catch (FileNotFoundException e) {
            System.out.println("image not found");
        }
    }

    public void reloadTheme(Theme theme){
        if (this.theme == theme)
            return;
        this.theme = theme;
        String prefix = "./resources/Images/";
        try {
            this.playerUp = new Image(new FileInputStream(prefix + "Player/" + theme.toString() + "/up.png"));
        } catch (FileNotFoundException e) {
            System.out.println("image not found");
        }
        try {
            this.playerRight = new Image(new FileInputStream(prefix + "Player/" + theme.toString() + "/right.png"));
        } catch (FileNotFoundException e) {
            System.out.println("image not found");
        }
        try {
            this.playerDown = new Image(new FileInputStream(prefix + "Player/" + theme.toString() + "/down.png"));
        } catch (FileNotFoundException e) {
            System.out.println("image not found");
        }
        try {
            this.playerLeft = new Image(new FileInputStream(prefix + "Player/" + theme.toString() + "/left.png"));
        } catch (FileNotFoundException e) {
            System.out.println("image not found");
        }
        try {
            this.follower = new Image(new FileInputStream(prefix + "Player/" + theme.toString() + "/follower.png"));
        } catch (FileNotFoundException e) {
            System.out.println("image not found");
        }
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
