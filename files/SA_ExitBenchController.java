package malha_taous;


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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.swing.JFileChooser;

/**
 * FXML Controller class
 *
 * @author MMC
 */
public class SA_ExitBenchController implements Initializable {

    @FXML
    private TableView<SolutionRecuit> SA_Table;
    @FXML
    private TableColumn<SolutionRecuit, Integer>counter;
    @FXML
    private TableColumn<SolutionRecuit, String> solution;
    @FXML
    private TableColumn<SolutionRecuit, Double> fitness;
    @FXML
    private TableColumn<SolutionRecuit, Double>totalWeight;
    @FXML
    private TextField temp_max;
    @FXML
    private TextField temp_min;
    @FXML
    private TextField alpha;
    @FXML
    private Label initial_temp;
    @FXML
    private Label cool_rate;
    @FXML
    private Label final_temp;
    @FXML
    private Label label_SA;
    @FXML
    private Button Run;
    @FXML
    private AnchorPane SA_pane;
    @FXML
    private Button initialiseSA;
    @FXML
    private Button retourSA;
    @FXML
    private Label iteration;
    @FXML
    private TextField nbr_ite;


    public TableView<SolutionRecuit> getSA_Table() {
        return SA_Table;
    }

    public TableColumn<SolutionRecuit, Integer> getCounter() {
        return counter;
    }

    public TableColumn<SolutionRecuit, String> getSolution() {
        return solution;
    }

    public TableColumn<SolutionRecuit, Double> getFitness() {
        return fitness;
    }

    public TableColumn<SolutionRecuit, Double> getTotalWeight() {
        return totalWeight;
    }

    public TextField getInitial_Temperature() {
        return temp_max;
    }

    public TextField getFinal_Temperature() {
        return temp_min;
    }

    public ArrayList<saisiInf> getListeObjets() {
        return listeObjets;
    }

    public ArrayList<SolutionRecuit> getListe_verif() {
        return liste_verif;
    }

    public ArrayList<SolutionRecuit> getListe_sol() {
        return liste_sol;
    }

    public TextField getCool_Rate() {
        return alpha;
    }
    public TextField getNbr_ite() {
        return nbr_ite;
    }

    ArrayList<saisiInf> listeObjets =new ArrayList<saisiInf>();
    double capacity;
    int nbrObj;
    static Long timeExec;
    String ligne;
    ArrayList<SolutionRecuit> liste_verif=new ArrayList<>();
    ArrayList<SolutionRecuit> liste_sol=new ArrayList<>();
    static  int count=0;
    static int iter;

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        this.counter.setCellValueFactory(new PropertyValueFactory<SolutionRecuit,Integer>("counter"));
        this.solution.setCellValueFactory(new PropertyValueFactory<SolutionRecuit,String>("sol"));
        this.fitness.setCellValueFactory(new PropertyValueFactory<SolutionRecuit,Double>("fitness"));
        this.totalWeight.setCellValueFactory(new PropertyValueFactory<SolutionRecuit,Double>("totalWeight"));

        this.SA_Table.setVisible(false);
    }

    @FXML
    private void RunRecuitAction(ActionEvent event) throws FileNotFoundException, IOException {

        try {
            double INITIAL_TEMPERATURE = Double.parseDouble(this.getInitial_Temperature().getText());
            double FINAL_TEMPERATURE= Double.parseDouble(this.getFinal_Temperature().getText());
            double Cool_Rate= Double.parseDouble(this.getCool_Rate().getText());
            int Nb_iterr=Integer.parseInt(this.getNbr_ite().getText());

            if((this.temp_min.getText()!=null)&&(this.temp_max.getText()!=null)&&(this.alpha.getText()!=null)&&(this.nbr_ite.getText()!=null))
            {
                Alert alert = new Alert (Alert.AlertType.WARNING);
                if(INITIAL_TEMPERATURE <=20)
                {
                    alert.setTitle("Error Dialog");
                    alert.setHeaderText(null);
                    alert.setContentText("Failed! Initial Temperature invalid... please try to enter a Temperature more than this one!");
                    alert.showAndWait();
                    this.temp_max.clear();
                }

                if(FINAL_TEMPERATURE>=INITIAL_TEMPERATURE  )
                {
                    alert.setTitle("Error Dialog");
                    alert.setHeaderText(null);
                    alert.setContentText("Error! Final Temperature invalid ... please try to enter a temperature less than the initial one !");
                    alert.showAndWait();
                    this.temp_min.clear();
                }

                if(Nb_iterr<=5)
                {
                    alert.setTitle("Error Dialog");
                    alert.setHeaderText(null);
                    alert.setContentText("Error!Nbr_iteration must be > 5!");
                    alert.showAndWait();
                    this.nbr_ite.clear();
                }

                if((Cool_Rate<=0)||(Cool_Rate>=1))
                {
                    alert.setTitle("Error Dialog");
                    alert.setHeaderText(null);
                    alert.setContentText("Failed! Enter a Cool_Rate between 0 and 1 !");
                    alert.showAndWait();
                    this.alpha.clear();
                }
                if (INITIAL_TEMPERATURE>=20 && FINAL_TEMPERATURE<INITIAL_TEMPERATURE  && Cool_Rate>0 && Cool_Rate<1 && Nb_iterr>5)
                {     this.listeObjets.clear();
                    JFileChooser dialogue = new JFileChooser(new File("."));
                    File fichier;
                    dialogue.showOpenDialog(null);
                    fichier = dialogue.getSelectedFile();
                    String filepath=fichier.getAbsolutePath();

                    Scanner in =new Scanner(fichier);
                    System.out.println("lire le fichier!");

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
                    }

                    this.SA_Table.setVisible(true);

                    /*********le programme principal de SA*///
                    Long begin =  System.currentTimeMillis();
                    double temperature = INITIAL_TEMPERATURE;
                    SolutionRecuit  current;
                    this.SA_Table.getItems().clear();
                    //utiliser une solution initiale nulle

                  /*if(this.listeObjets.size()+1<200)   {
                        current = new SolutionRecuit<>(nbrObj,capacity,listeObjets, null);
                    }*/
                    // utiliser une solution initiale aléatoire
                    //else {
                        current=new SolutionRecuit();
                        current.numberOfItems =nbrObj;
                        //current.Id=0;
                        current.solution = new int[nbrObj];
                        current.itemList=listeObjets;
                        current.cap=capacity;

                        System.out.println("generer la solution initiale: ");
                        //System.out.println("capacity :"+capacity);
                        Random r = new Random();
                        int randItem = r.nextInt(listeObjets.size());
                        for(int i=0;i<listeObjets.size();i++)
                        {
                            int p=0;
                            int nb=(int)(Math.random() *2);
                            p+=listeObjets.get(i).getPoids();
                            if (p<=capacity)
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

                  // } //fin de else si*/
                    ArrayList<Integer> solutionKey = current.getTakenItems();
                    SolutionRecuit  Plus_Optimal=new SolutionRecuit();
                    Plus_Optimal = current;

                    System.out.println("bestFitness :"+Plus_Optimal.fitness);

                    int Nbr_echec=0;
                    int max_echec=1050;
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
                                System.out.println(accept_sol);
                                if (accept_sol)
                                {
                                    current = neighbor;
                                }


                                liste_sol.add(current);
                                liste_verif.add(Plus_Optimal);

                            }catch(Exception e){
                                System.out.println("err"); }
                            if (Plus_Optimal.fitness< current.fitness)
                            {
                                System.out.println("soluuuuution : ");
                                Plus_Optimal=current;
                                Plus_Optimal.affich(listeObjets);
                                Plus_Optimal.fitness = current.fitness;
                                //   System.out.println("bestFitness : "+Plus_Optimal.fitness);
                                solutionKey = current.getTakenItems();
                                iter=palier;
                                System.out.println("iter:"+iter);

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
                        this.SA_Table.getItems().addAll(this.liste_sol);
                        System.out.println("Resultat");
                        System.out.println(current.fitness + ": " + current.getTakenItems());
                        System.out.println( Plus_Optimal.fitness + ": " + solutionKey);
                    }
                    System.out.println("iter:"+iter);
                    System.out.println("palier:"+palier);

//Get time at the end Time execution
                    Long end =  System.currentTimeMillis();
                    // Calculate the execution time
                    timeExec = end-begin;
                    // Display the execution time
                    System.out.println("Execution in " + timeExec + " ms");


                    FileWriter fw = null;
                    BufferedWriter bw = null;
                    PrintWriter pw = null;
                    String save_result="Result/SA_Result"+".txt";
                    fw = new FileWriter(save_result, true); //passing mode append = true
                    bw = new BufferedWriter(fw);
                    pw = new PrintWriter(bw);
                    double fitness=Plus_Optimal.fitness;
                    System.out.println("best.fitness :"+fitness);
                    ligne=nbrObj+","+fitness+","+timeExec+","+iter;
                    pw.println(ligne);
                    System.out.println("Data Successfully appended into file");
                    pw.flush();
                    try  {
                        pw.close(); bw.close(); fw.close();
                    } catch (IOException io) { System.out.println("errror");  }

                    //Interface of best solution
                    javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader();
                    loader.setLocation(getClass().getResource("bestSol.fxml"));
                    Parent tableDet = loader.load();

                    BestSolController control =loader.getController();
                    control.Affich_Sol(capacity, listeObjets, Plus_Optimal);

                    Scene tableDetScene = new Scene(tableDet);
                    Stage window = new Stage();
                    window.setTitle("Best Solution of simulated annealing");
                    window.setScene(tableDetScene);
                    window.show();


                    this.liste_verif.clear();
                    count=0;
                    this.liste_sol.clear();
                }
            }}catch(NumberFormatException m)  {
            Alert alert = new Alert (Alert.AlertType.WARNING);
            alert.setTitle("Error ");
            alert.setHeaderText(null);
            alert.setContentText("Verify if the fields aren't empty or incompatible type!");
            alert.showAndWait();
        }}

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
    private void initialiseActSA(ActionEvent event) {
        this.alpha.clear();
        this.temp_max.clear();
        this.temp_min.clear();
        this.nbr_ite.clear();
        this.listeObjets.clear();
        this.liste_verif.clear();
        count=0;
        this.liste_sol.clear();
        this.SA_Table.getItems().clear();
        this.SA_Table.setVisible(false);



    }

    @FXML
    private void retourSAact(ActionEvent event) throws IOException {
        count=0;
        javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader();
        loader.setLocation(getClass().getResource("metaheuritiques.fxml"));
        MetaheuritiquesController cont = loader.getController();

        Parent tableDet = loader.load();
        Scene tableDetScene = new Scene(tableDet);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(tableDetScene);
        window.show();


    }










}