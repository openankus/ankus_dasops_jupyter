package org.ankus.model;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@DynamicUpdate
@DynamicInsert
@NoArgsConstructor
@ToString(exclude = "tagList")
public class ShareCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codeId;

    @Column(nullable = false)
    private String title;

    @Column(name = "code_comment")
    private String codeComment;

    @Column(nullable = false)
    private Long writer;

    @Column(nullable = false)
    @UpdateTimestamp
    private Date udate;

    @Column(nullable = false)
    private String content;

    @OneToMany(mappedBy = "shareCode")
    private List<ShareCodeTag> tagList = new ArrayList<>();

    @Builder
    public ShareCode(long codeId,
                     String codeComment,
                     String content,
                     String title,
                     Date udate,
                     long writer) {
        this.codeId = codeId;
        this.title = title;
        this.codeComment = codeComment;
        this.writer = writer;
        this.udate = udate;
        this.content = content;
    }

    public CodeDto toDTO() {
        return CodeDto.builder()
                .codeId(codeId).title(title).content(content)
                .codeComment(codeComment).writer(writer).udate(udate)
                .build();
    }
}
