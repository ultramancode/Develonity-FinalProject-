package com.develonity.board.dto;

import com.develonity.board.entity.CommunityBoard;
import com.develonity.board.entity.CommunityCategory;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
//redis 캐싱 기능 위해 기본 생성자
@NoArgsConstructor
public class CommunityBoardResponse {

  private Long id;
  private String nickname;
  private CommunityCategory communityCategory;
  private String title;
  private String content;
  @JsonSerialize(using = LocalDateTimeSerializer.class)
  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
  private LocalDateTime createdAt;
  @JsonSerialize(using = LocalDateTimeSerializer.class)
  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
  private LocalDateTime lastModifiedAt;
  private long countAllComments;

  //  private long countAllReplyComments;
  private long boardLike;

  private boolean hasLike;

  private List<String> imagePaths;

  //querydsl용..
  public CommunityBoardResponse(
      Long id,
      String nickname,
      CommunityCategory communityCategory,
      String title,
      String content,
      LocalDateTime createdAt,
      LocalDateTime lastModifiedAt,
      long countAllComment,
      long countAllReplyComments,
      long boardLike
  ) {
    this.id = id;
    this.nickname = nickname;
    this.communityCategory = communityCategory;
    this.title = title;
    this.content = content;
    this.boardLike = boardLike;
    this.createdAt = createdAt;
    this.lastModifiedAt = lastModifiedAt;
    this.countAllComments = countAllComment + countAllReplyComments;
//    this.countAllReplyComments = countAllReplyComments;
  }

//


  public CommunityBoardResponse(CommunityBoard communityBoard, String nickname, long boardLike,
      Boolean isLike, List<String> imagePaths, long countAllComments) {
    this.id = communityBoard.getId();
    this.nickname = nickname;
    this.communityCategory = communityBoard.getCommunityCategory();
    this.title = communityBoard.getTitle();
    this.content = communityBoard.getContent();
    this.boardLike = boardLike;
    this.createdAt = communityBoard.getCreatedDate();
    this.lastModifiedAt = communityBoard.getLastModifiedDate();
    this.hasLike = isLike;
    this.imagePaths = imagePaths;
    this.countAllComments = countAllComments;


  }

  public static CommunityBoardResponse toCommunityBoardResponse(CommunityBoard communityBoard,
      String nickname, long countAllComments) {
    return CommunityBoardResponse.builder()
        .id(communityBoard.getId())
        .nickname(nickname)
        .communityCategory(communityBoard.getCommunityCategory())
        .title(communityBoard.getTitle())
        .content(communityBoard.getContent())
        .createdAt(communityBoard.getCreatedDate())
        .lastModifiedAt(communityBoard.getLastModifiedDate())
        .countAllComments(countAllComments)
        .build();
  }

//  public CommunityBoardResponse(Long id, String nickname, CommunityCategory communityCategory,
//      String title, String content, LocalDateTime createdAt, LocalDateTime lastModifiedAt,
//      long boardLike) {
//    this.id = id;
//    this.nickname = nickname;
//    this.communityCategory = communityCategory;
//    this.title = title;
//    this.content = content;
//    this.createdAt = createdAt;
//    this.lastModifiedAt = lastModifiedAt;
//    this.boardLike = boardLike;
//  }
//
//  public CommunityBoardResponse(Long id, long commentsCount, long replyCommentsCount) {
//    this.id = id;
//    this.countAllComments = commentsCount + replyCommentsCount;
//  }
}
