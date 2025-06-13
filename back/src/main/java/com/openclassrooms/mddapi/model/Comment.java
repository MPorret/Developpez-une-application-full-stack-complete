package com.openclassrooms.mddapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "COMMENTAIRES")
@RequiredArgsConstructor
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NonNull
    @Column(nullable = false, length = 2000, name = "message")
    private String comment;

    @ManyToOne
    @NonNull
    @JoinColumn(name="author", nullable=false)
    @JsonProperty("author")
    private User commentAuthor;

    @ManyToOne
    @NonNull
    @JoinColumn(name="article_id", nullable=false)
    private Post post;

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;
}
