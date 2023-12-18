package org.ankus.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QShareCodeView is a Querydsl query type for ShareCodeView
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QShareCodeView extends EntityPathBase<ShareCodeView> {

    private static final long serialVersionUID = 1933635216L;

    public static final QShareCodeView shareCodeView = new QShareCodeView("shareCodeView");

    public final StringPath codeComment = createString("codeComment");

    public final NumberPath<Long> codeId = createNumber("codeId", Long.class);

    public final StringPath name = createString("name");

    public final StringPath tags = createString("tags");

    public final StringPath title = createString("title");

    public final DateTimePath<java.util.Date> udate = createDateTime("udate", java.util.Date.class);

    public final NumberPath<Long> writer = createNumber("writer", Long.class);

    public QShareCodeView(String variable) {
        super(ShareCodeView.class, forVariable(variable));
    }

    public QShareCodeView(Path<? extends ShareCodeView> path) {
        super(path.getType(), path.getMetadata());
    }

    public QShareCodeView(PathMetadata metadata) {
        super(ShareCodeView.class, metadata);
    }

}

