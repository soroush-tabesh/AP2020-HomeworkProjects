package main.java.ir.soroushtabesh.ap2020.hw1;

import java.util.ArrayList;
import java.util.Scanner;

public class MarPelleh {
    public static void main(String[] args) {
        //create a Random object by a seed read from console
        Scanner scanner = new Scanner(System.in);
        //Create a marPelleh with 2 players and get those players
        Board board = new Board(2);
        Player player1 = board.getPlayers().get(0);
        Player player2 = board.getPlayers().get(1);

        //movements and printing results
        for (int i = 0; i < 25; i++) {
            board.movePlayer(0, scanner.nextInt(), scanner.nextInt());
            board.movePlayer(1, scanner.nextInt(), scanner.nextInt());

            System.out.println("Player1 is in " + player1.getCurrentCell());
            System.out.println("Player2 is in " + player2.getCurrentCell());
            System.out.println("Total score of player1 is " + player1.getTotalScore());
            System.out.println("Total score of player2 is " + player2.getTotalScore());
            System.out.println("ScoreFromLadder_Snake of player1 is " +
                    player1.getScoreFromLadder_Snake());
            System.out.println("ScoreFromLadder_Snake of player2 is " +
                    player2.getScoreFromLadder_Snake());
            System.out.println("Player1 is better than player2 : " + P1greaterThanP2(player1, player2));
        }
    }

    public static boolean P1greaterThanP2(Player player1, Player player2) {
        int result = player1.compareTo(player2);
        return result > 0;
    }
}

class Board {
    private Cell[][] cells;
    private ArrayList<Player> players;
    private ArrayList<Transmitter> transmitters;

    public Board(int numberOfPlayers) {
        initCells();
        initTransmitters();
        updateTransmittersOfCells();
        initPlayers(numberOfPlayers);
    }

    //initialize methods
    private void initCells() {
        cells = new Cell[10][10];
        for (int i = 0; i < 10; i++)
            for (int j = 0; j < 10; j++)
                cells[i][j] = new Cell(i, j);
    }

    private void initTransmitters() {
        /*
        Notice that you must use cells of board class and you
        can't create new cell in your code.You can only use methods there
        exist.

        Create five ladders here and add them to "transmitters" list
        1.A badLadder from (0,3) to (5,6)
        2.A badLadder from (3,4) to (7,8)
        3.A badLadder from (5,8) to (9,4)
        4.A goodLadder from (0,9) to (6,9)
        5.A goodLadder from (4,6) to (8,5)

        Also create five snakes here and add them to "transmitters" list
        1.A badSnake from (7,3) to (0,4)
        2.A badSnake from (8,8) to (5,5)
        3.A goodSnake from (9,3) to (1,1)
        4.A goodSnake from (4,4) to (2,9)
        5.A goodSnake from (8,6) to (5,7)
         */
        transmitters = new ArrayList<>();

        transmitters.add(new BadLadder(getCell(0, 3), getCell(5, 6)));
        transmitters.add(new BadLadder(getCell(3, 4), getCell(7, 8)));
        transmitters.add(new BadLadder(getCell(5, 8), getCell(9, 4)));
        transmitters.add(new GoodLadder(getCell(0, 9), getCell(6, 9)));
        transmitters.add(new GoodLadder(getCell(4, 6), getCell(8, 5)));

        transmitters.add(new BadSnake(getCell(7, 3), getCell(0, 4)));
        transmitters.add(new BadSnake(getCell(8, 8), getCell(5, 5)));
        transmitters.add(new GoodSnake(getCell(9, 3), getCell(1, 1)));
        transmitters.add(new GoodSnake(getCell(4, 4), getCell(2, 9)));
        transmitters.add(new GoodSnake(getCell(8, 6), getCell(5, 7)));
    }

    private void updateTransmittersOfCells() {
        for (Transmitter transmitter : transmitters)
            transmitter.getFirstCell().setTransmitter(transmitter);
    }

    private void initPlayers(int numberOfPlayers) {
        players = new ArrayList<>();
        for (int i = 0; i < numberOfPlayers; i++)
            players.add(new Player(getCell(0, 0)));
    }

    //normal methods
    public void movePlayer(int indexOfPlayer, int newX, int newY) {
        Player currentPlayer = getPlayers().get(indexOfPlayer);
        Cell newCell = getCell(newX, newY);
        currentPlayer.setCurrentCell(newCell);
        checkIfMustTransmit(currentPlayer);
    }

    private void checkIfMustTransmit(Player player) {
        Transmitter transmitter = player.getCurrentCell().getTransmitter();
        if (transmitter != null) {
            transmitter.transmit(player);
        }
    }

    //getter, setter methods
    public Cell getCell(int x, int y) {
        return cells[x][y];
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }
}

class Cell {
    private int x, y;
    private Transmitter transmitter;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Transmitter getTransmitter() {
        return transmitter;
    }

    //setter, getter methods
    public void setTransmitter(Transmitter transmitter) {
        this.transmitter = transmitter;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "(" + getX() + ", " + getY() + ")";
    }
}

class Player implements Comparable<Player> {
    private Cell currentCell;
    private int scoreFromLadder_Snake = 0;

    public Player(Cell currentCell) {
        this.currentCell = currentCell;
    }

    //getter, setter methods
    public Cell getCurrentCell() {
        return currentCell;
    }

    public void setCurrentCell(Cell currentCell) {
        this.currentCell = currentCell;
    }

    public int getScoreFromLadder_Snake() {
        return scoreFromLadder_Snake;
    }

    public void changeScoreFromLadder_Snake(int change) {
        this.scoreFromLadder_Snake += change;
    }

    public int getTotalScore() {
        return getCurrentCell().getX() + getCurrentCell().getY()
                + getScoreFromLadder_Snake();
    }

    @Override
    public int compareTo(Player o) {
        return getTotalScore() - o.getTotalScore();
    }
}

abstract class Transmitter {
    protected Cell firstCell, endCell;

    public void transmit(Player player) {
        player.setCurrentCell(endCell);
    }

    public Cell getFirstCell() {
        return firstCell;
    }

    public Transmitter(Cell firstCell, Cell endCell) {
        this.firstCell = firstCell;
        this.endCell = endCell;
    }
}

abstract class Snake extends Transmitter {
    public Snake(Cell firstCell, Cell endCell) {
        super(firstCell, endCell);
    }

}

class BadSnake extends Snake {
    public BadSnake(Cell firstCell, Cell endCell) {
        super(firstCell, endCell);
    }

    public static int usage = 0;

    @Override
    public void transmit(Player player) {
        super.transmit(player);
        if (usage < 7)
            player.changeScoreFromLadder_Snake(-1);
        usage++;
    }
}

class GoodSnake extends Snake {
    public GoodSnake(Cell firstCell, Cell endCell) {
        super(firstCell, endCell);
    }

    public static int usage = 0;

    @Override
    public void transmit(Player player) {
        if (BadSnake.usage <= GoodSnake.usage)
            return;
        super.transmit(player);
        usage++;
    }
}

abstract class Ladder extends Transmitter {
    public Ladder(Cell firstCell, Cell endCell) {
        super(firstCell, endCell);
    }

}

class BadLadder extends Ladder {
    public BadLadder(Cell firstCell, Cell endCell) {
        super(firstCell, endCell);
    }

    public static int usage = 0;

    @Override
    public void transmit(Player player) {
        if (GoodSnake.usage + BadSnake.usage <= GoodLadder.usage + BadLadder.usage)
            return;
        super.transmit(player);
        usage++;
    }
}

class GoodLadder extends Ladder {
    public GoodLadder(Cell firstCell, Cell endCell) {
        super(firstCell, endCell);
    }

    public static int usage = 0;

    @Override
    public void transmit(Player player) {
        if (GoodSnake.usage + BadSnake.usage <= GoodLadder.usage + BadLadder.usage)
            return;
        super.transmit(player);
        if(usage < 6)
            player.changeScoreFromLadder_Snake(1);
        usage++;
    }
}