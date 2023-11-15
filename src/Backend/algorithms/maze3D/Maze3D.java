package Backend.algorithms.maze3D;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * Represent a 3D maze
 */
public class Maze3D {
    private static final int WALL = 1, TILE = 0;
    private int[][][] grid;
    private Position3D startPosition, goalPosition;

    /**
     * constructor
     *
     * @param depth   number of layers
     * @param rows    number of rows
     * @param columns number of columns
     * @throws IllegalArgumentException if one one or more of the arguments are < 2
     */
    public Maze3D(int depth, int rows, int columns) throws IllegalArgumentException {
        if (depth < 2 || columns < 2 || rows < 2)
            throw new IllegalArgumentException("one or more of the arguments are < 2");
        this.grid = new int[depth][rows][columns];
        this.startPosition = new Position3D(0, 0, 0);
        this.goalPosition = new Position3D(depth - 1, rows - 1, columns - 1);
    }

    /**
     * constructor
     *
     * @param grid          a grid full of WALLs and TILEs
     * @param startPosition maze start position
     * @param goalPosition  maze goal position
     */
    public Maze3D(int[][][] grid, Position3D startPosition, Position3D goalPosition) {
        this.grid = grid;
        this.startPosition = startPosition;
        this.goalPosition = goalPosition;
    }

    /**
     * @return maze start position
     */
    public Position3D getStartPosition() {
        return startPosition;
    }

    /**
     * @return maze goal position
     */
    public Position3D getGoalPosition() {
        return goalPosition;
    }

    /**
     * print a colored console view of the maze
     */
    public void printColored() {
        String ans = "";
        if (this.getDepthSize() > 25 || this.getRowsSize() > 100 || this.getColumnsSize() > 100) {
            Scanner in = new Scanner(System.in);
            System.out.println("this maze is big, are you sure you want to print it? (y/n)");
            ans = in.nextLine();
            if (!ans.equals("y") && !ans.equals("Y"))
                return;
        }
        final String RED = "\033[0;31m";
        final String GREEN = "\033[0;32m";
        final String BLACK_BACKGROUND = "\u001B[40m";
        final String WHITE_BACKGROUND = "\u001B[47m";
        final String RESET = "\033[0m";
        System.out.println("{");
        for (int i = 0; i < this.getDepthSize(); i++) {
            for (int j = 0; j < this.getRowsSize(); j++) {
                System.out.print("{");
                for (int k = 0; k < this.getColumnsSize(); k++) {
                    if (this.startPosition.equals(new Position3D(i, j, k)))
                        System.out.print(GREEN + " S" + RESET);
                    else if (this.goalPosition.equals(new Position3D(i, j, k)))
                        System.out.print(RED + " E" + RESET);
                    else if (this.grid[i][j][k] == 1)
                        System.out.print(BLACK_BACKGROUND + "  " + RESET);
                    else
                        System.out.print("  ");
                }
                System.out.println(" }");
            }
            if (this.getDepthSize() - 1 != i) {
                for (int t = 0; t < this.getColumnsSize() * 2 + 3; t++)
                    System.out.print("-");
                System.out.println();
            } else
                System.out.println("}");
        }
    }

    /**
     * print a console view of the maze
     */
    public void myPrint() {
        System.out.println("{");
        for (int depth = 0; depth < this.getDepthSize(); depth++) {
            for (int row = 0; row < this.getRowsSize(); row++) {
                System.out.print("{");
                for (int column = 0; column < this.getColumnsSize(); column++) {
                    if (this.startPosition.equals(new Position3D(depth, row, column)))
                        System.out.print(" S");
                    else if (this.goalPosition.equals(new Position3D(depth, row, column)))
                        System.out.print(" E");
                    else
                        System.out.print(" " + this.grid[depth][row][column]);
                }
                System.out.println(" }");
            }
            if (this.getDepthSize() - 1 != depth) {
                for (int t = 0; t < this.getColumnsSize() * 2 + 3; t++)
                    System.out.print("-");
                System.out.println();
            } else
                System.out.println("}");
        }
    }

    /**
     * print a console view of the maze
     */
    public void print() {
        System.out.println("{");
        for (int depth = 0; depth < grid.length; depth++) {
            for (int row = 0; row < grid[0].length; row++) {
                System.out.print("{ ");
                for (int col = 0; col < grid[0][0].length; col++) {
                    if (depth == startPosition.getDepthIndex() && row == startPosition.getRowIndex() && col == startPosition.getColumnIndex()) // if the position is the start - mark with S
                        System.out.print("S ");
                    else {
                        if (depth == goalPosition.getDepthIndex() && row == goalPosition.getRowIndex() && col == goalPosition.getColumnIndex()) // if the position is the goal - mark with E
                            System.out.print("E ");
                        else
                            System.out.print(grid[depth][row][col] + " ");
                    }
                }
                System.out.println("}");
            }
            if (depth < grid.length - 1) {
                System.out.print("---");
                for (int i = 0; i < grid[0][0].length; i++)
                    System.out.print("--");
                System.out.println();
            }
        }
        System.out.println("}");
    }


    /**
     * remove all walls from the maze
     */
    public void cleanAllWalls() {
        for (int i = 0; i < grid.length; i++)
            for (int j = 0; j < grid[0].length; j++)
                for (int k = 0; k < grid[0][0].length; k++)
                    this.grid[i][j][k] = TILE;
    }

    /**
     * add a wall in every tile of the maze
     */
    public void makeAllWalls() {
        for (int i = 0; i < grid.length; i++)
            for (int j = 0; j < grid[0].length; j++)
                for (int k = 0; k < grid[0][0].length; k++)
                    this.grid[i][j][k] = WALL;
    }

    /**
     * add a wall to the given position in the maze.
     * do nothing if the position isn't a valid position in the maze
     *
     * @param position a valid position in the maze
     */
    public void addWall(Position3D position) {
        if (this.validMazePosition(position))
            this.grid[position.getDepthIndex()][position.getRowIndex()][position.getColumnIndex()] = WALL;
    }

    /**
     * remove a wall from the given position in the maze.
     * do nothing if the position isn't a valid position in the maze
     *
     * @param position a valid position in the maze
     */
    public void removeWall(Position3D position) {
        if (this.validMazePosition(position))
            this.grid[position.getDepthIndex()][position.getRowIndex()][position.getColumnIndex()] = TILE;
    }

    /**
     * @return number of layers in the maze
     */
    public int getDepthSize() {
        return this.grid.length;
    }

    /**
     * @return number of rows in the maze
     */
    public int getRowsSize() {
        return this.grid[0].length;
    }

    /**
     * @return number of columns in the maze
     */
    public int getColumnsSize() {
        return this.grid[0][0].length;

    }

    /**
     * get all the neighbour WALLs position around given position
     *
     * @param currentPosition position to get the surrounding positions
     * @return all the surrounding WALLs positions of the given position
     * @throws IllegalArgumentException one of the given positions is not a valid position in the maze
     */
    public ArrayList<Position3D> getNeighbourWalls(Position3D currentPosition) throws IllegalArgumentException {
        ArrayList<Position3D> wallsList = new ArrayList<>();
        if (!this.validMazePosition(currentPosition)) {
            throw new IllegalArgumentException("one of the given positions is not a valid position in the maze");
        }
        Position3D up = currentPosition.getUpPosition();
        if (this.validMazePosition(up) && this.positionOfWall(up))
            wallsList.add(up);
        Position3D down = currentPosition.getDownPosition();
        if (this.validMazePosition(down) && this.positionOfWall(down))
            wallsList.add(down);
        Position3D left = currentPosition.getLeftPosition();
        if (this.validMazePosition(left) && this.positionOfWall(left))
            wallsList.add(left);
        Position3D right = currentPosition.getRightPosition();
        if (this.validMazePosition(right) && this.positionOfWall(right))
            wallsList.add(right);
        Position3D higher = currentPosition.getHigherPosition();
        if (this.validMazePosition(higher) && this.positionOfWall(higher))
            wallsList.add(higher);
        Position3D lower = currentPosition.getLowerPosition();
        if (this.validMazePosition(lower) && this.positionOfWall(lower))
            wallsList.add(lower);
        return wallsList;
    }

    /**
     * get all the neighbour TILEs position around given position
     *
     * @param currentPosition position to get the surrounding positions
     * @return all the surrounding TILEs positions of the given position
     * @throws IllegalArgumentException one of the given positions is not a valid position in the maze
     */
    public ArrayList<Position3D> getNeighbourTiles(Position3D currentPosition) throws IllegalArgumentException {
        ArrayList<Position3D> tileList = new ArrayList<>();
        if (!this.validMazePosition(currentPosition)) {
            throw new IllegalArgumentException("one of the given positions is not a valid position in the maze");
        }
        Position3D up = currentPosition.getUpPosition();
        if (this.validMazePosition(up) && this.positionOfTile(up))
            tileList.add(up);
        Position3D down = currentPosition.getDownPosition();
        if (this.validMazePosition(down) && this.positionOfTile(down))
            tileList.add(down);
        Position3D left = currentPosition.getLeftPosition();
        if (this.validMazePosition(left) && this.positionOfTile(left))
            tileList.add(left);
        Position3D right = currentPosition.getRightPosition();
        if (this.validMazePosition(right) && this.positionOfTile(right))
            tileList.add(right);
        Position3D higher = currentPosition.getHigherPosition();
        if (this.validMazePosition(higher) && this.positionOfTile(higher))
            tileList.add(higher);
        Position3D lower = currentPosition.getLowerPosition();
        if (this.validMazePosition(lower) && this.positionOfTile(lower))
            tileList.add(lower);
        return tileList;
    }

    /**
     * get all the neighbour WALLs that position 2 blocks away the given position
     * neighbour is up/right/down/left to the position
     *
     * @param currentPosition position to get the surrounding positions
     * @return all the surrounding WALLs positions of the given position
     * @throws IllegalArgumentException one of the given positions is not a valid position in the maze
     */
    public ArrayList<Position3D> wallsTwoBlocksAway(Position3D currentPosition) {
        ArrayList<Position3D> wallsList = new ArrayList<>();
        if (this.validMazePosition(currentPosition)) {
            Position3D up = currentPosition.getUpPosition().getUpPosition();
            if (this.validMazePosition(up) && positionOfWall(up)) //UP
                wallsList.add(up);
            Position3D right = currentPosition.getRightPosition().getRightPosition();
            if (this.validMazePosition(right) && positionOfWall(right)) //RIGHT
                wallsList.add(right);
            Position3D down = currentPosition.getDownPosition().getDownPosition();
            if (this.validMazePosition(down) && positionOfWall(down)) //DOWN
                wallsList.add(down);
            Position3D left = currentPosition.getLeftPosition().getLeftPosition();
            if (this.validMazePosition(left) && positionOfWall(left)) //LEFT
                wallsList.add(left);
            Position3D higher = currentPosition.getHigherPosition().getHigherPosition();
            if (this.validMazePosition(higher) && positionOfWall(higher)) //HIGHER
                wallsList.add(higher);
            Position3D lower = currentPosition.getLowerPosition().getLowerPosition();
            if (this.validMazePosition(lower) && positionOfWall(lower)) //LOWER
                wallsList.add(lower);
        }
        return wallsList;
    }

    /**
     * connect between two positions that have one block separate in the middle between them.
     * X?Y - ? for wall to remove
     *
     * @param currentPosition position of one - X
     * @param neighbour       position of one block away neighbour - Y
     * @throws IllegalArgumentException one of the given positions is not a valid position in the maze
     */
    public void connectNeighbours(Position3D currentPosition, Position3D neighbour) throws IllegalArgumentException {
        if (!validMazePosition(currentPosition) || !validMazePosition(neighbour))
            throw new IllegalArgumentException("one of the given positions is not a valid position in the maze");
        if (currentPosition.getDepthIndex() == neighbour.getDepthIndex()) {
            if (currentPosition.getRowIndex() == neighbour.getRowIndex()) {
                this.removeWall(new Position3D(currentPosition.getDepthIndex(), currentPosition.getRowIndex(), Math.min(neighbour.getColumnIndex(), currentPosition.getColumnIndex()) + 1));
            } else if (currentPosition.getColumnIndex() == neighbour.getColumnIndex()) {
                this.removeWall(new Position3D(currentPosition.getDepthIndex(), Math.min(neighbour.getRowIndex(), currentPosition.getRowIndex()) + 1, currentPosition.getColumnIndex()));
            }
        } else if (currentPosition.getRowIndex() == neighbour.getRowIndex()) {
            if (currentPosition.getDepthIndex() == neighbour.getDepthIndex()) {
                this.removeWall(new Position3D(currentPosition.getDepthIndex(), currentPosition.getRowIndex(), Math.min(neighbour.getColumnIndex(), currentPosition.getColumnIndex()) + 1));
            } else if (currentPosition.getColumnIndex() == neighbour.getColumnIndex()) {
                this.removeWall(new Position3D(Math.min(currentPosition.getDepthIndex(), neighbour.getDepthIndex()) + 1, currentPosition.getRowIndex(), currentPosition.getColumnIndex()));
            }
        } else if (currentPosition.getColumnIndex() == neighbour.getColumnIndex()) {
            if (currentPosition.getDepthIndex() == neighbour.getDepthIndex()) {
                this.removeWall(new Position3D(currentPosition.getDepthIndex(), Math.min(currentPosition.getRowIndex(), neighbour.getRowIndex()) + 1, Math.min(neighbour.getColumnIndex(), currentPosition.getColumnIndex()) + 1));
            } else if (currentPosition.getRowIndex() == neighbour.getRowIndex()) {
                this.removeWall(new Position3D(Math.min(currentPosition.getDepthIndex(), neighbour.getDepthIndex()) + 1, currentPosition.getRowIndex(), currentPosition.getColumnIndex()));
            }
        }
    }

    /**
     * randomly select a new starting position for the maze
     */
    public void generateStartPosition() {
        Random random = new Random();
        int side = random.nextInt(6);
        switch (side) {
            case 0 -> this.startPosition = new Position3D(0, random.nextInt(this.getRowsSize()), random.nextInt(this.getColumnsSize())); // UP
            case 1 -> this.startPosition = new Position3D(this.getDepthSize() - 1, random.nextInt(this.getRowsSize()), random.nextInt(this.getColumnsSize())); // DOWN
            case 2 -> this.startPosition = new Position3D(random.nextInt(this.getDepthSize()), random.nextInt(this.getRowsSize()), this.getColumnsSize() - 1); // RIGHT
            case 3 -> this.startPosition = new Position3D(random.nextInt(this.getDepthSize()), random.nextInt(this.getRowsSize()), 0); // LEFT
            case 4 -> this.startPosition = new Position3D(random.nextInt(this.getDepthSize()), this.getRowsSize() - 1, random.nextInt(this.getColumnsSize())); // FRONT
            case 5 -> this.startPosition = new Position3D(random.nextInt(this.getDepthSize()), 0, random.nextInt(this.getColumnsSize())); // BACK
            default -> this.startPosition = new Position3D(0, 0, 0);
        }
    }

    /**
     * randomly select a new goal position for the maze.
     * this position will be different from the starting point.
     *
     * @throws RuntimeException no possible GoalPositions in maze boarders.
     */
    public void generateGoalPosition() {
        Random random = new Random();
        ArrayList<Position3D> possibleGoals = new ArrayList<>();
        int depthSize = this.getDepthSize(), columnsSize = this.getColumnsSize(), rowSize = this.getRowsSize();
        for (int i = 0; i < rowSize; i++) {
            for (int j = 0; j < columnsSize; j++) {
                if (this.grid[0][i][j] == TILE)
                    possibleGoals.add(new Position3D(0, i, j));
                if (this.grid[depthSize - 1][i][j] == TILE)
                    possibleGoals.add(new Position3D(depthSize - 1, i, j));
            }
        }
        for (int i = 0; i < depthSize; i++) {
            for (int j = 0; j < columnsSize; j++) {
                if (this.grid[i][0][j] == TILE)
                    possibleGoals.add(new Position3D(i, 0, j));
                if (this.grid[i][rowSize - 1][j] == TILE)
                    possibleGoals.add(new Position3D(i, rowSize - 1, j));
            }
        }
        for (int i = 0; i < depthSize; i++) {
            for (int j = 0; j < rowSize; j++) {
                if (this.grid[i][j][0] == TILE)
                    possibleGoals.add(new Position3D(i, j, 0));
                if (this.grid[i][j][columnsSize - 1] == TILE)
                    possibleGoals.add(new Position3D(i, j, columnsSize - 1));
            }
        }
        if (possibleGoals.size() <= 1)
            throw new RuntimeException("no possible GoalPositions in maze boarders");
        do
            this.goalPosition = possibleGoals.get(random.nextInt(possibleGoals.size()));
        while (this.getStartPosition().equals(this.getGoalPosition()));
    }

    /**
     * @param position a valid position in the maze
     * @return true if it contains WALL otherwise false. invalid position will return false too.
     */
    public boolean positionOfWall(Position3D position) {
        return this.validMazePosition(position) &&
                this.grid[position.getDepthIndex()][position.getRowIndex()][position.getColumnIndex()] == WALL;
    }

    /**
     * @param position a valid position in the maze
     * @return true if it contains TILE otherwise false. invalid position will return false too.
     */
    public boolean positionOfTile(Position3D position) {
        return this.validMazePosition(position) &&
                this.grid[position.getDepthIndex()][position.getRowIndex()][position.getColumnIndex()] == TILE;
    }

    /**
     * @param position position in the maze we wish to check
     * @return true for valid position in the maze, otherwise false
     */
    public boolean validMazePosition(Position3D position) {
        return (position != null &&
                0 <= position.getDepthIndex() && position.getDepthIndex() < this.getDepthSize() &&
                0 <= position.getRowIndex() && position.getRowIndex() < this.getRowsSize() &&
                0 <= position.getColumnIndex() && position.getColumnIndex() < this.getColumnsSize());
    }
}
