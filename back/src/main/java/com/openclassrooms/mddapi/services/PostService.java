package com.openclassrooms.mddapi.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.DTO.PostDTO;
import com.openclassrooms.mddapi.models.Post;
import com.openclassrooms.mddapi.models.Subscription;
import com.openclassrooms.mddapi.repository.PostRepository;
import com.openclassrooms.mddapi.repository.SubscriptionRepository;

@Service
public class PostService {

  @Autowired
  private PostRepository postRepository;

  @Autowired
  private SubscriptionRepository subscriptionRepository;

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
                .map(post -> new PostDTO(
                    post.getId(),
                    post.getTitle(),
                    post.getContent(),
                    post.getUserId(),
                    post.getTopicId(),
                    post.getUpdatedAt(),
                    post.getCreatedAt()
                ))
                .collect(Collectors.toList());
  }

  public PostDTO createPost(PostDTO postDTO) {
    Post post = new Post();
    post.setTitle(postDTO.getTitle());
    post.setContent(postDTO.getContent());
    post.setUserId(postDTO.getUserId());
    post.setTopicId(postDTO.getTopicId());

    Post savedPost = postRepository.save(post);

    return new PostDTO(
        savedPost.getId(),
        savedPost.getTitle(),
        savedPost.getContent(),
        savedPost.getUserId(),
        savedPost.getTopicId(),
        savedPost.getCreatedAt(),
        savedPost.getUpdatedAt()
    );
  }
}
