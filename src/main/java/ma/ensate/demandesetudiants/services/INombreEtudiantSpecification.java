package ma.ensate.demandesetudiants.services;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import ma.ensate.demandesetudiants.entities.Etudiants;
import org.springframework.data.jpa.domain.Specification;


public interface INombreEtudiantSpecification extends Specification<Etudiants> {
    Predicate toPredicate(Root<Etudiants> root, CriteriaQuery<?> query, CriteriaBuilder builder);
}
