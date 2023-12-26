package com.example.demo.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class BoardFile {

    // PK
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long fileId;

    // 파일 경로
    @Column
    private String filePath;

    // 파일 이름
    @Column
    private String fileName;

    // uuid (랜덤 키)
    @Column
    private String uuid;

    // 파일 포멧
    @Column
    private String fileType;

    // 파일 크기
    @Column
    private Long fileSize;

    // 1 대 다 연관관계 매핑
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @Builder
    public BoardFile(Long fileId, String filePath, String fileName, String uuid, String fileType, Long fileSize, Board board) {
        this.fileId = fileId;
        this.filePath = filePath;
        this.fileName = fileName;
        this.uuid = uuid;
        this.fileType = fileType;
        this.fileSize = fileSize;
        this.board = board;
    }

    public BoardFile toUpdate(Board board) {
        BoardFile boardFile = new BoardFile();
        this.board = board;
        return boardFile;
    }

}
