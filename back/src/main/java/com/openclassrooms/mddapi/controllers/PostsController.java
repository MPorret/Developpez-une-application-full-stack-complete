package com.openclassrooms.mddapi.controllers;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.mddapi.DTO.CommentDTO;
import com.openclassrooms.mddapi.DTO.PostDTO;
import com.openclassrooms.mddapi.models.DBUser;
import com.openclassrooms.mddapi.repository.DBUserRepository;
import com.openclassrooms.mddapi.services.DBUserService;
import com.openclassrooms.mddapi.services.PostService;


@RestController
@RequestMapping("/api/posts")
public class PostsController {

  @Autowired
  private PostService postService;

  @Autowired
  private DBUserRepository dbUserRepository;

  @GetMapping
  public List<PostDTO> getUsersFeed(@RequestParam Long userId) {
      return postService.getPostsByUserId(userId);
  }

  @PostMapping
  public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO postDTO) {
    PostDTO createdPost = postService.createPost(postDTO);
    return ResponseEntity.ok(createdPost);
  }

  // @GetMapping("/{postId}")
  // public ResponseEntity<PostDTO> getPostById(@PathVariable Long postId) {
  //   PostDTO postDTO = postService.getPostById(postId);
  //   return ResponseEntity.ok(postDTO);
  // }

  @GetMapping("/{postId}")
  public ResponseEntity<?> getPostById(@PathVariable Long postId) {
      PostDTO postDTO = postService.getPostById(postId);

      // Convertir l'objet DTO en Map
      ObjectMapper objectMapper = new ObjectMapper();
      Map<String, Object> postMap = objectMapper.convertValue(postDTO, new TypeReference<Map<String, Object>>() {});

      // Ajouter topicName dans la map
      String topicName = postService.getTopicNameById(postDTO.getTopicId());
      postMap.put("topicName", topicName);

      return ResponseEntity.ok(postMap);
  }

  // @GetMapping("/{postId}/comments")
  // public List<CommentDTO> getCommentsByPostId(@PathVariable Long postId) {
  //   return postService.getCommentsByPostId(postId);
  // }

  // @PostMapping("/{postId}/comments")
  // public ResponseEntity<CommentDTO> createComment(@PathVariable Long postId, @RequestBody CommentDTO commentDTO) {
  //   CommentDTO createdComment = postService.createComment(postId, commentDTO);
  //   return ResponseEntity.ok(createdComment);
  // }

  @PostMapping("/{postId}/comments")
  public ResponseEntity<CommentDTO> createComment(
          @PathVariable Long postId,
          @RequestBody Map<String, String> requestBody) {

      // Récupération de l'utilisateur connecté
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

      if (authentication == null || !authentication.isAuthenticated()) {
          return ResponseEntity.status(401).build();
      }

      String email = authentication.getName(); // Récupérer l'email de l'utilisateur connecté

      // Trouver l'utilisateur en base
      Optional<DBUser> userOpt = dbUserRepository.findByEmail(email);
      if (userOpt.isEmpty()) {
          return ResponseEntity.status(404).body(null);
      }

      DBUser user = userOpt.get();
      Long userId = user.getId(); // Récupération de l'ID utilisateur

      // Récupération du contenu du commentaire
      String content = requestBody.get("content");

      // Création du commentaire
      CommentDTO createdComment = postService.createComment(postId, content, userId);

      return ResponseEntity.ok(createdComment);
  }

}
