package org.ankus.repository;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.ankus.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.ankus.model.QStandardTerm.standardTerm;
import static org.ankus.model.QStandardTermView.standardTermView;

@Repository
public class StandardTermRepositoryImpl extends QuerydslRepositorySupport implements IStandardTermRepo {
    private final JPAQueryFactory queryFactory;

    public StandardTermRepositoryImpl(JPAQueryFactory queryFactory) {
        super(StandardTermView.class);
        this.queryFactory = queryFactory;
    }

    @Override
    public List<StandardTermView> findAllByName(String name, int category, Pageable pageable) {
        QStandardTermView c = standardTermView;

        //all category
        if (category == 0)
            return queryFactory.selectFrom(c).where(c.name.containsIgnoreCase(name))
                    .orderBy(getOrderSpec(pageable))
                    .fetch();

        return queryFactory.selectFrom(c).where(c.category.eq(category).and(c.name.containsIgnoreCase(name)))
                .orderBy(getOrderSpec(pageable))
                .fetch();
    }

    @Override
    public StandardTermView findOneByName(String name, int category) {
        QStandardTermView c = standardTermView;

        return queryFactory.selectFrom(c)
                .where(c.category.eq(category).and(c.name.equalsIgnoreCase(name)))
                .fetchFirst();
    }

    @Override
    public StandardTerm findOneByEng(String eng, int category) {
        /*QStandardTermView c = standardTermView;

        return queryFactory.selectFrom(c)
                .where(c.category.eq(category).and(c.engName.equalsIgnoreCase(eng)))
                .fetchFirst();*/

        QStandardTerm c = standardTerm;

        return queryFactory.selectFrom(c)
                .where(c.categoryId.eq(category).and(c.engName.equalsIgnoreCase(eng)))
                .fetchFirst();
    }

    @Override
    public List<StandardTermView> findAllByEng(String eng, int category, Pageable pageable) {
        QStandardTermView c = standardTermView;

        //all category
        if (category == 0)
            return queryFactory.selectFrom(c).where(c.engName.containsIgnoreCase(eng))
                    .orderBy(getOrderSpec(pageable))
                    .fetch();

        return queryFactory.selectFrom(c).where(c.category.eq(category).and(c.engName.containsIgnoreCase(eng)))
                .orderBy(getOrderSpec(pageable))
                .fetch();
    }

    @Override
    public List<StandardTermView> findAllByCategory(int category, Pageable pageable) {
        QStandardTermView c = standardTermView;

        if (category == 0)
            return queryFactory.selectFrom(c)
                    .orderBy(getOrderSpec(pageable))
                    .fetch();

        return queryFactory.selectFrom(c).where(c.category.eq(category))
                .orderBy(getOrderSpec(pageable))
                .fetch();
    }

    private OrderSpecifier getOrderSpec(Pageable pageable) {
        Sort.Order order = pageable.getSort().stream().findFirst().get();

        return new OrderSpecifier(order.getDirection().isAscending()? Order.ASC : Order.DESC,
                order.getProperty().equals("name")? standardTermView.name : standardTermView.engName);
    }
/*    @Override
    public Page<StandardTermView> findAllByName(Pageable pageable) {
        //JPQLQuery query = queryFactory.select(Projections.bean(
        //      CodeDto.class, c.codeId, c.title, c.codeComment, c.udate, c.name)).from(c);

        QStandardTermView c = standardTermView;
        long cnt = queryFactory.selectFrom(c)
                .fetchCount();

        List list = queryFactory.selectFrom(c)
                .offset(pageable.getOffset()).limit(pageable.getPageSize())
                .orderBy(getOrderSpec(pageable))
                .fetch();

        //페이지별 코드 데이터
        //List list = getQuerydsl().applyPagination(pageable, query).fetch();
        return new PageImpl<standardTermView>(list, pageable, cnt);
    }//findAllByTagContains

*/

}
