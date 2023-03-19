
package malha_taous;
import static malha_taous.ExistBenchController.timeExecution;
import static malha_taous.Population.liste_seq;
import static malha_taous.SA_ExitBenchController.timeExec;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javax.swing.JFileChooser;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author MMC
 */
public class Experimentation_MultipleController implements Initializable {

    @FXML
    private TextField taille_population;
    @FXML
    private Label pop_size;

    public TextField getTaille_population() {
        return taille_population;
    }

    public Label getPop_size() {
        return pop_size;
    }

    public TextField getNbGener() {
        return nbGener;
    }

    public Label getGenr() {
        return genr;
    }

    public TextField getPm() {
        return pm;
    }

    public TextField getPc() {
        return pc;
    }

    public Label getMut() {
        return mut;
    }

    public Label getCros() {
        return cros;
    }

    public TextField getTemp_max() {
        return temp_max;
    }

    public TextField getTemp_min() {
        return temp_min;
    }

    public TextField getNbr_ite() {
        return nbr_ite;
    }

    public TextField getAlpha() {
        return alpha;
    }

    public Label getInitial_temp() {
        return initial_temp;
    }

    public Label getFinal_temp() {
        return final_temp;
    }

    public Label getIteration() {
        return iteration;
    }

    public Label getCool_rate() {
        return cool_rate;
    }

    public Label getLabel_SA() {
        return label_SA;
    }
    @FXML
    private TextField nbGener;
    @FXML
    private Label genr;
    @FXML
    private TextField pm;
    @FXML
    private TextField pc;
    @FXML
    private Label mut;
    @FXML
    private Label cros;
    @FXML
    private TextField temp_max;
    @FXML
    private Button run;
    @FXML
    private Button initialise;
    @FXML
    private TextField temp_min;
    @FXML
    private TextField nbr_ite;
    @FXML
    private TextField alpha;
    @FXML
    private Label initial_temp;
    @FXML
    private Label final_temp;
    @FXML
    private Label iteration;
    @FXML
    private Label cool_rate;
    @FXML
    private Label label_SA;
    @FXML
    private Button returnRnd;

    @FXML
    private Button vider;
    
    ArrayList<saisiInf> listeObjets =new ArrayList<saisiInf>();
    double capacity;
    int nbrObj;
    ArrayList<SolutionRecuit> liste_verif=new ArrayList<>();
    ArrayList<SolutionRecuit> liste_sol=new ArrayList<>();
    Long timeExec_GA,timeExec_SA; 
    int ite_GA,ite_SA,count=0;
    
    String ligne;  
     
     static int generation; 
     
     

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void RunAction(ActionEvent event) throws FileNotFoundException, IOException {
   try{
    double pc= Double.parseDouble(this.pc.getText());
    double pm= Double.parseDouble(this.pm.getText());
    int taille_population= Integer.parseInt(this.taille_population.getText());
    int nbr_generation= Integer.parseInt(this.nbGener.getText());
    
    double INITIAL_TEMPERATURE = Double.parseDouble(this.temp_max.getText()); 
    double FINAL_TEMPERATURE= Double.parseDouble(this.temp_min.getText());
    double Cool_Rate= Double.parseDouble(this.alpha.getText());
    int Nb_iterr=Integer.parseInt(this.nbr_ite.getText());
 
  
  if((this.pc.getText()!=null)&&(this.pm.getText()!=null)&&(this.nbGener.getText()!=null)&&(this.taille_population.getText()!=null) && (this.temp_min.getText()!=null)&&(this.temp_max.getText()!=null)&&(this.alpha.getText()!=null)&&(this.nbr_ite.getText()!=null)) 
{
      Alert alert = new Alert (Alert.AlertType.WARNING); 
       if(taille_population<18)
    {
            alert.setTitle("Error Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Failed! size population invalid !");
            alert.showAndWait();
            this.taille_population.clear(); 
    }
    if(nbr_generation<5) 
    {
            alert.setTitle("Error Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Failed! number of generations invalid !");
            alert.showAndWait(); 
            this.nbGener.clear(); 
    }
    
    if((pc<=0)||(pc>1))
     {
            alert.setTitle("Error Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Failed! use a number between 0 and 1 !");
            alert.showAndWait(); 
            this.pc.clear(); 
               
       }
     if((pm<=0)||(pm>1))
            {   
            alert.setTitle("Error Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Failed! use a number between 0 and 1 !");
            alert.showAndWait();
            this.pm.clear(); 
          }
        
    if(INITIAL_TEMPERATURE <=20)
    {
            alert.setTitle("Error Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Failed! Initial_Temperature invalid... please try to enter a temperature more than this one!");
            alert.showAndWait();
            this.temp_max.clear();   
    }
       
    if(FINAL_TEMPERATURE>=INITIAL_TEMPERATURE  ) 
    {
            alert.setTitle("Error Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Error! Final_Temperature invalid ... please try to enter a temperature less than the initial one !");
            alert.showAndWait(); 
            this.temp_min.clear(); 
    }
    
    if(Nb_iterr<=5) 
    {
            alert.setTitle("Error Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Error!Number of iterration of one bearing should be more than 5!");
            alert.showAndWait(); 
            this.nbr_ite.clear(); 
    }
    
    if((Cool_Rate<=0)||(Cool_Rate>=1))
            {   
            alert.setTitle("Error Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Failed! Enter a Cooling_Rate between 0 and 1 !");
            alert.showAndWait();
            this.alpha.clear(); 
          }
  if ((INITIAL_TEMPERATURE>=20 && FINAL_TEMPERATURE<INITIAL_TEMPERATURE  && Cool_Rate>0 && Cool_Rate<1 && Nb_iterr>5)&&(taille_population>=18 && nbr_generation>=5 && pc>0 && pc<=1 && pm>0 && pm<=1))
    {
        
        JFileChooser dialogue = new JFileChooser(new File("."));
        dialogue.setMultiSelectionEnabled(true);
        dialogue.setFileSelectionMode(JFileChooser.FILES_ONLY);	//permet de ne sélectionner que les fichiers.
        int retour = dialogue.showOpenDialog(dialogue); 
        File[] liste_files=dialogue.getSelectedFiles();

   for(int j=0;j<liste_files.length;j++)
   {  
     System.out.println(liste_files.length);
     String name_file=liste_files[j].getName();     
     File fich=new File("Benchmarks",name_file);
    
      System.out.println("lire le fichier! "+liste_files[j].getName() );    
      Scanner in =new Scanner(fich);
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
      System.out.println();
   }
    
   //*****************************************Executer le fichier avec l'algorithme génetique*****************************************//
  
     Long begin_GA =  System.currentTimeMillis();
     generation=0;
     int Nbr_echec=0;
     int max_echec=50;
     ArrayList<Sequence> liste_new=new ArrayList<>();
     ArrayList<Sequence> liste_solution=new ArrayList<>();
     ArrayList<Sequence> liste_verif2=new ArrayList<>();
    
    
    Sequence best=new Sequence();
    Population p =new Population(liste_seq,listeObjets,pm, pc,taille_population,nbr_generation,capacity);
    System.out.println("capacity :"+capacity);
    System.out.println("nbr d objets :"+nbrObj);
     
   
     Sequence seq;
     Random r = new Random();
     int randItem = r.nextInt(listeObjets.size());
     
      for(int i=0;i<taille_population;i++)
     {
         seq=Sequence.generer_sequence(listeObjets,capacity);
        
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
     System.out.println("generer sequence "+seq.compute_weight(listeObjets));
      
     }
            liste_seq.add(seq);
     }
    
     System.out.println("debut while");
     
 while(generation<nbr_generation )   {        
   
     System.out.println("*******************generation******************* "+generation);
     //tri
     System.out.println("le triiiiiii");
     p.liste_seq=Sequence.trier_liste_sequences(liste_seq);
     
     for(int i=0;i<p.liste_seq.size();i++) //la taille de pop
      {  p.liste_seq.get(i).affich(listeObjets);  }
     
   
try {
    //selection de la moitié
      p.liste_seq=Sequence.selection(taille_population/2,p.liste_seq);
      
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
    for (int i=taille_population/2;i<taille_population;i++)  
    {
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
    
     liste_seq.clear();
     System.out.println("mutation:"); 
        for(int i=0;i<liste_new.size();i++)
      {
        liste_seq.add(liste_new.get(i).mutate(pm,listeObjets,capacity));
      }   
   System.out.println("donc la  liste seq est la suivante :");
    for(int i=0;i<liste_seq.size();i++)
  {
     liste_seq.get(i).affich(listeObjets);
   
  } 
       liste_new.clear();
       System.out.println("la taille de pop est : "+taille_population);
       System.out.println("la taille des liste_seq est:"+ liste_seq.size());
       generation++;
   
     best=Sequence.choisir_meilleur(liste_seq);//il choisit la meilleure sol dans liste_seq  puis il va la mettre dans liste_solution
     liste_solution.add(best);
     liste_verif2.add(best);
    
        //pour le critere d arret:nbr d echec 
        if(liste_verif2.size()>=2)
             {  
                 
                 System.out.println("voila");
                 liste_verif2.get(liste_verif2.size()-1).affich(listeObjets);
                 liste_verif2.get(liste_verif2.size()-2).affich(listeObjets);
                 if((liste_verif2.get(liste_verif2.size()-1).fitness)==liste_verif2.get(liste_verif2.size()-2).fitness)
                 {   
                     Nbr_echec++;
                     System.out.println("****Nbr_echec: **** "+Nbr_echec);
                 } 
                 else {
                         
                         liste_verif2.clear();
                         Nbr_echec=0;
                         System.out.println("***remettre nbr echec: *** "+Nbr_echec);
                       }
             }
            if( Nbr_echec==max_echec) 
            {
                System.out.println("*********************** il a atteint le nombre max d'echecs*********************** "+Nbr_echec);
                break;
 
            }
       
      p=new Population (liste_seq,listeObjets,pm, pc,taille_population,generation,capacity);
             
  }catch(Exception e){ System.out.println("eroor "); } 
  }//Fin while
  
  if(generation==nbr_generation ||  Nbr_echec==max_echec)   //si le poids de la solution optimale < capacity
   {
      try 
      {
       System.out.println("toute les meilleurs solutions");
       for(int i =0;i<liste_solution.size();i++)
      {
         liste_solution.get(i).affich(listeObjets);
       }   
          
          System.out.println("la solution la plus optimale est trouvée à l'iterration : "+generation);
          best=Sequence.choisir_meilleur(liste_seq);
          liste_solution.add(best);
          ite_GA=liste_solution.indexOf(best);
          System.out.println("ite: "+ite_GA);
         
      }catch(Exception e){ System.out.println("eroor ");}
 
//Get time at the end Time execution
  Long end =  System.currentTimeMillis();
  // Calculate the execution time
  timeExecution = end - begin_GA;
  // Display the execution time
  System.out.println("Execution in " + timeExecution + " ms");
  
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
  ligne=nbrObj+","+fitness+","+timeExecution+","+ite_GA;
  pw.println(ligne); 
  System.out.println("Data Successfully appended into file");
  pw.flush();
  try
       {
         pw.close(); bw.close(); fw.close();
        } catch (IOException io) { System.out.println("errror");  }
  
      } 
      liste_seq.clear(); liste_new.clear();  liste_solution.clear();  liste_verif2.clear();
        
    

   
   //******************************Executer le fichier avec l'algorithme de récuit simulé******************************/
   
      Long begin_SA =  System.currentTimeMillis();       
      double temperature = INITIAL_TEMPERATURE;
    
       /* SolutionRecuit<saisiInf> current = new SolutionRecuit<>(nbrObj,capacity,listeObjets, null);
        
        ArrayList<Integer> solutionKey = current.getTakenItems();
        SolutionRecuit Plus_Optimal=new SolutionRecuit();
        System.out.println("current solution : ");
        current.affich(listeObjets);
        Plus_Optimal = current;
         
         System.out.println("bestFitness :"+Plus_Optimal.fitness);*/
           SolutionRecuit  current=new SolutionRecuit();
           current.numberOfItems =nbrObj;
           current.Id=0;
           current.solution = new int[nbrObj];
           current.itemList=listeObjets;
           current.cap=capacity;
           
        System.out.println("generer la solution initiale: ");
   
 //System.out.println("capacity :"+capacity);
    Random rand = new Random();
    randItem = rand.nextInt(listeObjets.size());
      
        for(int i=0;i<listeObjets.size();i++)   
	  {   
              int w=0;
	    int nb=(int)(Math.random() *2);
            w+=listeObjets.get(i).getPoids();
            if (w<=capacity) 
	    current.solution[i]=nb;
            else current.solution[i]=0;
	  }  
      
     if (current.calculateWeight()<=capacity){
         current.fitness=current.calculatetotalProfit();
         current.totalWeight=current.calculateWeight();
         current.Id=0;
     }else {
        ArrayList<Integer> takenItemsID = current.getTakenItems();
                do {
                    randItem = r.nextInt(takenItemsID.size());
                    current.solution[takenItemsID.get(randItem)] = 0;
                    
                }while (current.calculateWeight()>capacity);     
        current.fitness=current.calculatetotalProfit();
        current.totalWeight=current.calculateWeight();
         current.Id=0; 
     }
   
        System.out.println("current solution : ");
        current.affich(listeObjets);
        ArrayList<Integer> solutionKey = current.getTakenItems();
        SolutionRecuit  Plus_Optim=new SolutionRecuit();
        Plus_Optim = current;
         
        System.out.println("bestFitness :"+Plus_Optim.fitness);
      
          Nbr_echec=0;
          max_echec=250;
         int palier=0;
         while (temperature > FINAL_TEMPERATURE && Nbr_echec<max_echec)
         {   
             int iterration=0;
             for(int i=0;i<Nb_iterr;i++)  
             {
             iterration++;
             System.out.println("iterration "+iterration);
	     count++;
            
             try{
            SolutionRecuit<saisiInf>neighbor = new SolutionRecuit<>(nbrObj,capacity,listeObjets, current.findNeighbour().solution);
           
              neighbor.affich(listeObjets);
            
             boolean accept_sol=isNeighbourAccapted(current, neighbor, temperature);
           //  System.out.println(accept_sol);
              if (accept_sol)
             {  
                 current = neighbor;
              }
              
             
              liste_sol.add(current);
              liste_verif.add(Plus_Optim);
              
             }catch(Exception e){
               System.out.println("erroor"); } 
	      if (Plus_Optim.fitness< current.fitness)
              {
                System.out.println("soluuution : ");
                Plus_Optim=current;
                Plus_Optim.affich(listeObjets);
                Plus_Optim.fitness = current.fitness;
                solutionKey = current.getTakenItems();
                ite_SA=palier;
                System.out.println("iter:"+ite_SA); 
              
              }
             if(liste_verif.size()>=2)
             {  
                 System.out.println("size:"+liste_verif.size());
                 System.out.println("voila");
                 liste_verif.get(liste_verif.size()-1).affich(listeObjets);
                 liste_verif.get(liste_verif.size()-2).affich(listeObjets);
                 if((liste_verif.get(liste_verif.size()-1).fitness)==liste_verif.get(liste_verif.size()-2).fitness)
                 {   
                     Nbr_echec++;
                     System.out.println("Nbr_echec: "+Nbr_echec);
                 } 
                 else {
                         
                         liste_verif.clear();
                         Nbr_echec=0;
                         System.out.println("remettre nbr echec: "+Nbr_echec);
                       }
             }
            if( Nbr_echec==max_echec) break;
              
             }  
                palier++;
                 temperature *= Cool_Rate;
                 temperature =(double)((int)(temperature*100))/100;
                 System.out.println("temp changement :"+temperature);
           
         }       
           if (temperature<=FINAL_TEMPERATURE ||  Nbr_echec>=max_echec)
         {  
             System.out.println("Resultat");
             System.out.println(current.fitness + ": " + current.getTakenItems());
             System.out.println( Plus_Optim.fitness + ": " + solutionKey);
         }
     System.out.println("iter:"+ite_SA); 
     System.out.println("palier:"+palier);     
           
//Get time at the end Time execution
  Long end =  System.currentTimeMillis();
  // Calculate the execution time
  timeExec = end-begin_SA;
  // Display the execution time
  System.out.println("Execution in " + timeExec + " ms");

    
  FileWriter fw = null; 
  BufferedWriter bw = null;
  PrintWriter pw = null;
  String save_result="Result/SA_Result"+".txt";
  fw = new FileWriter(save_result, true); //passing mode append = true
  bw = new BufferedWriter(fw);
  pw = new PrintWriter(bw); 
  double fitness=Plus_Optim.fitness;
  System.out.println("best.fitness :"+fitness);
 ligne=nbrObj+","+fitness+","+timeExec+","+ite_SA;
  pw.println(ligne); 
  System.out.println("Data Successfully appended into file"); 
  pw.flush(); 
  try  { 
         pw.close(); bw.close(); fw.close(); 
        } catch (IOException io) { System.out.println("errror");  }  
 
  this.listeObjets.clear(); 
  this.liste_verif.clear();
  this.count=0;
  this.liste_sol.clear();
   }//Fin for (il a traité tout les benchmarks sélectionnés)
   
        //Affichage des résultats (sur les graphes)
        javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader();
        loader.setLocation(getClass().getResource("Graphe.fxml"));
        Parent tableDet = loader.load();
        
        GrapheController controller = loader.getController();
        controller.read_files(this.capacity,this.listeObjets,null,null,null,0); 
        Scene tableDetScene = new Scene(tableDet);
        Stage window = new Stage();
        window.setTitle("Graphs representing the results of the two metaheuristics GA and SA");
        window.setScene(tableDetScene);
        window.show(); 
     
     
       } } }catch(NumberFormatException e) 
     {
        Alert alert = new Alert (Alert.AlertType.WARNING); 
        alert.setTitle("Error ");
        alert.setHeaderText(null);
        alert.setContentText("Verify if the fields aren't empty or incompatible type!");
        alert.showAndWait();
      }}
    
    
    
    @FXML
    private void InitialiseAction(ActionEvent event) 
   {
        this.pc .clear(); 
        this.pm.clear(); 
        this.taille_population .clear(); 
        this.nbGener.clear();
        liste_seq.clear();
        this.alpha.clear();
        this.temp_max.clear();
        this.temp_min.clear();
        this.nbr_ite.clear();
        this.listeObjets.clear();
        this.liste_verif.clear();
        this.count=0;
        this.liste_sol.clear();
  }
    
  public static boolean isNeighbourAccapted(SolutionRecuit current,SolutionRecuit neighbor, double temperature)
   {  
      double p=SolutionRecuit.Proba_Acceptation();//generer une proba aleatoire entre 0 et 1
       double Delta = current.fitness - neighbor.fitness;
        if  (Delta<0) 
        { return true;}
        
        return p<= Math.exp(-1*Delta/temperature);
        //la soution est acceptée si sa fitness inferieure à celle de la solution actuelle ou si la proba<= Math.exp(-1*Delta/temperature);
    }   

    @FXML
    private void returnRndAct(ActionEvent event)throws IOException {
        
        javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader();
        loader.setLocation(getClass().getResource("essai.fxml"));
        KnapsackController cont = loader.getController();
        
        Parent tableDet = loader.load();
        Scene tableDetScene = new Scene(tableDet);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(tableDetScene);
        window.show();  
    }


    @FXML
    private void Vider_Result(ActionEvent event)throws IOException
    {
        PrintWriter GA_result=new PrintWriter("Result/GA_Result"+".txt");
        GA_result.close();

        PrintWriter SA_result=new PrintWriter("Result/SA_Result"+".txt");
        SA_result.close();

        Alert alert = new Alert (Alert.AlertType.INFORMATION);
        alert.setTitle("Deleting GA and SA results files.");
        alert.setHeaderText(null);
        alert.setContentText("The files have been deleted successfully !!");
        alert.showAndWait();
    }
}
