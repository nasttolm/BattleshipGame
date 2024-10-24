package battleship;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Ship {

    private final String name;
    private final String code;

    private int squareCounter;

    private int x;
    private int y;

    private boolean horizontal;

    public Ship(String name, String code, int squareCounter){
        this.name = name;
        this.code = code;
        this.squareCounter = squareCounter;
    }

    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setLocation(int x, int y) {
        this.x = x;
        this.y = y;
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

    public boolean overlaps(Ship other){
        final Rectangle2D rectThis = new Rectangle2D.Double(this.x, this.y, this.getWidth(), this.getHeight());
        final Rectangle2D rectOther = new Rectangle2D.Double(other.x, other.y, other.getWidth(), other.getHeight());
        final Rectangle2D intersection = rectThis.createIntersection(rectOther);
        return (intersection.getWidth() >= 0) && (intersection.getHeight() >= 0);
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
