package battleship;

public class Square {

    private boolean tried = false;
    private Ship ship;

    public Ship getShip() {
        return this.ship;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }

    public boolean getTried() {
        return this.tried;
    }

    public void setTried(boolean b) {
        this.tried = true;
        if(this.ship != null) {
            this.ship.incrementHitCounter();
        }
    }

    public boolean isHit() {
        return this.tried && this.ship != null;
    }

    public boolean isMiss() {
        return this.tried && this.ship == null;
    }

    public String getCodeCharacter(boolean showShips) {
        if (this.tried){
            if (this.isHit()){
                return "*";
            } else if (this.isMiss()) {
                return "'";
            }
        } else {
            if (showShips && this.ship != null) {
                return this.ship.getCode();
            }
        }
        return "~";
    }
}
