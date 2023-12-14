package org.ankus.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QShareCode is a Querydsl query type for ShareCode
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QShareCode extends EntityPathBase<ShareCode> {

    private static final long serialVersionUID = -1557275317L;

    public static final QShareCode shareCode = new QShareCode("shareCode");

    public final StringPath codeComment = createString("codeComment");

    public final NumberPath<Long> codeId = createNumber("codeId", Long.class);

    public final StringPath content = createString("content");

    public final ListPath<ShareCodeTag, QShareCodeTag> tagList = this.<ShareCodeTag, QShareCodeTag>createList("tagList", ShareCodeTag.class, QShareCodeTag.class, PathInits.DIRECT2);

    public final StringPath title = createString("title");

    public final DateTimePath<java.util.Date> udate = createDateTime("udate", java.util.Date.class);

    public final NumberPath<Long> writer = createNumber("writer", Long.class);

    public QShareCode(String variable) {
        super(ShareCode.class, forVariable(variable));
    }

    public QShareCode(Path<? extends ShareCode> path) {
        super(path.getType(), path.getMetadata());
    }

    public QShareCode(PathMetadata metadata) {
        super(ShareCode.class, metadata);
    }

}

