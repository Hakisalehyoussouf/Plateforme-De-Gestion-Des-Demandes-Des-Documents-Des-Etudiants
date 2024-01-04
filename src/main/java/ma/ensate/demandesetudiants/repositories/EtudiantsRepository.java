package ma.ensate.demandesetudiants.repositories;

import ma.ensate.demandesetudiants.entities.Etudiants;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface EtudiantsRepository extends JpaRepository<Etudiants, Long> , JpaSpecificationExecutor<Etudiants> {
}
