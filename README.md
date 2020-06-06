# Crossword-GUI-Java
This project reports on the construction of a Crossword Puzzle, designed with the help of Java Programming Language. The project incorporates the use of application of various concepts of Object-Oriented Programming which has helped us in depicting our project on a Virtual Platform, while making use of Graphical User Interface (GUI).

The puzzle consists of words, both across and down as in the case of an original Crossword Puzzle.
Our puzzle consists of words corresponding to number of clues or definitions that are supplied into correspondingly fitted number of squares, i.e. one letter per square, the words being arranged horizontally and vertically so that most of the letters form two parts of a word. 

The puzzle consists of a Solved Clue Support which can be enabled, if the player wishes to know whether his guess is correct or not. This Support provides the word details that has been answered correctly by the player.
Once the player has filled all the words correctly and all such word details appear in the solved clue support space the crossword puzzle is complete and the game ends.


The Crossword project consists of 11 java files, containing various components of the Crossword.


# 1.	Cell.java

All the cells in the Crossword Grid are Classified into two types, Solid Cells, the cells that are not a part of any clue and Character Cells, which are a part of the clue.
The file Cell.java contains the Abstract class to allow both Solid Cells and Character Cells to be added to the same arrays.

# 2.	SolidCell.java

Fills the background black for all the cells that are not a part of any clue

# 3.	ClueSolver.java

Forms the string representation of the Statement to be printed on the Clue Solver, which includes the name of the person solving the crossword and the date and time on which the clue was guessed correctly.

# 4.	Clue.java

Contains a Class that represents a clue and its answer in the crossword.

# 5.	CharacterCell.java

Contains a Class for the characteristics of all the cells that form a part of the Clue. 

# 6.	Crossword.java

This file represents the Crossword that is currently being played, setting all the Clues, setting the Username etc.

# 7.	GridPanel.java

Grid Panel represents the crossword on the Screen. Involves creating Cells, drawing the necessary components and adding them to the Grid.

# 8.	SolvedLog.java

Contains a Class that displays the Log of the Solved Clues on the Solved Clues Panel, in a chronological order.

# 9.	CrosswordIO.java

This file handles all the input/output operations of the Crossword Puzzle, including reading from a file, writing onto a file.

# 10.	CrosswordFrame.java

Contains the Main Function and all the Swing API components for the Layout of the Crossword Puzzle. The described layout is included in this file.

# 11.	CrosswordExample.java

Contains the example Crossword that helps us to test the correct functionality of the Project. 
