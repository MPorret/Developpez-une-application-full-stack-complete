package com.openclassrooms.mddapi.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.DTO.CommentDTO;
import com.openclassrooms.mddapi.DTO.PostDTO;
import com.openclassrooms.mddapi.models.Comment;
import com.openclassrooms.mddapi.models.DBUser;
import com.openclassrooms.mddapi.models.Post;
import com.openclassrooms.mddapi.models.Subscription;
import com.openclassrooms.mddapi.repository.CommentRepository;
import com.openclassrooms.mddapi.repository.DBUserRepository;
import com.openclassrooms.mddapi.repository.PostRepository;
import com.openclassrooms.mddapi.repository.SubscriptionRepository;

@Service
public class PostService {

  @Autowired
  private PostRepository postRepository;

  @Autowired
  private SubscriptionRepository subscriptionRepository;

  @Autowired
  private CommentRepository commentRepository;

  @Autowired
  private DBUserRepository dbUserRepository;

  public List<PostDTO> getPostsByUserId(Long userId) {
    // Récupérer les souscriptions de l'utilisateur
    List<Subscription> subscriptions = subscriptionRepository.findByUserId(userId);

    // Extraire les topicIds des souscriptions
    List<Long> topicIds = subscriptions.stream()
                                      .map(Subscription::getTopicId)
                                      .collect(Collectors.toList());

    // Récupérer les posts liés à ces topics
    List<Post> posts = postRepository.findByTopicIdIn(topicIds);

    // Trier les posts par date de modification (du plus récent au plus ancien)
    List<Post> sortedPosts = posts.stream()
                                  .sorted((p1, p2) -> p2.getUpdatedAt().compareTo(p1.getUpdatedAt()))
                                  .collect(Collectors.toList());

    // Renvoyer une liste de post DTO

    return sortedPosts.stream()
                .map(post -> {
                  DBUser dbUser = dbUserRepository.findById(post.getUserId())
                                                  .orElseThrow(() -> new RuntimeException("User not found"));
                  return new PostDTO(
                    post.getId(),
                    post.getTitle(),
                    post.getContent(),
                    post.getUserId(),
                    dbUser.getUsername(),
                    post.getTopicId(),
                    post.getUpdatedAt(),
                    post.getCreatedAt()
                  );
                })
                .collect(Collectors.toList());
  }


// ------------------------ CREATE POST ---------------------------


  public PostDTO createPost(PostDTO postDTO) {
    Post post = new Post();
    post.setTitle(postDTO.getTitle());
    post.setContent(postDTO.getContent());
    post.setUserId(postDTO.getUserId());
    post.setTopicId(postDTO.getTopicId());

    Post savedPost = postRepository.save(post);

    DBUser dbUser = dbUserRepository.findById(savedPost.getUserId())
                                    .orElseThrow(() -> new RuntimeException("User not found"));

    return new PostDTO(
        savedPost.getId(),
        savedPost.getTitle(),
        savedPost.getContent(),
        savedPost.getUserId(),
        dbUser.getUsername(),
        savedPost.getTopicId(),
        savedPost.getCreatedAt(),
        savedPost.getUpdatedAt()
    );
  }


// ------------------------ GET POST BY ID ---------------------------


  public PostDTO getPostById(Long postId) {
    Post post = postRepository.findById(postId)
                              .orElseThrow(() -> new RuntimeException("Post not found"));

    DBUser dbUser = dbUserRepository.findById(post.getUserId())
                              .orElseThrow(() -> new RuntimeException("User not found"));

    return new PostDTO(
        post.getId(),
        post.getTitle(),
        post.getContent(),
        post.getUserId(),
        dbUser.getUsername(),
        post.getTopicId(),
        post.getCreatedAt(),
        post.getUpdatedAt()
    );
  }


// ------------------------ GET COMMENTS BY POST ID ---------------------------


  public List<CommentDTO> getCommentsByPostId(Long postId){

    List<Comment> comments = commentRepository.findByPostId(postId);

    List<Comment> sortedComments = comments.stream()
                                          .sorted((c1, c2) -> c2.getUpdatedAt().compareTo(c1.getUpdatedAt()))
                                          .collect(Collectors.toList());
    return sortedComments.stream()
                    .map(comment -> {
                      DBUser user = dbUserRepository.findById(comment.getUserId())
                                                    .orElseThrow(() -> new RuntimeException("User not found"));
                      return new CommentDTO(
                          comment.getId(),
                          comment.getContent(),
                          user.getUsername(),
                          comment.getUserId(),
                          comment.getCreatedAt(),
                          comment.getUpdatedAt()
                      );
                    })
                    .collect(Collectors.toList());
  }


// ------------------------ CREATE COMMENT ---------------------------


  public CommentDTO createComment(Long postId, CommentDTO commentDTO) {

    // Vérifier si le post existe
    if (!postRepository.existsById(postId)) {
      throw new RuntimeException("Post not found");
    }

    Comment comment = new Comment();
    comment.setContent(commentDTO.getContent());
    comment.setUserId(commentDTO.getUserId());
    comment.setPostId(postId);

    Comment savedComment = commentRepository.save(comment);

    DBUser dbUser = dbUserRepository.findById(savedComment.getUserId())
                                    .orElseThrow(() -> new RuntimeException("User not found"));

    return new CommentDTO(
        savedComment.getId(),
        savedComment.getContent(),
        dbUser.getUsername(),
        savedComment.getUserId(),
        savedComment.getCreatedAt(),
        savedComment.getUpdatedAt()
    );
  }

}
