package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Game {
    private IPlayer firstPlayer;
    private IPlayer secondPlayer;

    private int countXStep = 0;
    private int countYStep = 0;

    private int size = getSizeOfMap();
    private int[] map = new int[size * size];

    private boolean isGame = true;
    private boolean isXWin = false;

    private BufferedReader reader;

    public Game(){
        firstPlayer = null;
        secondPlayer = null;
    }

    public Game(IPlayer firstPlayer, IPlayer secondPlayer){
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
    }

    public void start(){
        while (isGame){
            if (isDraw()) {
                printMap();
                System.out.println("Draw");
                dataCleaning();
                return;
            }
            printMap();
            isGame = stepAndCheck(firstPlayer);
            countXStep++;
            if (!isGame) break;
            printMap();
            isGame = stepAndCheck(secondPlayer);
            countYStep++;
        }
        Statistics.setStats(countXStep, countYStep, size, isXWin);
        dataCleaning();
    }
    private void printMap(){
        System.out.println("--------------");
        for (int i = 0; i < map.length; i++) {
            if (i % size == 0 && i != 0) System.out.println();

            if (map[i] == 0) System.out.print(" . ");
            if (map[i] == 1) System.out.print(" X ");
            if (map[i] == 2) System.out.print(" O ");
        }
        System.out.println();
    }

    private void dataCleaning(){ // зачистка данных перед новой игрой
        countYStep = countXStep = 0;
        isGame = true;
        Arrays.fill(map,0);
    }

    public int mainMenu(){ // возвращает выбранный нормер из меню
        int input;
        reader = new BufferedReader(new InputStreamReader(System.in));
        while (true){
            System.out.println("1. Start");
            System.out.println("2. Show stats");
            System.out.println("3. Exit");

            try {
                input = Integer.parseInt(reader.readLine());
                if (input > 0 && input < 4) return input;
            } catch (IOException e) {
                System.out.println("Incorrect input");
            }catch (NumberFormatException ex){
                System.out.println("Enter correct number");
            }
        }

    }
    private int getSizeOfMap(){
        System.out.print("Enter size of the map : ");
        reader = new BufferedReader(new InputStreamReader(System.in));
        int size;
        while (true){
            try {
                size = Integer.parseInt(reader.readLine());
                if (size < 1) throw new NumberFormatException();
                return size;
            }catch (NumberFormatException e){
                System.out.println("Please enter the number from 1 to inf ");
            }catch (IOException e){
                System.out.println("Something wrong!");
            }
        }
    }

    private boolean stepAndCheck(IPlayer player){ // ход игрока (X/Y) + проверка на победу
        int fieldNumber = player.getNumber(map);
        map[fieldNumber] = player.getMark();
        if (isGameOver(fieldNumber)) {
            printMap();
            isXWin = player.getMark() == 1;
            System.out.println((isXWin ? "X":"O") + " is win");
            return false;
        }
        return true;
    }
    private boolean isGameOver(final int numberOfField){
        int row = numberOfField - numberOfField % size;
        for (int i = 1; i < size; i++) { // проверка по строке
            if (map[row] != map[row + i]) break;
            if (i == size - 1 && map[row] == map[row + i]) return true;
        }
        int colon = numberOfField % size;
        for (int i = 0; i < map.length; i+=size) { // проверка по колонке
            if (map[colon] != map[colon + i]) break;
            if (i == map.length - size && map[colon] == map[colon + i]) return true;
        }
        if (numberOfField % (size + 1) == 0){ // т.е левая диагональ
            for (int i = 0; i < map.length; i+= size + 1) {
                if (map[numberOfField] != map[i]) break;
                if (i ==map.length - 1 && map[numberOfField] == map[i]) return true;
            }
        }
        for (int i = size - 1; i <= map.length - size ; i+= size -1) { // проверка правой диагонали
            if (map[numberOfField] != map[i]) return false;
            if (i == map.length - size && map[numberOfField] == map[i]) return true;
        }
        return false;
    }
    public boolean isDraw() { // проверка на ничью
        for (int n : map) if (n==0) return false;
        return true;
    }
}
