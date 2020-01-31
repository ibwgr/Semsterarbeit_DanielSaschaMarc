package Application;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class GUI extends Application{
    private Stage window;
    private GridPane gameBoard;
    private BorderPane bPane;
    private ConnectFour game;
    private static GridPane gPaneWinner;
    private Scene gameScene;

    public static GridPane getgPaneWinner() {
        return gPaneWinner;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        window = primaryStage;
        window.setTitle("4 Gewinnt");
        window.setMaxHeight(560);
        window.setMaxWidth(870);

        // Logo Top
        Label lbVierGewinntLogo1 = new Label("4-GEWINNT");
            lbVierGewinntLogo1.getStyleClass().add("lbviergewinntlogo1");
        Label lbVierGewinntLogo2 = new Label("4-GEWINNT");
            lbVierGewinntLogo2.getStyleClass().add("lbviergewinntlogo2");

        // Spielanleitung
        Button btnSpielanleitung = new Button("Spielanleitung");
            btnSpielanleitung.getStyleClass().add("btn");
        Label lbSpielanleitungTitel = new Label("\n" + "Spielanleitung: ");
            lbSpielanleitungTitel.getStyleClass().add("lbspielanleitungtitel");

            String spielanleitung = "";
            File anleitung = new File("src\\resources\\Spielanleitung.txt");
        try (BufferedReader reader = new BufferedReader(new FileReader(anleitung))) {
            String line;
            while ((line = reader.readLine()) != null)
                spielanleitung += line;

        } catch (IOException e) {
            e.printStackTrace();
        }
        Text lbSpielanleitung = new Text(spielanleitung);
            lbSpielanleitung.getStyleClass().add("lbspielanleitung");
            lbSpielanleitung.setWrappingWidth(800);
        Button btnStartBack = new Button("Spielseite");
            btnStartBack.getStyleClass().add("btn");

        // Spielseite Bottom - Buttons
        Button btnStart = new Button("Start");
            btnStart.getStyleClass().add("btn");
            btnStart.setOnAction(event ->
                newGame());

        // Spielseite Bottom - HBox für Buttons
        HBox hBoxButtons = new HBox(btnStart, btnSpielanleitung);
            hBoxButtons.getStyleClass().add("hboxbuttons");

        // Borderpane Spielseite - Hier wird Spielfenster zusammengebaut
        bPane = new BorderPane();
            bPane.setTop(lbVierGewinntLogo1);
            bPane.setBottom(hBoxButtons);
            bPane.getStyleClass().add("bpane");

        // VBox Spielanleitung
        VBox vBoxSpielanleitung = new VBox(lbSpielanleitungTitel,lbSpielanleitung);
             vBoxSpielanleitung.getStyleClass().add("vboxspielanleitung");

        // Spielanleitung Bottom - HBox für Buttons
        HBox hBoxButtonsAnleitung = new HBox(btnStartBack);
            hBoxButtonsAnleitung.getStyleClass().add("hboxbuttonsanleitung");

        // Borderpane Spielanleitung
        BorderPane bPaneAnleitung = new BorderPane();
            bPaneAnleitung.setTop(lbVierGewinntLogo2);
            bPaneAnleitung.setLeft(vBoxSpielanleitung);
            bPaneAnleitung.setBottom(hBoxButtonsAnleitung);
            bPaneAnleitung.getStyleClass().add("bpaneanleitung");

        Scene sceneAnleitung = new Scene(bPaneAnleitung, 850,530);
            sceneAnleitung.getStylesheets().add("resources/styles.css");

        btnSpielanleitung.setOnAction(e -> primaryStage.setScene(sceneAnleitung));

        gameScene = new Scene(bPane, 850,530);
            gameScene.getStylesheets().add("resources/styles.css");
            btnStartBack.setOnAction(e -> primaryStage.setScene(gameScene));
            primaryStage.setResizable(false);

        window.setScene(gameScene);
        window.show();

        newGame();
    }

    private void newGame() {

        // Setup Game and Gameboard
        game = new ConnectFour(8,6);
        gameBoard = new GameBoard(game);

        // Left - Winnerinfo
        Label lbWinner = new Label("Sieger:");
            lbWinner.getStyleClass().add("lbwinner");
        Label lbWinnerWert = new Label("");
            lbWinnerWert.getStyleClass().add("lbwertwinner");
        Label lbWinnerAnzZuege = new Label("Anzahl Spielzüge:");
            lbWinnerAnzZuege.getStyleClass().add("lbwinner");
        Label lbWinnerAnzZuegeWert = new Label("");
            lbWinnerAnzZuegeWert.getStyleClass().add("lbwertwinner");

        // Left - Gameinfo
        Label lbWinnerSpieldauer = new Label("Spieldauer: ");
            lbWinnerSpieldauer.getStyleClass().add("lbwinner");
        Label lbWinnerSpieldauerWert = new Label("3min 15sek");
            lbWinnerSpieldauerWert.getStyleClass().add("lbwertwinner");
        Label lbAktSpieler = new Label("Aktueller Spieler: ");
            lbAktSpieler.getStyleClass().add("lb");
        Label lbAktSpielerWert = new Label("Player 1");
            lbAktSpielerWert.getStyleClass().add("lbwert");
        Label lbSpieldauer = new Label("Spieldauer: ");
            lbSpieldauer.getStyleClass().add("lb");
        Label lbSpieldauerWert = new Label("00m : 00s");
            lbSpieldauerWert.getStyleClass().add("lbwert");

        Service s = new Service() {
            @Override
            protected Task createTask() {
                Stoppuhr stoppuhr = new Stoppuhr(game);
                lbSpieldauerWert.textProperty().bind(stoppuhr.messageProperty());
                lbWinnerSpieldauerWert.textProperty().bind(stoppuhr.messageProperty());
                return stoppuhr;
            }
        };
        s.start();

        Label lbAnzZuegePl1 = new Label("Anzahl Spielzüge Player 1: ");
            lbAnzZuegePl1.getStyleClass().add("lb");
        Label lbAnzZuegePl1Wert = new Label("0");
            lbAnzZuegePl1Wert.getStyleClass().add("lbwert");
        Label lbAnzZuegePl2 = new Label("Anzahl Spielzüge Player 2: ");
            lbAnzZuegePl2.getStyleClass().add("lb");
        Label lbAnzZuegePl2Wert = new Label("0");
            lbAnzZuegePl2Wert.getStyleClass().add("lbwert");
        Label lbAbstand = new Label("");
            lbAbstand.getStyleClass().add("lb");

        // Gridpane Gameinfo
        GridPane gPaneSpielInfo = new GridPane();
        gPaneSpielInfo.setPrefWidth(260);
        gPaneSpielInfo.add(lbAktSpieler,0,0);
        gPaneSpielInfo.add(lbAktSpielerWert,1,0);
        gPaneSpielInfo.add(lbSpieldauer,0,1);
        gPaneSpielInfo.add(lbSpieldauerWert,1,1);
        gPaneSpielInfo.add(lbAnzZuegePl1,0,2);
        gPaneSpielInfo.add(lbAnzZuegePl1Wert,1,2);
        gPaneSpielInfo.add(lbAnzZuegePl2,0,3);
        gPaneSpielInfo.add(lbAnzZuegePl2Wert,1,3);
        gPaneSpielInfo.add(lbAbstand,0,4);

        // Gridpane Winner
        gPaneWinner = new GridPane();
        gPaneWinner.setVisible(false);
        gPaneWinner.getStyleClass().add("gpanewinner");
        gPaneWinner.add(lbWinner,0,0);
        gPaneWinner.add(lbWinnerWert,1,0);
        gPaneWinner.add(lbWinnerAnzZuege,0,1);
        gPaneWinner.add(lbWinnerAnzZuegeWert,1,1);
        gPaneWinner.add(lbWinnerSpieldauer,0,2);
        gPaneWinner.add(lbWinnerSpieldauerWert,1,2);

        // Left - VBox für Spielinfos
        VBox vBoxL = new VBox(gPaneSpielInfo, gPaneWinner);  //lbAktSpieler,lbSpieldauer,lbAnzZuegePl1,lbAnzZuegePl2
            vBoxL.getStyleClass().add("vboxl");

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

        bPane.setLeft(vBoxL);
        bPane.setCenter(gameBoard);
        window.setScene(gameScene);
    }
}