import java.awt.Graphics;
import java.awt.Color;

// THE CELLS WITH AREN'T THE PART OF ANY CLUE - SOLID CELL
public class SolidCell extends Cell {

    // FILLS IN THE CELL WITH A BLACK BACKGROUND
    public void paint(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
    }
}
