package org.ankus.repository;

import org.ankus.model.StandardTerm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface StandardWordRepository extends JpaRepository<StandardTerm, Long>, JpaSpecificationExecutor<StandardTerm> {
}
