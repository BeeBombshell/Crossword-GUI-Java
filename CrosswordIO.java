import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import javax.swing.JFileChooser;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

// CLASS THAT HANDLES ALL CROSSWORD INPUT/OUTPUT OPERATIONS
public class CrosswordIO {

    // READING A PUZZLE FROM THE FILE
    public static Crossword readPuzzle() {
        JFileChooser jfc = new JFileChooser();
        if (jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            try {
                File f = jfc.getSelectedFile();
                FileInputStream fis = new FileInputStream(f);
                ObjectInputStream ois = new ObjectInputStream(fis);
                Crossword result = (Crossword) ois.readObject();
                ois.close();
                return result;
            } catch (IOException e) {
                System.err.println("AN IO ERROR OCCURED!");
            } catch (ClassNotFoundException e) {
                System.err.println("A CLASS NOT FOUND ERROR OCCURED!");
            }
        }
        return null;
    }

    // WRITING A CROSSWORD INTO A FILE
    public static void writePuzzle(Crossword crossword) {
        JFileChooser jfc = new JFileChooser();
        if (jfc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            try {
                File f = jfc.getSelectedFile();
                FileOutputStream fos = new FileOutputStream(f);
                ObjectOutputStream oos = new ObjectOutputStream(fos);

                oos.writeObject(crossword);

                oos.flush();
                oos.close();
            } catch (IOException e) {
                System.err.println("AN IO ERROR OCCURED!");
            }
        }
    }

}