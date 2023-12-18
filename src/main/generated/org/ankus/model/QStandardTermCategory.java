package org.ankus.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStandardTermCategory is a Querydsl query type for StandardTermCategory
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QStandardTermCategory extends EntityPathBase<StandardTermCategory> {

    private static final long serialVersionUID = 79156392L;

    public static final QStandardTermCategory standardTermCategory = new QStandardTermCategory("standardTermCategory");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath name = createString("name");

    public final ListPath<StandardName, QStandardName> nameList = this.<StandardName, QStandardName>createList("nameList", StandardName.class, QStandardName.class, PathInits.DIRECT2);

    public final ListPath<StandardTerm, QStandardTerm> termList = this.<StandardTerm, QStandardTerm>createList("termList", StandardTerm.class, QStandardTerm.class, PathInits.DIRECT2);

    public QStandardTermCategory(String variable) {
        super(StandardTermCategory.class, forVariable(variable));
    }

    public QStandardTermCategory(Path<? extends StandardTermCategory> path) {
        super(path.getType(), path.getMetadata());
    }

    public QStandardTermCategory(PathMetadata metadata) {
        super(StandardTermCategory.class, metadata);
    }

}

