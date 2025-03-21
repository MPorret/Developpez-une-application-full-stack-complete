import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from '../interfaces/user.interface';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private pathService = 'api/user';
  private getUserInfosPath = 'api/auth/me';

  constructor(private httpClient: HttpClient) { }

  public getUserById(id: number): Observable<User> {
    return this.httpClient.get<User>(`${this.pathService}/${id}`);
  }

  public getUserByToken(): Observable<User> {
    return this.httpClient.get<User>(`${this.getUserInfosPath}`);
  }

  public updateUserDetails(user: User): Observable<User> {
    return this.httpClient.put<User>(`${this.pathService}/${user.id}`, user);
  }
}
