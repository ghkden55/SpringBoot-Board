package com.example.demo.entity;

import com.example.demo.DTO.CommentDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Entity
@Getter
public class Comment {

    // PK
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 작성자
    @Column (length = 45)
    private String writer;

    // 내용
    @Column (length = 300)
    private String contents;

    // 생성 시간
    @Column
    private LocalDateTime createdTime;

    // 연관관계 매핑 다:1 (댓글:게시글)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @Builder
    public Comment(Long id, String writer, String contents, LocalDateTime createdTime, Board board) {
        this.id = id;
        this.writer = writer;
        this.contents = contents;
        this.createdTime = createdTime;
        this.board = board;
    }

    public Comment toUpdate(Board board) {
        Comment comment = new Comment();
        this.board = board;
        return comment;
    }

    public void updateFromDTO(CommentDTO commentDTO){
        this.writer = commentDTO.getWriter();
        this.contents = commentDTO.getContents();
    }
}
