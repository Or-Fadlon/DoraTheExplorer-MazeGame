package Backend.IO;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * simple compress output stream - decorator pattern
 * compress maze as byte Array.
 */
public class SimpleCompressorOutputStream extends OutputStream {
    private OutputStream out;

    /**
     * Constructor
     *
     * @param out output stream to decorate
     */
    public SimpleCompressorOutputStream(OutputStream out) {
        this.out = out;
    }

    /**
     * compress byte array that represent a Maze.
     * count series of 1 or 0 and change it to the counter.
     * 255,0,15,6 - 255+15 times 1 and then 6 times 0.
     *
     * @param bytes first 12 cells are the meta-data. other are the maze details
     * @return compressed array
     */
    private static byte[] compress(byte[] bytes) {
        ArrayList<Byte> holder = new ArrayList<>(); //placeholder for our return array
        int counter = 0, //count the number of times Type appear in a sequence
                countingType = 1; //1-wall, 0-tile

        //insert meta data
        for (int i = 0; i < 12; i++)
            holder.add(bytes[i]);

        //compress maze
        for (int i = 12; i < bytes.length; i++) {
            if (bytes[i] == countingType)
                counter++;
            else {
                addNumberToByteArray(holder, counter);
                countingType = countingType == 1 ? 0 : 1; //change type
                counter = 1;
            }
        }
        addNumberToByteArray(holder, counter);

        //prepare the result array
        byte[] gridAsArray = new byte[holder.size()];
        for (int i = 0; i < gridAsArray.length; i++)
            gridAsArray[i] = holder.get(i);

        return gridAsArray;
    }


    /**
     * adding natural number to ArrayList of bytes like its "unsigned-byte".
     * if the number is bigger then 255 splits it and add 0 in middle.
     * 256 -> 255,0,1
     *
     * @param holder ArrayList to add to
     * @param number the number to add
     */
    private static void addNumberToByteArray(ArrayList<Byte> holder, int number) {
        while (number >= 0) {
            if (number > 255) {
                holder.add(toUnsignedByte(255));
                holder.add((byte) 0);
            } else
                holder.add(toUnsignedByte(number));
            number -= 255;
        }
    }

    /**
     * convert normal number into "unsigned-byte like"
     * can convert only numbers between 0-255.
     *
     * @param num number between 0-255
     * @return positive number represent the same value, negative numbers represent (num*-1)+127
     * @throws IllegalArgumentException can get only number between 0-255
     */
    private static byte toUnsignedByte(int num) {
        byte result;
        if (num < 0 || 255 < num)
            throw new IllegalArgumentException("can get only number between 0-255");
        return (byte) num;
    }

    @Override
    public void write(int b) throws IOException {
        this.out.write(b);
    }

    @Override
    public void write(byte[] b) throws IOException {
        this.out.write(compress(b));
    }

    @Override
    public void flush() throws IOException {
        this.out.flush();
        super.flush();
    }

    @Override
    public void close() throws IOException {
        this.out.close();
        super.close();
    }
}
