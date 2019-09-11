
package grafo;

import java.util.HashMap;
import java.util.Map;

public class Arista {
    //Atributos de la clase
    //Un Map de dos elementos
    //un ID arista
    HashMap <Integer, Nodo> arista=new HashMap<>(); 
    String IDarista;
    
    /*El constructor crea el la instancia de la clase
    Arista, que en este caso consiste en un objeto de tipo
    Map, con solo dos elementos correspondientes a la 
    pareja de nodos que conecta la arista*/
    public Arista (String name, int num, Nodo NodoX,Nodo NodoY)
    {
        arista.put(1, NodoX);
        arista.put(2, NodoY);
        /*El mapa "arista" es único, se comparte el mismo mapa
        con todas las instancias de la clase "Arista", no importa
        que se traten de objetos distintos con ID's diferentes, 
        todos accesan al mismo mapa "arista". Como consecuencia,
        cada que construyo un nuevo objeto de la clase "Arista"
        el mapa "arista" se modifica y guarda únicamente la 
        última combinación de parejas de nodos*/
        /*Crear un mapa "arista_{c}" distinto para cada objArista*/
        /*Al parecer el ERROR estaba en que le había puesto "static"
        al mapa "arista"*/
        IDarista=name+num;
    }
    
}
