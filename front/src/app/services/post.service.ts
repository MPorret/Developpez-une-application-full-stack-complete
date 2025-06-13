import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Post } from '../interface/post.interface';

export interface PostDto {
  title: string;
  message: string;
  topic_id: number;
  author_id: number;
}

export interface CommentDto {
  comment: string;
  post_id: number;
  author_id: number;
}

@Injectable({
  providedIn: 'root'
})
export class PostService {

  constructor(private httpClient: HttpClient) { }

  public getSubscribedPosts(): Observable<Post[]> {
    return this.httpClient.get<Post[]>('/api/post');
  }

  public getPostById(id: number): Observable<Post> {
    return this.httpClient.get<Post>(`/api/post/${id}`);
  }

  public createPost(post: PostDto): Observable<void> {
    return this.httpClient.post<void>('/api/post', post);
  }

  public addComment(comment: CommentDto): Observable<Post> {
    return this.httpClient.post<Post>(`/api/post/comment`, comment);
  }
}
