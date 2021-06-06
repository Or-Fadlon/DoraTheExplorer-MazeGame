package View;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;
import algorithms.search.AState;
import algorithms.search.Solution;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;

public class MazeCanvasDisplay extends Canvas {
    private final Color playerColor = Color.CYAN;
    private final Color wallColor = Color.RED;
    private final Color goalColor = Color.GREEN;
    private final Color solutionColor = Color.GOLD;
    public double zoom = 1;
    // wall and player path images:
    private Image playerImage, wallImage, solutionImage, goalImage;
    private AudioClip startAudio, wallAudio, goalAudio;
    private Maze maze;
    private Solution solution = null;
    private Position playerPosition;
    private double scale;
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
        LoadSound(theme);
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
        String path;
        try {
            path = getClass().getResource("/Themes/" + theme + "/Sound/start.wav").toString();
            this.startAudio = new AudioClip(path);
        } catch (Exception e) {
            System.out.println("Start sound not found");
        }
        try {
            path = getClass().getResource("/Themes/" + theme + "/Sound/hitWall.wav").toString();
            this.wallAudio = new AudioClip(path);
        } catch (Exception e) {
            System.out.println("Start sound not found");
        }
        try {
            path = getClass().getResource("/Themes/" + theme + "/Sound/goal.wav").toString();
            this.goalAudio = new AudioClip(path);

        } catch (Exception e) {
            System.out.println("Start sound not found");
        }

    }

    private void draw() {
        if (maze != null) {
            GraphicsContext graphicsContext = getGraphicsContext2D();
            //////////////////////////////////////////
            //background color
            graphicsContext.setFill(Color.BEIGE);
            graphicsContext.fillRect(0, 0, this.getWidth(), this.getHeight());

            graphicsContext.translate(camera.getX(), camera.getY());

            this.drawMazeWalls(graphicsContext, this.scale);
            this.drawSolution(graphicsContext, this.scale);
            this.drawGoal(graphicsContext, this.scale);
            this.drawPlayer(graphicsContext, this.scale);
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
        this.updateScale();
        this.updateCamera();
        this.draw();
    }

    private void updateScale() {
        double minSize = Math.min(this.getHeight() / this.maze.getRowsSize(), this.getWidth() / this.maze.getColumnsSize());
        if (minSize <= 20)
            this.scale = 20 * this.zoom;
        else if (minSize >= 100)
            this.scale = 100 * this.zoom;
        else
            this.scale = minSize * this.zoom;
    }

    public void drawNewMaze(Maze maze, Position playerPosition) {
        this.maze = maze;
        this.updateScale();
        this.setPlayerPosition(playerPosition);
        this.draw();
        if (this.startAudio != null)
            this.startAudio.play();
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
        this.draw();
    }

    private void updateCamera() {
        if (this.camera == null)
            this.camera = new Camera(this);
        this.camera.updateCamera(this.playerPosition, this.scale);
    }

    public void setSolution(Solution solution) {
        this.solution = solution;
        this.draw();
    }

    public void finish() {
        if (this.goalAudio != null)
            this.goalAudio.play();
    }

    public void wallHit() {
        if (this.wallAudio != null)
            this.wallAudio.play();
    }

    public double getScale() {
        return this.scale;
    }
}
