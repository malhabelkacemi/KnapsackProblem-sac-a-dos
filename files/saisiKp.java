
package malha_taous;

public class saisiKp {
    double capacity;
    int nbrObj;
	
     public saisiKp(double capacity,int nbr_obj)
	{     
	this.capacity=capacity;
        this.nbrObj=nbr_obj;
	
 }
 //Getters
    public double getCapacity() {
        return capacity;
    }


    public int getNbrObjs() {
        return nbrObj;
    }
    
 /*saisiKp saisir_Kp(SaisiKpController i) {  
     double capacity= Double.parseDouble(this.capacity.getText());
     int nbrObj= Integer.parseInt(nbrObj.getText());
     saisiKp k =new saisiKp(capacity,nbr_obj);
     return k ;
    }*/


}