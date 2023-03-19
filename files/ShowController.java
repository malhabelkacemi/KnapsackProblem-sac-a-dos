
package malha_taous;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


public class ShowController implements Initializable {
 
    ArrayList<saisiInf> listeObjets = new ArrayList<>();
    ArrayList<Sequence> liste_seq = new ArrayList<>();

    @FXML
    private Button retour;
    @FXML
    private TableView<saisiInf> table_objets;
    @FXML
    private TableColumn<saisiInf, String> name;
    @FXML
    private TableColumn<saisiInf, Long> value;
    @FXML
    private TableColumn<saisiInf, Long> weight;
 
 int nbrObj;
 double capacity;
   
public TableView<saisiInf> getTable() {
return table_objets;
}
public void setTable(TableView<saisiInf> table_objets) {
this.table_objets= table_objets;
}


public TableColumn<saisiInf, String> getNom() {
        return name;
    }

    public  TableColumn<saisiInf, Long> getWeight() {
        return weight;
    }

    public  TableColumn<saisiInf, Long> getValeur() {
        return value;
    }

 
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.name.setCellValueFactory(new PropertyValueFactory<saisiInf,String>("name"));
        this.value.setCellValueFactory(new PropertyValueFactory<saisiInf, Long>("valeur"));
        this.weight.setCellValueFactory(new PropertyValueFactory<saisiInf, Long>("poids"));
      
    }    
 
    @FXML
     private void ReturnAction(ActionEvent event) throws IOException {
       javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader();
       loader.setLocation(getClass().getResource("saisiInf.fxml"));
       Parent tableDet = loader.load();
       SaisiInfController controller = loader.getController();
       controller.stage(listeObjets,nbrObj,capacity,(Stage)((Node)event.getSource()).getScene().getWindow());
       Scene tableDetScene = new Scene(tableDet);
       Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
       window.setScene(tableDetScene);
       window.show();  
    }
   
   void saisir_dans_table(ArrayList<saisiInf> listebj,int nbrObj,double capacity)
   {
        this.table_objets.getItems().clear();
        this.listeObjets=listebj;
        this.table_objets.getItems().addAll(this.listeObjets);
       this.nbrObj=nbrObj;
       this.capacity=capacity;
     
    }
   

 }