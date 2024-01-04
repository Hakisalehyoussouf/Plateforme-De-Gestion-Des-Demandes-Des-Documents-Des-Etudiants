package ma.ensate.demandesetudiants.services;

import lombok.AllArgsConstructor;
import ma.ensate.demandesetudiants.entities.Etudiants;
import ma.ensate.demandesetudiants.repositories.EtudiantsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@AllArgsConstructor
@Transactional
public class NombreCi2 extends NombreCi2Specification {

    private final EtudiantsRepository etudiantsRepository;

    public int  getAllCi2Etutiants(){
        List<Etudiants> etudiants = etudiantsRepository.findAll(new NombreCi2Specification());
        return  etudiants.size();
    }


}
