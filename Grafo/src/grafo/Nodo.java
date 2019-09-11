
package grafo;

public class Nodo {
    //Atributos: Identificador de Nodo
    String IDnodo;
    double x,y;
    int grado;

    public Nodo (String name, int i)
    {
        /*Constructor para instancias de clase Nodo que
        únicamente crea al objeto con un ID único*/
        IDnodo=name+i;
    }
    public Nodo (String name, int i, double X, double Y)
    {
        /*Constructor para instancias de la clase Nodo que
        crea un objeto con un ID único y una coordenada (x,y)
        en un espacio unitario*/
        IDnodo=name+i;
        x=X;
        y=Y;        
    }
}
