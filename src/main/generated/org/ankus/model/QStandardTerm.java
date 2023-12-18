package org.ankus.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStandardTerm is a Querydsl query type for StandardTerm
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QStandardTerm extends EntityPathBase<StandardTerm> {

    private static final long serialVersionUID = -802955894L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QStandardTerm standardTerm = new QStandardTerm("standardTerm");

    public final NumberPath<Integer> categoryId = createNumber("categoryId", Integer.class);

    public final StringPath desc = createString("desc");

    public final StringPath engDesc = createString("engDesc");

    public final StringPath engName = createString("engName");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath mainName = createString("mainName");

    public final ListPath<StandardName, QStandardName> nameList = this.<StandardName, QStandardName>createList("nameList", StandardName.class, QStandardName.class, PathInits.DIRECT2);

    public final QStandardTermCategory wdCategory;

    public QStandardTerm(String variable) {
        this(StandardTerm.class, forVariable(variable), INITS);
    }

    public QStandardTerm(Path<? extends StandardTerm> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QStandardTerm(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QStandardTerm(PathMetadata metadata, PathInits inits) {
        this(StandardTerm.class, metadata, inits);
    }

    public QStandardTerm(Class<? extends StandardTerm> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.wdCategory = inits.isInitialized("wdCategory") ? new QStandardTermCategory(forProperty("wdCategory")) : null;
    }

}

