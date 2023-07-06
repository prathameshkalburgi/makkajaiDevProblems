package org.problems.gameoflife;

public class Main {
    public static void main(String[] args) {
        GameOfLife game = new GameOfLife();

        // Input A - Block pattern
        game.addCell(1, 1, true);
        game.addCell(1, 2, true);
        game.addCell(2, 1, true);
        game.addCell(2, 2, true);

        System.out.println("Output A:");
        game.printCells();
        System.out.println();

        game.runGeneration();
        System.out.println("Output A after one generation:");
        game.printCells();
        System.out.println();

        game = new GameOfLife();

        // Input B - Boat pattern
        game.addCell(0, 1, true);
        game.addCell(1, 0, true);
        game.addCell(2, 1, true);
        game.addCell(0, 2, true);
        game.addCell(1, 2, true);

        System.out.println("Output B:");
        game.printCells();
        System.out.println();

        game.runGeneration();
        System.out.println("Output B after one generation:");
        game.printCells();
        System.out.println();

        // Repeat the same process for Input C and Input D
        // ...

    }
}
