import { Injectable } from '@angular/core';
import { BehaviorSubject, catchError, map, Observable, of, Subscription } from 'rxjs';
import { SessionInformation } from '../interface/sessionInformation.interface';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class SessionService {

  public isLogged = false;
  public sessionInformation: SessionInformation | undefined;

  private isLoggedSubject = new BehaviorSubject<boolean>(this.isLogged);

  constructor(private httpClient: HttpClient) { }

  public $isLogged(): Observable<boolean> {
    return this.isLoggedSubject.asObservable();
  }

  public logIn(user: SessionInformation): void {
    this.sessionInformation = user;
    localStorage.setItem('token', user.token);
    this.isLogged = true;
    this.next();
  }

  private next(): void {
    this.isLoggedSubject.next(this.isLogged);
  }

  public tokenIsValid(): Observable<boolean> {
    if(localStorage.getItem('token')) {
      return this.httpClient.get(`/api/auth/token`).pipe(
        map(() => {
          this.isLogged = true;
          this.next();
          return true;
        }),
        catchError(() => {
          this.sessionInformation = undefined;
          localStorage.removeItem('token');
          this.isLogged = false;
          this.next();
          return of(false);
        })
      );
    } else {      
      this.isLogged = false;
      this.next();
      return of(false);
    }
  }
}