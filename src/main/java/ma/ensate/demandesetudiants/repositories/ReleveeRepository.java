package ma.ensate.demandesetudiants.repositories;

import ma.ensate.demandesetudiants.entities.Relevee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface ReleveeRepository extends JpaRepository<Relevee,Long> {

    @Query("select r from Relevee  r where r.etudiant.apogee = :apogee and type(r) = :type")
    public Relevee findReleveeByEtudiantApogeeAndType(@Param("apogee") Long apogee, @Param("type") Class<? extends  Relevee> type);
}

