
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

public class KnapsackController implements Initializable {

    @FXML
    private Label label;
    @FXML
    private Button returnKp;

   
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      
    }    

    @FXML
    private void returnAction(ActionEvent event) throws IOException {
       
     javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader();
        loader.setLocation(getClass().getResource( "FXMLDocument.fxml"));
       FXMLDocumentController cont = loader.getController();
        Parent tableDet = loader.load();
        Scene tableDetScene = new Scene(tableDet);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(tableDetScene);
        window.show();            
            
    }

    
   
    
}
