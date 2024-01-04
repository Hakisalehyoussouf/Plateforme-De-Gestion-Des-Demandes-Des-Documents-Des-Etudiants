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
public class Relelvee_Prepa2_Prepa1 extends Relevee{


    private Double algebre1;
    private Double analyse1;
    private Double algorithmique;
    private Double mtu;
    private Double physique1;
    private Double mecanique1;


    private Double analyse2;
    private Double electrocinetique;
    private Double mao;
    private Double tec;
    private Double physique2;
    private Double mecanique2;





}
