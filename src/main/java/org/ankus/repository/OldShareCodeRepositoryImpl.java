package org.ankus.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.ankus.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.ankus.model.QShareCode.shareCode;
import static org.ankus.model.QShareCodeTagView.shareCodeTagView;
import static org.ankus.model.QUser.user;

@Repository
public class OldShareCodeRepositoryImpl extends QuerydslRepositorySupport implements IOldShareCodeRepo {
    private final JPAQueryFactory queryFactory;
    //private final Date toDate = java.sql.Date.valueOf(LocalDate.of(2023, 3, 2));

    public OldShareCodeRepositoryImpl(JPAQueryFactory queryFactory) {
        super(ShareCode.class);
        this.queryFactory = queryFactory;
    }

    @Override
    public Page<ShareCode> findAllWithTag(Pageable pageable) {
        QShareCode c = shareCode;

        JPQLQuery query = queryFactory.select(
                Projections.bean(CodeDto.class, c.codeId, c.title, c.codeComment, c.udate, user.name)).from(c)
                .join(user).on(c.writer.eq(user.id)).where(c.content.startsWith("{\"cells\":[").not());
                //.where(c.udate.before(toDate));

        long cnt = queryFactory.selectFrom(c)
                .where(c.content.startsWith("{\"cells\":[").not())
                //.where(c.udate.before(toDate))
                .fetchCount();

        //페이지별 코드 데이터
        List list = getQuerydsl().applyPagination(pageable, query).fetch();
        list = findTag(list);
        return new PageImpl<ShareCode>(list, pageable, cnt);
    }//findAllByTagContains

    @Override
    public Page<ShareCode> findAllByTagContains(List<String> taglist, Pageable pageable) {
        QShareCodeTagView ct = shareCodeTagView;

        //첫번째 검색 태그를 포함하는, 코드 아이디 목록
        List<Long> codeidlist = queryFactory.select(ct.codeTagId.codeId).from(ct)
                .where(ct.name.containsIgnoreCase(taglist.get(0)))
                .groupBy(ct.codeTagId.codeId).fetch();

        for (int i=1; i< taglist.size(); ++i) {
            //코드 없음
            if (codeidlist.isEmpty())
                return null;

            //검색 태그를 포함하고, 이전 태그 검색 결과에 있는, 코드 아이디 목록
            codeidlist = queryFactory.select(ct.codeTagId.codeId).from(ct)
                    .where(ct.name.containsIgnoreCase(taglist.get(i)).and(ct.codeTagId.codeId.in(codeidlist)))
                    .groupBy(ct.codeTagId.codeId).fetch();
        }

        //코드 없음
        if (codeidlist.isEmpty())
            return null;

        //코드 데이터 검색
        QShareCode c = shareCode;

        long cnt = queryFactory.selectFrom(c)
                .where(c.content.startsWith("{\"cells\":[").not().and(c.codeId.in(codeidlist))).fetchCount();

        JPQLQuery query = queryFactory.select(Projections.bean(CodeDto.class,
                        c.codeId, c.title, c.codeComment, c.udate, user.name)).from(c)
                .join(user).on(c.writer.eq(user.id))
                .where(c.content.startsWith("{\"cells\":[").not().and(c.codeId.in(codeidlist)));

        //페이지별 코드 데이터
        List list = getQuerydsl().applyPagination(pageable, query).fetch();
        list = findTag(list);
        return new PageImpl<ShareCode>(list, pageable, cnt);
    }//findAllByTagContains

    @Override
    public Page<ShareCode> findAllByNameContains(String name, Pageable pageable) {
        QShareCode c = shareCode;

        long cnt = queryFactory.selectFrom(c)
                .where(c.content.startsWith("{\"cells\":[").not().and(c.title.containsIgnoreCase(name)))
                .fetchCount();

        JPQLQuery query = queryFactory.select(Projections.bean(CodeDto.class,
                        c.codeId, c.title, c.codeComment, c.udate, user.name)).from(c)
                .join(user).on(c.writer.eq(user.id))
                .where(c.content.startsWith("{\"cells\":[").not().and(c.title.containsIgnoreCase(name)));

        List list = getQuerydsl().applyPagination(pageable, query).fetch();
        list = findTag(list);
        return new PageImpl<ShareCode>(list, pageable, cnt);
    }//findAllByName

    @Override
    public Page<ShareCode> findAllByCommentContains(List<String> keyword, Pageable pageable) {
        QShareCode c = shareCode;

        BooleanExpression expr = c.codeComment.containsIgnoreCase(keyword.get(0));
        for (int i=1; i< keyword.size(); ++i) {
            expr = expr.and(c.codeComment.containsIgnoreCase(keyword.get(i)));
        }

        long cnt = queryFactory.selectFrom(c)
                .where(c.content.startsWith("{\"cells\":[").not().and(expr))
                .fetchCount();

        JPQLQuery query = queryFactory.select(Projections.bean(CodeDto.class,
                        c.codeId, c.title, c.codeComment, c.udate, user.name)).from(c)
                .join(user).on(c.writer.eq(user.id))
                .where(c.content.startsWith("{\"cells\":[").not().and(expr));

        List list = getQuerydsl().applyPagination(pageable, query).fetch();
        list = findTag(list);
        return new PageImpl<ShareCode>(list, pageable, cnt);
    } //findAllByCommentContains

    @Override
    public Page<ShareCode> findAllByWriterContains(String writer, Pageable pageable) {
        QShareCode c = shareCode;

        long cnt = queryFactory.selectFrom(c)
                .join(user).on(c.writer.eq(user.id))
                .where(c.content.startsWith("{\"cells\":[").not().and(user.name.containsIgnoreCase(writer)))
                .fetchCount();

        JPQLQuery query = queryFactory.select(Projections.bean(CodeDto.class,
                        c.codeId, c.title, c.codeComment, c.udate, user.name)).from(c)
                .join(user).on(c.writer.eq(user.id))
                .where(c.content.startsWith("{\"cells\":[").not().and(user.name.containsIgnoreCase(writer)));

        List list = getQuerydsl().applyPagination(pageable, query).fetch();
        list = findTag(list);
        return new PageImpl<ShareCode>(list, pageable, cnt);
    }//findAllByName

    private List<CodeDto> findTag(List<CodeDto> codelist) {
        QShareCodeTagView t = shareCodeTagView;

        codelist.forEach(code -> {
            List list = queryFactory.select(Projections.bean(TagDto.class, t.codeTagId.tagId, t.name))
                    .from(t).where(t.codeTagId.codeId.eq(code.getCodeId()))
                    .fetch();

            code.setTags(list);
        });
        return codelist;
    }
}
