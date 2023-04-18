package com.develonity.board.service;

public interface BoardLikeService {

  long countLikes(Long boardId);

  void deleteLike(Long boardId);

  void addBoardLike(Long userId, Long boardId);

  void cancelBoardLike(Long userId, Long boardId);

  boolean existsLikesBoardIdAndUserId(Long boardId, Long userId);


}
