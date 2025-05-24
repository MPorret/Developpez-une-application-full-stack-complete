import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Topic } from '../interface/topic.interface';

@Injectable({
  providedIn: 'root'
})
export class TopicService {
  private pathService = 'topic/';

  constructor(private httpClient: HttpClient) { }

  public all(): Observable<Topic[]> {
    return this.httpClient.get<Topic[]>("/api/" + this.pathService);
  }
}
