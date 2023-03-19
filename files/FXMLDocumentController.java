
package malha_taous;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class FXMLDocumentController implements Initializable {
    
    @FXML
    private Button suiv;
    @FXML
    private Label label;
       @FXML
    private Label label3;
    @FXML
    private Button kp;
    @FXML
    private Button exit;
    @FXML
    private Label label1;
   
   
    @FXML
    private void pourSuivant(ActionEvent event) throws IOException {
   
    javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader();
      loader.setLocation(getClass().getResource("essai.fxml"));
      
        EssaiController cont = loader.getController();
        
        Parent tableDet = loader.load();
        Scene tableDetScene = new Scene(tableDet);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(tableDetScene);
        window.show();
        
    }
    public void initialize(URL url, ResourceBundle rb) {
    
    }    

    @FXML
    private void enonce(ActionEvent event) throws IOException {
        javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader();
        loader.setLocation(getClass().getResource("knapsack.fxml"));
        KnapsackController cont = loader.getController();
        
        Parent tableDet = loader.load();
        Scene tableDetScene = new Scene(tableDet);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(tableDetScene);
        window.show();  
    }

    @FXML
    private void ExitAction(ActionEvent event) {
      Stage stage = (Stage) exit.getScene().getWindow();
      stage.close();  
        
    }
    
}
