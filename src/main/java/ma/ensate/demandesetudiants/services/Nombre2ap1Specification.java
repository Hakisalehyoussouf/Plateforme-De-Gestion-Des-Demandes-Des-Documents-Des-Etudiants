package ma.ensate.demandesetudiants.services;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import ma.ensate.demandesetudiants.entities.Etudiants;
import ma.ensate.demandesetudiants.entities.Prepa1;

public class Nombre2ap1Specification implements INombreEtudiantSpecification {
    @Override
    public Predicate toPredicate(Root<Etudiants> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        return builder.equal(root.type(), Prepa1.class);
    }

}
