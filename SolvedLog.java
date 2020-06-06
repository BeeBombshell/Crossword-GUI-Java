import javax.swing.JTextArea;
import java.util.ArrayList;
import java.util.Collections;

// A CLASS THAT DISPLAYS A LOG OF SOLVED CLUES
public class SolvedLog extends JTextArea {

    ArrayList<Clue> solvedClue = new ArrayList<Clue>();

    // ADDING A CLUE TO THE SOLVED CLUE LIST AND DISPLAYS IT IN CHRONOLOGICAL ORDER,
    // IN WHICH THEY WERE SOLVED
    public void addSolvedClue(Clue clue) {
        if (solvedClue.contains(clue) == false) {
            solvedClue.add(clue);
            Collections.sort(solvedClue);
            setText("");
            for (Clue c : solvedClue) {
                append(c.getSolvedString() + "\n");
            }
        }
    }

    // RESETS THE LOG TO EMPTY AND CLEARS THE VISUAL COMPONENT
    public void removeAllSolvedClues() {
        solvedClue = new ArrayList<Clue>();
        setText("");
    }

}