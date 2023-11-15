package Frontend.View;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class PlayerConfig {
    private static final String filePath = "resources/PlayerConfig.properties";
    private static PlayerConfig single_instance = null;
    private final Properties properties = new Properties();

    private boolean mute;
    private double fxVolume, backVolume;
    private GenerateAlgorithm generateAlgorithm;

    private PlayerConfig() {
        File file = new File(filePath);

        try {
            if (file.createNewFile()) {
                FileOutputStream fileOut = new FileOutputStream(filePath);
                this.properties.setProperty("mute", "false");
                this.properties.setProperty("fxVolume", "1.0");
                this.properties.setProperty("backVolume", "0.5");
                this.properties.setProperty("generateAlgorithm", GenerateAlgorithm.MyMazeGenerator.name());
                this.properties.store(fileOut, null);
            }

            FileInputStream fileIn = new FileInputStream(filePath);
            this.properties.load(fileIn);
            this.mute = this.properties.getProperty("mute").equals("true");
            this.fxVolume = Double.parseDouble(this.properties.getProperty("fxVolume"));
            this.backVolume = Double.parseDouble(this.properties.getProperty("backVolume"));
            this.generateAlgorithm = toAlgorithm(this.properties.getProperty("generateAlgorithm"));
        } catch (IOException var3) {
            var3.printStackTrace();
        }

    }

    public static PlayerConfig getInstance() {
        if (single_instance == null)
            single_instance = new PlayerConfig();

        return single_instance;
    }

    public boolean isMute() {
        return mute;
    }

    public void setMute(boolean mute) {
        try {
            FileOutputStream fileOut = new FileOutputStream(filePath);
            this.properties.setProperty("mute", "" + mute);
            this.properties.store(fileOut, null);
            this.mute = mute;
        } catch (IOException var4) {
            var4.printStackTrace();
        }

    }

    public double getFxVolume() {
        return fxVolume;
    }

    public void setFxVolume(double fxVolume) {
        if (0 <= this.fxVolume && this.fxVolume <= 1) {
            try {
                FileOutputStream fileOut = new FileOutputStream(filePath);
                this.properties.setProperty("fxVolume", "" + fxVolume);
                this.properties.store(fileOut, null);
                this.fxVolume = fxVolume;
            } catch (IOException var4) {
                var4.printStackTrace();
            }
        }
    }

    public double getBackVolume() {
        return backVolume;
    }

    public void setBackVolume(double backVolume) {
        if (0 <= backVolume && backVolume <= 1) {
            try {
                FileOutputStream fileOut = new FileOutputStream(filePath);
                this.properties.setProperty("backVolume", "" + backVolume);
                this.properties.store(fileOut, null);
                this.backVolume = backVolume;
            } catch (IOException var4) {
                var4.printStackTrace();
            }
        }
    }

    public GenerateAlgorithm getGenerateAlgorithm() {
        return this.generateAlgorithm;
    }

    public void setGenerateAlgorithm(GenerateAlgorithm generateAlgorithm) {
        this.generateAlgorithm = generateAlgorithm;
    }

    public static GenerateAlgorithm toAlgorithm(String algo) {
        GenerateAlgorithm retVal = GenerateAlgorithm.MyMazeGenerator;
        switch (algo) {
            case "EmptyMazeGenerator" -> retVal = GenerateAlgorithm.EmptyMazeGenerator;
            case "SimpleMazeGenerator" -> retVal = GenerateAlgorithm.SimpleMazeGenerator;
        }
        return retVal;
    }

    public enum GenerateAlgorithm {
        EmptyMazeGenerator,
        SimpleMazeGenerator,
        MyMazeGenerator
    }
}
