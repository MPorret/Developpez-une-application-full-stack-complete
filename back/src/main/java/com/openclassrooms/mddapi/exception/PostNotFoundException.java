package com.openclassrooms.mddapi.exception;

public class PostNotFoundException extends RuntimeException {
    public PostNotFoundException(Integer postId) {
        super("Post not found with id : " + postId);
    }
}
