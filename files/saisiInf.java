package malha_taous;

public class saisiInf {
    String name;
    double poids;
    int valeur;
    
public saisiInf(String name,double poids,int valeur)
{    
this.name=name;
this.poids=poids;
this.valeur=valeur;
 }
//Getters
    public String getName() {
        return name;
    }


    public double getPoids() {
        return poids;
    }

    public int  getValeur() {
        return valeur;
    }

 static saisiInf saisir_objet(SaisiInfController s) {
     String name = s.getName().getText();
     int value= Integer.parseInt(s.getValeur().getText());
     double weight= Double.parseDouble(s.getWeight().getText());
     saisiInf o =new saisiInf(name,weight,value);
     return o ;
    }
 void Afficher_objet()
{
System.out.println("son nom :"+name+", son poids:" +poids+",sa valeur:"+valeur);
}
           @Override
  public String toString() {
        return "saisiInf{" + "name=" + name + " valeur=" + valeur + ",poids=" + poids + '}';
    }  
}