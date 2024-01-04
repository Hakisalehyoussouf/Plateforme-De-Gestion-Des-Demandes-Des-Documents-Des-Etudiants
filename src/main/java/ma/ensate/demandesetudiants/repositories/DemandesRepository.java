package ma.ensate.demandesetudiants.repositories;

import ma.ensate.demandesetudiants.entities.Demandes;
import ma.ensate.demandesetudiants.entities.Etudiants;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;


@Repository
public interface DemandesRepository extends JpaRepository<Demandes,Long> {
    @Query("SELECT d.document, COUNT(d) FROM Demandes d GROUP BY d.document")
    public List<Object[]> countDistinctByDocument();

    public List<Demandes> findByDocument(String document);

    public void deleteByEtudiantAndDocument(Etudiants etudiants, String document);

}
