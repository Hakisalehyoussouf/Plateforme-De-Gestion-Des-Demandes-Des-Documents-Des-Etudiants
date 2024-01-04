package ma.ensate.demandesetudiants.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data @NoArgsConstructor @AllArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Relevee {

    @Id
    protected Long id;
    protected Double moyenneN;
    protected String resultatN;

    @OneToOne(fetch = FetchType.LAZY)
    protected  Etudiants etudiant;

}
