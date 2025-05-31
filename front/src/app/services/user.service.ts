import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { RegisterDTO, User } from '../interface/user.interface';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private servicePath = '/api/user';

  constructor(private httpClient: HttpClient) { }

  public getUserInfo(): Observable<User> {
    return this.httpClient.get<User>(`${this.servicePath}/`);
  }

  public updateUserInfo(user: RegisterDTO): Observable<User> {
    return this.httpClient.patch<User>(`${this.servicePath}/`, user);
  }
}
