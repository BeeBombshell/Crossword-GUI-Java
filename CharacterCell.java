import java.awt.Graphics;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

//A Cell THAT CONTAINS A CHARACTER OF A Clue
public class CharacterCell extends Cell {

    private Clue acrossClue, downClue;
    private final int x, y;
    // true IF IS THE FIRST LETTER OF A Clue
    private boolean isFirst;
    private boolean isFocus, isHighlighted, isDown, defaultDown;
    // ARRAY TO ALLOW PRINTING
    private char character[];
    private char clueNumber[];

    // CONSTRUCTOR CharacterCell, INITIALIZING THE x AND y COORDINATES OF THE CELL
    public CharacterCell(int x, int y) {
        super();
        this.x = x;
        this.y = y;
        isFirst = false;
        isFocus = false;
        isHighlighted = false;
        isDown = false;

        character = new char[1];
        character[0] = ' ';

        // ALLOWING THE CELL TO OBTAIN FOCUS
        setFocusable(true);
        setRequestFocusEnabled(true);

        addMouseListener(new MouseAdapter() {
            // IF ITS ALREADY FOCUSED SWAP isDown, OTHERWISE SET defaultDown
            public void mousePressed(MouseEvent e) {
                // mousePressed USED TO PREVENT DRAGGING ISSUE
                if (isFocus) {
                    ((GridPanel) getParent()).setFocus(CharacterCell.this.x, CharacterCell.this.y, !isDown);
                } else {
                    ((GridPanel) getParent()).setFocus(CharacterCell.this.x, CharacterCell.this.y, defaultDown);
                }
            }
        });

        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                // ALL LETTERS ARE MADE CAPITAL TO RESOLVE CASE SENSITIVITY ISSUES
                character[0] = Character.toUpperCase(e.getKeyChar());

                // IF THE CHARACTER ISN'T A SPECIAL CHARACTER THEN WRITE IT TO THE CELL AND MOVE
                // ON
                if (((int) character[0] >= 32) && ((int) character[0] <= 126)) {
                    ((GridPanel) getParent()).focusNextCell();

                    if (acrossClue != null) {
                        acrossClue.setUserAnswer(CharacterCell.this.x, CharacterCell.this.y, character[0]);
                    }
                    if (downClue != null) {
                        downClue.setUserAnswer(CharacterCell.this.x, CharacterCell.this.y, character[0]);
                    }

                    repaint();
                }
                // IF THE CHARACTER IS A BACKSPACE, SET THIS CELL EMPTY AND MOVE BACK
                else if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                    character[0] = ' ';
                    ((GridPanel) getParent()).focusPreviousCell();

                    if (acrossClue != null) {
                        acrossClue.setUserAnswer(CharacterCell.this.x, CharacterCell.this.y, character[0]);
                    }
                    if (downClue != null) {
                        downClue.setUserAnswer(CharacterCell.this.x, CharacterCell.this.y, character[0]);
                    }

                    repaint();
                }
                // IF THE CHARACTER IS A DELETE, SET THIS CELL EMPTY AND STAY PUT
                else if (e.getKeyCode() == KeyEvent.VK_DELETE) {
                    character[0] = ' ';
                    repaint();
                }
            }
        });

        addFocusListener(new FocusAdapter() {
            // SET THE isFocus FLAG AND FORCE repaint()
            public void focusGained(FocusEvent e) {
                isFocus = true;
                repaint();
            }

            public void focusLost(FocusEvent e) {
                isFocus = false;
                repaint();
            }
        });
    }

    // ASSOCIATES AN ACROSS CLUE WITH THE CELL
    public void addAcrossClue(Clue clue) {
        acrossClue = clue;
        // CHECK TO SEE IF THE CELL IS THE FIRST OF THIS CLUE
        if ((acrossClue.getX() == x) && (acrossClue.getY() == y)) {
            isFirst = true;
            clueNumber = Integer.toString(clue.getNumber()).toCharArray();
        }
        calculateDefaultDown();

        // ALLOWS SUPPORT FOR LOADING A CLUE WITH A CHARACTER SAVED FOR THIS CELL
        character[0] = clue.getUserAnswer(x, y);
    }

    // ASSOCIATES A DOWN CLUE WITH THE CELL
    public void addDownClue(Clue clue) {
        downClue = clue;
        // CHECK TO SEE IF THE CELL IS FIRST OF THIS CLUE
        if ((downClue.getX() == x) && (downClue.getY() == y)) {
            isFirst = true;
            clueNumber = Integer.toString(clue.getNumber()).toCharArray();
        }
        calculateDefaultDown();

        // ALLOWS SUPPORT FOR LOADING A CLUE WITH A CHARACTER SAVED FOR THIS CELL
        character[0] = clue.getUserAnswer(x, y);
    }

    // CALCULATES IF THE DEFAULT LOCATION OF THE CLUE SHOULD BE ACROSS OR DOWN,
    // BASED ON WHICH CLUES EXIST
    private void calculateDefaultDown() {
        if (downClue != null) {
            if ((downClue.getX() == x) && (downClue.getY() == y)) {
                defaultDown = true;
            }
        }

        if (acrossClue != null) {
            if ((acrossClue.getX() == x) && (acrossClue.getY() == y)) {
                defaultDown = false;
            }
        }

        if (acrossClue == null) {
            defaultDown = true;
        }
        if (downClue == null) {
            defaultDown = false;
        }

        isDown = defaultDown;

    }

    // RETURNS TRUE IF CELL IS CURRENTLY IN FOCUS
    public boolean getIsFocus() {
        return isFocus;
    }

    // SETTING THE VALUE OF isFocus
    public void setFocus(boolean isFocus) {
        this.isFocus = isFocus;
        if (isFocus) {
            requestFocusInWindow();
        }
    }

    // HIGHLIGHTING THE VALUE isFocus IS SET TO
    public void setHighlighted(boolean isHighlighted) {
        this.isHighlighted = isHighlighted;
    }

    // RETURNING x COORDINATE OF THE CELL
    public int getCellX() {
        return x;
    }

    // RETURNING y COORDINATE OF THE CELL
    public int getCellY() {
        return y;
    }

    // RETURNS TRUE IF isDown
    public boolean getIsDown() {
        return isDown;
    }

    // SETTING isDown's VALUE
    public void setIsDown(boolean isDown) {
        this.isDown = isDown;
    }

    // RETURNS THE CURRENT CLUE, DEPENDS ON CURRENT isDown
    public Clue getClue() {
        if ((isDown) && (downClue != null)) {
            return downClue;
        } else if ((!isDown) && (acrossClue != null)) {
            return acrossClue;
        }

        // TOGGLE isDown
        isDown = !isDown;

        if ((isDown) && (downClue != null)) {
            return downClue;
        } else if ((!isDown) && (acrossClue != null)) {
            return acrossClue;
        }

        System.out.println("NO ASSOCIATED CLUE");
        return null;
    }

    // PRINTING ALL CELL RELATED DATA TO THE SCREEN
    public void paint(Graphics g) {
        // FILL IN WHITE
        if (isHighlighted) {
            g.setColor(Color.YELLOW);
            g.fillRect(0, 0, getWidth(), getHeight());
        } else {
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, getWidth(), getHeight());
        }

        // DRAWING A RED BORDER TO INDICATE THAT IT IS THE FOCUS
        if (isFocus) {
            int thickness = 4;
            g.setColor(Color.RED);
            g.fillRect(0, 0, getWidth() - 0, getHeight() - 0);
            g.setColor(Color.YELLOW);
            g.fillRect(thickness + 1, thickness + 1, getWidth() - (2 * thickness) - 1,
                    getHeight() - (2 * thickness) - 1);
        }

        // DRAW BORDER
        g.setColor(Color.BLACK);
        g.drawLine(0, 0, getWidth(), 0);
        g.drawLine(0, 0, 0, getHeight());

        // DRAW THE NUMBER IF IT IS THE FIRST OF A CLUE
        if (isFirst) {
            g.setFont(new Font(g.getFont().getFontName(), Font.PLAIN, getHeight() / 3));
            if (downClue != null) {
                g.drawChars(clueNumber, 0, clueNumber.length, 3, g.getFontMetrics().getAscent());
            } else if (acrossClue != null) {
                g.drawChars(clueNumber, 0, clueNumber.length, 3, g.getFontMetrics().getAscent());
            }
        }

        // DRAW CHARACTER HELD IN THE CELL
        if (character[0] != ' ') {
            g.setFont(new Font(g.getFont().getFontName(), Font.BOLD, getHeight() / 2));
            // 'M' IS USUALLY THE WIDEST CHARACTER
            g.drawChars(character, 0, 1, getWidth() - g.getFontMetrics().stringWidth("M") - 5,
                    getHeight() - g.getFontMetrics().getDescent() - 3);
        }
    }
}