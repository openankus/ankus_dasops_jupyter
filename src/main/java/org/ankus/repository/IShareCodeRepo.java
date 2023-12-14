package org.ankus.repository;

import org.ankus.model.CodeDto;
import org.ankus.model.ShareCodeView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface IShareCodeRepo {
    public Page<ShareCodeView> findAllByTagContains(List<String> taglist, Pageable pageable);
    public Page<ShareCodeView> findAllByNameContains(String name, Pageable pageable);
    public Page<ShareCodeView> findAllByCommentContains(List<String> keyword, Pageable pageable);
    public Page<ShareCodeView> findAllByWriterContains(String writer, Pageable pageable);
    public Page<ShareCodeView> findAllWithTag(Pageable pageable);

    public CodeDto findById(long id);
}
