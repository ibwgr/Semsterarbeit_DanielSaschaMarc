package Application;

import  javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.media.AudioClip;
import javafx.scene.shape.Circle;

import java.nio.file.Paths;

public class GameBoard extends GridPane {

    public GameBoard(ConnectFour game) {
        this.getStyleClass().add("gameboard");
        AudioClip drop = new AudioClip((Paths.get("src\\resources\\drop.mp3").toUri().toString()));
        AudioClip winner = new AudioClip((Paths.get("src\\resources\\winner.mp3").toUri().toString()));

        for (int i = 0; i < game.getGrid().size(); i++) {
            for (int j = 0; j < game.getGrid().get(i).size(); j++) {
                Button button = new Button();
                double r = 30;
                button.setShape(new Circle(r));
                button.setMinSize(2*r, 2*r);
                button.setMaxSize(2*r, 2*r);
                button.setStyle("-fx-background-color: " + game.getGrid().get(i).get(j).getValue());
                int finalI = i;
                int finalJ = j;
                button.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        if (!game.hasAWinner()) {
                            game.drop(finalJ);
                            drop.play();
                        }
                        if (game.hasAWinner()) {
                            GUI.getgPaneWinner().setVisible(true);
                            winner.play();
                        }
                    }
                });
                game.getGrid().get(i).get(j).addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                        button.setStyle("-fx-background-color: " + newValue);
                    }
                });
                this.add(button, j, i);
            }
        }
    }
}
