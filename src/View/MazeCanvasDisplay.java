package View;

import Model.MovementDirection;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;
import algorithms.search.AState;
import algorithms.search.Solution;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.HashSet;

public class MazeCanvasDisplay extends Canvas {
    public double zoom = 1;
    // wall and player path images:
    private MazeGallery mazeGallery;
    private MazeMediaPlayer mazeMediaPlayer;
    private MovementDirection playerDirection = MovementDirection.DOWN;
    private MovementDirection followerDirection;
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
        this.LoadRes(MazeGallery.Theme.Dora);
    }

    public void LoadRes(MazeGallery.Theme theme) {
        LoadImages(theme);
        LoadSound();
    }

    private void LoadImages(MazeGallery.Theme theme) {
        this.mazeGallery = MazeGallery.getInstance(theme);
    }

    private void LoadSound() {
        this.mazeMediaPlayer = MazeMediaPlayer.getInstance();
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
        Image wallImage = mazeGallery.getImage(MazeGallery.MazeImage.Wall);
        Color wallColor = Color.RED;

        int rowSize = this.maze.getRowsSize();
        int colSize = this.maze.getColumnsSize();
        for (int i = -1; i <= rowSize; i++) {
            for (int j = -1; j <= colSize; j++) {
                if (this.maze.positionOfWall(new Position(i, j)) || i == -1 || i == rowSize || j == -1 || j == colSize) {
                    //if it is a wall:
                    double x = j * cellSize;
                    double y = i * cellSize;
                    if (wallImage == null) {
                        graphicsContext.setFill(wallColor);
                        graphicsContext.fillRect(x, y, cellSize, cellSize);
                    } else
                        graphicsContext.drawImage(wallImage, x, y, cellSize, cellSize);
                }
            }
        }
    }

    private void drawGoal(GraphicsContext graphicsContext, double cellSize) {
        Image goalImage = mazeGallery.getImage(MazeGallery.MazeImage.Goal);
        Color goalColor = Color.GREEN;

        Position goalPosition = this.maze.getGoalPosition();
        double x = goalPosition.getColumnIndex() * cellSize;
        double y = goalPosition.getRowIndex() * cellSize;

        if (goalImage == null) {
            graphicsContext.setFill(goalColor);
            graphicsContext.fillRect(x, y, cellSize, cellSize);
        } else
            graphicsContext.drawImage(goalImage, x, y, cellSize, cellSize);
    }

    private void drawPlayer(GraphicsContext graphicsContext, double cellSize) {
        Image playerImage = null;
        Color playerColor = Color.CYAN;

        switch (this.playerDirection) {
            case UP -> playerImage = mazeGallery.getImage(MazeGallery.MazeImage.PlayerUp);
            case RIGHT, UP_RIGHT, DOWN_RIGHT -> playerImage = mazeGallery.getImage(MazeGallery.MazeImage.PlayerRight);
            case DOWN -> playerImage = mazeGallery.getImage(MazeGallery.MazeImage.PlayerDown);
            case LEFT, UP_LEFT, DOWN_LEFT -> playerImage = mazeGallery.getImage(MazeGallery.MazeImage.PlayerLeft);
        }

        double x = getPlayerCol() * cellSize;
        double y = getPlayerRow() * cellSize;

        if (playerImage == null) {
            graphicsContext.setFill(playerColor);
            graphicsContext.fillRect(x, y, cellSize, cellSize);
        } else
            graphicsContext.drawImage(playerImage, x, y, cellSize, cellSize);
    }

    private void drawSolution(GraphicsContext graphicsContext, double cellSize) {
        if (this.solution == null)
            return;

        HashSet<Position> pathHashMap = solutionToPositionsHashSet();
        Image solutionImage = mazeGallery.getImage(MazeGallery.MazeImage.Solution);
        Color solutionColor = Color.GOLD;

        Position goalPosition = this.maze.getGoalPosition();
        Position tempPosition = null;
        for (int i = 0; i < this.maze.getRowsSize(); i++) {
            for (int j = 0; j < this.maze.getColumnsSize(); j++) {
                tempPosition = new Position(i, j);
                if (pathHashMap.contains(tempPosition) && !tempPosition.equals(goalPosition)) {
                    //if it is a wall:
                    double x = j * cellSize;
                    double y = i * cellSize;
                    if (solutionImage == null) {
                        graphicsContext.setFill(solutionColor);
                        graphicsContext.fillRect(x, y, cellSize, cellSize);
                    } else
                        graphicsContext.drawImage(solutionImage, x, y, cellSize, cellSize);

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
        this.mazeMediaPlayer.play(MazeMediaPlayer.MazeSound.Start);
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

    public void setPlayerDirection(MovementDirection direction) {
        if (direction != null)
            this.playerDirection = direction;
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

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Finish!");
        alert.show();
    }

    public void wallHit() {
        this.mazeMediaPlayer.play(MazeMediaPlayer.MazeSound.WallHit);
    }

    public double getScale() {
        return this.scale;
    }
}
