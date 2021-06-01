package View;

import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.control.*;

public class AlertBox {
    public static void display(String title, String message) {
        Stage alert = new Stage();
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setTitle(title);
        alert.setMinWidth(250);

        Label label = new Label();
        label.setText(message);
        Button closeButton = new Button("Ok");
        closeButton.setOnAction(e -> alert.close());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, closeButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        alert.setScene(scene);
        alert.showAndWait();
    }
}
