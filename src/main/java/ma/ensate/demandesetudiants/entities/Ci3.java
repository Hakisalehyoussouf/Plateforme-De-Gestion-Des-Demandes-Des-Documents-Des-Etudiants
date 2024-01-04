package ma.ensate.demandesetudiants.entities;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
//@Data @NoArgsConstructor @AllArgsConstructor
@DiscriminatorValue("ci3")
public class Ci3 extends Etudiants{

}
