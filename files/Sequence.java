
package malha_taous;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import javafx.scene.control.Alert;
import static malha_taous.ExistBenchController.pop;


public class Sequence {
                static int nbrObj;//taille d'une séquence c le nbrObj
                ArrayList<Integer> sequence;//séquence de 0 et 1
	            double prob_crois, prob_mut;
                double fitness, poids_seq;
                int nb_pop=pop;

//Getters
    public int getNbrObj() {
        return nbrObj;
    }
     public int getNb_pop() {
        return nb_pop ;
    }
    public  ArrayList<Integer> getSequence() {
        return sequence;
    }

    public double  getProb_crois() {
        return prob_crois ;
    }
  public double  getProb_mut() {
        return prob_mut ;
    }
 public double  getFitness() {
        return fitness ;
    }
 public double  getPoids_seq() {
        return poids_seq ;
    }
 
 public Sequence()
 {    
      this.sequence= new ArrayList <Integer>();
      this.associer_prob();
      //nb_pop=pop;
 }
  
 public static Sequence generer_sequence(ArrayList<saisiInf>listeObjets,double capacity)
 {
      Sequence seq=new Sequence(); 
    
  do{ 
      int p=0;
      for(int i=0;i<listeObjets.size();i++) 
	  { 
	    int nb= (int)(Math.random()*2);
            //if (nb==1){
            p+=listeObjets.get(i).getPoids();
            if (p<=capacity)  
	    seq.sequence.add(nb);
            else 
                seq.sequence.add(0); 
          
           }

    }while(seq.Inf_à_Capacity(seq,capacity)==false);
  return seq;

 }
public ArrayList<Integer> getNontakenItems(){
        ArrayList<Integer> a = new ArrayList<>();
        for(int i = 0; i <sequence.size(); i++)
            if (sequence.get(i) == 0)
                a.add(i);

        return a;
    } 

    public ArrayList<Integer> getTakenItems(){
         ArrayList<Integer> a = new ArrayList<>();
        for(int i = 0; i <sequence.size(); i++)
            if (sequence.get(i) == 1)
                a.add(i);
        return a;
    }
    
public boolean Inf_à_Capacity(Sequence seq,double capacity)
{       
                    return seq.poids_seq<=capacity;
  }
   
public void associer_prob()
{  
    this.prob_mut=aleatoire(2);
    this.prob_crois=aleatoire(2);
}

public double aleatoire(int precision)
{
	Random randGen=new Random();
	double randNum=randGen.nextDouble();
	double p=(double)Math.pow(10,precision);
	double v=randNum*p;
        
	double tmp=Math.round(v);
	return(double)tmp/p;
}



static ArrayList<Sequence> selection(int select,ArrayList<Sequence> liste)//par rang
{ 
    
    ArrayList<Sequence>liste2=new ArrayList<Sequence>();  
     for(int i=0;i<select;i++)
      { 
        liste2.add(liste.get(i));//.affich();
       }
     return liste2;
}

void affich(ArrayList<saisiInf>listeObjets)
{ 
	 System.out.print("la sequence est: ");
	 for(int i=0;i<listeObjets.size();i++)
	  { 
		 System.out.print(sequence.get(i));  
	  }
	 System.out.print(" sa prob_mut = "+this.prob_mut +" prob_crois : "+this.prob_crois+" fitness= "+this.fitness+" poids_total= "+this.poids_seq);  
	 System.out.println();
         
}

static Sequence choisir_meilleur(ArrayList<Sequence> list)
{  
   ArrayList<Sequence>new_list;
   new_list=Sequence.trier_liste_sequences(list);
   return new_list.get(0);
}

static ArrayList<Sequence> trier_liste_sequences(ArrayList<Sequence> list_a_trier)//le tri par rapport au fitness
{
        ArrayList<Sequence>liste_tri=new ArrayList<Sequence>();
        // le tri ascendant de la liste
        Collections.sort(list_a_trier, new FitnessCompr());
        Collections.reverse(list_a_trier);
        for(Sequence c:list_a_trier)
{
            liste_tri.add(c);
        }
    return liste_tri;
}
//afficher les objets d'une sequence
void Afficher_sequence(ArrayList<saisiInf>listeObjets)
{ 
	System.out.print("Les objets de cette sequence sont: ");
	
  	for(int i=0;i<sequence.size();i++)
	 {   	
	   if(sequence.get(i)==1)
	    {
		   System.out.print("l'objet "+i+" ");
		    listeObjets.get(i).Afficher_objet();
		 
		 }   System.out.println(); 
	  }	
}

//calculer la fitness d'une sequence
double compute_fitness(ArrayList<saisiInf>listeObjets)
{ 
    double somme_val=0;
    for(int i=0;i<sequence.size();i++)
    {
          if(sequence.get(i)==1)
	    {
	      somme_val=somme_val+listeObjets.get(i).valeur; 
	    }
        
    } 
  	return somme_val;
}


//calculer la somme des poids des objets  d'une sequence particuliere
double compute_weight(ArrayList<saisiInf>listeObjets)
{
     double somme_poids=0;   	
     for(int i=0;i<sequence.size();i++)
	 { 	
           if(sequence.get(i)==1)
		{
	          somme_poids=somme_poids+listeObjets.get(i).poids; 
                  
		 }
         } somme_poids= (double) Math.round( somme_poids * 100) / 100; 
	return somme_poids;
}



//mutation
@SuppressWarnings("static-access")
Sequence mutate(double prob_mut_compr,ArrayList<saisiInf>listeObjets,double capacity)
{
Sequence s2=new Sequence();
s2.associer_prob();
       // System.out.println("la séquence");
       // this.affich(listeObjets);
       //System.out.println("résultat mut");
       
   if(this.prob_mut<prob_mut_compr)
    {
        int b =listeObjets.size();
        int gene_to_mutate=(int)(Math.random() *b);
//System.out.println("l indice à muté est :"+gene_to_mutate );
       
    for(int i=0;i<this.sequence.size();i++)
        {
            if(i!=gene_to_mutate)
            {
               s2.sequence.add(i,this.sequence.get(i));      
            }
            else // si (i==gene_to_mutate)
            {
               if (this.sequence.get(i)==0) s2.sequence.add(i,1);      
                  else  s2.sequence.add(i,0);            
            }  
        }
        s2.fitness=s2.compute_fitness(listeObjets);
        s2.poids_seq=s2.compute_weight(listeObjets);
        //s2.affich(listeObjets);
        if(s2.poids_seq<=capacity) {return s2;}else return this;  
}
else //dans le cas ou (s2.prob_mut>=prob_mut_compr) on laisse la sequence comme elle est ... on inverse rien

return this;
 } 


Sequence Croiser_1_Seq(Sequence autre,int point_crois,double prob_crois_compr )
 {  
    int i;
    Sequence result =new Sequence(); 
    if((this.prob_crois<=prob_crois_compr)&&(autre.prob_crois<=prob_crois_compr))
  {  
     for(i=0;i<point_crois;i++)
     {  
       result.sequence.add(i,this.sequence.get(i));
     }
     for(i=point_crois;i<this.sequence.size();i++)
     {  
       result.sequence.add(i,autre.sequence.get(i));
     }
  }  
 else { if(this.fitness>autre.fitness)
        { return this;}
           else return autre;
      }
 return result;
 }

Sequence Croiser_2_Seq(Sequence autre,int point_crois,double prob_crois_compr )
 {  
    Sequence result =new Sequence(); 
    int i;
    if((this.prob_crois<=prob_crois_compr)&&(autre.prob_crois<=prob_crois_compr))
  {  for(i=0;i<point_crois;i++)
     {  	 
       result.sequence.add(i,autre.sequence.get(i));
     } 
     for(i=point_crois;i<this.sequence.size();i++)
     {   
        result.sequence.add(i,this.sequence.get(i));
     }                
   }
 else { if(this.fitness>autre.fitness)
        { return this;}
           else return autre;
      }
  return result;
  }

 static Sequence crois_popl(ArrayList<saisiInf>listeObjets, ArrayList<Sequence> liste,int i,int j,double prob_crois_compr,double capacity)
{           
             int point_crois=(int)(Math.random() *listeObjets.size());
             System.out.println("point_crois pris est :"+point_crois);
             
             Sequence new_seq =new Sequence();
             Sequence s= liste.get(i).Croiser_1_Seq(liste.get(j),point_crois, prob_crois_compr);
           
             s.poids_seq=s.compute_weight(listeObjets);
             s.fitness=s.compute_fitness(listeObjets);
             
             Sequence s2= liste.get(i).Croiser_2_Seq(liste.get(j),point_crois,prob_crois_compr);
             s2.poids_seq=s2.compute_weight(listeObjets);
             s2.fitness=s2.compute_fitness(listeObjets);
           
  if((s.poids_seq<=capacity)&&(s2.poids_seq<=capacity))
      {
        if(s.fitness>s2.fitness)
          { new_seq=s;  }
           else  {  new_seq=s2; }
     }
   
    if((s.poids_seq<=capacity)&&(s2.poids_seq>capacity))
              {new_seq=s; }
   
    if((s.poids_seq>capacity)&&(s2.poids_seq<=capacity))
              {new_seq=s2;}
  
    if((s.poids_seq>capacity)&&(s2.poids_seq>capacity))
      {
           System.out.println("les deux sequences enfants sont superieures à la capacity donc : ");
              Sequence copie=new Sequence();
              copie=s;
             
        i=0;
        System.out.println();   
        double p=copie.poids_seq;System.out.println("p: "+p); 
        double f=copie.fitness;
    while(i<copie.sequence.size())
	{
           if (p>capacity)
           {
              System.out.println("i "+i);
              if(copie.sequence.get(i)==1)
              { 
                copie.sequence.set(i,0);
                f=copie.compute_fitness(listeObjets);
                p= copie.compute_weight(listeObjets);
                System.out.println("poids copie : "+p);
                System.out.println("afficher copie pour le moment :");
                new_seq.sequence=copie.sequence;
                new_seq.fitness=f;
                new_seq.poids_seq=p;
                new_seq.affich(listeObjets);  
              }
           }
               if (p<=capacity)
	   {   
                System.out.println("c'est fait");
                new_seq.sequence=copie.sequence;
                new_seq.fitness=f;
                new_seq.poids_seq=p;
                break;
           }
            
            i++;
        } 
     
      }
 return new_seq;  
} 
 
//verifffffffffffffffffff if kolchhh 

 boolean equal_Kolch(Sequence s)
{ 
   boolean b=true;

if((this.prob_crois==s.prob_crois)&& this.prob_mut==s.prob_mut && b)
 {
   for(int i=0;i<this.sequence.size();i++)
    {  
      if((this.sequence.get(i)==s.sequence.get(i))&& b)
         b=true;
          else  b=false;     
    }
 }

else {b=false; }  //if((this.prob_crois!=s.prob_crois)|| this.prob_mut!=s.prob_mut )
return b;
}
  
 static boolean Verif_if_Kolch(Sequence s,ArrayList<Sequence> liste_seq) //pr la vérif dans tte la liste
{  
    boolean b=false;
    for(int i=0;i<liste_seq.size();i++)
     {
        if((liste_seq.get(i).equal_Kolch(s)==true))  
        {  b=true; break;}
            else b=false;       
      }
   return b;
}
       @Override
  public String toString() {
        return "Sequence{" + "nb_pop=" + nb_pop+ " ,sequence=" +sequence + ",fitness=" +fitness +",weight="+ poids_seq+ ",pm="+  prob_mut+",pc=" +prob_crois+ '}';
    }  
  

}