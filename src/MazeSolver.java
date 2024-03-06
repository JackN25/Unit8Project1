import java.util.ArrayList;
import java.util.Collections;

public class MazeSolver {

    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_RESET = "\u001B[0m";

    private static ArrayList<String> correctCoordinates = new ArrayList<String>();


    public static String[][] tryPath(String[][] currentMaze, int startRow, int startColumn) {
        String[][] maze = new String[currentMaze.length][currentMaze[0].length];
        for (int i = 0; i < maze.length; i++) {
            System.arraycopy(currentMaze[i], 0, maze[i], 0, maze[0].length);
        }
        int currentRow = startRow;
        int currentColumn = startColumn;
        boolean endFound = false;
        ArrayList<String> coordinatesWentOver = new ArrayList<String>();
        while (!endFound) {
            ArrayList<Integer> possiblePaths = PathChecker.getPossiblePaths(maze, currentRow, currentColumn);
            //if only 1 path keep going down the path
            if (possiblePaths.size() == 2) {
                currentRow = possiblePaths.get(0);
                currentColumn = possiblePaths.get(1);
                maze[currentRow][currentColumn] = ANSI_GREEN + "V" + ANSI_RESET;
                coordinatesWentOver.add("(" + currentRow + ", " + currentColumn + ")");
                //if more than 1 path, try all the paths(unless one already reached the end)
            } else if (possiblePaths.size() > 2) {
                while (!endFound && !possiblePaths.isEmpty()) {
                    //removes the path coordinates
                    int rowToTry = possiblePaths.remove(0);
                    int columnToTry = possiblePaths.remove(0);
                    maze[rowToTry][columnToTry] = ANSI_GREEN + "V" + ANSI_RESET;
                    coordinatesWentOver.add("(" + rowToTry + ", " + columnToTry + ")");
                    //RECURSION runs this function for the specific path
                    String[][] triedPath = tryPath(maze, rowToTry, columnToTry);
                    //if specific path reaches end return everything and exit recursion loops
                    if (triedPath[maze.length - 1][maze[0].length - 1].equals(ANSI_GREEN + "V" + ANSI_RESET)) {
                        endFound = true;
                        maze = triedPath;
                    }
                    else {
                        //reached dead end so block entrance of dead end
                        coordinatesWentOver.remove(coordinatesWentOver.size() - 1);
                        maze[rowToTry][columnToTry] = ANSI_RED + "X" + ANSI_RESET;
                    }
                }
                //returns maze before fork if dead end
            } else {
                return currentMaze;
            }
            //if found end return the completed maze and exit recursion loops
            if (maze[maze.length - 1][maze[0].length - 1].equals(ANSI_GREEN + "V" + ANSI_RESET)) {
                Collections.reverse(coordinatesWentOver);
                correctCoordinates.addAll(coordinatesWentOver);
                endFound = true;
            }
        }
        return maze;
    }

    public static String printCorrectCoordinates() {
        correctCoordinates.add("(0, 0)");
        Collections.reverse(correctCoordinates);
        String coordinatesString = "";
        for (int i = 0; i < correctCoordinates.size() - 1; i++) {
            coordinatesString += correctCoordinates.get(i) + " ---> ";
        }
        coordinatesString += correctCoordinates.get(correctCoordinates.size() - 1);
        return coordinatesString;
    }
}
