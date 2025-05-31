import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Topic } from '../interface/topic.interface';

@Injectable({
  providedIn: 'root'
})
export class TopicService {
  private pathService = 'api/topic/';

  constructor(private httpClient: HttpClient) { }

  public all(): Observable<Topic[]> {
    return this.httpClient.get<Topic[]>(this.pathService);
  }

  public subscribe(topicId: number): Observable<Topic[]> {
    return this.httpClient.post<Topic[]>(`${this.pathService}/${topicId}/subscribe`, {});
  }

  public unsubscribe(topicId: number): Observable<Topic[]> {
    return this.httpClient.post<Topic[]>(`${this.pathService}/${topicId}/unsubscribe`, {});
  }
}
