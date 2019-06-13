package SnakeLogic;
import java.util.ArrayList;
import java.util.Stack;
/**
 *A subclass of Coordinate, that is used by the Astar algorithm
 */
public class ExtendCord extends Coordinate implements Comparable<ExtendCord> {
    public int Value;
    private int G;
    private int H;
    private int F;
    private Coordinate start;
    private Coordinate end;
    public ExtendCord parent;

    public ExtendCord(int X, int Y, Coordinate start, Coordinate end) {
        super(X, Y);
        this.start = start;
        this.end = end;
        this.G = finddist(start);
        this.H = finddist(end);
        this.F = getF();


    }

    /**
     *finds the distance beetween the current object, and another cordinate. Used for getting G and H values
     */

    private int finddist(Coordinate point) {
        int pointX = point.getX();
        int pointY = point.getY();
        int distX;
        int distY;

        int tempdist = pointX-this.X;
        distX = tempdist + (2*(-1*tempdist)); // used to keep all G and G vakues positive





        tempdist = pointY-this.Y;
        distY = tempdist + (2*(-1*tempdist));


        double hypoteneuse = Math.pow(distX, 2) + Math.pow(distY, 2);

        hypoteneuse = Math.sqrt(hypoteneuse);

        int localG = (int) hypoteneuse;



        return localG;
    }
  private int getF(){

        int F = this.G + this.H;

        return F;
    }



    @Override
    public String toString(){
        String result = "Coordinat: ("+ X + "," + Y + ") H: " + H + " G:  " + G + " F: " + F;
        return result;
    }
    /**
     *Using the getNeighbors from the superclass, only converting Extendscords to Coordinates and back again
     */
    public ArrayList<ExtendCord> getAstarNeigbors(ExtendCord Acord, Maze maze, ArrayList<ExtendCord> allvisits, Coordinate start, Coordinate end){
        Coordinate tempCord = new Coordinate(Acord.getX(), Acord.getY());
        ArrayList<Coordinate> allCvisits = new ArrayList<>();



        for (ExtendCord A:allvisits) {
            allCvisits.add(new Coordinate(A.getX(), A.getY()));

        }
        ArrayList<Coordinate> Cordlist = getNeigbors(tempCord, maze, allCvisits);

        ArrayList<ExtendCord> aStarlist = new ArrayList<>();

        for (Coordinate c: Cordlist) {
            ExtendCord tempA = new ExtendCord(c.getX(), c.getY(),start, end);
            tempA.parent = Acord;
            aStarlist.add(tempA);

        }

        return aStarlist;
    }
    /**
     *overrriding compateTo, so that Extnedscords can be compared by their F-values
     */
    @Override
    public int compareTo(ExtendCord o) {
        if (F < o.getF()){return -1;  }
        else if (F > o.getF() ) { return 1;}

        return 0;
    }
    public static  Stack<Coordinate> converttocord (Stack<ExtendCord> Astarpath)
    {
        Stack<Coordinate> Acordpath = new Stack<>();
        for (ExtendCord A: Astarpath) {
            Coordinate tempCord = new Coordinate(A.getX(), A.getY());

            Acordpath.push(tempCord);

        }

        return Acordpath;
    }


}
