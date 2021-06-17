package View;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CharSelect implements Initializable {
    public static MazeGallery.Theme theme = MazeGallery.Theme.Dora;
    public static int rows = 0, cols = 0;
    public ImageView doraChar;
    public ImageView diegoChar;
    public ImageView imageView;
    Image doraC = new Image(getClass().getResourceAsStream("/Images/Select/doraColor.png"));
    Image doraB = new Image(getClass().getResourceAsStream("/Images/Select/doraBW.png"));
    Image diegoC = new Image(getClass().getResourceAsStream("/Images/Select/diegoColor.png"));
    Image diedoB = new Image(getClass().getResourceAsStream("/Images/Select/diegoBW.png"));

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Image image = new Image(getClass().getResourceAsStream("/Images/select.png"));
        imageView.setImage(image);
        theme = MazeGallery.Theme.Dora;
        doraChar.setImage(doraC);
        diegoChar.setImage(diedoB);
    }

    public void keyPressed(KeyEvent keyEvent) {
        switch ((keyEvent.getCode())) {
            case A, D, NUMPAD6, RIGHT, NUMPAD4, LEFT -> this.changeLeft();
            case ENTER -> this.generateMazeButton(null);
        }
        keyEvent.consume();
    }

    private void changeLeft() {
        switch (theme) {
            case Dora -> {
                doraChar.setImage(doraB);
                diegoChar.setImage(diegoC);
                theme = MazeGallery.Theme.Diego;
            }
            case Diego -> {
                doraChar.setImage(doraC);
                diegoChar.setImage(diedoB);
                theme = MazeGallery.Theme.Dora;
            }
        }
    }

    public void generateMazeButton(ActionEvent actionEvent) {
        MazeGallery.getInstance().reloadTheme(theme);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/MazeDisplayer.fxml"));

        Parent root;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        MazeView mazeView = loader.getController();
        Stage stage = StageGenerator.getInstance(StageGenerator.StageName.Main);
        stage.setScene(new Scene(root));
        stage.show();
        mazeView.generateNewMaze(rows, cols);
    }

    public void mouseClicked(MouseEvent mouseEvent) {
        StageGenerator.getInstance(StageGenerator.StageName.Main).requestFocus();
    }

}
