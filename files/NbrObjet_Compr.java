
package malha_taous;
import java.util.Comparator;

public class NbrObjet_Compr implements Comparator<Graphe>  {
    
      public int compare(Graphe a, Graphe b)
        {
          if(a.name_file==b.name_file)
          {
              return 0;
          }
          else if (a.name_file>b.name_file)
          {
              return 1;
          }
          else return -1;
                  
}

}