package com.company;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/*

     0 1 2   0  1  2  3   <-- Номера ячеек. Не придумал как более удобнее сделать.
     3 4 5   4  5  6  7       Можно, конечно, использовать эти цифры вместо '.', но
     6 7 8   8  9 10 11       будет не совсем понятно куда тыкать.
            12 13 14 15  и т.д
*/

public class Main {
    static BufferedReader reader;

    public static void main(String[] args) {
        Game game;
        try {
            game = new Game(getPlayer('X',"First"),getPlayer('O', "Second"));
        }catch (IOException | NumberFormatException ex){
            System.out.println(ex.getMessage());
            return;
        }
        while (true) {
            switch(game.mainMenu()){
                case 1:
                    game.start();
                    break;
                case 2:
                    Statistics.getStats();
                    break;
                case 3:
                    return;
                default:
                    break;
            }
        }
    }
    private static IPlayer getPlayer(char mark, String playerNumber) throws IOException{
        System.out.println(playerNumber + " player is Bot?");
        reader = new BufferedReader(new InputStreamReader(System.in));
        return Integer.parseInt(reader.readLine()) == 1 ?
                new Bot(mark):
                new Player(mark);
    }
}
