package ma.ensate.demandesetudiants.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Entity
@Data @NoArgsConstructor @AllArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "niveau", length = 4)
public abstract class Etudiants {

    @Id
    protected Long apogee;
    protected String cin;
    protected String nom;
    protected String prenom;
    protected String email;
    protected String filiere;


    @OneToOne(mappedBy = "etudiant")
    protected Relevee relevee;

    @OneToMany(mappedBy = "etudiant")
    protected List<Demandes> demandes;

    @OneToMany(mappedBy = "etudiant")
    protected List<Historiques> historiques;

}
