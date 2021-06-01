package View;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MazeDisplayer extends Canvas {
    // wall and player images:
    StringProperty imageFileNamePlayer = new SimpleStringProperty();
    StringProperty imageFileNameWall = new SimpleStringProperty();
    private Maze maze;
    // player position:
    private Position playerPosition;

    public MazeDisplayer(Maze maze) {
        this.maze = maze;
        this.playerPosition = maze.getStartPosition();
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

    public String getImageFileNameWall() {
        return imageFileNameWall.get();
    }

    public void setImageFileNameWall(String imageFileNameWall) {
        this.imageFileNameWall.set(imageFileNameWall);
    }

    public String imageFileNameWallProperty() {
        return imageFileNameWall.get();
    }

    public String getImageFileNamePlayer() {
        return imageFileNamePlayer.get();
    }

    public void setImageFileNamePlayer(String imageFileNamePlayer) {
        this.imageFileNamePlayer.set(imageFileNamePlayer);
    }

    public String imageFileNamePlayerProperty() {
        return imageFileNamePlayer.get();
    }

    public void drawMaze(Maze maze) {
        this.maze = maze;
        draw();
    }

    private void draw() {
        if (maze != null) {
            double canvasHeight = getHeight();
            double canvasWidth = getWidth();
            int rows = maze.getRowsSize();
            int cols = maze.getColumnsSize();

            double cellHeight = canvasHeight / rows;
            double cellWidth = canvasWidth / cols;

            GraphicsContext graphicsContext = getGraphicsContext2D();
            //clear the canvas:
            graphicsContext.clearRect(0, 0, canvasWidth, canvasHeight);

            drawMazeWalls(graphicsContext, cellHeight, cellWidth, rows, cols);
            drawPlayer(graphicsContext, cellHeight, cellWidth);
        }
    }

    private void drawMazeWalls(GraphicsContext graphicsContext, double cellHeight, double cellWidth, int rows, int cols) {
        graphicsContext.setFill(Color.RED);

        Image wallImage = null;
        try {
            wallImage = new Image(new FileInputStream(getImageFileNameWall()));
        } catch (FileNotFoundException e) {
            System.out.println("There is no wall image file");
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (maze.getGrid()[i][j] == 1) {
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

    private void drawPlayer(GraphicsContext graphicsContext, double cellHeight, double cellWidth) {
        double x = getPlayerCol() * cellWidth;
        double y = getPlayerRow() * cellHeight;
        graphicsContext.setFill(Color.GREEN);

        Image playerImage = null;
        try {
            playerImage = new Image(new FileInputStream(getImageFileNamePlayer()));
        } catch (FileNotFoundException e) {
            System.out.println("There is no player image file");
        }
        if (playerImage == null)
            graphicsContext.fillRect(x, y, cellWidth, cellHeight);
        else
            graphicsContext.drawImage(playerImage, x, y, cellWidth, cellHeight);
    }
}
