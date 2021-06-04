package View;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;
import algorithms.search.AState;
import algorithms.search.Solution;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.ResourceBundle;

public class MazeCanvasDisplay extends Canvas implements Initializable {
    private final Color playerColor = Color.CYAN;
    private final Color wallColor = Color.RED;
    private final Color goalColor = Color.GREEN;
    private final Color solutionColor = Color.WHITE;
    // wall and player path images:
    private String playerImage = "./resources/Images/Mario/player.png";
    private String wallImage = "./resources/Images/Mario/wall.png";
    private String goalImage = "./resources/Images/Mario/goal.png";
    private Maze maze;
    private Solution solution = null;
    private Position playerPosition;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.requestFocus();
    }

    private void draw() {
        if (maze != null) {
            double canvasHeight = getHeight();
            double canvasWidth = getWidth();

            double cellHeight = canvasHeight / this.maze.getRowsSize();
            double cellWidth = canvasWidth / this.maze.getColumnsSize();

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

        Image wallImage = null;
        try {
            wallImage = new Image(new FileInputStream(this.wallImage));
        } catch (FileNotFoundException e) {
            System.out.println("There is no wall image file");
        }

        for (int i = 0; i < this.maze.getRowsSize(); i++) {
            for (int j = 0; j < this.maze.getColumnsSize(); j++) {
                if (maze.positionOfWall(new Position(i, j))) {
                    //if it is a wall:
                    double x = j * cellWidth;
                    double y = i * cellHeight;
                    if (wallImage == null)
                        graphicsContext.fillRect(x, y, cellWidth, cellHeight);
                    else
                        graphicsContext.drawImage(wallImage, x, y, cellWidth, cellHeight);
                }
            }
        }
    }

    private void drawGoal(GraphicsContext graphicsContext, double cellHeight, double cellWidth) {
        graphicsContext.setFill(this.goalColor);

        Position goalPosition = this.maze.getGoalPosition();
        double x = goalPosition.getColumnIndex() * cellWidth;
        double y = goalPosition.getRowIndex() * cellHeight;

        Image goalImage = null;
        try {
            goalImage = new Image(new FileInputStream(this.goalImage));
        } catch (FileNotFoundException e) {
            System.out.println("There is no goal image file");
        }
        if (goalImage == null)
            graphicsContext.fillRect(x, y, cellWidth, cellHeight);
        else
            graphicsContext.drawImage(goalImage, x, y, cellWidth, cellHeight);
    }

    private void drawPlayer(GraphicsContext graphicsContext, double cellHeight, double cellWidth) {
        graphicsContext.setFill(this.playerColor);

        double x = getPlayerCol() * cellWidth;
        double y = getPlayerRow() * cellHeight;

        Image playerImage = null;
        try {
            playerImage = new Image(new FileInputStream(this.playerImage));
        } catch (FileNotFoundException e) {
            System.out.println("There is no player image file");
        }
        if (playerImage == null)
            graphicsContext.fillRect(x, y, cellWidth, cellHeight);
        else
            graphicsContext.drawImage(playerImage, x, y, cellWidth, cellHeight);
    }

    private void drawSolution(GraphicsContext graphicsContext, double cellHeight, double cellWidth) {
        if (this.solution == null)
            return;

        graphicsContext.setFill(this.solutionColor);

        Image wallImage = null;
        //TODO: Solution image
//        try {
//            wallImage = new Image(new FileInputStream(this.wallImage));
//        } catch (FileNotFoundException e) {
//            System.out.println("There is no wall image file");
//        }
        HashSet<Position> pathHashMap = solutionToPositionsHashSet();
        for (int i = 0; i < this.maze.getRowsSize(); i++) {
            for (int j = 0; j < this.maze.getColumnsSize(); j++) {
                if (pathHashMap.contains(new Position(i, j))) {
                    //if it is a wall:
                    double x = j * cellWidth;
                    double y = i * cellHeight;
                    if (wallImage == null)
                        graphicsContext.fillRect(x, y, cellWidth, cellHeight);
                    else
                        graphicsContext.drawImage(wallImage, x, y, cellWidth, cellHeight);
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

    public void drawNewMaze(Maze maze, Position playerPosition) {
        this.maze = maze;
        this.playerPosition = playerPosition;
        this.draw();
    }

    public void playerMoved(Position newPosition) {
        this.playerPosition = newPosition;
        draw();
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
    }

}
