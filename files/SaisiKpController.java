
package malha_taous;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.JTextField;


public class SaisiKpController implements Initializable {

    @FXML
   public TextField capacity;
    @FXML
    public TextField nbrObj;
    @FXML
    private Button next;
    @FXML
    public Label labelKp;
    @FXML
    private Label label_kp;
    @FXML
    private Label labelerr;
    @FXML
    private Label laelerr1;
   
   private Stage stage;
 
   public TextField getCapacity() {
        return capacity;
    }

    public TextField getNbrObj() {
        return nbrObj;
    }
    int metha;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    
  
    }    

  @FXML
  private void NextAction(ActionEvent event) throws IOException {
  
try{
     double capacity= Double.parseDouble(this.capacity.getText());
     int nbrObj= Integer.parseInt(this.nbrObj.getText());
     Alert alert = new Alert (AlertType.WARNING); 
        
    if((this.capacity.getText()!=null)&&(this.nbrObj.getText()!=null)|| !this.capacity.getText().matches("\\w") || this.nbrObj.getText().matches("\\d*"))
     {  
       if ((capacity>0)&&(nbrObj<=2))
       {     if (nbrObj<=0)
             {
                  this.next.setVisible(true);
                  alert.setTitle("Error Dialog");
                  alert.setHeaderText(null);
                  alert.setContentText("Number negative or null invalid !");
                  alert.showAndWait();
                  this.nbrObj.clear(); }
       
              if ((nbrObj==2))
             {
                  this.next.setVisible(true);
                  alert.setTitle("Error Dialog");
                  alert.setHeaderText(null);
                  alert.setContentText("Please enter a number more than 2 !");
                  alert.showAndWait();
                  this.nbrObj.clear();
                 
              } 
       }
      
       else  if (capacity<=0)
              {
                 if ((nbrObj<=2))
                {
                  this.next.setVisible(true);
                  alert.setTitle("Error Dialog");
                  alert.setHeaderText(null);
                  alert.setContentText("Invalid entries retype the both fields please!");
                  alert.showAndWait();
                  this.nbrObj.clear();
                  this.capacity.clear();
                 }
                 
                 else //si capacity <=0 et nbr>2 
                 {
                  this.next.setVisible(true);
                  alert.setTitle("Error Dialog");
                  alert.setHeaderText(null);
                  alert.setContentText("Capacity invalid !");
                  alert.showAndWait();
                  this.capacity.clear();
               }
              }
       
       
       
     if ((capacity>0)&&(nbrObj>2))
    {
       javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader();
       loader.setLocation(getClass().getResource("saisiInf.fxml"));
       Parent tableDet = loader.load();
       
       SaisiInfController controller = loader.getController();
       controller.saisirInf(nbrObj,capacity);
        Scene tableDetScene = new Scene(tableDet);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(tableDetScene);
        window.show();
       }
    }
    }catch (NumberFormatException e){
        
      
        Alert alert = new Alert (AlertType.WARNING); 
        
     if(!this.capacity.getText().isEmpty()||!this.nbrObj.getText().isEmpty())
     { 
        if (!this.capacity.getText().matches("\\w") && this.nbrObj.getText().matches("\\d*"))
        { 
        alert.setHeaderText(null);
        alert.setContentText("Capacity should be numeric !! try again please");
        alert.showAndWait();
        this.capacity.clear();alert.setTitle("Error ");
       }
        if (!this.nbrObj.getText().matches("\\d*") && this.capacity.getText().matches("\\w")) 
        {
            alert.setHeaderText(null);
            alert.setContentText("The number of objects should be numeric !!! try again please");
            alert.showAndWait();
            this.nbrObj.clear();
        }
        if (!this.nbrObj.getText().matches("\\d*") && !this.capacity.getText().matches("\\w") )
        { 
            alert.setHeaderText(null);
            alert.setContentText("capacity and number of objects should be numeric !!! try again please");
            alert.showAndWait();
            this.capacity.clear();
            this.nbrObj.clear();
        } 
     }  
       // else if (this.capacity.getText().isEmpty() || this.nbrObj.getText().isEmpty())
        if(this.capacity.getText().isEmpty() && this.nbrObj.getText().isEmpty() )
       {
        alert.setTitle("Error ");
        alert.setHeaderText(null);
        alert.setContentText("Verify if the fields aren't empty !");
        alert.showAndWait();
        }  
    } 
    }

 void  manulGS (int metha){
    this.metha=metha;
}


}
    
    
    
    
    
