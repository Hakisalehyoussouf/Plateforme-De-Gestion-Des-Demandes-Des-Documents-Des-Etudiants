package ma.ensate.demandesetudiants.services;

import ma.ensate.demandesetudiants.entities.Demandes;
import ma.ensate.demandesetudiants.entities.Etudiants;
import ma.ensate.demandesetudiants.entities.Historiques;

import java.util.List;

public interface IDemandesServices {

    public int getNumberOfDemands() ;
    public List<Object[]> getDistinctDemands() ;
    public List<Demandes> getDemandsByDocument(String document);
    public void deleteDemandsByStudentAndDocument(Etudiants etudiants, String document);

    public void rejectedDemand(Demandes demandes);
    public void genererAttestationDeReussite(Demandes demandes);
    public void genererConventionDeStage(Demandes demandes);
    public void genererReleveeDesNotes(Demandes demandes);



    public int getNumberOfHistoricalDemands();
    public List<Historiques> getHistoricalDemandsByDocument(String document);
    public Historiques addHistoricalDemand(Historiques demande);



}
