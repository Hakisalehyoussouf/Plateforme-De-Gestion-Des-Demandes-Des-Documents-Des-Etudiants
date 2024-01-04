package ma.ensate.demandesetudiants.repositories;

import ma.ensate.demandesetudiants.entities.Admins;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AdminsRepository extends JpaRepository<Admins,Long> {
    public Admins findByEmailAndPassword(String email, String password);

}
