import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Subject } from '../interface/subject.interface';

export interface SubjectDto {
  title: string;
  message: string;
  topic_id: number;
  author_id: number;
}

@Injectable({
  providedIn: 'root'
})
export class SubjectService {

  constructor(private httpClient: HttpClient) { }

  public getSubscribedSubjects(): Observable<Subject[]> {
    return this.httpClient.get<Subject[]>('/api/subject');
  }

  public getSubjectById(id: number): Observable<Subject> {
    return this.httpClient.get<Subject>(`/api/subject/${id}`);
  }

  public createSubject(subject: SubjectDto): Observable<void> {
    return this.httpClient.post<void>('/api/subject', subject);
  }
}
