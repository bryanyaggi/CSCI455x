/*
 * Name: Bryan Yaggi
 * USC Login ID: yaggi
 * CS 455, PA3
 * Fall 2016
 */

public class MazeTester
{
   private static void testMazeCoord()
   {
      MazeCoord mazeCoord1 = new MazeCoord(1, 2);
      System.out.println(mazeCoord1.toString());
   }
   
   private static void testMazeAccessors(Maze mazeTest)
   {
      System.out.println("Number of rows: " + mazeTest.numRows());
      System.out.println("Number of columns: " + mazeTest.numCols());
      System.out.println("Entry location: " + mazeTest.getEntryLoc().toString());
      System.out.println("Exit location: " + mazeTest.getExitLoc().toString());
      System.out.println("Wall at entry location: " + mazeTest.hasWallAt(mazeTest.getEntryLoc()));
      System.out.println("Wall at exit location: " + mazeTest.hasWallAt(mazeTest.getExitLoc()));
      System.out.println("Wall at (2, 4): " + mazeTest.hasWallAt(new MazeCoord(2, 4)));
      System.out.println("Maze coordinates equal: " + new MazeCoord(2, 3).equals(new MazeCoord(2, 3)));
   }
   
   public static void main(String[] args)
   {
      testMazeCoord();
      
      // Create a Maze
      boolean[][] mazeData1 =
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
      MazeCoord entryLoc1 = new MazeCoord(5, 8);
      MazeCoord exitLoc1 = new MazeCoord(8, 9);
      
      boolean[][] mazeData2 =
            {
                  {false,true,true,false,false,false,false,false,false,false},
                  {false,false,false,false,true,true,true,true,true,false},
                  {true,false,true,true,true,false,true,false,true,false},
                  {true,false,false,false,false,false,true,false,false,false},
                  {true,false,true,true,false,true,true,true,false,true},
                  {true,false,true,false,false,false,true,true,true,true},
                  {false,false,false,false,true,false,false,false,false,false},
                  {true,true,true,true,true,true,true,true,true,false}
            };
      MazeCoord entryLoc2 = new MazeCoord(5, 8);
      MazeCoord exitLoc2 = new MazeCoord(7, 9);
      
      Maze mazeTest1 = new Maze(mazeData1, entryLoc1, exitLoc1);
      Maze mazeTest2 = new Maze(mazeData2, entryLoc2, exitLoc2);
      
      testMazeAccessors(mazeTest1);
      testMazeAccessors(mazeTest2);
      
   }
}
