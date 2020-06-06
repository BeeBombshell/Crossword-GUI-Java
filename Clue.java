import java.util.ArrayList;
import java.util.Date;
import java.io.Serializable;

// REPRESENT A CLUE, AND ANSWER IN THE CROSSWORD
public class Clue implements Serializable, Comparable<Clue> {
    private final int number, x, y;
    private final String clue, answer;
    private boolean isDown;
    private char userAnswer[];
    private ClueSolver clueSolver;
    private ArrayList<SolvedLog> solvedLog = new ArrayList<SolvedLog>();
    private static String currentSolverName;

    // CONSTRUCTOR THAT SETS UP THE CLUE
    Clue(int number, int x, int y, String clue, String answer) {
        this.number = number;
        this.x = x;
        this.y = y;
        this.clue = clue;
        this.answer = answer;
        currentSolverName = "";
        userAnswer = new char[answer.length()];
    }

    // RETURN TRUE IF CLUE HAS BEEN SOLVED
    public boolean checkSolved() {
        return ((new String(userAnswer)).equals(answer.toUpperCase()));
    }

    // SETS THE USER'S ANSWER FOR A GIVEN CELL
    public void setUserAnswer(int x, int y, char character) {
        int position;

        if (isDown) {
            position = y - this.y;
        } else {
            position = x - this.x;
        }

        userAnswer[position] = character;

        if ((checkSolved()) && (clueSolver == null)) {
            clueSolver = new ClueSolver(currentSolverName, new Date());
            updateSolvedLog();
        }
    }

    // ADDS DETAILS OF THE SOLVED CLUE TO THE SOLVED LOG
    public void updateSolvedLog() {
        if (checkSolved()) {
            for (SolvedLog s1 : solvedLog) {
                s1.addSolvedClue(this);
            }
        }
    }

    // GETS THE CHARACTER INPUTTED AT A GIVEN POSITION
    public char getUserAnswer(int x, int y) {
        int position;

        if (isDown) {
            position = y - this.y;
        } else {
            position = x - this.x;
        }

        return userAnswer[position];
    }

    // SETTING VALUE TO isDown
    public void setIsDown(boolean isDown) {
        this.isDown = isDown;
    }

    // RETURNING THE VALUE OF isDown
    public boolean getIsDown() {
        return isDown;
    }

    // RETURNING THE NUMBER OF THE CLUE
    public int getNumber() {
        return number;
    }

    // RETURNING THE X-COORDINATE OF THE ASSOCIATED CELL
    public int getX() {
        return x;
    }

    // RETURNING THE Y-COORDINATE OF THE ASSOCIATED CELL
    public int getY() {
        return y;
    }

    // RETURN THE LENGTH OF THE ANSWER
    public int getSize() {
        return answer.length();
    }

    // RETURNING THE TIME AND DATE OF THE CLUE SOLVED
    public Date getDateSolved() {
        if (clueSolver != null) {
            return clueSolver.getTimeCompleted();
        }

        return null;
    }

    // ADDING THE SOLVEDLOG TO AN ALREADY EXISTING SOLVEDLOGS
    public void addSolvedLog(SolvedLog solvedLog) {
        this.solvedLog.add(solvedLog);
        updateSolvedLog();
    }

    // SETTING THE NAME OF THE SOLVER OF THE CLUE
    public static void setCurrentSolverName(String solverName) {
        currentSolverName = solverName;
    }

    // FUNCTION PRODUCING A BRACKETED STRING OF THE LENGTH OF THE ANSWER
    // FOR FUNCTIONS CONTAINING SPECIAL CHARACTERS LIKE ' ' AND '-'
    // RETURNS A STRING DESCRIBING THE LENGTH OF THE ANSWER
    public String getAnswerLength() {
        String result = "(";
        int count = 0;
        for (int i = 0; i < answer.length(); i++) {
            if (answer.charAt(i) == ' ') {
                result = result + Integer.toString(count) + ",";
                count = 0;
            } else if (answer.charAt(i) == '-') {
                result = result + Integer.toString(count) + "-";
                count = 0;
            } else {
                count++;
            }
        }

        return result + Integer.toString(count) + ")";
    }

    // RETURNING A STRING REPRESENTATION OF THE CLUE
    public String toString() {
        return Integer.toString(number) + ". " + clue + " " + getAnswerLength();
    }

    // RETURNING A STRING REPRESENTATION OF THE SOLVED CLUE
    public String getSolvedString() {
        return Integer.toString(number) + ". " + clue + getAnswerLength() + clueSolver;
    }

    // COMPARING THE CLUE WITH ANOTHER CLUE BASED ON WHERE THEY WERE SOLVED
    public int compareTo(Clue c) {
        if ((getDateSolved() != null) && (c.getDateSolved() != null)) {
            return getDateSolved().compareTo(c.getDateSolved());
        } else {
            return 0;
        }
    }

}