package org.ankus.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QShareCodeTag is a Querydsl query type for ShareCodeTag
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QShareCodeTag extends EntityPathBase<ShareCodeTag> {

    private static final long serialVersionUID = 1447846479L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QShareCodeTag shareCodeTag = new QShareCodeTag("shareCodeTag");

    public final QCodeTagId codeTagId;

    public final QShareCodeSearchTag searchTag;

    public final QShareCode shareCode;

    public QShareCodeTag(String variable) {
        this(ShareCodeTag.class, forVariable(variable), INITS);
    }

    public QShareCodeTag(Path<? extends ShareCodeTag> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QShareCodeTag(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QShareCodeTag(PathMetadata metadata, PathInits inits) {
        this(ShareCodeTag.class, metadata, inits);
    }

    public QShareCodeTag(Class<? extends ShareCodeTag> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.codeTagId = inits.isInitialized("codeTagId") ? new QCodeTagId(forProperty("codeTagId")) : null;
        this.searchTag = inits.isInitialized("searchTag") ? new QShareCodeSearchTag(forProperty("searchTag")) : null;
        this.shareCode = inits.isInitialized("shareCode") ? new QShareCode(forProperty("shareCode")) : null;
    }

}

