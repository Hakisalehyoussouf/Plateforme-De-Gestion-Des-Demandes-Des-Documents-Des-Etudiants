package ma.ensate.demandesetudiants.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Admins {


    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private  String email;
    private String nom;
    private String prenom;
    private  String password;
}
