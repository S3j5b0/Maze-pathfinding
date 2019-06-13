/**
 * @author      Ask Sejsbo <64033 harup@ruc.dk>
 * @version     1.4 EC2 examproject 01/05/2019
 */

package SnakeGUI;

import SnakeLogic.*;

import SnakeLogic.gameobjects.Item;
import SnakeLogic.gameobjects.Walls;
import SnakeLogic.gameobjects.ghost;
import SnakeLogic.moveLogic.Algorithms;
import SnakeLogic.moveLogic.*;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

import java.util.*;

public class Controller {

    @FXML
    Label labelStatus;
    @FXML
    Canvas canvas;


    public Coordinate EndCord;

    private double fieldHeight;
    private double fieldWidth;
    private int width = 47;// 9
    private int height = 33; // 9
    private float refreshRate =300;
    //private Player player = new Player(); // PlayerWASD
    private SnakeLogic.gameobjects.ghost bfsghost = new ghost();
    private SnakeLogic.gameobjects.ghost dfsghost = new ghost();
    private SnakeLogic.gameobjects.ghost Astarghost = new ghost();
    //walked paths:






    private KeyCode keyPressed = KeyCode.BACK_SPACE;

    ArrayList<Item> items = new ArrayList<>(); // ArrayOfItems
    ArrayList<Walls> walls = new ArrayList<>(); // ArrayOfWalls



    //Walls[][] walls = new Walls [][];
    Maze maze = new Maze();
    int V = maze.sizeOfMaze;
    int[][] Maze = maze.get_maze();
    Graph graf;

    private Stack<Coordinate> bfspathOne;
    private Stack<Coordinate> bfspathTwo;

    private Stack<Coordinate> dfspathOne;
    private Stack<Coordinate> dfspathTwo;
    private Stack<ExtendCord> firstAstarpath;
    private Stack<ExtendCord> SecondAstarpath;

    /**
     * the program will run all over again when the button is pressed
     *
     */

    public void btnStartAction(ActionEvent event)
    {
        System.out.println("btn clicked");

        ClearAll();
        initialize();



    }

    public void initialize() // all initialising
    {

        AddItems();
        calculateFields();
        setup_gameobjects();

        // Start and control game loop
        new AnimationTimer(){
            long lastUpdate;
            public void handle (long now)
            {
                if (now > lastUpdate + refreshRate * 1000000)
                {
                    lastUpdate = now;
                    update(now);
                }             }
        }.start();
    }
    /**
     * simply clearing a re-initializing all lists, so that the program can run once more
     *
     */
    private void ClearAll(){
        bfsghost = new ghost();
        dfsghost = new ghost();
        Astarghost = new ghost();

        items = new ArrayList<>(); // ArrayOfItems
        walls = new ArrayList<>(); // ArrayOfWalls
        maze = new Maze();
        V = maze.sizeOfMaze;
        Maze = maze.get_maze();

    }
    /**
     * Looping through the maze (2D array of ints) and adding
     *
     */
    private void AddItems() {
        for (int row = 0; row < Maze.length; row++) {
            for (int col = 0; col < Maze[0].length; col++) {
                switch (Maze[row][col]) {
                    case 1 : walls.add(new Walls(Color.BLACK,row,col)); break;
                    case 9 : items.add(new Item(Color.LIGHTGREEN, row,col)); EndCord = new Coordinate(row, col); break;
                    case 3 :items.add(new Item(Color.GREEN, row,col)); break;
                    default : walls.add(new Walls(Color.LIGHTGREY,row,col)); break;
                }
            }
        }
    }

    public void keyPressed(KeyCode keyCode)
    {
        //System.out.println("key pressed: " + keyCode);
        this.keyPressed = keyCode;
    }

    /**
     * Game loop - executed continously during the game
     * @param now game time in nano seconds
     */
    private void update(long now) {
        if(bfsghost.firstphase){
        followpath(bfspathOne, bfsghost);}
        else{followpath(bfspathTwo, bfsghost); }

        if (dfsghost.firstphase){
        followpath(dfspathOne, dfsghost);} else {
            followpath(dfspathTwo, dfsghost);
        }

        if (Astarghost.firstphase){
            followpath(firstAstarpath, Astarghost);}else {
            followpath(SecondAstarpath, Astarghost);}

        drawCanvas();
    }




    /**
     * Calculate height and width of each field
     */
    private void calculateFields() {
        this.fieldHeight = canvas.getHeight() / this.getHeight();
        this.fieldWidth = canvas.getWidth() / this.getWidth();
    }
    /**
     * Accespoint for the Astar algorithm
     * <p>
     * calling the Astar-algorithm from our A*-ghosts, startingpoint
     * </p>
     * @return returns a pathpair object, which contains two Stacks that are walkable paths
     */

    public Pathpair Astar(){

        this.Astarghost.startinpos = new Coordinate(this.Astarghost.getX(), this.Astarghost.getY());

        Pathpair twopaths= Algorithms.AStar(Astarghost.startinpos, EndCord, maze);

        return twopaths;

    }

    /**
     * Accespoint for bfs-algorithm
     * <p>
     * initializing a graph, that is passed as a parameter in our bfs algorithm
     * </p>
     * @return A pathpair object with two walkable paths
     */
    public Pathpair bfs(){

        this.bfsghost.startinpos = new Coordinate(this.bfsghost.getX(), this.bfsghost.getY());


        graf = new Graph(maze);

        graf.printGraph(graf);



        Pathpair twopaths = Algorithms.bfs(68, graf, EndCord);


       return twopaths;
    }
    /**
     * Accespoint for dfs algorithm
     * <p>
     * dfs- algorithm, this algorithm only shows the generated path and not the analysis-part.
     * this is because the visualization becomes very visually confusing when all 3 are showing at the same time
     * </p>
     * @return just returns one walkable path (Stack of Cords)
     */
    private Pathpair dfs(){
        this.dfsghost.startinpos = new Coordinate(this.dfsghost.getX(), this.dfsghost.getY());

        Pathpair dfspath = Algorithms.dfs(maze, dfsghost.startinpos, EndCord);

        return dfspath;

    }

    /**
     * A general method for ghosts to walk specific paths
     * <p>
     * takes a path as argument, and makes a specific ghost follow this path.
     * clears the stack, when the traversing is finished
     * </p>

     * @param  thispath the path that you would like the ghost to walk
     * @param thisghost the ghost that you would like to walk the path
     */

    private void followpath(Stack<? extends Position> thispath, ghost thisghost){

        if(!thispath.isEmpty()) {
            int X = thispath.elementAt(0).getX();
            int Y = thispath.elementAt(0).getY();

            Coordinate Current =  new Coordinate(X,Y);
            thispath.remove(0);

            thisghost.walked.add(Current);


            thisghost.setX(Current.getX());

            thisghost.setY(Current.getY());
        }

        if (thispath.size() == 0){

             thisghost.pathFound = true;
             thisghost.walked.clear();
             thisghost.firstphase = false;
        }



    }

    /**
     * Setting the positions of all the ghosts.
     * Also generating the paths that are to be walked later
     */
    private void setup_gameobjects() {
        this.bfsghost.setX(2);
        this.bfsghost.setY(2);

        this.dfsghost.setX(2);
        this.dfsghost.setY(30);

        this.Astarghost.setX(27);
        this.Astarghost.setY(2);



        if (!bfsghost.pathFound){
            bfspathOne = bfs().analysispath;
            bfspathTwo = bfs().path; }

        if (!dfsghost.pathFound){
            dfspathOne = dfs().analysispath;
            dfspathTwo = dfs().path;
        }

        if (!Astarghost.pathFound){
            firstAstarpath = Astar().analysispathE;
            SecondAstarpath = Astar().pathE;
        }
    }


    /**
     * Drawing on my canvas,
     * <p>every time a 'loop' in my game happens, this method is called and the graphics (colors, sizes shapes etc.)off
     * all my objects are specified and drawn
     *
     * <p/>
     */

    private void drawCanvas() {
        GraphicsContext g = canvas.getGraphicsContext2D();


        // draw items
        for (Item item : items) // runs for every element -
        {
            g.setFill(item.getColor());
            g.fillRoundRect(item.getX() * fieldWidth, item.getY() * fieldHeight, fieldWidth, fieldHeight, 5,5);
        }
        // drawing the walls
        for (Walls walls : walls)
        {
            g.setFill(walls.getColor());
            g.fillRoundRect(walls.getX() * fieldWidth, walls.getY() * fieldHeight, fieldWidth, fieldHeight,0,0);
        }

        // drawing walked paths
        for (Coordinate draw:bfsghost.walked) {
            g.setFill(Color.GOLD);
            g.fillRoundRect(draw.getX()* fieldWidth, draw.getY()* fieldHeight, fieldWidth, fieldHeight, 3,3 );
        }
        for (Coordinate draw:Astarghost.walked) {
            g.setFill(Color.PINK);
            g.fillRoundRect(draw.getX()* fieldWidth, draw.getY()* fieldHeight, fieldWidth, fieldHeight, 3,3 );
        }

        // drawing paths
        g.setFill(Color.RED);
        g.fillRoundRect(this.dfsghost.getX() * fieldWidth, this.dfsghost.getY() * fieldHeight, fieldWidth, fieldHeight, 3, 3);

       g.setFill(Color.YELLOW);
        g.fillRoundRect(this.bfsghost.getX() * fieldWidth, this.bfsghost.getY() * fieldHeight, fieldWidth, fieldHeight, 3, 3);

        g.setFill(Color.PINK);
        g.fillRoundRect(this.Astarghost.getX() * fieldWidth, this.Astarghost.getY() * fieldHeight, fieldWidth, fieldHeight, 3, 3);





    }


    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

}