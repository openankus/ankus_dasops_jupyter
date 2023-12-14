package org.ankus.repository;

import com.querydsl.core.types.*;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import org.ankus.model.*;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.ankus.model.QShareCode.shareCode;
import static org.ankus.model.QShareCodeTagView.shareCodeTagView;
import static org.ankus.model.QShareCodeView.shareCodeView;
import static org.ankus.model.QUser.user;


@Repository
public class ShareCodeRepositoryImpl extends QuerydslRepositorySupport implements IShareCodeRepo {
    private final JPAQueryFactory queryFactory;

    public ShareCodeRepositoryImpl(JPAQueryFactory queryFactory) {
        super(ShareCode.class);
        this.queryFactory = queryFactory;
    }

    @Override
    public Page<ShareCodeView> findAllWithTag(Pageable pageable) {
        //JPQLQuery query = queryFactory.select(Projections.bean(
          //      CodeDto.class, c.codeId, c.title, c.codeComment, c.udate, c.name)).from(c);

        QShareCodeView c = shareCodeView;
        long cnt = queryFactory.selectFrom(c)
                .fetchCount();

        List list = queryFactory.selectFrom(c)
                .offset(pageable.getOffset()).limit(pageable.getPageSize())
                .orderBy(getOrderSpec(pageable))
                .fetch();

        //페이지별 코드 데이터
        //List list = getQuerydsl().applyPagination(pageable, query).fetch();
        return new PageImpl<ShareCodeView>(list, pageable, cnt);
    }//findAllByTagContains

    private OrderSpecifier getOrderSpec(Pageable pageable) {
        Sort.Order order = pageable.getSort().stream().findFirst().get();

        return new OrderSpecifier(order.getDirection().isAscending()? Order.ASC : Order.DESC,
                order.getProperty().equals("title")? shareCodeView.title : shareCodeView.udate);
    }

    @Override
    public Page<ShareCodeView> findAllByTagContains(List<String> taglist, Pageable pageable) {
/*
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
        QShareCodeView c = shareCodeView;
        JPQLQuery query = queryFactory.select(Projections.bean(CodeDto.class,
                        c.codeId, c.title, c.codeComment, c.udate, c.name)).from(c)
                .where(c.codeId.in(codeidlist));*/

        QShareCodeView c = shareCodeView;

        BooleanExpression expr = c.tags.containsIgnoreCase(taglist.get(0));
        for (int i=1; i< taglist.size(); ++i) {
            expr = expr.and(c.tags.containsIgnoreCase(taglist.get(i)));
        }

        long cnt = queryFactory.selectFrom(c).where(expr)
                .fetchCount();

        List list = queryFactory.selectFrom(c).where(expr)
                .offset(pageable.getOffset()).limit(pageable.getPageSize())
                .orderBy(getOrderSpec(pageable))
                .fetch();

        //페이지별 코드 데이터
        //List list = getQuerydsl().applyPagination(pageable, query).fetch();
        return new PageImpl<ShareCodeView>(list, pageable, cnt);
    }//findAllByTagContains

    @Override
    public Page<ShareCodeView> findAllByNameContains(String name, Pageable pageable) {
        QShareCodeView c = shareCodeView;

        long cnt = queryFactory.selectFrom(c).where(c.title.containsIgnoreCase(name))
                .fetchCount();

        List list = queryFactory.selectFrom(c).where(c.title.containsIgnoreCase(name))
                .offset(pageable.getOffset()).limit(pageable.getPageSize())
                .orderBy(getOrderSpec(pageable))
                .fetch();

        //List<ShareCodeView> codes = getQuerydsl().applyPagination(pageable, query).fetch();
        return new PageImpl<ShareCodeView>(list, pageable, cnt);
    }//findAllByName

    @Override
    public Page<ShareCodeView> findAllByCommentContains(List<String> keyword, Pageable pageable) {
        QShareCodeView c = shareCodeView;

        BooleanExpression expr = c.codeComment.containsIgnoreCase(keyword.get(0));
        for (int i=1; i< keyword.size(); ++i) {
            expr = expr.and(c.codeComment.containsIgnoreCase(keyword.get(i)));
        }

        long cnt = queryFactory.selectFrom(c).where(expr)
                .fetchCount();

        List list = queryFactory.selectFrom(c).where(expr)
                .offset(pageable.getOffset()).limit(pageable.getPageSize())
                .orderBy(getOrderSpec(pageable))
                .fetch();

        return new PageImpl<ShareCodeView>(list, pageable, cnt);
    } //findAllByCommentContains

    @Override
    public Page<ShareCodeView> findAllByWriterContains(String writer, Pageable pageable) {
        QShareCodeView c = shareCodeView;

        long cnt = queryFactory.selectFrom(c).where(c.name.containsIgnoreCase(writer))
                .fetchCount();

        List list = queryFactory.selectFrom(c).where(c.name.containsIgnoreCase(writer))
                .offset(pageable.getOffset()).limit(pageable.getPageSize())
                .orderBy(getOrderSpec(pageable))
                .fetch();

        return new PageImpl<ShareCodeView>(list, pageable, cnt);
    }//findAllByName

    @Override
    public CodeDto findById(long id) {
        //code
        QShareCode c = shareCode;

        List<CodeDto> res = queryFactory.select(Projections.constructor(
                CodeDto.class, c.codeId, c.codeComment, c.content, c.title, c.udate, c.writer, user.name))
                .from(c).where(c.codeId.eq(id))
                .join(user).on(c.writer.eq(user.id))
                .fetch();

        return findTag(res).get(0);
    }//findById

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
