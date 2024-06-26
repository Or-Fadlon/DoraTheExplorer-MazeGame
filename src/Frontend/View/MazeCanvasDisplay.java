package Frontend.View;

import Backend.algorithms.mazeGenerators.Maze;
import Backend.algorithms.mazeGenerators.Position;
import Backend.algorithms.search.AState;
import Backend.algorithms.search.Solution;
import Frontend.Model.MovementDirection;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.HashSet;

public class MazeCanvasDisplay extends Canvas {
    public double zoom = 1;
    private MazeGallery mazeGallery;
    private MazeAudioPlayer mazeAudioPlayer;
    private MovementDirection playerDirection = MovementDirection.DOWN;
    private Maze maze;
    private Solution solution = null;
    private Position playerPosition;
    private Position followerPosition;
    private double scale;
    private Camera camera;


    public MazeCanvasDisplay() {
        this.LoadRes();
        this.requestFocus();
    }


    public void LoadRes() {
        LoadImages();
        LoadSound();
    }

    private void LoadImages() {
        this.mazeGallery = MazeGallery.getInstance();
    }

    private void LoadSound() {
        this.mazeAudioPlayer = MazeAudioPlayer.getInstance();
    }

    private void draw() {
        if (maze != null) {
            GraphicsContext graphicsContext = getGraphicsContext2D();
            //////////////////////////////////////////
            //background color
            graphicsContext.clearRect(0, 0, this.getWidth(), this.getHeight());

            this.drawBack(graphicsContext, this.scale);

            graphicsContext.translate(camera.getX(), camera.getY());
            this.drawMazeWalls(graphicsContext, this.scale);
            this.drawSolution(graphicsContext, this.scale);
            this.drawGoal(graphicsContext, this.scale);
            this.drawFollower(graphicsContext, this.scale);
            this.drawPlayer(graphicsContext, this.scale);
            graphicsContext.translate(-camera.getX(), -camera.getY());
            /////////////////////////////////////////
        }
    }

    private void drawBack(GraphicsContext graphicsContext, double cellSize) {
        Image outImage = mazeGallery.getImage(MazeGallery.MazeImage.Out);
        Color outColor = Color.BEIGE;

        for (int i = 0; i <= this.getHeight() / cellSize; i++) {
            for (int j = 0; j <= this.getWidth() / cellSize; j++) {
                double x = j * cellSize;
                double y = i * cellSize;
                if (outImage == null) {
                    graphicsContext.setFill(outColor);
                    graphicsContext.fillRect(x, y, cellSize, cellSize);
                } else
                    graphicsContext.drawImage(outImage, x, y, cellSize, cellSize);
            }
        }
    }

    private void drawMazeWalls(GraphicsContext graphicsContext, double cellSize) {
        Image wallImage = mazeGallery.getImage(MazeGallery.MazeImage.Wall);
        Color wallColor = Color.RED;

        int rowSize = this.maze.getRowsSize();
        int colSize = this.maze.getColumnsSize();

        graphicsContext.setFill(Color.BEIGE);
        graphicsContext.fillRect(0, 0, rowSize * cellSize, colSize * cellSize);

        for (int i = -1; i <= rowSize; i++) {
            for (int j = -1; j <= colSize; j++) {
                Position tempPosition = new Position(i, j);
                double x = j * cellSize;
                double y = i * cellSize;
                if (this.maze.positionOfWall(tempPosition) || i == -1 || i == rowSize || j == -1 || j == colSize) {
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

    private void drawFollower(GraphicsContext graphicsContext, double cellSize) {
        if (followerPosition == null)
            return;
        Image followerImage = mazeGallery.getImage(MazeGallery.MazeImage.Follower);
        Color playerColor = Color.BLUE;


        double x = this.followerPosition.getColumnIndex() * cellSize;
        double y = this.followerPosition.getRowIndex() * cellSize;

        if (followerImage == null) {
            graphicsContext.setFill(playerColor);
            graphicsContext.fillRect(x, y, cellSize, cellSize);
        } else
            graphicsContext.drawImage(followerImage, x, y, cellSize, cellSize);
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

        double x = this.playerPosition.getColumnIndex() * cellSize;
        double y = this.playerPosition.getRowIndex() * cellSize;

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
        this.solution = null;
        this.maze = maze;
        this.updateScale();
        this.setPlayerPosition(playerPosition);
        this.followerPosition = null;
        this.draw();
        this.mazeAudioPlayer.play(MazeAudioPlayer.MazeSound.BackGround);
        this.mazeAudioPlayer.play(MazeAudioPlayer.MazeSound.Start);
    }

    public void setPlayerPosition(Position position) {
        this.followerPosition = this.playerPosition == null ? null : new Position(this.playerPosition);
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

    public void moveFreeCamera(double diffX, double diffY, double scale) {
        if (-diffY >= scale)
            this.camera.moveFreeCamera(0, -1);
        if (diffX >= scale)
            this.camera.moveFreeCamera(1, 0);
        if (diffY >= scale)
            this.camera.moveFreeCamera(0, 1);
        if (-diffX >= scale)
            this.camera.moveFreeCamera(-1, 0);
        this.draw();
    }

    public void toggleFreeCamera() {
        this.camera.toggleFreeCamera();
        this.draw();
    }

    public void setSolution(Solution solution) {
        this.solution = solution;
        this.draw();
    }

    public void finish() {
        this.mazeAudioPlayer.stopAll();
        StageGenerator.getInstance(StageGenerator.StageName.Main).close();
        StageGenerator.startVideo();
    }

    public void wallHit() {
        this.mazeAudioPlayer.play(MazeAudioPlayer.MazeSound.WallHit);
    }

    public double getScale() {
        return this.scale;
    }
}
