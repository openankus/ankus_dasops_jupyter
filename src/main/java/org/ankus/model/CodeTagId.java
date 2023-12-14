package org.ankus.model;

import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

@NoArgsConstructor
@Embeddable
public class CodeTagId implements Serializable {
    private Long codeId;
    private Long tagId;

    @Builder
    public CodeTagId(long cid, long tid) {
        this.codeId = cid;
        this.tagId = tid;
    }
}
