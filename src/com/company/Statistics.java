package com.company;

public class Statistics {
    private static float xEfficiency = 0f;// кпд игрока = размер поля / кол-во ходов для победы
    private static float yEfficiency = 0f;

    private static int numberOfWinX = 0;
    private static int numberOfWinY = 0;

    public static void setStats(int XStep, int YStep, int mapSize, boolean isXWin){
        if (isXWin) {++numberOfWinX; xEfficiency += mapSize / (XStep * 1.0);}
        else {++numberOfWinY; yEfficiency += mapSize / (YStep * 1.0);}
    }

    public static void getStats(){
        System.out.println("\nEfficiency of x : " + xEfficiency / numberOfWinX);
        System.out.println("Efficiency of y : " + yEfficiency / numberOfWinY);
        System.out.println("number of X's wins : " + numberOfWinX);
        System.out.println("number of Y's wins : " + numberOfWinY + "\n");
    }
}
