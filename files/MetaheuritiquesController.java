
package malha_taous;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.util.Duration;



public class MetaheuritiquesController implements Initializable {
    @FXML
    private Button run;
    @FXML
    private Button GA;
    @FXML
    private Button SA;
    @FXML
    private Button retour;

static int metha;
 
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       this.GA.setVisible(false);
       this.SA.setVisible(false);
       
    }    

    @FXML
    private void GAaction(ActionEvent event) throws IOException {

       metha=1;
      javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader();
       loader.setLocation(getClass().getResource("existBench.fxml"));
      ExistBenchController control = loader.getController();
       
        Parent tableDet = loader.load();
        Scene tableDetScene = new Scene(tableDet);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(tableDetScene);
        window.show();    
    
     
     
     
    }

    @FXML
    private void SAaction(ActionEvent event) throws IOException {
      metha=2;
        javafx.fxml.FXMLLoader load = new javafx.fxml.FXMLLoader();
       load.setLocation(getClass().getResource("SA_ExitBench.fxml"));
      
       SA_ExitBenchController control = load.getController();
       
      
        Parent tabDet = load.load();
        Scene tabDetScene = new Scene(tabDet);
        Stage wind = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        wind.setScene(tabDetScene);
        wind.show();    
     
    }

  
    @FXML
    private void runAction(ActionEvent event) {
       //this.GA.setVisible(true);
       this.SA.setVisible(true);
       this.GA.setVisible(true);
     }

     @FXML
    private void RetourAction(ActionEvent event) throws IOException {
    javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader();
        loader.setLocation(getClass().getResource("essai.fxml"));
        MetaheuritiquesController cont = loader.getController();
        
        Parent tableDet = loader.load();
        Scene tableDetScene = new Scene(tableDet);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(tableDetScene);
        window.show();            
        
        
        
    }
 
}
