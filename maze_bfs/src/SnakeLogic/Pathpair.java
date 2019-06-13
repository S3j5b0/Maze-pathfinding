package SnakeLogic;

import java.util.Stack;
/**
 * pathpair: contains two paths of walkable paths in the form of stacks.
 * the class's constructor is overloaded, to both take Stacks of both Coordinates, but also ExtendCords
 *
 */
public class Pathpair {


    public Stack<Coordinate> analysispath;
    public Stack<Coordinate> path;

    public Stack<ExtendCord> pathE;
    public Stack<ExtendCord> analysispathE;


    public Pathpair(Stack<Coordinate> analysispath, Stack<Coordinate> path)
    {
        this.analysispath = analysispath;
        this.path = path;
    }

        public Pathpair(Stack<ExtendCord> analysispathE, Stack<ExtendCord> pathE, int D)
        {
            this.analysispathE = analysispathE;
            this.pathE = pathE;
        }





}
