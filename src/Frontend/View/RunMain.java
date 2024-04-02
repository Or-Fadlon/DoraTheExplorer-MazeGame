package Frontend.View;

import java.nio.file.Path;
import java.nio.file.Paths;

public class RunMain {
    public static void main(String[] args) {
        System.out.println("Dora The Explorer - START!!!");
        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        System.out.println("Current absolute path is: " + s);
        Main.main(args);
    }
}
