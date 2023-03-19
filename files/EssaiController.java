
package malha_taous;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;


public class EssaiController implements Initializable {

    @FXML
    private Label titre;
    @FXML
    private Button exp;
    @FXML
    private Button manual;
    @FXML
    private Button rand;
    @FXML
    private Button indiv;
    @FXML
    private Button mult;
 ArrayList<saisiInf> listeObjets =new ArrayList<>();
 
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.manual.setVisible(false);
        this.rand.setVisible(false);
        this.indiv.setVisible(false);
        this.mult.setVisible(false); 
    }    

    @FXML
    private void EnterBenAct(ActionEvent event) {
     this.manual.setVisible(true);
     this.rand.setVisible(true);    
    }

    @FXML
    private void ExpriAct(ActionEvent event) {
      this.indiv.setVisible(true);
      this.mult.setVisible(true);    
    }

    @FXML
    private void manualBench(ActionEvent event) throws IOException {
      javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader();
        loader.setLocation(getClass().getResource("saisiKp.fxml"));
        SaisiKpController cont = loader.getController();
        
        Parent tableDet = loader.load();
        Scene tableDetScene = new Scene(tableDet);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(tableDetScene);
        window.show();     
    }

    @FXML
    private void randBench(ActionEvent event) throws IOException {
      int num_seq=0;
     Random rand=new Random();
     DecimalFormat df=new DecimalFormat("0.00");
     String newline = System.getProperty("line.separator");
     String ligne="";
     
     int nbrObj=40+(int)(Math.random()*(1400-40)+1);
     
     System.out.println("nbr="+nbrObj);
     double capacity=40+(Math.random()*(31450-40)+1);
     capacity= (double)((int)(capacity*100))/100;
     System.out.println("cap="+capacity);
     
    String nom_fichier="alt_"+nbrObj+"_"+capacity+".txt";
    System.out.println("nom_fich="+nom_fichier);
    Path path=Paths.get("Benchmarks",nom_fichier);
    //creation du new file
    Files.write(path, ligne.getBytes(),StandardOpenOption.CREATE_NEW,StandardOpenOption.WRITE);
   
    String nb_objets="nombre d'objets:"+nbrObj;
    Files.write(path, nb_objets.getBytes(),StandardOpenOption.APPEND);
    Files.write(path, newline.getBytes(),StandardOpenOption.APPEND);
   
    String cap="capacity:"+capacity;
    Files.write(path, cap.getBytes(),StandardOpenOption.APPEND);
    Files.write(path, newline.getBytes(),StandardOpenOption.APPEND);
   
 
   for (int i=0;i<nbrObj;i++)
   {
     num_seq++;
     
     String name="obj"+num_seq;
     double weight=30+(Math.random()*(1050.8-30));
     int value =40+(int)(Math.random()*(14500-40)+1);
     
     ligne=name+","+value+","+df.format(weight);    
     Files.write(path, ligne.getBytes(),StandardOpenOption.APPEND);
     Files.write(path, newline.getBytes(),StandardOpenOption.APPEND);
     double poids=(double)((int)(weight*100))/100;
     saisiInf o =new saisiInf(name,poids,value);
     o.Afficher_objet();
     listeObjets.add(o);
   }          
       Alert alert = new Alert (Alert.AlertType.INFORMATION); 
        alert.setTitle("Creating a new file randomly");
        alert.setHeaderText(null);
        alert.setContentText("The file : alt"+"_"+nbrObj+"_"+capacity+".txt has created successfully");
        alert.showAndWait();     
        
        
    }

    @FXML
    private void IndivBench(ActionEvent event) throws IOException {
          javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader();
        loader.setLocation(getClass().getResource("metaheuritiques.fxml"));
        MetaheuritiquesController cont = loader.getController();
        
        Parent tableDet = loader.load();
        Scene tableDetScene = new Scene(tableDet);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(tableDetScene);
        window.show();        
    }

    @FXML
    private void multipleBench(ActionEvent event)throws FileNotFoundException, IOException  {
    
        javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader();
        loader.setLocation(getClass().getResource("Experimentation_Multiple.fxml"));
        KnapsackController cont = loader.getController();
        Parent tableDet = loader.load();
        Scene tableDetScene = new Scene(tableDet);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(tableDetScene);
        window.show();  
        
        
    }
    
}
