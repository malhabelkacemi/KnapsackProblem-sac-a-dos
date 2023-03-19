
package malha_taous;

import java.util.Comparator;

public class FitnessCompr implements Comparator<Sequence> 
{
     public int compare(Sequence a, Sequence b)
        {
          if(a.fitness==b.fitness)
          {
              return 0;
          }
          else if (a.fitness>b.fitness)
          {
              return 1;
          }
          else return -1;
                  
    
}
}