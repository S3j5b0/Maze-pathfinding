package SnakeLogic;

import java.util.ArrayList;
/**
 *class for Coordinates, contains a few booleans used in the dfs
 */
public class Coordinate implements Position {

    protected int X;
    protected int Y;
    public boolean  isup;
    public boolean  isdown;
    public boolean  isright;
    public boolean isLeft;
    public boolean Goal;





    public Coordinate(int X, int Y){
        this.X = X;
        this.Y = Y;
    }

    @Override
    public boolean equals(Object obj) {
        //return object2 instanceof MyClass && a.equals(((MyClass)object2).a);

        return obj instanceof Coordinate && X == ((Coordinate) obj).getX() && Y == ((Coordinate) obj).getY();
    }

    @Override

    public String toString(){
       String result = "Coordinat: "+ X + "," + Y ;
        return result;
    }


    public ArrayList<Coordinate> getNeigbors(Coordinate cord, Maze maze, ArrayList<Coordinate> visiteds){
        ArrayList<Coordinate>  neighbors = new ArrayList<>();

        int X = cord.getX();
        int Y = cord.getY();

        Coordinate upCord = new Coordinate(X, Y-1); // cordinate for up
        Coordinate downCord = new Coordinate(X, Y+1);// coordinate for down
        Coordinate leftCord = new Coordinate(X-1, Y); // coordinate for left
        Coordinate rightCord = new Coordinate(X+1, Y); // cordinate for right

        if(visiteds.contains(downCord)){
            System.out.println("contains is in visited");
        }
        System.out.println("size of visiteds" + visiteds.size());

        int[][] Maze2D = maze.get_maze();
        // up
        if (Maze2D[X][Y-1] != 1 &&  !visiteds.contains(upCord))
        {
            neighbors.add(upCord);
        }
        // down
        if (Maze2D[X][Y+1] != 1 && !visiteds.contains(downCord))
        {
            neighbors.add(downCord);
        }
        // left
        if (Maze2D[X-1][Y] != 1 && !visiteds.contains(leftCord))
        {
            neighbors.add(leftCord);
        }
        // right
        if (Maze2D[X+1][Y] != 1 && !visiteds.contains(rightCord))
        {

            neighbors.add(rightCord);
        }



        return neighbors;
    }



    public int getX() { return X; }

    public int getY() {return Y; }
}
