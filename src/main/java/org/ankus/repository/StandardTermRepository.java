package org.ankus.repository;

import org.ankus.model.StandardTermView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface StandardTermRepository extends JpaRepository<StandardTermView, Long>, JpaSpecificationExecutor<StandardTermView>, IStandardTermRepo {
}
