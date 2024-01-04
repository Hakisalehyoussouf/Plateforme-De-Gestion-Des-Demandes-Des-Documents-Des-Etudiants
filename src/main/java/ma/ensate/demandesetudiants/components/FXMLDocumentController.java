package ma.ensate.demandesetudiants.components;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import ma.ensate.demandesetudiants.App;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

//@Component
public class FXMLDocumentController implements ApplicationListener<App.StageReadyEvent> {

    private Parent root;

    @Value("classpath:/AdminDemandesDashboardtr.fxml")
    private Resource parentResource;
    private ApplicationContext context;

    public FXMLDocumentController(ApplicationContext context) {
        this.context = context;
    }


    public Parent getRoot() {
        return root;
    }

    public void setRoot(Parent root) {
        this.root = root;
    }



   /* public void saveFXML(String filePath) throws IOException {

        // Créer un fichier temporaire
        File tempFile = File.createTempFile("temp", ".fxml");
        tempFile.deleteOnExit();

        // Écrire le contenu du FXML dans le fichier temporaire
        try (FileWriter writer = new FileWriter(tempFile)) {
            FXMLLoader loader = new FXMLLoader(parentResource.getURL());
            //FXMLLoader loader = new FXMLLoader();
            //loader.setRoot(root);
            loader.setControllerFactory(aClass -> context.getBean(aClass));
            this.root = loader.load();
            //loader.load();
            writer.write(loader.getNamespace().toString());
        }

        // Renommer le fichier temporaire avec le nom spécifié
        File outputFile = new File(filePath);
        tempFile.renameTo(outputFile);
    }*/


    @Override
    public void onApplicationEvent(App.StageReadyEvent event) {

        // Créer un fichier temporaire
        File tempFile = null;
        try {
            tempFile = File.createTempFile("temp", ".fxml");

            // Écrire le contenu du FXML dans le fichier temporaire
            try (FileWriter writer = new FileWriter(tempFile)) {
                FXMLLoader loader = new FXMLLoader(parentResource.getURL());
                //FXMLLoader loader = new FXMLLoader();
                //loader.setRoot(root);
                loader.setControllerFactory(aClass -> context.getBean(aClass));
                //this.root = loader.load();
                loader.load();
                writer.write(loader.getNamespace().toString());
            }

            // Renommer le fichier temporaire avec le nom spécifié
            File outputFile = new File("/AdminDemandesDashboardtr.fxml");
            System.out.println("Juste pour renommer!!!!!!!!!!!!!");
            System.out.println("Juste pour renommer!!!!!!!!!!!!!");
            System.out.println("Juste pour renommer!!!!!!!!!!!!!");;
            tempFile.renameTo(outputFile);


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        tempFile.deleteOnExit();

    }
}
