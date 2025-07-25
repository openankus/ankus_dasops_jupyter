package org.ankus.repository;

import org.ankus.model.StandardName;
import org.ankus.model.StandardTerm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface StandardNameRepository extends JpaRepository<StandardName, Long>, JpaSpecificationExecutor<StandardName>, IStandardNameRepo {
}
