package org.ankus.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStandardName is a Querydsl query type for StandardName
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QStandardName extends EntityPathBase<StandardName> {

    private static final long serialVersionUID = -803138647L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QStandardName standardName = new QStandardName("standardName");

    public final NumberPath<Integer> categoryId = createNumber("categoryId", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final QStandardTermCategory nmCategory;

    public final QStandardTerm word;

    public final NumberPath<Long> wordId = createNumber("wordId", Long.class);

    public QStandardName(String variable) {
        this(StandardName.class, forVariable(variable), INITS);
    }

    public QStandardName(Path<? extends StandardName> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QStandardName(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QStandardName(PathMetadata metadata, PathInits inits) {
        this(StandardName.class, metadata, inits);
    }

    public QStandardName(Class<? extends StandardName> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.nmCategory = inits.isInitialized("nmCategory") ? new QStandardTermCategory(forProperty("nmCategory")) : null;
        this.word = inits.isInitialized("word") ? new QStandardTerm(forProperty("word"), inits.get("word")) : null;
    }

}

