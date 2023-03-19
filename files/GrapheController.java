
package malha_taous;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;


public class GrapheController implements Initializable {

    
    //graphe pour les benchmarcks en fonction du temps
    @FXML
    private LineChart<?, ?> LineChart1;
    @FXML
    private NumberAxis axe_Y;
    @FXML
    private CategoryAxis axe_X;
   
    @FXML
    private LineChart<?, ?> LineChart2;
    @FXML
    private NumberAxis axe_y2;
    @FXML
    private CategoryAxis axe_X2;
  
    

    
  ArrayList<Graphe> liste_Result_GA =new ArrayList<Graphe>();
  ArrayList<Graphe> liste_Result_SA =new ArrayList<Graphe>();
  ArrayList<Graphe> liste_new=new ArrayList<Graphe>();
  int n;
  double capacity;
 int nbrObj;
  ArrayList<Sequence> liste_sol=new ArrayList<>();
 ArrayList<saisiInf> listeObjets=new ArrayList<>();   
 SolutionRecuit bestOne;
 Sequence Plus_Optimal;
    @FXML
    private Button retour;
   
 @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
       
    }  
    
 void read_files(double capacity,ArrayList<saisiInf>listeObjets,SolutionRecuit Best_sol,Sequence best,ArrayList<Sequence> liste_solutions,int a) throws FileNotFoundException{

 this.n=a;
 this.capacity=capacity;
 this.listeObjets=listeObjets;
 if(n==0){this.retour.setVisible(false);}
 
 if(n==1)
 {
     
    for(int i=0;i<liste_solutions.size();i++)
 {
   liste_sol.add(liste_solutions.get(i));
 }
}
 
 if(n==2)
 
 {
  this.bestOne=Best_sol;
  SolutionRecuit Plus_Optimal =  Best_sol;

 } 
    int name_file;
    int iterration;
    double result_fitness,result_time; 
   
  try{
   File fichier= new File("Result/SA_Result.txt");
   File fichier2= new File("Result/GA_Result.txt");
   
   

 //SA et GA n'existent pas
 if((!fichier.exists() && !fichier2.exists())||(fichier.length()==0 && fichier2.length()==0))
 {       
        Alert alert = new Alert (Alert.AlertType.WARNING); 
        alert.setTitle("Warning ");
        alert.setHeaderText(null);
        alert.setContentText("Error in Result's files ! please try to run benchmarks with both GA and SA methods...");
        alert.showAndWait();   
        alert.close();
       
 }


 //SA existe et GA n'existe pas ou peut etre qu'il est vide
if((fichier.exists() && fichier.length()!=0) && (fichier2.length()==0 || !fichier2.exists()) )
{
     // Graphes SA  en fct du temps
      XYChart.Series SA =new  XYChart.Series<>();
      SA.setName("SA_time");
      
     // Graphes SA  en fct du fitness
      XYChart.Series SA_Fitness =new  XYChart.Series<>();
      SA_Fitness.setName("SA_fit");

    
    Scanner in =new Scanner(fichier);
    System.out.println("lire le fichier SA!");       
    while(in.hasNext())
    {
      String line=in.nextLine();
      //découpage de chaque ligne 
      String[] decoupage = line.split(",");
      name_file = Integer.parseInt(decoupage[0]);//récuperer le nom du fichier

      result_fitness = Double.parseDouble(decoupage[1]);//récuperer result_fitness
      result_time = Double.parseDouble(decoupage[2]);//récuperer result_time
      iterration=Integer.parseInt(decoupage[3]);
      Graphe g =new Graphe(name_file,result_fitness,result_time,iterration);  
       
    
      this.liste_Result_SA.add(g); 
    }
     
    //Affichage de liste_Result_SA
    for(int i=0;i<liste_Result_SA.size();i++) 
    {
       liste_Result_SA.get(i).Afficher_Result();  
    }
    
     //tri
     System.out.println("le tri");
     liste_new=Graphe.trier_liste(liste_Result_SA);
     
     for(int i=0;i<liste_new.size();i++) 
      {
          liste_new.get(i).Afficher_Result();
      }
    
    for(int j=0;j<liste_new.size();j++)   
      { 

        // pour recuit simulé et temps
        String name=Integer.toString(liste_new.get(j).name_file);
        SA.getData().add(new XYChart.Data<>(name,liste_Result_SA.get(j).result_time));//nbr_bench+time 
                  
       // pour recuit simulé et fitness
        SA_Fitness.getData().add(new XYChart.Data<>(name,liste_Result_SA.get(j).result_fitness));//nbr_bench+fitness
      

      }
        LineChart1.getData().addAll(SA);
        LineChart2.getData().addAll(SA_Fitness);        

  }    
    


//GA existe et SA n'existe pas ou peut etre qu'il est vide
if((fichier2.exists() && fichier2.length()!=0) && (!fichier.exists() || fichier.length()==0))
{
     // Graphes GA en fct du temps
      XYChart.Series GA =new  XYChart.Series<>();
      GA.setName("GA_time");
        
      // Graphes  GA en fct du fitness
      XYChart.Series GA_Fitness =new  XYChart.Series<>();
      GA_Fitness.setName("GA_fit");
      

    Scanner inp =new Scanner(fichier2);
    System.out.println("lire le fichier GA!");       
     while(inp.hasNext())
    {
      String line=inp.nextLine();
      //découpage de chaque ligne 
      String[] decoupage = line.split(",");
      name_file = Integer.parseInt(decoupage[0]);//récuperer le nom du fichier
      result_fitness = Double.parseDouble(decoupage[1]);//récuperer result_fitness
      result_time = Double.parseDouble(decoupage[2]);//récuperer result_time
      iterration=Integer.parseInt(decoupage[3]);
      Graphe g =new Graphe(name_file,result_fitness,result_time,iterration);   
      this.liste_Result_GA.add(g); 
    }
     
       //Affichage de liste_Result_GA
       for(int i=0;i<liste_Result_GA.size();i++) 
       {  
           liste_Result_GA.get(i).Afficher_Result(); 
       }
 
     //tri
     System.out.println("le tri");
     liste_new=Graphe.trier_liste(liste_Result_GA);
     
     for(int i=0;i<liste_new.size();i++) 
      {
          liste_new.get(i).Afficher_Result();
      }
     for(int i=0;i<liste_new.size();i++) 
    { 
        // pour genetique et temps
        String name=Integer.toString(liste_new.get(i).name_file); 
        GA.getData().add(new XYChart.Data<>(name, liste_Result_GA.get(i).result_time));//nbr_bench+time 
       
        // pour genetique et fitness
         GA_Fitness.getData().add(new XYChart.Data<>(name,liste_Result_GA.get(i).result_fitness));//nbr_bench+fitness 
       

    }
        LineChart1.getData().addAll(GA);       
        LineChart2.getData().addAll(GA_Fitness);     

    }    
   

 
   
//SA et GA existent (tout les deux)
 if( fichier.exists() && fichier2.exists() && fichier.length()!=0 && fichier2.length()!=0)
 {
      // Graphes SA et GA en fct du temps
      XYChart.Series SA =new  XYChart.Series<>();
     // SA.setName("SA");
      
      
      XYChart.Series GA =new  XYChart.Series<>();
      //GA.setName("GA");
        
      // Graphes SA et GA en fct du fitness
      XYChart.Series SA_Fitness =new  XYChart.Series<>();
      SA_Fitness.setName("SA");
      
      XYChart.Series GA_Fitness =new  XYChart.Series<>();
      GA_Fitness.setName("GA");
      

 
 try{
    Scanner in =new Scanner(fichier);
    System.out.println("lire le fichier SA!");       
    while(in.hasNext())
    {
      String line=in.nextLine();
      //découpage de chaque ligne 
      String[] decoupage = line.split(",");
      name_file = Integer.parseInt(decoupage[0]);//récuperer le nom du fichier
      System.out.print(name_file+" ");
      result_fitness = Double.parseDouble(decoupage[1]);//récuperer result_fitness
      result_time = Double.parseDouble(decoupage[2]);//récuperer result_time
      System.out.print(result_fitness+" ");
      iterration=Integer.parseInt(decoupage[3]);
      System.out.print(result_time+" ");
      System.out.print(iterration+" ");
      Graphe g =new Graphe(name_file,result_fitness,result_time,iterration);
      
      this.liste_Result_SA.add(g);
        System.out.println();
        System.out.println();

    }
     
    //Affichage de liste_Result_SA
    for(int i=0;i<liste_Result_SA.size();i++) 
    {
       liste_Result_SA.get(i).Afficher_Result();  
    }

    //deuxieme fichier    
    Scanner inp =new Scanner(fichier2);
    System.out.println("lire le fichier GA!");       
     while(inp.hasNext())
    {
      String line=inp.nextLine();
      //découpage de chaque ligne 
      String[] decoupage = line.split(",");
      name_file = Integer.parseInt(decoupage[0]);//récuperer le nom du fichier
      System.out.print(name_file+" ");
      result_fitness = Double.parseDouble(decoupage[1]);//récuperer result_fitness
      System.out.print(result_fitness+" ");
      result_time = Double.parseDouble(decoupage[2]);//récuperer result_time
      System.out.print(result_time+" ");
      iterration=Integer.parseInt(decoupage[3]);
      System.out.print(iterration);
      Graphe g =new Graphe(name_file,result_fitness,result_time,iterration);   
      this.liste_Result_GA.add(g);  
      System.out.println();
      System.out.println();
    }
     
       //Affichage de liste_Result_GA
       for(int i=0;i<liste_Result_GA.size();i++) //la taille de pop
       {  
           liste_Result_GA.get(i).Afficher_Result(); 
       }
 }catch(Exception e){System.out.println("erroooooooooooor");}
 
     //tri
     System.out.println("le tri");
     liste_new=Graphe.trier_liste(liste_Result_GA);
     
     for(int i=0;i<liste_new.size();i++) 
      {
          liste_new.get(i).Afficher_Result();
      }

    //on verifie a chaque fois si un benchmarck existant dans les 2 listes si oui on l'insere dans le graphe
    int i,j;

    for(i=0;i<liste_new.size();i++)   { 
      j=0;    
      System.out.println("i "+i);
     while(j<liste_Result_SA.size()) {    
    try{               
      if(Integer.toString(liste_new.get(i).name_file).equalsIgnoreCase(Integer.toString(liste_Result_SA.get(j).name_file)))   {
      
       // pour recuit simulé et temps
        SA.getData().add(new XYChart.Data<>(Integer.toString(liste_Result_SA.get(j).name_file),liste_Result_SA.get(j).result_time));//nbr_bench+time 
                  
        // pour genetique et temps
        GA.getData().add(new XYChart.Data<>(Integer.toString(liste_new.get(i).name_file), liste_Result_GA.get(i).result_time));//nbr_bench+time 
       
        
        // pour recuit simulé et fitness
         SA_Fitness.getData().add(new XYChart.Data<>(Integer.toString(liste_Result_SA.get(j).name_file), liste_Result_SA.get(j).result_fitness));//nbr_bench+fitness
      
        // pour genetique et fitness
         GA_Fitness.getData().add(new XYChart.Data<>(Integer.toString(liste_new.get(i).name_file),liste_Result_GA.get(i).result_fitness));//nbr_bench+fitness 

         break;
       }
      j++;
      }catch(Exception e){ System.out.println("errooor");}
       
    }}  LineChart1.getData().addAll(SA);
        LineChart1.getData().addAll(GA);
        LineChart2.getData().addAll(SA_Fitness);       
        LineChart2.getData().addAll(GA_Fitness);  

        
 }  } catch(Exception e){ System.out.println("errooor");}
 }
  
 
    @FXML
    private void retourACT(ActionEvent event) throws IOException {
     if(n==1)
         {
         javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader();
        loader.setLocation(getClass().getResource("bestSol.fxml"));
        Parent tableDet = loader.load();
        BestSolController controller = loader.getController();
        controller.Affich_Solution(capacity, listeObjets, this.liste_sol,this.Plus_Optimal);
        Scene tableDetScene = new Scene(tableDet);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(tableDetScene);
        window.show();       
         
 
         } 
    if(n==2)
         {
        javafx.fxml.FXMLLoader load = new javafx.fxml.FXMLLoader();
        load.setLocation(getClass().getResource("bestSol.fxml"));
        Parent tableDt = load.load();
        BestSolController controll = load.getController();
        controll.Affich_Sol(capacity, listeObjets, this.bestOne);
       
        Scene tableDtScene = new Scene(tableDt);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(tableDtScene);
        window.show();  
         
         }    
     }
} 