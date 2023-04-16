package com.develonity.board.entity;

import com.develonity.user.entity.TimeStamp;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn
public abstract class Board extends TimeStamp {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(nullable = false)
  private String title;

  @Column(nullable = false)
  private String content;

//  @ManyToOne
//  @JoinColumn(name = "USER_ID", nullable = false, fetch = FetchType.LAZY)
//  private User user;

  @Column(name = "USER_ID")
  private Long userId;

  public Board(Long userId, String title, String content) {
    this.userId = userId;
    this.title = title;
    this.content = content;
  }

  public void updateBoard(String title, String content) {
    this.title = title;
    this.content = content;
  }

  public Boolean isWriter(Long id) {
    return userId.equals(id);
  }


}

