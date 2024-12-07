package battleship.strategy;

import battleship.Board;
import battleship.ComputerPlayerStrategy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import battleship.Outcome;

public class BetterStrategyWithDoubleRunAndSpaceDivision implements ComputerPlayerStrategy {

    private final List<int[]> lastHits = new ArrayList<>();

    private boolean firstRunCompleted;
    private boolean firstRunWasVertical;

    @Override
    public int[] chooseTarget(Board opponentBoard, Board ownBoard) {
        System.out.println( "\nChoosing target....");
        if (!this.lastHits.isEmpty()) {

            // EXTRA optimisation for bombing carriers - if we have completed the first run
            // and had one (random) hit on the other side of the carrier, this will ensure
            // we start bombing along the other side instead of waiting for two (random) hits
            // on the other side to land next to each other
            if (this.firstRunCompleted) {
                // pass zero - favour untried squares adjacent to two hits - this ONLY
                // applies when destroying an aircraft carrier's second strip
                for(int index = 0; index < this.lastHits.size(); index++) {
                    final int[] lastHit = this.lastHits.get(index);
                    Adjacent candidate = Adjacent.random();
                    int c = 4;
                    while(c > 0) {
                        int x = lastHit[0] + candidate.dx;
                        int y = lastHit[1] + candidate.dy;
                        if (inBoundsAndUntried(opponentBoard, x, y) && squareMightBeShip(opponentBoard, x, y)) {
                            int ac = 0;
                            for(int j = 0; j < this.lastHits.size(); j++) {
                                final int[] hit = this.lastHits.get(j);
                                if ((hit[0] == x && Math.abs(hit[1] - y) == 1) ||
                                        (hit[1] == y && Math.abs(hit[0] - x) == 1)) {
                                    ac++;
                                }
                            }
                            if (ac > 1) {
                                System.out.println("PASS0 - TWO ADJACENTS [" + x + ", " + y + "]");
                                return new int[] { x, y };
                            }
                        }
                        candidate = candidate.next();
                        c--;
                    }
                }
            }

            // pass one: favour untried square adjacent to and opposite hits
            // effectively, try to place a hit in line with two existing hits

            Adjacent candidate = null;
            if (this.firstRunCompleted) {
                // favour run in same axis and any existing run
                if (this.firstRunWasVertical) {
                    candidate = Adjacent.ABOVE;
                } else {
                    candidate = Adjacent.LEFT;
                }
                if (Math.random() >= 0.5) {
                    candidate = candidate.opposite();
                }
            } else {
                candidate = Adjacent.random();
            }

            int[] runExtension = null;
            runExtension = extendRunOnAxis(opponentBoard, candidate, runExtension);
            if (runExtension == null) {
                candidate = candidate.next();
                runExtension = extendRunOnAxis(opponentBoard, candidate, runExtension);
            }

            if (runExtension != null) {
                System.out.println("PASS1 - EXTENDING RUN: " + Arrays.toString(runExtension));
                return runExtension;
            } else {
                System.out.println("CANNOT EXTEND RUN");
            }

            // at this point we know it is impossible to place three in a row
            // this might be because there is only one hit at the moment, or
            // because we have had a miss at each end of an existing run
            // hence
            if (!this.firstRunCompleted && this.lastHits.size() > 2) {
                this.firstRunCompleted = true;
                // we now want to favour runs in the same direction to optimise bombing aircraft carriers
                this.firstRunWasVertical = lastHitsLooksVertical();
                System.out.println( "FIRST RUN COMPLETED: " + (this.firstRunWasVertical ? "VERTICAL" : "HORIZONTAL"));
            }

            // pass two: any untried square adjacent to a hit
            for(int index = 0; index < this.lastHits.size(); index++) {
                final int[] lastHit = this.lastHits.get(index);
                candidate = Adjacent.random();
                int c = 4;
                while(c > 0) {
                    int x = lastHit[0] + candidate.dx;
                    int y = lastHit[1] + candidate.dy;
                    if (inBoundsAndUntried(opponentBoard, x, y) && squareMightBeShip(opponentBoard, x, y)) {
                        System.out.println("PASS2 - RANDOM ADJACENT [" + x + ", " + y + "]");
                        return new int[] { x, y };
                    }
                    candidate = candidate.next();
                    c--;
                }
            }
        } else {
            // lasthits is empty; attacking a new ship
            this.firstRunCompleted = false;
            this.firstRunWasVertical = false;
        }

        return chooseSquare(opponentBoard);
    }

    private int[] extendRunOnAxis(Board opponentBoard, Adjacent candidate, int[] runExtension) {
        for(int index = 0; index < this.lastHits.size(); index++) {
            final int[] lastHit = this.lastHits.get(index);
            System.out.print( Arrays.toString(lastHit));
            System.out.print( " " + candidate);
            runExtension = tryThreeInARow(opponentBoard, lastHit, candidate);
            if (runExtension != null) { break; }
            candidate = candidate.opposite();
            System.out.print( " " + candidate + " ");
            runExtension = tryThreeInARow(opponentBoard, lastHit, candidate);
            if (runExtension != null) { break; }
        }
        System.out.println();
        return runExtension;
    }

    private int[] tryThreeInARow(Board opponentBoard, final int[] lastHit, Adjacent candidate) {
        int x = lastHit[0] + candidate.dx;
        int y = lastHit[1] + candidate.dy;
        final int ox = lastHit[0] + candidate.opposite().dx;
        final int oy = lastHit[1] + candidate.opposite().dy;
        if (inBoundsAndUntried(opponentBoard, x, y) && squareMightBeShip(opponentBoard, x, y)) {
            if (opponentBoard.inBounds(ox, oy) && opponentBoard.getSquare(ox, oy).isHit()) {
                return new int[] { x, y };
            }
        }
        return null;
    }

    private boolean inBoundsAndUntried(Board board, int x, int y) {
        return board.inBounds(x, y) && !board.getSquare(x, y).getTried();
    }

    private boolean lastHitsLooksVertical() {
        int maxY = -1;
        int minY = Integer.MAX_VALUE;
        int maxX = -1;
        int minX = Integer.MAX_VALUE;
        for (int[] is : this.lastHits) {
            if (is[0] > maxX) { maxX = is[0]; }
            if (is[0] < minX) { minX = is[0]; }
            if (is[1] > maxY) { maxY = is[1]; }
            if (is[1] < minY) { minY = is[1]; }
        }
        return maxY - minY >  maxX - minX;
    }

    private int[] chooseSquare(final Board board) {

        int[][] scoreArray = new int[board.getHeight()][board.getWidth()];
        for (int i = 0; i < scoreArray.length; i++) {
            Arrays.fill(scoreArray[i], -1);
        }

        for(int x = 0; x < board.getWidth(); x++) {
            for(int y = 0; y < board.getHeight(); y++) {
                if (!board.getSquare(x, y).getTried()) {
                    scoreArray[y][x] = spaceDividingPotential(board, x, y);
                }
            }
        }

        for (int y = 0; y < board.getHeight(); y++) {
            for (int x = 0; x < board.getWidth(); x++) {
                System.out.printf( " %3d", scoreArray[y][x]);
            }
            System.out.println();
        }
        System.out.println();


        List<int[]> bests = new ArrayList<>();
        int bestScore = -1;
        for (int y = 0; y < board.getHeight(); y++) {
            for (int x = 0; x < board.getWidth(); x++) {
                if (scoreArray[y][x] > bestScore) {
                    bests.clear();
                    bests.add(new int[] { x, y });
                    bestScore = scoreArray[y][x];
                } else if (scoreArray[y][x] == bestScore) {
                    bests.add(new int[] { x, y });
                }
            }
        }
        int[] bestSquare = bests.get((int) (Math.random() * bests.size()));
        System.out.println( Arrays.toString(bestSquare) + "  " + bestScore);

        return bestSquare;
    }

    /**
     * True if no square around x,y is an existing hit on a ship. Ships
     * can't touch, so if there is such a square there can't be
     * another ship in this square.
     *
     * @param board
     * @param x
     * @param y
     * @return
     */
    private boolean squareMightBeShip(final Board board, final int x, final int y) {
        if (!board.inBounds(x, y)) {
            return false;
        }
        if (board.getSquare(x, y).isMiss()) {
            return false;
        }
        // examine squares around to the target being considered
        for(int i = -1; i <= 1; i++) {
            for(int j = -1; j <= 1; j++) {
                if (i != 0 || j != 0) {
                    final int tx = x + i;
                    final int ty = y + j;
                    if (board.inBounds(tx, ty) && board.getSquare(tx, ty).isHit()) {
                        // if the square is a hit in lastHits that is OK as its part of the ship we are part
                        // way through destroying (this method is also used when picking adjacent squares in
                        // that process).
                        if (!isInLastHits( tx, ty)) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    /**
     * Counting up, down, left or right from the target up to a maximum distance of four squares
     * to see how many consecutives hits would be possible if this square is a hit.
     *
     * @param board
     * @param x
     * @param y
     * @param adjacent
     * @return
     */
    private int potentialHitsInDirection(final Board board, final int x, final int y, Adjacent adjacent) {
        int count = 0;
        int countLimit = 50; // not wanted for spacedivision // length of longest ship
        int tx, ty;
        do {
            count++;
            tx = x + (count * adjacent.dx);
            ty = y + (count * adjacent.dy);
        } while(squareMightBeShip(board, tx, ty) && count < countLimit);
        return count - 1;
    }

    /**
     * Returns a high score for squares which bisect long stretches of possible
     * ship locations in either x or y axes, or even higher if both!
     *
     * @param board
     * @param x
     * @param y
     * @return
     */
    private int spaceDividingPotential(final Board board, final int x, final int y) {
        if (squareMightBeShip(board, x, y)) {
            int above = potentialHitsInDirection(board, x, y, Adjacent.ABOVE);
            int below = potentialHitsInDirection(board, x, y, Adjacent.BELOW);
            int left = potentialHitsInDirection(board, x, y, Adjacent.LEFT);
            int right = potentialHitsInDirection(board, x, y, Adjacent.RIGHT);
            int horizontal = Math.min(left, right) + 1;
            int vertical = Math.min(above, below) + 1;
            if (horizontal > vertical) {
                return (horizontal * horizontal) * vertical;
            } else {
                return (vertical * vertical) * horizontal;
            }
        } else {
            return 0;
        }
    }

    private boolean isInLastHits(int x, int y) {
        for(int[] hit : this.lastHits) {
            if (hit[0] == x && hit[1] == y) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void resultOfMove(Outcome outcome) {
        if (outcome.hit()) {
            this.lastHits.add(new int[] { outcome.getX(), outcome.getY() });
            if (outcome.sunk() != null) {
                this.lastHits.clear();
            }
        }
    }


}

