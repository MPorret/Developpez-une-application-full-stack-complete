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
  sortOrder: boolean = true;

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
        this.posts = data;
        this.sortPosts();
      },
      (error: Error) => {
        console.error('Error fetching posts', error);
      }
    );
  }

  convertDate(dateString: string): Date {
    const [day, month, year] = dateString.split('/');
    return new Date(`${year}-${month}-${day}`);
  }


  sortPosts(): void {
    if (this.sortOrder) {
      this.posts.sort((a, b) => this.convertDate(b.updatedAt).getTime() - this.convertDate(a.updatedAt).getTime());
    } else {
      this.posts.sort((a, b) => this.convertDate(a.updatedAt).getTime() - this.convertDate(b.updatedAt).getTime());
    }
  }

  toggleSortOrder(): void {
    this.sortOrder = !this.sortOrder;
    this.sortPosts();
  }
}
