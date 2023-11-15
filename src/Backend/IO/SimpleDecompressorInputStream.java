package Backend.IO;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * simple decompress input stream - decorator pattern.
 * decompress maze as byte Array.
 */
public class SimpleDecompressorInputStream extends InputStream {
    private InputStream in;

    /**
     * Constructor
     *
     * @param in input stream to decorate
     */
    public SimpleDecompressorInputStream(InputStream in) {
        this.in = in;
    }

    /**
     * decompress byte array that represent a Maze.
     * the meta data stay the same. for the maze part:
     * 255,0,15,6 -> 255+15 times 1 and then 6 times 0.
     *
     * @param b      input array to decompress
     * @param target in-place changes the values to the decompress form of the input
     * @return the total number of bytes read into the buffer
     * @throws IllegalArgumentException the target byte Array given in too small
     */
    private static int decompress(byte[] b, byte[] target) throws IllegalArgumentException {
        ArrayList<Byte> holder = new ArrayList<>(); //placeholder for our return array
        byte countingType = 1; //1-wall, 0-tile
        //insert meta data
        for (int i = 0; i < 12; i++)
            holder.add(b[i]);

        //check target match the expected
        int expectedSize = (holder.get(0) + holder.get(1) * 127) * (holder.get(2) + holder.get(3) * 127) + 12;
        if (target.length < expectedSize)
            throw new IllegalArgumentException("the target byte Array given in too small");

        //compress maze
        for (int i = 12; i < b.length; i++) {
            int value = unsignedByteToInt(b[i]);
            for (int j = 0; j < value; j++)
                holder.add(countingType);
            countingType = countingType == 1 ? (byte) 0 : 1; //change type
        }

        for (int i = 0; i < expectedSize; i++)
            target[i] = holder.get(i);

        return expectedSize;
    }

    /**
     * convert "unsigned-byte like" number into normal one.
     *
     * @param num number in "unsigned-byte like" format
     * @return positive number represent the same value, negative numbers represent (num*-1)+127
     */
    private static int unsignedByteToInt(byte num) {
        int result;
        if (num >= 0)
            result = num;
        else
            result = num + 256;
        return result;
    }

    @Override
    public int read() throws IOException {
        return this.in.read();
    }

    @Override
    public int read(byte[] b) throws IOException, IllegalArgumentException {
        return decompress(in.readAllBytes(), b);
    }

    @Override
    public void close() throws IOException {
        this.in.close();
        super.close();
    }
}
