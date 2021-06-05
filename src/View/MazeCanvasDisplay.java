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
    private Camera camera;


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

            double cellSize = Math.min(canvasHeight / this.maze.getRowsSize(), canvasWidth / this.maze.getColumnsSize());
            cellSize = Math.min(cellSize, 100);
            GraphicsContext graphicsContext = getGraphicsContext2D();

            //////////////////////////////////////////
            graphicsContext.setFill(Color.BEIGE);
            graphicsContext.fillRect(0, 0, this.getWidth(), this.getHeight());

            graphicsContext.translate(camera.getX(), camera.getY());
            //clear the canvas:

            //TODO: avoid smooth background
            this.drawMazeWalls(graphicsContext, cellSize);
            this.drawSolution(graphicsContext, cellSize);
            this.drawGoal(graphicsContext, cellSize);
            this.drawPlayer(graphicsContext, cellSize);
            graphicsContext.translate(-camera.getX(), -camera.getY());
            /////////////////////////////////////////
        }
    }

    private void drawMazeWalls(GraphicsContext graphicsContext, double cellSize) {

        int rowSize = this.maze.getRowsSize();
        int colSize = this.maze.getColumnsSize();
        for (int i = -1; i <= rowSize; i++) {
            for (int j = -1; j <= colSize; j++) {
                if (this.maze.positionOfWall(new Position(i, j)) || i == -1 || i == rowSize || j == -1 || j == colSize) {
                    //if it is a wall:
                    double x = j * cellSize;
                    double y = i * cellSize;
                    if (this.wallImage == null)
                        graphicsContext.fillRect(x, y, cellSize, cellSize);
                    else {
                        graphicsContext.setFill(this.wallColor);
                        graphicsContext.drawImage(this.wallImage, x, y, cellSize, cellSize);
                    }
                }
            }
        }
    }

    private void drawGoal(GraphicsContext graphicsContext, double cellSize) {
        Position goalPosition = this.maze.getGoalPosition();
        double x = goalPosition.getColumnIndex() * cellSize;
        double y = goalPosition.getRowIndex() * cellSize;

        if (this.goalImage == null)
            graphicsContext.fillRect(x, y, cellSize, cellSize);
        else {
            graphicsContext.setFill(this.goalColor);
            graphicsContext.drawImage(this.goalImage, x, y, cellSize, cellSize);
        }
    }

    private void drawPlayer(GraphicsContext graphicsContext, double cellSize) {
        double x = getPlayerCol() * cellSize;
        double y = getPlayerRow() * cellSize;

        if (this.playerImage == null)
            graphicsContext.fillRect(x, y, cellSize, cellSize);
        else {
            graphicsContext.setFill(this.playerColor);
            graphicsContext.drawImage(this.playerImage, x, y, cellSize, cellSize);
        }
    }

    private void drawSolution(GraphicsContext graphicsContext, double cellSize) {
        if (this.solution == null)
            return;

        HashSet<Position> pathHashMap = solutionToPositionsHashSet();

        Position goalPosition = this.maze.getGoalPosition();
        Position tempPosition = null;
        for (int i = 0; i < this.maze.getRowsSize(); i++) {
            for (int j = 0; j < this.maze.getColumnsSize(); j++) {
                tempPosition = new Position(i, j);
                if (pathHashMap.contains(tempPosition) && !tempPosition.equals(goalPosition)) {
                    //if it is a wall:
                    double x = j * cellSize;
                    double y = i * cellSize;
                    if (this.solutionImage == null)
                        graphicsContext.fillRect(x, y, cellSize, cellSize);
                    else {
                        graphicsContext.setFill(this.solutionColor);
                        graphicsContext.drawImage(this.solutionImage, x, y, cellSize, cellSize);
                    }
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

    public void resizeHandle() {
        this.updateCamera();
        draw();
    }

    public void drawNewMaze(Maze maze, Position playerPosition) {
        this.maze = maze;
        this.setPlayerPosition(playerPosition);
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
        this.updateCamera();
        draw();
    }

    private void updateCamera() {
        if (this.camera == null)
            this.camera = new Camera(this);
        double scale = Math.min(this.getHeight() / this.maze.getRowsSize(), this.getWidth() / this.maze.getColumnsSize());
        scale = Math.min(scale, 100);
        this.camera.updateCamera(this.playerPosition, scale);
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
