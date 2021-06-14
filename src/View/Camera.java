package View;

import algorithms.mazeGenerators.Position;

public class Camera {
    private MazeCanvasDisplay mazeCanvasDisplay;
    private Position playerPosition;
    private double scale;
    private double x = 0, y = 0;
    private double freeX = 0, freeY = 0;
    private boolean freeCamera = false;


    public Camera(MazeCanvasDisplay mazeCanvasDisplay) {
        this.mazeCanvasDisplay = mazeCanvasDisplay;
    }

    public void updateCamera(Position playerPosition, double scale) {
        this.playerPosition = playerPosition;
        this.scale = scale;
        double windowHeight = this.mazeCanvasDisplay.getHeight();
        double windowWidth = this.mazeCanvasDisplay.getWidth();
        try {
//            if (playerPosition.getColumnIndex() * scale > windowWidth / 2)
//                this.x = -playerPosition.getColumnIndex() * scale + windowWidth / 2;
//            else
//                this.x = 1*scale;
//            if (playerPosition.getRowIndex() * scale > windowHeight / 2)
//                this.y = -playerPosition.getRowIndex() * scale + windowHeight / 2;
//            else
//                this.y = 1*scale;
            if (this.freeCamera) {
                this.x = freeX;
                this.y = freeY;
            } else {
                this.x = (-playerPosition.getColumnIndex() * scale + windowWidth / 2);
                this.y = (-playerPosition.getRowIndex() * scale + windowHeight / 2);
            }
        } catch (Exception e) {
            System.out.println("No Player Found.....");
            this.x = 0;
            this.y = 0;
        }
    }

    public void moveFreeCamera(double offsetX, double offsetY) {
        if (this.freeCamera) {
            this.freeX += offsetX * this.scale;
            this.freeY += offsetY * this.scale;
            this.x = freeX;
            this.y = freeY;
        } else {
            this.freeCamera = true;
            this.freeX = x + offsetX;
            this.freeY = y + offsetY;
        }
    }

    public void toggleFreeCamera() {
        if (this.freeCamera) {
            this.freeCamera = false;
            this.x = (-playerPosition.getColumnIndex() * scale + this.mazeCanvasDisplay.getWidth() / 2);
            this.y = (-playerPosition.getRowIndex() * scale + this.mazeCanvasDisplay.getHeight() / 2);
        } else {
            this.freeCamera = true;
            this.freeX = this.x;
            this.freeY = this.y;
        }
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
