package ma.ensate.demandesetudiants.services;

import lombok.AllArgsConstructor;
import ma.ensate.demandesetudiants.entities.Etudiants;
import ma.ensate.demandesetudiants.repositories.EtudiantsRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
@AllArgsConstructor
public class Nombre2ap2 extends Nombre2ap2Specification {

    private final EtudiantsRepository etudiantsRepository;

    public int  getAll2ap2Etutiants(){
        List<Etudiants> etudiants = etudiantsRepository.findAll(new Nombre2ap2Specification());
        return  etudiants.size();
    }

}

