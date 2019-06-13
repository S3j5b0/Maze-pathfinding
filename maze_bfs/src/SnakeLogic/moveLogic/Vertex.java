package SnakeLogic.moveLogic;
/**
 * Class for vertex, used bt the bfs to assign parents,
 * and thereby being able to generate a path by trasversing from the last vertex
 */
public class Vertex {

        int Value;
        public Vertex parent;
    public Vertex(int Value){

        this.Value = Value;


    }

}
