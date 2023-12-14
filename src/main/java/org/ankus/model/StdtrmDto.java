package org.ankus.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StdtrmDto {
    private Long wordId;
    private String engName;
    private String desc;
    private String engDesc;
    private Integer category;

    @Builder
    public StdtrmDto(long id,
                   String engName,
                   String desc,
                     String engDesc,
                   int category) {
        this.wordId = id;
        this.engName = engName;
        this.desc = desc;
        this.engDesc = engDesc;
        this.category = category;
    }
}
