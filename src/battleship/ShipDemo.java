package battleship;

import battleship.ships.Aeroplane;
import battleship.ships.Battleship;
import battleship.ships.SimpleShip;

import java.util.ArrayList;

public class ShipDemo {
    public static void main(String[] args) {
        //Ship s = new SimpleShip("Battleship", "B", 5);
        Ship s = new Battleship();
        Ship s2 = new Aeroplane();

        System.out.println(s);
        s.rotate();
        System.out.println(s);

        System.out.println(s2);
        s2.rotate();
        System.out.println(s2);

    }
}
