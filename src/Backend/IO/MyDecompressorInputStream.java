package Backend.IO;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * my decompress input stream - decorator pattern.
 * decompress maze as byte Array.
 */
public class MyDecompressorInputStream extends InputStream {
    private InputStream in;

    /**
     * Constructor
     *
     * @param in input stream to decorate
     */
    public MyDecompressorInputStream(InputStream in) {
        this.in = in;
    }


    /**
     * decompress methode
     * the meta data stay the same. for the maze part, expend every byte into 8.
     * every byte in the array became 8 bytes, one for each bit of the byte.
     * (13) -> 00001101 -> 0,0,0,0,1,1,0,1
     *
     * @param bytes  array of bytes represent compressed maze
     * @param target the target
     * @return the total number of bytes read into the buffer
     * @throws IllegalArgumentException the target byte Array given is null or too small
     */
    private static int decompress(byte[] bytes, byte[] target) throws IllegalArgumentException {
        ArrayList<Byte> holder = new ArrayList<>(); //placeholder for our return array

        //insert meta data
        for (int i = 0; i < 12; i++)
            holder.add(bytes[i]);

        //check target match the expected
        int expectedSize = (holder.get(0) + holder.get(1) * 127) * (holder.get(2) + holder.get(3) * 127) + 12;
        if (target == null || target.length < expectedSize)
            throw new IllegalArgumentException("the target byte Array given is null or too small");

        //compress maze
        for (int i = 12; i < bytes.length; i++) {
            int temp = bytes[i] > 0 ? bytes[i] : bytes[i] + 256;
            for (int j = 0; j < 8; j++) {
                holder.add((byte) (temp % 2));
                temp /= 2;
            }
        }

        //prepare the result array
        for (int i = 0; i < expectedSize; i++)
            target[i] = holder.get(i);

        return expectedSize;
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
