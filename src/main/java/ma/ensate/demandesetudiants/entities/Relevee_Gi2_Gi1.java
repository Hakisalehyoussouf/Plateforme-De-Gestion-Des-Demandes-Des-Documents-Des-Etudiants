package ma.ensate.demandesetudiants.entities;


import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
@AttributeOverrides({
        @AttributeOverride(name = "moyenneN", column = @Column(name = "moyenne")),
        @AttributeOverride(name = "resultatN", column = @Column(name = "resultat"))
})
public class Relevee_Gi2_Gi1 extends Relevee{

    private Double management;
    private Double tec;
    private Double proba;
    private Double baseDesDonnees;
    private Double rechercheOperationnelle;
    private Double reseau;


    private Double languageC;
    private Double sysExxploitation;
    private Double compilation;
    private Double electronique;
    private Double archOrdinateurs;
    private Double web;



}
