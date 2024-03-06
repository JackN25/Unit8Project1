import java.util.ArrayList;

public class PathChecker {

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

}
