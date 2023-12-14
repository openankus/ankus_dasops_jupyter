package org.ankus.service;

import org.ankus.model.StandardName;
import org.ankus.model.StandardTerm;
import org.ankus.model.StandardTermCategory;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class StandardTermSpecifications {

    public static Specification<StandardName> findName(String name, int category) {
        return new Specification<StandardName>() {
            @Override
            public Predicate toPredicate(Root<StandardName> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();

                //name
                predicates.add(criteriaBuilder.equal(criteriaBuilder.lower(root.get("name")), name.toLowerCase()));
                //category
                predicates.add(criteriaBuilder.equal(root.get("categoryId"), category));

                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            }
        };//return
    }//findName

    public static Specification<StandardTerm> findWord(String eng, int category) {
        return new Specification<StandardTerm>() {
            @Override
            public Predicate toPredicate(Root<StandardTerm> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();

                //abbr
                predicates.add(criteriaBuilder.equal(criteriaBuilder.lower(root.get("engName")), eng.toLowerCase()));
                //category
                predicates.add(criteriaBuilder.equal(root.get("categoryId"), category));

                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            }
        };//return
    }//find word
}
