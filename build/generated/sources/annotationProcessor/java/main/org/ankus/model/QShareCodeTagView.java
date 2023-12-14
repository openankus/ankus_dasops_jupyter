package org.ankus.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QShareCodeTagView is a Querydsl query type for ShareCodeTagView
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QShareCodeTagView extends EntityPathBase<ShareCodeTagView> {

    private static final long serialVersionUID = -1177726572L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QShareCodeTagView shareCodeTagView = new QShareCodeTagView("shareCodeTagView");

    public final QCodeTagId codeTagId;

    public final StringPath name = createString("name");

    public QShareCodeTagView(String variable) {
        this(ShareCodeTagView.class, forVariable(variable), INITS);
    }

    public QShareCodeTagView(Path<? extends ShareCodeTagView> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QShareCodeTagView(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QShareCodeTagView(PathMetadata metadata, PathInits inits) {
        this(ShareCodeTagView.class, metadata, inits);
    }

    public QShareCodeTagView(Class<? extends ShareCodeTagView> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.codeTagId = inits.isInitialized("codeTagId") ? new QCodeTagId(forProperty("codeTagId")) : null;
    }

}

