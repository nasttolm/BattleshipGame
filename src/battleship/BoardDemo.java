package battleship;

import battleship.ships.Battleship;
import battleship.ships.Destroyer;
import battleship.ships.Submarine;

public class BoardDemo {
    public static void main(String[] args) {
        Board b1 = new Board(10, 10);

        Fleet fleet = new Fleet(1, 2, 3);
        b1.setup(fleet);
        System.out.println(b1);

        System.out.println("Bombing two whole rows");
        for (int y = 3; y < 5; y++) {
            for (int x = 0; x < b1.getWidth(); x++) {
                System.out.println("Bombing x=" + x + ", y=" + y);
                b1.dropBomb(x, y);
                System.out.println(b1);
                System.out.println();
            }
        }

//        for (int i = 0; i < 2; i++) {
//            Ship s = new Ship("Battleship", "B", 5);
//            b1.placeShip(s);
//        }


//        b1.placeShip(new Battleship());
//        b1.placeShip(new Destroyer());
//        b1.placeShip(new Submarine());
//        b1.placeShip(new Submarine());
//
//        System.out.println(b1);


//        b1.setup();

//        System.out.println("Bombing square x=2, y=0");
//        b1.dropBomb(2, 0);
//        System.out.println(b1);
//        System.out.println();
//
//        System.out.println("Bombing square x=2, y=1");
//        b1.dropBomb(2, 1);
//        System.out.println(b1);
//
//        System.out.println("Showing board with the ships hidden:");
//        String[] b = b1.toStringArray(false);
//        for (String r : b) {
//            System.out.println(r);
//        }
    }
}
