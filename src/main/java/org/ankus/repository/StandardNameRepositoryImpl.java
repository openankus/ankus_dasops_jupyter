package org.ankus.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.ankus.model.QStandardName;
import org.ankus.model.QStandardTerm;
import org.ankus.model.StandardName;
import org.ankus.model.StandardTerm;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import static org.ankus.model.QStandardName.standardName;
import static org.ankus.model.QStandardTerm.standardTerm;

@Repository
public class StandardNameRepositoryImpl extends QuerydslRepositorySupport implements IStandardNameRepo {
    private final JPAQueryFactory queryFactory;

    public StandardNameRepositoryImpl(JPAQueryFactory queryFactory) {
        super(StandardName.class);
        this.queryFactory = queryFactory;
    }
}
