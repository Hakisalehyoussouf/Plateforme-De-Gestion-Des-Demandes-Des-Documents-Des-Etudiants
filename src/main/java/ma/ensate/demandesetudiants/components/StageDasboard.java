package ma.ensate.demandesetudiants.components;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import ma.ensate.demandesetudiants.App;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.Resource;
import ma.ensate.demandesetudiants.App.StageReadyEvent;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
public class StageDasboard implements ApplicationListener<StageReadyEvent> {


    @Value("classpath:/AdminDemandesDashboard.fxml")
    private Resource AdminDemandesDashboardResource;
    private ApplicationContext applicationContext;
    private Parent parent;


    public Parent getParent(){
        return this.parent;
    }
    public void setAdminDemandesDashboardResource(Resource adminDemandesDashboardResource) {
        AdminDemandesDashboardResource = adminDemandesDashboardResource;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public StageDasboard(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void onApplicationEvent(App.StageReadyEvent event) {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(AdminDemandesDashboardResource.getURL());
            fxmlLoader.setControllerFactory(aClass -> applicationContext.getBean(aClass));
            this.parent = fxmlLoader.load();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }


}


