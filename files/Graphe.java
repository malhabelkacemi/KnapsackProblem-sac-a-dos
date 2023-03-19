
package malha_taous;

import java.util.ArrayList;
import java.util.Collections;


public class Graphe {
    int name_file;
    double result_fitness;
    double result_time;
    int iterration;

    public int getIterration() {
        return iterration;
    }

    public int getName_file() {
        return name_file;
    }

    public double getResult_fitness() {
        return result_fitness;
    }

    public double getResult_time() {
        return result_time;
    }
    
public Graphe (int name_file,double result_fitness,double result_time,int iterration)
{    
this.name_file=name_file;
this.result_fitness=result_fitness;
this.result_time=result_time;
this.iterration=iterration;
 }
    
void Afficher_Result()
{
  System.out.println("Le nom du fichier :"+name_file+", sa fitness " +result_fitness+",son temps :"+result_time +" ,iterration :"+iterration);
}

static ArrayList<Graphe> trier_liste(ArrayList<Graphe> list_a_trier)//le tri par rapport au nbr d objet (qui est name_file)
{  
        ArrayList<Graphe>liste_tri=new ArrayList<Graphe>();
        // le tri ascendant de la liste
        Collections.sort(list_a_trier, new NbrObjet_Compr());
        for(Graphe c:list_a_trier)
{
            liste_tri.add(c);
        }
    return liste_tri;
}
   
}
