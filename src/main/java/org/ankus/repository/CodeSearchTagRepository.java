package org.ankus.repository;

import org.ankus.model.ShareCodeSearchTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface CodeSearchTagRepository extends JpaRepository<ShareCodeSearchTag, Long>, JpaSpecificationExecutor<ShareCodeSearchTag>, ICodeSearchTagRepo {
    public Optional<ShareCodeSearchTag> findByName(String name);
}
