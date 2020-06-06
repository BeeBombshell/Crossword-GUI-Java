import java.util.Date;
import java.io.Serializable;

public class ClueSolver implements Serializable {
    private String name;
    private Date timeCompleted;

    // DEFAULT CONSTRUCTOR
    public ClueSolver(String name, Date timeCompleted) {
        this.name = "";
        this.timeCompleted = timeCompleted;
    }

    // RETURN THE NAME OF THE USER WHO SOLVED THE CLUE
    public String getName() {
        return name;
    }

    // RETURNING DATE AND TIME WHEN THE CLUE WAS COMPLETED
    public Date getTimeCompleted() {
        return timeCompleted;
    }

    // RETURNING A STRING REPRESENTATION OF THE STRING ON COMPLETION
    public String toString() {
        return "By " + name + " At " + timeCompleted;
    }
}