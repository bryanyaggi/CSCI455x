/*
 * Name: Bryan Yaggi
 * USC Login ID: yaggi
 * CS 455, PA3
 * Fall 2016
 */

import javax.swing.JFrame;

public class MazeViewerNoArgs
{
   private static final char WALL_CHAR = '1';
   private static final char FREE_CHAR = '0';
   
   private static MazeFrame readMazeFile()
   {
      boolean[][] mazeData =
            {
                  {false,true,true,true,true,true,true,true,true,true},
                  {false,true,true,false,false,false,false,false,false,false},
                  {false,false,false,false,true,true,true,true,true,false},
                  {true,false,true,true,true,false,true,false,true,false},
                  {true,false,false,false,false,false,true,false,false,false},
                  {true,false,true,true,false,true,true,true,false,true},
                  {true,false,true,false,false,false,true,true,true,true},
                  {false,false,false,false,true,false,false,false,false,false},
                  {true,true,true,true,true,true,true,true,true,false}
            };
      MazeCoord entryLoc = new MazeCoord(5, 8);
      MazeCoord exitLoc = new MazeCoord(8, 9);
      
      return new MazeFrame(mazeData, entryLoc, exitLoc);
   }
   
   public static void main(String[] args)
   {  
      JFrame frame = readMazeFile();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setVisible(true);
   }
}
