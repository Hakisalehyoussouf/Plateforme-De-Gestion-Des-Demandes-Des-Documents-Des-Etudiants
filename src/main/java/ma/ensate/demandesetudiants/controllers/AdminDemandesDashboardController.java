package ma.ensate.demandesetudiants.controllers;

import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import ma.ensate.demandesetudiants.StageInitializer;
import ma.ensate.demandesetudiants.components.StageDasboard;
import ma.ensate.demandesetudiants.entities.Demandes;
import ma.ensate.demandesetudiants.entities.Historiques;
import ma.ensate.demandesetudiants.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.*;


@Controller
public class AdminDemandesDashboardController implements Initializable {

    private Map<String, String> viewMap = new HashMap<>();


    /**
     * Les variables d'etats!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     */
    private boolean btnDemHis_etat=false;
    private boolean btnDemAtt_etat=false;

    private boolean btnRejAttRes=false;
    private boolean btnRejAttSco=false;
    private boolean btnRejRelNotes=false;
    private boolean btnRejConvStage=false;

    private boolean btnValAttRes=false;
    private boolean btnValAttSco=false;
    private boolean btnValRelNotes=false;
    private boolean btnValConvStage=false;

    private boolean btnRenAttRes=false;
    private boolean btnRenAttSco=false;
    private boolean btnRenRelNotes=false;
    private boolean btnRenConvStage=false;


    private boolean btnTelAttRes=false;
    private boolean btnTelAttSco=false;
    private boolean btnTelRelNotes=false;
    private boolean btnTelConvStage=false;


    private boolean btnAttRes_etat=false;
    private boolean btnAttSco_etat=false;
    private boolean btnRelNotes_etat=false;
    private boolean btnConvStage_etat=false;

    /**
     * L'injection des dependances!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     */
    @Autowired
    private FacadeEtudiant facadeEtudiant;
    @Autowired
    private FacadeDemande facadeDemande;

    @Autowired
    @Qualifier("attestationDeReussitePDFGenerator")
    private DemandePDFGenerator attestationDeReussitePDFGenerator;

    @Autowired
    @Qualifier("releveeDesNotesPDFGenerator")
    private DemandePDFGenerator releveeDesNotesPDFGenerator;


    @Autowired
    @Qualifier("conventionDeStagePDFGenerator")
    private DemandePDFGenerator conventionDeStagePDFGenerator;


    @Autowired
    private Invoqueur invoqueur ;
    @Autowired
    private LogoutReceiver logoutReceiver;
    @Autowired
    @Qualifier("commandLogout")
    private Command commandLogout ;
    @Autowired
    private StageInitializer stageInitializer;




    @FXML
    private AnchorPane Constant_form;

    @FXML
    private AnchorPane Demandes_form;

    @FXML
    private AnchorPane Home_form;

    @FXML
    private Button btnDashboard;

    @FXML
    private Button btnDemEnAtt;

    @FXML
    private Button btnHistorique;

    @FXML
    private Button btnLogout;

    @FXML
    private Label demandes_number;

    @FXML
    private Label historiques_number;

    @FXML
    private BarChart<?, ?> home_chart;

    @FXML
    private Label inge_number;

    @FXML
    private Label prepa_number;


    @FXML
    private Button btnReject;

    @FXML
    private Button btnValidate;

    @FXML
    private TableView<Demandes> demandes_TableView;

    @FXML
    private TableColumn<Demandes, Long> demandes_TableView_App_col;

    @FXML
    private TableColumn<Demandes,String > demandes_TableView_Doc_col;

    @FXML
    private TableColumn<Demandes, String> demandes_TableView_Fil_col;

    @FXML
    private TableColumn<Demandes, String> demandes_TableView_NivDoc_col;

    @FXML
    private TableColumn<Demandes, String> demandes_TableView_Nom_col;

    @FXML
    private TableColumn<Demandes, String> demandes_TableView_Pre_col;

    @FXML
    private TextField searchFielDemAtt;

    @FXML
    private AnchorPane parentAnchorPane;

    @FXML
    private StackPane parentStackPane;





    @FXML
    private AnchorPane Historique_form;

    @FXML
    private Button btnDownload;

    @FXML
    private Button btnRenvoyer;

    @FXML
    private TableView<Historiques> historiques_TableView;

    @FXML
    private TableColumn<Historiques, Long> historiques_TableView_App_col;

    @FXML
    private TableColumn<Historiques, String> historiques_TableView_Doc_col;

    @FXML
    private TableColumn<Historiques, String> historiques_TableView_Fil_col;

    @FXML
    private TableColumn<Historiques, String> historiques_TableView_NivDoc_col;

    @FXML
    private TableColumn<Historiques, String> historiques_TableView_Nom_col;

    @FXML
    private TableColumn<Historiques, String> historiques_TableView_Pre_col;

    @FXML
    private TableColumn<Historiques, String> historiques_TableView_Status_col;


    @FXML
    private TextField searchFielHisAtt;









    @FXML
    private AnchorPane Demandes_choix_form;

    @FXML
    private Button btnAttReus;

    @FXML
    private Button btnAttScol;

    @FXML
    private Button btnConvStage;

    @FXML
    private Button btnRelNotes;

    @FXML
    private AnchorPane title_demandes;

    @FXML
    private AnchorPane title_historiques;




    /**
     ********************************************************Le constructeur********************************************
     */

    public AdminDemandesDashboardController() {
        viewMap.put("choiceType", "/Components/choiceType.fxml");
        viewMap.put("dashboard", "/Components/dashboard.fxml");
        viewMap.put("demandesArray", "/Components/demandesArray.fxml");
        viewMap.put("historiquesArray", "/Components/historiquesArray.fxml");
    }

    /**
     * La methode d'initialisation de la page d'acceuil***********************************************************************************
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        homeCP();
        homeCI();
        homeDemEnAtt();
        homeDemEnHis();
        homeChart();
    }


    /**
     ********************************************************Les methodes de gestion des actions********************************************
     */






    @FXML
    void handleTypeAction(MouseEvent event) {

        if(event.getSource()==btnAttReus){

            if(btnDemAtt_etat==true){
                loadView("demandesArray");
                ListeDemandesdAttReusAffichage();

            }else if(btnDemHis_etat==true){
                loadView("historiquesArray");
                ListeDemandesHistoriquesdAttReusAffichage();
            }

            btnRejAttRes=true;
            btnRejAttSco=false;
            btnRejRelNotes=false;
            btnRejConvStage=false;


            btnValAttRes=true;
            btnValAttSco=false;
            btnValRelNotes=false;
            btnValConvStage=false;

            btnRenAttRes=true;
            btnRenAttSco=false;
            btnRenRelNotes=false;
            btnRenConvStage=false;

            btnTelAttRes=true;
            btnTelAttSco=false;
            btnTelRelNotes=false;
            btnTelConvStage=false;


            btnAttRes_etat=true;
            btnAttSco_etat=false;
            btnRelNotes_etat=false;
            btnConvStage_etat=false;



           // DemandesSearch();

        }else if(event.getSource()==btnAttScol){

            if(btnDemAtt_etat==true){
                loadView("demandesArray");
                ListeDemandesAttScolAffichage();
            }else if(btnDemHis_etat==true){
                loadView("historiquesArray");
                ListeDemandesHistoriquesAttScolAffichage();
            }

            btnRejAttRes=false;
            btnRejAttSco=true;
            btnRejRelNotes=false;
            btnRejConvStage=false;

            btnValAttRes=false;
            btnValAttSco=true;
            btnValRelNotes=false;
            btnValConvStage=false;


            btnRenAttRes=false;
            btnRenAttSco=true;
            btnRenRelNotes=false;
            btnRenConvStage=false;

            btnTelAttRes=false;
            btnTelAttSco=true;
            btnTelRelNotes=false;
            btnTelConvStage=false;

            btnAttRes_etat=false;
            btnAttSco_etat=true;
            btnRelNotes_etat=false;
            btnConvStage_etat=false;




           // DemandesSearch();

        }else if(event.getSource()==btnConvStage){

            if(btnDemAtt_etat==true){
                loadView("demandesArray");
                ListeDemandesConvStageAffichage();
            }else if(btnDemHis_etat==true){
                loadView("historiquesArray");
                ListeDemandesHistoriquesConvStageAffichage();
            }

            btnRejAttRes=false;
            btnRejAttSco=false;
            btnRejRelNotes=false;
            btnRejConvStage=true;


            btnValAttRes=false;
            btnValAttSco=false;
            btnValRelNotes=false;
            btnValConvStage=true;


            btnRenAttRes=false;
            btnRenAttSco=false;
            btnRenRelNotes=false;
            btnRenConvStage=true;

            btnTelAttRes=false;
            btnTelAttSco=false;
            btnTelRelNotes=false;
            btnTelConvStage=true;

            btnAttRes_etat=false;
            btnAttSco_etat=false;
            btnRelNotes_etat=false;
            btnConvStage_etat=true;


          //  DemandesSearch();



        }else if(event.getSource()==btnRelNotes){

            if(btnDemAtt_etat==true){
                loadView("demandesArray");
                ListeDemandesRelNotesAffichage();
            }else if(btnDemHis_etat==true){
                loadView("historiquesArray");
                ListeDemandesHistoriquesRelNotesAffichage();
            }

            btnRejAttRes=false;
            btnRejAttSco=false;
            btnRejRelNotes=true;
            btnRejConvStage=false;

            btnValAttRes=false;
            btnValAttSco=false;
            btnValRelNotes=true;
            btnValConvStage=false;

            btnRenAttRes=false;
            btnRenAttSco=false;
            btnRenRelNotes=true;
            btnRenConvStage=false;

            btnTelAttRes=false;
            btnTelAttSco=false;
            btnTelRelNotes=true;
            btnTelConvStage=false;

            btnAttRes_etat=false;
            btnAttSco_etat=false;
            btnRelNotes_etat=true;
            btnConvStage_etat=false;


           // DemandesSearch();


        }


    }

    @FXML
    void handleDemandesAction(MouseEvent event) {

        if(event.getSource()==btnDemEnAtt){

            loadView("choiceType");

            title_demandes.setVisible(true);
            title_historiques.setVisible(false);

            btnDemAtt_etat=true;
            btnDemHis_etat=false;

        }
    }


    @FXML
    void handleHistoriquesAction(MouseEvent event) {

        if(event.getSource()==btnHistorique){

            loadView("choiceType");

            title_demandes.setVisible(false);
            title_historiques.setVisible(true);


            btnDemAtt_etat=false;
            btnDemHis_etat=true;

        }

    }

    @FXML
    void handleHomeAction(MouseEvent event) {

        if(event.getSource()==btnDashboard){
            loadView("dashboard");
        }

    }

    @FXML
    void handleLogoutAction(MouseEvent event) {

        if(event.getSource()==btnLogout){


            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Message de Confirmation!");
            alert.setHeaderText(null);
            alert.setContentText("Voulez-Vous se deconnecter?");
            Optional<ButtonType> option = alert.showAndWait();

            if (option.get().equals(ButtonType.OK)) {

                if (logoutReceiver != null) {


                    invoqueur.addNewCommand("commandLogout",this.commandLogout);
                    invoqueur.invoquer("commandLogout");


                    Parent newRoot = stageInitializer.getParent();
                    Node node = (Node)event.getSource();
                    Scene currentScene = (Scene)node.getScene();
                    currentScene.setRoot(newRoot);


                } else {
                    System.err.println("Erreur : logoutReceiver est null");
                    System.err.println("Erreur : logoutReceiver est null");
                    System.err.println("Erreur : logoutReceiver est null");
                    System.err.println("Erreur : logoutReceiver est null");
                    System.err.println("Erreur : logoutReceiver est null");
                }

            }


        }



    }




    /**
     * ******************************************** Fin des fonction de gestion de gestion des actions*************************************************************
     */







    /**
     * ******************************************** Debut des fonction de gestion de dashboard*************************************************************
     */
   private void homeCP(){
        if (facadeEtudiant!=null) {

            int countCP1 = facadeEtudiant.getNumberOfStudentsByLevel("2ap1");
            int countCP2 = facadeEtudiant.getNumberOfStudentsByLevel("2ap2");
            int countCP = countCP1 + countCP2;
            prepa_number.setText(String.valueOf(countCP)+" Etudiants");

        }else {
           System.out.println("Erreur : facadeEtudiant est null");
       }

   }


    void homeCI(){
        if (facadeEtudiant!=null) {

            int countCI1 = facadeEtudiant.getNumberOfStudentsByLevel("ci1");
            int countCI2 = facadeEtudiant.getNumberOfStudentsByLevel("ci2");
            int countCI3 = facadeEtudiant.getNumberOfStudentsByLevel("ci3");
            int countCI = countCI1 + countCI2 + countCI3;
            inge_number.setText(String.valueOf(countCI)+" Etudiants");

        }else {
            System.out.println("Erreur : facadeEtudiant est null");
        }

    }


    void homeDemEnAtt(){
       int countDemAtt = facadeDemande.getNumberOfDemands();
       demandes_number.setText(String.valueOf(countDemAtt));
    }

    void homeDemEnHis(){
            int countDemEnHis = facadeDemande.getNumberOfHistoricalDemands();
            historiques_number.setText(String.valueOf(countDemEnHis));
    }


    void homeChart(){

        home_chart.getData().clear();


        List<Object[]> distinctdemands = facadeDemande.getDistinctDemands();
        XYChart.Series chart = new XYChart.Series();



        for (Object[] row : distinctdemands){
            chart.getData().add(new XYChart.Data((String) row[0], (Long) row[1]));
        }
        home_chart.getData().add(chart);

    }


    /**
     * ******************************************** Fin des fonction de gestion de dashboard*************************************************************
     */







    /**
     ************************************************* Les foctions utiles à la logique*************************************************************
     *
     */
    private void loadView(String viewName) {

        try {
            // Charge la vue sélectionnée depuis le fichier FXML
            String fxmlPath = viewMap.get(viewName);

            FXMLLoader loader = new FXMLLoader(new ClassPathResource(fxmlPath).getURL());
            loader.setController(this);
            Parent view = loader.load();


            double x = parentAnchorPane.getChildren().get(1).getLayoutX();
            double y = parentAnchorPane.getChildren().get(1).getLayoutY();

            parentAnchorPane.getChildren().set(1, view);

            parentAnchorPane.getChildren().get(1).setLayoutX(x);
            parentAnchorPane.getChildren().get(1).setLayoutY(y);



        } catch (IOException e) {
            e.printStackTrace();
        }
    }







    public ObservableList<Demandes> ListeDemandesdAttReusDisponibles() {

        if(btnDemAtt_etat==true){
            ObservableList<Demandes> listeDemande = FXCollections.observableArrayList(facadeDemande.getDemandsByDocument("Attestation de reussite"));
            return listeDemande;
        }
        return null;
    }

    public ObservableList<Historiques> ListeDemandesHistoriquesdAttReusDisponibles() {

        if(btnDemHis_etat==true){
            ObservableList<Historiques> listeDemande  = FXCollections.observableArrayList(facadeDemande.getHistoricalDemandsByDocument("Attestation de reussite"));
            return listeDemande;
        }

        return null;
    }

    private ObservableList<Demandes> V_ListeDemandesdAttReusDisponibles;
    private ObservableList<Historiques> V_ListeHistoriquesDemandesdAttReusDisponibles;
    public void ListeDemandesdAttReusAffichage(){

        V_ListeDemandesdAttReusDisponibles = ListeDemandesdAttReusDisponibles();
        if(btnDemAtt_etat==true){
            demandes_TableView_App_col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Demandes, Long>, ObservableValue<Long>>() {
                @Override
                public ObservableValue<Long> call(TableColumn.CellDataFeatures<Demandes, Long> cellData) {
                    return new SimpleObjectProperty<>(cellData.getValue().getEtudiant().getApogee());
                }
            });
            demandes_TableView_Nom_col.setCellValueFactory(new PropertyValueFactory<>("nom"));
            demandes_TableView_Pre_col.setCellValueFactory(new PropertyValueFactory<>("prenom"));
            demandes_TableView_Fil_col.setCellValueFactory(new PropertyValueFactory<>("filiere"));
            demandes_TableView_NivDoc_col.setCellValueFactory(new PropertyValueFactory<>("niveauDocument"));
            demandes_TableView_Doc_col.setCellValueFactory(new PropertyValueFactory<>("document"));

            demandes_TableView.setItems(V_ListeDemandesdAttReusDisponibles);

        }
    }



    public void ListeDemandesHistoriquesdAttReusAffichage(){

        V_ListeHistoriquesDemandesdAttReusDisponibles = ListeDemandesHistoriquesdAttReusDisponibles();

        if(btnDemHis_etat==true){
            historiques_TableView_App_col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Historiques, Long>, ObservableValue<Long>>() {
                @Override
                public ObservableValue<Long> call(TableColumn.CellDataFeatures<Historiques, Long> cellData) {
                    return new SimpleObjectProperty<>(cellData.getValue().getEtudiant().getApogee());
                }
            });
            historiques_TableView_Nom_col.setCellValueFactory(new PropertyValueFactory<>("nom"));
            historiques_TableView_Pre_col.setCellValueFactory(new PropertyValueFactory<>("prenom"));
            historiques_TableView_Fil_col.setCellValueFactory(new PropertyValueFactory<>("filiere"));
            historiques_TableView_NivDoc_col.setCellValueFactory(new PropertyValueFactory<>("niveauDocument"));
            historiques_TableView_Doc_col.setCellValueFactory(new PropertyValueFactory<>("document"));
            historiques_TableView_Status_col.setCellValueFactory(new PropertyValueFactory<>("status"));
            historiques_TableView.setItems(V_ListeHistoriquesDemandesdAttReusDisponibles);
        }
    }






    public ObservableList<Demandes> ListeDemandesRelNotesDisponibles() {
        if(btnDemAtt_etat==true){
            ObservableList<Demandes> listeDemande = FXCollections.observableArrayList(facadeDemande.getDemandsByDocument("Relevée des notes"));
            return listeDemande;
        }
        return null;
    }
    public ObservableList<Historiques> ListeDemandesHistoriquesRelNotesDisponibles() {
        if(btnDemHis_etat==true){
            ObservableList<Historiques> listeDemande = FXCollections.observableArrayList(facadeDemande.getHistoricalDemandsByDocument("Relevée des notes"));
            return listeDemande;
        }
        return null;
    }

    private ObservableList<Demandes> V_ListeDemandesRelNotesDisponibles;
    private ObservableList<Historiques> V_ListeDemandesHistoriquesRelNotesDisponibles;
    public void ListeDemandesRelNotesAffichage(){

        V_ListeDemandesRelNotesDisponibles = ListeDemandesRelNotesDisponibles();
        if(btnDemAtt_etat==true){
            demandes_TableView_App_col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Demandes, Long>, ObservableValue<Long>>() {
                @Override
                public ObservableValue<Long> call(TableColumn.CellDataFeatures<Demandes, Long> cellData) {
                    return new SimpleObjectProperty<>(cellData.getValue().getEtudiant().getApogee());
                }
            });
            demandes_TableView_Nom_col.setCellValueFactory(new PropertyValueFactory<>("nom"));
            demandes_TableView_Pre_col.setCellValueFactory(new PropertyValueFactory<>("prenom"));
            demandes_TableView_Fil_col.setCellValueFactory(new PropertyValueFactory<>("filiere"));
            demandes_TableView_NivDoc_col.setCellValueFactory(new PropertyValueFactory<>("niveauDocument"));
            demandes_TableView_Doc_col.setCellValueFactory(new PropertyValueFactory<>("document"));


            demandes_TableView.setItems(V_ListeDemandesRelNotesDisponibles);
        }
    }
    public void ListeDemandesHistoriquesRelNotesAffichage(){

        V_ListeDemandesHistoriquesRelNotesDisponibles = ListeDemandesHistoriquesRelNotesDisponibles();

        if(btnDemHis_etat==true){
            historiques_TableView_App_col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Historiques, Long>, ObservableValue<Long>>() {
                @Override
                public ObservableValue<Long> call(TableColumn.CellDataFeatures<Historiques, Long> cellData) {
                    return new SimpleObjectProperty<>(cellData.getValue().getEtudiant().getApogee());
                }
            });
            historiques_TableView_Nom_col.setCellValueFactory(new PropertyValueFactory<>("nom"));
            historiques_TableView_Pre_col.setCellValueFactory(new PropertyValueFactory<>("prenom"));
            historiques_TableView_Fil_col.setCellValueFactory(new PropertyValueFactory<>("filiere"));
            historiques_TableView_NivDoc_col.setCellValueFactory(new PropertyValueFactory<>("niveauDocument"));
            historiques_TableView_Doc_col.setCellValueFactory(new PropertyValueFactory<>("document"));
            historiques_TableView_Status_col.setCellValueFactory(new PropertyValueFactory<>("status"));

            historiques_TableView.setItems(V_ListeDemandesHistoriquesRelNotesDisponibles);
        }
    }






    public ObservableList<Demandes> ListeDemandesConvStageDisponibles() {
        if(btnDemAtt_etat==true){
            ObservableList<Demandes> listeDemande = FXCollections.observableArrayList(facadeDemande.getDemandsByDocument("Convention de stage"));
            return listeDemande;
        }
        return null;
    }
    public ObservableList<Historiques> ListeDemandesHistoriquesConvStageDisponibles() {
        if(btnDemHis_etat==true){
            ObservableList<Historiques> listeDemande = FXCollections.observableArrayList(facadeDemande.getHistoricalDemandsByDocument("Convention de stage"));
            return listeDemande;
        }
        return null;
    }


    private ObservableList<Demandes> V_ListeDemandesConvStageDisponibles;
    private ObservableList<Historiques> V_ListeDemandesHistoriquesConvStageDisponibles;
    public void ListeDemandesConvStageAffichage(){

        V_ListeDemandesConvStageDisponibles = ListeDemandesConvStageDisponibles();
        if(btnDemAtt_etat==true){
            demandes_TableView_App_col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Demandes, Long>, ObservableValue<Long>>() {
                @Override
                public ObservableValue<Long> call(TableColumn.CellDataFeatures<Demandes, Long> cellData) {
                    return new SimpleObjectProperty<>(cellData.getValue().getEtudiant().getApogee());
                }
            });
            demandes_TableView_Nom_col.setCellValueFactory(new PropertyValueFactory<>("nom"));
            demandes_TableView_Pre_col.setCellValueFactory(new PropertyValueFactory<>("prenom"));
            demandes_TableView_Fil_col.setCellValueFactory(new PropertyValueFactory<>("filiere"));
            demandes_TableView_NivDoc_col.setCellValueFactory(new PropertyValueFactory<>("niveauDocument"));
            demandes_TableView_Doc_col.setCellValueFactory(new PropertyValueFactory<>("document"));


            demandes_TableView.setItems(V_ListeDemandesConvStageDisponibles);
        }
    }
    public void ListeDemandesHistoriquesConvStageAffichage(){

        V_ListeDemandesHistoriquesConvStageDisponibles = ListeDemandesHistoriquesConvStageDisponibles();

        if(btnDemHis_etat==true){
            historiques_TableView_App_col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Historiques, Long>, ObservableValue<Long>>() {
                @Override
                public ObservableValue<Long> call(TableColumn.CellDataFeatures<Historiques, Long> cellData) {
                    return new SimpleObjectProperty<>(cellData.getValue().getEtudiant().getApogee());
                }
            });
            historiques_TableView_Nom_col.setCellValueFactory(new PropertyValueFactory<>("nom"));
            historiques_TableView_Pre_col.setCellValueFactory(new PropertyValueFactory<>("prenom"));
            historiques_TableView_Fil_col.setCellValueFactory(new PropertyValueFactory<>("filiere"));
            historiques_TableView_NivDoc_col.setCellValueFactory(new PropertyValueFactory<>("niveauDocument"));
            historiques_TableView_Doc_col.setCellValueFactory(new PropertyValueFactory<>("document"));
            historiques_TableView_Status_col.setCellValueFactory(new PropertyValueFactory<>("status"));

            historiques_TableView.setItems(V_ListeDemandesHistoriquesConvStageDisponibles);
        }
    }





    public ObservableList<Demandes> ListeDemandesAttScolDisponibles() {
        if(btnDemAtt_etat==true){
            ObservableList<Demandes> listeDemande = FXCollections.observableArrayList(facadeDemande.getDemandsByDocument("Attestation de scolarité"));
            return listeDemande;
        }
        return null;
    }
    public ObservableList<Historiques> ListeDemandesHistoriquesAttScolDisponibles() {
        if(btnDemHis_etat==true){
            ObservableList<Historiques> listeDemande = FXCollections.observableArrayList(facadeDemande.getHistoricalDemandsByDocument("Attestation de scolarité"));
            return listeDemande;
        }
        return null;
    }

    private ObservableList<Demandes> V_ListeDemandesAttScolDisponibles;
    private ObservableList<Historiques> V_ListeDemandesHistoriquesAttScolDisponibles;
    public void ListeDemandesAttScolAffichage(){

        V_ListeDemandesAttScolDisponibles = ListeDemandesAttScolDisponibles();
        if(btnDemAtt_etat==true){
            demandes_TableView_App_col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Demandes, Long>, ObservableValue<Long>>() {
                @Override
                public ObservableValue<Long> call(TableColumn.CellDataFeatures<Demandes, Long> cellData) {
                    return new SimpleObjectProperty<>(cellData.getValue().getEtudiant().getApogee());
                }
            });
            demandes_TableView_Nom_col.setCellValueFactory(new PropertyValueFactory<>("nom"));
            demandes_TableView_Pre_col.setCellValueFactory(new PropertyValueFactory<>("prenom"));
            demandes_TableView_Fil_col.setCellValueFactory(new PropertyValueFactory<>("filiere"));
            demandes_TableView_NivDoc_col.setCellValueFactory(new PropertyValueFactory<>("niveauDocument"));
            demandes_TableView_Doc_col.setCellValueFactory(new PropertyValueFactory<>("document"));


            demandes_TableView.setItems(V_ListeDemandesAttScolDisponibles);
        }
    }
    public void ListeDemandesHistoriquesAttScolAffichage(){

        V_ListeDemandesHistoriquesAttScolDisponibles = ListeDemandesHistoriquesAttScolDisponibles();

        if(btnDemHis_etat==true){
            historiques_TableView_App_col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Historiques, Long>, ObservableValue<Long>>() {
                @Override
                public ObservableValue<Long> call(TableColumn.CellDataFeatures<Historiques, Long> cellData) {
                    return new SimpleObjectProperty<>(cellData.getValue().getEtudiant().getApogee());
                }
            });
            historiques_TableView_Nom_col.setCellValueFactory(new PropertyValueFactory<>("nom"));
            historiques_TableView_Pre_col.setCellValueFactory(new PropertyValueFactory<>("prenom"));
            historiques_TableView_Fil_col.setCellValueFactory(new PropertyValueFactory<>("filiere"));
            historiques_TableView_NivDoc_col.setCellValueFactory(new PropertyValueFactory<>("niveauDocument"));
            historiques_TableView_Doc_col.setCellValueFactory(new PropertyValueFactory<>("document"));
            historiques_TableView_Status_col.setCellValueFactory(new PropertyValueFactory<>("status"));

            historiques_TableView.setItems(V_ListeDemandesHistoriquesAttScolDisponibles);
        }
    }



    @FXML
    public void DemandesSearch() {
        if (btnAttRes_etat == true && btnDemHis_etat == true) {
            FilteredList<Historiques> filter = new FilteredList<>(V_ListeHistoriquesDemandesdAttReusDisponibles, e -> true);
            Task<Void> task = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    searchFielHisAtt.textProperty().addListener((Observable, oldValue, newValue) -> {
                        filter.setPredicate(PrediateDemandes -> {
                            if (newValue.isEmpty() || newValue == null) {
                                return true;
                            }
                            String searchKey = newValue.toLowerCase();
                            if (PrediateDemandes.getEtudiant().getApogee().toString().contains(searchKey)) {
                                return true;
                            } else if (PrediateDemandes.getNom().toLowerCase().contains(searchKey)) {
                                return true;
                            } else if (PrediateDemandes.getPrenom().toLowerCase().contains(searchKey)) {
                                return true;
                            } else if (PrediateDemandes.getFiliere().toLowerCase().contains(searchKey)) {
                                return true;
                            } else if (PrediateDemandes.getNiveauDocument().toLowerCase().contains(searchKey)) {
                                return true;
                            } else if (PrediateDemandes.getDocument().toLowerCase().contains(searchKey)) {
                                return true;
                            } else {
                                return false;
                            }
                        });

                        SortedList<Historiques> sortList = new SortedList<>(filter);
                        sortList.comparatorProperty().bind(historiques_TableView.comparatorProperty());
                        historiques_TableView.setItems(sortList);
                    });
                    return null;
                }
            };
            new Thread(task).start();
        } else if (btnConvStage_etat == true && btnDemHis_etat == true) {
            FilteredList<Historiques> filter1 = new FilteredList<>(V_ListeDemandesHistoriquesConvStageDisponibles, e -> true);
            Task<Void> task1 = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    searchFielHisAtt.textProperty().addListener((Observable, oldValue, newValue) -> {
                        filter1.setPredicate(PrediateDemandes -> {
                            if (newValue.isEmpty() || newValue == null) {
                                return true;
                            }
                            String searchKey = newValue.toLowerCase();
                            if (PrediateDemandes.getEtudiant().getApogee().toString().contains(searchKey)) {
                                return true;
                            } else if (PrediateDemandes.getNom().toLowerCase().contains(searchKey)) {
                                return true;
                            } else if (PrediateDemandes.getPrenom().toLowerCase().contains(searchKey)) {
                                return true;
                            } else if (PrediateDemandes.getFiliere().toLowerCase().contains(searchKey)) {
                                return true;
                            } else if (PrediateDemandes.getNiveauDocument().toLowerCase().contains(searchKey)) {
                                return true;
                            } else if (PrediateDemandes.getDocument().toLowerCase().contains(searchKey)) {
                                return true;
                            } else {
                                return false;
                            }
                        });

                        SortedList<Historiques> sortList = new SortedList<>(filter1);
                        sortList.comparatorProperty().bind(historiques_TableView.comparatorProperty());
                        historiques_TableView.setItems(sortList);
                    });
                    return null;
                }
            };
            new Thread(task1).start();
        } else if (btnRelNotes_etat == true && btnDemHis_etat == true) {
            FilteredList<Historiques> filter2 = new FilteredList<>(V_ListeDemandesHistoriquesRelNotesDisponibles, e -> true);
            Task<Void> task2 = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    searchFielHisAtt.textProperty().addListener((Observable, oldValue, newValue) -> {
                        filter2.setPredicate(PrediateDemandes -> {
                            if (newValue.isEmpty() || newValue == null) {
                                return true;
                            }
                            String searchKey = newValue.toLowerCase();
                            if (PrediateDemandes.getEtudiant().getApogee().toString().contains(searchKey)) {
                                return true;
                            } else if (PrediateDemandes.getNom().toLowerCase().contains(searchKey)) {
                                return true;
                            } else if (PrediateDemandes.getPrenom().toLowerCase().contains(searchKey)) {
                                return true;
                            } else if (PrediateDemandes.getFiliere().toLowerCase().contains(searchKey)) {
                                return true;
                            } else if (PrediateDemandes.getNiveauDocument().toLowerCase().contains(searchKey)) {
                                return true;
                            } else if (PrediateDemandes.getDocument().toLowerCase().contains(searchKey)) {
                                return true;
                            } else {
                                return false;
                            }
                        });

                        SortedList<Historiques> sortList = new SortedList<>(filter2);
                        sortList.comparatorProperty().bind(historiques_TableView.comparatorProperty());
                        historiques_TableView.setItems(sortList);
                    });
                    return null;
                }
            };
            new Thread(task2).start();

        }



        else if (btnConvStage_etat == true && btnDemAtt_etat == true) {
            FilteredList<Demandes> filter3 = new FilteredList<>(V_ListeDemandesConvStageDisponibles, e -> true);
            Task<Void> task3 = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    searchFielDemAtt.textProperty().addListener((Observable, oldValue, newValue) -> {
                        filter3.setPredicate(PrediateDemandes -> {
                            if (newValue.isEmpty() || newValue == null) {
                                return true;
                            }
                            String searchKey = newValue.toLowerCase();
                            if (PrediateDemandes.getEtudiant().getApogee().toString().contains(searchKey)) {
                                return true;
                            } else if (PrediateDemandes.getNom().toLowerCase().contains(searchKey)) {
                                return true;
                            } else if (PrediateDemandes.getPrenom().toLowerCase().contains(searchKey)) {
                                return true;
                            } else if (PrediateDemandes.getFiliere().toLowerCase().contains(searchKey)) {
                                return true;
                            } else if (PrediateDemandes.getNiveauDocument().toLowerCase().contains(searchKey)) {
                                return true;
                            } else if (PrediateDemandes.getDocument().toLowerCase().contains(searchKey)) {
                                return true;
                            } else {
                                return false;
                            }
                        });

                        SortedList<Demandes> sortList = new SortedList<>(filter3);
                        sortList.comparatorProperty().bind(demandes_TableView.comparatorProperty());
                        demandes_TableView.setItems(sortList);
                    });
                    return null;
                }
            };
            new Thread(task3).start();
        } else if (btnRelNotes_etat == true && btnDemAtt_etat == true) {
            FilteredList<Demandes> filter5 = new FilteredList<>(V_ListeDemandesRelNotesDisponibles, e -> true);
            Task<Void> task5 = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    searchFielDemAtt.textProperty().addListener((Observable, oldValue, newValue) -> {
                        filter5.setPredicate(PrediateDemandes -> {
                            if (newValue.isEmpty() || newValue == null) {
                                return true;
                            }
                            String searchKey = newValue.toLowerCase();
                            if (PrediateDemandes.getEtudiant().getApogee().toString().contains(searchKey)) {
                                return true;
                            } else if (PrediateDemandes.getNom().toLowerCase().contains(searchKey)) {
                                return true;
                            } else if (PrediateDemandes.getPrenom().toLowerCase().contains(searchKey)) {
                                return true;
                            } else if (PrediateDemandes.getFiliere().toLowerCase().contains(searchKey)) {
                                return true;
                            } else if (PrediateDemandes.getNiveauDocument().toLowerCase().contains(searchKey)) {
                                return true;
                            } else if (PrediateDemandes.getDocument().toLowerCase().contains(searchKey)) {
                                return true;
                            } else {
                                return false;
                            }
                        });

                        SortedList<Demandes> sortList = new SortedList<>(filter5);
                        sortList.comparatorProperty().bind(demandes_TableView.comparatorProperty());
                        demandes_TableView.setItems(sortList);
                    });
                    return null;
                }
            };
            new Thread(task5).start();

        } else if (btnAttRes_etat == true && btnDemAtt_etat == true) {
            FilteredList<Demandes> filter6 = new FilteredList<>(V_ListeDemandesdAttReusDisponibles, e -> true);
            Task<Void> task6 = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    searchFielDemAtt.textProperty().addListener((Observable, oldValue, newValue) -> {
                        filter6.setPredicate(PrediateDemandes -> {
                            if (newValue.isEmpty() || newValue == null) {
                                return true;
                            }
                            String searchKey = newValue.toLowerCase();
                            if (PrediateDemandes.getEtudiant().getApogee().toString().contains(searchKey)) {
                                return true;
                            } else if (PrediateDemandes.getNom().toLowerCase().contains(searchKey)) {
                                return true;
                            } else if (PrediateDemandes.getPrenom().toLowerCase().contains(searchKey)) {
                                return true;
                            } else if (PrediateDemandes.getFiliere().toLowerCase().contains(searchKey)) {
                                return true;
                            } else if (PrediateDemandes.getNiveauDocument().toLowerCase().contains(searchKey)) {
                                return true;
                            } else if (PrediateDemandes.getDocument().toLowerCase().contains(searchKey)) {
                                return true;
                            } else {
                                return false;
                            }
                        });

                        SortedList<Demandes> sortList = new SortedList<>(filter6);
                        sortList.comparatorProperty().bind(demandes_TableView.comparatorProperty());
                        demandes_TableView.setItems(sortList);
                    });
                    return null;
                }
            };
            new Thread(task6).start();
        }


    }











    public Demandes Demendeselectionnee() {

        Demandes demande = demandes_TableView.getSelectionModel().getSelectedItem();
        int num = demandes_TableView.getSelectionModel().getSelectedIndex();
        if ((num - 1) < -1) {
            return null;
        }
        return demande;
    }


    public Historiques DemendeHisselectionnee() {
        Historiques demande = historiques_TableView.getSelectionModel().getSelectedItem();
        int num = historiques_TableView.getSelectionModel().getSelectedIndex();
        if ((num - 1) < -1) {
            return null;
        }
        return demande;
    }


    public void DemandeRejeter(){

        Demandes demande =  Demendeselectionnee();
        facadeDemande.deleteDemandsByStudentAndDocument(demande.getEtudiant(),demande.getDocument());


        Alert alert;
        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Message de Confirmation!");
        alert.setHeaderText(null);
        alert.setContentText("Vouslez-vous vraiment valider cette demande?");
        Optional<ButtonType> option = alert.showAndWait();


        if (option.get().equals(ButtonType.OK)) {


            Historiques historique = new Historiques();

            //historique.setId("H1"); // A generer en utilisant le fonctions de spring boot!??????????????????????????????????????????????????????
            historique.setNom(demande.getNom());
            historique.setPrenom(demande.getPrenom());
            historique.setFiliere(demande.getFiliere());
            historique.setNiveauDocument(demande.getNiveauDocument());
            historique.setDocument(demande.getDocument());
            historique.setEtudiant(demande.getEtudiant());
            historique.setStatus("rejetée");

            facadeDemande.addHistoricalDemand(historique);



            if(btnValAttRes==true){

                facadeDemande.rejectedDemand(demande);
                //Reussite.genererEnvoyer(demande.getAppogee(),demande.getNom()+" "+demande.getPrenom(),demande.getFiliere()); //.genererEnvoyer(demande.getAppogee(),demande.getNom()+" "+demande.getPrenom(),demande.getFiliere());

                if(btnDemAtt_etat==true){
                    ListeDemandesdAttReusAffichage();
                } else if (btnDemHis_etat==true) {
                    ListeDemandesHistoriquesdAttReusAffichage();
                }


            }else if( btnValRelNotes==true){

                facadeDemande.rejectedDemand(demande);
                //Relevee.genererEnvoyer(demande.getAppogee(),demande.getNom()+" "+demande.getPrenom(),demande.getAnnee_Document(),demande.getFiliere());


                if(btnDemAtt_etat==true){
                    ListeDemandesRelNotesAffichage();
                } else if (btnDemHis_etat==true) {
                    ListeDemandesHistoriquesRelNotesAffichage();
                }

            }else if(btnValConvStage==true){

                facadeDemande.rejectedDemand(demande);
                //Convention.genererEnvoyer(demande.getAppogee(),demande.getNom()+" "+demande.getPrenom(),demande.getFiliere());

                if(btnDemAtt_etat==true){
                    ListeDemandesConvStageAffichage();
                } else if (btnDemHis_etat==true) {
                    ListeDemandesHistoriquesConvStageAffichage();
                }

            }

        }
    }





    public void DemandeValider(){


        Demandes demande =  Demendeselectionnee();
        facadeDemande.deleteDemandsByStudentAndDocument(demande.getEtudiant(),demande.getDocument());

        Alert alert;
        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Message de Confirmation!");
        alert.setHeaderText(null);
        alert.setContentText("Vouslez-vous vraiment valider cette demande?");
        Optional<ButtonType> option = alert.showAndWait();


        if (option.get().equals(ButtonType.OK)) {

                Historiques historique = new Historiques();

                //historique.setId("H1"); // A generer en utilisant le fonctions de spring boot!??????????????????????????????????????????????????????
                historique.setNom(demande.getNom());
                historique.setPrenom(demande.getPrenom());
                historique.setFiliere(demande.getFiliere());
                historique.setNiveauDocument(demande.getNiveauDocument());
                historique.setDocument(demande.getDocument());
                historique.setEtudiant(demande.getEtudiant());
                historique.setStatus("validée");

                facadeDemande.addHistoricalDemand(historique);


                if(btnValAttRes==true){

                    attestationDeReussitePDFGenerator.setDemande(demande);
                    attestationDeReussitePDFGenerator.generatePDF();
                    //Reussite.genererEnvoyer(demande.getAppogee(),demande.getNom()+" "+demande.getPrenom(),demande.getFiliere()); //.genererEnvoyer(demande.getAppogee(),demande.getNom()+" "+demande.getPrenom(),demande.getFiliere());

                    if(btnDemAtt_etat==true){
                        ListeDemandesdAttReusAffichage();
                    } else if (btnDemHis_etat==true) {
                        ListeDemandesHistoriquesdAttReusAffichage();
                    }


                }else if( btnValRelNotes==true){

                    releveeDesNotesPDFGenerator.setDemande(demande);
                    releveeDesNotesPDFGenerator.generatePDF();
                    //Relevee.genererEnvoyer(demande.getAppogee(),demande.getNom()+" "+demande.getPrenom(),demande.getAnnee_Document(),demande.getFiliere());


                    if(btnDemAtt_etat==true){
                        ListeDemandesRelNotesAffichage();
                    } else if (btnDemHis_etat==true) {
                        ListeDemandesHistoriquesRelNotesAffichage();
                    }

                }else if(btnValConvStage==true){


                    conventionDeStagePDFGenerator.setDemande(demande);
                    conventionDeStagePDFGenerator.generatePDF();
                    //Convention.genererEnvoyer(demande.getAppogee(),demande.getNom()+" "+demande.getPrenom(),demande.getFiliere());


                    if(btnDemAtt_etat==true){
                        ListeDemandesConvStageAffichage();
                    } else if (btnDemHis_etat==true) {
                        ListeDemandesHistoriquesConvStageAffichage();
                    }

                }
                /*else if(btnValAttSco==true){
                    try{
                        Scolarite.genererEnvoyer(demande.getAppogee(),demande.getNom()+" "+demande.getPrenom(),demande.getFiliere());
                    }catch(IOException ex){
                        System.out.println(ex.getMessage());
                    }
                    ListeDemandesAttScolAffichage();
                }*/
        }
    }






    public void DemandeRenvoyer(){

        Historiques historiques =  DemendeHisselectionnee();


        Alert alert;
        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Message de Confirmation!");
        alert.setHeaderText(null);
        alert.setContentText("Vouslez-vous vraiment Renvoyer cette demande?");
        Optional<ButtonType> option = alert.showAndWait();


        if (option.get().equals(ButtonType.OK)) {

            Demandes demande  = new Demandes();

            //demande.setId("D10"); // A generer en utilisant le fonctions de spring boot!??????????????????????????????????????????????????????
            demande.setNom(historiques.getNom());
            demande.setPrenom(historiques.getPrenom());
            demande.setFiliere(historiques.getFiliere());
            demande.setNiveauDocument(historiques.getNiveauDocument());
            demande.setDocument(historiques.getDocument());
            demande.setEtudiant(historiques.getEtudiant());

            if(btnValAttRes==true){

                attestationDeReussitePDFGenerator.setDemande(demande);
                attestationDeReussitePDFGenerator.generatePDF();
                //Reussite.genererEnvoyer(demande.getAppogee(),demande.getNom()+" "+demande.getPrenom(),demande.getFiliere()); //.genererEnvoyer(demande.getAppogee(),demande.getNom()+" "+demande.getPrenom(),demande.getFiliere());

                if(btnDemAtt_etat==true){
                    ListeDemandesdAttReusAffichage();
                } else if (btnDemHis_etat==true) {
                    ListeDemandesHistoriquesdAttReusAffichage();
                }


            }else if( btnValRelNotes==true){

                releveeDesNotesPDFGenerator.setDemande(demande);
                releveeDesNotesPDFGenerator.generatePDF();
                //Relevee.genererEnvoyer(demande.getAppogee(),demande.getNom()+" "+demande.getPrenom(),demande.getAnnee_Document(),demande.getFiliere());


                if(btnDemAtt_etat==true){
                    ListeDemandesRelNotesAffichage();
                } else if (btnDemHis_etat==true) {
                    ListeDemandesHistoriquesRelNotesAffichage();
                }

            }else if(btnValConvStage==true){


                conventionDeStagePDFGenerator.setDemande(demande);
                conventionDeStagePDFGenerator.generatePDF();
                //Convention.genererEnvoyer(demande.getAppogee(),demande.getNom()+" "+demande.getPrenom(),demande.getFiliere());


                if(btnDemAtt_etat==true){
                    ListeDemandesConvStageAffichage();
                } else if (btnDemHis_etat==true) {
                    ListeDemandesHistoriquesConvStageAffichage();
                }

            }/*else if(btnValAttSco==true){
                    try{
                        Scolarite.genererEnvoyer(demande.getAppogee(),demande.getNom()+" "+demande.getPrenom(),demande.getFiliere());
                    }catch(IOException ex){
                        System.out.println(ex.getMessage());
                    }
                    ListeDemandesAttScolAffichage();
             }*/
        }
    }




    public void DemandeTelecharger(){

        Historiques historiques =  DemendeHisselectionnee();


        Alert alert;
        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Message de Confirmation!");
        alert.setHeaderText(null);
        alert.setContentText("Vouslez-vous vraiment Renvoyer cette demande?");
        Optional<ButtonType> option = alert.showAndWait();


        if (option.get().equals(ButtonType.OK)) {

            Demandes demande  = new Demandes();

           // demande.setId("D10"); // A generer en utilisant le fonctions de spring boot!??????????????????????????????????????????????????????
            demande.setNom(historiques.getNom());
            demande.setPrenom(historiques.getPrenom());
            demande.setFiliere(historiques.getFiliere());
            demande.setNiveauDocument(historiques.getNiveauDocument());
            demande.setDocument(historiques.getDocument());
            demande.setEtudiant(historiques.getEtudiant());

            if(btnValAttRes==true){

                facadeDemande.genererAttestationDeReussite(demande);
                //Reussite.genererEnvoyer(demande.getAppogee(),demande.getNom()+" "+demande.getPrenom(),demande.getFiliere()); //.genererEnvoyer(demande.getAppogee(),demande.getNom()+" "+demande.getPrenom(),demande.getFiliere());

                if(btnDemAtt_etat==true){
                    ListeDemandesdAttReusAffichage();
                } else if (btnDemHis_etat==true) {
                    ListeDemandesHistoriquesdAttReusAffichage();
                }


            }else if( btnValRelNotes==true){

                facadeDemande.genererReleveeDesNotes(demande);
                //Relevee.genererEnvoyer(demande.getAppogee(),demande.getNom()+" "+demande.getPrenom(),demande.getAnnee_Document(),demande.getFiliere());


                if(btnDemAtt_etat==true){
                    ListeDemandesRelNotesAffichage();
                } else if (btnDemHis_etat==true) {
                    ListeDemandesHistoriquesRelNotesAffichage();
                }

            }else if(btnValConvStage==true){


                facadeDemande.genererConventionDeStage(demande);
                //Convention.genererEnvoyer(demande.getAppogee(),demande.getNom()+" "+demande.getPrenom(),demande.getFiliere());


                if(btnDemAtt_etat==true){
                    ListeDemandesConvStageAffichage();
                } else if (btnDemHis_etat==true) {
                    ListeDemandesHistoriquesConvStageAffichage();
                }

            }/*else if(btnValAttSco==true){
                    try{
                        Scolarite.genererEnvoyer(demande.getAppogee(),demande.getNom()+" "+demande.getPrenom(),demande.getFiliere());
                    }catch(IOException ex){
                        System.out.println(ex.getMessage());
                    }
                    ListeDemandesAttScolAffichage();
             }*/
        }
    }



    @FXML
    void handleDecisionButton(MouseEvent event) {

        if(event.getSource()==btnReject){
            DemandeRejeter();
        }else if(event.getSource()==btnValidate){
            DemandeValider();
        }else if(event.getSource()==btnDownload){
            DemandeTelecharger();
        }else if(event.getSource()==btnRenvoyer){
            DemandeRenvoyer();
        }

    }




    /*public void logout(){

        try{

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Message de Confirmation!");
            alert.setHeaderText(null);
            alert.setContentText("Voulez-Vous se deconnecter?");
            Optional<ButtonType> option = alert.showAndWait();

            if (option.get().equals(ButtonType.OK)) {

                // HIDE YOUR DASHBOARD FORM
                btnLogout.getScene().getWindow().hide();

                // LINK YOUR LOGIN FORM
                Parent root = FXMLLoader.load(getClass().getResource("/interfaces/Login.fxml"));
                Scene scene = new Scene(root);
                Stage stage = new Stage();

                root.setOnMousePressed((MouseEvent event) -> {
                    x = event.getSceneX();
                    y = event.getSceneY();
                });

                root.setOnMouseDragged((MouseEvent event) -> {
                    stage.setX(event.getScreenX() - x);
                    stage.setY(event.getScreenY() - y);
                });


                stage.initStyle(StageStyle.DECORATED);
                stage.getIcons().add(new Image("/images/logo ENSA.png"));
                stage.setTitle("Ensate Management System");

                stage.setScene(scene);
                stage.show();
            }
        }catch (IOException e) {

            System.out.println(e.getMessage());
        }
    }*/



}
