import { Component, OnDestroy, OnInit } from '@angular/core';
import { BackButtonComponent } from "../../../commons/back-button/back-button.component";
import { CommentDto, PostService } from 'src/app/services/post.service';
import { ActivatedRoute } from '@angular/router';
import { CommonModule } from '@angular/common';
import { MatDividerModule } from '@angular/material/divider';
import { Subscription } from 'rxjs';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { FormBuilder, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { UserService } from 'src/app/services/user.service';
import { User } from 'src/app/interface/user.interface';
import { Post } from 'src/app/interface/post.interface';

@Component({
  selector: 'app-details',
  imports: [BackButtonComponent, CommonModule, ReactiveFormsModule, FormsModule, MatDividerModule, MatFormFieldModule, MatInputModule, MatIconModule],
  templateUrl: './details.component.html',
  styleUrl: './details.component.scss'
})
export class DetailsComponent implements OnInit, OnDestroy {
  public post!: Post;
  public subscriptions: Subscription[] = [];
  public userId!: number;
  public onError: boolean = false;

  public form = this.fb.group({
          comment: [
            '',
            [Validators.required]
          ]
        });

  constructor(
    private fb: FormBuilder,
    private postService:PostService,
    private userService: UserService,
    private route: ActivatedRoute) { }

  public addComment(): void {
    this.onError = false;
    const comment: CommentDto = {
      comment: this.form.value.comment!,
      author_id: this.userId,
      post_id: this.post.id
    }
    this.subscriptions.push(this.postService.addComment(comment).subscribe({
      next: (post) => {
        this.post = post;
        this.form.reset();
      },
      error: (err) => {
        this.onError = true;
      }
    }));
  }

  ngOnInit(): void {
    this.subscriptions.push(this.postService.getPostById(this.route.snapshot.params['id']).subscribe({
      next: (post) => {
        this.post = post;
      },
      error: (err) => {
        console.error('Error fetching post details:', err);
      }
    }));

    this.subscriptions.push(this.userService.getUserInfo().subscribe({
      next: (user: User) => {
        this.userId = user.id;
      }
    }));
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach((subscription) => {
      subscription.unsubscribe
    })
  }
}
