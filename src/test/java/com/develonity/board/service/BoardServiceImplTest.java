package com.develonity.board.service;

import static org.junit.jupiter.api.Assertions.*;

import com.develonity.board.dto.BoardPage;
import com.develonity.board.dto.BoardResponse;
import com.develonity.board.dto.CommunityBoardRequest;
import com.develonity.board.entity.Board;
import com.develonity.board.entity.CommunityBoard;
import com.develonity.board.entity.CommunityCategory;
import com.develonity.board.repository.BoardRepository;
import com.develonity.user.entity.User;
import com.develonity.user.repository.UserRepository;
import com.develonity.user.service.UserService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.multipart.MultipartFile;

@SpringBootTest
class BoardServiceImplTest {

  @Autowired
  private BoardService boardService;
  @Autowired
  private BoardRepository boardRepository;
  @Autowired
  private ScrapService scrapService;
  @Autowired
  private UserService userService;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private CommunityBoardService communityBoardService;

  @Test
  @DisplayName("스크랩한 전체 게시글 조회")
  void getScrapBoardPage() throws IOException {

    CommunityBoardRequest request = new CommunityBoardRequest("제목생성", "내용생성",
        CommunityCategory.NORMAL);
    CommunityBoardRequest requestSecond = new CommunityBoardRequest("제목생성2", "내용생성2",
        CommunityCategory.NORMAL);

    BoardPage boardPage = new BoardPage();

    Optional<User> firstUser = userRepository.findById(1L);
    Optional<User> secondUser = userRepository.findById(2L);

    List<MultipartFile> multipartFiles = new ArrayList<>();

    CommunityBoard createCommunityBoard = communityBoardService.createCommunityBoard(request,
        multipartFiles, firstUser.get());
    CommunityBoard createCommunityBoardSecond = communityBoardService.createCommunityBoard(requestSecond,
        multipartFiles, secondUser.get());


    scrapService.addScrap(firstUser.get().getId(),createCommunityBoard.getId());
    scrapService.addScrap(firstUser.get().getId(),createCommunityBoardSecond.getId());

    Page<BoardResponse> scrapBoards = boardService.getScrapBoardPage(firstUser.get(),boardPage);
    Page<BoardResponse> scrapBoardsBySecondUser = boardService.getScrapBoardPage(secondUser.get(),boardPage);
    Assertions.assertThat(scrapBoards).size().isEqualTo(2);
    Assertions.assertThat(scrapBoardsBySecondUser).size().isEqualTo(0);

  }

  @Test
  @DisplayName("게시글 내 유저 닉네임 리턴")
  void getNicknameByBoard() throws IOException {

    CommunityBoardRequest request = new CommunityBoardRequest("제목생성", "내용생성",
        CommunityCategory.NORMAL);
    Optional<User> findUser = userRepository.findById(1L);
    List<MultipartFile> multipartFiles = new ArrayList<>();
    CommunityBoard createCommunityBoard = communityBoardService.createCommunityBoard(request,
        multipartFiles, findUser.get());

    Assertions.assertThat(boardService.getNicknameByBoard(createCommunityBoard)).isEqualTo(findUser.get().getNickname());
  }
}