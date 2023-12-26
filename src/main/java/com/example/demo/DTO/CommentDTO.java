package com.example.demo.DTO;

import com.example.demo.entity.Comment;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {


    private Long id;

    private String writer;

    private String contents;

    private LocalDateTime createdTime;

    private Long boardId;


    public Comment toEntity() {
        return Comment.builder()
                .writer(writer)
                .contents(contents)
                .createdTime(createdTime)
                .build();

    }


    public static CommentDTO toCommentDTO(Comment comment, Long boardId) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(comment.getId());
        commentDTO.setWriter(comment.getWriter());
        commentDTO.setContents(comment.getContents());
        commentDTO.setCreatedTime(comment.getCreatedTime());
        commentDTO.setBoardId(boardId);
        return commentDTO;
    }
}
