package ma.ensate.demandesetudiants.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor @AllArgsConstructor
public class Demandes {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String prenom;


    private String filiere;
    private String niveauDocument;
    private String document;


    @ManyToOne
    private  Etudiants etudiant;


}
