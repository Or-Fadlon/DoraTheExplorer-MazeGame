package Backend.algorithms.maze3D;

/**
 * this class represents a position in 3D area.
 * we think about our area as a 3D table with x as column number, y as row and z as depth.
 * the values of the depth, row, column are Integer number.
 */
public class Position3D {
    private int depth, row, column;

    /**
     * constructor
     *
     * @param depth  depth.
     * @param column column.
     * @param row    row.
     */
    public Position3D(int depth, int row, int column) {
        this.depth = depth;
        this.column = column;
        this.row = row;
    }

    /**
     * constructor
     *
     * @param str format: "{depth,row,column}"
     * @throws IllegalArgumentException argument String don't match the format required: {depth,row,column}
     */
    public Position3D(String str) throws IllegalArgumentException {
        if (str == null || str.length() < 7 || str.charAt(0) != '{' || str.charAt(str.length() - 1) != '}' || !str.contains(","))
            throw new IllegalArgumentException("argument String don't match the format required: {depth,row,column}");
        String[] parts = str.split(",");
        this.depth = Integer.parseInt(parts[0].substring(1));
        this.row = Integer.parseInt(parts[1]);
        this.column = Integer.parseInt(parts[2].substring(0, parts[2].length() - 1));
    }

    /**
     * copy constructor
     * if other is null create with default values
     *
     * @param other Position to copy
     */
    public Position3D(Position3D other) {
        if (other != null) {
            this.depth = other.depth;
            this.column = other.column;
            this.row = other.row;
        }
    }

    /**
     * @return depth index
     */
    public int getDepthIndex() {
        return this.depth;
    }

    /**
     * @return row index
     */
    public int getRowIndex() {
        return this.row;
    }

    /**
     * @return column index
     */
    public int getColumnIndex() {
        return this.column;
    }

    /**
     * @return Position with row-1
     */
    public Position3D getUpPosition() {
        return new Position3D(this.depth, this.row - 1, this.column);
    }

    /**
     * @return Position with column+1
     */
    public Position3D getRightPosition() {
        return new Position3D(this.depth, this.row, this.column + 1);
    }

    /**
     * @return Position of with row+1
     */
    public Position3D getDownPosition() {
        return new Position3D(this.depth, this.row + 1, this.column);
    }

    /**
     * @return Position of with column-1
     */
    public Position3D getLeftPosition() {
        return new Position3D(this.depth, this.row, this.column - 1);
    }

    /**
     * @return Position of with depth-1
     */
    public Position3D getHigherPosition() {
        return new Position3D(this.depth - 1, this.row, this.column);
    }

    /**
     * @return Position of with depth+1
     */
    public Position3D getLowerPosition() {
        return new Position3D(this.depth + 1, this.row, this.column);
    }

    @Override
    public String toString() {
        return "{" + depth + "," + row + "," + column + '}';
    }

    /**
     * two Points equals when its depth, row and column index equals
     *
     * @param o
     * @return true - depth, row and column index equals. otherwise false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Position3D that = (Position3D) o;

        if (depth != that.depth) return false;
        if (row != that.row) return false;
        return column == that.column;
    }

    @Override
    public int hashCode() {
        int result = depth;
        result = 31 * result + row;
        result = 31 * result + column;
        return result;
    }
}
