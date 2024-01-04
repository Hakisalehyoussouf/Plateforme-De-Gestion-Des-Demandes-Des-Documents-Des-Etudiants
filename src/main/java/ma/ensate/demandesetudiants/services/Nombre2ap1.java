package ma.ensate.demandesetudiants.services;


import lombok.AllArgsConstructor;
import ma.ensate.demandesetudiants.entities.Etudiants;
import ma.ensate.demandesetudiants.repositories.EtudiantsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class Nombre2ap1 extends Nombre2ap1Specification{

    private final EtudiantsRepository etudiantsRepository;
    public int getAll2ap1Etudiants() {
        List<Etudiants> etudiants = etudiantsRepository.findAll(new Nombre2ap1Specification());
        return  etudiants.size();
    }
}
