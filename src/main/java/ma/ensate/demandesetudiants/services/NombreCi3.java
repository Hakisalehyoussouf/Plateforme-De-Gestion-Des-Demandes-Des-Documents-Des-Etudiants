package ma.ensate.demandesetudiants.services;

import lombok.AllArgsConstructor;
import ma.ensate.demandesetudiants.entities.Etudiants;
import ma.ensate.demandesetudiants.repositories.EtudiantsRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;



@Service
@AllArgsConstructor
@Transactional
public class NombreCi3 extends NombreCi3Specification {


    private final EtudiantsRepository etudiantsRepository;
    public int  getAllCi3Etutiants(){
        List<Etudiants> etudiants = etudiantsRepository.findAll(new NombreCi3Specification());
        return  etudiants.size();
    }


}
