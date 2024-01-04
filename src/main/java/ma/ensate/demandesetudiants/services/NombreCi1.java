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
public class NombreCi1 extends NombreCi1Specification {

    private final EtudiantsRepository etudiantsRepository;
    public int  getAllCi1Etutiants(){
        List<Etudiants> etudiants = etudiantsRepository.findAll(new NombreCi1Specification());
        return  etudiants.size();
    }


}
