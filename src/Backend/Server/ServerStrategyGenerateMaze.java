package Backend.Server;

import Backend.IO.MyCompressorOutputStream;
import Backend.algorithms.mazeGenerators.IMazeGenerator;
import Backend.algorithms.mazeGenerators.Maze;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.SocketException;

/**
 * generating maze server strategy
 */
public class ServerStrategyGenerateMaze extends AServerStrategy {

    public ServerStrategyGenerateMaze() {
        super("generate");
    }

    @Override
    public void ServerStrategy(InputStream inFromClient, OutputStream outToClient) {
        try {
            ObjectInputStream dimensionInputStream = new ObjectInputStream(inFromClient);
            ObjectOutputStream toClient = new ObjectOutputStream(outToClient);
            int[] mazeDimensions = (int[]) dimensionInputStream.readObject(); //get requested maze dimensions
            //create IMazeGenerator class using the name that in configuration file to generate the maze with.
            Class<?> mazeGeneratorClass = Class.forName("Backend.algorithms.mazeGenerators." + Configurations.getInstance().getMazeGeneratingAlgorithm());
            IMazeGenerator mazeGenerator = (IMazeGenerator) mazeGeneratorClass.getDeclaredConstructor().newInstance();
            Maze maze = mazeGenerator.generate(mazeDimensions[0], mazeDimensions[1]);

            ByteArrayOutputStream ba = new ByteArrayOutputStream();
            MyCompressorOutputStream compressorOutputStream = new MyCompressorOutputStream(ba);
            compressorOutputStream.write(maze.toByteArray());
            compressorOutputStream.flush();
            compressorOutputStream.close();

            toClient.writeObject(ba.toByteArray());
            toClient.flush();
            toClient.close();
            ba.close();
        } catch (SocketException e) {
            LOG.info("Lost connection with client " + outToClient.toString());
        } catch (IOException | InvocationTargetException | ClassNotFoundException | NoSuchMethodException
                | InstantiationException | IllegalAccessException e) {
            LOG.error(e.toString());
            e.printStackTrace();
        } catch (Exception e) {
            LOG.error("Unexpected error!");
            LOG.error(e.toString());
            e.printStackTrace();
        }
    }
}
