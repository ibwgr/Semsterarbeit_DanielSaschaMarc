package Application;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;

public class ConnectFour {
<<<<<<< HEAD
=======

>>>>>>> featureNada
    private final String[] Colors = {"Red", "Yellow"};
    private int columns, rows;
    private ArrayList<ArrayList<SimpleStringProperty>> grid;
    private int lastCol, lastRow;
    private SimpleIntegerProperty player;
    private ArrayList<SimpleIntegerProperty> playerMoves;

    public ConnectFour(int columns, int rows) {
        player = new SimpleIntegerProperty(0);
        playerMoves = new ArrayList<SimpleIntegerProperty>() {
            {
                add(new SimpleIntegerProperty(0));
                add(new SimpleIntegerProperty(0));
            }
        };
        lastCol = -1;
        lastRow = -1;
        grid = new ArrayList<>();
        this.columns = columns;
        this.rows = rows;

        for (int row = 0; row < rows; row++) {
            grid.add(row, new ArrayList<>());
        }
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                grid.get(row).add(column, new SimpleStringProperty("White"));
            }
        }
    }

    public ArrayList<ArrayList<SimpleStringProperty>> getGrid() {
        return this.grid;
    }

    public SimpleIntegerProperty getPlayer() {
        return this.player;
    }

    public ArrayList<SimpleIntegerProperty> getPlayerMoves() {
        return this.playerMoves;
    }

    public String horizontalAddition() {
        String horizontal = "";
        for (int column = 0; column < columns; column++) {
            horizontal += grid.get(lastRow).get(column).getValue();
        }
        return horizontal;
    }

    public String verticalAddition() {
        String vertical = "";
        for (int row = 0; row < rows; row++) {
            vertical += grid.get(row).get(lastCol).getValue();
        }
        return vertical;
    }

    public String slashDiagonalAddition() {
        // move slashdiagonal to the left or lower boarder
        int c = lastCol;
        int r = lastRow;
        while (r != rows - 1 && c != 0) {
            r++;
            c--;
        }
        //Add the Strings by moving slashdiagnoal
        String slashdiagonal = "";
        while (r >= 0 && c < columns) {
            slashdiagonal += grid.get(r).get(c).getValue();
            c++;
            r--;
        }
        return slashdiagonal;
    }

    public String backslashDiagonal() {
        // move backslashdiagonal to the right or lower boarder
        int c = lastCol;
        int r = lastRow;
        while (r != rows - 1 && c != columns -1) {
            r++;
            c++;
        }
        //Add the Strings by moving backslashdiagnoal
        String slashdiagonal = "";
        while (r >= 0 && c >= 0) {
            slashdiagonal += grid.get(r).get(c).getValue();
            c--;
            r--;
        }
        return slashdiagonal;
    }

    public boolean hasAWinner() {
        if(lastCol == -1) return false;
        String sym = grid.get(lastRow).get(lastCol).getValue();
        String forConnect = sym + sym + sym + sym;

        return horizontalAddition().contains(forConnect) ||
                verticalAddition().contains(forConnect) ||
                slashDiagonalAddition().contains(forConnect) ||
                backslashDiagonal().contains(forConnect);
    }

    public void drop(int col) {

        for (int row = rows - 1; row >= 0; row--) {

            if (grid.get(row).get(col).getValue() == "White") {
                grid.get(lastRow = row).get(lastCol = col).set(Colors[player.get()]);
                playerMoves.get(player.get()).set(playerMoves.get(player.get()).get() + 1);
                player.set(1 - player.get());
                return;
            }
        }
        System.out.println("Column " + col + " is full.");
    }

}