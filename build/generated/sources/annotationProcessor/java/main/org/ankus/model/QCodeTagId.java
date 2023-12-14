package org.ankus.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QCodeTagId is a Querydsl query type for CodeTagId
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class QCodeTagId extends BeanPath<CodeTagId> {

    private static final long serialVersionUID = -1112487097L;

    public static final QCodeTagId codeTagId = new QCodeTagId("codeTagId");

    public final NumberPath<Long> codeId = createNumber("codeId", Long.class);

    public final NumberPath<Long> tagId = createNumber("tagId", Long.class);

    public QCodeTagId(String variable) {
        super(CodeTagId.class, forVariable(variable));
    }

    public QCodeTagId(Path<? extends CodeTagId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCodeTagId(PathMetadata metadata) {
        super(CodeTagId.class, metadata);
    }

}

