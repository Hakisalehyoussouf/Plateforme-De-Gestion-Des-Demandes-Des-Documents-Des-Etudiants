package ma.ensate.demandesetudiants.entities;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
//@Data @NoArgsConstructor @AllArgsConstructor
@DiscriminatorValue("2ap1")
public class Prepa1 extends Etudiants{
}
