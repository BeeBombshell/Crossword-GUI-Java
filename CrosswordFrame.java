import java.awt.BorderLayout;
//import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.DefaultListModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
//import javax.swing.event.ListSelectionEvent;
//import javax.swing.event.ListSelectionListener;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
//import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.SwingUtilities;

// MAIN WINDOW CONTAINING THE CROSSWORD
public class CrosswordFrame extends JFrame {

    // MAIN METHOD
    public static void main(String[] args) {
        final CrosswordFrame cf = new CrosswordFrame();
        // ENSURES INITIALIZATION ON A SINGLE SPECIAL THREAD CALLED EDT(Event Dispatch
        // Thread)
        SwingUtilities.invokeLater(new Runnable() {
            // OVERRIDING FUNCTION run()
            public void run() {
                cf.init();
            }
        });
    }

    // SWING COMPONENTS
    private JLabel titleLabel, nameLabel, acrossLabel, downLabel, solvedLabel;
    private JList acrossList, downList;
    private SolvedLog solvedLog;
    private JScrollPane acrossListPane, downListPane, solvedLogPane;
    private JButton loadButton, saveButton;
    private JCheckBox solvedClueCheckBox;
    private JPanel[] panel;
    private JTextField nameField;
    private GridPanel gridPanel;

    // CONSTRUCTOR TO SET THE TITLE OF THE FRAME
    public CrosswordFrame() {
        // CALLING JFrame's CONSTRUCTOR TO SET THE TITLE
        super("Crossword");
    }

    // INITIALIZER FOR THE FRAME, SETS UP THE COMPONENTS
    public void init() {
        setSize(1050, 525);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // CREATES THE COMPONENTS
        createGrid();
        createLabels();
        createLists();
        createSolvedLog();
        createButtons();
        createCheckBoxes();
        createTextFields();
        createPanels();
        initGrid();

        repaint();
        setVisible(true);

        // SETS THE FOCUS TO THE NAME FIELD, PREVENTS UNWANTED BEHAVIOUR IN THE
        // GRIDPANEL
        nameField.requestFocus();
    }

    // INITIALIZING GRID AND BUILDING OTHER COMPONENTS
    private void initGrid() {
        // ENSURES THAT ACROSS AND DOWN LISTS CONTAIN NO ELEMENTS (INITIALIZING)
        ((DefaultListModel) acrossList.getModel()).removeAllElements();
        ((DefaultListModel) downList.getModel()).removeAllElements();

        // ADDING ALL THE CLUES TO THE LIST
        for (Clue c : gridPanel.getCrossword().getAcrossClues()) {
            ((DefaultListModel) acrossList.getModel()).addElement(c);
        }
        for (Clue c : gridPanel.getCrossword().getDownClues()) {
            ((DefaultListModel) downList.getModel()).addElement(c);
        }
        gridPanel.addList(acrossList);
        gridPanel.addList(downList);

        // ENSURING THE SOLVED LOG IS EMPTY, THEN ADDING ALL SOLVED CLUES
        solvedLog.removeAllSolvedClues();
        for (Clue clue : gridPanel.getCrossword().getClues()) {
            clue.addSolvedLog(solvedLog);
        }
    }

    // CREATING ALL THE LABELS THAT APPEAR IN THE WINDOW
    private void createLabels() {
        titleLabel = new JLabel(gridPanel.getCrossword().getTitle());
        nameLabel = new JLabel("Name: ");
        acrossLabel = new JLabel("Across");
        downLabel = new JLabel("Down");
        solvedLabel = new JLabel("Solved");
    }

    // CREATES LISTS CONTAINING THE CLUES
    private void createLists() {
        acrossList = new JList(new DefaultListModel());
        downList = new JList(new DefaultListModel());

        // LISTS ARE PLACED INSIDE A SCROLL PANE TO ALLOW SCROLLING
        acrossListPane = new JScrollPane(acrossList);
        downListPane = new JScrollPane(downList);

        acrossListPane.setPreferredSize(new Dimension(300, 265));
        downListPane.setPreferredSize(new Dimension(300, 265));

        acrossList.addMouseListener(new ListListener(acrossList, false));
        downList.addMouseListener(new ListListener(downList, true));
    }

    // LISTENER FOR THE LISTS, WHEN THE LIST IS CLICKED THE CORRESPONDING CLUE IS
    // HIGHLIGHTED
    private class ListListener extends MouseAdapter {
        private JList list;
        private boolean isDown;

        // CONSTRUCTOR
        public ListListener(JList list, boolean isDown) {
            super();
            this.list = list;
            this.isDown = isDown;
        }

        // ACTION TO BE PERFORMED ON A CLICK
        public void mouseClicked(MouseEvent e) {
            int index = list.getSelectedIndex();
            if (index >= 0) {
                ListModel listModel = list.getModel();
                Clue clue = (Clue) listModel.getElementAt(index);
                int x = clue.getX();
                int y = clue.getY();

                gridPanel.setFocus(x, y, isDown);
            }
        }
    }

    // CREATING SOLVED LOG
    private void createSolvedLog() {
        solvedLog = new SolvedLog();
        solvedLogPane = new JScrollPane(solvedLog);
        solvedLog.setEditable(false);
        solvedLogPane.setPreferredSize(new Dimension(600, 140));
        // SINCE THE CHECKBOX IS UNTICKED BY DEFAULT, HIDING THE SOLVED LOG
        solvedLog.setVisible(false);
    }

    // CREATING BUTTONS AND THEIR LISTENERS
    private void createButtons() {
        loadButton = new JButton("Load");
        loadButton.addActionListener(new ActionListener() {
            // LOADS IN NEW CROSSWORD OBJECT FROM THE FILE AND INITIALIZES IT
            public void actionPerformed(ActionEvent e) {
                Crossword cw = CrosswordIO.readPuzzle();
                if (cw != null) {
                    // REMOVE OLD GRIDPANEL, ADDING THE ONE W THE NEW CROSSWORD
                    panel[3].remove(gridPanel);
                    gridPanel = new GridPanel(400, 400, cw);
                    panel[3].add(gridPanel, BorderLayout.CENTER);

                    titleLabel = new JLabel(gridPanel.getCrossword().getTitle());

                    // DISPLAY THE NEW GRID PANEL
                    repaint();
                    setVisible(true);

                    // SET THE USERNAME TO THAT OF THE SAVED GAME
                    nameField.setText(cw.getUsername());
                    Clue.setCurrentSolverName(cw.getUsername());

                    // INITIALIZING OTHER COMPONENTS
                    initGrid();
                }
            }
        });

        saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            // WRITING THE CURRENT CROSSWORD TO A FILE
            public void actionPerformed(ActionEvent e) {
                CrosswordIO.writePuzzle(gridPanel.getCrossword());
            }
        });
    }

    // CREATING CHECK BOXES
    private void createCheckBoxes() {
        solvedClueCheckBox = new JCheckBox("Solved Clues Support");
        solvedClueCheckBox.addChangeListener(new ChangeListener() {

            // OVERRIDES stateChanged()
            public void stateChanged(ChangeEvent e) {
                if (((JCheckBox) e.getSource()).isSelected()) {
                    solvedLog.setVisible(true);
                } else
                    solvedLog.setVisible(false);
            }
        });
    }

    // CREATING TEXT FIELDS
    private void createTextFields() {
        nameField = new JTextField(10);
        nameField.addFocusListener(new FocusAdapter() {
            // WHEN THE USER DESELECTS THE TEXT FIELD, USERNAME MUST BE UPDATED
            public void focusLost(FocusEvent e) {
                String username = nameField.getText();
                gridPanel.getCrossword().setUsername(username);
            }
        });
    }

    // CREATING GRID
    private void createGrid() {
        gridPanel = new GridPanel(400, 400, (new CrosswordExample()).getPuzzle());
    }

    // CREATING ALL PANELS USING A TREE LAYOUT
    private void createPanels() {
        panel = new JPanel[10];
        for (int i = 0; i < 10; i++) {
            panel[i] = new JPanel();
        }

        // FORCING VERTICAL LAYOUT WITH BORDER LAYOUT
        panel[1].setLayout(new BorderLayout());
        panel[3].setLayout(new BorderLayout());
        panel[2].setLayout(new BorderLayout());
        panel[7].setLayout(new BorderLayout());
        panel[8].setLayout(new BorderLayout());
        panel[9].setLayout(new BorderLayout());

        // BEGINING W THE TREE
        panel[0].add(panel[1]); // MAIN <- LEFT
        panel[0].add(panel[2]); // MAIN <- RIGHT
        panel[1].add(panel[3], BorderLayout.NORTH); // LEFT <- GRID
        panel[1].add(panel[4], BorderLayout.CENTER); // LEFT <- NAME
        panel[1].add(panel[5], BorderLayout.SOUTH); // LEFT <- LOAD AND SAVE
        panel[2].add(panel[6], BorderLayout.NORTH); // RIGHT <- RIGHTTOP, WILL CONTAIN CLUES
        panel[2].add(panel[7], BorderLayout.SOUTH); // RIGHT <- SOLVEDPANEL
        panel[6].add(panel[8]); // RIGHTTOP <- ACROSS CLUES
        panel[6].add(panel[9]); // RIGHTTOP <- DOWN CLUES

        // ADDING COMPONENTS TO THE PANEL
        panel[3].add(titleLabel, BorderLayout.NORTH);
        panel[3].add(gridPanel, BorderLayout.CENTER);
        panel[4].add(nameLabel);
        panel[4].add(nameField);
        panel[5].add(loadButton);
        panel[5].add(saveButton);
        panel[7].add(solvedLabel, BorderLayout.NORTH);
        panel[7].add(solvedLogPane, BorderLayout.CENTER);
        panel[7].add(solvedClueCheckBox, BorderLayout.SOUTH);
        panel[8].add(acrossLabel, BorderLayout.NORTH);
        panel[8].add(acrossListPane, BorderLayout.CENTER);
        panel[9].add(downLabel, BorderLayout.NORTH);
        panel[9].add(downListPane, BorderLayout.CENTER);

        // ADDING THE MAIN PANEL (AND ITS CHILDEN PANELS)
        getContentPane().add(panel[0]);
    }
}
