import { Component, OnInit } from '@angular/core';
import { PostService } from 'src/app/services/posts/posts.service';
import { Post } from 'src/app/models/post.model';
import { AuthService } from 'src/app/auth/services/auth.service';
import { User } from 'src/app/interfaces/user.interface';
import { DEFAULT_POST } from 'src/app/models/post.model';
import { ActivatedRoute } from '@angular/router';
import { CommentService } from 'src/app/services/comments/comments.service';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';

@Component({
  selector: 'app-posts',
  templateUrl: './postDetails.component.html',
  styleUrls: ['./postDetails.component.scss']
})
export class PostDetailsComponent implements OnInit {

  post: Post = { ...DEFAULT_POST };
  currentUserId: number = 0;
  postId: number = 0;
  newCommentContent: string = '';
  private destroy$ = new Subject<void>();

  constructor(
    private postService: PostService,
    private authService: AuthService,
    private route: ActivatedRoute,
    private commentService: CommentService
  ) {}

  ngOnInit(): void {
    // Récupérer l'ID du post depuis l'URL
    this.route.paramMap.pipe(takeUntil(this.destroy$)).subscribe(params => {
      const id = params.get('id');
      if (id) {
        this.postId = +id; // Convertir en nombre
        this.loadPostDetails();
      }
    });

    // Récupérer l'utilisateur connecté
    this.authService.me().pipe(takeUntil(this.destroy$)).subscribe(
      (user: User) => {
        this.currentUserId = user.id;
      },
      (error: Error) => {
        console.error('Error fetching current user', error);
      }
    );
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  loadPostDetails(): void {
    if (!this.postId) return;

    this.postService.getPostById(this.postId).pipe(takeUntil(this.destroy$)).subscribe(
      (data: Post) => {
        this.post = data;
      },
      (error: Error) => {
        console.error('Error fetching post details', error);
      }
    );
  }

  // Méthode pour soumettre un commentaire
  submitComment(): void {
    if (this.newCommentContent.trim()) {
      // Appel du service pour créer un commentaire
      this.commentService.createComment(this.postId, this.newCommentContent).pipe(takeUntil(this.destroy$)).subscribe(
        (response) => {
          // Ajouter le commentaire à la liste locale après la création
          this.post.comments.push(response);
          this.newCommentContent = ''; // Réinitialiser le champ de texte
        },
        (error) => {
          console.error('Error submitting comment', error);
        }
      );
    }
  }
}
