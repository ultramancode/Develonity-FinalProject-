package com.develonity.board.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.develonity.board.dto.CommunityBoardRequest;
import com.develonity.board.entity.CommunityBoard;
import com.develonity.board.entity.CommunityCategory;
import com.develonity.board.entity.Scrap;
import com.develonity.board.repository.CommunityBoardRepository;
import com.develonity.board.repository.ScrapRepository;
import com.develonity.user.entity.User;
import com.develonity.user.repository.UserRepository;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@SpringBootTest
class ScrapServiceImplTest {

  @Autowired
  private UserRepository userRepository;
  @Autowired
  private CommunityBoardRepository communityBoardRepository;

  @Autowired
  private ScrapRepository scrapRepository;

  @Autowired
  private ScrapService scrapService;

  @Autowired
  private CommunityBoardService communityBoardService;

  @BeforeEach
  void allDeleteBefore() {
    communityBoardRepository.deleteAll();
  }

  @AfterEach
  void allDeleteAfter() {
    communityBoardRepository.deleteAll();
    scrapRepository.deleteAll();
  }

  CommunityBoardRequest request = new CommunityBoardRequest("제목2", "내용2",
      CommunityCategory.NORMAL);
  List<MultipartFile> multipartFiles = new ArrayList<>();
  CommunityBoardRequest request2 = new CommunityBoardRequest("제목22", "내용22",
      CommunityCategory.NORMAL);
  List<MultipartFile> multipartFiles2 = new ArrayList<>();

  @Test
  @DisplayName("스크랩 추가 & 삭제 & 예외")
  void addScrap() throws IOException {
    Optional<User> findUser = userRepository.findById(1L);

    //when
    CommunityBoard createCommunityBoard = communityBoardService.createCommunityBoard(request,
        multipartFiles, findUser.get());
    scrapService.addScrap(findUser.get().getId(), createCommunityBoard.getId());

    assertThat(scrapRepository.findByBoardIdAndUserId(createCommunityBoard.getId(),
        findUser.get().getId())).isNotNull();
    //이미 스크랩 추가한 경우 예외 떠야함
    assertThrows(Exception.class,
        () -> scrapService.addScrap(findUser.get().getId(), createCommunityBoard.getId()));

    scrapService.cancelScrap(
        findUser.get().getId(), createCommunityBoard.getId());

    assertThat(scrapRepository.findByBoardIdAndUserId(createCommunityBoard.getId(),
        findUser.get().getId())).isNull();

    //이미 스크랩 취소한 경우 예외 떠야함
    assertThrows(Exception.class, () -> scrapService.cancelScrap(
        createCommunityBoard.getId(),
        findUser.get().getId()));


  }

  @Test
  @DisplayName("스크랩한 게시물 ID들 리턴")
  void getScrapBoardIds() throws IOException {

    Optional<User> findUser = userRepository.findById(1L);
    CommunityBoard createCommunityBoard = communityBoardService.createCommunityBoard(request2,
        multipartFiles, findUser.get());
    CommunityBoard createCommunityBoard2 = communityBoardService.createCommunityBoard(request,
        multipartFiles2, findUser.get());

    scrapService.addScrap(findUser.get().getId(), createCommunityBoard.getId());
    scrapService.addScrap(findUser.get().getId(), createCommunityBoard2.getId());

    assertThat(scrapService.getScrapBoardIds(findUser.get().getId())).size().isEqualTo(2);

  }

  //유저가 총 스크랩한 갯수
  @Test
  @DisplayName("스크랩한 게시물 갯수")
  void countScraps() throws IOException {
    Optional<User> findUser = userRepository.findById(1L);
    CommunityBoard createCommunityBoard = communityBoardService.createCommunityBoard(request2,
        multipartFiles, findUser.get());

    scrapService.addScrap(findUser.get().getId(), createCommunityBoard.getId());

    assertThat(scrapService.countScraps(findUser.get().getId())).isEqualTo(1L);

  }


  //해당 유저가 해당 게시글에 스크랩 했는지 여부
  @Test
  @DisplayName("현재 유저가 해당 게시글 스크랩 했는지 여부")
  public void existsScrapBoardIdAndUserId() throws IOException {

    Optional<User> findUser = userRepository.findById(1L);
    CommunityBoard createCommunityBoard = communityBoardService.createCommunityBoard(request2,
        multipartFiles, findUser.get());
    CommunityBoard createCommunityBoard2 = communityBoardService.createCommunityBoard(request,
        multipartFiles2, findUser.get());

    scrapService.addScrap(findUser.get().getId(), createCommunityBoard.getId());

    assertThat(scrapService.existsScrapBoardIdAndUserId(createCommunityBoard.getId(), findUser.get().getId())).isTrue();
    assertThat(scrapService.existsScrapBoardIdAndUserId(createCommunityBoard2.getId(), findUser.get().getId())).isFalse();
  }
}



