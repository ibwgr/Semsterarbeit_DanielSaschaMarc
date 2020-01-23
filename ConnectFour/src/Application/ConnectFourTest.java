import javafx.beans.property.SimpleStringProperty;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ConnectFourTest {


    @Test
    public void ConnectFourcreatesGridwithStringsWhite (){

        ConnectFour cf = new ConnectFour(8,6);
        SimpleStringProperty s = new SimpleStringProperty("White");

        for (int i = 0; i < cf.getGrid().size(); i++) {
            for (int j = 0; j < cf.getGrid().get(i).size(); j++) {
                Assertions.assertEquals(s.getValue(), cf.getGrid().get(i).get(j).getValue());
            }
        }
    }

    // Wenn alle Tests laufen failed Test Nr. 1, separat jedoch in Ordnung...

    @Test
    public void horizontalAdditionReturnsExpectedString (){
        ConnectFour cf = new ConnectFour(8,6);
        String result = "YellowWhiteRedWhiteYellowWhiteRedWhite";
        cf.drop(2);
        cf.drop(0);
        cf.drop(6);
        cf.drop(4);
        Assertions.assertEquals(result,cf.horizontalAddition());

    }

    @Test
    public void verticalAdditionReturnsExpectedString () {
        ConnectFour cf = new ConnectFour(8,6);
        String result = "WhiteWhiteYellowRedYellowRed";
        cf.drop(0);
        cf.drop(0);
        cf.drop(0);
        cf.drop(0);
        Assertions.assertEquals(result,cf.verticalAddition());

    }

    @Test
    public void slashDiagonalAdditionReturnsExpectedString (){
        ConnectFour cf = new ConnectFour(8,6);
        String result = "RedYellowRedRedWhiteWhite";

        for (int i = 0; i <= 2; i++) {
            for (int j = 0; j <= 7; j++){
                cf.drop(j);
            }
        }
        cf.drop(3);

        Assertions.assertEquals(result,cf.slashDiagonalAddition());


    }

    @Test
    public void backslashAdditionReturnsExpectedString (){
        ConnectFour cf = new ConnectFour(8,6);
        String result = "YellowRedYellowRedWhiteWhite";

        for (int i = 0; i <= 2; i++) {
            for (int j = 0; j <= 7; j++){
                cf.drop(j);
            }
        }
        cf.drop(2);

        Assertions.assertEquals(result,cf.backslashDiagonal());

    }

    @Test
    public void hasAWinnerReturnsTrueifTestSlashDiagonal (){
        ConnectFour cf = new ConnectFour(8,6);
        String result = "RedRedRedRed";

        cf.drop(0);
        cf.hasAWinner();

//        Assertions.assertEquals(result, cf.getForConnect());
//        System.out.println(cf.getForConnect());
    }


    @Test
    public void hasWinnerReturnsFalse (){
        ConnectFour cf = new ConnectFour(8,6);

        cf.drop(0);
        cf.hasAWinner();
        System.out.println(cf.hasAWinner());

        Assertions.assertFalse(cf.hasAWinner());

    }

    @Test
    public void hasWinnerReturnsTrueForHorizontal (){
        ConnectFour cf = new ConnectFour(8,6);
        Boolean result = true;


        for (int i = 0; i<=3; i++){
            cf.drop(i);
            cf.drop(i);
        }

        Assertions.assertTrue(cf.hasAWinner());

    }

    @Test
    public void hasWinnerReturnsTrueForVertikal (){
        ConnectFour cf = new ConnectFour(8,6);
        Boolean result = true;


        for (int i = 0; i <= 3; i++) {
            cf.drop(0);
            cf.drop(1);
        }

        Assertions.assertTrue(cf.hasAWinner());

    }
    @Test
    public void hasWinnerReturnsTrueForSlashDiagonal (){
        ConnectFour cf = new ConnectFour(8,6);

        for (int i = 0; i <= 3; i++) {
            cf.drop(i);
            cf.drop(i);
            cf.drop(i);
        }
        cf.drop(3);

        Assertions.assertTrue(cf.hasAWinner());

    }

    @Test
    public void hasWinnerReturnsTrueForBackSlashDiagonal (){
        ConnectFour cf = new ConnectFour(8,6);

        for (int j = 0; j <= 3; j++) {
            cf.drop(j);
            cf.drop(j);
            cf.drop(j);
        }
        cf.drop(1);
        cf.drop(0);

        Assertions.assertTrue(cf.hasAWinner());

    }

    @Test
    public void checkIfFirstDropColorisCorrect(){
        String result = "Red";
        ConnectFour cf = new ConnectFour(8,6);

        cf.drop(5);

        Assertions.assertEquals(result, cf.getGrid().get(5).get(5).getValue());

    }

    @Test
    public void checkIfNotFirstDropColorisCorrect(){
        String result = "Yellow";
        ConnectFour cf = new ConnectFour(8,6);

        for (int i = 0; i <= 7 ; i++){
            cf.drop(i);
            cf.drop(i);
            cf.drop(i);
        }

        cf.drop(4);
        cf.drop(4);

        Assertions.assertEquals(result, cf.getGrid().get(1).get(4).getValue());
    }

}