package com.openclassrooms.mddapi.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "ARTICLES")
@RequiredArgsConstructor
@NoArgsConstructor
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NonNull
    @Column(name = "name", nullable = false)
    private String title;

    @NonNull
    @Column(nullable = false, length = 2000)
    private String message;

    @ManyToOne
    @NonNull
    @JoinColumn(name="author", nullable=false)
    private User author;

    @ManyToOne
    @NonNull
    @JoinColumn(name="theme_id", nullable=false)
    private Topic topic;

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;
}
