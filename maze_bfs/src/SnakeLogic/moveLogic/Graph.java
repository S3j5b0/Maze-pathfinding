package SnakeLogic.moveLogic;
import SnakeLogic.Coordinate;
import SnakeLogic.Maze;
import javafx.geometry.Pos;

import java.util.ArrayList;

public class Graph {

    public Maze classmaze;




    public int V;
    private Maze maze;

    ArrayList<Integer> AdjList[]; // Array of type ArrayList Integer
    /**
     *the construcor for the graph. the Graph is basically a 2-d arraylist with every list shwoing the connections
     * to other vertexes. the forloop here initalizes ever list in the list
     * the fillgraph method is then called on the graph
     * @param maze takes a maze-object as a argument
     */
    public Graph(Maze maze) {

        this.maze = maze; // Number of vertices is the size of the array
        this.V = maze.sizeOfMaze;

        AdjList = new ArrayList[V]; // Initializing array with V members of type ArrayList

        // Create list for each vertex
        // stores the adjacent nodes

        for (int i = 0; i < V; i++) {
            AdjList[i] = new ArrayList<>();
        }

        this.fillGraph();  // Populates graph from maze
    }

    /**
     * Adding a new vertex the graph, or in other words, adding a number to a specified list in the 2d-arraylist
     * @param  src the number of the list, or number of vertex that we are building
     *
     * @param des the vertex that there is supposed to be a reference to
     */


    public void addEdge(int src, int des){

        AdjList[src].add(des);
    }
    /**
     * @return  a specific Vertex, and it relatiosn
     */

    public ArrayList<Integer> getAdjecentverts(int v){

        return AdjList[v];
    }

    /**
     * fill out the entire graph. loops through every vertex and if it has availabe neghbbors, they are added
     */

    public void fillGraph(){
        this.classmaze = maze;


        int adjacentVertex; // next/ adjacent field

        for (int row = 0; row < maze.numRow; row++) {
            for (int col = 0; col < maze.numCol; col++) {

                if (maze.maze[row][col] == 1) {
                    continue;
                }

                int currentVertex = row * maze.rowLength + col; //row & col to Vertex // Could use int currentVertex = posToVertex(new Position(row,col))



                //op
                if (row > 0) {
                    if (maze.maze[row - 1][col] == 0 || maze.maze[row-1][col] == 9) {
                        adjacentVertex = (row - 1) * maze.rowLength + col;
                        this.addEdge(currentVertex,adjacentVertex); // Will initiate graph and add edge
                    }
                }
                //højre
                if (col < maze.numCol-1) {
                    if (maze.maze[row][col + 1] == 0 || maze.maze[row][col + 1] == 9) {
                        adjacentVertex = row * maze.rowLength + (col + 1);
                        this.addEdge(currentVertex,adjacentVertex);
                    }
                }
                //vesntre
                if (col > 0) {
                    if (maze.maze[row][col - 1] == 0 || maze.maze[row][col - 1] == 9) {
                        adjacentVertex = row * maze.rowLength + (col - 1);
                        this.addEdge(currentVertex,adjacentVertex);
                    }
                }
                //ned
                if (row < maze.numRow-1) {
                    if (maze.maze[row + 1][col] == 0 || maze.maze[row + 1][col] == 9) {
                        adjacentVertex = (row + 1) * maze.rowLength + col;
                        this.addEdge(currentVertex,adjacentVertex);
                    }
                }
            }
        }
    }
    /**
     *looping through the 2d-arraylist and printing all the lists
     */

    public void printGraph(Graph graph){
        for (int v = 0; v < graph.V ; v++) { //Looping through each v

            System.out.println("adj index" + v);
            System.out.println();
            Coordinate tempc = converttoCord(v);
            System.out.println("Adjacency list of vertex "+ v + " (" + tempc.getX() + "," + tempc.getY() + ")");


            for(Integer i: graph.AdjList[v]){ // For each v print the adj list
                Coordinate tempi = converttoCord(i);
                System.out.print(" -> "+i +  " (" + tempi.getX() + "," + tempi.getY() + ")");
            }
            System.out.println("\n");
        }
    }

    /**
     *converting V-values to coordinates, so they are usable in pathfinding
     */
    public Coordinate converttoCord(int V){
        int x = V%classmaze.rowLength;
        int y = (V-x)/classmaze.rowLength;
        Coordinate cord = new Coordinate(y, x);

        return cord;
    }

}