package com.openclassrooms.mddapi.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
import com.openclassrooms.mddapi.models.Topic;
import com.openclassrooms.mddapi.repository.CommentRepository;
import com.openclassrooms.mddapi.repository.DBUserRepository;
import com.openclassrooms.mddapi.repository.PostRepository;
import com.openclassrooms.mddapi.repository.SubscriptionRepository;
import com.openclassrooms.mddapi.repository.TopicRepository;

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

  @Autowired
  private TopicRepository topicRepository;

  public List<PostDTO> getPostsByUserId(Long userId) {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

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

                  List<CommentDTO> commentDTO = post.getComments().stream()
                                                    .map(comment -> {
                                                      DBUser commentUser = dbUserRepository.findById(comment.getUserId())
                                                                                          .orElseThrow(() -> new RuntimeException("User not found"));
                                                      return new CommentDTO(
                                                          comment.getId(),
                                                          comment.getContent(),
                                                          commentUser.getUsername(),
                                                          comment.getUserId(),
                                                          comment.getCreatedAt().format(formatter),
                                                          comment.getUpdatedAt().format(formatter)
                                                      );
                                                    })
                                                    .collect(Collectors.toList());

                  return new PostDTO(
                    post.getId(),
                    post.getTitle(),
                    post.getContent(),
                    post.getUserId(),
                    dbUser.getUsername(),
                    post.getTopicId(),
                    commentDTO,
                    post.getUpdatedAt().format(formatter),
                    post.getCreatedAt().format(formatter)
                  );
                })
                .collect(Collectors.toList());
  }


// ------------------------ CREATE POST ---------------------------


  public PostDTO createPost(PostDTO postDTO) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    Post post = new Post();
    post.setTitle(postDTO.getTitle());
    post.setContent(postDTO.getContent());
    post.setUserId(postDTO.getUserId());
    post.setTopicId(postDTO.getTopicId());

    Post savedPost = postRepository.save(post);

    DBUser dbUser = dbUserRepository.findById(savedPost.getUserId())
                                    .orElseThrow(() -> new RuntimeException("User not found"));

    List<CommentDTO> emptyComments = List.of();

    return new PostDTO(
        savedPost.getId(),
        savedPost.getTitle(),
        savedPost.getContent(),
        savedPost.getUserId(),
        dbUser.getUsername(),
        savedPost.getTopicId(),
        emptyComments,
        savedPost.getCreatedAt().format(formatter),
        savedPost.getUpdatedAt().format(formatter)
    );
  }


// ------------------------ GET POST BY ID ---------------------------


  public PostDTO getPostById(Long postId) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    Post post = postRepository.findById(postId)
                              .orElseThrow(() -> new RuntimeException("Post not found"));

    DBUser dbUser = dbUserRepository.findById(post.getUserId())
                              .orElseThrow(() -> new RuntimeException("User not found"));

    List<CommentDTO> commentDTO = post.getComments().stream()
                                      .map(comment -> {
                                        DBUser commentUser = dbUserRepository.findById(comment.getUserId())
                                                                            .orElseThrow(() -> new RuntimeException("User not found"));
                                        return new CommentDTO(
                                            comment.getId(),
                                            comment.getContent(),
                                            commentUser.getUsername(),
                                            comment.getUserId(),
                                            comment.getCreatedAt().format(formatter),
                                            comment.getUpdatedAt().format(formatter)
                                        );
                                      })
                                      .collect(Collectors.toList());

    return new PostDTO(
        post.getId(),
        post.getTitle(),
        post.getContent(),
        post.getUserId(),
        dbUser.getUsername(),
        post.getTopicId(),
        commentDTO,
        post.getCreatedAt().format(formatter),
        post.getUpdatedAt().format(formatter)
    );
  }


// ------------------------ CREATE COMMENT ---------------------------

  public CommentDTO createComment(Long postId, String content, Long userId) {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    // Vérifier si le post existe
    if (!postRepository.existsById(postId)) {
        throw new RuntimeException("Post not found");
    }

    // Récupérer l'utilisateur connecté depuis la base de données
    DBUser dbUser = dbUserRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));

    // Créer le commentaire
    Comment comment = new Comment();
    comment.setContent(content);
    comment.setUserId(dbUser.getId()); // Utiliser l'ID récupéré depuis le token
    comment.setPostId(postId);
    comment.setCreatedAt(LocalDateTime.now());
    comment.setUpdatedAt(LocalDateTime.now());

    Comment savedComment = commentRepository.save(comment);

    return new CommentDTO(
        savedComment.getId(),
        savedComment.getContent(),
        dbUser.getUsername(),
        dbUser.getId(),
        savedComment.getCreatedAt().format(formatter),
        savedComment.getUpdatedAt().format(formatter)
    );
  }

  public String getTopicNameById(Long topicId) {
    return topicRepository.findById(topicId)
        .map(Topic::getName)
        .orElse("Unknown Topic");
  }
}
