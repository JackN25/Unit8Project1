import java.io.File;
import java.io.FileNotFoundException;
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

    public static String[][] tryPath(String[][] currentMaze, int startRow, int startColumn) {
        String[][] maze = currentMaze;
        int currentRow = startRow;
        int currentColumn = startColumn;
        boolean endFound = false;
        while (!endFound) {
            ArrayList<Integer> possiblePaths = getPossiblePaths(currentMaze, currentRow, currentColumn);
            if (possiblePaths.size() == 2) {
                currentRow = possiblePaths.get(0);
                currentColumn = possiblePaths.get(1);
            }
        }
        return currentMaze;
    }

    public static ArrayList<Integer> getPossiblePaths(String[][] currentMaze, int currentRow, int currentColumn) {
        ArrayList<Integer> possiblePaths = new ArrayList<Integer>();
        if (currentRow + 1 < currentMaze.length || currentMaze[currentRow + 1][currentColumn].equals(".")) {
            possiblePaths.add(currentRow + 1);
            possiblePaths.add(currentColumn);
        }
        if ()
        return possiblePaths;
    }

    public static int[] getCoordinatesOfCorrectPath(String[][] solvedMaze) {

    }

    public static void main(String[] args) {
        String[][] maze = getMaze("source/data");


    }
}