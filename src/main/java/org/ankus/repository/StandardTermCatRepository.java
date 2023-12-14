package org.ankus.repository;

import org.ankus.model.StandardTermCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface StandardTermCatRepository extends JpaRepository<StandardTermCategory, Integer>, JpaSpecificationExecutor<StandardTermCategory>, IStandardTermCatRepo {
}
