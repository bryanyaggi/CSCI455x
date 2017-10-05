/*
 * Name: Bryan Yaggi
 * USC Login ID: yaggi
 * CS 455, PA3
 * Fall 2016
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.util.ListIterator;
import javax.swing.JComponent;

/**
 * MazeComponent class
 * 
 * A component that displays the maze and path through it if one has been
 * found.
 */
public class MazeComponent extends JComponent
{
   private static final int START_X = 10; // spacing from left
   private static final int START_Y = 10; // spacing from top
   private static final int BOX_WIDTH = 20; // width of one maze space
   private static final int BOX_HEIGHT = 20; // height of one maze space
   private static final int INSET = 2; // marker margin within space
   private static final int BORDER_THICKNESS = 1;
   private static final Color WALL_COLOR = Color.BLACK;
   private static final Color FREE_COLOR = Color.WHITE;
   private static final Color ENTRANCE_COLOR = Color.YELLOW;
   private static final Color EXIT_COLOR = Color.GREEN;
   private static final Color PATH_COLOR = Color.BLUE;
   
   private final Maze maze;
   
   /**
    * Constructs the component.
    * @param maze   the maze to display
    */
   public MazeComponent(Maze maze) 
   {   
      this.maze = maze;
   }
   
   /**
    * paintComponent draws the current state of maze including the path through
    * it if one has been found.
    * 
    * @param g the graphics context
    */
   public void paintComponent(Graphics g)
   {
      Graphics2D g2 = (Graphics2D) g;
      
      paintMaze(g2);
      
      paintMarker(g2, maze.getEntryLoc(), ENTRANCE_COLOR);
      paintMarker(g2, maze.getExitLoc(), EXIT_COLOR);
      
      paintPath(g2);
   }
   
   /**
    * paintMazeBackground draws the maze background and borders. The maze
    * background and borders have the same color, specified by WALL_COLOR.
    * @param g2   Graphics2D object
    */
   private void paintMazeBackground(Graphics2D g2)
   {
      Rectangle background = new Rectangle(START_X - BORDER_THICKNESS,
            START_Y - BORDER_THICKNESS, maze.numCols() * BOX_WIDTH + 2
            * BORDER_THICKNESS, maze.numRows() * BOX_HEIGHT + 2
            * BORDER_THICKNESS);
      
      g2.setColor(WALL_COLOR);
      g2.fill(background);
   }
   
   /**
    * paintMaze draws the maze walls and free spaces
    * @param g2   Graphics2D object
    */
   private void paintMaze(Graphics2D g2)
   {
      paintMazeBackground(g2);
      
      for (int row = 0; row < maze.numRows(); row++)
      {
         for (int col = 0; col < maze.numCols(); col++)
         {
            if (!maze.hasWallAt(new MazeCoord(row, col)))
            {
               Rectangle freeBox =
                     new Rectangle(START_X + col * BOX_WIDTH,
                           START_Y + row * BOX_HEIGHT, BOX_WIDTH, BOX_HEIGHT);
               
               g2.setColor(FREE_COLOR);
               g2.fill(freeBox);
            }
         }
      }
   }
   
   /**
    * paintMarker draws a marker at the specified location in the specified
    * color. The marker is a rectangle inset from the space by the value INSET.
    * 
    * @param g2         Graphics2D object
    * @param location   MazeCoord location for intended marker
    * @param color      Color for intended marker
    */
   private void paintMarker(Graphics2D g2, MazeCoord location, Color color)
   {
      Rectangle marker =
            new Rectangle(START_X + location.getCol() * BOX_WIDTH
                  + INSET, START_Y + location.getRow() * BOX_HEIGHT + INSET,
                  BOX_WIDTH - 2 * INSET, BOX_HEIGHT - 2 * INSET);
      
      g2.setColor(color);
      g2.fill(marker);
   }
   
   /**
    * paintPath draws the path through the maze if found.
    * @param g2   Graphics2D object
    */
   private void paintPath(Graphics2D g2)
   {
      if (!maze.getPath().isEmpty())
      {  
         g2.setColor(PATH_COLOR);
         
         ListIterator<MazeCoord> iterator = maze.getPath().listIterator();
         
         MazeCoord mazeCoordA = iterator.next();
         
         // Define line start location coordinates
         double xCenterA = START_X + mazeCoordA.getCol() * BOX_WIDTH
               + (double) BOX_WIDTH / 2;
         double yCenterA = START_Y + mazeCoordA.getRow() * BOX_HEIGHT
               + (double) BOX_HEIGHT / 2;
         
         while (iterator.hasNext())
         {
            MazeCoord mazeCoordB = iterator.next();
            
            // Define line end location coordinates
            double xCenterB = START_X + mazeCoordB.getCol() * BOX_WIDTH
                  + (double) BOX_WIDTH / 2;
            double yCenterB = START_Y + mazeCoordB.getRow() * BOX_HEIGHT
                  + (double) BOX_HEIGHT / 2;
            
            // Create and draw line
            Line2D pathSegment =
                  new Line2D.Double(xCenterA, yCenterA, xCenterB, yCenterB);
            
            g2.draw(pathSegment);
            
            // Set mazeCoordA to mazeCoordB and define next line start location
            mazeCoordA = mazeCoordB;
            xCenterA = xCenterB;
            yCenterA = yCenterB;
         }
      }
   }
}