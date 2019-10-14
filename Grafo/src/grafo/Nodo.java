
package grafo;

import java.util.LinkedList;

public class Nodo {
    //Atributos: Identificador de Nodo
    String IDnodo;
    double x,y;
    int grado;
    boolean explorado; //todos los nodos en automático son "NO explorados"
    LinkedList <Nodo> adyList = new LinkedList <>();
    
    //Métodos de la clase Arista
    public Nodo ()
    {
        //Constructor vacío.
        explorado=false;
    }
    public Nodo (String name, int i)
    {
        /*Constructor para instancias de clase Nodo que
        únicamente crea al objeto con un ID único*/
        IDnodo=name+i;
        explorado=false;
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
    }
}
