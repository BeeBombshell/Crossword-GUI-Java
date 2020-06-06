import java.util.ArrayList;
import java.io.Serializable;

// REPRESENTS THE CROSSWORD PUZZLE THE USER IS PLAYING
public class Crossword implements Serializable {
    private final ArrayList<Clue> acrossClues, downClues;
    private final String title;
    private final int size;
    private String username;

    // DEFAULT CONSTRUCTOR FOR CROSSWORD
    Crossword(String title, int size, ArrayList<Clue> acrossClues, ArrayList<Clue> downClues) {
        this.title = title;
        this.size = size;
        this.acrossClues = acrossClues;
        this.downClues = downClues;

        // SETS THE CORRECT IsDown FLAG WITHIN THE CLUES AND CHECKS THAT THE CLUES FIT
        // ON THE GRID
        for (Clue c : acrossClues) {
            c.setIsDown(false);
            if (c.getSize() + c.getX() > size) {
                System.out.println("THE WORD SIZE WAS TOO LARGE (ACROSS)");
                System.out.println("ATTEMPTING TO RESOLVE THE PROBLEM BY RESIZING");
                size = c.getSize() + c.getX();
            }
        }
        for (Clue c : downClues) {
            c.setIsDown(true);
            if (c.getSize() + c.getY() > size) {
                System.out.println("THE WORD SIZE WAS TOO LARGE (DOWN)");
                System.out.println("ATTEMPTING TO RESOLVE THE PROBLEM BY RESIZING");
                size = c.getSize() + c.getY();
            }
        }
    }

    // RETURNING THE SIZE OF THE CROSSWORD SQUARE GRID
    public int getSize() {
        return size;
    }

    // RETURNING TITLE OF THE CROSSWORD
    public String getTitle() {
        return title;
    }

    // RETURNING LIST OF ALL ACROSS CLUES
    public ArrayList<Clue> getAcrossClues() {
        return acrossClues;
    }

    // RETURNING LIST OF ALL DOWN CLUES
    public ArrayList<Clue> getDownClues() {
        return downClues;
    }

    // RETURNING A LIST OF ALL CLUES COMBINED INTO ONE LIST
    public ArrayList<Clue> getClues() {
        ArrayList<Clue> result = new ArrayList<Clue>();
        for (Clue clue : acrossClues) {
            result.add(clue);
        }
        for (Clue clue : downClues) {
            result.add(clue);
        }

        return result;
    }

    // SETS THE USERNAME OF USER CURRENTLY SOLVING THE CROSSWORD
    public void setUsername(String Username) {
        this.username = username;
        Clue.setCurrentSolverName(username);
    }

    // RETURNING USERNAME OF PERSON CURRENTLY SOLVING THE CROSSWORD
    public String getUsername() {
        return this.username;
    }
}