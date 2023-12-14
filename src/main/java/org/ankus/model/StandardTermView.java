package org.ankus.model;

import jdk.nashorn.internal.ir.annotations.Immutable;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Immutable
@Table(name = "standard_word_view")
@Getter
@Setter
public class StandardTermView {
    @Id
    @Column(name = "name_id")
    private Long nameId;

    @Column(name = "word_id")
    private Long wordId;

    @Column(name = "word_name")
    private String name;

    @Column(name = "eng_name")
    private String engName;

    @Column(name = "word_desc")
    private String desc;

    @Column(name = "eng_desc")
    private String engDesc;

    @Column(name = "category_id")
    private Integer category;

}
