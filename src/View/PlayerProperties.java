package View;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;

import java.net.URL;
import java.util.ResourceBundle;

public class PlayerProperties implements Initializable {
    public Slider backSlider;
    public Slider fxSlider;
    public ToggleButton muteToggle;
    public ChoiceBox<PlayerConfig.GenerateAlgorithm> choiceBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //TODO: check the FX value we get!
        PlayerConfig config = PlayerConfig.getInstance();

        this.backSlider.setValue(config.getBackVolume()*100);
        this.fxSlider.setValue(config.getFxVolume()*100);
        this.muteToggle.setSelected(config.isMute());
        this.choiceBox.getItems().add(0,PlayerConfig.GenerateAlgorithm.MyMazeGenerator);
        this.choiceBox.getItems().add(1,PlayerConfig.GenerateAlgorithm.SimpleMazeGenerator);
        this.choiceBox.getItems().add(2,PlayerConfig.GenerateAlgorithm.EmptyMazeGenerator);
    }

    public void submit(ActionEvent actionEvent) {
        PlayerConfig config = PlayerConfig.getInstance();

        config.setBackVolume(this.backSlider.getValue()/100);
        config.setFxVolume(this.fxSlider.getValue()/100);
        config.setMute(this.muteToggle.isSelected());
        if (this.choiceBox.getValue() != null)
            config.setGenerateAlgorithm(PlayerConfig.toAlgorithm(this.choiceBox.getValue().toString()));

        StageGenerator.getInstance(StageGenerator.StageName.Properties).close();
        MazeAudioPlayer.getInstance().stopAll();
        MazeAudioPlayer.getInstance().play(MazeAudioPlayer.MazeSound.BackGround);
    }
}
