package grafo;

import java.util.HashMap;
import java.util.LinkedList;
import java.io.FileWriter;
import java.io.IOException;

public class Grafo {

//Atributos generales del Grafo
    /*Tiene como atributos dos objetos que corresponden a:
    -Conjunto de objetos de la clase Nodo
    -Conjunto de objetos de la clase Arista*/
    
    static HashMap <Integer, Nodo> conjNodos= new HashMap<>();
    static HashMap <Integer, Arista> conjAristas= new HashMap<>();
    //Usar este para búsqueda de aristas repetidas
    static HashMap <Integer, String> conj= new HashMap<>();
    //Conjunto R de aristas para método que obtiene el árbool DFS. 
    static LinkedList <Arista> R = new LinkedList<>();
    int aDFS=1; //contador para aristas en arbolDFS_R
    static String par1, par2, escritura; //para escribir el archivo .txt
    
    /*----------MÉTODOS PARA LA GENERACIÓN DE GRAFOS ALEATORIOS----------*/   
    
    public static Grafo genErdosRenyi(int n, int m,boolean diri,boolean auto) throws IOException 
    {
        Grafo objGrafo=new Grafo(); //crea el objeto grafo
        /*Método que construye un Grafo con el método de Erdos-Renyi, donde
        n es el número de nodos, m es el número de aristas, "auto" indica 
        si el grafo permite autociclos y "diri" indica si el grafo es dirigido*/
        String nameGrafo="grafoErdosRenyi"; //Para crear el archivo con el nombre correcto
        Grafo.construirE(n); //construye el conjunto de nodos
        
        //CONSTRUYE EL CONJUNTO DE ARISTAS
        int c=1; //contador para numero de aristas
        //ciclo para construir el conjunto de aristas 
        while(conjAristas.size()<m)
        {
            /*se eligen dos números al azar de entre 1 y n*/
            int k1=(int)(Math.random()*n)+1;
            int k2=(int)(Math.random()*n)+1;
            
            /*crea el objeto arista, siendo un mapa con una 
            pareja de nodos y un ID de arista*/
            Arista objArista=new Arista("A",c,conjNodos.get(k1),conjNodos.get(k2)); 
            par1=k1+"--"+k2;
            par2=k2+"--"+k1;/*crea un string con la pareja de nodos seleccionada
            para la arista, se usa para identificarla más tarde y buscar que 
            no se repita la misma arista en el conjunto*/
            if(auto) //SI autociclos
            {
                if(diri) //Grafo DIRIGIDO
                {
                   if(conj.containsValue(par1))
                    {
                        /*si la pareja de nodos elegida al azar ya existe en el conjunto
                        no se asigna nuevamente a este*/                    
                    }
                    else
                    {
                        //coloca el objeto arista en el conjunto de aristas.
                        conjAristas.put(c, objArista); 
                        conj.put(c, par1); /*coloca la pareja de nodos de la arista
                        donde se busca más fácilmente*/
                        c++; //incrementa el índice para las claves de "aristas"
                    } 
                }
                else if(conj.containsValue(par1)||conj.containsValue(par2))//Grafo NO DIRIGIDO
                {
                    /*si la pareja de nodos elegida al azar o una combinación 
                    de estas ya existe en el conjunto no se asigna nuevamente*/  
                }
                else //Continuación de la condición de grafo NO DIRIGIDO
                {
                    //coloca el objeto arista en el conjunto de aristas.
                    conjAristas.put(c, objArista); 
                    conj.put(c, par1); /*coloca la pareja de nodos de la arista
                    donde se busca más fácilmente*/
                    c++; //incrementa el índice para las claves de "aristas"
                }
            }
            else //NO autociclos
            {
                if(k1==k2) //condición que descarta directamente todos los autociclos
                {
                    /*Si la pareja de nodos elegida para la arista es el mismo
                    nodo, no se guarda la arista en el conjunto. No importa
                    si el grafo es dirigido o no, esta primera condición 
                    descarta la posibilidad de crear la arista, independientemente
                    de si la pareja ya se encuentra en el conjunto o no*/
                }
                else //evaluar si el grafo es dirigido o no
                {
                    if(diri) //Grafo DIRIGIDO
                    {
                        if(conj.containsValue(par1))
                        {
                            /*si la pareja de nodos elegida al azar ya existe en el conjunto
                            no se asigna nuevamente a este*/
                        }
                        else
                        {
                            //coloca el objeto arista en el conjunto de aristas.
                            conjAristas.put(c, objArista);  
                            conj.put(c, par1); /*coloca la pareja de nodos de la arista
                            donde se busca más fácilmente*/
                            c++; //incrementa el índice para las claves de "aristas"
                        }
                    }
                    else if(conj.containsValue(par1)||conj.containsValue(par2)) //Grafo NO DIRIGIDO
                    {
                        /*Si una combinación de la pareja 
                        de nodos creada ya existe en el conjunto
                        esta no se asigna al conjunto de aristas*/
                    }
                    else //condición restante del grafo no dirigido
                    {
                        //coloca el objeto arista en el conjunto de aristas.
                        conjAristas.put(c, objArista); 
                        conj.put(c, par1);
                        c++;
                    }
                }
            }
        }
        escribirTxt(m,diri,nameGrafo);  //crea el archivo grafoErdosRenyi.gv
        return objGrafo;
    }
    
    public static Grafo genGilbert(int n, double p, boolean diri,boolean auto) throws IOException 
    {
        Grafo objGrafo=new Grafo(); //crea el objeto grafo
        /*Método que construye un Grafo con el método de Gilbert, donde
        n es el número de nodos, p es la probabilidad de formar una arista, "auto" indica 
        si el grafo permite autociclos y "diri" indica si el grafo es dirigido*/
        String nameGrafo="grafoGilbert"; //Para crear al archivo con el nombre correcto
        Grafo.construirE(n); //construye el conjunto de nodos

        //CONSTRUIR EL CONJUNTO DE ARISTAS
        
        int c=1; //contador para numero de aristas
        for(int i=1;i<=n;i++)
        {
            for (int j=1;j<=n;j++)
            {
                double r=Math.random();                
                Arista objArista=new Arista("A",c,conjNodos.get(i),conjNodos.get(j));
                par1=i+"--"+j;
                par2=j+"--"+i;/*crea un string con la pareja de nodos seleccionada
                para la arista, se usa para identificarla más tarde y buscar que 
                no se repita la misma arista en el conjunto*/
                if (r<=p) /*si el aleatorio fue mayor o igual que la 
                probabilidad "p" entonces se crea la arista, o mejor dicho, se conserva*/
                {
                    if (auto) //SI autociclos
                    {
                        if (diri) //Grafo DIRIGIDO
                        {
                            //coloca el objeto arista en el conjunto de aristas.
                            conjAristas.put(c, objArista); 
                            conj.put(c, par1);
                            c++; 
                        }
                        else if(conj.containsValue(par2)) //Grafo NO DIRIGIDO
                        {
                           /*Si una combinación de la pareja 
                            de nodos creada ya existe en el conjunto
                            esta no se asigna al conjunto de aristas*/
                        }
                        else //condición restante del grafo no dirigido
                        {
                            //coloca el objeto arista en el conjunto de aristas.
                            conjAristas.put(c, objArista); 
                            conj.put(c, par1);
                            c++;
                        }
                    }
                        
                    else //NO autociclos
                    {
                        if(i==j) //condición que descarta directamente todos los autociclos
                        {
                            /*Si la pareja de nodos elegida para la arista es el mismo
                            nodo, no se guarda la arista en el conjunto. No importa
                            si el grafo es dirigido o no, esta primera condición 
                            descarta la posibilidad de crear la arista, independientemente
                            de si la pareja ya se encuentra en el conjunto o no*/
                        }
                        else //caso para evaluar si es dirigido o no dirigido
                        {
                            /*En caso de que la pareja elegida esté formada por
                            nodos distintos, evaluar si el grafo es dirigido o no*/
                            
                            if(diri) //Grafo DIRIGIDO
                            {
                                /*En caso de que el grafo sea dirigido admite cualquier
                                combinación de parejas de nodos, por lo tanto, coloca 
                                el objeto arista en el conjunto de aristas.*/
                                conjAristas.put(c, objArista);
                                conj.put(c, par1);
                                c++; //incrementa el contador para las claves de aristas
                            }
                            else if(conj.containsValue(par2)) //Grafo NO DIRIGIDO
                            {
                                /*Si una combinación de la pareja 
                                de nodos creada ya existe en el conjunto
                                esta no se asigna al conjunto de aristas*/
                            }
                            else //condición restante del grafo NO DIRIGIDO
                            {
                                //coloca el objeto arista en el conjunto de aristas.
                                conjAristas.put(c, objArista); 
                                conj.put(c, par1);
                                c++; //incrementa el contador para las claves de aristas
                            }
                        }
                    }
                }                                               
            }
        }        
        int m=conjAristas.size(); //calcula el número de aristas en el grafo
        escribirTxt(m,diri,nameGrafo);  //crea el archivo grafoGilbert.gv
        return objGrafo;
    }
    
    public static Grafo genGeografico(int n, double r, boolean diri, boolean auto) throws IOException
    {
        Grafo objGrafo=new Grafo(); //crea el objeto grafo
        /*Método que construye un Grafo con el método Geográfico simple, donde
        n es el número de nodos, r es la distancia mínima para formar una arista, "auto" indica 
        si el grafo permite autociclos y "diri" indica si el grafo es dirigido*/
        
        String nameGrafo="grafoGeografico"; //Para crear al archivo con el nombre correcto
        //construye el conjunto de nodos con se respectiva coordenada (x,y) en el "mapa"
        //Ciclo para construir el conjunto de nodos
        for (int j=1; j<=n;j++)
        {
            /*con cada iteración del ciclo se crea un nuevo nodo con coordenadas
            nuevas en el espacio unitario*/
            double X=Math.random();
            double Y=Math.random();
            Nodo nodo=new Nodo("N",j,X,Y); //Construye un nuevo nodo con un ID de nodo
            conjNodos.put(j,nodo);     //Añade el objeto al conjunto.
        }
        
        //CONSTRUIR EL CONJUNTO DE ARISTAS
        int c=1; //contador para numero de aristas
        for (int i=1;i<=n;i++)
        {
            for(int j=1;j<=n;j++)
            {
                double dx=Math.abs(conjNodos.get(i).x - conjNodos.get(j).x);
                double dy=Math.abs(conjNodos.get(i).y - conjNodos.get(j).y);
                double d=Math.sqrt(Math.pow(dx,2)+Math.pow(dy, 2));
                
                par1=i+"--"+j;
                par2=j+"--"+i;/*crea un string con la pareja de nodos seleccionada
                para la arista, se usa para identificarla más tarde y buscar que 
                no se repita la misma arista en el conjunto*/
                if(d<=r)
                {
                    /*Si el la distancia entre la pareja de nodos es menor o 
                    igual a r, se crea la arista*/
                    Arista objArista=new Arista("A",c,conjNodos.get(i),conjNodos.get(j));
                    
                    if(auto)//SI Autociclos
                    {
                        if (diri)//Grafo DIRIGIDO
                        {
                            conjAristas.put(c, objArista);
                            conj.put(c, par1);
                            c++;
                        }
                        else if(conj.containsValue(par2)) //Grafo NO DIRIGIDO
                        {
                            /*Si una combinación de la pareja 
                            de nodos creada ya existe en el conjunto
                            esta no se asigna al conjunto de aristas*/
                        }
                        else //continuación de la condición de grafo NO DIRIGIDO
                        {
                            //coloca el objeto arista en el conjunto de aristas.
                            conjAristas.put(c, objArista); 
                            conj.put(c, par1);
                            c++;
                        }
                    }
                    else //NO Autociclos
                    {
                        if(i==j) //condición que descarta directamente todos los autociclos
                        {
                            /*Si la pareja de nodos elegida para la arista es el mismo
                            nodo, no se guarda la arista en el conjunto. No importa
                            si el grafo es dirigido o no, esta primera condición 
                            descarta la posibilidad de crear la arista, independientemente
                            de si la pareja ya se encuentra en el conjunto o no*/
                        }
                        else //caso para evaluar si es dirigido o no dirigido
                        {
                            /*En caso de que la pareja elegida esté formada por
                            nodos distintos, evaluar si el grafo es dirigido o no*/
                            
                            if(diri) //Grafo DIRIGIDO
                            {
                                /*En caso de que el grafo sea dirigido admite cualquier
                                combinación de parejas de nodos, por lo tanto, coloca 
                                el objeto arista en el conjunto de aristas.*/
                                conjAristas.put(c, objArista);
                                conj.put(c, par1);
                                c++; //incrementa el contador para las claves de aristas
                            }
                            else if(conj.containsValue(par2)) //Grafo NO DIRIGIDO
                            {
                                /*Si una combinación de la pareja 
                                de nodos creada ya existe en el conjunto
                                esta no se asigna al conjunto de aristas*/
                            }
                            else //condición restante del grafo NO DIRIGIDO
                            {
                                //coloca el objeto arista en el conjunto de aristas.
                                conjAristas.put(c, objArista); 
                                conj.put(c, par1);
                                c++; //incrementa el contador para las claves de aristas
                            }
                        }
                    }
                }
                
            }
        }
        int m=conjAristas.size(); //calcula el número de aristas en el grafo
        escribirTxt(m,diri,nameGrafo);  //crea el archivo grafoGilbert.gv
        return objGrafo;
    }
    
    public static Grafo genBarabasiAlbert(int n, double d, boolean diri, boolean auto) throws IOException
    {
        Grafo objGrafo=new Grafo(); //crea el objeto grafo
        /*Método que construye un Grafo con el método de Barabasi-Albert, donde
        n es el número de nodos, d es el número de aristas de cada nodo a nodos distintos, "auto" indica 
        si el grafo permite autociclos y "diri" indica si el grafo es dirigido*/
        String nameGrafo="grafoBarabasiAlbert"; //para crear el archivo con el nombre correcto
        int c=1; //iniciar el contador de aristas.
        for(int i=1;i<=n;i++)
        {
            Nodo nodo=new Nodo("N",i); //Construye un nuevo nodo con un ID de nodo
            conjNodos.put(i,nodo);     //Añade el objeto al conjunto
            /*Ciclo para formar las posibles aristas, se revisa si cada nodo i
            se puede unir a cada nodo j*/
            for(int j=1;j<=conjNodos.size();j++)
            {
                //se verifica el grado del nodo j, para ver si unirlo al nodo i
                //solo si el grado del nodo j es <d es posible crear una arista mas  
                int g= obtenerGrado(j);
                if(g<d)
                {
                    /*calcula la probabilidad de unir el nodo i al nodo j, 
                    en función del grado del nodo j*/
                    double p=1-(g/d); 
                    double r=Math.random(); //probabilidad aleatoria
                    if(r<=p)
                    {
                        /*Al parecer, la naturaleza de este grafo impide que
                        se tenga el caso en que se vuelva a elegir una pareja
                        de nodos ya formada antes, así como tampoco sucede que 
                        exista una combinación previa de la pareja elegida*/
                        if(auto) //SI Autociclo
                        {
                            //se crea la arista
                            Arista objArista=new Arista("A",c,conjNodos.get(i),conjNodos.get(j));
                            conjAristas.put(c, objArista);
                            c++;
                        }
                        else //No Autociclo
                        {
                            if(i==j) //condición que descarta directamente todos los autociclos
                            {
                                /*Si la pareja de nodos elegida para la arista es el mismo
                                nodo, no se guarda la arista en el conjunto. No importa
                                si el grafo es dirigido o no, esta primera condición 
                                descarta la posibilidad de crear la arista, independientemente
                                de si la pareja ya se encuentra en el conjunto o no*/
                            }
                            else
                            {
                                //se crea la arista
                                Arista objArista=new Arista("A",c,conjNodos.get(i),conjNodos.get(j));
                                conjAristas.put(c, objArista);
                                c++;
                            }
                        }
                    }
                }             
            }
        }
        
        int m=conjAristas.size(); //calcula el número de aristas en el grafo
        Grafo.escribirTxt(m,diri,nameGrafo);  //crea el archivo grafoGilbert.gv 
        return objGrafo;
    }
    
    //Método para construir el conjunto de nodos
    public static void construirE(int n)
    {
        //Ciclo para construir el conjunto de nodos
        //n es el número de nodos deseados.
        for (int j=1; j<=n;j++)
       {
           Nodo nodo=new Nodo("N",j); //Construye un nuevo nodo con un ID de nodo
           conjNodos.put(j,nodo);     //Añade el objeto al conjunto.
       }
    }
    
    
    //Método para crear el archivo .txt 
    public static void escribirTxt(int m, boolean diri, String nombreG)throws IOException
    {
        /*m indica el número de aristas, "diri indica si el grafo es dirigido"*/
        
        //se crea el archivo en la ruta específicada y con el nombre y extensión indicados
        FileWriter fileGrafo = new FileWriter("C:\\Users\\F5-573\\Desktop\\"+nombreG+".gv");
        //se usan estos String para construir el cuerpo del archivo
        String nombreGrafo; 
        String simbArista;
        
        if(diri)//Grafo DIRIGIDO
        {
            //si el grafo es dirigido lo escribirá con esta sintaxis
            nombreGrafo="digraph";
            simbArista="->";
        }
        else //Grafo NO dirigido
        {
            //si el grafo no es dirigido, lo escribirá con esta otra
            nombreGrafo="graph";
            simbArista="--";
        }      
        /*Escribe el cuerpo del archivo con la sitanxis adecuada para 
        el conjunto de aristas que forman el grafo*/
        fileGrafo.write(nombreGrafo+" "+nombreG+" \n{");        
        for(int cont=1;cont<=m;cont++)
        {  
       escritura=conjAristas.get(cont).arista.get(1).IDnodo+
               simbArista+conjAristas.get(cont).arista.get(2).IDnodo+";";

            fileGrafo.write(escritura+"\n");
            
        System.out.println("Arista "+conjAristas.get(cont).IDarista+" "+escritura);

        }
        fileGrafo.write("}");
        fileGrafo.close(); //concluye la escritura del archivo   
        
        
    }
    
    public static int obtenerGrado(int numNodo)
    {
        /*Método estático para calcular el grado de un nodo, es decir,
        el número de aristas con el que cuenta*/
        int grado=0; //se inicializa el grado en cero
        String nodo="N"+numNodo; /*se crea el String del nodo j para poder
                                compararlo con el IDnodo, el cual es String "N"+num*/
        for(HashMap.Entry <Integer, Arista> i: conjAristas.entrySet())
        {
            /*Este ciclo for recorre todo el conjunto de aristas formadas
            hasta la fecha y en cada arista se mete al objeto y revisa su
            mini mapa interno, el cual guarda la pareja de objetos de Nodo*/
            Arista value= i.getValue();
            /*De cada objeto arista dentro del conjunto, extrae los IDnodo
            que conforman a dicha arista*/
            String nod1=value.arista.get(1).IDnodo;
            String nod2=value.arista.get(2).IDnodo;
            if(nod1.equals(nodo) || nod2.equals(nodo))
            {
                /*Si el nodo j que se está buscando aparece en alguna de 
                las parejas ya creadas, incrementa su grado en uno por cada
                vez que aparezca*/
                grado++;
            }
        }
        return grado; //devuelve el grado del nodo j en cuestión
    }
    
    /*---------MÉTODOS PARA GENERAR ÁRBOLES A PARTIR DE UN GRAFO DADO---------*/
    
    public Grafo BFS(String S) throws IOException
    {   /*Existe un defecto en el método, solo maneja dos capas, la L[i] y la L[i+1].
        Al final se construye el árbol, pero no se genera el conjunto de capas, solo
        se guardan las últimas dos capas.*/
        
        //S es el nodo raíz (ID del nodo S de la forma "N# <- número de nodo")
        /*Todo el método trabaja con el grafo "G" que llame al método, "G" fue
        generado previamente por alguno de los métodos anteriores*/
        Nodo nodoS = new Nodo(); //Nodo que se usa como raíz.      
        Grafo arbolBFS = new Grafo(); //generar el objeto grafo de salida. 
        R.clear(); //borrar el árbol formado por el método anterior.
        LinkedList <Nodo> capaL= new LinkedList<>(); //Capa L[i]
        LinkedList <Nodo> capaL1= new LinkedList<>(); //Capa L[i+1]                
        String nameGrafo="arbolBFS"; //Nombre usado para escribir el archivo.                                              
                
        for(HashMap.Entry <Integer, Arista> i: conjAristas.entrySet())
        {
            /*Marcar todos los nodos del grafo como No explorados, 
            a excepción del nodo "S"*/ 
            Arista value= i.getValue();
            String nod1=value.arista.get(1).IDnodo;
            String nod2=value.arista.get(2).IDnodo;
            //Este ciclo es necesario si se usan los tres métodos de árbol juntos.            
            if(nod1.equals(S))
            {
                value.arista.get(2).explorado=false; //este orden es importante
                value.arista.get(1).explorado=true;  //si se llegan a tener autociclos.             
                nodoS=value.arista.get(1);
            }
            else if (nod2.equals(S))
            {                
                value.arista.get(1).explorado=false;
                value.arista.get(2).explorado=true;
                nodoS=value.arista.get(2);
            }
            else
            {   //Again, esto es necesario porque se usó DFS_R o similar, previamente.
                value.arista.get(1).explorado=false;
                value.arista.get(2).explorado=false;
            }   
        }
        listaAdy(); //Calcular la lista de nodos adyacentes de cada nodo en el grafo.                  
        capaL.add(nodoS); //A la capa L[0] se le asigna el nodo S.                       
        Nodo V = new Nodo(); //objeto para identificar al nodo "v"
        int a=1; //contador de aristas para el árbol.
        while(!capaL.isEmpty())
        {                                      
            capaL1.clear(); //Asignar la capa L[i+1] como vacía.                        
            for (int j=0;j<capaL.size();j++)
            {   //ciclo para cada nodo "u" perteneciente a la capa L[i]                                                
                for (int i=0;i<capaL.get(j).adyList.size();i++)
                {
                    //Considerar cada arista (u,v) o (v,u)
                    V=capaL.get(j).adyList.get(i);
                    if (!V.explorado)
                    {
                        /*Si el nodo "v" no ha sido explorado, se marca como 
                        explorado y  se agrega a la capa L[i+1]*/
                        Arista aristaT = new Arista("A",a,capaL.get(j),V);
                        R.add(aristaT); //crear y añadir la arista (u,v) al árbol.                   
                        V.explorado=true; //marcar v como explorado 
                        capaL1.add(V);
                        a++;
                    }
                }                                
            }
            capaL.clear(); //tal vez esta acción hacía falta en el otro enfoque.
            capaL.addAll(capaL1); //Copiar los elementos de la capa L[i+1] a la L[i]                    
        }                
        int m=R.size(); //calcula el número de aristas en el grafo        
        escribirArbol(nameGrafo); //se escribe el .txt del árbol con un método especial.
        System.out.println("Se construyo el Arbol BFS con "+m+" aristas");//el "false" es por NO Dirigido
        return arbolBFS;                
        
    }
    
    public Grafo DFS_R(String u) throws IOException
    {
        //S es el nodo raíz (ID del nodo S de la forma "N# <- número de nodo")
        /*Todo el método trabaja con el grafo "G" que llame al método, "G" fue
        generado previamente por alguno de los métodos anteriores*/
        Nodo nodoU = new Nodo();
        nodoU.IDnodo=u;
        Grafo arbolDFS = new Grafo(); //No estoy seguro de si se usa este obj. Grafo. 
        String arbolName = "arbolDFS_R";
        for(HashMap.Entry <Integer, Arista> i: conjAristas.entrySet())
        {
            /*Marca el nodo "u" como "Explorado", los demás se dejan intactos.
            Es necesario hacerlo así por la llamada recursiva.*/ 
            Arista value= i.getValue();
            String nod1=value.arista.get(1).IDnodo;
            String nod2=value.arista.get(2).IDnodo;
            if (nod1.equals(u) && !value.arista.get(1).explorado)
            {
                value.arista.get(1).explorado=true;                
            }
            else if (nod2.equals(u) && !value.arista.get(2).explorado)
            {
                value.arista.get(2).explorado=true;                
            }                 
        } 
        for(HashMap.Entry <Integer, Arista> i: conjAristas.entrySet())
        {
            /*Para cada arista (u,v)*/
            Arista value= i.getValue();
            String nod1=value.arista.get(1).IDnodo;
            String nod2=value.arista.get(2).IDnodo;
            if (nod1.equals(u))
            {                
                if (!value.arista.get(2).explorado)
                {                    
                    Arista aristaT= new Arista("A",aDFS,nodoU,value.arista.get(2));
                    R.add(aristaT); //se añade la arista (u,v) al conjunto al conjunto del Árbol  
                    aDFS++;         // "R" es un atributo de la clase Grafo.
                    DFS_R(nod2); //llamada recursiva sobre nod2
                }
            }
            else if (nod2.equals(u))
            {                
                if (!value.arista.get(1).explorado)
                {                    
                    Arista aristaT= new Arista("A",aDFS,nodoU,value.arista.get(1));
                    R.add(aristaT); //se añade la arista (u,v) al conjunto del Árbol
                    aDFS++;         // "R" es un atributo de la clase Grafo.   
                    DFS_R(nod1); //llamada recursiva sobre nod1
                }
            }
        }
        /*Dado que se usa un atributo distinto (LinkedList R), no se puede usar el método
        "escribirTxt" porque no será compatible, por eso se hace de forma "manual" aquí.*/
        escribirArbol(arbolName);
        int num=R.size();
        System.out.println("Se construyo el Arbol DFS_R con  "+num+" aristas");
        return arbolDFS;
    }
    
    public Grafo DFS_I(String u) throws IOException
    {
        R.clear(); //Vaciar todo el conjunto R porque en el método Iterativo se usó.
        Grafo arbolDFS = new Grafo();
        String arbolName = "arbolDFS_I";
        Nodo nodoU = new Nodo();
        nodoU.IDnodo=u;        
        LinkedList <Nodo> stackList = new LinkedList <> (); //Stack de nodos
        for(HashMap.Entry <Integer, Arista> i: conjAristas.entrySet())
        {
            /*Marcar todos los nodos del grafo como No explorados, 
            a excepción del nodo "S".*/
            Arista value= i.getValue();
            String nod1=value.arista.get(1).IDnodo;
            String nod2=value.arista.get(2).IDnodo;
            //ESTE CICLO ES NECESARIO SI SE USAN LOS 3 MÉTODOS DE ÁRBOL A LA VEZ.                
            if(nod1.equals(u))
            {
                value.arista.get(2).explorado=false; //este orden es importante
                value.arista.get(1).explorado=true;  //si se llegan a tener autociclos.             
                nodoU=value.arista.get(1);
            }
            else if (nod2.equals(u))
            {                
                value.arista.get(1).explorado=false;
                value.arista.get(2).explorado=true;
                nodoU=value.arista.get(2);
            }
            else
            {   //Again, esto es necesario porque se usó DFS_R previamente.
                value.arista.get(1).explorado=false;
                value.arista.get(2).explorado=false;
            }                            
        }                     
        //Crear lista de adyacencia para cada nodo        
        listaAdy();
        stackList.add(nodoU); //añadir el nodo raiz al Stack
        Nodo lastNodo = new Nodo(); //objeto para identificar al nodo al Final del Stack.
        Nodo V = new Nodo();        //objeto para identificar al primero nodo en la lista de adyacencia.
        int a=1; //contador aristas del árbol.
        while (!stackList.isEmpty())
        {     
            lastNodo=stackList.getLast();            
            if (!lastNodo.adyList.isEmpty()) //si el nodo tiene nodos adyacentes
            {
                V= lastNodo.adyList.getFirst();
                if (!V.explorado) //si no ha sido explorado
                {                    
                   Arista aristaT = new Arista("A",a,lastNodo,V);
                   R.add(aristaT); //crear y añadir la arista (u,v) al árbol.
                   a++;
                   V.explorado=true; //marcar v como explorado
                   stackList.add(V); //añadir v al final del stack
                }
                lastNodo.adyList.removeFirst();
            }
            else
            {
                //si el nodo no tiene nodos adyacentes, remover del stack
                stackList.removeLast();
            }            
            if (!stackList.isEmpty())
            {
                /*Si el stack sigue sin estar vacío, tomar el último nodo
                para explorarlo a lo profundo. Esto "evita" la llamada recursiva
                y hace la búsqueda a lo profundo.*/
                lastNodo=stackList.getLast();
            }
        }
        /*Dado que se usa un atributo distinto (LinkedList R), no se puede usar el método
        "escribirTxt" porque no será compatible, por eso se hace de forma "manual" aquí.*/
        escribirArbol(arbolName);
        int num=R.size();
        System.out.println("Se construyo el Arbol DFS_I con  "+num+" aristas");
        return arbolDFS;
    }
    
    public void listaAdy()
    {
        for (HashMap.Entry <Integer, Arista> i: conjAristas.entrySet()) 
        {//asignar a cada nodo la lista de todos los nodos a los que está conectado
            Arista value= i.getValue();
            value.arista.get(1).adyList.add(value.arista.get(2));
            value.arista.get(2).adyList.add(value.arista.get(1)); //Algunos valores se repiten            
        }        
    }
    
    public void escribirArbol (String nombre) throws IOException
    {
        FileWriter fileGrafo = new FileWriter("C:\\Users\\F5-573\\Desktop\\"+nombre+".gv");
        fileGrafo.write("graph "+nombre+"\n{");        
        int num=R.size();                       
        for (int j=0;j<num;j++)
        {
            escritura=R.get(j).arista.get(1).IDnodo+
            "--"+R.get(j).arista.get(2).IDnodo+";";

            fileGrafo.write(escritura+"\n");
            //System.out.println("Arista "+R.get(j).IDarista+" "+escritura);
        }
        fileGrafo.write("}");
        fileGrafo.close(); //concluye la escritura del archivo
    }
    
    public static void main(String[] args) throws IOException 
    { /*excepción
        añadida por sugerencia de Java*/
        Grafo grafoG = new Grafo();        
        //valores booleanos: dirigido, autociclos.
        //genErdosRenyi(100,120,false,false);
        //genGilbert(500,0.15,false,false);
        //genGeografico(500,0.3,false,false);
        grafoG=genBarabasiAlbert(500,10,false,false);
        
        //Método para calcular los árboles.   (EL ORDEN EN QUE SE INVOCAN LOS MÉTODOS SÍ IMPORTA)    
        grafoG.DFS_R("N1"); //Parece que ya trabajan bien en conjunto. Al menos obtienen el mismo número de aristas.
        grafoG.DFS_I("N1");     
        grafoG.BFS("N1"); //obtuve un caso en que BFS obtiene menos aristas que los dos anteriroes.                         
    }
}

/*Las asignaciones de un objeto a otro provocan que ambos objetos apunten a los mismos registros,
por lo que lo que le pase a uno, le pasará al otro. Por eso, en algunos casos, es necesario
resetear los parámetros de los nodos o las aristas en algunos métodos que se usan de forma
simultánea.*/
