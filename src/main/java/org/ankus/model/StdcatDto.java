package org.ankus.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StdcatDto {
    private Integer id;
    private String name;

    @Builder
    public StdcatDto(int id,
                     String name) {
        this.id = id;
        this.name = name;
    }
}
