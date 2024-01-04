package ma.ensate.demandesetudiants.repositories;

import ma.ensate.demandesetudiants.entities.Historiques;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface HistoriquesRepository extends JpaRepository<Historiques, Long> {
    List<Historiques> findByDocument(String document);
}
