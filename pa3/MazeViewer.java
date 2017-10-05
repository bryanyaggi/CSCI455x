/*
 * Name: Bryan Yaggi
 * USC Login ID: yaggi
 * CS 455, PA3
 * Fall 2016
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.JFrame;
import java.util.Scanner;

/**
 * MazeViewer class
 * 
 * Program to read in and display a maze and a path through the maze. At user
 * command displays a path through the maze if there is one.
 * 
 * How to call it from the command line:
 * 
 *    java MazeViewer mazeFile
 * 
 * where mazeFile is a text file of the maze. The format is the number of rows
 * and number of columns, followed by one line per row, followed by the start
 * location, and ending with the exit location. Each maze location is either a
 * wall (1) or free (0). Here is an example of contents of a file for a 3x4
 * maze, with start location as the top left, and exit location as the bottom
 * right (we count locations from 0, similar to Java arrays):
 * 
 * 3 4 
 * 0111
 * 0000
 * 1110
 * 0 0
 * 2 3
 * 
 */
public class MazeViewer
{   
   private static final char WALL_CHAR = '1';
   private static final char FREE_CHAR = '0';
   private static final int NUM_DIMENSIONS = 2;
   private static final int ROW_INDEX = 0;
   private static final int COLUMN_INDEX = 1;

   /**
    * readMazeSize reads the maze size from the maze file.
    * 
    * @param in   Scanner for reading maze file
    * @return Integer array specifying the maze size
    * @throws IOException  indicates invalid data in maze file
    */
   private static Integer[] readMazeSize(Scanner in) throws IOException
   {
      if (!in.hasNextLine())
      {
         throw new IOException("Invalid data: Missing maze size.");
      }
      
      Scanner lineReader = new Scanner(in.nextLine());
      Integer[] mazeSize = new Integer[NUM_DIMENSIONS];
      
      try
      {
         for (int i = 0; i < NUM_DIMENSIONS; i++)
         {
            if (lineReader.hasNextInt())
            {
               int sizeValue = lineReader.nextInt();
               
               if (sizeValue > 0)
               {
                  mazeSize[i] = sizeValue;
               }
               else
               {
                  throw new IOException("Invalid data: Maze size must be"
                        + " positive.");
               }
            }
            else
            {
               throw new IOException("Invalid data: Missing or invalid data"
                     + " type for maze size.");
            }
         }
         
         if (lineReader.hasNext())
         {
            throw new IOException("Invalid data: Too many arguments for maze"
                  + " size.");
         }
      }
      finally
      {
         lineReader.close();
      }
      
      return mazeSize;
   }
   
   /**
    * readMazeData reads the maze data about the locations of walls and free
    * spaces from the maze file.
    * 
    * @param in         Scanner for reading maze file
    * @param mazeSize   Integer array specifying the maze size
    * 
    * @return 2D boolean array containing maze data for use with Maze class
    * @throws IOException  indicates invalid data in maze file
    */
   private static boolean[][] readMazeData(Scanner in, Integer[] mazeSize)
         throws IOException
   {  
      boolean[][] mazeData =
            new boolean[mazeSize[ROW_INDEX]][mazeSize[COLUMN_INDEX]];
            // elements initialized as false
      
      for (int row = 0; row < mazeData.length; row++)
      {
         if (!in.hasNextLine())
         {
            throw new IOException("Invalid data: Missing maze data.");
         }
         
         String rowData = in.nextLine();
         
         if (rowData.length() != mazeData[0].length)
         {
            throw new IOException("Invalid data: Maze rows must equal"
                  + " specified size.");
         }
         
         for (int col = 0; col < rowData.length(); col++)
         {
            if (rowData.charAt(col) == WALL_CHAR)
            {
               mazeData[row][col] = Maze.WALL;
            }
            else if (rowData.charAt(col) != FREE_CHAR)
            {
               throw new IOException("Invalid data: Invalid maze data value.");
            }
         }
      }
      
      return mazeData;
   }
   
   /**
    * readMazeCoord reads a maze coordinate from the maze file
    * 
    * @param in         Scanner for reading maze file
    * @param mazeSize   Integer array specifying maze size
    * 
    * @return MazeCoord created from read coordinates
    * @throws IOException  indicates invalid data in maze file
    */
   private static MazeCoord readMazeCoord(Scanner in, Integer[] mazeSize)
         throws IOException
   {
      if (!in.hasNextLine())
      {
         throw new IOException("Invalid data: Missing maze coordinates.");
      }
      
      Scanner lineReader = new Scanner(in.nextLine());
      Integer[] mazeCoordArr = new Integer[NUM_DIMENSIONS];
      
      try
      {
         for (int i = 0; i < NUM_DIMENSIONS; i++)
         {
            if (lineReader.hasNextInt())
            {
               int coordValue = lineReader.nextInt();
               
               if (coordValue >= 0 && coordValue < mazeSize[i])
               {
                  mazeCoordArr[i] = coordValue;
               }
               else
               {
                  throw new IOException("Invalid data: Maze coordinate outside"
                        + " of maze.");
               }
            }
            else
            {
               throw new IOException("Invalid data: Missing or invalid data"
                     + " type for maze coordinates.");
            }
         }
         
         if (lineReader.hasNext())
         {
            throw new IOException("Invalid data: Too many arguments given for"
                  + " maze coordinates.");
         }
      }
      finally
      {
         lineReader.close();
      }
      
      return new MazeCoord(mazeCoordArr[ROW_INDEX],
            mazeCoordArr[COLUMN_INDEX]);
   }
   
   /**
    * readMazeFile reads in maze from the file whose name is given and 
    * returns a MazeFrame created from it.
    *    
    * @param fileName   the name of a maze file to read from (file format shown
    *    in class comments, above)
    * @returns a MazeFrame containing the data from the file.
    * 
    * @throws FileNotFoundException if there's no such file
    *    (subclass of IOException)
    * @throws IOException (hook given in case you want to do more
    *    error-checking. That would also involve changing main to catch other
    *    exceptions)
    */
   private static MazeFrame readMazeFile(String fileName) throws IOException
   {
      File mazeFile = new File(fileName);
      Scanner in = new Scanner(mazeFile);
      
      try
      {  
         Integer[] mazeSize = readMazeSize(in);
         
         return new MazeFrame(readMazeData(in, mazeSize),
               readMazeCoord(in, mazeSize), readMazeCoord(in, mazeSize));
      }
      finally
      {
         in.close();
      }
   }
   
   /**
    * printUsage prints the correct usage of MazeViewer.
    */
   private static void printUsage()
   {
      System.out.println("Usage: java MazeViewer fileName");
   }
   
   /**
    * Checks command line arguments and handles invalid input. Reads the maze
    * file and creates the MazeFrame. Catches exceptions.
    * 
    * @param args command line arguments
    */
   public static void main(String[] args)
   {
      String fileName = "";

      try
      {
         if (args.length < 1)
         {
            System.out.println("ERROR: Missing file name command line"
                  + " argument.");
            printUsage();
         }
         else if (args.length > 1)
         {
            System.out.println("ERROR: Too many command line arguments.");
            printUsage();
         }
         else
         {
            fileName = args[0];
            
            JFrame frame = readMazeFile(fileName);

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
         }
      }
      catch (FileNotFoundException exc)
      {
         System.out.println("File not found: " + fileName);
      }
      catch (IOException exc)
      {
         exc.printStackTrace();
      }
   }
}