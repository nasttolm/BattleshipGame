package battleship.ships;

public class AircraftCarrier extends TemplateShip{

    private static final int[][] pattern = new int[][]{
            new int[]{0, 1},
            new int[]{1, 1},
            new int[]{1, 1},
            new int[]{1, 1},
            new int[]{1, 0},
    };

    public AircraftCarrier() {
        super("Aircraft Carrier", "C", pattern);
    }
}
