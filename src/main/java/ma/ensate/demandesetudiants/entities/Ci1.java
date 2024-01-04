package ma.ensate.demandesetudiants.entities;



import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
//@Data @NoArgsConstructor @AllArgsConstructor
@DiscriminatorValue("ci1")
public class Ci1 extends Etudiants{

}
