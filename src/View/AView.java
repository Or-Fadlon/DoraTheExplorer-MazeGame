package View;

import ViewModel.MyViewModel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;


public abstract class AView implements IView, Observer {
    protected MyViewModel myViewModel;

    @Override
    public void update(Observable o, Object arg) {
    }
}

