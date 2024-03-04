import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {

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

    public static ArrayList<String> correctCoordinates = new ArrayList<String>();

    public static String[][] tryPath(String[][] currentMaze, int startRow, int startColumn) {
        String[][] maze = currentMaze;
        int currentRow = startRow;
        int currentColumn = startColumn;
        boolean endFound = false;
        ArrayList<String> coordinatesWentOver = new ArrayList<String>();
        while (!endFound) {
                ArrayList<Integer> possiblePaths = getPossiblePaths(currentMaze, currentRow, currentColumn);
                if (possiblePaths.size() == 2) {
                    currentRow = possiblePaths.get(0);
                    currentColumn = possiblePaths.get(1);
                    maze[currentRow][currentColumn] = "O";
                    //for (String[] row : maze) {
                        //for (String column : row) {
                        //    System.out.print(column);
                        //}
                        //System.out.println();
                    //}
                    coordinatesWentOver.add("(" + currentRow + ", " + currentColumn +")");
                } else if (possiblePaths.size() > 2) {
                    while (!endFound && !possiblePaths.isEmpty()) {
                        int rowToTry = possiblePaths.remove(0);
                        int columnToTry = possiblePaths.remove(0);
                        String[][] maze1 = maze;
                        maze[rowToTry][columnToTry] = "O";
                        String[][] triedPath = tryPath(maze, rowToTry, columnToTry);
                        if (triedPath[maze.length - 1][maze[0].length - 1].equals("O")) {
                            endFound = true;
                            maze = triedPath;
                        }
                    }
                } else {
                    return currentMaze;
                }
                if (maze[maze.length - 1][maze[0].length - 1].equals("O")) {
                    endFound = true;
                }
        }
        return maze;
    }

    public static ArrayList<Integer> getPossiblePaths(String[][] currentMaze, int currentRow, int currentColumn) {
        ArrayList<Integer> possiblePaths = new ArrayList<Integer>();
        if (currentRow + 1 < currentMaze.length && currentMaze[currentRow + 1][currentColumn].equals(".")) {
            possiblePaths.add(currentRow + 1);
            possiblePaths.add(currentColumn);
        }
        if (currentRow - 1 >= 0 && currentMaze[currentRow - 1][currentColumn].equals(".")) {
            possiblePaths.add(currentRow - 1);
            possiblePaths.add(currentColumn);
        }
        if (currentColumn + 1 < currentMaze[currentRow].length && currentMaze[currentRow][currentColumn + 1].equals(".")) {
            possiblePaths.add(currentRow);
            possiblePaths.add(currentColumn + 1);
        }
        if (currentColumn - 1 >= 0 && currentMaze[currentRow][currentColumn - 1].equals(".")) {
            possiblePaths.add(currentRow);
            possiblePaths.add(currentColumn - 1);
        }
        return possiblePaths;
    }

    public static int[] getCoordinatesOfCorrectPath(String[][] solvedMaze) {
        int[] coordinates = new int[1];
        return coordinates;
    }

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
        System.out.println(correctCoordinates);
    }
}