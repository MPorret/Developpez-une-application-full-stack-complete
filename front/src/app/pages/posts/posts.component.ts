import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { SubscribeButtonComponent } from 'src/app/commons/subscribe-button/subscribe-button.component';
import { CardComponent } from "../../commons/card/card.component";
import { CommonModule } from '@angular/common';
import { MatIconModule } from '@angular/material/icon';
import { Subscription } from 'rxjs';
import { PostService } from 'src/app/services/post.service';
import { Post } from 'src/app/interface/post.interface';

@Component({
  selector: 'app-posts',
  imports: [SubscribeButtonComponent, CardComponent, CommonModule, MatIconModule],
  templateUrl: './posts.component.html',
  styleUrl: './posts.component.scss'
})
export class PostsComponent implements OnInit, OnDestroy {
  public posts!: Post[];
  public subscription!: Subscription;
  public sort = 'south';

  constructor(private router: Router, private postservice: PostService) { }

  public onClickCreate() {
    this.router.navigate(['/articles/create']);
  }

  public onClick(id: number) {
    this.router.navigate(['/articles/' + id]);
  }

  public onSort() {
    if (this.sort === 'south') {
      this.posts.sort((a, b) => new Date(a.created_at).getTime() - new Date(b.created_at).getTime());
      this.sort = 'north';
    }
    else {
      this.posts.sort((a, b) => new Date(b.created_at).getTime() - new Date(a.created_at).getTime());
      this.sort = 'south';
    }
  }

  ngOnInit(): void {
    this.subscription = this.postservice.getSubscribedPosts().subscribe({
      next: (posts: Post[]) => {
        this.posts = posts;
      this.posts.sort((a, b) => new Date(b.created_at).getTime() - new Date(a.created_at).getTime());
        this.sort = 'south';
      },
      error: (err) => {
        console.error('Error fetching posts:', err);
      }
    });
  }

  ngOnDestroy(): void {
    if (this.subscription) {
      this.subscription.unsubscribe();
    } 
  }

}
