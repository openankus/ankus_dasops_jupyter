package org.ankus.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.ankus.model.QShareCodeTag;
import org.ankus.model.QShareCodeTagView;
import org.ankus.model.ShareCodeTag;
import org.ankus.model.TagDto;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

import java.util.List;

import static org.ankus.model.QShareCodeTag.shareCodeTag;
import static org.ankus.model.QShareCodeTagView.shareCodeTagView;

@Repository
public class ShareCodeTagRepositoryImpl extends QuerydslRepositorySupport implements IShareCodeTagRepo {
    private final JPAQueryFactory queryFactory;

    public ShareCodeTagRepositoryImpl(JPAQueryFactory queryFactory) {
        super(ShareCodeTag.class);
        this.queryFactory = queryFactory;
    }

    @Transactional
    @Override
    public void deleteByCodeId(long id) {
        QShareCodeTag ct = shareCodeTag;
        queryFactory.delete(ct).where(ct.codeTagId.codeId.eq(id)).execute();
    }

    @Override
    public List<TagDto> findTagByCodeId(long id) {
        QShareCodeTagView ct = shareCodeTagView;

        JPQLQuery query2 = queryFactory.select(
                        Projections.bean(TagDto.class, ct.codeTagId.tagId, ct.name)).from(ct)
                .where(ct.codeTagId.codeId.eq(id));

        //if (query2.fetchCount() == 0)
          //  return null;

        return query2.fetch();
    }//findTagByCodeId
}
