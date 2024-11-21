package battleship.gui;

import battleship.Square;

import javax.swing.*;
import java.awt.*;

public class BoardButton extends JButton {
    private Square square;
    private boolean showShips;

    public BoardButton(final Square square) {
        this.square = square;
        setPreferredSize(new Dimension(16,16));
    }

    public void setShowShips(boolean showShips) {
        this.showShips = showShips;
    }

    @Override
    public void paintComponent(Graphics g) {
        g = g.create();
        final Color darkRed = new Color(192, 0, 0);
        if (this.square.getTried()) {
            if (this.square.isHit()) {
                // hit ship
                g.setColor(Color.DARK_GRAY);
                g.fillRect(0,0,getWidth(),getHeight());
                if (this.square.getShip().isSunk()) {
                    g.setColor(darkRed);
                } else {
                    g.setColor(Color.ORANGE);
                }
                g.fillOval(4,4,getWidth() - 8, getHeight() - 8);
            } else if (this.square.isMiss()) {
                // sea miss
                g.setColor(Color.BLUE);
                g.fillRect(0,0,getWidth(),getHeight());
                g.setColor(Color.WHITE);
                g.drawOval(4,4,getWidth() - 8, getHeight() - 8);
            }
        } else {
            if (this.showShips && (this.square.getShip() != null)) {
                // ship
                g.setColor(Color.DARK_GRAY);
                g.fillRect(0,0,getWidth(),getHeight());
            } else {
                // sea
                g.setColor(Color.BLUE);
                g.fillRect(0,0,getWidth(),getHeight());
            }
        }
    }

    public static void launch(String[] args) {
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        Square s = new Square();
        s.setTried(true);

        BoardButton boardButton = new BoardButton(s);
        f.add(boardButton);
        f.pack();
        f.setVisible(true);
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> launch(args));
    }
}
