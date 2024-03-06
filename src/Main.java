import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;

public class Main {

    //colors
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RESET = "\u001B[0m";


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


    public static void main(String[] args) {
        String[][] maze = getMaze("source/data");
        maze[0][0] = ANSI_GREEN + "V" + ANSI_RESET;
        String[][] finishedMaze = MazeSolver.tryPath(maze, 0, 0);
        for (String[] row : finishedMaze) {
            for (String column : row) {
                System.out.print(column);
            }
            System.out.println();
        }
        System.out.println(MazeSolver.printCorrectCoordinates());
    }
}