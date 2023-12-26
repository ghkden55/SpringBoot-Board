package com.example.demo.entity;

import com.example.demo.DTO.BoardDTO;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Builder;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Entity // = sql에서 table
@Getter
public class Board {

    // PK
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 작성자 이메일
    @Column (length = 100)
    private String email;

    // 작성자 이름
    @Column (length = 45)
    private String user_name;

    // 제목
    @Column (length = 50)
    private String boardTitle;

    // 내용
    @Column (length = 300)
    private String boardContents;

    // 최초 작성 시간
    @Column
    private LocalDateTime create_time;

    // 최근 수정 시간
    @Column
    private LocalDateTime update_time;

    @Column
    private Boolean fileExist;

    // 1:다 매핑 (게시글:댓글)
    // 소유(1)와 비소유(다)
    // mappedBy = "board" : board 테이블과 매핑
    // cascade = CascadeType.REMOVE : 소유한 쪽에서 데이터를 지웠을 때(게시글 삭제), "다"에 해당하는 데이터도 함께 삭제한다.(게시글에 달린 댓글도 삭제)
    // orphanRemoval = true : 연결관계가 끊어지면 삭제
    // fetch = FetchType.LAZY : 지연 로딩 (성능 최적화)
    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Comment> comment = new ArrayList<>();

    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<BoardFile> boardFile = new ArrayList<>();


    @Builder
    public Board(Long id, String email, String user_name, String boardTitle, String boardContents, LocalDateTime create_time, LocalDateTime update_time, Boolean fileExist) {
        this.id = id;
        this.email = email;
        this.user_name = user_name;
        this.boardTitle = boardTitle;
        this.boardContents = boardContents;
        this.create_time = create_time;
        this.update_time = update_time;
        this.fileExist = fileExist;
    }


    public void updateFromDTO(BoardDTO boardDTO) {
        this.boardTitle = boardDTO.getBoardTitle();
        this.boardContents = boardDTO.getBoardContents();
        this.update_time = boardDTO.getUpdate_time();
        this.fileExist = boardDTO.getFileExist();
    }

}
