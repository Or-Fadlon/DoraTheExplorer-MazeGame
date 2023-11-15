package Backend.Server;

import Backend.algorithms.mazeGenerators.IMazeGenerator;
import Backend.algorithms.search.ISearchingAlgorithm;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

/**
 * Server Configuration - implementing "SingleTone Pattern"
 */
public class Configurations {
    // static variable of Singleton pattern
    private static Configurations single_instance = null;

    private final Properties properties;
    private int threadPoolSize;
    private String mazeGeneratingAlgorithm;
    private String mazeSearchingAlgorithm;

    /**
     * private constructor restricted to this class itself
     */
    private Configurations() {
        this.properties = new Properties();
        File file = new File("resources/config.properties");
        try {
            if (file.createNewFile()) {
                FileOutputStream fileOut = new FileOutputStream("resources/config.properties");
                properties.setProperty("threadPoolSize", "" + 3);
                properties.setProperty("mazeGeneratingAlgorithm", "MyMazeGenerator");
                properties.setProperty("mazeSearchingAlgorithm", "BestFirstSearch");
                properties.store(fileOut, null);
            }
            FileInputStream fileIn = new FileInputStream("resources/config.properties");
            properties.load(fileIn);
            this.threadPoolSize = Integer.parseInt(properties.getProperty("threadPoolSize"));
            this.mazeGeneratingAlgorithm = properties.getProperty("mazeGeneratingAlgorithm");
            this.mazeSearchingAlgorithm = properties.getProperty("mazeSearchingAlgorithm");

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    /**
     * static method to create instance of Singleton class
     *
     * @return instance of the Single-Tone class
     */
    public static Configurations getInstance() {
        if (single_instance == null)
            single_instance = new Configurations();

        return single_instance;
    }

    /**
     * @return thread pool size
     */
    public int getThreadPoolSize() {
        return this.threadPoolSize;
    }

    /**
     * set methode of the threadPoolSize
     *
     * @param threadPoolSize natural number, number of threads
     * @throws IllegalArgumentException ThreadPool size cant be < 1
     */
    public void setThreadPoolSize(int threadPoolSize) throws IllegalArgumentException {
        if (threadPoolSize < 1)
            throw new IllegalArgumentException("ThreadPool size cant be < 1");
        try {
            FileOutputStream fileOut = new FileOutputStream("resources/config.properties");
            properties.setProperty("threadPoolSize", "" + threadPoolSize);
            properties.store(fileOut, null);
            this.threadPoolSize = threadPoolSize;
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    /**
     * @return name of the maze generating algorithm
     */
    public String getMazeGeneratingAlgorithm() {
        return mazeGeneratingAlgorithm;
    }

    /**
     * set methode of the mazeGeneratingAlgorithm
     *
     * @param mazeGeneratingAlgorithm name of maze generating algorithm that implement IMazeGenerator
     *                                and locate at Backend.algorithms.mazeGenerators
     * @throws ClassNotFoundException Given class isn't implementing IMazeGenerator
     */
    public void setMazeGeneratingAlgorithm(String mazeGeneratingAlgorithm) throws ClassNotFoundException {
        try {
            Class<?> mazeGenerateClass = Class.forName("Backend.algorithms.mazeGenerators." + mazeGeneratingAlgorithm);
            IMazeGenerator mazeGenerateAlgorithm = (IMazeGenerator) mazeGenerateClass.getDeclaredConstructor().newInstance();
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            throw new ClassNotFoundException("Given class isn't implementing IMazeGenerator");
        }

        try {
            FileOutputStream fileOut = new FileOutputStream("resources/config.properties");
            properties.setProperty("mazeGeneratingAlgorithm", mazeGeneratingAlgorithm);
            properties.store(fileOut, null);
            this.mazeGeneratingAlgorithm = mazeGeneratingAlgorithm;
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    /**
     * @return name of the maze searching algorithm
     */
    public String getMazeSearchingAlgorithm() {
        return mazeSearchingAlgorithm;
    }

    /**
     * set methode of the mazeSearchingAlgorithm
     *
     * @param mazeSearchingAlgorithm name of maze solving algorithm that implement ISearchingAlgorithm
     *                               and locate at Backend.algorithms.search
     * @throws ClassNotFoundException Given class isn't implementing ISearchingAlgorithm
     */
    public void setMazeSearchingAlgorithm(String mazeSearchingAlgorithm) throws ClassNotFoundException {
        try {
            Class<?> mazeSearchClass = Class.forName("Backend.algorithms.search." + mazeSearchingAlgorithm);
            ISearchingAlgorithm mazeSearchAlgorithm = (ISearchingAlgorithm) mazeSearchClass.getDeclaredConstructor().newInstance();
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            throw new ClassNotFoundException("Given class isn't implementing ISearchingAlgorithm");
        }

        try {
            FileOutputStream fileOut = new FileOutputStream("resources/config.properties");
            properties.setProperty("mazeSearchingAlgorithm", mazeSearchingAlgorithm);
            properties.store(fileOut, null);
            this.mazeSearchingAlgorithm = mazeSearchingAlgorithm;
        } catch (IOException io) {
            io.printStackTrace();
        }
    }
}