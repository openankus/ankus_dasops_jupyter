package org.ankus.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = "codeList")
public class ShareCodeSearchTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tagId;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "searchTag")
    private List<ShareCodeTag> codeList = new ArrayList<>();

    @Builder
    public ShareCodeSearchTag(Long tagId, String name) {
        this.tagId = tagId;
        this.name = name;
    }

    public TagDto toDTO() {
        return TagDto.builder().tagId(this.tagId).name(this.name).build();
    }
}
