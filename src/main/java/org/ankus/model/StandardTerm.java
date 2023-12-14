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
@Table(name = "standard_word")
@ToString(exclude = {"wdCategory", "nameList"})
public class StandardTerm {
    @Id
    @Column(name = "word_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "eng_name", nullable = false, length = 50)
    private String engName;

    @Column(name = "word_name", length = 50)
    private String mainName;

    @Column(name = "word_desc", length = 300)
    private String desc;

    @Column(name = "eng_desc", length = 100)
    private String engDesc;

    @Column(name = "category_id", nullable = false)
    private int categoryId;

    @ManyToOne(targetEntity = StandardTermCategory.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", insertable = false, updatable = false)
    private StandardTermCategory wdCategory;

    @OneToMany(mappedBy = "word")
    private List<StandardName> nameList = new ArrayList<>();

    public StandardTerm(Long id) {
        this.id = id;
    }

    @Builder
    public StandardTerm(long id, String engName, String desc, String engDesc, int categoryId) {
        this.id = id;
        this.engName = engName;
        this.desc = desc;
        this.engDesc = engDesc;
        this.categoryId = categoryId;
    }

    public StdtrmDto toDTO() {
        return StdtrmDto.builder()
                .id(id).engName(engName).desc(desc).engDesc(engDesc).category(categoryId).build();
    }
}
