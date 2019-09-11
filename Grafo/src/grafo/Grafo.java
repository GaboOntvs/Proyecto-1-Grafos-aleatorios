package grafo;

import java.util.HashMap;
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
    
    static String par1, par2, escritura; //para escribir el archivo .txt
         
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
        Grafo.escribirTxt(m,diri,nameGrafo);  //crea el archivo grafoErdosRenyi.gv
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
        Grafo.escribirTxt(m,diri,nameGrafo);  //crea el archivo grafoGilbert.gv
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
        Grafo.escribirTxt(m,diri,nameGrafo);  //crea el archivo grafoGilbert.gv
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
    
    public static void main(String[] args) throws IOException 
    { /*excepción
        añadida por sugerencia de Java*/
                    
        //valores booleanos: dirigido, autociclos.
        //genErdosRenyi(500,400,true,true);
        //genGilbert(500,0.15,true,true);
        //genGeografico(500,0.15,false,false);
        genBarabasiAlbert(500,15,true,false);
    }
}
