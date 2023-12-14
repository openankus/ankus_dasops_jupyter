package org.ankus.service;

import org.ankus.model.ShareCode;
import org.ankus.model.ShareCodeSearchTag;
import org.ankus.model.ShareCodeTag;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class ShareCodeSpecifications {
    public static Specification<ShareCode> containComment(List<String> comments) {
        return new Specification<ShareCode>() {
            @Override
            public Predicate toPredicate(Root<ShareCode> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();

                for (String comment : comments)
                    predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("codeComment")), "%" + comment.toLowerCase() + "%"));

                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            }
        };
    }//containComment

    public static Specification<ShareCode> containName(String name) {
        return new Specification<ShareCode>() {
            @Override
            public Predicate toPredicate(Root<ShareCode> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%" + name.toLowerCase() + "%");
            }
        };
    }//containComment
}
