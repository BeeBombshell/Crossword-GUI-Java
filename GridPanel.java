import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Graphics;
import java.awt.Color;
//import java.awt.ComponentOrientation;
//import java.awt.Point;
//import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;
import java.util.ArrayList;
//import javax.swing.JTextArea;
import javax.swing.JList;

// GridPanel REPRESENTS A CROSSWORD ON THE SCREEN
public class GridPanel extends JPanel {

    private int width, height;
    private Crossword crossword;
    private CharacterCell focus;
    private ArrayList<JList> list = new ArrayList<JList>();
    private boolean currentlyDown; // FOR PROGRESSING ONTO THE NEXT CELL
    private Cell[][] cell;

    // CONSTRUCTOR FOR GridPanel
    public GridPanel(int width, int height, Crossword crossword) {
        super();
        this.width = width;
        this.height = height;
        setSize(width, height);
        setPreferredSize(new Dimension(width, height));

        this.crossword = crossword;

        // CREATING CELLS AND PANELS TO PUT THEM IN
        cell = new Cell[crossword.getSize()][crossword.getSize()];
        setLayout(new GridLayout(crossword.getSize(), crossword.getSize()));

        // FILLING ALL THE CELLS WITH SOLID CELLS INITIALLY
        for (int i = 0; i < crossword.getSize(); i++) {
            for (int j = 0; j < crossword.getSize(); j++) {
                cell[i][j] = new SolidCell();
            }
        }

        // REPLACING THE CELLS THAT REPRESENT A PART OF THE CLUE WITH CHARACTER CELLS
        for (Clue c : crossword.getAcrossClues()) {
            for (int i = 0; i < c.getSize(); i++) {
                if (cell[c.getX() + i][c.getY()] instanceof CharacterCell) {
                    ((CharacterCell) cell[c.getX() + i][c.getY()]).addAcrossClue(c);
                } else {
                    cell[c.getX() + i][c.getY()] = new CharacterCell(c.getX() + i, c.getY());
                    ((CharacterCell) cell[c.getX() + i][c.getY()]).addAcrossClue(c);
                }
            }
        }
        for (Clue c : crossword.getDownClues()) {
            for (int i = 0; i < c.getSize(); i++) {
                if (cell[c.getX()][c.getY() + i] instanceof CharacterCell) {
                    ((CharacterCell) cell[c.getX()][c.getY() + i]).addDownClue(c);
                } else {
                    cell[c.getX()][c.getY() + i] = new CharacterCell(c.getX(), c.getY() + i);
                    ((CharacterCell) cell[c.getX()][c.getY() + i]).addDownClue(c);
                }
            }
        }

        // ADD CELLS TO THE GRID
        for (int i = 0; i < crossword.getSize(); i++) {
            for (int j = 0; j < crossword.getSize(); j++) {
                add(cell[j][i]);
            }
        }

    }

    // HIGHLIGHTS OR UN-HIGHTLIGHTS THE CELLS ASSOCIATED WITH A CLUE
    public void setHighlightedClue(Clue clue, boolean isHighlighted) {
        for (int i = 0; i < clue.getSize(); i++) {
            if (clue.getIsDown())
                ((CharacterCell) cell[clue.getX()][clue.getY() + i]).setHighlighted(isHighlighted);
            else
                ((CharacterCell) cell[clue.getX() + i][clue.getY()]).setHighlighted(isHighlighted);
        }
    }

    // HIGHLIGHTS A GIVEN CELL AND ITS CLUE
    public void setFocus(int x, int y, boolean isDown) {
        // CHECK THAT THE GIVEN CELL IS A CHARACTER CELL
        if (cell[x][y] instanceof CharacterCell) {
            CharacterCell sender = ((CharacterCell) cell[x][y]);

            // AS LONG AS CURRENT CELL ISNT BEING SELECTED AGAIN,
            // REMOVING FOCUS FROM THE CURRENT CELL
            if (focus != null) {
                setHighlightedClue(focus.getClue(), false);
                if (focus != sender) {
                    focus.setFocus(false);
                }
            }

            // SETS THE FOCUS AND HIGHLIGHTING GIVEN TO A CELL
            sender.setFocus(true);
            sender.setIsDown(isDown);
            setHighlightedClue(sender.getClue(), true);
            focus = sender;
            repaint();

            currentlyDown = isDown;

            // SETS THE GIVEN CELL AS THE SELECTION IN THE LISTS
            for (JList l : list) {
                l.clearSelection();
                l.setSelectedValue(sender.getClue(), true);
            }

        } else
            System.err.println("Not a character cell");

    }

    // FOCUSES THE NEXT CELL IN THE CLUE
    public void focusNextCell() {
        // IF THE CELL IS CURRENTLY FOCUSED
        if (focus != null) {
            int x = focus.getCellX();
            int y = focus.getCellY();

            if (currentlyDown)
                y++;
            else
                x++;

            int size = crossword.getSize();

            if ((x < size) && (y < size)) {
                if (cell[x][y] instanceof CharacterCell)
                    setFocus(x, y, currentlyDown);
            }
        }
    }

    // FOCUSES THE NEXT CELL IN THE CLUE
    public void focusPreviousCell() {
        // IF A CELL IS CURRENTLY FOCUSED
        if (focus != null) {
            int x = focus.getCellX();
            int y = focus.getCellY();

            if (currentlyDown)
                y--;
            else
                x--;

            int size = crossword.getSize();

            if ((x >= 0) && (y >= 0)) {
                if (cell[x][y] instanceof CharacterCell)
                    setFocus(x, y, currentlyDown);
            }
        }
    }

    // RETURNING THE CROSSWORD ASSOCIATED WITH THE GRIDPANEL
    public Crossword getCrossword() {
        return crossword;
    }

    // ADDING LIST TO THE SUBSCRIBED LISTS
    public void addList(JList list) {
        this.list.add(list);
    }

    // DRAWING THE NESSESSARY COMPONENTS RELATED TO THE GRIDPANEL ON THE SCREEN
    public void paint(Graphics g) {
        // PAINTS THE JPanel COMPONENTS
        super.paint(g);
        g.setColor(Color.BLACK);
        // DRAWING LINES ALONG THE SIDE AND THE BOTTOM OF THE PANEL
        // MAKES UP FOR THE LINES MISSED BY THE CELLS
        g.drawLine(getWidth() - 2, 2, getWidth() - 2, getHeight() - 2);
        g.drawLine(2, getHeight() - 2, getWidth() - 2, getHeight() - 2);
    }
}
