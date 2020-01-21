package Application;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class GUI extends Application{
    Stage window;

    @Override
    public void start(Stage primaryStage) throws Exception {
        ConnectFour game = new ConnectFour(8,6);
        window = primaryStage;
        window.setTitle("4 Gewinnt");
        window.setMinHeight(530);
        window.setMinWidth(850);

        // Gameboard
        GridPane layout = new GridPane();
        layout.setPadding(new Insets(10,10,10,10));
        layout.setHgap(5);
        layout.setVgap(5);

        // Logo Top
        Label lbVierGewinntLogo1 = new Label("4-GEWINNT");
        Label lbVierGewinntLogo2 = new Label("4-GEWINNT");
        lbVierGewinntLogo1.setPadding(new Insets(5,5,5,10));
        lbVierGewinntLogo1.setFont(Font.font ("Verdana", FontWeight.BOLD, 20));
        lbVierGewinntLogo2.setPadding(new Insets(5,5,5,10));
        lbVierGewinntLogo2.setFont(Font.font ("Verdana", FontWeight.BOLD, 20));

        // Left - Winnerinfo
        Label lbWinner = new Label("Sieger:");
        lbWinner.setPadding(new Insets(5,5,5,5));
        lbWinner.setFont(Font.font("Verdana", FontWeight.BOLD,12));
        Label lbWinnerWert = new Label("");
        lbWinnerWert.setPadding(new Insets(5,5,5,0));
        Label lbWinnerAnzZuege = new Label("Anzahl Spielzüge:                    ");
        lbWinnerAnzZuege.setPadding(new Insets(5,5,5,5));
        lbWinnerAnzZuege.setFont(Font.font("Verdana", FontWeight.BOLD,12));
        Label lbWinnerAnzZuegeWert = new Label("0");
        lbWinnerAnzZuegeWert.setPadding(new Insets(5,5,5,0));
        lbWinnerAnzZuege.setFont(Font.font("Verdana", FontWeight.BOLD,12));
        Label lbWinnerSpieldauer = new Label("Spieldauer: ");
        lbWinnerSpieldauer.setPadding(new Insets(5,5,5,5));
        lbWinnerSpieldauer.setFont(Font.font("Verdana", FontWeight.BOLD,12));
        Label lbWinnerSpieldauerWert = new Label("3min 15sek");
        lbWinnerSpieldauerWert.setPadding(new Insets(5,5,5,0));

        // Left - Game Infos
        Label lbAktSpieler = new Label("Aktueller Spieler: ");
            lbAktSpieler.setPadding(new Insets(5,5,5,0));
            lbAktSpieler.setFont(Font.font("Verdana", FontWeight.BOLD,12));
        Label lbAktSpielerWert = new Label("Player 1");
        Label lbSpieldauer = new Label("Spieldauer: ");
            lbSpieldauer.setPadding(new Insets(5,5,5,0));
            lbSpieldauer.setFont(Font.font("Verdana", FontWeight.BOLD,12));
        Label lbSpieldauerWert = new Label("2min 15sek");
        Service s = new Service() {
            @Override
            protected Task createTask() {
                Stoppuhr stoppuhr = new Stoppuhr(game);
                try {
                    stoppuhr.messageProperty().addListener(new ChangeListener<String>() {
                        @Override
                        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                            lbSpieldauerWert.setText(newValue);
                            lbWinnerSpieldauerWert.setText(newValue);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return stoppuhr;
            }
        };
        s.start();
        Label lbAnzZuegePl1 = new Label("Anzahl Spielzüge Player 1: ");
            lbAnzZuegePl1.setPadding(new Insets(5,25,5,0));
            lbAnzZuegePl1.setFont(Font.font("Verdana", FontWeight.BOLD,12));
        Label lbAnzZuegePl1Wert = new Label("0");
        Label lbAnzZuegePl2 = new Label("Anzahl Spielzüge Player 2: ");
            lbAnzZuegePl2.setPadding(new Insets(5,5,5,0));
            lbAnzZuegePl2.setFont(Font.font("Verdana", FontWeight.BOLD,12));
        Label lbAnzZuegePl2Wert = new Label("0");
        Label lbAbstand = new Label("");


        // Gridpane Gameinfo
        GridPane gPaneSpielInfo = new GridPane();
        gPaneSpielInfo.add(lbAktSpieler,0,0);
        gPaneSpielInfo.add(lbAktSpielerWert,1,0);
        gPaneSpielInfo.add(lbSpieldauer,0,1);
        gPaneSpielInfo.add(lbSpieldauerWert,1,1);
        gPaneSpielInfo.add(lbAnzZuegePl1,0,2);
        gPaneSpielInfo.add(lbAnzZuegePl1Wert,1,2);
        gPaneSpielInfo.add(lbAnzZuegePl2,0,3);
        gPaneSpielInfo.add(lbAnzZuegePl2Wert,1,3);
        gPaneSpielInfo.add(lbAbstand,0,4);


        // Gridpand Winner
        GridPane gPaneWinner = new GridPane();
        gPaneWinner.setVisible(false);
        gPaneWinner.setStyle("-fx-background-color: lightgrey");
        gPaneWinner.add(lbWinner,0,0);
        gPaneWinner.add(lbWinnerWert,1,0);
        gPaneWinner.add(lbWinnerAnzZuege,0,1);
        gPaneWinner.add(lbWinnerAnzZuegeWert,1,1);
        gPaneWinner.add(lbWinnerSpieldauer,0,2);
        gPaneWinner.add(lbWinnerSpieldauerWert,1,2);


        // Left - VBox für Spielinfos
        VBox vBoxL = new VBox(gPaneSpielInfo, gPaneWinner);  //lbAktSpieler,lbSpieldauer,lbAnzZuegePl1,lbAnzZuegePl2
        vBoxL.setPadding(new Insets(10,10,10,10));
        //vBoxL.setStyle("-fx-background-color: lightblue");

        // Spielanleitung
        Button btnSpielanleitung = new Button("Spielanleitung");
        btnSpielanleitung.setPadding(new Insets(5,5,5,5));
        Label lbSpielanleitungTitel = new Label("Spielanleitung: ");
        lbSpielanleitungTitel.setPadding(new Insets(5,5,5,5));
        Label lbSpielanleitung = new Label("Spielanleitung Text..............................");
        lbSpielanleitung.setPadding(new Insets(5,5,5,5));
        Button btnStartBack = new Button("Spielseite");
        btnStartBack.setPadding(new Insets(5,5,5,5));

        // Spielseite Bottom - Buttons
        Button btnStart = new Button("Start");
        btnStart.setPadding(new Insets(5,5,5,5));

            // Spielseite Bottom - HBox für Buttons
        HBox hBoxButtons = new HBox(btnStart, btnSpielanleitung);
        hBoxButtons.setPadding(new Insets(5,5,5,5));
        hBoxButtons.setSpacing(5);

        // Borderpane Spielseite - Hier wird Spielfenster zusammengebaut
        BorderPane bPane = new BorderPane();
        bPane.setTop(lbVierGewinntLogo1);
        bPane.setCenter(layout);
        bPane.setBottom(hBoxButtons);
        bPane.setLeft(vBoxL);

        // VBox Spielanleitung
        VBox vBoxSpielanleitung = new VBox(lbSpielanleitungTitel,lbSpielanleitung);
        //vBoxSpielanleitung.setStyle("-fx-background-color: red");

        // Spielanleitung Bottom - HBox für Buttons
        HBox hBoxButtonsAnleitung = new HBox(btnStartBack);
        hBoxButtonsAnleitung.setPadding(new Insets(5,5,5,5));
        hBoxButtonsAnleitung.setSpacing(5);

        // Borderpane Spielanleitung
        BorderPane bPaneAnleitung = new BorderPane();
        bPaneAnleitung.setTop(lbVierGewinntLogo2);
        bPaneAnleitung.setLeft(vBoxSpielanleitung);
        bPaneAnleitung.setBottom(hBoxButtonsAnleitung);
 //       bPaneAnleitung.setStyle("-fx-background-color: LightGoldenRodYellow ");
        Scene sceneAnleitung = new Scene(bPaneAnleitung, 850,530);

        btnSpielanleitung.setOnAction(e -> primaryStage.setScene(sceneAnleitung));

        Scene scene = new Scene(bPane, 850,530);
        btnStartBack.setOnAction(e -> primaryStage.setScene(scene));  // Position in Code wäre schöner wenn weiter oben..
        window.setScene(scene);
        window.show();


        // Start Spielfeld


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
                        if (!game.hasAWinner()) {
                            game.drop(finalJ);
                        }
                        if (game.hasAWinner()) {
                            gPaneWinner.setVisible(true);
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
        // Ende Spielfeld

        game.getPlayer().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                lbAktSpielerWert.setText("Player " + (newValue.intValue() + 1));
                lbWinnerWert.setText("Player " + (2-newValue.intValue()));
            }
        });

        game.getPlayerMoves().get(0).addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                lbAnzZuegePl1Wert.setText(newValue.toString());
                lbWinnerAnzZuegeWert.setText(newValue.toString());
            }
        });

        game.getPlayerMoves().get(1).addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                lbAnzZuegePl2Wert.setText(newValue.toString());
                lbWinnerAnzZuegeWert.setText(newValue.toString());
            }
        });


    }
}