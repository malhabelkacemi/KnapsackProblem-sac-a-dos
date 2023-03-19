package malha_taous;

import java.io.File;
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
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class SaisiInfController implements Initializable {
   
 static  ArrayList<saisiInf> listeObjets = new ArrayList<> ();
    
         @FXML
    private Button suivant;
    @FXML
     private Button add;
     @FXML
     private Button show;
    @FXML
     public TextField name;
    @FXML
    public TextField value;
    @FXML
    public  TextField weight;

    double capacity;
    int  nbrObj,n;
    static int i=0;
  ArrayList<Sequence> liste_seq=new ArrayList<>();
  ArrayList<Integer> Seq_Solution=new ArrayList<>(); 

   private int test;
   private Stage stage;
    private Stage stageActu;

   
   
     public ArrayList<saisiInf> getlisteObjets()
     {
        return listeObjets;
    }
 public ArrayList<Sequence> getliste_seq()
     {
        return liste_seq;
    }

    public TextField getName() {
        return name;
    }

    public TextField getWeight() {
        return weight;
    }

    public TextField getValeur() {
        return value;
    }
    //la fenetre des metaheuristiques apparait quand vous cliquez sur suivant
   @FXML
    private void suiv_fenetre(ActionEvent event) throws IOException {
     javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader();
      loader.setLocation(getClass().getResource("essai.fxml"));
      
        EssaiController cont = loader.getController();
        
        Parent tableDet = loader.load();
        Scene tableDetScene = new Scene(tableDet);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(tableDetScene);
        window.show();
        
}
//ajouter un param capacity
void saisirInf(int nbrObj,double capacity)  {
  this.suivant.setVisible(false);
  this.add.setVisible(true);
  this.nbrObj=nbrObj;
  this.capacity=capacity;

  
   if (this.nbrObj==1){
           this.suivant.setVisible(true);
            this.add.setVisible(false);
            this.listeObjets.add(saisiInf.saisir_objet(this));
            this.capacity=capacity;//ajouter
       }
   else
   {   this.suivant.setVisible(false);
       this.add.setVisible(true);
       this.capacity=capacity;//ajouter
   }  
}
    @FXML
    private void add_objet(ActionEvent event) throws IOException {
        try{
            double poids= Double.parseDouble(this.weight.getText());
            int valeur= Integer.parseInt(this.value.getText());
            String nom=this.name.getText();
            Alert alert = new Alert (Alert.AlertType.WARNING);
            int j=0;

            if(((this.name.getText()!=null)&&(this.weight.getText()!=null)&&(this.getValeur().getText()!=null)|| this.value.getText().matches("\\d*")|| !this.weight.getText().matches("\\w*")))
            {

                if (i<nbrObj-1)
                {
                    i++;
                    this.listeObjets.add(saisiInf.saisir_objet(this));
                    this.suivant.setVisible(false);//cacher le suivant
                    this.name.clear();
                    this.value.clear();
                    this.weight.clear();

                }

                else {
                    this.listeObjets.add(saisiInf.saisir_objet(this));
                    this.suivant.setVisible(true);
                    this.add.setVisible(true);
                    j=3;
                    this.name.clear();
                    this.value.clear();
                    this.weight.clear();
                }
            }

            if(j==3)
            {
                System.out.println("fichier crée avec succés");
                File dossier= new File("Benchmarks");
                int nbr,cpt =0;
                if(dossier.isDirectory())
                {
                    for(File fichier:dossier.listFiles())
                    {
                        if(fichier.getName().matches("Manu"+".*"))
                        {
                            cpt++;
                        }
                    }
                }

                nbr=cpt+1;
                System.out.println("cpt :"+nbr);

                String nom_fichier="Manuel"+nbr+"_"+nbrObj+"_"+capacity+".txt";//nom associé au fichier
                System.out.println("nom_fich="+nom_fichier);
                System.out.println(nbrObj);
                System.out.println("cap :"+capacity);

                Path path=Paths.get("Benchmarks",nom_fichier);
                String newline = System.getProperty("line.separator");
                DecimalFormat df=new DecimalFormat("0.00");

                //Création du fichier
                String ligne=" ";
                Files.write(path, ligne.getBytes(),StandardOpenOption.CREATE_NEW,StandardOpenOption.WRITE);

                String nb_objets="nombre d'objets:"+nbrObj;
                Files.write(path, nb_objets.getBytes(),StandardOpenOption.APPEND);
                Files.write(path, newline.getBytes(),StandardOpenOption.APPEND);

                String cap="capacity:"+capacity;
                Files.write(path, cap.getBytes(),StandardOpenOption.APPEND);
                Files.write(path, newline.getBytes(),StandardOpenOption.APPEND);


                for (int i=0;i<nbrObj;i++)
                {
                    String name=listeObjets.get(i).name;
                    int value= listeObjets.get(i).valeur;
                    double weight=listeObjets.get(i).poids;
                    ligne=name+","+value+","+df.format(weight);
                    Files.write(path, ligne.getBytes(),StandardOpenOption.APPEND);
                    Files.write(path, newline.getBytes(),StandardOpenOption.APPEND);
                }

            }



        }catch (NumberFormatException e)
        {
            Alert alert = new Alert (Alert.AlertType.WARNING);
            if (!this.weight.getText().matches("\\w") && !this.value.getText().matches("\\d"))
            {
                alert.setHeaderText(null);
                alert.setContentText("Both of weight and value should be numeric!try again please");
                alert.showAndWait();
                alert.setTitle("Warning ");
                this.weight.clear();
                this.value.clear();
                this.name.clear();
            }

            if(!this.name.getText().isEmpty()||!this.weight.getText().isEmpty() ||  !this.getValeur().getText().isEmpty())
            {
                if (!this.value.getText().matches("\\d") && this.weight.getText().matches("\\w") )
                {
                    alert.setHeaderText(null);
                    alert.setContentText("The value of objects should be numeric (integer)!!! try again please");
                    alert.showAndWait();
                    alert.setTitle("Warning ");
                    this.value.clear();

                }
                if (!this.weight.getText().matches("\\w") && this.value.getText().matches("\\d*"))// weight error
                {
                    alert.setHeaderText(null);
                    alert.setContentText("please try to enter a numeric number for the weight field (integer or real)!!");
                    alert.showAndWait();
                    alert.setTitle("Warning ");
                    this.weight.clear();
                }


            }
            if(this.name.getText().isEmpty() && this.weight.getText().isEmpty() && this.getValeur().getText().isEmpty())
            {
                alert.setTitle("Warning ");
                alert.setHeaderText(null);
                alert.setContentText("Enter the information of the object please!");
                alert.showAndWait();
            }
        }
    }

    @FXML
    private void show_objets(ActionEvent event) throws IOException
    {
        javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader();
        loader.setLocation(getClass().getResource("show.fxml"));
        Parent tableDet = loader.load();
        ShowController controller = loader.getController();
        controller.saisir_dans_table(this.listeObjets,this.nbrObj,this.capacity);
        Scene tableDetScene = new Scene(tableDet);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(tableDetScene);
        window.show();     
    }

    void stage(ArrayList<saisiInf> liste_obj,int nbrObj,double capacity,Stage stage) {
        this.test = 1;
        this.listeObjets =liste_obj;
        this.nbrObj=nbrObj;
        this.capacity=capacity;
        this.stage = stage;

    }
   
 
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      this.suivant.setVisible(false);  
         
    }  
   
 
}//fin