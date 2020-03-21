package com.company;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Player implements IPlayer {
    private char mark;

    public Player() {
        mark = 'X';
    }
    public Player(char mark){
        this.mark = mark;
    }
    public int getMark(){
        return mark == 'X' ? 1:2;
    }

    public int getNumber(final int[] map){
        System.out.print("Mark " + mark + " : ");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (true){
            try {
                int fieldNumber = Integer.parseInt(reader.readLine());
                // проверка на границы / свобоной клетки
                if (fieldNumber >= 0 && fieldNumber < map.length && map[fieldNumber]==0){
                    return fieldNumber;
                }
                System.out.println("Choose free cell and enter its number");
            } catch (NumberFormatException e) {
                System.out.println("Please enter the number");
            } catch (IOException e) {
                System.out.println("Something wrong");
            }
        }
    }
}
