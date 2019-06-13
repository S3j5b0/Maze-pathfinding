package SnakeLogic;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class MazeTest {


    Maze maze;

    @Before
    public void setUp() throws Exception {
        maze = new Maze();


    }

    @Test
    public void getUnvisitedN() {
        ArrayList<Coordinate> visited = new ArrayList<>();
        Assert.assertTrue(maze.getUnvisitedN(new Coordinate(1,10), visited).size() == 1);










    }
}