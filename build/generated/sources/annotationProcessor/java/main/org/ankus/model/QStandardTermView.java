package org.ankus.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QStandardTermView is a Querydsl query type for StandardTermView
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QStandardTermView extends EntityPathBase<StandardTermView> {

    private static final long serialVersionUID = 950974287L;

    public static final QStandardTermView standardTermView = new QStandardTermView("standardTermView");

    public final NumberPath<Integer> category = createNumber("category", Integer.class);

    public final StringPath desc = createString("desc");

    public final StringPath engDesc = createString("engDesc");

    public final StringPath engName = createString("engName");

    public final StringPath name = createString("name");

    public final NumberPath<Long> nameId = createNumber("nameId", Long.class);

    public final NumberPath<Long> wordId = createNumber("wordId", Long.class);

    public QStandardTermView(String variable) {
        super(StandardTermView.class, forVariable(variable));
    }

    public QStandardTermView(Path<? extends StandardTermView> path) {
        super(path.getType(), path.getMetadata());
    }

    public QStandardTermView(PathMetadata metadata) {
        super(StandardTermView.class, metadata);
    }

}

