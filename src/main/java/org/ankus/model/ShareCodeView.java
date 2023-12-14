package org.ankus.model;

import lombok.*;
import jdk.nashorn.internal.ir.annotations.Immutable;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Immutable
@Table(name = "share_code_view")
@Getter
public class ShareCodeView {
    @Id
    @Column(name = "code_id")
    private Long codeId;

    private String title;

    @Column(name = "code_comment")
    private String codeComment;

    //작성자 고유번호
    private Long writer;

    private Date udate;

    //작성자명
    private String name;

    //태그들을 ' #'으로 연결
    private String tags;
}
