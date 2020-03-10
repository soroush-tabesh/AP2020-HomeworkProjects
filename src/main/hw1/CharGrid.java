package main.hw1;

public class CharGrid {
    private char[][] grid;

    /**
     * Constructs a new main.hw1.CharGrid with the given grid.
     * Does not make a copy.
     *
     * @param grid
     */
    public CharGrid(char[][] grid) {
        this.grid = grid;
    }

    /**
     * Returns the area for the given char in the grid.
     *
     * @param ch char
     * @return area for given char
     */
    public int charArea(char ch) {
        int minI = grid.length, minJ = grid[0].length, maxI = 0, maxJ = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == ch) {
                    minI = Math.min(minI, i);
                    minJ = Math.min(minJ, j);
                    maxI = Math.max(maxI, i);
                    maxJ = Math.max(maxJ, j);
                }
            }
        }
        return minI == grid.length ? 0 : (maxI - minI + 1) * (maxJ - minJ + 1);
    }

    /**
     * Returns the count of '+' figures in the grid
     *
     * @return number of + in grid
     */
    public int countPlus() {
        int cnt = 0;
        for (int i = 1; i < grid.length - 1; i++) {
            for (int j = 1; j < grid[i].length - 1; j++) {
                if (grid[i][j] == grid[i][j - 1]
                        && grid[i][j] == grid[i - 1][j]
                        && grid[i][j] == grid[i][j + 1]
                        && grid[i][j] == grid[i + 1][j]) {
                    ++cnt;
                }
            }
        }
        return cnt;
    }

}
