package org.ankus.model;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@DynamicInsert
@NoArgsConstructor
@Table(name = "standard_name")
@ToString(exclude = {"word", "nmCategory"})
public class StandardName {
    @Id
    @Column(name = "name_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "word_name", nullable = false, length = 50)
    private String name;

    @Column(name = "category_id", nullable = false)
    private Integer categoryId;

    @Column(name = "word_id", nullable = false)
    private Long wordId;

    @ManyToOne(targetEntity = StandardTermCategory.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", insertable = false, updatable = false)
    private StandardTermCategory nmCategory;

    @ManyToOne(targetEntity = StandardTerm.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "word_id", insertable = false, updatable = false)
    private StandardTerm word;

@Builder
    public StandardName(long id, String name, int categoryId, long wordId) {
        this.id = id;
        this.name = name;
        this.categoryId = categoryId;
        this.wordId = wordId;
    }
}
