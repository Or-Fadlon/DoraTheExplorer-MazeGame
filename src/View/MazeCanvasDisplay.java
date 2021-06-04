package View;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;
import algorithms.search.AState;
import algorithms.search.Solution;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;

public class MazeCanvasDisplay extends Canvas {
    private final Color playerColor = Color.CYAN;
    private final Color wallColor = Color.RED;
    private final Color goalColor = Color.GREEN;
    private final Color solutionColor = Color.GOLD;
    // wall and player path images:
    private Image playerImage, wallImage, solutionImage, goalImage;
    private AudioClip startAudio, wallAudio, goalAudio;
    private Maze maze;
    private Solution solution = null;
    private Position playerPosition;


    public MazeCanvasDisplay() {
        this.LoadRes();
        this.requestFocus();
    }


    public void LoadRes() {
        this.LoadRes("Mario");
    }

    public void LoadRes(String theme) {
        LoadImages(theme);
//        LoadSound(theme);//TODO: AUDIO
    }

    private void LoadImages(String theme) {
        String prefix = "./resources/Themes/" + theme + "/Images/";

        try {
            this.playerImage = new Image(new FileInputStream(prefix + "player.png"));
        } catch (FileNotFoundException e) {
            System.out.println("There is no " + theme + " player image file");
        }

        try {
            this.wallImage = new Image(new FileInputStream(prefix + "wall.png"));
        } catch (FileNotFoundException e) {
            System.out.println("There is no " + theme + " wall image file");
        }

        try {
            this.solutionImage = new Image(new FileInputStream(prefix + "solution.png"));
        } catch (FileNotFoundException e) {
            System.out.println("There is no " + theme + " solution image file");
        }

        try {
            this.goalImage = new Image(new FileInputStream(prefix + "goal.png"));
        } catch (FileNotFoundException e) {
            System.out.println("There is no " + theme + " goal image file");
        }
    }

    private void LoadSound(String theme) {
        String path = getClass().getResource("/Themes/" + theme + "/Sound/test.mp3").toString();
        this.startAudio = new AudioClip(path);
        path = getClass().getResource("/Themes/" + theme + "/Sound/hitWall.wav").toString();
        this.wallAudio = new AudioClip(path);
        path = getClass().getResource("/Themes/" + theme + "/Sound/goal.wav").toString();
        this.goalAudio = new AudioClip(path);

    }

    private void draw() {
        if (maze != null) {
            double canvasHeight = getHeight();
            double canvasWidth = getWidth();

            double cellHeight = canvasHeight / this.maze.getRowsSize();
            double cellWidth = canvasWidth / this.maze.getColumnsSize();
            cellHeight = Math.min(Math.min(cellHeight, cellWidth),100);
            cellWidth = Math.min(cellHeight, cellWidth);
            GraphicsContext graphicsContext = getGraphicsContext2D();
            //clear the canvas:
            graphicsContext.clearRect(0, 0, canvasWidth, canvasHeight);

            this.drawMazeWalls(graphicsContext, cellHeight, cellWidth);
            this.drawSolution(graphicsContext, cellHeight, cellWidth);
            this.drawGoal(graphicsContext, cellHeight, cellWidth);
            this.drawPlayer(graphicsContext, cellHeight, cellWidth);
        }
    }

    private void drawMazeWalls(GraphicsContext graphicsContext, double cellHeight, double cellWidth) {
        graphicsContext.setFill(this.wallColor);

        for (int i = 0; i < this.maze.getRowsSize(); i++) {
            for (int j = 0; j < this.maze.getColumnsSize(); j++) {
                if (this.maze.positionOfWall(new Position(i, j))) {
                    //if it is a wall:
                    double x = j * cellWidth;
                    double y = i * cellHeight;
                    if (this.wallImage == null)
                        graphicsContext.fillRect(x, y, cellWidth, cellHeight);
                    else
                        graphicsContext.drawImage(this.wallImage, x, y, cellWidth, cellHeight);
                }
            }
        }
    }

    private void drawGoal(GraphicsContext graphicsContext, double cellHeight, double cellWidth) {
        graphicsContext.setFill(this.goalColor);

        Position goalPosition = this.maze.getGoalPosition();
        double x = goalPosition.getColumnIndex() * cellWidth;
        double y = goalPosition.getRowIndex() * cellHeight;

        if (this.goalImage == null)
            graphicsContext.fillRect(x, y, cellWidth, cellHeight);
        else
            graphicsContext.drawImage(this.goalImage, x, y, cellWidth, cellHeight);
    }

    private void drawPlayer(GraphicsContext graphicsContext, double cellHeight, double cellWidth) {
        graphicsContext.setFill(this.playerColor);

        double x = getPlayerCol() * cellWidth;
        double y = getPlayerRow() * cellHeight;

        if (this.playerImage == null)
            graphicsContext.fillRect(x, y, cellWidth, cellHeight);
        else
            graphicsContext.drawImage(this.playerImage, x, y, cellWidth, cellHeight);
    }

    private void drawSolution(GraphicsContext graphicsContext, double cellHeight, double cellWidth) {
        if (this.solution == null)
            return;

        graphicsContext.setFill(this.solutionColor);

        HashSet<Position> pathHashMap = solutionToPositionsHashSet();

        Position goalPosition = this.maze.getGoalPosition();
        Position tempPosition = null;
        for (int i = 0; i < this.maze.getRowsSize(); i++) {
            for (int j = 0; j < this.maze.getColumnsSize(); j++) {
                tempPosition = new Position(i, j);
                if (pathHashMap.contains(tempPosition) && !tempPosition.equals(goalPosition)) {
                    //if it is a wall:
                    double x = j * cellWidth;
                    double y = i * cellHeight;
                    if (this.solutionImage == null)
                        graphicsContext.fillRect(x, y, cellWidth, cellHeight);
                    else
                        graphicsContext.drawImage(this.solutionImage, x, y, cellWidth, cellHeight);
                }
            }
        }
    }

    private HashSet<Position> solutionToPositionsHashSet() {
        HashSet<Position> pathHashMap = new HashSet<>();
        ArrayList<AState> list = this.solution.getSolutionPath();
        for (int i = 0; i < list.size(); i++)
            pathHashMap.add((Position) list.get(i).getCurrentState());
        return pathHashMap;
    }

    public void resizeHandle(){
        draw();
    }

    public void drawNewMaze(Maze maze, Position playerPosition) {
        this.maze = maze;
        this.playerPosition = playerPosition;
        this.draw();
//        this.startAudio.play(); //TODO: AUDIO
    }

    public int getPlayerRow() {
        return this.playerPosition.getRowIndex();
    }

    public int getPlayerCol() {
        return this.playerPosition.getColumnIndex();
    }

    public void setPlayerPosition(Position position) {
        this.playerPosition = position;
        draw();
    }

    public void setSolution(Solution solution) {
        this.solution = solution;
        draw();
    }

    public void finish() {
//        if (this.goalAudio != null)
//            this.goalAudio.play();//TODO: AUDIO
    }

    public void wallHit() {
//        if (this.wallAudio != null)
//            this.wallAudio.play();//TODO: AUDIO
    }

}
