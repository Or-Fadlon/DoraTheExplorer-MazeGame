package View;

import algorithms.mazeGenerators.Position;

public class Camera {
    //TODO: add move camera option??
    private MazeCanvasDisplay mazeCanvasDisplay;
    private double x, y;


    public Camera(MazeCanvasDisplay mazeCanvasDisplay) {
        this.mazeCanvasDisplay = mazeCanvasDisplay;
        this.x = 0;
        this.y = 0;
    }

    public void updateCamera(Position playerPosition, double scale) {
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
            this.x = -playerPosition.getColumnIndex() * scale + windowWidth / 2;
            this.y = -playerPosition.getRowIndex() * scale + windowHeight / 2;
        } catch (Exception e) {
            System.out.println("No Player Found.....");
            this.x = 0;
            this.y = 0;
        }
        //System.out.format("cameraX:{%s} cameraY:{%s} \n", this.x, this.y); //TODO:REMOVE
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
