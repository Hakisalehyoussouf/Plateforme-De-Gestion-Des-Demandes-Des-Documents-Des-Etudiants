package ma.ensate.demandesetudiants.services;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import ma.ensate.demandesetudiants.entities.Demandes;
import org.springframework.stereotype.Service;


@Getter @Setter
public abstract class DemandePDFGenerator {
    protected Demandes demande;

    public final void generatePDF() {
        createDocument();
        addHeader();
        addBody();
        addFooter();
        saveDocument();
    }

    protected abstract void createDocument();
    protected abstract void addHeader();
    protected abstract void addBody();
    protected abstract void addFooter();
    protected abstract void saveDocument();
}
