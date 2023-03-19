
package malha_taous;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;
import java.io.IOException;
import java.io.Serializable;
import java.text.DecimalFormat;
import static malha_taous.SA_ExitBenchController.count;

public class SolutionRecuit<E> implements Serializable{
    
    public int[] solution;
    public double fitness;
    public double cap;
    ArrayList<E> itemList;
    public double totalWeight;
    int Id;
  
    public int numberOfItems;
    String sol=" ";
    public int getNumberOfItems() {
        return numberOfItems;
    }

    public int[] getSolution() {
        return solution;
    }

    public double getFitness() {
        return fitness;
    }

    public String getSol() {
        return sol;
    }
    public double getCap() {
        return cap;
    }

    public ArrayList<E> getItemList() {
        return itemList;
    }

    public double getTotalWeight() {
        return totalWeight;
    }

    public int getCounter() {
        return Id;
    }

    
    public SolutionRecuit()	{}

    public SolutionRecuit(int numberOfItems, double capacity, ArrayList<E> items, int[] b){
       Id=count;

       this.numberOfItems = numberOfItems;
        solution = new int[numberOfItems];
        int i;
       /* if (b == null)
            for (i = 0; i< numberOfItems; i++)
	      
	         solution[i]=0;
         
        else  solution = b;*/
       if(b!=null) solution=b;
        cap = capacity;
        itemList = items;
        
        fitness = (double)((int)( calculatetotalProfit()*100))/100;
        totalWeight = (double)((int)(calculateWeight()*100))/100;
        for(i=0;i<solution.length;i++)
        {
            sol=sol+solution[i];
        }
       
    }
    

void affich(ArrayList<saisiInf>listeObjets)
{ 
	 System.out.print("la solution est: ");
	 for(int i=0;i<listeObjets.size();i++)
	  { 
		 System.out.print(this.solution[i]+" ");  
	  }
	 System.out.println(" Id ="+Id+"  fitness= "+this.fitness+" poids_total= "+this.totalWeight);  
         
	 
         
}
    public SolutionRecuit(SolutionRecuit s){
        this(s.numberOfItems, s.cap, s.itemList, s.solution);
    }

public SolutionRecuit<E> findNeighbour(){
        System.out.print("result findNeighbour(): ");
        Random r = new Random();
        int howManyToChange = r.nextInt(2)+1;
        int randItem = r.nextInt(numberOfItems);
        ArrayList<Integer> nonTakenItemsID = getNontakenItems();
        int[] solutionCopy = new int[solution.length];
        for(int i = 0 ; i<this.solution.length; i++)
		{
            solutionCopy[i] = this.solution[i]; // not to do a shallow copy
        }
        SolutionRecuit<E> neighbor =  new SolutionRecuit<E>(numberOfItems, cap, itemList, solutionCopy);
    	
		while(howManyToChange-- !=0)
		{
            randItem = r.nextInt(nonTakenItemsID.size());
            neighbor.solution[nonTakenItemsID.get(randItem)] = 1;
            neighbor.calculateWeight();
            if (neighbor.isOverWeight())
			{    
		        // if you are over-weight drop item
                ArrayList<Integer> takenItemsID = neighbor.getTakenItems();
                do {
                    randItem = r.nextInt(takenItemsID.size());
                    // !!! BUG: to improve make sure it is not the item you selected !!!
                    neighbor.solution[takenItemsID.get(randItem)] = 0;
                    neighbor.calculateWeight();
                }while (neighbor.isOverWeight()); // until you are not over-weight
            }
        }
  	  return neighbor;
    }
    public boolean isOverWeight(){
        return (totalWeight > cap);
    }

    public double calculatetotalProfit(){
        double totalProfit = 0;
        for(int i = 0; i<numberOfItems;i++){
            if (solution[i]==1) { // if item i is taken
                totalProfit += ((saisiInf)itemList.get(i)).getValeur();
            }
        }
        return totalProfit;
    }

    

    public double calculateWeight(){
        double sum=0;
        for(int i = 0; i<numberOfItems;i++){
            if (solution[i]==1) { // if item i is taken
                sum += ((saisiInf)itemList.get(i)).getPoids();
            }
        }
        totalWeight = sum;
        return sum;
    }


   
    public ArrayList<Integer> getTakenItems(){
        ArrayList<Integer> a = new ArrayList<>();
        for(int i = 0; i < solution.length; i++)
            if (solution[i] == 1)
                a.add(i);

        return a;
    }

    public ArrayList<Integer> getNontakenItems(){
        ArrayList<Integer> a = new ArrayList<>();
        for(int i = 0; i < solution.length; i++)
            if (solution[i] == 0)
                a.add(i);

        return a;
    }
     static double Proba_Acceptation() //entre 0 et 1 
 {
          DecimalFormat df=new DecimalFormat("0.00");
          Random p=new Random();   
	  double prob=p.nextDouble();
          prob=(double)((int)( prob*100))/100;
          System.out.println("p= "+prob);
          return prob; 
 }   
}

