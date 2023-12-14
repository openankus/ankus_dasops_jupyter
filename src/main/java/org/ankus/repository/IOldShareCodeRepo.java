package org.ankus.repository;

import org.ankus.model.ShareCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IOldShareCodeRepo {
    public Page<ShareCode> findAllByTagContains(List<String> taglist, Pageable pageable);
    public Page<ShareCode> findAllByNameContains(String name, Pageable pageable);
    public Page<ShareCode> findAllByCommentContains(List<String> keyword, Pageable pageable);
    public Page<ShareCode> findAllByWriterContains(String writer, Pageable pageable);
    public Page<ShareCode> findAllWithTag(Pageable pageable);
}
