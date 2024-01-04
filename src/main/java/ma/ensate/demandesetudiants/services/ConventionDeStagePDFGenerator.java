package ma.ensate.demandesetudiants.services;


import ma.ensate.demandesetudiants.components.Email;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;


@Service
@Transactional
public class ConventionDeStagePDFGenerator extends DemandePDFGenerator {

    private final Email email;


    public ConventionDeStagePDFGenerator(Email email) {
        this.email = email;
    }


    @Override
    protected void createDocument() {

    }

    @Override
    protected void addHeader() {

    }

    @Override
    protected void addBody() {

    }

    @Override
    protected void addFooter() {

    }

    @Override
    protected void saveDocument() {

        try {
            this.email.envoie(this.demande.getEtudiant().getEmail(), "C:\\Formation\\Spring_Ecosystem\\demandes-etudiants\\src\\main\\resources\\static\\documents\\Convention de stage.pdf");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
