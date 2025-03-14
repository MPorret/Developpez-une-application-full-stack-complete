// posts-list.component.ts
import { Component, OnInit } from '@angular/core';
import { PostService } from 'src/app/services/posts/posts.service';
import { Post } from 'src/app/models/post.model';
import { AuthService } from 'src/app/auth/services/auth.service';
import { User } from 'src/app/interfaces/user.interface';

@Component({
  selector: 'app-posts',
  templateUrl: './posts.component.html',
  styleUrls: ['./posts.component.scss']
})
export class PostsComponent implements OnInit {

  posts: Post[] = [];
  currentUserId: number = 0;

  constructor(private postService: PostService, private authService: AuthService) {}

  ngOnInit(): void {
    this.authService.me().subscribe(
      (user: User) => {
        this.currentUserId = user.id;
        this.loadPosts();
      },
      (error: Error) => {
        console.error('Error fetching current user', error);
      }
    );
  }

  loadPosts(): void {
    this.postService.getPostsByUserId(this.currentUserId).subscribe(
      (data: Post[]) => {
        console.log('data from api: ', data);
        // /!\
        // /!\
        // ---------> /!\ this.posts n'est pas mis à jour avec data et reste vide /!\ <---------
        // /!\
        // /!\
        //
        //
        // ------- A faire : -------
        // -créer la route de création d'article en back
        // -page articles + modification de la navbar
        // -Ajout de commentaires
        // -infos utilisateur
        // -responsive
        //
        //
        this.posts = data;
      },
      (error: Error) => {
        console.error('Error fetching posts', error);
      }
    );
    console.log('this posts -> ', this.posts);
  }
}
