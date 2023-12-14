package org.ankus.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QShareCodeSearchTag is a Querydsl query type for ShareCodeSearchTag
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QShareCodeSearchTag extends EntityPathBase<ShareCodeSearchTag> {

    private static final long serialVersionUID = 2030970823L;

    public static final QShareCodeSearchTag shareCodeSearchTag = new QShareCodeSearchTag("shareCodeSearchTag");

    public final ListPath<ShareCodeTag, QShareCodeTag> codeList = this.<ShareCodeTag, QShareCodeTag>createList("codeList", ShareCodeTag.class, QShareCodeTag.class, PathInits.DIRECT2);

    public final StringPath name = createString("name");

    public final NumberPath<Long> tagId = createNumber("tagId", Long.class);

    public QShareCodeSearchTag(String variable) {
        super(ShareCodeSearchTag.class, forVariable(variable));
    }

    public QShareCodeSearchTag(Path<? extends ShareCodeSearchTag> path) {
        super(path.getType(), path.getMetadata());
    }

    public QShareCodeSearchTag(PathMetadata metadata) {
        super(ShareCodeSearchTag.class, metadata);
    }

}

