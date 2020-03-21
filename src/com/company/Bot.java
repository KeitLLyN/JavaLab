package com.company;

import java.util.Random;

public class Bot implements IPlayer {
    private char mark;

    public Bot(){
        mark = 'O';
    }

    public Bot(char mark){
        this.mark = mark;
    }

    public int getMark(){
        return mark == 'X' ? 1:2;
    }

    public int getNumber(final int[] map){
        int fieldNumber;
        do{
            Random random = new Random();
            fieldNumber = random.nextInt(map.length);
        }while (map[fieldNumber] != 0);
        return fieldNumber;
    }

}
