import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;

public class Main {

    //extract data and put in list
    public static String[][] getMaze(String fileName) {
        File f = new File(fileName);
        Scanner s = null;
        try {
            s = new Scanner(f);
        }
        catch (FileNotFoundException e) {
            System.out.println("File not found.");
            System.exit(1);
        }

        ArrayList<String> fileData = new ArrayList<String>();
        while (s.hasNextLine())
            fileData.add(s.nextLine());

        int rows = fileData.size();
        int cols = fileData.get(0).length();

        String[][] maze = new String[rows][cols];

        for (int i = 0; i < fileData.size(); i++) {
            String d = fileData.get(i);
            for (int j = 0; j < d.length(); j++) {
                maze[i][j] = d.charAt(j) + "";
            }
        }
        return maze;

    }

    //list of correct coordinates(still testing)
    public static ArrayList<String> correctCoordinates = new ArrayList<String>();

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
                ArrayList<Integer> possiblePaths = getPossiblePaths(maze, currentRow, currentColumn);
                //if only 1 path keep going down the path
                if (possiblePaths.size() == 2) {
                    currentRow = possiblePaths.get(0);
                    currentColumn = possiblePaths.get(1);
                    maze[currentRow][currentColumn] = "O";
                    coordinatesWentOver.add("(" + currentRow + ", " + currentColumn + ")");
                    //print statements for testing
                    //for (String[] row : maze) {
                        //for (String column : row) {
                            //System.out.print(column);
                        //}
                        //System.out.println();
                    //}
                    //System.out.println("__________________________");
                //if more than 1 path, try all the paths(unless one already reached the end)
                } else if (possiblePaths.size() > 2) {
                    while (!endFound && !possiblePaths.isEmpty()) {
                        //removes the path coordinates
                        int rowToTry = possiblePaths.remove(0);
                        int columnToTry = possiblePaths.remove(0);
                        maze[rowToTry][columnToTry] = "O";
                        coordinatesWentOver.add("(" + rowToTry + ", " + columnToTry + ")");
                        //RECURSION runs this function for the specific path
                        String[][] triedPath = tryPath(maze, rowToTry, columnToTry);
                        //if specific path reaches end return everything and exit recursion loops
                        if (triedPath[maze.length - 1][maze[0].length - 1].equals("O")) {
                            endFound = true;
                            maze = triedPath;
                        }
                        else {
                            coordinatesWentOver.remove(coordinatesWentOver.size() - 1);
                            maze[rowToTry][columnToTry] = "X";
                        }
                    }
                //NOT WORKING(attempt to return maze before it checks a dead end)
                } else {
                    return currentMaze;
                }
                //if found end return the completed maze and exit recursion loops
                if (maze[maze.length - 1][maze[0].length - 1].equals("O")) {
                    Collections.reverse(coordinatesWentOver);
                    correctCoordinates.addAll(coordinatesWentOver);
                    endFound = true;
                }
        }
        return maze;
    }

    public static ArrayList<Integer> getPossiblePaths(String[][] currentMaze, int currentRow, int currentColumn) {
        ArrayList<Integer> possiblePaths = new ArrayList<Integer>();
        //checks bottom row for available path(does not go out of bounds)
        if (currentRow + 1 < currentMaze.length && currentMaze[currentRow + 1][currentColumn].equals(".")) {
            possiblePaths.add(currentRow + 1);
            possiblePaths.add(currentColumn);
        }
        //checks top row for available path(does not go out of bounds)
        if (currentRow - 1 >= 0 && currentMaze[currentRow - 1][currentColumn].equals(".")) {
            possiblePaths.add(currentRow - 1);
            possiblePaths.add(currentColumn);
        }
        //checks right column for available path(does not go out of bounds)
        if (currentColumn + 1 < currentMaze[currentRow].length && currentMaze[currentRow][currentColumn + 1].equals(".")) {
            possiblePaths.add(currentRow);
            possiblePaths.add(currentColumn + 1);
        }
        //checks left column for available path(does not go out of bounds)
        if (currentColumn - 1 >= 0 && currentMaze[currentRow][currentColumn - 1].equals(".")) {
            possiblePaths.add(currentRow);
            possiblePaths.add(currentColumn - 1);
        }
        //return list of possible paths to take in pairs > (row, column, row, column....)
        return possiblePaths;
    }

    //will get coordinates of correct path but not implemented because the maze also marks dead ends


    public static void main(String[] args) {
        String[][] maze = getMaze("source/data");
        maze[0][0] = "O";
        String[][] finishedMaze = tryPath(maze, 0, 0);
        for (String[] row : finishedMaze) {
            for (String column : row) {
                System.out.print(column);
            }
            System.out.println();
        }
        correctCoordinates.add("(0, 0)");
        Collections.reverse(correctCoordinates);
        for (int i = 0; i < correctCoordinates.size() - 1; i++) {
            System.out.print(correctCoordinates.get(i) + " ---> ");
        }
        System.out.print(correctCoordinates.get(correctCoordinates.size() - 1));
    }
}