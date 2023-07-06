package org.problems.gameoflife;

import java.util.HashSet;
import java.util.Set;

class GameOfLife {
    private Set<Cell> liveCells;

    public GameOfLife() {
        liveCells = new HashSet<>();
    }

    public void addLiveCell(int x, int y) {
        System.out.println(x + "," + y);
        liveCells.add(new Cell(x, y));
    }

    public void simulateGeneration() {
        Set<Cell> nextGeneration = new HashSet<>();
        Set<Cell> candidates = new HashSet<>();

        for (Cell cell : liveCells) {
            int liveNeighbors = countLiveNeighbors(cell);
            if (liveNeighbors == 2 || liveNeighbors == 3)
                nextGeneration.add(cell);

            getCandidateCells(cell, candidates);
        }

        for (Cell candidate : candidates) {
            int liveNeighbors = countLiveNeighbors(candidate);
            if (liveNeighbors == 3)
                nextGeneration.add(candidate);
        }

        liveCells.clear();
        liveCells.addAll(nextGeneration);
    }

    private int countLiveNeighbors(Cell cell) {
        int count = 0;
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (dx == 0 && dy == 0)
                    continue;

                Cell neighbor = new Cell(cell.getX() + dx, cell.getY() + dy);
                if (liveCells.contains(neighbor))
                    count++;
            }
        }
        return count;
    }

    private void getCandidateCells(Cell cell, Set<Cell> candidates) {
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (dx == 0 && dy == 0)
                    continue;

                Cell candidate = new Cell(cell.getX() + dx, cell.getY() + dy);
                if (!liveCells.contains(candidate))
                    candidates.add(candidate);
            }
        }
    }

    public Set<Cell> getLiveCells() {
        return liveCells;
    }
}