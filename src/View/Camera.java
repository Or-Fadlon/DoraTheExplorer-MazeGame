package View;

import algorithms.mazeGenerators.Position;

public class Camera {

    Position playerPosition;
    private double x, y;
    public double rowSize, colSize;

    public Camera(double rowSize, double colSize,Position playerPosition) {
        this.rowSize = rowSize;
        this.colSize = colSize;
        this.playerPosition = playerPosition;
        this.x = 0;
        this.y = 0;
    }

    public void updateCamera() {
        try {
            if (playerPosition.getColumnIndex() > this.colSize / 2)
                this.x = -playerPosition.getColumnIndex() + this.rowSize / 2;
            else
                this.x = 0;
            if (playerPosition.getRowIndex() > 3 * this.rowSize / 4)
                this.y = -playerPosition.getRowIndex() + 3 * this.rowSize / 4;
            else
                this.y = 0;
        } catch (Exception e) {
            System.out.println("No Player Found.....");
            this.x = 0;
            this.y = 0;
        }
    }

    public double getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}
