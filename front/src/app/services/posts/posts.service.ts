// post.service.ts
import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Post } from 'src/app/models/post.model';

@Injectable({
  providedIn: 'root'
})
export class PostService {

  private apiUrl = 'api/posts';

  constructor(private http: HttpClient) {}

  getPostsByUserId(userId: number): Observable<Post[]> {
    const params = new HttpParams().set('userId', userId.toString());
    return this.http.get<Post[]>(this.apiUrl, { params });
  }

  getPostById(postId: number): Observable<Post> {
    return this.http.get<Post>(`${this.apiUrl}/${postId}`);
  }

  createPost(post: Post): Observable<Post> {
    return this.http.post<Post>(this.apiUrl, post);
  }
}
