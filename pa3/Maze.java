/*
 * Name: Bryan Yaggi
 * USC Login ID: yaggi
 * CS 455, PA3
 * Fall 2016
 */

import java.util.LinkedList;

/**
 * Maze class
 * 
 * Stores information about a maze and can find a path through the maze
 * (if there is one).
 * 
 * Assumptions about structure of the maze, as given in mazeData, entryLoc, and
 * exitLoc (parameters to constructor), and the path:
 *    -- no outer walls given in mazeData -- search assumes there is a virtual 
 *       border around the maze (i.e., the maze path can't go outside of the
 *       maze boundaries)
 *    -- entry location for a path is maze coordinate entryLoc
 *    -- exit location is maze coordinate exitLoc
 *    -- mazeData input is a 2D array of booleans, where true means there is a
 *       wall at that location, and false means there isn't (see public FREE /
 *       WALL constants below) 
 *    -- in mazeData the first index indicates the row.
 *       e.g., mazeData[row][col]
 *    -- only travel in 4 compass directions (no diagonal paths)
 *    -- can't travel through walls
 */
public class Maze
{   
   public static final boolean FREE = false;
   public static final boolean WALL = true;
   
   private static final int NUM_BORDER_ROWS = 2;
   private static final int NUM_BORDER_COLS = 2;
   private static final byte FREE_BYTE = 0;
   private static final byte WALL_BYTE = 1;
   private static final byte VISITED_BYTE = 2;
   
   private final boolean[][] mazeData;
   private final MazeCoord entryLoc;
   private final MazeCoord exitLoc;
   
   private LinkedList<MazeCoord> path;
   /*
    * Representation invariants for path:
    * - If valid path found through maze,
    *    - First element is entryLoc.
    *    - Last element is exitLoc.
    *    - Each MazeCoord represents a valid location in the maze.
    * - If no valid path found,
    *    - path is empty.
    * - If search has not been called,
    *    - path is null.
    */
   
   private byte[][] paddedMaze;
   /*
    * Representation invariant for paddedMaze:
    * - First and last rows bounding maze represent walls.
    * - First and last columns bounding maze represent walls.
    * - Array should only hold FREE_BTYE, WALL_BYTE, or VISITED_BYTE values.
    */
  
   /**
    * Constructs a maze.
    * 
    * @param mazeData   the maze to search. See general Maze comments for what
    *    goes in this array.
    * @param entryLoc   the location in maze to start the search
    *    (not necessarily on an edge)
    * @param exitLoc    the "exit" location of the maze
    *    (not necessarily on an edge)
    * 
    * PRE:  0 <= entryLoc.getRow() < mazeData.length
    *       0 <= entryLoc.getCol() < mazeData[0].length
    *       0 <= exitLoc.getRow() < mazeData.length
    *       0 <= exitLoc.getCol() < mazeData[0].length
    */
   public Maze(boolean[][] mazeData, MazeCoord entryLoc, MazeCoord exitLoc)
   {
      this.mazeData = mazeData;
      this.entryLoc = entryLoc;
      this.exitLoc = exitLoc;
   } 
   
   /**
    * numRows returns the number of rows in the maze.
    * @return number of rows
    */
   public int numRows()
   {
      return mazeData.length;
   }
   
   /**
    * numCols returns the number of columns in the maze.
    * @return number of columns
    */
   public int numCols()
   {
      return mazeData[0].length;
   }
   
   /**
    * hasWallAt returns true iff there is a wall at the specified location.
    * 
    * @param loc  MazeCoord of location
    * @return boolean indicating whether there is a wall there
    * 
    * PRE: 0 <= loc.getRow() < numRows() and 0 <= loc.getCol() < numCols()
    */
   public boolean hasWallAt(MazeCoord loc)
   {
      return mazeData[loc.getRow()][loc.getCol()];
   }

   /**
    * getEntryLoc returns the entry location of this maze.
    * @return MazeCoord of maze entry location
    */
   public MazeCoord getEntryLoc()
   {
      return entryLoc;
   }
   
   /**
    * getExitLoc returns the exit location of this maze.
    * @return MazeCoord of maze exit location
    */
   public MazeCoord getExitLoc()
   {
      return exitLoc;
   }
   
   /**
    * getPath returns the path through the maze. First element is entry
    * location, and last element is exit location. If there was not path, or if
    * this is called before search, returns empty list.
    * 
    * @return maze path as linked list
    */
   public LinkedList<MazeCoord> getPath()
   {
      // Handle case if search has not been called
      if (path == null)
      {
         return new LinkedList<MazeCoord>();
      }
      
      return path;
   }

   /**
    * search finds a path through the maze if there is one. Client can access
    * the path found via getPath method.
    * 
    * @return boolean indicating whether path was found.
    */
   public boolean search()
   {
      // Check if path has already been found.
      if (path != null)
      {
         if (path.isEmpty())
         {
            return false;
         }
         
         return true;
      }
      
      path = new LinkedList<MazeCoord>();
      
      if (hasWallAt(entryLoc) || hasWallAt(exitLoc))
      {
         return false;
      }
      
      padMaze();
      
      return (searchFromLoc(entryLoc.getRow(), entryLoc.getCol()));
   }
   
   /**
    * padMaze creates a 2D byte array, pads the borders with virtual walls, and
    * stores the maze data in the interior. Wall locations are stores as
    * WALL_BYTE, free locations are stored as FREE_BYTE. The array will be used
    * by search and searchFromLoc to find a path solution to the maze. Visited
    * locations are stored as VISITED_BYTE. This array does not alter mazeData.
    */
   private void padMaze()
   {
      paddedMaze =
            new byte[numRows() + NUM_BORDER_ROWS][numCols() + NUM_BORDER_COLS];
      
      // Create top and bottom border walls.
      for (int col = 0; col < paddedMaze[0].length; col++)
      {
         paddedMaze[0][col] = WALL_BYTE;
         paddedMaze[paddedMaze.length - 1][col] = WALL_BYTE;
      }
      
      // Complete left and right border walls.
      for (int row = 1; row < paddedMaze.length - 1; row++)
      {
         paddedMaze[row][0] = WALL_BYTE;
         paddedMaze[row][paddedMaze[0].length - 1] = WALL_BYTE;
      }
      
      // Fill interior with maze data in byte format.
      for (int row = 1; row < paddedMaze.length - 1; row++)
      {
         for (int col = 1; col < paddedMaze[0].length - 1; col++)
         {
            if (mazeData[row - 1][col - 1] == WALL)
            {
               paddedMaze[row][col] = WALL_BYTE;
            }
         }
      }
   }
   
   /**
    * searchFromLoc indicates whether there is a valid path from the specified
    * location to the exit. This function is used recursively by search.
    * 
    * @param row  row coordinate of location
    * @param col  column coordinate of location
    * 
    * @return  boolean indicating whether there is a valid path from the
    *    specified location to the exit
    */
   private boolean searchFromLoc(int row, int col)
   {
      int paddedRow = row + 1;
      int paddedCol = col + 1;
      
      if (paddedMaze[paddedRow][paddedCol] == WALL_BYTE)
      {
         return false;
      }
      
      if (paddedMaze[paddedRow][paddedCol] == VISITED_BYTE)
      {
         return false;
      }
      
      if (row == exitLoc.getRow() && col == exitLoc.getCol())
      {
         path.addLast(exitLoc);
         
         return true;
      }
      
      paddedMaze[paddedRow][paddedCol] = VISITED_BYTE;
      
      // Check if neighboring location is part of a valid path
      if (searchFromLoc(row - 1, col) || searchFromLoc(row, col + 1) ||
            searchFromLoc(row + 1, col) || searchFromLoc(row, col - 1))
      {
         path.addFirst(new MazeCoord(row, col));
         return true;
      }
      
      return false;
   }
}