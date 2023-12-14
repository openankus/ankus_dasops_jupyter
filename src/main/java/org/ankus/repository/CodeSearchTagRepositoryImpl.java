package org.ankus.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.ankus.model.QShareCodeSearchTag;
import org.ankus.model.QShareCodeTag;
import org.ankus.model.ShareCodeSearchTag;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

import static org.ankus.model.QShareCodeSearchTag.shareCodeSearchTag;
import static org.ankus.model.QShareCodeTag.shareCodeTag;


@Repository
public class CodeSearchTagRepositoryImpl extends QuerydslRepositorySupport implements ICodeSearchTagRepo {
    private final JPAQueryFactory queryFactory;

    public CodeSearchTagRepositoryImpl(JPAQueryFactory queryFactory) {
        super(ShareCodeSearchTag.class);
        this.queryFactory = queryFactory;
    }

    @Transactional
    @Override
    public void deleteNotUsed() {
        QShareCodeSearchTag t = shareCodeSearchTag;
        QShareCodeTag ct = shareCodeTag;
        queryFactory.delete(t).where(t.tagId.notIn(
                queryFactory.select(ct.codeTagId.tagId).from(ct).groupBy(ct.codeTagId.tagId))).execute();
    }
}
