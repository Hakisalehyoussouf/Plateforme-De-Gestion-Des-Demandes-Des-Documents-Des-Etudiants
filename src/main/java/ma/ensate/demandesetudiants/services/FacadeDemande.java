package ma.ensate.demandesetudiants.services;


import lombok.AllArgsConstructor;
import ma.ensate.demandesetudiants.entities.Demandes;
import ma.ensate.demandesetudiants.entities.Etudiants;
import ma.ensate.demandesetudiants.entities.Historiques;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@Transactional
@AllArgsConstructor
public class FacadeDemande {
    private final IDemandesServices demandesServices;

    public int getNumberOfDemands(){
        return  demandesServices.getNumberOfDemands();
    }
    public List<Object[]> getDistinctDemands(){return demandesServices.getDistinctDemands();}
    public List<Demandes> getDemandsByDocument(String document) {return demandesServices.getDemandsByDocument(document);}
    public void deleteDemandsByStudentAndDocument(Etudiants etudiants, String document) { demandesServices.deleteDemandsByStudentAndDocument(etudiants, document); }
    public void rejectedDemand(Demandes demandes) { demandesServices.rejectedDemand(demandes);  }
    public void genererConventionDeStage(Demandes demandes) { demandesServices.genererConventionDeStage(demandes);  }

    public void genererAttestationDeReussite(Demandes demandes) { demandesServices.genererAttestationDeReussite(demandes);  }
    public void genererReleveeDesNotes(Demandes demandes) { demandesServices.genererReleveeDesNotes(demandes);  }

    public int getNumberOfHistoricalDemands(){ return  demandesServices.getNumberOfHistoricalDemands();}
    public List<Historiques> getHistoricalDemandsByDocument(String document) { return demandesServices.getHistoricalDemandsByDocument(document);}
    public Historiques addHistoricalDemand(Historiques demande) {return demandesServices.addHistoricalDemand(demande);}


}
