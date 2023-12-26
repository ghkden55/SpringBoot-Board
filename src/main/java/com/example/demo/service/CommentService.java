package com.example.demo.service;

import com.example.demo.repository.BoardRepository;
import com.example.demo.repository.CommentRepository;
import com.example.demo.DTO.CommentDTO;
import com.example.demo.entity.Board;
import com.example.demo.entity.Comment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class CommentService {

    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public Comment save(CommentDTO commentDTO) {
        Optional<Board> optionalComment = boardRepository.findById(commentDTO.getBoardId());
        commentDTO.setCreatedTime(LocalDateTime.now());

        if(optionalComment.isPresent()) {
            Board board = optionalComment.get();

            Comment comment = commentDTO.toEntity();
            comment.toUpdate(board);
            return commentRepository.save(comment);
        } else {
            return null;
        }
    }

    public List<CommentDTO> findAll(Long boardId) {
        Board boardEntity = boardRepository.findById(boardId).get();
        java.util.List<Comment> commentEntityList = commentRepository.findAllByBoardOrderByIdDesc(boardEntity);
        /* EntityList -> DTOList */
        List<CommentDTO> commentDTOList = new ArrayList<>();
        for (Comment commentEntity: commentEntityList) {
            CommentDTO commentDTO = CommentDTO.toCommentDTO(commentEntity, boardId);
            commentDTOList.add(commentDTO);
        }
        return commentDTOList;
    }


    public Optional<Comment> findById(Long id) {
        return commentRepository.findById(id);
    }


    @Transactional
    public void update(CommentDTO commentDTO) {
        Optional<Comment> commentOptional = commentRepository.findById(commentDTO.getId());

        Comment comment = commentOptional.get();

        comment.updateFromDTO(commentDTO);

        commentRepository.save(comment);
    }


    @Transactional
    public void delete(Long id) {
        commentRepository.deleteById(id);
    }

}
