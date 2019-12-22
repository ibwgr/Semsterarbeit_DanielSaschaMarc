package Application;

import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;

public class ConnectFour {
    private static final String[] FIELD = {"Yellow", "Red"};
    private static int columns, rows;
    private static ArrayList<ArrayList<SimpleStringProperty>> grid = new ArrayList<>();
    private static int lastCol = -1, lastRow = -1;
    private int player = 0;


    public ConnectFour(int columns, int rows) {
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

    public static ArrayList<ArrayList<SimpleStringProperty>> getGrid() {
        return grid;
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
                grid.get(lastRow = row).get(lastCol = col).set(FIELD[player]);
                player = 1 - player;
                return;
            }
        }
        System.out.println("Column " + col + " is full.");
    }

}