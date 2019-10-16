
package grafo;

import java.util.LinkedList;
import java.util.HashMap;

public class Nodo {
    //Atributos: Identificador de Nodo
    String IDnodo;
    double x,y;
    int grado;
    //Utiles para los árboles BFS y DFS
    boolean explorado; //todos los nodos en automático son "NO explorados"
    LinkedList <Nodo> adyList = new LinkedList <>();
    //Utiles para Dijkstra
    LinkedList <contenedor> adyList2 = new LinkedList <>();
    float dist; //distancia hacia el nodo, se inicializan en infinito.
    Nodo prev; //Nodo previo "u" (u,v), para construir el arbol de Dijkstra.
    //float w; //Peso de la arista (u,v), donde "v" es este mismo Nodo y "u" el anterior
    
    //Métodos de la clase Nodo
    public Nodo ()
    {
        //Constructor vacío.
        explorado=false;
        dist=Float.POSITIVE_INFINITY;
    }
    public Nodo (String name, int i)
    {
        /*Constructor para instancias de clase Nodo que
        únicamente crea al objeto con un ID único*/
        IDnodo=name+i;
        explorado=false;
        dist=Float.POSITIVE_INFINITY;
    }
    public Nodo (String name, int i, double X, double Y)
    {
        /*Constructor para instancias de la clase Nodo que
        crea un objeto con un ID único y una coordenada (x,y)
        en un espacio unitario*/
        IDnodo=name+i;
        x=X;
        y=Y;    
        explorado=false;
        dist=Float.POSITIVE_INFINITY;
    }
}
