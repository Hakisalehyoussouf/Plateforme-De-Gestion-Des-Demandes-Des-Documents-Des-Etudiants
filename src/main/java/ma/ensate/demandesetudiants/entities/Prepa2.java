package ma.ensate.demandesetudiants.entities;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Entity
//@Data @NoArgsConstructor @AllArgsConstructor //Je dois ajouter lombok sur le cv(a ne pas oublier HHhhhhhhhhhhhhhhhhhhhhhhhh)
@DiscriminatorValue("2ap2")
public class Prepa2 extends Etudiants{
}
