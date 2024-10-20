package battleship;

public class BoardDemo {
    public static void main(String[] args) {
        Board b1 = new Board(10, 10);
        b1.setup();

        System.out.println("Bombing square x=2, y=0");
        b1.dropBomb(2, 0);
        System.out.println(b1);
        System.out.println();

        System.out.println("Bombing square x=2, y=1");
        b1.dropBomb(2, 1);
        System.out.println(b1);

        System.out.println("Showing board with the ships hidden:");
        String[] b = b1.toStringArray(false);
        for (String r : b) {
            System.out.println(r);
        }
    }
}
