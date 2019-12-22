package Application;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class GUI extends Application{
    Stage window;

    @Override
    public void start(Stage primaryStage) throws Exception {
        ConnectFour game = new ConnectFour(8,6);
        window = primaryStage;
        window.setTitle("4 Gewinnt");
        window.setMinHeight(500);
        window.setMinWidth(700);

        GridPane layout = new GridPane();
        layout.setPadding(new Insets(10,10,10,10));
        layout.setHgap(5);
        layout.setVgap(5);

        for (int i = 0; i < game.getGrid().size(); i++) {
            for (int j = 0; j < game.getGrid().get(i).size(); j++) {
                Button button = new Button();
                button.setMinWidth(60);
                button.setMinHeight(60);
                button.setStyle("-fx-background-color: " + game.getGrid().get(i).get(j).getValue());
                int finalI = i;
                int finalJ = j;
                button.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        game.drop(finalJ);
                        if (game.hasAWinner()) {
                            System.out.println("soemone won");
                        }
                    }
                });
                game.getGrid().get(i).get(j).addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                        button.setStyle("-fx-background-color: " + newValue);
                    }
                });
                layout.add(button, j, i);
            }
        }

        Scene scene = new Scene(layout, 500,500);
        window.setScene(scene);
        window.show();
    }
}
