package Application;

import javafx.concurrent.Task;

import java.util.Date;

public class Stoppuhr extends Task<String> {

    private String anz_m, anz_s, anz_hs;
    private Date startZeit, aktuelleZeit;
    private ConnectFour game;

    public Stoppuhr(ConnectFour game) {
        this.game = game;
    }

    @Override
    protected String call() throws Exception {
        startZeit = new Date();
        String diff = "";
        while (!game.hasAWinner()) {
            if (isCancelled() || game.hasAWinner()) {
                break;
            }
            Thread.sleep(500);
            aktuelleZeit = new Date();
            long diffZeit = (aktuelleZeit.getTime()-startZeit.getTime());
            long hs = (diffZeit % 1000) / 10;
            if (hs < 10)
                anz_hs = "0" + hs;
            else
                anz_hs = "" + hs;
            diffZeit = diffZeit / 1000;
            long s = diffZeit % 60;
            if (s < 10)
                anz_s = "0" + s;
            else
                anz_s = "" + s;
            diffZeit = diffZeit / 60;
            long m = diffZeit % 60;
            if (m < 10)
                anz_m = "0" + m;
            else
                anz_m = "" + m;
            diff = anz_m + "m :" + anz_s + "s";
            updateMessage(diff);
        }

        return diff;
    }
}
