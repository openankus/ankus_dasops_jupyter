package org.ankus.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ShareCodeTag {
    @EmbeddedId
    private CodeTagId codeTagId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "codeId", insertable = false, updatable = false)
    private  ShareCode shareCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tagId", insertable = false, updatable = false)
    private ShareCodeSearchTag searchTag;

    @Builder
    public ShareCodeTag(CodeTagId codeTagId) {
        this.codeTagId = codeTagId;
    }
}
