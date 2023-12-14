package org.ankus.repository;

import org.ankus.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IStandardTermRepo {
    public List<StandardTermView> findAllByName(String name, int category, Pageable pageable);
    public List<StandardTermView> findAllByEng(String eng, int category, Pageable pageable);
    public List<StandardTermView> findAllByCategory(int category, Pageable pageable);
    public StandardTermView findOneByName(String name, int category);
    public StandardTerm findOneByEng(String eng, int category);
}
