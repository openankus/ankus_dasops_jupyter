package org.ankus.repository;

import org.ankus.model.CodeTagId;
import org.ankus.model.ShareCodeTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ShareCodeTagRepository extends JpaRepository<ShareCodeTag, CodeTagId>, JpaSpecificationExecutor<ShareCodeTag>, IShareCodeTagRepo {

}
