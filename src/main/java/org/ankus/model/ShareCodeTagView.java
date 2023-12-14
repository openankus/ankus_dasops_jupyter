package org.ankus.model;

import jdk.nashorn.internal.ir.annotations.Immutable;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Immutable
@Table(name = "share_code_tag_view")
@Getter
public class ShareCodeTagView {
    @EmbeddedId
    private CodeTagId codeTagId;

    private String name;
}
