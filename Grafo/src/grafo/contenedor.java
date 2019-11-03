
package grafo;

/**
 *
 * @author F5-573
 */
 public class contenedor
{
    /*Objeto que contiene el nodo adyacente con el respectivo peso
    de la arista que está incidiendo en él. Puede haber más de una
    arista incidente, por lo que un solo nodo puede manejar el peso
    de todas las aristas que inciden en él.*/
    Nodo nodAd;
    float w;
    int ID;
    String IDs;
    //constructor
    public contenedor (int id)
    {
        ID=id; //usar un ID para identificar multiples contenedores
    }
    public contenedor (String id, int n)
    {
        IDs=id+n;
    }
}
