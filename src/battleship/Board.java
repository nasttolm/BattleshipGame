package battleship;

public class Board {
    private int height;
    private int width;

    private Square board[][];

    /** Hardcoded ships **/
    public void setup() {
        Ship s = new Ship("battleship", "B");
        getSquare(1, 1).setShip(s);
        getSquare(2, 1).setShip(s);
        getSquare(3, 1).setShip(s);
        getSquare(4, 1).setShip(s);
        getSquare(5, 1).setShip(s);

        s = new Ship("destroyer", "D");
        getSquare(7, 4).setShip(s);
        getSquare(7, 5).setShip(s);
        getSquare(7, 6).setShip(s);
        getSquare(7, 7).setShip(s);

        s = new Ship("submarine", "S");
        getSquare(3, 3).setShip(s);
        getSquare(4, 3).setShip(s);
        getSquare(5, 3).setShip(s);
    }

    /** Hardcoded bombs **/
    public boolean dropBomb(final int x, final int y) {
        Square square = getSquare(x, y);
        if (!square.getTried()){
            square.setTried();
            return square.isHit();
        } else {
            // to/do
            return false;
        }
    }

    public Board(int height, int width) {
        this.height = height;
        this.width = width;
        this.board = new Square[height][width];

        for (int i = 0; i < this.board.length; i++){
            Square[] row = this.board[i];
            for (int j = 0; j < row.length; j++){
                row[j] = new Square();
            }
        }
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public Square getSquare(int x, int y) {
        return board[x][y];
    }

    public boolean inBounds(int x, int y) {
        return x >= 0 && y >= 0 && x < this.width && y < this.height;
    }

    public String[] toStringArray(final boolean showShips) {
        final String[] array = new String[this.height];
        for (int y = 0; y < this.height; y++){
            final StringBuilder builder = new StringBuilder(this.width);
            for (int x = 0; x < this.width; x++){
                Square square = getSquare(x, y);
                builder.append(square.getCodeCharacter(showShips));
            }
            array[y] = builder.toString();

        }
        return array;
    }

    @Override
    public String toString() {
        final String[] array = this.toStringArray(true);
        final StringBuilder builder = new StringBuilder(this.width);
        for(int y = 0; y < this.height; y++){
            builder.append(array[y]);
            builder.append("\n");
        }
        return builder.toString();
    }


}
