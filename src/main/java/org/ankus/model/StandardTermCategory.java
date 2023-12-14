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
@Table(name = "standard_word_category")
@ToString(exclude = {"termList", "nameList"})
public class StandardTermCategory {
    @Id
    @Column(name = "category_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "category_name", nullable = false, length = 50)
    private String name;

    @OneToMany(mappedBy = "nmCategory")
    private List<StandardName> nameList = new ArrayList<>();

    @OneToMany(mappedBy = "wdCategory")
    private List<StandardTerm> termList = new ArrayList<>();

    public StdcatDto toDTO() {
        return StdcatDto.builder()
                .id(id).name(name).build();
    }

    @Builder
    public StandardTermCategory(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
