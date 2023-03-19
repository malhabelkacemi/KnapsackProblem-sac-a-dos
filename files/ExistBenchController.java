
package malha_taous;

//import static malha_taous.Sequence.nb_pop;


import static malha_taous.Population.liste_seq;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.Set;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.swing.JFileChooser;


public class ExistBenchController implements Initializable 
{  
    @FXML
    private Button run;
    
    @FXML
    private AnchorPane existBenPane;
    @FXML
    private TableView<Sequence> Pop_Table;
    @FXML
    private TableColumn<Sequence,Integer> nb_pop;
    @FXML
    private TableColumn<Sequence, ArrayList <Integer>> sequence;
    @FXML
    private TableColumn<Sequence,Double> fitness;
    @FXML
    private TableColumn<Sequence, Double> poids_seq;
    @FXML
    private TableColumn<Sequence,Double> prob_mut;
    @FXML
    private TableColumn<Sequence,Double> prob_crois; 
   
    ArrayList<saisiInf> listeObjets = new ArrayList<> ();
    @FXML
    private TextField taille_population;
    @FXML
    private TextField nbGener;
    @FXML
    private TextField pm;
    @FXML
    private TextField pc;
    @FXML
    private Label pop_size;
    @FXML
    private Label genr;
    @FXML
    private Label mut;
    @FXML
    private Label cros;
    @FXML
    private Button initialise;
    @FXML
    private Button retour;
 
public TableView<Sequence> getPop_Table() {
return Pop_Table;
}    

public TableColumn<Sequence,Integer> getNb_pop() {
        return nb_pop;
    }

    public  TableColumn<Sequence,ArrayList <Integer>> getSequence() {
        return sequence;
    }

    public  TableColumn<Sequence,Double> getFitness() {
        return fitness;
    }
    public  TableColumn<Sequence,Double> getWeight() {
        return poids_seq;
    }
     public  TableColumn<Sequence,Double> getProb_mut() {
        return prob_mut;
    }
     public  TableColumn<Sequence,Double> getProb_crois() {
        return prob_crois;
    }  
     
static int generation;   
String ligne;
 static Long timeExecution;
 static int ite;
 static  int pop=0;
    public int getPop() {
        return pop;
    }
  ArrayList<Sequence> liste_solution=new ArrayList<>();
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       this.existBenPane.setVisible(false);    
       
        this.nb_pop.setCellValueFactory(new PropertyValueFactory<Sequence,Integer>("nb_pop"));
        this.sequence.setCellValueFactory(new PropertyValueFactory<Sequence,ArrayList <Integer>>("sequence"));
        this.fitness.setCellValueFactory(new PropertyValueFactory<Sequence,Double>("fitness"));
        this.poids_seq.setCellValueFactory(new PropertyValueFactory<Sequence,Double>("poids_seq"));
        this.prob_crois.setCellValueFactory(new PropertyValueFactory<Sequence,Double>("prob_crois"));
        this.prob_mut.setCellValueFactory(new PropertyValueFactory<Sequence,Double>("prob_mut")); 
    }    

   @FXML
   private void RunAction(ActionEvent event) throws FileNotFoundException, IOException
 {
       try{
     double pc= Double.parseDouble(this.pc.getText());
    double pm= Double.parseDouble(this.pm.getText());
    int taille_population= Integer.parseInt(this.taille_population.getText());
    int nbr_generation= Integer.parseInt(this.nbGener.getText());
    
    if((this.pc.getText()!=null)&&(this.pm.getText()!=null)&&(this.nbGener.getText()!=null)&&(this.taille_population.getText()!=null)) 
{
      Alert alert = new Alert (Alert.AlertType.WARNING); 
       if(taille_population<18)
    {
            alert.setTitle("Error Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Failed! size population invalid !");
            alert.showAndWait();
            this.taille_population.clear();   }
    if(nbr_generation<5) //à changer
    {
            alert.setTitle("Error Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Failed! nbr genration invalid !");
            alert.showAndWait(); 
              this.nbGener.clear(); 
    }
    
    if((pc<=0)||(pc>1))
     {
            alert.setTitle("Error Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Failed! Use a number between 0 and 1 !");
            alert.showAndWait(); 
              this.pc.clear(); 
               
       }
     if((pm<=0)||(pm>1))
            {   
            alert.setTitle("Error Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Failed! Use a number between 0 and 1 !");
            alert.showAndWait();
              this.pm.clear(); 
          }
        if (taille_population>=18 && nbr_generation>=5 && pc>0 && pc<=1 && pm>0 && pm<=1)
        {
            this.listeObjets.clear();
        JFileChooser dialogue = new JFileChooser(new File("."));
        File fichier;
        dialogue.showOpenDialog(null);
        fichier = dialogue.getSelectedFile();
        String filepath=fichier.getAbsolutePath();
       
      Scanner in =new Scanner(fichier);
      System.out.println("lire le fichier!");  
 
      String param=in.nextLine();
      String[] decoupage = param.split(":");  
      int nbrObj = Integer.parseInt(decoupage[1]);//récuperer le nbr
      System.out.println("nb="+ nbrObj);
   
      String param2=in.nextLine();
      decoupage = param2.split(":");
      double capacity = Double.parseDouble(decoupage[1]);//récuperer la cap
      System.out.println("capacity="+capacity);
      String[] par=in.nextLine().split("\\s{1,}");
   while(in.hasNext())
  {
      String line=in.nextLine();
      System.out.println("la ligne complete est "+line);//afficher la ligne complete
     
      //découpage de chaque ligne
      decoupage = line.split(",");
      String name = decoupage[0];//récuperer le nom
      System.out.println("name ="+ name);
      int value = Integer.parseInt(decoupage[1]);//récuperer la valeur
      System.out.println("value ="+ value);
     
     //récuperer le poids
     int p1 = Integer.parseInt(decoupage[2]);
     int p2 = Integer.parseInt(decoupage[3]);
     String p=p1+"."+p2;
     double weight = Double.parseDouble(p);
     System.out.println("weight ="+weight);
     System.out.println();
     
      saisiInf o =new saisiInf(name,weight,value);  
      this.listeObjets.add(o);
  }

    this.existBenPane.setVisible(true);
/***************programme pricipal GA***************************/         
 
 System.out.println("capacity :"+capacity);
 System.out.println("nbr d objets :"+nbrObj);
     
     Long begin =  System.currentTimeMillis();
     generation=0;
     int Nbr_echec=0;
     int max_echec=1000;
     
     ArrayList<Sequence> liste_new=new ArrayList<>();
     ArrayList<Sequence> list_crois=new ArrayList<>();
     ArrayList<Sequence> liste_solution=new ArrayList<>();
     ArrayList<Sequence> liste_verif=new ArrayList<>();
     ArrayList<Sequence>tournoi=new ArrayList<>();
     ArrayList<Sequence>liste_tmp=new ArrayList<>();
     ArrayList<Sequence> liste=new ArrayList<>();
    
    Sequence best=new Sequence();
    Sequence best_temp=new Sequence();
    Population p =new Population(liste_seq,listeObjets,pm, pc,taille_population,nbr_generation,capacity);
    System.out.println("capacity :"+capacity);
    System.out.println("nbr d objets :"+nbrObj);

     Sequence seq;
     Random r = new Random();
     int randItem = r.nextInt(listeObjets.size());
     
      for(int i=0;i<taille_population;i++)
     {
       do{
          seq=Sequence.generer_sequence(listeObjets,capacity);
       }while(seq.compute_weight(listeObjets)==0);
       if(seq.compute_weight(listeObjets)<=capacity){          
             seq.fitness=seq.compute_fitness(listeObjets); 
              seq.poids_seq=seq.compute_weight(listeObjets);
              System.out.println("generer sequence"+seq.compute_weight(listeObjets));
 }
     else {
        
         ArrayList<Integer> takenItemsID = seq.getTakenItems();
                do {
                    randItem = r.nextInt(takenItemsID.size());
                    seq.sequence.set(randItem,0);
                    seq.compute_weight(listeObjets);
                }while (seq.compute_weight(listeObjets)>capacity);    
      seq.fitness=seq.compute_fitness(listeObjets); 
       seq.poids_seq=seq.compute_weight(listeObjets);          
     System.out.println("generer sequence hhhh"+seq.compute_weight(listeObjets));
      
     }
          liste_seq.add(seq); 
     }
    
 System.out.println("debut while");
  this.Pop_Table.getItems().clear();
    while(generation<nbr_generation )  {

   for( int i=0;i<liste_seq.size();i++) //**************
        {
            liste_seq.get(i).nb_pop=0;
        }
     for( int i=0;i<liste_seq.size();i++) //**************
    {
        pop=generation;
        liste_seq.get(i).nb_pop=pop;
    }

        System.out.println("*******************generation******************* "+generation);
try {
 if(this.listeObjets.size()+1<200){
 System.out.println("tournoi:"+this.listeObjets.size()+1);
//selection par tournois

 best_temp=Sequence.choisir_meilleur(liste_seq);
 liste_new.add(best_temp);
 liste_tmp.add(best_temp);
     
 int a1,i,j=1;
while(j<taille_population/2)
 {
  //faire des petits tournois...chaque tournoi contient 4 sequences puis on  choisit la meilleur et  on la garde
  for(i=0;i<4;i++)
   {
     a1 =(int)(Math.random()*liste_seq.size());
     tournoi.add(i,liste_seq.get(a1));
   }
   
     best_temp=Sequence.choisir_meilleur(tournoi);
     liste_new.add(best_temp);
     liste_tmp.add(best_temp);
     tournoi.clear();
     j++;
   if(j==taille_population/2){break;}
 }
    System.out.println("liste new :"+liste_new.size());
 
    //croisement
    System.out.println("croisement:");

    int l=liste_new.size();
    int a,b; Sequence s2 = null;
    while(liste_new.size()<taille_population){
        do{
    for ( i=taille_population/2;i<taille_population;i++)
    {
       
     a=(int)(Math.random() *liste_tmp.size());
     b=(int)(Math.random() *liste_tmp.size());
     System.out.print("le 1 er nbr aleatoire est :"+a+" ");
     System.out.print("le 2eme nbr aleatoire est :"+b+" ");
     s2=Sequence.crois_popl(listeObjets,liste_tmp, a, b,pc,capacity);   
     s2.affich(listeObjets);
     
    if (Sequence.Verif_if_Kolch(s2, liste_new)==false)
    {
        if (liste_new.size()!=taille_population)
        {
          liste_new.add(l,s2); 
          list_crois.add(s2);
            l++ ;  
        }else break;
       
    }break;
    }
       }while(Sequence.Verif_if_Kolch(s2, liste_new)==false && liste_new.size()>taille_population);          
    }  
}
  else{
    System.out.println("rang:"+this.listeObjets.size()+1);
      //tri
     System.out.println("le triiiiiii");
     p.liste_seq=Sequence.trier_liste_sequences(liste_seq);
     
     for(int i=0;i<p.liste_seq.size();i++) //la taille de pop
      {  p.liste_seq.get(i).affich(listeObjets);  }
     

    //selection par rang
      p.liste_seq=Sequence.selection(taille_population/2,p.liste_seq);//Sequence.selection(taille_population*3/4,p.liste_seq);//Sequence.selection(taille_population/2,p.liste_seq);
      
    for(int i=0;i<p.liste_seq.size();i++)
    {
      liste_new.add(p.liste_seq.get(i));
    }
      //croisement
    System.out.println("croisement:");

  int l=liste_new.size();
  int a,b; Sequence s2 = null;
    while(liste_new.size()<taille_population){
        do{
    for (int i=taille_population/2;i<taille_population;i++)  {
     a=(int)(Math.random() *p.liste_seq.size());
      b=(int)(Math.random() *p.liste_seq.size());
      
      System.out.print("le 1 er nbr aleatoire est :"+a+" ");
      System.out.print("le 2eme nbr aleatoire est :"+b+" ");
     s2=Sequence.crois_popl(listeObjets,p.liste_seq, a, b,pc,capacity);   
      
    if (Sequence.Verif_if_Kolch(s2, liste_new)==false){
        if (liste_new.size()!=taille_population){
          liste_new.add(l,s2); 
            l++ ;  
        }else break;
       
    } break; 
    }
        
        }while(Sequence.Verif_if_Kolch(s2, liste_new)==false && liste_new.size()>taille_population);
            
    }  
}

   int i;
    
    liste_seq.clear();
     System.out.println("mutation:"); 
        for(  i=0;i<liste_new.size();i++)
      {
          liste_seq.add(liste_new.get(i).mutate(pm,listeObjets,capacity));
      }

   System.out.println("donc la  liste seq est la suivante :");
    for( i=0;i<liste_seq.size();i++)
  {
     liste_seq.get(i).affich(listeObjets);

  }
       liste_new.clear();
       System.out.println("la taille de pop est : "+taille_population);
       System.out.println("la taille des liste_seq est:"+ liste_seq.size());

     generation++;
   
     best=Sequence.choisir_meilleur(liste_seq);//il choisit la meilleure sol dans liste_seq  puis il va la mettre dans liste_solution
     liste_solution.add(best);
    liste_verif.add(best);
    
        //pour le critere d arret:nbr d echec 
        if(liste_verif.size()>=2)
             {  
                 //System.out.println("size:"+liste_verif.size());
                 System.out.println("voila");
                 liste_verif.get(liste_verif.size()-1).affich(listeObjets);
                 liste_verif.get(liste_verif.size()-2).affich(listeObjets);
                 if((liste_verif.get(liste_verif.size()-1).fitness)==liste_verif.get(liste_verif.size()-2).fitness)
                 {   
                     Nbr_echec++;
                     System.out.println("****Nbr_echec: **** "+Nbr_echec);
                 } 
                 else {
                         
                         liste_verif.clear();
                         Nbr_echec=0;
                         System.out.println("***remettre nbr echec: *** "+Nbr_echec);
                       }
             }
            if( Nbr_echec==max_echec) 
            { System.out.println("*********************** il a fait le  break *********************** "+Nbr_echec);
                break;
 
            }
      //for(i=0;i<liste_seq.size();i++)
     this.Pop_Table.getItems().addAll(liste_seq);

     p=new Population (liste_seq,listeObjets,pm, pc,taille_population,generation,capacity);
             
  }catch(Exception e){ System.out.println("erooooooooooooooor "); } 
  }  //Fin while
  
  if(generation==nbr_generation || Nbr_echec==max_echec) //si le poids de la solution optimale < capacity
   {
      try 
      {
       System.out.println("toute les meilleurs solutions");
       for(int i =0;i<liste_solution.size();i++)
      {
         liste_solution.get(i).affich(listeObjets);
       }   
  
      System.out.println("**************************");
        
          System.out.println("la solution la plus optimale est trouvée à l'iterration : "+generation);
          best=Sequence.choisir_meilleur(liste_seq);
          liste_solution.add(best);
         ite=liste_solution.indexOf(best);
         System.out.println("ite: "+  ite);
         
      }catch(Exception e){ System.out.println("erooooooooooooooor ");}
 //Get time at the end Time execution
  Long end =  System.currentTimeMillis();
  // Calculate the execution time
  timeExecution = end-begin;
  // Display the execution time
  System.out.println("Execution in " + timeExecution + " ms");
  
  //sauvegarder les infos afficher dans best sol
  //sauvegarder les résultats en GA_Result
   // In order to append text to a file, you need to open  file into append mode, you do it by using  FileReader and passing append = true
  FileWriter fw = null;
  BufferedWriter bw = null;
  PrintWriter pw = null;
  String save_result="Result/GA_Result"+".txt";
  fw = new FileWriter(save_result, true); //passing  mode append = true
  bw = new BufferedWriter(fw);
  pw = new PrintWriter(bw);
  double fitness=best.fitness;
  System.out.println("best.fitness :"+best.fitness);
  ligne=nbrObj+","+fitness+","+timeExecution+","+ite;
  pw.println(ligne);
  System.out.println("Data Successfully appended into file");
  pw.flush();
  try
       {
         pw.close(); bw.close(); fw.close();
        } catch (IOException io) { System.out.println("errror");  }
  
   //Interface of best solution 
        javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader();
        loader.setLocation(getClass().getResource("bestSol.fxml"));
        Parent tableDet = loader.load();
        BestSolController control =loader.getController();
        control.Affich_Solution(capacity, listeObjets, liste_solution,best);
        Scene tableDetScene = new Scene(tableDet);
        Stage window = new Stage();
        window.setTitle("Best Solution of Genetic Algorithm");
        window.setScene(tableDetScene);
        window.show();

          liste_seq.clear();

          
 }      }} 
       }catch(NumberFormatException k)  {
        Alert alert = new Alert (Alert.AlertType.WARNING); 
        alert.setTitle("Error ");
        alert.setHeaderText(null);
        alert.setContentText("Verify if the fields aren't empty or incompatible type!");
        alert.showAndWait();
          }   
   }

    @FXML
    private void InitialiseAction(ActionEvent event) {
        
        this.pc .clear();
       this.pm.clear();
       this.taille_population .clear();
        this.nbGener.clear();
        this.listeObjets.clear();
        liste_seq.clear();
        this.Pop_Table.getItems().clear();
        this.liste_solution.clear();
       this.existBenPane.setVisible(false);
         
    }

    @FXML
    private void RetourAction(ActionEvent event) throws IOException {
    javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader();
        loader.setLocation(getClass().getResource("metaheuritiques.fxml"));
        MetaheuritiquesController cont = loader.getController();
        
        Parent tableDet = loader.load();
        Scene tableDetScene = new Scene(tableDet);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(tableDetScene);
        window.show();            
        
        
        
    }

}

   
        
    


   
   








