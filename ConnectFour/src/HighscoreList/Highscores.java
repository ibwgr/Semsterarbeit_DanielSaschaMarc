package HighscoreList;

import Application.ConnectFour;
import Application.GUI;
import Application.Stoppuhr;
import javafx.beans.InvalidationListener;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class Highscores {
    private SimpleStringProperty name;
    private SimpleIntegerProperty playerMoves;
    private ObjectProperty<Time> spieldauer;
    private ObservableList<Highscores> data;

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public int getPlayerMoves() {
        return playerMoves.get();
    }

    public SimpleIntegerProperty playerMovesProperty() {
        return playerMoves;
    }

    public Time getSpieldauer() {
        return spieldauer.get();
    }

    public ObjectProperty<Time> spieldauerProperty() {
        return spieldauer;
    }

    public Highscores() {
    }

    public Highscores (String name, int playerMoves, Time spieldauer) {
        this.name = new SimpleStringProperty(name);
        this.playerMoves = new SimpleIntegerProperty(playerMoves);
        this.spieldauer = new SimpleObjectProperty<Time>(spieldauer);
    }

    public ObservableList<Highscores> getList() {
        Connection conn;
        data = FXCollections.observableArrayList();
        Statement st;
        ResultSet rs;

        try {
            Connection myConn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/ConnectFour", "root", "MariaDBVinkja");
            st = myConn.createStatement();

            String recordQuery = ("Select * from highscores");
            rs = st.executeQuery(recordQuery);
            while(rs.next()){
                String name = rs.getString("name");
                int playerMoves = rs.getInt("playerMoves");
                Time spieldauer = rs.getTime("spieldauer");
                data.add(new Highscores(name, playerMoves, spieldauer));
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return data;
    }

    public void addPlayer(ConnectFour game, String name) {
        try {
            Connection myConn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/ConnectFour", "root", "MariaDBVinkja");
            Statement myStmt = myConn.createStatement();

            int spielzuege = game.getPlayerMoves().get(1-game.getPlayer().getValue()).getValue();
            int spieldauerH = Integer.parseInt(GUI.getStoppuhr().getAnz_hs());
            int spieldauerM = Integer.parseInt(GUI.getStoppuhr().getAnz_m());
            int spieldauerS = Integer.parseInt(GUI.getStoppuhr().getAnz_s());
            Time spieldauer = new Time(spieldauerH,spieldauerM,spieldauerS);

            System.out.println(spielzuege);

            String value = " values ('" + name + "', " + spielzuege + ", '" + spieldauer + "')";

            String sql = "insert into highscores "
                    + " (name, playerMoves, spieldauer)"
                    + value;

            myStmt.executeUpdate(sql);

            System.out.println("Done");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
