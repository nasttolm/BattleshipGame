package battleship.ships;

import battleship.Board;
import battleship.Ship;

public class SimpleShip extends Ship {

    private boolean horizontal;

    public SimpleShip(String name, String code, int squareCounter) {
        super(name, code, squareCounter);
    }

    public void rotate(){
        this.horizontal = !this.horizontal;
    }

    public int getWidth(){
        if(this.horizontal){
            return this.squareCounter;
        } else {
            return 1;
        }
    }

    public int getHeight(){
        if(this.horizontal){
            return 1;
        } else{
            return this.squareCounter;
        }
    }

    public void addToBoard(final Board board){
        if (this.horizontal){
            for(int x = 0; x < this.squareCounter; x++){
                board.getSquare(x + this.x, this.y).setShip(this);
            }
        } else {
            for(int y = 0; y < this.squareCounter; y++){
                board.getSquare(this.x, y + this.y).setShip(this);
            }
        }
    }

    @Override
    public String toString(){
        final StringBuilder builder = new StringBuilder();
        if(this.horizontal){
            for(int x = 0; x < this.squareCounter; x++){
                builder.append("O");
            }
            builder.append("\n");
        } else {
            for(int y = 0; y < this.squareCounter; y++){
                builder.append("O\n");
            }
        }
        return builder.toString();
    }
}
