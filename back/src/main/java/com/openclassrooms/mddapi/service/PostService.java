package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.*;
import com.openclassrooms.mddapi.exception.PostNotFoundException;
import com.openclassrooms.mddapi.exception.TopicNotFoundException;
import com.openclassrooms.mddapi.exception.UserNameNotFoundException;
import com.openclassrooms.mddapi.exception.UserNotFoundException;
import com.openclassrooms.mddapi.mapper.CommentMapper;
import com.openclassrooms.mddapi.mapper.PostMapper;
import com.openclassrooms.mddapi.model.Comment;
import com.openclassrooms.mddapi.model.Post;
import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.CommentRepository;
import com.openclassrooms.mddapi.repository.PostRepository;
import com.openclassrooms.mddapi.repository.TopicRepository;
import com.openclassrooms.mddapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * Service to manage posts.
 */
@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final TopicRepository topicRepository;
    private final CommentRepository commentRepository;
    private final PostMapper mapper;
    private final CommentMapper commentMapper;
    private final TopicService topicService;

    /**
     * Save a new post
     *
     * @param postDTO Post info
     * @throws UserNameNotFoundException if user is not found with this name
     * @throws TopicNotFoundException if topic doesn't exist with id
     * */
    public void createPost (PostDTO postDTO){
        User user = userRepository.findById(postDTO.getAuthor_id()).orElseThrow(() -> new UserNotFoundException(postDTO.getAuthor_id()));
        Topic topic = topicRepository.findById(postDTO.getTopic_id()).orElseThrow(() -> new TopicNotFoundException(postDTO.getTopic_id()));
        Post post = mapper.toEntity(postDTO, user, topic);

        postRepository.save(post);
    }

    /**
     * Find specific post
     *
     * @param id Post's id
     * @return Post information
     * @throws PostNotFoundException if post is not found with this id
     * */
    public PostResponseDTO findPost (Integer id){
        Post post = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id));
        PostResponseDTO response = mapper.toDto(post);
        List<MinimalCommentResponseDTO> comments = commentMapper.toDto(commentRepository.findAll().stream()
                .filter(comment -> Objects.equals(comment.getPost().getId(), response.getId()))
                .toList());
        response.setComments(comments);
        return response;
    }

    /**
     * Find all subscribed posts
     *
     * @param authentication Authentication info of logged user
     * @return List of posts of subscribed topics
     * @throws UserNameNotFoundException if user is not found with this name
     * */
    public List<PostResponseDTO> findSubscribedPosts (Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new UserNameNotFoundException(authentication.getName()));
        List<Topic> topics = topicService.findAllTopicsByUser(user);
        List<Post> posts = postRepository
                .findAll().stream()
                .filter(post -> topics.stream()
                        .anyMatch(topic -> Objects.equals(topic.getId(), post.getTopic().getId())))
                .toList();

        return mapper.toDto(posts);
    }

    /**
     * Save a comment for a post
     *
     * @param commentDTO Comment info
     * @return Post information with list of comments updated
     * @throws UserNameNotFoundException if user is not found with this name
     * @throws PostNotFoundException if post doesn't exist with id
     * */
    public PostResponseDTO addComment (CommentDTO commentDTO) {
        User user = userRepository.findById(commentDTO.getAuthor_id())
                .orElseThrow(() -> new UserNotFoundException(commentDTO.getAuthor_id()));
        Post post = postRepository.findById(commentDTO.getPost_id())
                .orElseThrow(() -> new PostNotFoundException(commentDTO.getPost_id()));
        Comment comment = commentMapper.toEntity(commentDTO, user, post);
        commentRepository.save(comment);
        return this.findPost(comment.getPost().getId());
    }
}
