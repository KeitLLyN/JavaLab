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
    static int countXStep = 0;
    static int countYStep = 0;

    static int size = getSizeOfMap();
    static int[] map = new int[size * size];

    static boolean isGame = true;
    static boolean isBot = false;
    static boolean isXWin = false;

    static Player firstPlayer = new Player();
    static Player secondPlayer;

    static BufferedReader reader;

    public static void main(String[] args) {
        while (true) {
            switch (mainMenu()) {
                case 1:
                    isBot = true;
                    secondPlayer = new Player(isBot, 'O');
                    start();
                    break;

                case 2:
                    isBot = false;
                    secondPlayer = new Player(isBot, 'O');
                    start();
                    break;

                case 3:
                    Statistics.getStats();
                    break;

                case 4:
                    System.out.println("\nThanks for playing ! :)");
                    return;

                default:
                    break;
            }
        }
    }

    private static void start(){
        while (isGame){
            printMap();
            isGame = stepAndCheck(firstPlayer);
            countXStep++;
            if (!isGame) break;
            if (isDraw()) {
                printMap();
                System.out.println("Draw");
                break;
            }
            if(!isBot) printMap();
            isGame = stepAndCheck(secondPlayer);
            countYStep++;
        }
        Statistics.setStats(countXStep, countYStep, size, isXWin);
        dataCleaning();
    }
    private static void printMap(){
        for (int i = 0; i < map.length; i++) {
            if (i % size == 0 && i != 0) System.out.println();

            if (map[i] == 0) System.out.print(" . ");
            if (map[i] == 1) System.out.print(" X ");
            if (map[i] == 2) System.out.print(" O ");
        }
        System.out.println();
    }
    private static void dataCleaning(){ // зачистка данных перед новой игрой
        countYStep = countXStep = 0;
        isGame = true;
        Arrays.fill(map,0);
    }

    private static int mainMenu(){ // возвращает выбранный нормер из меню
        int input;
        reader = new BufferedReader(new InputStreamReader(System.in));
        while (true){
            System.out.println("1. Start new game with AI");
            System.out.println("2. Start new game with 2nd player");
            System.out.println("3. Show stats");
            System.out.println("4. Exit");
            try {
                input = Integer.parseInt(reader.readLine());
                if (input > 0 && input < 5) return input;
            } catch (IOException e) {
                System.out.println("Incorrect input");
            }catch (NumberFormatException ex){
                System.out.println("Enter correct number");
            }
        }

    }
    private static int getSizeOfMap(){
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

    private static boolean stepAndCheck(Player player){ // ход игрока (X/Y) + проверка на победу
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
    private static boolean isGameOver(final int numberOfField){
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
    private static boolean isDraw() { // проверка на ничью
        for (int n : map) if (n==0) return false;
        return true;
    }


}
