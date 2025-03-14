import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, tap } from 'rxjs';
import { LoginRequest } from '../interfaces/loginRequest.interface';
import { AuthSuccess  } from '../interfaces/authSuccess.interface';
import { RegisterRequest } from '../interfaces/registerRequest.interface';
import { User } from 'src/app/interfaces/user.interface';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private pathService = 'api/auth';

  constructor(private httpClient: HttpClient) { }

  public register(registerRequest: RegisterRequest): Observable<AuthSuccess> {
    return this.httpClient.post<AuthSuccess>(`${this.pathService}/register`, registerRequest);
  }

  // public login(loginRequest: LoginRequest): Observable<AuthSuccess> {
  //   return this.httpClient.post<AuthSuccess>(`${this.pathService}/login`, loginRequest);
  // }

  public login(loginRequest: LoginRequest): Observable<AuthSuccess> {
    return this.httpClient.post<AuthSuccess>(`${this.pathService}/login`, loginRequest).pipe(
      tap((response: AuthSuccess) => {
        localStorage.setItem('token', response.token);
      })
    );
  }

  public logout(): Observable<void> {
    return this.httpClient.post<void>(`/api/logout`, null).pipe(
      tap(() => {
        localStorage.removeItem('token');
      })
    );
  }

  public me(): Observable<User> {
    const headers = { Authorization: `Bearer ${localStorage.getItem('token')}` };
    return this.httpClient.get<User>(`${this.pathService}/me`);
  }
}
