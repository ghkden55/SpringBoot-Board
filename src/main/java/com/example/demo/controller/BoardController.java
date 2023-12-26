package com.example.demo.controller;

import com.example.demo.DTO.CommentDTO;
import com.example.demo.DTO.FileDTO;
import com.example.demo.entity.BoardFile;
import com.example.demo.repository.FileRepository;
import com.example.demo.service.BoardService;
import com.example.demo.DTO.BoardDTO;

import com.example.demo.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;
    private final CommentService commentService;
    private final FileRepository fileRepository;


    @GetMapping("/createPost")
    public String createPoast() {
        return "createPost";
    }


    @GetMapping(value = {"/paging", "/"} )
    public String paging(@PageableDefault(page = 1) Pageable pageable, Model model) {
        Page<BoardDTO> boards = boardService.paging(pageable);

        // 보여지는 페이지 수
        int blockLimit = 3;

        // 보여지는 페이지 시작 번호
        int startPage = (int)(Math.ceil((double)pageable.getPageNumber() / blockLimit) - 1) * blockLimit + 1;

        // 보여지는 페이지 끝 번호
        int endPage = ((startPage + blockLimit - 1) < boards.getTotalPages()) ? (startPage + blockLimit - 1) : boards.getTotalPages();

        model.addAttribute("boardList", boards);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "paging";
    }


    // 변경 전 데이터를 불러옴
    @GetMapping("/updatePost/{Id}")
    public String updateForm(@PathVariable Long Id, Model model) {

        BoardDTO boardDTO = boardService.findById(Id);
        model.addAttribute("board", boardDTO);

        return "updatePost";
    }


    // 변경 후 데이터를 db에 넣어줌
    @PostMapping("/updatePost")
    public String updatePost(@ModelAttribute BoardDTO boardDTO,
                             @RequestParam MultipartFile[] files) throws IOException {
        boardService.updatePost(boardDTO, files);
        return "redirect:/board/";
    }


    @GetMapping("/{id}")
    public String paging(@PathVariable Long id, Model model, @PageableDefault(page = 1) Pageable pageable) {

        // 댓글 정보 가져오기
        BoardDTO boardDTO = boardService.findById(id);
        List<CommentDTO> commentList = commentService.findAll(id);

        model.addAttribute("board", boardDTO);
        model.addAttribute("page", pageable.getPageNumber());
        model.addAttribute("commentList", commentList);

        // 파일 정보 가져오기
        List<BoardFile> byBoardFiles = fileRepository.findByBoardId(id);

        model.addAttribute("files", byBoardFiles);

        return "postDetail";
    }


    @PostMapping("/save")
    public String save(@ModelAttribute BoardDTO boardDTO,
                       @RequestParam MultipartFile[] files) throws IOException {
        boardService.save(boardDTO, files);
        return "redirect:/board/";
    }


    @GetMapping("/deletePost/{Id}")
    public String deletePost(@PathVariable Long Id) {
        boardService.delete(Id);
        return "redirect:/board/paging";
    }

}
