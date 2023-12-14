package org.ankus.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class TagDto {
    private Long tagId;
    private String name;

    public ShareCodeSearchTag toEntity() {
        return ShareCodeSearchTag.builder()
                .tagId(tagId).name(name)
                .build();
    }

    @Builder
    public TagDto(long tagId, String name) {
        this.tagId = tagId;
        this.name = name;
    }

    public TagDto(String name) {
        this.name = name;
    }
}
