package org.problems.gameoflife;

import java.util.*;


public class Main {
    public static void main(String[] args) {
        GameOfLife game = new GameOfLife();

        // Example: Block pattern
        System.out.println("input 1 :");
        game.addLiveCell(1, 1);
        game.addLiveCell(1, 2);
        game.addLiveCell(2, 1);
        game.addLiveCell(2, 2);

        processData(game, 1);

        System.out.println("--------------------------------------------------------------------------------");

        GameOfLife game2 = new GameOfLife();
        System.out.println("input 2 :");
        game2.addLiveCell(0, 1);
        game2.addLiveCell(1, 0);
        game2.addLiveCell(2, 1);
        game2.addLiveCell(0, 2);
        game2.addLiveCell(1, 2);
        processData(game2, 2);

        System.out.println("--------------------------------------------------------------------------------");


        GameOfLife game3 = new GameOfLife();
        System.out.println("input 3 :");
        game3.addLiveCell(1, 1);
        game3.addLiveCell(1, 0);
        game3.addLiveCell(1, 2);
        processData(game3, 3);
        System.out.println("--------------------------------------------------------------------------------");

        GameOfLife game4 = new GameOfLife();
        System.out.println("input 4 :");
        game4.addLiveCell(1, 1);
        game4.addLiveCell(1, 2);
        game4.addLiveCell(1, 3);
        game4.addLiveCell(2, 2);
        game4.addLiveCell(2, 3);
        game4.addLiveCell(2, 4);


        processData(game4, 4);

    }

    private static void processData(GameOfLife game, int outputnumber) {
        game.simulateGeneration();

        Set<Cell> nextGeneration = game.getLiveCells();
        List<String> data = new ArrayList<>();
        System.out.println("Output " + outputnumber + ":");
        for (Cell cell : nextGeneration) {
            data.add(cell.getX() + "," + cell.getY());
        }
        Collections.sort(data);
        data.forEach(System.out::println);
    }
}
