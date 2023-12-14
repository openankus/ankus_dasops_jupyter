package org.ankus.repository;


import org.ankus.model.TagDto;

import java.util.List;

public interface IShareCodeTagRepo {
    public void deleteByCodeId(long id);
    public List<TagDto> findTagByCodeId(long id);
}
