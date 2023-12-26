package com.example.demo.DTO;

import com.example.demo.entity.Board;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BoardDTO {

    private Long id;

    // 작성자 이메일
//    private String email;

    // 작성자 이름
//    private String user_name;

    // 제목
    private String boardTitle;

    // 내용
    private String boardContents;

    // 최초 작성 시간
    private LocalDateTime create_time;

    // 최근 수정 시간
    private LocalDateTime update_time;

    private Boolean fileExist;


    public Board toEntity() {
        return Board.builder()
//                .email(email)
//                .user_name(user_name)
                .boardTitle(boardTitle)
                .boardContents(boardContents)
                .create_time(create_time)
                .fileExist(fileExist)
                .update_time(LocalDateTime.now())
                .build();
    }


    public static BoardDTO toBoardDTO(Board board) {
        return new BoardDTO(
                board.getId(),
                board.getBoardTitle(),
                board.getBoardContents(),
                board.getCreate_time(),
                board.getUpdate_time(),
                board.getFileExist()
                );
    }

}
