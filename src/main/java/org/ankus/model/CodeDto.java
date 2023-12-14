package org.ankus.model;

import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class CodeDto {
    private Long codeId;
    private String title;
    private String codeComment;
    private Long writer;  //user no
    private Date udate;
    private String content;
    private String name;    //user name

    private List<TagDto> tags;

    @Builder
    public CodeDto(long codeId,
                   String codeComment,
                   String content,
                   String title,
                   Date udate,
                   long writer,
                   String name) {
        this.codeId = codeId;
        this.title = title;
        this.codeComment = codeComment;
        this.writer = writer;
        this.udate = udate;
        this.content = content;
        this.name = name;
    }

    public ShareCode toEntity() {
        return ShareCode.builder()
                .codeId(codeId).title(title).content(content)
                .codeComment(codeComment).writer(writer).udate(udate)
                .build();
    }
}
