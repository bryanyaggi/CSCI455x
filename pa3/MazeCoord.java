/*
 * Name: Bryan Yaggi
 * USC Login ID: yaggi
 * CS 455, PA3
 * Fall 2016
 */

/**
 * MazeCoord class
 * 
 * Immutable class for storing maze coordinates. Note that the first argument to
 * the constructor is a row, and the second is a column. (So if rows go down the
 * screen, this is the reverse of the Java GUI coordinates, where x, the
 * horizontal part of the coordinate, comes first.)
 */
public class MazeCoord
{
   final private int row; // final (non-static) means it can't be updated once         
   final private int col; // it's initialized
   
   /**
    * Creates a MazeCoord with the given row and column.
    * @param row
    * @param col
    */
   public MazeCoord(int row, int col)
   {
      this.row = row;
      this.col = col;
   }

   /**
    * Gets the row of this MazeCoord.
    * @return row
    */
   public int getRow()
   {
      return row;
   }
   
   /**
    * Gets the column of this MazeCoord.
    * @return column
    */
   public int getCol()
   {
      return col;
   }
   
   /**
    * Returns true iff these two MazeCoords have the same row and col.
    * (second arg is type Object because this override equals in Object class)
    */
   public boolean equals(Object other)
   {   
      if (other == null)
      {
         return false;
      }
      
      if (!(other instanceof MazeCoord))
      {
         return false;
      }
      
      MazeCoord otherCoord = (MazeCoord) other;
      
      return this.row == otherCoord.row && this.col == otherCoord.col;
   }
   
   /**
    * Returns a String representation of the MazeCoord.
    */
   public String toString()
   {
      return "MazeCoord[row = " + row + ", col = " + col + "]";
   }
}