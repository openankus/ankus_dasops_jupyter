package org.ankus.repository;

import org.ankus.model.ShareCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface OldShareCodeRepository extends JpaRepository<ShareCode, Long>, JpaSpecificationExecutor<ShareCode>, IOldShareCodeRepo {
}
