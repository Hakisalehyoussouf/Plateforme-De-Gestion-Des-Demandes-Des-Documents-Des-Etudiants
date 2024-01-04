package ma.ensate.demandesetudiants;

import javafx.application.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class DemandesEtudiantsApplication {

    public static void main(String[] args) {
        Application.launch(App.class, args);
        //SpringApplication.run(DemandesEtudiantsApplication.class, args);
    }

}
