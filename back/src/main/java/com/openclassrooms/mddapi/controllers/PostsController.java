package com.openclassrooms.mddapi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.DTO.CommentDTO;
import com.openclassrooms.mddapi.DTO.PostDTO;
import com.openclassrooms.mddapi.services.PostService;


@RestController
@RequestMapping("/api/posts")
public class PostsController {

  @Autowired
  private PostService postService;

  @GetMapping
  public List<PostDTO> getUsersFeed(@RequestParam Long userId) {
      return postService.getPostsByUserId(userId);
  }

  @PostMapping
  public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO postDTO) {
    PostDTO createdPost = postService.createPost(postDTO);
    return ResponseEntity.ok(createdPost);
  }

  @GetMapping("/{postId}")
  public ResponseEntity<PostDTO> getPostById(@PathVariable Long postId) {
    PostDTO postDTO = postService.getPostById(postId);
    return ResponseEntity.ok(postDTO);
  }

  // @GetMapping("/{postId}/comments")
  // public List<CommentDTO> getCommentsByPostId(@PathVariable Long postId) {
  //   return postService.getCommentsByPostId(postId);
  // }

  @PostMapping("/{postId}/comments")
  public ResponseEntity<CommentDTO> createComment(@PathVariable Long postId, @RequestBody CommentDTO commentDTO) {
    CommentDTO createdComment = postService.createComment(postId, commentDTO);
    return ResponseEntity.ok(createdComment);
  }

  // /!\ /!\ /!\
  //
  //
  // ----- Reste à faire : -----
  //
  // * Le frontend
  //
  //
  // /!\ /!\ /!\
}
