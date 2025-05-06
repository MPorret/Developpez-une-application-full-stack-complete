// post.service.ts
import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Subscription } from 'src/app/models/subscription.model';

@Injectable({
  providedIn: 'root'
})
export class SubscriptionService {

  private apiUrl = 'api/subscriptions';

  constructor(private http: HttpClient) {}

  getUsersSubscriptions(): Observable<Subscription[]> {
    return this.http.get<Subscription[]>(this.apiUrl);
  }

  createSubscription(topicId: number): Observable<any> {
    const subscription = { topicId };
    return this.http.post<any>(this.apiUrl, subscription);
  }


  deleteSubscription(topicId: number): Observable<any> {
    const params = new HttpParams().set('topicId', topicId.toString());
    return this.http.delete<any>(`${this.apiUrl}`, { params });
  }

}
