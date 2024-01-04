package ma.ensate.demandesetudiants.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import lombok.NoArgsConstructor;
import ma.ensate.demandesetudiants.components.StageDasboard;
import ma.ensate.demandesetudiants.services.Command;
import ma.ensate.demandesetudiants.services.Invoqueur;
import ma.ensate.demandesetudiants.services.LoginReceiver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;


import java.net.URL;
import java.util.ResourceBundle;


@Controller
@NoArgsConstructor
public class LoginController implements Initializable {

    // Je dois recouperer les innstances de ces classes a partir de context application('est le plus grand probleme)?????????????????????????????????????????????????
    //Pour cela je dois utiliser un @PostConstruct
    @Autowired
    private  Invoqueur invoqueur ;
    @Autowired
    private LoginReceiver loginReceiver;
    @Autowired
    @Qualifier("commandLogin")
    private Command commandLogin ;
    @Autowired
    private StageDasboard stageDashboard;



    @FXML
    private Button btnPasswordForget;

    @FXML
    private Button btnSignin;

    @FXML
    private Label lblErrors;

    @FXML
    private TextField txtEmail;

    @FXML
    private PasswordField txtPassword;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


    /**
     * Pour la gestion de bouton de connexion
     * @param event
     */
    @FXML
    void handleLoginAction(MouseEvent event) {

        if(event.getSource()== btnSignin){

            String email = txtEmail.getText();
            String password = txtPassword.getText();


            if (loginReceiver != null) {

                loginReceiver.setEmail(email);
                loginReceiver.setPassword(password);


                invoqueur.addNewCommand("commandLogin",this.commandLogin);
                invoqueur.invoquer("commandLogin");

                boolean result = this.loginReceiver.isResult();
                if(result==true){

                    txtEmail.clear();
                    txtPassword.clear();
                    Parent newRoot = stageDashboard.getParent();
                    Node node = (Node)event.getSource();
                    Scene currentScene = (Scene)node.getScene(); // Cela nous permet de changer le root d'origine
                    currentScene.setRoot(newRoot);

                }else{
                    printErrorLabel(Color.TOMATO,"Email ou mot de passe incorrect !");
                }
            } else {
                System.err.println("Erreur : loginReceiver est null");
                System.err.println("Erreur : loginReceiver est null");
                System.err.println("Erreur : loginReceiver est null");
                System.err.println("Erreur : loginReceiver est null");
                System.err.println("Erreur : loginReceiver est null");
            }


        }

    }



    /**
     * Pour la gestion de boutoon forgetpasswrd
     * @param event
     */
    @FXML
    void handlePassordForgetAction(MouseEvent event) {


    }


    /**
     * Pour la gestion de label pour afficher les erreurs
     * @param color
     * @param strError
     */
    private void  printErrorLabel(Color color,String strError){
        lblErrors.setTextFill(color);
        lblErrors.setText(strError);
    }



}
