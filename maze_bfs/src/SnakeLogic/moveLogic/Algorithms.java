package SnakeLogic.moveLogic;

import SnakeLogic.ExtendCord;
import SnakeLogic.Coordinate;
import SnakeLogic.Maze;
import SnakeLogic.gameobjects.Player;
import SnakeLogic.Pathpair;


import java.util.*;

public class Algorithms extends Player {


    ArrayList<Coordinate> visited = new ArrayList<>();
    Stack<ExtendCord> closedPath = new Stack<>();




    /**
     *depth first-search that works by traversing through the maze with a while loop that stops when it hits the end-coordinate
     *A stack is used to keep track of all the paths that are walked down, and if a dead end is hit, the pathstack, is popped
     * until it hits a node that has free neighbors, it then keeps going from there
     * When this algorithm is done, it returns this path
     * @param maze  the maze/ that we want to walk on
     * @param  startCord the coordinate that we are starting from
     * @param end the Endcord, or our succescriterie
     * @return a walkable finished path
     */


    public  static  Pathpair dfs(Maze maze, Coordinate startCord, Coordinate end){

        ArrayList<Coordinate> visited = new ArrayList<>();
        Stack<Coordinate> path = new Stack<>(); // vores endelige vej
        Stack<Coordinate> fullpath = new Stack<>(); // analysevejen

        Coordinate current = startCord;
        while (!current.equals(end)){ // så længe vi ikke er nået til enden

            if (!visited.contains(current)){ // hvis
            path.add(current);} // vi tilføjer det sted vi står til vores path
            fullpath.add(current); // og også til vores fullpath (måske ikke nødvendig)
            visited.add(current); // og også til vores visited

            ArrayList<Coordinate> Neighbors = current.getNeigbors(current, maze, visited);

            if(Neighbors.size() > 0 ){ // vi tager den første nabo som er tilgængelig
            current = Neighbors.get(0);}

            if(Neighbors.size() == 0){

                path.pop(); // hvis der ikke er nogle steder at gå hen, popper vi fra path, indtil vi nå¨r et sted med flere 'muligheder'
                current = path.peek();

            }
        }
        Pathpair returnpair = new Pathpair(fullpath, path);
        return returnpair;
    }
    /**
     *Astar looks at the starting positions neigbors and picks the one with the lowest F, this Node is added to the closedlist
     * All Nodes that arent put on the closed list, but still evaluated, are stored in the closed list
     * @param start where we start from
     * @param end where we end/the succes criterie
     * @param maze the maze that is traversed
     * @return two paths contained in a pathpair object, one that shows analysis, and one that show the path generated
     */
    public static  Pathpair AStar(Coordinate start, Coordinate end, Maze maze){

        ExtendCord firstA = new ExtendCord(start.getX(), start.getY(), start, end); // starting Astar-coordinate
        ArrayList<ExtendCord> Allvisits = new ArrayList<>(); // liste som skal holde styr på alt som vi har besøgt
        ExtendCord Aend = new ExtendCord(end.getX(), end.getY(), start, end); // slutkordinat
        PriorityQueue<ExtendCord> openList = new PriorityQueue<>();  // putting the starting node on the open list. always sorted

        Allvisits.add(firstA);
        openList.add(firstA); // adding the very first coordinate to the openlist
        ArrayList<ExtendCord> closedList = new ArrayList<>(); // closed list, for visited nodes


        ExtendCord current = null; // vi sætter den bare lig med null i starten
        while (!Aend.equals(current)){ // succes condition
            current = openList.poll(); // Det forreste element i vores openlist er også den laveste f-værdi, derfor går vi derhen
            closedList.add(current); // nu 'står' vi på current, derfor skal den i closedlist

            ArrayList<ExtendCord> Neighbors;
            Neighbors = current.getAstarNeigbors(current, maze , Allvisits, start, end); // vi bruger extendcords nabo-funktion

            Allvisits.addAll(Neighbors);
            openList.addAll(Neighbors); // alle naboer addes til openlist
        }
        Stack<ExtendCord> closedpath = generateClosedlist(closedList);
        Stack<ExtendCord> path = generateAstarpath(current); // traverser baglæns ved at poppe stack. Her bruger vi Extendcords reference til en parent

        Pathpair resultpaths = new Pathpair(closedpath, path,0 ); // et pathpairobjekt til returnering

        return resultpaths;

    }
    /**
     *reversing the closedlist
     */
    public static Stack<ExtendCord> generateClosedlist(ArrayList<ExtendCord> closedList){

        int index = 0;


        Stack<ExtendCord> closedpath = new Stack<>();
        for (int i = 0; i < closedList.size()-1; i++) {
            ExtendCord Acord = closedList.get(index);

            closedpath.push(Acord);

            index++;
        }

        return closedpath;

    }
    /**
     *generating the actual path of the Astar algorithm by simply taking the last current node in the algorithm, and
     * traversing backwards, by visiting all Vertex's parents, until the parent i null/ unitl we have reached the
     * original starting point
     * .
     */
    private static Stack<ExtendCord> generateAstarpath(ExtendCord last){

        Stack<ExtendCord> path = new Stack<>();

        System.out.println("path:");

        ExtendCord A = last;
        while(A != null){
            path.push(A);


            System.out.println(A.toString());

            A = A.parent;

        }
    return path;
    }

    /**
     *the bfs algorithm. traversing the 2d-arraylist that is our graph, we walk trough each vertex, and visit all its
     * unvisited Neighbors. parents are generated meanwhile
     * @param graph object
     * @param v the 'V' that we are starting from
     * @param end the ending coordinate/ the suces criteria
     */
    public static Pathpair bfs(int v, Graph graph, Coordinate end){

        Vertex vObj = new Vertex(v); // den vertex som vi starter på

        Vertex vert = new Vertex(0); // en placeholder

        Stack<Integer> path = new Stack<>(); // path som vi vil generere, indtil videre tom

        Stack<Integer> finalpathV; // den "rigtige" path, altså den som man traverser tilbage med
        Stack<Coordinate> finalpath = new Stack<>(); // den samme, bare med kordinater

        LinkedList<Vertex> queue = new LinkedList<>(); // en queue til at holde styr på alle de 'næste' elementer
        Stack<Coordinate> cordPath = new Stack<>(); // en kordinatversion af analysepathen

        boolean[] visited = new boolean[graph.V];  // et array af booleans som vi kan bruge til at holde styr på pladser som vi har vært på

        visited[vObj.Value] = true; // vi starter på vobj, derfor er den true
        queue.add(vObj); // og vi tilføjer den til queue

        while(!queue.isEmpty()){ // itererer igennem Verts

            vObj = queue.poll(); // removing first Node



            path.add(vObj.Value); // tilføjer til path

            Coordinate tempcord = graph.converttoCord(vObj.Value); // vi får et kordinat ud af de elementer vi går på

            cordPath.add(tempcord);

            if(tempcord.equals(end)){ // vi bryder ud af dette loop hvis vi er nået enden
                break;
            }

            for (int u: graph.getAdjecentverts(vObj.Value)) { // vi finder den relevante verts, så vi
                if (!visited[u]){ // vi kigger i listen af booleans om vi har gået på den før
                    visited[u] = true; // vi tjekker om vi har gået på denne før
                    vert = new Vertex(u); // vi laver et vertex object
                    vert.parent = vObj; // sætter den forrige vertex til parent
                    queue.add(vert); // vi tilføjer denne til qeueue så vi kan gå videre på dens ledige naboer
                }
            }
        }

        finalpathV = getbfspath(vert); // Når vi er nået endepunktet, kan vi igennem parents traverse tilbage og sådan finde den 'rigtige' path
        for (Integer I :finalpathV) {
            Coordinate tempC = graph.converttoCord(I);
            finalpath.add(tempC);
        }

        Pathpair pair = new Pathpair(cordPath, finalpath);

        return  pair;



    }
    /**
     * traversing backwards from the the last vertexpoint, while-looping through until the current vertex has no parent
     *
     */

    public static Stack<Integer> getbfspath(Vertex vert){

        Stack<Integer> truepathV = new Stack<>();

        while (vert != null){

            truepathV.add(vert.Value);

            vert = vert.parent;
        }

        return truepathV;

    }









}
