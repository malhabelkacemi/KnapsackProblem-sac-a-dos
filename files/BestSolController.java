
package malha_taous;


import static malha_taous.SA_ExitBenchController.iter;
import static malha_taous.SA_ExitBenchController.timeExec;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import static malha_taous.ExistBenchController.ite;
import static malha_taous.ExistBenchController.timeExecution;


public class BestSolController implements Initializable {

    /*@FXML
    private Label Seq;*/
    @FXML
    private Label time;
    @FXML
    private Label tot_fitness;
    @FXML
    private Label tot_weight;
    @FXML
    private TableView<saisiInf> sol_table;
    @FXML
    private TableColumn<saisiInf,String> name;
    @FXML
    private TableColumn<saisiInf,Integer> fitness;
    @FXML
    private TableColumn<saisiInf,Double> weight;
    @FXML
    private Button show;
    @FXML
    private Label iteration;
    @FXML
    private Label itr;
    int a;
 double capacity;
 int nbrObj;
 ArrayList<Sequence> liste_sol=new ArrayList<>();
 ArrayList<saisiInf> listeObjets=new ArrayList<>();   
 Sequence meilleure_Sol;   
SolutionRecuit bestOne;
 Sequence Plus_Optimal;

   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.name.setCellValueFactory(new PropertyValueFactory<saisiInf,String>("name"));
        this.fitness.setCellValueFactory(new PropertyValueFactory<saisiInf,Integer>("valeur"));
        this.weight.setCellValueFactory(new PropertyValueFactory<saisiInf,Double>("poids"));
      }    

  
 //GA   
void Affich_Solution(double capacity,ArrayList<saisiInf>listeObjets,ArrayList<Sequence>liste_solution,Sequence Plus_Opt)
{  
 a=1;
 this.capacity=capacity;
 this.listeObjets=listeObjets;
 
 for(int i=0;i<liste_solution.size();i++)
 {
   liste_sol.add(liste_solution.get(i));
 }

  Plus_Optimal = Sequence.choisir_meilleur(liste_solution);
  Plus_Opt=Plus_Optimal ;
  ArrayList<Integer> sequence=Plus_Optimal.sequence;
  String listS = String.valueOf(sequence);
  //Seq.setText(listS);
  
  double fitness=Plus_Optimal.fitness;
  String valFit=String.valueOf(fitness);
  tot_fitness.setText(valFit);
  
  double WeightT=Plus_Optimal.poids_seq;
  String totWeight=String.valueOf(WeightT);
  tot_weight.setText(totWeight);
  
  this.sol_table.getItems().clear();
  for (int i=0;i<Plus_Optimal.sequence.size();i++){
    if (Plus_Optimal.sequence.get(i)==1){
        
      this.sol_table.getItems().addAll(listeObjets.get(i));
    }  
  }
  long timeEx=timeExecution;
  String timexec=String.valueOf(timeEx);
  time.setText(timexec +" ms");
  
  itr.setText("The solution was found in generation:");
  int itr=ite;
  String solite=String.valueOf(itr);
  iteration.setText(solite); 
}
 void Affich_Sol(double capacity,ArrayList<saisiInf>listeObjets,SolutionRecuit Best_sol)
{   
 a=2;
 this.capacity=capacity;
 this.listeObjets=listeObjets;

 SolutionRecuit Plus_Optimal =  Best_sol;
 this.bestOne=Best_sol;
  int [] sequence= Best_sol.solution;
  String listS=" ";
  for(int i=0;i< sequence.length;i++)
  {
      int a=sequence[i];
      listS = listS+ a;
  }
  //Seq.setText(listS);
  
  double fitness=Plus_Optimal.fitness;
  String valFit=String.valueOf(fitness);
  tot_fitness.setText(valFit);
  
  
 double WeightT= Plus_Optimal.totalWeight;
  String totWeight=String.valueOf(WeightT);
  tot_weight.setText(totWeight);
  
  this.sol_table.getItems().clear();
  for (int i=0;i<Plus_Optimal.solution.length;i++){
    if (Plus_Optimal.solution[i]==1){
        
      this.sol_table.getItems().addAll(listeObjets.get(i));
    }  
  }
  
   long timeEx=timeExec;
  String timexec=String.valueOf(timeEx);
  time.setText(timexec +" ms");
  
 
   itr.setText("The solution was found in bearing number:");
  int itr=iter;
  String solite=String.valueOf(itr);
  iteration.setText(solite);
 
} 

   @FXML
    private void ShowAction(ActionEvent event)throws IOException
    {
       if(a==1)
       {
        javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader();
        loader.setLocation(getClass().getResource("Graphe.fxml"));
        Parent tableDet = loader.load();
        GrapheController controller = loader.getController();
        controller.read_files(this.capacity,this.listeObjets,null,this.Plus_Optimal,this.liste_sol,a);
        Scene tableDetScene = new Scene(tableDet);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(tableDetScene);
        window.show();  
       } 
           
      if(a==2)
       {
        javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader();
        loader.setLocation(getClass().getResource("Graphe.fxml"));
        Parent tableDet = loader.load();
        GrapheController controller = loader.getController();
        controller.read_files(this.capacity,this.listeObjets,this.bestOne,null,this.liste_sol,a);
        Scene tableDetScene = new Scene(tableDet);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(tableDetScene);
        window.show();  
       }
    }
}
