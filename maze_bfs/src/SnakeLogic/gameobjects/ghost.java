package SnakeLogic.gameobjects;

import SnakeLogic.Coordinate;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

public class ghost extends Player {
    /**
     *the ghosts, that are used to draw and move around the game
     */
    public boolean  pathFound = false;

    public Coordinate startinpos;
    public ArrayList<Coordinate> walked = new ArrayList<>();

    public  boolean firstphase = true;


}





