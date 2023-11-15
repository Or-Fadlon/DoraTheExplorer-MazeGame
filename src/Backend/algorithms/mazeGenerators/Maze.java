package Backend.algorithms.mazeGenerators;

import Backend.algorithms.mazeGenerators.Position;

import java.io.Serializable;
import java.util.*;

/**
 * Represent a 2D maze
 */
public class Maze implements Serializable {
    private static final int WALL = 1, TILE = 0;
    private int[][] grid;
    private Position startPosition, goalPosition;

    /**
     * constructor
     *
     * @param rows    number of rows
     * @param columns number of columns
     * @throws IllegalArgumentException if one one or more of the arguments are < 2
     */
    public Maze(int rows, int columns) throws IllegalArgumentException {
        if (columns < 2 || rows < 2)
            throw new IllegalArgumentException("one or more of the arguments are < 2");
        this.grid = new int[rows][columns];
        this.startPosition = new Position(0, 0);
        this.goalPosition = new Position(rows - 1, columns - 1);
    }

    /**
     * constructor
     *
     * @param grid          a grid full of WALLs and TILEs
     * @param startPosition maze start position
     * @param goalPosition  maze goal position
     */
    public Maze(int[][] grid, Position startPosition, Position goalPosition) {
        this.grid = grid;
        this.startPosition = startPosition;
        this.goalPosition = goalPosition;
    }

    /**
     * constructor
     * 0,1-row size. 2,3-column size. 4,5-start position row. 6,7-start position column.
     * 8,9-goal position row. 10,11-goal position column. 12+ -the maze
     *
     * @param bytes in the format. first 12 cells using base 127
     * @throws IllegalArgumentException the array was given not in the right format.
     */
    public Maze(byte[] bytes) throws IllegalArgumentException {
        try {
            this.grid = new int[base127ToDecimal(bytes[0], bytes[1])][base127ToDecimal(bytes[2], bytes[3])];
            this.startPosition = new Position(base127ToDecimal(bytes[4], bytes[5]), base127ToDecimal(bytes[6], bytes[7]));
            this.goalPosition = new Position(base127ToDecimal(bytes[8], bytes[9]), base127ToDecimal(bytes[10], bytes[11]));

            int counter = 12;
            for (int i = 0; i < this.grid.length; i++)
                for (int j = 0; j < this.grid[0].length; j++)
                    this.grid[i][j] = bytes[counter++];
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException("the array was given not in the right format");
        }
    }

    /**
     * concatenate tro bytes Arrays
     *
     * @param a first array to concatenate
     * @param b second array to concatenate
     * @return concatenate array
     */
    private static byte[] concatenateArrays(byte[] a, byte[] b) {
        byte[] result = new byte[a.length + b.length];
        for (int i = 0; i < a.length; i++)
            result[i] = a[i];
        for (int i = 0; i < b.length; i++)
            result[a.length + i] = b[i];
        return result;
    }

    /**
     * transfer base 127 into decimal number.
     * maximum decimal number 16,256
     *
     * @param lsb the lsb of the 127 base number
     * @param msb the msb of the 127 base number
     * @return decimal number
     */
    private static int base127ToDecimal(byte lsb, byte msb) {
        return msb * 127 + lsb;
    }

    /**
     * print a colored console view of the maze
     */
    public void printColored() {
        if (this.getRowsSize() > 100 || this.getColumnsSize() > 100) {
            Scanner in = new Scanner(System.in);
            System.out.println("this maze is big, are you sure you want to print it? (y/n)");
            String ans = in.nextLine();
            if (!ans.equals("y") && !ans.equals("Y"))
                return;
        }
        final String RED = "\033[0;31m";
        final String GREEN = "\033[0;32m";
        final String BLACK_BACKGROUND = "\u001B[40m";
        final String WHITE_BACKGROUND = "\u001B[47m";
        final String RESET = "\033[0m";
        for (int i = 0; i < this.getRowsSize(); i++) {
            System.out.print("{");
            for (int j = 0; j < this.getColumnsSize(); j++) {
                if (this.startPosition.equals(new Position(i, j)))
                    System.out.print(GREEN + " S" + RESET);
                else if (this.goalPosition.equals(new Position(i, j)))
                    System.out.print(RED + " E" + RESET);
                else if (this.grid[i][j] == 1)
                    System.out.print(BLACK_BACKGROUND + "  " + RESET);
                else
                    System.out.print("  ");
            }
            System.out.println(" }");
        }
    }

    /**
     * print a console view of the maze
     */
    public void print() {
        for (int i = 0; i < this.getRowsSize(); i++) {
            System.out.print("{");
            for (int j = 0; j < this.getColumnsSize(); j++) {
                if (this.startPosition.equals(new Position(i, j)))
                    System.out.print(" S");
                else if (this.goalPosition.equals(new Position(i, j)))
                    System.out.print(" E");
                else
                    System.out.print(" " + this.grid[i][j]);
            }
            System.out.println(" }");
        }
    }

    /**
     * print a colored console view of the maze
     * highlight maze Positions
     *
     * @param trace Positions to highlight
     */
    public void printColoredTrace(HashSet<Position> trace) {
        if (this.getRowsSize() > 100 || this.getColumnsSize() > 100) {
            Scanner in = new Scanner(System.in);
            System.out.println("this maze is big, are you sure you want to print it? (y/n)");
            String ans = in.nextLine();
            if (!ans.equals("y") && !ans.equals("Y"))
                return;
        }
        final String RED = "\033[0;31m";
        final String GREEN = "\033[0;32m";
        final String RESET = "\033[0m";
        final String YELLOW_BACKGROUND = "\u001B[43m";
        final String WHITE_BACKGROUND = "\u001B[47m";
        Position position;
        for (int i = 0; i < this.getRowsSize(); i++) {
            System.out.print("{");
            for (int j = 0; j < this.getColumnsSize(); j++) {
                position = new Position(i, j);
                if (this.startPosition.equals(position))
                    System.out.print(GREEN + " S" + RESET);
                else if (this.goalPosition.equals(position))
                    System.out.print(RED + " E" + RESET);
                else if (trace.contains(position))
                    System.out.print(YELLOW_BACKGROUND + "  " + RESET);
                else if (this.grid[i][j] == 1)
                    System.out.print(WHITE_BACKGROUND + "  " + RESET);
                else
                    System.out.print("  ");
            }
            System.out.println(" }");
        }
    }

    /**
     * @return copy of the maze grid
     */
    public int[][] getGrid() {
        return grid.clone();
    }

    /**
     * @return the start Position of the maze
     */
    public Position getStartPosition() {
        return new Position(this.startPosition);
    }

    /**
     * set start position in maze
     *
     * @param position valid Position in the maze
     * @throws IllegalArgumentException given position in not valid position in the maze
     */
    public void setStartPosition(Position position) throws IllegalArgumentException {
        if (!this.validMazePosition(position))
            throw new IllegalArgumentException("given position in not valid position in the maze");
        this.startPosition = new Position(position);
    }

    /**
     * @return the goal Position of the maze
     */
    public Position getGoalPosition() {
        return new Position(this.goalPosition);
    }

    /**
     * set goal position in maze
     *
     * @param position valid Position in the maze
     * @throws IllegalArgumentException given position in not valid position in the maze
     */
    public void setGoalPosition(Position position) throws IllegalArgumentException {
        if (!this.validMazePosition(position))
            throw new IllegalArgumentException("given position in not valid position in the maze");
        this.goalPosition = new Position(position);
    }

    /**
     * @param position a valid position in the maze
     * @return true if it contains WALL otherwise false. invalid position will return false too.
     */
    public boolean positionOfWall(Position position) {
        return this.validMazePosition(position) &&
                this.grid[position.getRowIndex()][position.getColumnIndex()] == WALL;
    }

    /**
     * @param position a valid position in the maze
     * @return true if it contains TILE otherwise false. invalid position will return false too.
     */
    public boolean positionOfTile(Position position) {
        return this.validMazePosition(position) &&
                this.grid[position.getRowIndex()][position.getColumnIndex()] == TILE;
    }

    /**
     * remove all walls from the maze
     */
    public void cleanAllWalls() {
        for (int i = 0; i < grid.length; i++)
            for (int j = 0; j < grid[0].length; j++)
                this.grid[i][j] = TILE;
    }

    /**
     * add a wall in every tile of the maze
     */
    public void makeAllWalls() {
        for (int i = 0; i < grid.length; i++)
            for (int j = 0; j < grid[0].length; j++)
                this.grid[i][j] = WALL;
    }

    /**
     * add a wall to the given position in the maze.
     * do nothing if the position isn't a valid position in the maze
     *
     * @param position a valid position in the maze
     */
    public void addWall(Position position) {
        if (this.validMazePosition(position))
            this.grid[position.getRowIndex()][position.getColumnIndex()] = WALL;
    }

    /**
     * remove a wall from the given position in the maze.
     * do nothing if the position isn't a valid position in the maze
     *
     * @param position a valid position in the maze
     */
    public void removeWall(Position position) {
        if (this.validMazePosition(position))
            this.grid[position.getRowIndex()][position.getColumnIndex()] = TILE;
    }

    /**
     * @return number of rows in the maze
     */
    public int getRowsSize() {
        return grid.length;
    }

    /**
     * @return number of columns in the maze
     */
    public int getColumnsSize() {
        return grid[0].length;
    }

    /**
     * @param position position in the maze we wish to check
     * @return true for valid position in the maze, otherwise false
     */
    public boolean validMazePosition(Position position) {
        return (position != null &&
                0 <= position.getRowIndex() && position.getRowIndex() < this.getRowsSize() &&
                0 <= position.getColumnIndex() && position.getColumnIndex() < this.getColumnsSize());
    }

    /**
     * get all the neighbour WALLs position around given position
     *
     * @param currentPosition position to get the surrounding positions
     * @return all the surrounding WALLs positions of the given position
     * @throws IllegalArgumentException one of the given positions is not a valid position in the maze
     */
    public ArrayList<Position> getNeighbourWalls(Position currentPosition) {
        ArrayList<Position> wallsList = new ArrayList<>();
        if (this.validMazePosition(currentPosition)) {
            Position up = currentPosition.getUpPosition();
            if (this.validMazePosition(up) && this.positionOfWall(up)) //UP
                wallsList.add(up);
            Position right = currentPosition.getRightPosition();
            if (this.validMazePosition(right) && this.positionOfWall(right)) //RIGHT
                wallsList.add(right);
            Position down = currentPosition.getDownPosition();
            if (this.validMazePosition(down) && this.positionOfWall(down)) //DOWN
                wallsList.add(down);
            Position left = currentPosition.getLeftPosition();
            if (this.validMazePosition(left) && this.positionOfWall(left)) //LEFT
                wallsList.add(left);
        }
        return wallsList;
    }

    /**
     * get all the neighbour TILEs position around given position
     *
     * @param currentPosition position to get the surrounding positions
     * @return all the surrounding TILEs positions of the given position
     * @throws IllegalArgumentException one of the given positions is not a valid position in the maze
     */
    public ArrayList<Position> getNeighbourTiles(Position currentPosition) {
        ArrayList<Position> tilesList = new ArrayList<>();
        if (this.validMazePosition(currentPosition)) {
            Position up = currentPosition.getUpPosition();
            if (this.validMazePosition(up) && this.positionOfTile(up)) //UP
                tilesList.add(up);
            Position down = currentPosition.getDownPosition();
            if (this.validMazePosition(down) && this.positionOfTile(down)) //DOWN
                tilesList.add(down);
            Position left = currentPosition.getLeftPosition();
            if (this.validMazePosition(left) && this.positionOfTile(left)) //LEFT
                tilesList.add(left);
            Position right = currentPosition.getRightPosition();
            if (this.validMazePosition(right) && this.positionOfTile(right)) //RIGHT
                tilesList.add(right);
        }
        return tilesList;
    }

    /**
     * get all the neighbour WALLs that position 2 positions away given position
     * neighbour is up/right/down/left to the position
     *
     * @param currentPosition position to get the surrounding positions
     * @return all the surrounding WALLs positions of the given position
     */
    public ArrayList<Position> wallsTwoBlocksAway(Position currentPosition) {
        ArrayList<Position> wallsList = new ArrayList<>();
        if (this.validMazePosition(currentPosition)) {
            Position up = currentPosition.getUpPosition().getUpPosition();
            if (this.validMazePosition(up) && positionOfWall(up)) //UP
                wallsList.add(up);
            Position right = currentPosition.getRightPosition().getRightPosition();
            if (this.validMazePosition(right) && positionOfWall(right)) //RIGHT
                wallsList.add(right);
            Position down = currentPosition.getDownPosition().getDownPosition();
            if (this.validMazePosition(down) && positionOfWall(down)) //DOWN
                wallsList.add(down);
            Position left = currentPosition.getLeftPosition().getLeftPosition();
            if (this.validMazePosition(left) && positionOfWall(left)) //LEFT
                wallsList.add(left);
        }
        return wallsList;
    }

    /**
     * connect between two positions that have one block separate in the middle between them.
     * X?Y - ? for wall to remove
     *
     * @param currentPosition position of one - X
     * @param neighbour       position of one block away neighbour - Y
     * @throws IllegalArgumentException one of the given positions is not a valid position in the
     */
    public void connectNeighbours(Position currentPosition, Position neighbour) throws IllegalArgumentException {
        if (!this.validMazePosition(currentPosition)) {
            throw new IllegalArgumentException("one of the given positions is not a valid position in the maze");
        }
        if (currentPosition.getColumnIndex() == neighbour.getColumnIndex()) {
            this.removeWall(new Position(Math.min(neighbour.getRowIndex(), currentPosition.getRowIndex()) + 1, currentPosition.getColumnIndex()));
        } else if (currentPosition.getRowIndex() == neighbour.getRowIndex()) {
            this.removeWall(new Position(currentPosition.getRowIndex(), Math.min(neighbour.getColumnIndex(), currentPosition.getColumnIndex()) + 1));
        }
    }

    /**
     * randomly select a new starting position for the maze
     * can be a TILE or a WALL
     */
    public void generateStartPosition() {
        Random random = new Random();
        int side = random.nextInt(4);
        switch (side) {
            case 0 -> this.startPosition = new Position(0, random.nextInt(this.getColumnsSize())); //UP
            case 1 -> this.startPosition = new Position(random.nextInt(this.getRowsSize()), this.getColumnsSize() - 1); //RIGHT
            case 2 -> this.startPosition = new Position(this.getRowsSize() - 1, random.nextInt(this.getColumnsSize())); //DOWN
            case 3 -> this.startPosition = new Position(random.nextInt(this.getRowsSize()), 0); //LEFT
            default -> this.startPosition = new Position(0, 0);
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
        ArrayList<Position> possibleGoals = new ArrayList<>();
        int columnsSize = this.getColumnsSize(), rowSize = this.getRowsSize();
        for (int i = 0; i < columnsSize; i++) {
            if (this.grid[0][i] == TILE)
                possibleGoals.add(new Position(0, i));
            if (this.grid[rowSize - 1][i] == TILE)
                possibleGoals.add(new Position(rowSize - 1, i));
        }
        for (int i = 0; i < rowSize; i++) {
            if (this.grid[i][0] == TILE)
                possibleGoals.add(new Position(i, 0));
            if (this.grid[i][columnsSize - 1] == TILE)
                possibleGoals.add(new Position(i, columnsSize - 1));
        }
        if (possibleGoals.size() <= 1)
            throw new RuntimeException("no possible GoalPositions in maze boarders");
        do
            this.setGoalPosition(possibleGoals.get(random.nextInt(possibleGoals.size())));
        while (this.getStartPosition().equals(this.getGoalPosition()));

    }

    /**
     * meta data:
     * 0,1-row size. 2,3-column size. 4,5-start position row. 6,7-start position column.
     * 8,9-goal position row. 10,11-goal position column.
     *
     * @return maze as an array of bytes, first 12 cells contains the meta data
     */
    public byte[] toByteArray() {
        byte[] metaData = concatenateArrays(decimalToBase127(this.getRowsSize()), decimalToBase127(this.getColumnsSize()));
        byte[] metaStart = concatenateArrays(decimalToBase127(this.getStartPosition().getRowIndex()),
                decimalToBase127(this.getStartPosition().getColumnIndex()));
        byte[] metaGoal = concatenateArrays(decimalToBase127(this.getGoalPosition().getRowIndex()),
                decimalToBase127(this.getGoalPosition().getColumnIndex()));
        metaData = concatenateArrays(metaData, metaStart);
        metaData = concatenateArrays(metaData, metaGoal);

        byte[] gridAsArray = new byte[this.getRowsSize() * this.getColumnsSize()];
        int counter = 0;
        for (int i = 0; i < this.getRowsSize(); i++) {
            for (int j = 0; j < this.getColumnsSize(); j++) {
                gridAsArray[counter++] = (byte) this.grid[i][j];
            }
        }

        return concatenateArrays(metaData, gridAsArray);
    }

    /**
     * transfer decimal number into base 127.
     * maximum number 16,256
     *
     * @param num number to transfer (maximum 16,256)
     * @return 2 cells, 0-lsb 1-msb
     */
    private byte[] decimalToBase127(int num) {
        byte[] result = new byte[2];
        result[0] = (byte) (num % 127);
        result[1] = (byte) ((num / 127) % 127);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Backend.algorithms.mazeGenerators.Maze maze = (Backend.algorithms.mazeGenerators.Maze) o;
        return Arrays.equals(this.toByteArray(), maze.toByteArray());
    }

    @Override
    public int hashCode() {
        return hashArray(this.toByteArray());
    }

    private int hashArray(byte[] array) {
        int counter = 0;
        if (array != null)
            for (byte b : array) counter += b;
        return counter;
    }
}
