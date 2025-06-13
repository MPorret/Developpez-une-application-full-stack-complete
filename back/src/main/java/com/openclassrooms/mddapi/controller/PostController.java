package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.CommentDTO;
import com.openclassrooms.mddapi.dto.PostDTO;
import com.openclassrooms.mddapi.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for manage post
 * Endpoint for save and find a post, find all posts and save a comment
 */
@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    /**
     * Save a new post
     *
     * @param postDTO Post info
     * */
    @PostMapping("")
    public ResponseEntity<?> createPost (@RequestBody PostDTO postDTO){
        postService.createPost(postDTO);
        return ResponseEntity.ok().body("");
    }

    /**
     * Find specific post
     *
     * @param id Post's id
     * @return Response Entity with post information
     * */
    @GetMapping("/{id}")
    public ResponseEntity<?> getPostInfos (@PathVariable Integer id){
        return ResponseEntity.ok().body(postService.findPost(id));
    }

    /**
     * Find all subscribed posts
     *
     * @param authentication Authentication info of logged user
     * @return Response Entity with list of posts of subscribed topics
     * */
    @GetMapping("")
    public ResponseEntity<?> getAllSubscribedPosts (Authentication authentication) {
        return ResponseEntity.ok().body(postService.findSubscribedPosts(authentication));
    }

    /**
     * Save a comment for a post
     *
     * @param commentDTO Comment info
     * @return Response Entity with post information with list of comments updated
     * */
    @PostMapping("/comment")
    public ResponseEntity<?> addComment (@RequestBody CommentDTO commentDTO){
        return ResponseEntity.ok().body(postService.addComment(commentDTO));
    }
}
