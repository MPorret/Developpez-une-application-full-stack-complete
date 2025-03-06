package com.openclassrooms.mddapi.models;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name="comments")
public class Comment {


  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "content")
  private String content;

  @Column(name = "user_id")
  private Long userId;

  @Column(name = "post_id")
  private Long postId;

  @CreationTimestamp
  @Column(name = "created_at", updatable = false)
  private LocalDateTime createdAt;

  @UpdateTimestamp
  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  // Getters

  public Long getId() {
    return this.id;
  }

  public String getContent() {
    return this.content;
  }

  public Long getUserId() {
    return this.userId;
  }

  public Long getPostId() {
    return this.postId;
  }

  public LocalDateTime getCreatedAt() {
    return this.createdAt;
  }

  public LocalDateTime getUpdatedAt() {
    return this.updatedAt;
  }

  // Setters

  public void setContent(String content) {
    this.content = content;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public void setPostId(Long postId) {
    this.postId = postId;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public void setUpdatedAt(LocalDateTime updatedAt) {
    this.updatedAt = updatedAt;
  }

}
