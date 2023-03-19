
package malha_taous;


import java.util.ArrayList;


public class Population {
    	static ArrayList<Sequence> liste_seq=new ArrayList<Sequence>();
        static ArrayList<saisiInf> listeObjets;   //=new ArrayList<saisiInf>();
	public  static double  prob_mut_compr;
	public static double  prob_crois_compr;
	public static int taille_population;
        public static int nbr_generation;
        public static double capacity;

              
public ArrayList<Sequence> getListe_seq()
{ return liste_seq;}

public ArrayList<saisiInf> getObjets()
{ return listeObjets;}


 /*public int  getNum_seq()
 { return num_seq;}*/
 
 public int  getTaille_population()
 { return taille_population;}
 
 public int  getNbr_generation()
 { return nbr_generation; }      

public double getProb_mut_compr() 
{return prob_mut_compr;}
         
 public double getProb_crois_compr() 
 {return prob_crois_compr;}

 public double getCapacity() 
 {return capacity;}
 
  
public Population (ArrayList<Sequence>liste_seq,ArrayList <saisiInf> listeObjets,double prob_mut_compr,double prob_crois_compr,int taille_population,int nbr_generation,double capacity )
	{
      
      /* this.listeObjets=listeObjets;
       this.liste_seq=liste_seq;*/

	this.prob_mut_compr=prob_mut_compr;
        this.prob_crois_compr=prob_crois_compr;
        this.taille_population=taille_population;
        this.nbr_generation=nbr_generation;
        this.capacity=capacity;
       
        }


}
