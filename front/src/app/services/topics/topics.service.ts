import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Topic } from 'src/app/models/topic.model';

@Injectable({
  providedIn: 'root'
})
export class TopicsService {

  private apiUrl = 'http://localhost:3001/api/topics';

  constructor( private http: HttpClient) { }

  getAllTopics(): Observable<Topic[]> {
    return this.http.get<Topic[]>(this.apiUrl);
  }
}
